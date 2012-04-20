/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Sebastian
 */
public class ControladorPpal {
    static ControladorPpal unicaInstancia;    
    
    
    public void accionesAlSalir(){
        gisModule.WebServer.getInstance().cerrarWebServer();        
        gisModule.Browser.getInstance().cerrarBrowserPortable();
    }    
    
    public static ControladorPpal getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControladorPpal();       
       return unicaInstancia;
    }
}