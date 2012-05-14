/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import gnu.io.CommPortIdentifier;
import java.util.ArrayList;
import java.util.Enumeration;
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
            if (!(getPuertosSerieExistentes())) {
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
        setLeyendoPuertos(true);
        setErrorLeyendo(false);
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
            Enumeration e = CommPortIdentifier.getPortIdentifiers();
            Logueador.getInstance().agregaAlLog("Listado de puertos obtenido!");
            while (e.hasMoreElements()) {
                CommPortIdentifier id = (CommPortIdentifier) e.nextElement();
                if (id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    Logueador.getInstance().agregaAlLog(id.getName());
                    // Vamos guardando los puertos que se encontraron
                    fullPortName=id.getName();
                    if (!(id.getCurrentOwner() == null)){
                        fullPortName=fullPortName+": "+id.getCurrentOwner();
                    }
                    puertos.add(fullPortName);
                    i++;
                }
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo=false;
        }
        setPuertosSerie(puertos);
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