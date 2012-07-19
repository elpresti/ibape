/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.gisModule;

import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class WebServer implements Runnable {

    static WebServer unicaInstancia;
    private String pathActual;
    private boolean webServerEncendido = false;
    private Process procesoWebServer;
    private Thread threadWebServer;

    private WebServer() {
    }

    public static WebServer getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new WebServer();
        }
        return unicaInstancia;
    }

    public void run() {
        runWebServer();
        threadWebServer = null;
    }

    public void start() {
        if (threadWebServer == null) {
            threadWebServer = new Thread(this);
            threadWebServer.setPriority(Thread.MIN_PRIORITY);
            threadWebServer.start();
        }
    }

    public boolean runWebServer() {
        boolean sePudo = false;
        setPathActual(System.getProperty("user.dir"));
        Runtime aplicacion = Runtime.getRuntime();
        try {
            setProcesoWebServer(aplicacion.exec(getPathActual() + "\\SoftExterno\\server2go\\Server2Go.exe"));
            setWebServerEncendido(true);
            sePudo = true;
            return sePudo;
        } catch (Exception e) {
            System.out.println(e);
            return sePudo;
        }
    }

    public boolean stopWebServer() {
        boolean sePudo = false;
        getProcesoWebServer().destroy();

        Runtime aplicacion = Runtime.getRuntime();
        try {
            String rutaCompleta = getPathActual() + "\\SoftExterno\\server2go\\stop.bat";
            rutaCompleta = rutaCompleta.replace("\\", "/");
            Process procesoCierraWebServer = aplicacion.exec("cmd.exe /C " + rutaCompleta);
            //procesoCierraWebServer.destroy();
            setWebServerEncendido(false);
            sePudo = true;
            return sePudo;
        } catch (Exception e) {
            System.out.println(e);
            return sePudo;
        }
    }

    public boolean cerrarWebServer() {
        boolean sePudo = false;

        Runtime aplicacion = Runtime.getRuntime();
        try {
            Process procesoWebServer = aplicacion.exec("cmd.exe /C taskkill /F /T /IM Server2Go.exe");
            //procesoCierraWebServer.destroy();
            setWebServerEncendido(false);
            threadWebServer=null;
            sePudo = true;
            return sePudo;
        } catch (Exception e) {
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

    /**
     * @return the webServerEncendido
     */
    public boolean isWebServerEncendido() {
        return webServerEncendido;
    }

    /**
     * @param webServerEncendido the webServerEncendido to set
     */
    public void setWebServerEncendido(boolean webServerEncendido) {
        this.webServerEncendido = webServerEncendido;
    }
}