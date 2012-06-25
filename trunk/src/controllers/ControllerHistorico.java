/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import persistencia.BrokerHistoricoPunto;
import persistencia.BrokerHistoricoSondaSet;

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
   
    
}