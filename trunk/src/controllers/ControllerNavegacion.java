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
import persistencia.BrokerDbMapa;
import persistencia.Logueador;



/**
 *
 * @author Sebastian
 */
public class ControllerNavegacion {
    private static ControllerNavegacion unicaInstancia;
    
    private ControllerNavegacion(){
        inicializador();
    }
    
    public static ControllerNavegacion getInstance() {
        if (unicaInstancia == null){
            unicaInstancia = new ControllerNavegacion();
        }
        return unicaInstancia;
    }

    private void inicializador() {

    }

    public void actualizaDatosEnGui() {
        PanelNavegacion.getInstance().inicializaTablaCategoriasPois(); 
        PanelNavegacion.getInstance().seteaBotonesMapa();
    }
    
    public void cargaPoisEnMapa(int idCampaniaElegida, ArrayList<Integer> categoriasSeleccionadas) {
        if (!BrokerDbMapa.getInstance().cargarPoisDeCamp(categoriasSeleccionadas)){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa de Navegación");
        }
    }

    public ArrayList<CategoriaPoi> getCatPOISDeUnaCampFromDB(int idCampaniaElegida) {
        return  persistencia.BrokerCategoriasPOI.getInstance().getCatPOISDeUnaCampFromDB(idCampaniaElegida);
    }

    public Object getCantPOISDeUnaCampSegunCatPoi(int idCampaniaElegida, int idCategoria) {
        return persistencia.BrokerPOIs.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCampaniaElegida, idCategoria);
    }

    public void vaciaMapaNavegacion() {
        BrokerDbMapa.getInstance().vaciaMapaNavegacion();
    }

    public void graficarDatos(int retardo) {
        GraficaDatosHistoricos grafica = new GraficaDatosHistoricos();
        grafica.retardo=retardo;
        grafica.start();
    }

    public void cargaRecorridoEnMapaNavegacion() {
        if (!BrokerDbMapa.getInstance().cargarRecorridoDeCamp()){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa de Navegación");
        }
    }

    public void restauraBtnGraficarDatos(){
        PanelNavegacion.getInstance().restauraBtnGraficarDatos();
    }

}
class GraficaDatosNavegacion implements Runnable{
    Thread thGraficar;
    public int retardo;
    public void run() { 
        try{
            thGraficar.sleep(retardo);
            ControllerNavegacion.getInstance().vaciaMapaNavegacion();
            if (gui.PanelNavegacion.getInstance().getCategoriasSeleccionadas().size()>0){
                ControllerNavegacion.getInstance().cargaPoisEnMapa(modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso().getId(),
                        gui.PanelNavegacion.getInstance().getCategoriasSeleccionadas());
            }
            if (gui.PanelNavegacion.getInstance().getChkRecorrido().isSelected()){
                ControllerNavegacion.getInstance().cargaRecorridoEnMapaNavegacion();
            }
        }
        catch(Exception e){
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