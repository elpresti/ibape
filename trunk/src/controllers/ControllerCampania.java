/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import org.jdom.Element;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerConfig;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerCampania {
    static ControllerCampania unicaInstancia;
    private int estadoHistoricoDeCampEnCurso;
   
    private ControllerCampania(){
        inicializador();
    }
    
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
                gui.PanelSelector.getInstance().mostrarBtnPanelNavegacion(true);                
                gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
                sePudo=true;
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
        else
          { JOptionPane.showMessageDialog(null, "No se pudo eliminar del disco el historico de la campaña y/o la campaña de la base de datos"); }
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
        //tengo q marcar el estado de la campania en curso en la DB como finalizado
        
        boolean sePudo = false;
        if (modelo.dataManager.AdministraCampanias.getInstance().finalizarCampaniaEnCurso()){
            gui.PanelOpcCampanias.getInstance().cargaGrillaCampanias();
            gui.PanelSelector.getInstance().mostrarBtnPanelNavegacion(false);
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
            gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosGpsSonda").getBooleanValue());
            if (gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected()){
                gui.PanelOpcCampanias.getInstance().clickEnChkHistoricoGpsSonda();
            }
            gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosProcImg").getBooleanValue());
            gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().setSelected(parametros.getChild("PanelConfiguracion-Historico").getAttribute("GuardarDatosConfigSonda").getBooleanValue());
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    

    public void iniciarLogueoHistorico() {
        persistencia.BrokerHistoricoPunto.getInstance().setGuardaDatosGps(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        persistencia.BrokerHistoricoPunto.getInstance().setGuardaDatosSonda(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        persistencia.BrokerHistoricoPunto.getInstance().setGuardaDatosPeces(gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().isSelected());
        persistencia.BrokerHistoricoSondaSet.getInstance().setGuardaDatosSondaSets(gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().isSelected());     
        persistencia.BrokerHistoricoSondaSet.getInstance().disparaEjecucion(); 
    }

    public void detenerLogueoHistorico() {
        persistencia.BrokerHistoricoSondaSet.getInstance().detieneEjecucion();
    }

    public int getEstadoCampaniaEnCurso() {
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null){
            return AdministraCampanias.getInstance().getCampaniaEnCurso().getEstado();
        }
        else
            { return -1; }
    }

    public void setEstadoCampaniaEnCurso(int estadoCampania) {
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null){
            AdministraCampanias.getInstance().getCampaniaEnCurso().setEstado(estadoCampania);
        }
    }
    
    public boolean cargarCampaniaPausada(){
        boolean sePudo=false;
        // ---
        
        return sePudo;
    }    

    /**
     * @return the estadoHistoricoDeCampEnCurso
     */
    public int getEstadoHistoricoDeCampEnCurso() {
        return estadoHistoricoDeCampEnCurso;
    }

    /**
     * @param estadoHistoricoDeCampEnCurso the estadoHistoricoDeCampEnCurso to set
     */
    public void setEstadoHistoricoDeCampEnCurso(int estadoHistoricoCampCurso) {
        this.estadoHistoricoDeCampEnCurso = estadoHistoricoCampCurso;
        if (estadoHistoricoCampCurso==0){
            gui.PanelBarraDeEstado.getInstance().setHistoricoDesactivado();
        }
        else if (estadoHistoricoCampCurso==1){
            gui.PanelBarraDeEstado.getInstance().setHistoricoActivando();
        }
        else if (estadoHistoricoCampCurso == 2){
            gui.PanelBarraDeEstado.getInstance().setHistoricoActivado();
        }
        else if (estadoHistoricoCampCurso == 3){
            gui.PanelBarraDeEstado.getInstance().setHistoricoGuardando();
        }
    }

    private void inicializador() {
        setEstadoHistoricoDeCampEnCurso(0);
    }
    
}