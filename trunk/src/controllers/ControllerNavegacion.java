/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelNavegacion;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.dataCapture.Sistema;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.AdministraCatPoi;
import modelo.dataManager.CategoriaPoi;
import modelo.gisModule.Browser;
import modelo.dataManager.Punto;
import modelo.dataManager.UltimaImgProcesada;
import persistencia.BrokerDbMapa;
import persistencia.Logueador;


/**
 *
 * @author Sebastian
 */
public class ControllerNavegacion {

    private static ControllerNavegacion unicaInstancia;

    private ControllerNavegacion() {
        inicializador();
    }

    public static ControllerNavegacion getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ControllerNavegacion();
        }
        return unicaInstancia;
    }

    private void inicializador() {
    }

    public void actualizaDatosEnGui() {
        PanelNavegacion.getInstance().actualizaRecorrido();
        PanelNavegacion.getInstance().inicializaTablaCategoriasPois(); 
        PanelNavegacion.getInstance().seteaBotonesMapa();
    }

    public void cargaPoisEnMapa(int idCampaniaElegida, ArrayList<Integer> categoriasSeleccionadas) {
        if (!BrokerDbMapa.getInstance().cargarPoisDeCamp(categoriasSeleccionadas)) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa de Navegación");
        }
    }

    public ArrayList<CategoriaPoi> getCatPOISDeUnaCampFromDB(int idCampaniaElegida) {
        return persistencia.BrokerCategoriasPOI.getInstance().getCatPOISDeUnaCampFromDB(idCampaniaElegida);
    }

    public Object getCantPOISDeUnaCampSegunCatPoi(int idCampaniaElegida, int idCategoria) {
        return persistencia.BrokerPOIs.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCampaniaElegida, idCategoria);
    }

    public void vaciaMapaNavegacion() {
        BrokerDbMapa.getInstance().vaciaMapaNavegacion();
    }

    public void graficarDatos(int retardo) {
        GraficaDatosNavegacion grafica = new GraficaDatosNavegacion();
        grafica.retardo = retardo;
        grafica.start();
    }

    public void cargaRecorridoEnMapaNavegacion() {
        if (!BrokerDbMapa.getInstance().cargarRecorridoDeCamp()) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa de Navegación");
        }
    }

    public void restauraBtnGraficarDatos() {
        PanelNavegacion.getInstance().restauraBtnGraficarDatos();
    }
    
    public boolean iniciaServerYabreBrowser(){
        boolean sePudo=false;
        try{
            if (!(BrokerDbMapa.getInstance().isUsarMapaNavegacion())) {
                modelo.gisModule.WebServer.getInstance().start();
                if (BrokerDbMapa.getInstance().disparaEjecucion()) {
                    BrokerDbMapa.getInstance().setUsarMapaNavegacion(true);
                    //do what you want to do before sleeping
                    //Thread.currentThread().sleep(2000);//sleep for 2000 ms --> ya se hace dentro del Broker
                    //do what you want to do after sleeptig
                    abreBrowserConMapaNavegacion();
                    sePudo=true;
                }
            }
        }
        catch(Exception e){
            //If this thread was intrrupted by nother thread
            persistencia.Logueador.getInstance().agregaAlLog(e.toString());
            }
        return sePudo;
    }
    
    public boolean detieneServerYcierraBrowser(){
        boolean sePudo=false;
        try {                    
            BrokerDbMapa.getInstance().setUsarMapaNavegacion(false);            
            if (modelo.gisModule.WebServer.getInstance().cerrarWebServer() && 
                modelo.gisModule.Browser.getInstance().cerrarBrowserPortable() &&
                persistencia.BrokerDbMapa.getInstance().detieneEjecucion()){
                sePudo=true;
            }
        }
        catch (Exception e)
            { System.out.println(e.toString());
              Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    public double getLatitudActual() {
        return Punto.getInstance().getLatitud();
    }

    public double getLongitudActual() {
        return Punto.getInstance().getLongitud();
    }
    private void abreBrowserConMapaNavegacion() {
        modelo.gisModule.Browser.getInstance().setUrlTemp(Browser.getInstance().getUrl());
        modelo.gisModule.Browser.getInstance().start();
    }

    public void actualizaGuiProcesamientoImgs() {
        //gui.ProcesaImgWin.getInstance().actualizaImgs(AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+UltimaImgProcesada.getInstance().getFileName()
        //        , "imgs\\"+Sistema.getInstance().getImgWithDetectedMarksFileName());
        gui.ProcesaImgWin.getInstance().actualizaImgs(
                AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+UltimaImgProcesada.getInstance().getFileName(),null);
        gui.ProcesaImgWin.getInstance().setIndexImgProcesada(6);
        //Logueador.getInstance().agregaAlLog("actualizaGuiProcesamientoImgs(): TODO BIEN");
    }

    public void errorGuiProcesamientoImgs() {
        gui.ProcesaImgWin.getInstance().actualizaImgs(AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+UltimaImgProcesada.getInstance().getFileName()
                , "/imgs/errorProcessing.jpg");
        //Logueador.getInstance().agregaAlLog("errorGuiProcesamientoImgs(): TODO MAL"); 
    }

    public void errorGuiNotFoundImgProcesada() {
        gui.ProcesaImgWin.getInstance().actualizaImgs(null,"/imgs/imgNotFound.jpg");
    }
    
    public void loadingGuiImgProcesada() {
        gui.ProcesaImgWin.getInstance().setLoadingOnImgProcesada("/imgs/loading.gif");
    }

    public void loadingGuiImgSinProcesar() {
        gui.ProcesaImgWin.getInstance().setLoadingOnImgSinProcesar("/imgs/loading.gif");
    }

    public void errorImgNoApta() {
        gui.ProcesaImgWin.getInstance().actualizaImgs(AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+UltimaImgProcesada.getInstance().getFileName()
                , "/imgs/imgNotSuitable.jpg");
    }

    public void errorGuiNotFoundImgSinProcesar() {
        gui.ProcesaImgWin.getInstance().actualizaImgs("/imgs/imgNotFound.jpg",null);
    }

    public void cargaResultadosEnTablaMarcas() {
        if (modelo.dataManager.UltimaImgProcesada.getInstance().getMarcas() != null && 
                modelo.dataManager.UltimaImgProcesada.getInstance().getMarcas().size()>0){
            PanelNavegacion.getInstance().inicializaTablaDC();
            for (modelo.dataManager.Marca marca : modelo.dataManager.UltimaImgProcesada.getInstance().getMarcas()){
                PanelNavegacion.getInstance().agregaUnaMarca(marca.getId(), marca.getFechaYhora(), 
                        marca.getLatitud(),marca.getLongitud(), marca.getProfundidad(),marca.getImgFileName());
            }
            PanelNavegacion.getInstance().setCantMarcasEncontradas(modelo.dataManager.UltimaImgProcesada.getInstance().getMarcas().size());
        }else{
            PanelNavegacion.getInstance().cargaMsgEnTablaMarcas("No se encontraron marcas para la imagen procesada...");
        }
    }

    public void loadingGuiTablaMarcas() {
        PanelNavegacion.getInstance().habilitaPanelCantMarcas(false); 
        PanelNavegacion.getInstance().cargaMsgEnTablaMarcas("Procesando imagen recibida...");
    }

    public void errorGuiTablaMarcas() {
        PanelNavegacion.getInstance().cargaMsgEnTablaMarcas("Hubo un error al procesar la ultima imagen y no se pudieron obtener las marcas...");
    }

    public boolean guardaPoiDeImgConMarcas() {//intentará guardar un POI de img con marcas
        boolean sePudo=false;
        try{
            //verifica si ya existe la categoria de imgsConMarcas
            if (!ControllerPois.getInstance().existeCategoria(
                    modelo.dataManager.AdministraCatPoi.getInstance().getIdCatImgsConMarcas())){
                //si no existe la crea
                ControllerPois.getInstance().agregaCategoriaPOI(
                    modelo.dataManager.AdministraCatPoi.getInstance().getIdCatImgsConMarcas(), 
                    modelo.dataManager.AdministraCatPoi.getInstance().getNombreCatImgsConMarcas(), 
                    modelo.dataManager.AdministraCatPoi.getInstance().getIconoFileNameCatImgsConMarcas());
            }
            //obtiene y formatea los datos que compondran el POI
            modelo.dataManager.Marca ultimaMarca = UltimaImgProcesada.getInstance().getMarcas().get(
                    UltimaImgProcesada.getInstance().getMarcas().size()-1);
            String descripcionPoi = "<datosImgProcesada>";
            descripcionPoi += "<cantMarcas nombre=\"Marcas encontradas: \" valor=\""+UltimaImgProcesada.getInstance().getMarcas().size()+"\" />";
            String rutaImg = System.getProperty("user.dir")+AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\";
            rutaImg += UltimaImgProcesada.getInstance().getFileName();
            descripcionPoi += "<imgFileName nombre=\"Imagen sin procesar: \" valor=\""+rutaImg+"\" />";
            descripcionPoi += "</datosImgProcesada>";
            ControllerPois.getInstance().agregaPOI(
                    modelo.dataManager.AdministraCatPoi.getInstance().getIdCatImgsConMarcas(), 
                    descripcionPoi, 
                    ultimaMarca.getLatitud(),
                    ultimaMarca.getLongitud(), 
                    null);
            sePudo=true;
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("guardaPoiDeImgConMarcas(): "+e.toString());
        }
        return sePudo;
    }

}
class GraficaDatosNavegacion implements Runnable {
    Thread thGraficar;
    public int retardo;
    public void run() {
        try {
            thGraficar.sleep(retardo);
            ControllerNavegacion.getInstance().vaciaMapaNavegacion();
            if (gui.PanelNavegacion.getInstance().getChkPoisTodos().isSelected() && 
                    gui.PanelNavegacion.getInstance().getCategoriasSeleccionadas().size()>0){
                ControllerNavegacion.getInstance().cargaPoisEnMapa(modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso().getId(),
                        gui.PanelNavegacion.getInstance().getCategoriasSeleccionadas());
            }
            if (gui.PanelNavegacion.getInstance().getChkRecorrido().isSelected()) {
                ControllerNavegacion.getInstance().cargaRecorridoEnMapaNavegacion();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        ControllerNavegacion.getInstance().restauraBtnGraficarDatos();
        thGraficar = null;
    }
    public void start() {
        if (thGraficar == null) {
            thGraficar = new Thread(this);
            thGraficar.setPriority(Thread.MIN_PRIORITY);
            thGraficar.start();
        }
    }
}