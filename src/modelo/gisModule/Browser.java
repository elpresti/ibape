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
import persistencia.Logueador;
import vista.VentanaPpal;

/**
 *
 * @author Sebastian
 */
public class Browser implements Runnable{
    private String nombre="Mapa";
    private String url="http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"+persistencia.BrokerDbMapa.getInstance().getPuertoWebServer();
    private static Browser unicaInstancia;
    private String urlTemp;
    private Thread threadBrowser;
        
    private Browser() {}

    public boolean abrirPaginaEnPestania(String url) {
        boolean sePudo=false;
        BrowserLauncher browserLauncher;
        try {            
            browserLauncher = new BrowserLauncher();            
            browserLauncher.openURLinBrowser(url);
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

    public void run(){ 
        try {
            //threadBrowser.sleep(3000);        
            Thread.sleep(3000); 
            abrirPaginaEnPestania(getUrlTemp());
            controllers.ControllerHistorico.getInstance().restauraBtnIniciarMapa();
        } catch (Exception ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        threadBrowser = null; 
    }
    
    public void start(){
       if (threadBrowser == null) {
            threadBrowser = new Thread(this);
            threadBrowser.setPriority(Thread.MIN_PRIORITY);
            threadBrowser.start();
        }
    }

    /**
     * @return the urlTemp
     */
    public String getUrlTemp() {
        return urlTemp;
    }

    /**
     * @param urlTemp the urlTemp to set
     */
    public void setUrlTemp(String urlTemp) {
        this.urlTemp = urlTemp;
    }
}