/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import persistencia.BrokerPuntoHistorico;
import persistencia.BrokerSondaSetHistorico;

/**
 *
 * @author Sebastian
 */
public class ControllerHistorico {
    static ControllerHistorico unicaInstancia;
    private BrokerPuntoHistorico brokerPuntoHistorico;
    private BrokerSondaSetHistorico brokerSondaSetHistorico;
            
    private ControllerHistorico(){
        inicializador();
    }
    
    public static ControllerHistorico getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerHistorico();
       return unicaInstancia;
    }

    private void inicializador() {
        brokerPuntoHistorico = BrokerPuntoHistorico.getInstance();
        brokerSondaSetHistorico = BrokerSondaSetHistorico.getInstance();
        configuraBrokerHistorico();
        
    }

    public void configuraBrokerHistorico() {
        brokerPuntoHistorico.setGuardaDatosGps(gui.PanelOpcCampanias.getInstance().getChkHistoricoGps().isSelected());
        brokerPuntoHistorico.setGuardaDatosSonda(gui.PanelOpcCampanias.getInstance().getChkHistoricoSonda().isSelected());
        brokerPuntoHistorico.setGuardaDatosPeces(gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().isSelected());
        brokerSondaSetHistorico.setGuardaDatosSondaSets(gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().isSelected());        
    }
   
    
}