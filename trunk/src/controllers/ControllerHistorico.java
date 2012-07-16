/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerDbMapa;
import persistencia.BrokerHistoricoPunto;
import persistencia.BrokerHistoricoSondaSet;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerHistorico {
    static ControllerHistorico unicaInstancia;
    private BrokerHistoricoPunto brokerHistoricoPunto;
    private BrokerHistoricoSondaSet brokerHistoricoSondaSet;
            
    private ControllerHistorico(){
        inicializador();
    }
    
    public static ControllerHistorico getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerHistorico();
       return unicaInstancia;
    }

    private void inicializador() {
        brokerHistoricoPunto = BrokerHistoricoPunto.getInstance();
        brokerHistoricoSondaSet = BrokerHistoricoSondaSet.getInstance();
    }

    public void configuraBrokerHistorico() {
        brokerHistoricoPunto.setGuardaDatosGps(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosSonda(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosPeces(gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().isSelected());
        brokerHistoricoSondaSet.setGuardaDatosSondaSets(gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().isSelected());
    }

    public void cargaGrillaCampanias() {
        gui.PanelHistorico.getInstance().vaciaTabla();
        int cantConHistorico=0;
        ArrayList<modelo.dataManager.Campania> campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();
        if ((campanias == null) || (campanias.isEmpty()) ) {
            modelo.dataManager.AdministraCampanias.getInstance().leerCampaniasDeLaDB();
            campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();            
        }
        if ((!(campanias == null)) && (campanias.size() > 0)) {
            // while (), pongo cada objeto Campania en la grilla de campanias                    
            int i = 0;
            boolean tieneHistorico;            
            while (i < campanias.size()) {
                tieneHistorico = (campanias.get(i).getFolderHistorico() != null && campanias.get(i).getFolderHistorico().length() > 0);
                if (tieneHistorico){
                    gui.PanelHistorico.getInstance().agregaUnaFilaCampania(
                            campanias.get(i).getId(),
                            campanias.get(i).getDescripcion(),
                            campanias.get(i).getBarco(),
                            campanias.get(i).getCapitan(),
                            campanias.get(i).getEstado(),
                            campanias.get(i).getFechaInicio(),
                            campanias.get(i).getFechaFin(),
                            campanias.get(i).getCantTotalCajones()
                            );
                    cantConHistorico++;
                }
                i++;
            }
        }
        if (cantConHistorico == 0) {
            gui.PanelHistorico.getInstance().agregaUnaFilaCampania(-1, "No hay campañas con Historico",
                    null, null, 0, null, null, 0);
            gui.PanelHistorico.getInstance().habilitaPanelTablaCampanias(false);
        } else {
            gui.PanelHistorico.getInstance().habilitaPanelTablaCampanias(true);
        }
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            gui.PanelHistorico.getInstance().marcaCampaniaEnCurso();
        }
    }

    public int getCantPuntosHistoricos(int idCamp){
        return BrokerHistoricoPunto.getInstance().cuantosPuntosTiene(idCamp);
    }        

    public boolean iniciaServerYabreBrowser() {
        boolean sePudo=false;
        try{
            if (ControllerPpal.getInstance().abrirWebServer()){
                if (BrokerDbMapa.getInstance().disparaEjecucion()){
                    if (persistencia.BrokerDbMapaHistorico.getInstance().disparaEjecucion()){                        
                        //do what you want to do after sleeptig
                        modelo.gisModule.Browser.getInstance().abrirPaginaEnPestania(modelo.gisModule.Browser.getInstance().getUrl()+"/historico.php");
                        sePudo=true;
                    }
                }
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    
}