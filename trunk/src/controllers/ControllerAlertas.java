/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.alertas.AdministraAlertas;
import modelo.alertas.Alerta;
import modelo.alertas.Condicion;
import modelo.alertas.Relacion;
import modelo.alertas.Variable;
import org.jdom.Element;
import persistencia.BrokerConfig;
import persistencia.Logueador;

/**
 *
 * @author Martin
 */
public class ControllerAlertas {
    static ControllerAlertas unicaInstancia;
    private ArrayList<Variable> variables;
    private ArrayList<Relacion> relaciones;
    private ArrayList<Condicion> condicionesAct=new ArrayList<Condicion>();
    private Condicion condicionAct;
    private Relacion relacionAct;
    private Variable variableAct;
    private Alerta alertaAct;
    private int idUltAlertaInsertada;
   
    public static ControllerAlertas getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerAlertas();
       return unicaInstancia;
    }

    /**
     * @return the variables
     */
    public ArrayList<Variable> getVariables() {
        return variables;
    }

    /**
     * @return the relaciones
     */
    public ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }

    /**
     * @param aRelaciones the relaciones to set
     */
    public void setRelaciones(ArrayList<Relacion> aRelaciones) {
        relaciones = aRelaciones;
    }

    /**
     * @param aVariables the variables to set
     */
    public boolean leeVariablesDB() {
        boolean sePudo=false;
        try{
        variables=persistencia.BrokerAlertas.getInstance().getVariablesFromDB();
        sePudo=true;
        }catch (Exception e){
            sePudo=false;
        }
        return sePudo;
    }

    public boolean nuevaAlerta(String nombre, String mensaje, String estado, ArrayList<modelo.alertas.Condicion> condiciones){
        boolean sePudo=false;
        boolean est=false;
        if ((nombre.length()>1) && (mensaje.length()>1) && (!condiciones.isEmpty())){
            modelo.alertas.Alerta alerta = new modelo.alertas.Alerta();
            alerta.setTitulo(nombre);
            alerta.setMensaje(mensaje);
            if (estado.matches("Activada")){
                est=true;
            }else{
                est=false;
            }
            alerta.setEstado(est);
            for (modelo.alertas.Condicion c:condiciones){
                alerta.agregarCondicion(c);
            }
           
            if (modelo.alertas.AdministraAlertas.getInstance().agregarAlerta(alerta)) {
                sePudo=true;
                gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
            }
        }
        return sePudo;
    }
    
    public boolean borrarAlerta(int idAlerta){
        boolean sePudo=false;
        if (AdministraAlertas.getInstance().eliminarAlerta(AdministraAlertas.getInstance().getAlerta(idAlerta))){
            gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
            sePudo=true;
        }        
        return sePudo;
    }

    public boolean modificarAlerta(int id, String nombre, String mensaje, String estado,ArrayList<Condicion> condiciones){
        boolean sePudo=false;
        boolean est;
        if ((nombre.length()>1) && (mensaje.length()>1)){
            //traigo la campania vieja y le cargo los valores nuevos
            modelo.alertas.Alerta alerta = modelo.alertas.AdministraAlertas.getInstance().getAlerta(id);
            alerta.setTitulo(nombre);            
            alerta.setMensaje(mensaje);             
            if (estado.matches("Activada")){
                est=true;
            }else{
                est=false;
            }
            alerta.setEstado(est); 
            alerta.setCondiciones(condiciones);
            if (modelo.alertas.AdministraAlertas.getInstance().modificarAlerta(alerta)) {             
                gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
                sePudo=true;                
            }
        }
        return sePudo;
    }
    
    public boolean leeDocYseteaPanelOpcAlertas(){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document        
        try {
            Element raizConfiguracionIbape= BrokerConfig.getInstance().getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            gui.PanelOpcAlertas.getInstance().getChkAlertas().setSelected(parametros.getChild("PanelAlertas").getAttribute("Estado").getBooleanValue());
            modelo.alertas.AdministraAlertas.setEstadoAlertas(parametros.getChild("PanelAlertas").getAttribute("Estado").getBooleanValue());
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    

    public void setEstadoAlertas(boolean estado) {
        if (estado){
            persistencia.BrokerConfig.getInstance().actualizaDatosPanelAlertas("true");
            gui.PanelOpcAlertas.getInstance().habilitaTablaAlertas();
            
        }else {
            persistencia.BrokerConfig.getInstance().actualizaDatosPanelAlertas("false");
            gui.PanelOpcAlertas.getInstance().deshabilitaTablaAlertas();
        }
        persistencia.BrokerConfig.getInstance().guardaConfiguracion();
        modelo.alertas.AdministraAlertas.setEstadoAlertas(estado);               
    }

    public boolean leeRelacionesDB(int index) {
        boolean sePudo=false;
        try{
        relaciones=persistencia.BrokerAlertas.getInstance().getRelacionesFromDB(index);
        sePudo=true;
        }catch (Exception e){
            sePudo=false;
        }
        return sePudo;
    }

    public void agregaCondicionAct(int idProvCondicion,int idRelacion,int idVariable,float valMin, float valMax,String descripcion){
        boolean encontro=false;
        int i=0;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (idProvCondicion==getCondicionesAct().get(i).getId()){
                encontro=true;
                getCondicionesAct().get(i).setIdRelacion(idRelacion);
                getCondicionesAct().get(i).setDescripcion(descripcion);
                getCondicionesAct().get(i).setValorMaximo(valMax);
                getCondicionesAct().get(i).setValorMinimo(valMin);
                getCondicionesAct().get(i).setIdVariable(idVariable);
            }
            i++;
        }
        if (!encontro){    
            
            Condicion condicion=new Condicion(idProvCondicion,idVariable,idRelacion,valMin,valMax,descripcion);
            getCondicionesAct().add(condicion);
        }
        
    }

    public void cambiaDatosActuales(int indexCondicion) {
        boolean encontro=false;
        int i=0;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (indexCondicion==getCondicionesAct().get(i).getId()){
                encontro=true;
                setCondicionAct(getCondicionesAct().get(i));
                setRelacionAct(getRelacionConId(condicionAct.getIdRelacion()));
                setVariableAct(getVariableConId(condicionAct.getIdVariable()));               
            }
            i++;
        }
    }

    /**
     * @return the relacionAct
     */
    public Relacion getRelacionAct() {
        return relacionAct;
    }
 
    public Variable getVariableConId(int index){
    int i=0;
    Variable variable=new Variable();
    boolean encontro=false;
        while ((!encontro)&&(i<=variables.size()-1)){
            if (variables.get(i).getId()==index){
                encontro=true;
                variable= variables.get(i);
            }
            i++;
        }
        return variable;
    }
    
    public Relacion getRelacionConId(int index){
    int i=0;
    Relacion relacion=new Relacion();
    boolean encontro=false;
        while ((!encontro)&&(i<=relaciones.size()-1)){
            if (relaciones.get(i).getId()==index){
                encontro=true;
                relacion= relaciones.get(i);
            }
            i++;
        }
        return relacion;
    }

    /**
     * @param relacionAct the relacionAct to set
     */
    public void setRelacionAct(Relacion relacionAct) {
        this.relacionAct = relacionAct;
    }

    /**
     * @return the variableAct
     */
    public Variable getVariableAct() {
        return variableAct;
    }

    /**
     * @param variableAct the variableAct to set
     */
    public void setVariableAct(Variable variableAct) {
        this.variableAct = variableAct;
    }
    


    /**
     * @return the alertaAct
     */
    public Alerta getAlertaAct() {
        return alertaAct;
    }

    /**
     * @param alertaAct the alertaAct to set
     */
    public void setAlertaAct(Alerta alertaAct) {
        this.alertaAct = alertaAct;
    }

    /**
     * @return the condicionAct
     */
    public Condicion getCondicionAct() {
        return condicionAct;
    }

    /**
     * @param condicionAct the condicionAct to set
     */
    public void setCondicionAct(Condicion condicionAct) {
        this.condicionAct = condicionAct;
    }

    public void borraCondicionListaAct (int idCondicion){
    int i=0;
    boolean encontro=false;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (getCondicionesAct().get(i).getId()==idCondicion){
                encontro=true;
                getCondicionesAct().remove(i);
            }
            i++;
        }

    }
    
    public void borrarCondicion(int idDeCondicionSeleccionada) {
        borraCondicionListaAct(idDeCondicionSeleccionada);
    }

    /**
     * @return the condicionesAct
     */
    public ArrayList<Condicion> getCondicionesAct() {
        return condicionesAct;
    }

    /**
     * @param condicionesAct the condicionesAct to set
     */
    public void setCondicionesAct(ArrayList<Condicion> condicionesAct) {
        this.condicionesAct = condicionesAct;
    }

    /**
     * @return the idUltAlertaInsertada
     */
    public int getIdUltAlertaInsertada() {
        return idUltAlertaInsertada;
    }

    /**
     * @param idUltAlertaInsertada the idUltAlertaInsertada to set
     */
    public void setIdUltAlertaInsertada(int idUltAlertaInsertada) {
        this.idUltAlertaInsertada = idUltAlertaInsertada;
    }
    
}
