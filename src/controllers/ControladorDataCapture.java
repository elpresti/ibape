/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Observable;
import dataCapture.Gps;
import dataCapture.Sonda;



/**
 *
 * @author Sebastian
 */
public class ControladorDataCapture implements java.util.Observer{
    static ControladorDataCapture unicaInstancia; 
    private Gps gps = Gps.getInstance();
    private Sonda sonda = Sonda.getInstance();

    public static ControladorDataCapture getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControladorDataCapture();       
       return unicaInstancia;
    }

    private ControladorDataCapture(){
        gps.addObserver(this);
        sonda.addObserver(this);
    }
    
    public void setEstadoGps(int nroEstado){
        if (nroEstado==0){
            gui.PanelOpcConfiguracion.getInstance().setGpsDesconectado();
        }
        else if (nroEstado==1){
            gui.PanelOpcConfiguracion.getInstance().setGpsConectando();
        }
        else if (nroEstado == 2){
            gui.PanelOpcConfiguracion.getInstance().setGpsConectado();
        }                    
    }

    public void setEstadoSonda(int nroEstado){
        if (nroEstado==0){
            gui.PanelOpcConfiguracion.getInstance().setSondaDesconectado();
        }
        else if (nroEstado==1){
            gui.PanelOpcConfiguracion.getInstance().setSondaConectando();
        }
        else if (nroEstado == 2){
            gui.PanelOpcConfiguracion.getInstance().setSondaConectado();
        }                
    }

    public void setEstadoLan(int nroEstado){
        
        
    }
    
    
    
    public void update(Observable o, Object arg) {

      if (o == gps){
          setEstadoGps(gps.getEstadoConexion());
      }
      else
          if (o == sonda){
              setEstadoSonda(sonda.getEstadoConexion());
          }
    }

}
