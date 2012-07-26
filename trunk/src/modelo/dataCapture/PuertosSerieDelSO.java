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
    
    private String currentVarDir;

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
                JOptionPane.showMessageDialog(null, "IBAPE fue inicializado correctamente, es necesario que reinicie la aplicación");
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
        boolean estanTodos=true;
        estanTodos= estanTodos && compruebaArchivosDeRxtxcomm();
        return estanTodos;
    }

    private boolean copiarArchivosNecesarios() {
        boolean sePudo=false; 
        //--- revisar, en principio si está la DLL q corresponde para la RXTXcomm segun la version de SO
        try {
            sePudo = copiarArchivosDeRxtxcomm();
        } catch (Exception e) { 
          Logueador.getInstance().agregaAlLog("No se pudieron copiar los archivos necesarios para la ejecución de IBAPE\n" + e);
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

    private ArrayList<String> getOsUserPaths() {
        ArrayList<String> directorios = new ArrayList();
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (int i = 0; i < paths.length; i++) {
                directorios.add(paths[i]);
            }
        } catch (IllegalAccessException e) {
            Logueador.getInstance().agregaAlLog("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            Logueador.getInstance().agregaAlLog("Failed to get field handle to set library path");
        }
        return directorios;
    }

    private boolean compruebaArchivosDeRxtxcomm() {
        boolean estanTodos = false;
        String winSystem32Dir = System.getProperty("windir")+"\\system32\\rxtxSerial.dll";
        File archivoRxtx = new File(winSystem32Dir);
        estanTodos = archivoRxtx.exists();
        return estanTodos;
    }

    private boolean copiarArchivosDeRxtxcomm() {
        boolean sePudo = false;
        //    ---> C:\Program Files\Java\jdk1.7.0_05\bin\rxtxSerial.dll
        // probar con System.setProperty( "java.library.path", "/path/to/libs" );
        String rutaFrom64b = System.getProperty("user.dir") + "\\lib\\DLL-RXTX-win64"+File.separatorChar+"rxtxSerial.dll";
        String rutaFrom32b = System.getProperty("user.dir") + "\\lib\\DLL-RXTX-win32"+File.separatorChar+"rxtxSerial.dll";
        String archivoFrom = new String();
        if (is64bOS()) {
            archivoFrom = rutaFrom64b;
        } else {
            archivoFrom = rutaFrom32b;
        }
        //System.load(ruta32b);
        try {            
            //agregarDirectorioDeLibrerias(ruta64b);
            //obtenemos los directorios de usuario e intentamos copiar la DLL en alguno de estos q tengamos permisos suficientes
            ArrayList<String> dirSOvars= getOsUserPaths();
            int i = 0;
            while ((i<dirSOvars.size()) && (!sePudo)){
                sePudo=copy(archivoFrom,dirSOvars.get(i)+File.separatorChar+"rxtxSerial.dll");
                i++;
            }
            if (sePudo){
                setCurrentVarDir(dirSOvars.get(i-1));
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("No se pudo cargar la DLL para utilizar puertos COM\n"
                    + e.toString());
        }
        return sePudo;
    }

    private boolean copy(String from, String to) {
        boolean sePudo=false;
        int BUFFER_SIZE = 2048;
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream in = null;
        OutputStream out = null;
        int amountRead;
        try {
            in = new FileInputStream(from);
            out = new FileOutputStream(to);
            while (true) {
                amountRead = in.read(buffer);
                if (amountRead == -1) {
                    break;
                }
                out.write(buffer, 0, amountRead);
            }
            sePudo=true;
        } catch(Exception e){            
            System.out.println(e.toString());
            //Logueador.getInstance().agregaAlLog(e.toString());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logueador.getInstance().agregaAlLog(ex.toString());
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logueador.getInstance().agregaAlLog(ex.toString());
                }
            }
        }
        return sePudo;
    }

    /**
     * @return the currentVarDir
     */
    public String getCurrentVarDir() {
        return currentVarDir;
    }

    /**
     * @param currentVarDir the currentVarDir to set
     */
    public void setCurrentVarDir(String currentVarDir) {
        this.currentVarDir = currentVarDir;
    }
    
    public boolean is64bOS() {
        boolean is64bit = false;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
        }
        return is64bit;
    }
    
}