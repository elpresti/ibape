/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import org.jdom.Element;
import persistencia.BrokerConfig;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerCampania {
    static ControllerCampania unicaInstancia;
   
    public static ControllerCampania getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerCampania();
       return unicaInstancia;
    }

    public boolean nuevaCampania(String descripcion, String capitan, String barco){
        boolean sePudo=false;
        if ((descripcion.length()>1) && (capitan.length()>1) && (barco.length()>1)){
            modelo.dataManager.Campania campania = new modelo.dataManager.Campania();
            campania.setBarco(barco);
            campania.setFechaInicio(Calendar.getInstance().getTime());
            campania.setCapitan(capitan);
            campania.setEstado(0);
            campania.setDescripcion(descripcion);
            if (modelo.dataManager.AdministraCampanias.getInstance().agregarCampania(campania)) {
                sePudo=true;
                gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
            }
        }
        return sePudo;
    }
    
    public boolean borrarCampania(int idCampania){
        boolean sePudo=false;
        if (AdministraCampanias.getInstance().eliminarCampania(AdministraCampanias.getInstance().getCampania(idCampania))){
            gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
            sePudo=true;
        }        
        return sePudo;
    }

    public boolean modificarCampania(int id, String nombre, String capitan, String barco){
        boolean sePudo=false;
        if ((nombre.length()>1) && (capitan.length()>1) && (barco.length()>1)){
            //traigo la campania vieja y le cargo los valores nuevos
            modelo.dataManager.Campania campania = modelo.dataManager.AdministraCampanias.getInstance().getCampania(id);
            campania.setBarco(barco);            
            campania.setCapitan(capitan);
            campania.setDescripcion(nombre);
            if (modelo.dataManager.AdministraCampanias.getInstance().modificarCampania(campania)) {             
                gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
                sePudo=true;                
            }
        }
        return sePudo;
    }
    
    public boolean finalizaCampaniaEnCurso() {
        boolean sePudo = false;
        if (modelo.dataManager.AdministraCampanias.getInstance().finalizarCampaniaEnCurso()){
            gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
            sePudo=true;
        }        
        return sePudo;
    }

    public int getIdCampaniaEnCurso() {
        int salida = -2;
        if (modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso() != null){
            salida = modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso().getId();
        }
        return salida;
    }

    public boolean leeDocYseteaPanelConfig_Historico(){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document        
        try {
            Element raizConfiguracionIbape= BrokerConfig.getInstance().getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            gui.PanelOpcCampanias.getInstance().getChkHistoricoGps().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosGps").getBooleanValue());
            if (gui.PanelOpcCampanias.getInstance().getChkHistoricoGps().isSelected()){
                gui.PanelOpcCampanias.getInstance().clickEnChkHistoricoGps();
            }
            gui.PanelOpcCampanias.getInstance().getChkHistoricoSonda().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosSonda").getBooleanValue());
            gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosProcImg").getBooleanValue());
            gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosConfigSonda").getBooleanValue());
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    
    
}