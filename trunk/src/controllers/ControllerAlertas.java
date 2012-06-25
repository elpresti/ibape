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
import modelo.alertas.Condicion;
import org.jdom.Element;
import persistencia.BrokerConfig;
import persistencia.Logueador;

/**
 *
 * @author Martin
 */
public class ControllerAlertas {
    static ControllerAlertas unicaInstancia;
   
    public static ControllerAlertas getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerAlertas();
       return unicaInstancia;
    }

    public boolean nuevaAlerta(String nombre, String mensaje, String estado, List<modelo.alertas.Condicion> condiciones){
        boolean sePudo=false;
        boolean est=false;
        if ((nombre.length()>1) && (mensaje.length()>1) && (!condiciones.isEmpty())){
            modelo.alertas.Alerta alerta = new modelo.alertas.Alerta();
            alerta.setTitulo(nombre);
            alerta.setMensaje(mensaje);
            if (estado.matches("ACTIVADA")){
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

    public boolean modificarAlerta(int id, String nombre, String mensaje, boolean estado,List<Condicion> condiciones){
        boolean sePudo=false;
        if ((nombre.length()>1) && (mensaje.length()>1)){
            //traigo la campania vieja y le cargo los valores nuevos
            modelo.alertas.Alerta alerta = modelo.alertas.AdministraAlertas.getInstance().getAlerta(id);
            alerta.setTitulo(nombre);            
            alerta.setMensaje(mensaje);  
            alerta.setEstado(estado); 
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
    
}
