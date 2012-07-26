/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import gnu.io.CommPortIdentifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class PuertosSerieDelSO extends java.util.Observable implements Runnable{
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
    Thread ps;
    
    static PuertosSerieDelSO unicaInstancia;
    
    private ArrayList<String> puertosSerie;   

    private boolean leyendoPuertos=false;
    
    private boolean errorLeyendo=false;
        
    private PuertosSerieDelSO(){
    }

    public void run() {
        try { 
            setLeyendoPuertos(true);
            setErrorLeyendo(false);            
            int intentosDeConexion=1;
            boolean hayConexionEnCurso=(modelo.dataCapture.Gps.getInstance().getEstadoConexion() == 1) ||
                 (modelo.dataCapture.Sonda.getInstance().getEstadoConexion() == 1);
            while ( (hayConexionEnCurso) && (intentosDeConexion<4) ){
                ps.sleep(15000); //detengo durante 20 segundos xq hay un intento de conexion en curso
                hayConexionEnCurso=(modelo.dataCapture.Gps.getInstance().getEstadoConexion() == 1) ||
                                    (modelo.dataCapture.Sonda.getInstance().getEstadoConexion() == 1);
                intentosDeConexion++;
            }
            if (!(hayConexionEnCurso)){
                if (!(getPuertosSerieExistentes())) {
                    setErrorLeyendo(true);
                }   
            }
            else { 
                setErrorLeyendo(true); 
            }
            ps = null;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            setErrorLeyendo(true);
        }
        finally { setLeyendoPuertos(false); }
    }

    public void start() {
        if (ps == null) {
            ps = new Thread(this);
            ps.setPriority(Thread.MIN_PRIORITY);
            ps.start();  //primer Thread
        }
    }
    
    private boolean getPuertosSerieExistentes() {
        boolean sePudo=true;
        ArrayList<String> puertos = new ArrayList<String>();
        String fullPortName;
        int i = 0;
        try {
            // Lista de los puertos disponibles en la máquina=tiempo aproximado: 1min 12seg
            Logueador.getInstance().agregaAlLog("Obteniendo listado de puertos del Sistema Operativo, puede llevar hasta 2 minutos...");
            boolean retryPendiente=false;
            Enumeration e = null;
            try{
                e = CommPortIdentifier.getPortIdentifiers();
            }
            catch(UnsatisfiedLinkError ule){
                if (Sistema.getInstance().estanTodosLosArchivosNecesarios()){
                    Logueador.getInstance().agregaAlLog("Error al intentar obtener los puertos serie del SO\n"+
                            ule.toString());
                }
                else{
                    if (Sistema.getInstance().copiarArchivosNecesarios()){
                        retryPendiente = true;
                    }                    
                }
            }
            if (retryPendiente){
                controllers.ControllerPpal.getInstance().msgReiniciarAplicacion();
                try{
                    e = CommPortIdentifier.getPortIdentifiers(); //try again
                }
                catch(Exception exc){
                    Logueador.getInstance().agregaAlLog("Se cargó la DLL pero aun asi no se pudieron leer los COM de esta PC\n"+
                            exc.toString());
                }                
            }
            if (e != null){
                Logueador.getInstance().agregaAlLog("Listado de puertos obtenido!");
                while (e.hasMoreElements()) {
                    CommPortIdentifier id = (CommPortIdentifier) e.nextElement();
                    if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        //Logueador.getInstance().agregaAlLog(id.getName());
                        // Vamos guardando los puertos que se encontraron
                        fullPortName=id.getName();
                        /*  si quisiera anexarle al nombre del puerto el nombre de la aplicacion q lo esta usando, pero por ahora no conviene
                        if (!(id.getCurrentOwner() == null)){
                            fullPortName=fullPortName+": "+id.getCurrentOwner();
                        }
                        */ 
                        puertos.add(fullPortName);
                        i++;
                    }
                }
            }                
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo=false;
        }
        if (puertos.size()>0){
            setPuertosSerie(puertos);
        }
        else{
            puertos.add("NO COM PORTS!");
            setPuertosSerie(puertos);
        }
        return sePudo;
    }
        

    public static PuertosSerieDelSO getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new PuertosSerieDelSO();       
       return unicaInstancia;
    }

    /**
     * @return the puertosSerie
     */
    public ArrayList<String> getPuertosSerie() {
        return puertosSerie;
    }

    /**
     * @param puertosSerie the puertosSerie to set
     */
    private void setPuertosSerie(ArrayList<String> puertosSerie) {
        this.puertosSerie = puertosSerie;
        setChanged();
        notifyObservers();        
    }

    /**
     * @return the leyendoPuertos
     */
    public boolean isLeyendoPuertos() {
        return leyendoPuertos;
    }

    /**
     * @param leyendoPuertos the leyendoPuertos to set
     */
    private void setLeyendoPuertos(boolean leyendoPuertos) {
        this.leyendoPuertos = leyendoPuertos;
        setChanged();
        notifyObservers();        
    }

    /**
     * @return the errorLeyendo
     */
    public  boolean isErrorLeyendo() {
        return errorLeyendo;
    }

    /**
     * @param errorLeyendo the errorLeyendo to set
     */
    private void setErrorLeyendo(boolean errorLeyendo) {
        this.errorLeyendo = errorLeyendo;
        setChanged();
        notifyObservers();                
    }
    
    
}