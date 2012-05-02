/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gisModule;

/**
 *
 * @author Sebastian
 */
public class WebServer {
    static WebServer unicaInstancia;
    private String pathActual;
    private Process procesoWebServer;
        
    private WebServer(){
    }
    
    public static WebServer getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new WebServer();          
       }
       return unicaInstancia;
    }

    public boolean runWebServer(){
        boolean sePudo=false;            
        setPathActual(System.getProperty("user.dir"));        
        Runtime aplicacion = Runtime.getRuntime();         
        try{
            setProcesoWebServer(aplicacion.exec(getPathActual()+"\\SoftExterno\\server2go\\Server2Go.exe"));
            sePudo=true;
            return sePudo;
        }
        catch(Exception e){
            System.out.println(e);
            return sePudo;
        }        
    }

    public boolean stopWebServer(){
        boolean sePudo=false;            
        getProcesoWebServer().destroy();
                
        Runtime aplicacion = Runtime.getRuntime();         
        try{
            String rutaCompleta = getPathActual()+"\\SoftExterno\\server2go\\stop.bat";
            rutaCompleta = rutaCompleta.replace("\\", "/");
            Process procesoCierraWebServer = aplicacion.exec("cmd.exe /C "+rutaCompleta);
            //procesoCierraWebServer.destroy();
            sePudo=true;
            return sePudo;
        }
        catch(Exception e){
            System.out.println(e);
            return sePudo;
        }        
    }
    
    public boolean cerrarWebServer() {
        boolean sePudo=false;

        Runtime aplicacion = Runtime.getRuntime();         
        try{            
            Process procesoWebServer = aplicacion.exec("cmd.exe /C taskkill /F /T /IM Server2Go.exe");
            //procesoCierraWebServer.destroy();            
            sePudo=true;
            return sePudo;
        }
        catch(Exception e){
            System.out.println(e);
            return sePudo;
        }                        
    }
    
    /**
     * @return the pathActual
     */
    public String getPathActual() {
        return pathActual;
    }

    /**
     * @param pathActual the pathActual to set
     */
    public void setPathActual(String pathActual) {
        this.pathActual = pathActual;
    }

    /**
     * @return the procesoWebServer
     */
    public Process getProcesoWebServer() {
        return procesoWebServer;
    }

    /**
     * @param procesoWebServer the procesoWebServer to set
     */
    public void setProcesoWebServer(Process procesoWebServer) {
        this.procesoWebServer = procesoWebServer;
    }
}