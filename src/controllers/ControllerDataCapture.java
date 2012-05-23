/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelBarraDeEstado;
import java.util.ArrayList;
import java.util.Observable;
import modelo.dataCapture.Gps;
import modelo.dataCapture.PuertosSerieDelSO;
import modelo.dataCapture.Sonda;



/**
 *
 * @author Sebastian
 */
public class ControllerDataCapture implements java.util.Observer{
    static ControllerDataCapture unicaInstancia; 
    private Gps gps = Gps.getInstance();
    private Sonda sonda = Sonda.getInstance();
    private PuertosSerieDelSO puertosSerie = PuertosSerieDelSO.getInstance();    

    public static ControllerDataCapture getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerDataCapture();       
       return unicaInstancia;
    }

    private ControllerDataCapture(){
        gps.addObserver(this);
        sonda.addObserver(this);
        puertosSerie.addObserver(this);
    }
    
    public void obtienePuertosComExistentes(){        
        puertosSerie.start();
    }
    
    
    
    public void setEstadoGps(int nroEstado){
        if (nroEstado==0){
            gui.PanelOpcConfiguracion.getInstance().setGpsDesconectado();
            gui.PanelBarraDeEstado.getInstance().setGpsDesconectado();
            gui.PanelOpcConfiguracion.getInstance().habilitaBtnConectaGps();
        }
        else if (nroEstado==1){
            gui.PanelOpcConfiguracion.getInstance().setGpsConectando();
            gui.PanelBarraDeEstado.getInstance().setGpsConectando();
        }
        else if (nroEstado == 2){
            gui.PanelOpcConfiguracion.getInstance().setGpsConectado();
            gui.PanelBarraDeEstado.getInstance().setGpsConectado();
        }                    
    }

    public void setEstadoSonda(int nroEstado){
        if (nroEstado==0){
            gui.PanelOpcConfiguracion.getInstance().setSondaDesconectado();
            gui.PanelBarraDeEstado.getInstance().setSondaDesconectado();
            gui.PanelOpcConfiguracion.getInstance().habilitaBtnConectaSonda();
        }
        else if (nroEstado==1){
            gui.PanelOpcConfiguracion.getInstance().setSondaConectando();
            gui.PanelBarraDeEstado.getInstance().setSondaConectando();
        }
        else if (nroEstado == 2){
            gui.PanelOpcConfiguracion.getInstance().setSondaConectado();
            gui.PanelBarraDeEstado.getInstance().setSondaConectado();
        }                
    }

    public void setEstadoLan(int nroEstado){
        
        
    }
    

    private void errorAlLeerPuertosSerieDelSO() {
        ArrayList<String> mensaje = new ArrayList<String> ();
        mensaje.add("Error al obtener puertos");
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMgps(mensaje);
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMsonda(mensaje);
    }

    private void setEstadoLeyendoPuertosSerieSO() {
        ArrayList<String> mensaje = new ArrayList<String> ();
        mensaje.add("Obteniendo listado de puertos del Sistema Operativo, puede llevar hasta 2 minutos...");
        gui.PanelOpcConfiguracion.getInstance().getChkEstadoGps().setEnabled(false);
        gui.PanelOpcConfiguracion.getInstance().setPanelConfigGps(false);
        gui.PanelOpcConfiguracion.getInstance().setPanelConfigSonda(false);        
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMgps(mensaje);
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMsonda(mensaje);
    }

    private void setCombosPuertosSerieDelSO(ArrayList<String> puertosSerie) {
        gui.PanelOpcConfiguracion.getInstance().btnEscanearPuertosPresionado(false);
        gui.PanelOpcConfiguracion.getInstance().getChkEstadoGps().setEnabled(true);
        gui.PanelOpcConfiguracion.getInstance().setPanelConfigGps(true);
        gui.PanelOpcConfiguracion.getInstance().setPanelConfigSonda(true);
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMgps(puertosSerie);
        gui.PanelOpcConfiguracion.getInstance().setContenidoComboCOMsonda(puertosSerie);
    }    
    
    
    
    public void update(Observable o, Object arg) {

      if (o == gps){
          setEstadoGps(gps.getEstadoConexion());
      }
      else
          if (o == sonda){
              setEstadoSonda(sonda.getEstadoConexion());
          }
          else 
              if (o == puertosSerie){
                  if (puertosSerie.isErrorLeyendo())
                    { errorAlLeerPuertosSerieDelSO(); }
                  else
                    { if (puertosSerie.isLeyendoPuertos()) {
                          setEstadoLeyendoPuertosSerieSO();
                        }
                      else
                        { setCombosPuertosSerieDelSO(puertosSerie.getPuertosSerie()); }                        
                    }                    
              }
    }

}
