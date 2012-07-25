/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import gnu.io.CommPortIdentifier;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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
                if (estanTodosLosArchivosNecesarios()){
                    Logueador.getInstance().agregaAlLog("Error al intentar obtener los puertos serie del SO\n"+
                            ule.toString());
                }
                else{
                    if (copiarArchivosNecesarios()){
                        retryPendiente = true;
                    }                    
                }
            }
            if (retryPendiente){
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
    
    private boolean estanTodosLosArchivosNecesarios() {
        boolean estanTodos=false;
        String winSystem32Dir = System.getProperty("windir")+"\\system32\\rxtxSerial.dll";
        File archivoRxtx = new File(winSystem32Dir);
        estanTodos = archivoRxtx.exists();
        //--- revisar, en principio si está la DLL q corresponde para la RXTXcomm segun la version de SO
        return estanTodos;
    }

    private boolean copiarArchivosNecesarios() {
        boolean sePudo=false; 
        //--- revisar, en principio si está la DLL q corresponde para la RXTXcomm segun la version de SO
        try {
            //    ---> C:\Program Files\Java\jdk1.7.0_05\bin\rxtxSerial.dll
            // probar con System.setProperty( "java.library.path", "/path/to/libs" );
            String ruta64b = System.getProperty("user.dir")+"\\lib\\DLL-RXTX-win64";
            String ruta32b = System.getProperty("user.dir")+"\\lib\\DLL-RXTX-win32";
            //System.load(ruta32b);
            try{
                agregarDirectorioDeLibrerias(ruta64b);
                sePudo=true;
            }
            catch(Exception e){
                Logueador.getInstance().agregaAlLog("No se pudo cargar la DLL para utilizar puertos COM\n"+
                        e.toString());
            }
        } catch (UnsatisfiedLinkError e) { 
          System.err.println("No se pudo cargar la libreria DLL para la lectura de puertos Serie.\n" + e);
        }
        return sePudo;
    }
    
    public void agregarDirectorioDeLibrerias(String fullPathDir) throws IOException {
            try {
                    // This enables the java.library.path to be modified at runtime
                    // From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
                    Field field = ClassLoader.class.getDeclaredField("usr_paths");
                    field.setAccessible(true);
                    String[] paths = (String[])field.get(null);
                    for (int i = 0; i < paths.length; i++) {
                            if (fullPathDir.equals(paths[i])) {
                                    return;
                            }
                    }
                    String[] tmp = new String[paths.length+1];
                    System.arraycopy(paths,0,tmp,0,paths.length);
                    tmp[paths.length] = fullPathDir;
                    field.set(null,tmp);
                    System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + fullPathDir);
            } catch (IllegalAccessException e) {
                    throw new IOException("Failed to get permissions to set library path");
            } catch (NoSuchFieldException e) {
                    throw new IOException("Failed to get field handle to set library path");
            }
    }    

}