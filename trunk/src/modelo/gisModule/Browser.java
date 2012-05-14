/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.gisModule;
//import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import edu.stanford.ejalbert.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import vista.VentanaPpal;

/**
 *
 * @author Sebastian
 */
public class Browser {
    private String nombre="Mapa";
    private String url="http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"+persistencia.BrokerDbMapa.getInstance().getPuertoWebServer();
    private static Browser unicaInstancia;
        
    private Browser() {}

    public boolean abrirPaginaEnPestania() {
        boolean sePudo=false;
        BrowserLauncher browserLauncher;
        try {            
            browserLauncher = new BrowserLauncher();            
            browserLauncher.openURLinBrowser(getUrl());
            sePudo=true;
        } catch (BrowserLaunchingInitializingException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperatingSystemException ex) {
            Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sePudo;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    public boolean abrirBrowserPortable() {
        boolean sePudo=false;
        Runtime aplicacion = Runtime.getRuntime();         
        try{
            Process browserPortable = aplicacion.exec(WebServer.getInstance().getPathActual()+"\\SoftExterno\\ChromePortable\\GoogleChromePortable.exe");            
            sePudo=true;
            return sePudo;
        }
        catch(Exception e){
            System.out.println(e);
            return sePudo;
        }
    }

    public boolean cerrarBrowserPortable() {
        boolean sePudo=false;

        Runtime aplicacion = Runtime.getRuntime();         
        try{            
            Process procesoBrowserPortable = aplicacion.exec("cmd.exe /C taskkill /F /T /IM GoogleChromePortable.exe");
            //procesoCierraWebServer.destroy();            
            sePudo=true;
            return sePudo;
        }
        catch(Exception e){
            System.out.println(e);
            return sePudo;
        }                        
    }

    public static Browser getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new Browser();          
       }
       return unicaInstancia;
    }

}