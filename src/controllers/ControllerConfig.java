/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Observable;
import modelo.dataCapture.Gps;
import modelo.dataCapture.LanSonda;
import modelo.dataCapture.PuertosSerieDelSO;
import modelo.dataCapture.Sonda;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerConfig  implements java.util.Observer {
    static ControllerConfig unicaInstancia; 
    private Gps gps = Gps.getInstance();
    private Sonda sonda = Sonda.getInstance();
    private PuertosSerieDelSO puertosSerie = PuertosSerieDelSO.getInstance();
    private LanSonda lanSonda = LanSonda.getInstance();

    public static ControllerConfig getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerConfig();
       return unicaInstancia;
    }

    private ControllerConfig(){
        gps.addObserver(this);
        sonda.addObserver(this);
        puertosSerie.addObserver(this);
        lanSonda.addObserver(this);
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
        if (nroEstado==0){
            gui.PanelOpcConfiguracion.getInstance().setLanDesconectado();
            gui.PanelBarraDeEstado.getInstance().setLanDesconectado();
            gui.PanelOpcConfiguracion.getInstance().controlaBtnLan(true);
        } 
        else if (nroEstado==1){
            gui.PanelOpcConfiguracion.getInstance().setLanConectando();
            gui.PanelBarraDeEstado.getInstance().setLanConectando();
            gui.PanelOpcConfiguracion.getInstance().controlaBtnLan(false);
        }
        else if (nroEstado == 2){
            gui.PanelOpcConfiguracion.getInstance().setLanConectado();
            gui.PanelBarraDeEstado.getInstance().setLanConectado();
        }
        else if (nroEstado == 3){
            gui.PanelBarraDeEstado.getInstance().setLanLeyendo();
        }
        else if (nroEstado == 4){
            gui.PanelBarraDeEstado.getInstance().mostrarMensaje("No se pudo conectar al host remoto. Revise la ruta de conexión especificada",1);
            gui.PanelOpcConfiguracion.getInstance().mostrarMsgSinConexionAhost();
        }
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
              if (o == lanSonda){
                  setEstadoLan(lanSonda.getEstadoConexion());
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

    public void setParametrosLan() {
        String ruta=gui.PanelOpcConfiguracion.getInstance().getCampoRutaHistorico().getText();
        modelo.dataCapture.LanSonda.getInstance().setCarpetaHistoricoRemoto(ruta);
        if (modelo.dataCapture.LanSonda.getInstance().getCarpetaHistoricoLocal() == null){
            modelo.dataCapture.LanSonda.getInstance().setCarpetaHistoricoLocal("Historico");
        }        
    }

    public boolean detenerLecturaLan() {
        boolean sePudo = false;
        try{
            if (lanSonda.detieneLectura()){
                sePudo=true;  
            }            
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean disparaLecturaLan() {
        boolean sePudo = false;
        try{
            lanSonda.disparaLectura();
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

}