/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelNavegacion;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import modelo.gisModule.Browser;
import modelo.dataManager.Punto;
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
        Logueador.getInstance().agregaAlLog("actualizaGuiProcesamientoImgs(): TODO BIEN");
    }

    public void errorGuiProcesamientoImgs() {
        Logueador.getInstance().agregaAlLog("errorGuiProcesamientoImgs(): TODO MAL"); 
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