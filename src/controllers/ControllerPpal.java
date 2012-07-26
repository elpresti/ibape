/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.gisModule.Browser;
import persistencia.BrokerConfig;
import persistencia.BrokerDbMapa;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerPpal {
    static ControllerPpal unicaInstancia;    
    private ControllerConfig contConfig=ControllerConfig.getInstance();
    
    public void accionesAlSalir(){
        if (BrokerDbMapa.getInstance().isUsarMapaNavegacion() || modelo.gisModule.WebServer.getInstance().isWebServerEncendido()){
            modelo.gisModule.WebServer.getInstance().cerrarWebServer();        
            modelo.gisModule.Browser.getInstance().cerrarBrowserPortable();            
        } 
        controllers.ControllerPpal.getInstance().guardarConfigPanelConfig(); 
        if ( (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) && 
             (AdministraCampanias.getInstance().getCampaniaEnCurso().getEstado() == 1) ){
            controllers.ControllerCampania.getInstance().setEstadoCampaniaEnCurso(2); //si hay alguna campania en curso, la pauso
        }
    }
    
    public void accionesAlIniciar(){
        controllers.ControllerConfig.getInstance().inicializaConexiones();        
        persistencia.BrokerCampania.getInstance();
        controllers.ControllerHistorico.getInstance();
        controllers.ControllerCampania.getInstance();
        if (AdministraCampanias.getInstance().cargaUltimaCampaniaPausada()){ 
            gui.PanelOpcCampanias.getInstance().setGuiCampaniaIniciada();
            gui.PanelOpcCampanias.getInstance().setGuiCampaniaPausada();
            gui.PanelOpcCampanias.getInstance().cargarDatosDeCampaniaEnCurso();
            gui.PanelOpcCampanias.getInstance().marcaCampaniaEnCurso();
        }        
    }
    
    public static ControllerPpal getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerPpal();       
       return unicaInstancia;
    }

    public boolean guardarConfigPanelConfig(){
        boolean sePudo=true;
        gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();
        gui.PanelOpcCampanias c = gui.PanelOpcCampanias.getInstance();
        String comboPuertoGPS="";
        if (p.getComboPuertoGps().getSelectedItem() != null){
            comboPuertoGPS = p.getComboPuertoGps().getSelectedItem().toString();
        }
        String comboPuertoSONDA="";
        if (p.getComboPuertoSonda().getSelectedItem() != null){
            comboPuertoSONDA = p.getComboPuertoSonda().getSelectedItem().toString();
        }        
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Gps(
                String.valueOf(p.getChkEstadoGps().isSelected()), 
                comboPuertoGPS, 
                p.getComboVelocidadGps().getSelectedItem().toString(), 
                p.getComboBitsDatosGps().getSelectedItem().toString(), 
                p.getComboParidadGps().getSelectedItem().toString(),
                String.valueOf(p.getChkAutoConectaGps().isSelected()));
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Sonda(
                String.valueOf(p.getChkEstadoSonda().isSelected()), 
                comboPuertoSONDA,
                p.getComboVelocidadSonda().getSelectedItem().toString(), 
                p.getComboBitsDatosSonda().getSelectedItem().toString(), 
                p.getComboParidadSonda().getSelectedItem().toString(),
                String.valueOf(p.getChkAutoConectaSonda().isSelected()));
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Lan(
                String.valueOf(p.getChkEstadoLan().isSelected()), 
                p.getCampoRutaHistorico().getText(),
                String.valueOf(p.getChkAutoConectaLan().isSelected()));
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Unidades(
                p.getComboDistancia().getSelectedItem().toString(), 
                p.getComboVelocidad().getSelectedItem().toString(), 
                p.getComboTemp().getSelectedItem().toString());
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Historico(
                String.valueOf(c.getChkHistoricoGpsSonda().isSelected()), 
                String.valueOf(c.getChkHistoricoPeces().isSelected()), 
                String.valueOf(c.getChkHistoricoSondaSets().isSelected()));
        sePudo=sePudo && BrokerConfig.getInstance().guardaConfiguracion();
        return sePudo;
    }

    public boolean iniciaServerYabreBrowser(){
        boolean sePudo=false;
        try{
            if (!(BrokerDbMapa.getInstance().isUsarMapaNavegacion())) {
                modelo.gisModule.WebServer.getInstance().start();
                if (BrokerDbMapa.getInstance().disparaEjecucion()) {
                    BrokerDbMapa.getInstance().setUsarMapaNavegacion(true);
                    //do what you want to do before sleeping
                    //Thread.currentThread().sleep(2000);//sleep for 2000 ms --> ya se hace dentro del Broker
                    //do what you want to do after sleeptig
                    modelo.gisModule.Browser.getInstance().setUrlTemp(Browser.getInstance().getUrl());
                    modelo.gisModule.Browser.getInstance().start();
                    sePudo=true;
                }
            }
        }
        catch(Exception e){
            //If this thread was intrrupted by nother thread
            persistencia.Logueador.getInstance().agregaAlLog(e.toString());
            }
        return sePudo;
    }
    
    public boolean detieneServerYcierraBrowser(){
        boolean sePudo=false;
        try {                    
            BrokerDbMapa.getInstance().setUsarMapaNavegacion(false);            
            if (modelo.gisModule.WebServer.getInstance().cerrarWebServer() && 
                modelo.gisModule.Browser.getInstance().cerrarBrowserPortable() &&
                persistencia.BrokerDbMapa.getInstance().detieneEjecucion()){
                sePudo=true;
            }
        }
        catch (Exception e)
            { System.out.println(e.toString());
              Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean conectaYleeDelGps(){
        boolean sePudo=false;       
        try {
            gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();        
            modelo.dataCapture.Gps gps = modelo.dataCapture.Gps.getInstance();
            //seteamos el puerto
            String puertoGps = String.valueOf(p.getComboPuertoGps().getSelectedItem());
            String nroCom;
            if (!(puertoGps.indexOf(":")== -1)){
                nroCom = puertoGps.substring(0, puertoGps.indexOf(":"));
            }            
            else { nroCom = puertoGps; }
            nroCom = nroCom.replace("COM","").trim();        
            gps.setNroCom(Integer.valueOf(nroCom));
            
            //seteamos la velocidad del puerto
            Integer velocidadGps = Integer.valueOf(String.valueOf(p.getComboVelocidadGps().getSelectedItem()));
            gps.setVelocidadCom(velocidadGps);
            //seteamos los bits de datos
            Integer bitsDatosGps = Integer.valueOf(String.valueOf(p.getComboBitsDatosGps().getSelectedItem()));
            gps.setBitsDeDatosCom(bitsDatosGps);
            //seteamos los bits de paridad
            if (String.valueOf(p.getComboParidadGps().getSelectedItem()).equals("Impar"))
                { gps.setBitsParidadCom(1); }
            else
                if (String.valueOf(p.getComboParidadGps().getSelectedItem()).equals("Par"))
                    { gps.setBitsParidadCom(2); }
                else
                    { gps.setBitsParidadCom(0); }    
            //disparamos la lectura del puerto
            gps.disparaLectura();
            sePudo=true;
        }
        catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public boolean detenerLecturaGps(){
        boolean sePudo = false;
        try{
            modelo.dataCapture.Gps.getInstance().detieneLectura();
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }                        
        return sePudo;
    }
    
    public boolean conectaYleeDeLaSonda(){
        boolean sePudo=false;       
        try {
            gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();        
            modelo.dataCapture.Sonda sonda = modelo.dataCapture.Sonda.getInstance();
            //seteamos el puerto
            String puertoSonda = String.valueOf(p.getComboPuertoSonda().getSelectedItem());
            String nroCom;
            if (!(puertoSonda.indexOf(":")== -1)){
                nroCom = puertoSonda.substring(0, puertoSonda.indexOf(":"));
            }            
            else { nroCom = puertoSonda; }
            nroCom = nroCom.replace("COM","").trim();        
            sonda.setNroCom(Integer.valueOf(nroCom));
            //seteamos la velocidad del puerto
            Integer velocidadSonda = Integer.valueOf(String.valueOf(p.getComboVelocidadSonda().getSelectedItem()));
            sonda.setVelocidadCom(velocidadSonda);
            //seteamos los bits de datos
            Integer bitsDatosSonda = Integer.valueOf(String.valueOf(p.getComboBitsDatosSonda().getSelectedItem()));
            sonda.setBitsDeDatosCom(bitsDatosSonda);
            //seteamos los bits de paridad
            if (String.valueOf(p.getComboParidadSonda().getSelectedItem()).equals("Impar"))
                { sonda.setBitsParidadCom(1); }
            else
                if (String.valueOf(p.getComboParidadSonda().getSelectedItem()).equals("Par"))
                    { sonda.setBitsParidadCom(2); }
                else
                    { sonda.setBitsParidadCom(0); }    
            //disparamos la lectura del puerto
            sonda.disparaLectura();
            sePudo=true;
        }
        catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public boolean detenerLecturaSonda(){
        boolean sePudo = false;
        try{
            modelo.dataCapture.Sonda.getInstance().detieneLectura();
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }                        
        return sePudo;
    }

    public boolean abrirWebServer(){
        boolean sePudo=false;
        try{
            if (!modelo.gisModule.WebServer.getInstance().isWebServerEncendido()){
                modelo.gisModule.WebServer.getInstance().start();
                sePudo=true;
            }
            else{
                sePudo=true;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }    

    public void msgReiniciarAplicacion() {
        JOptionPane.showMessageDialog(null, "Fue necesario inicializar IBAPE, ahora debe reiniciar la aplicación");
    }

}