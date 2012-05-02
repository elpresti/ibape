/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import persistencia.BrokerConfig;
import persistencia.BrokerDbMapa;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControladorPpal {
    static ControladorPpal unicaInstancia;    
    private ControladorDataCapture contDataCapture=ControladorDataCapture.getInstance();
    
    public void accionesAlSalir(){
        if (BrokerDbMapa.getInstance().isUsarMapaNavegacion()){
            gisModule.WebServer.getInstance().cerrarWebServer();        
            gisModule.Browser.getInstance().cerrarBrowserPortable();            
        }
        controllers.ControladorPpal.getInstance().guardarConfigPanelConfig();
    }    
    
    public static ControladorPpal getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControladorPpal();       
       return unicaInstancia;
    }

    public boolean guardarConfigPanelConfig(){
        boolean sePudo=true;
        gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Gps(
                String.valueOf(p.getChkEstadoGps().isSelected()), 
                p.getComboPuertoGps().getSelectedItem().toString(), 
                p.getComboVelocidadGps().getSelectedItem().toString(), 
                p.getComboBitsDatosGps().getSelectedItem().toString(), 
                p.getComboParidadGps().getSelectedItem().toString());
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Sonda(
                String.valueOf(p.getChkEstadoSonda().isSelected()), 
                p.getComboPuertoSonda().getSelectedItem().toString(),
                p.getComboVelocidadSonda().getSelectedItem().toString(), 
                p.getComboBitsDatosSonda().getSelectedItem().toString(), 
                p.getComboParidadSonda().getSelectedItem().toString());
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Lan(
                String.valueOf(p.getChkEstadoDetectaPeces().isSelected()), 
                p.getCampoRutaHistorico().getText());
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Unidades(
                p.getComboDistancia().getSelectedItem().toString(), 
                p.getComboVelocidad().getSelectedItem().toString(), 
                p.getComboTemp().getSelectedItem().toString());
        sePudo=sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Historico(
                String.valueOf(p.getChkHistoricoGps().isSelected()), 
                String.valueOf(p.getChkHistoricoSonda().isSelected()), 
                String.valueOf(p.getChkHistoricoPeces().isSelected()), 
                String.valueOf(p.getChkHistoricoSondaSets().isSelected()));
        sePudo=sePudo && BrokerConfig.getInstance().guardaConfiguracion();
        return sePudo;
    }

    public boolean iniciaServerYabreBrowser(){
        boolean sePudo=false;
        try{
            if (!(BrokerDbMapa.getInstance().isUsarMapaNavegacion())) {
                if (gisModule.WebServer.getInstance().runWebServer() &&
                        BrokerDbMapa.getInstance().disparaEjecucion()) {
                    BrokerDbMapa.getInstance().setUsarMapaNavegacion(true);
                    //do what you want to do before sleeping
                    Thread.currentThread().sleep(2000);//sleep for 2000 ms
                    //do what you want to do after sleeptig
                    gisModule.Browser.getInstance().abrirPaginaEnPestania();
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
            if (gisModule.WebServer.getInstance().cerrarWebServer() && 
                gisModule.Browser.getInstance().cerrarBrowserPortable() &&
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
            dataCapture.Gps gps = dataCapture.Gps.getInstance();
            //seteamos el puerto
            String puertoGps = String.valueOf(p.getComboPuertoGps().getSelectedItem());
            gps.setNroCom(Integer.valueOf(puertoGps.replace("COM","").trim()));
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
            dataCapture.Gps.getInstance().detieneLectura();
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
            dataCapture.Sonda sonda = dataCapture.Sonda.getInstance();
            //seteamos el puerto
            String puertoSonda = String.valueOf(p.getComboPuertoSonda().getSelectedItem());
            
            sonda.setNroCom(Integer.valueOf(puertoSonda.replace("COM","").trim()));
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
            dataCapture.Sonda.getInstance().detieneLectura();
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }                        
        return sePudo;
    }        
    
    
}