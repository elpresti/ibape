/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class Sistema {
    private static Sistema unicaInstancia;
    private String currentVarDir;
    private String rutaIconosCatPois;
    
    private Sistema(){
        inicializador();
    }
    
    public static Sistema getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new Sistema();       
       return unicaInstancia;
    }
    
    public boolean estanTodosLosArchivosNecesarios() {
        boolean estanTodos=true;
        estanTodos= estanTodos && compruebaArchivosDeRxtxcomm();
        return estanTodos;
    }

    public boolean copiarArchivosNecesarios() {
        boolean sePudo=false; 
        //--- revisar, en principio si está la DLL q corresponde para la RXTXcomm segun la version de SO
        try {
            sePudo = copiarArchivosDeRxtxcomm();
        } catch (Exception e) { 
          Logueador.getInstance().agregaAlLog("No se pudieron copiar los archivos necesarios para la ejecución de IBAPE\n" + e);
        }
        return sePudo;
    }
    
    private void agregarDirectorioDeLibrerias(String fullPathDir) throws IOException {
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
            Logueador.getInstance().agregaAlLog("Se encontraron en sistema "+dirSOvars.size()+" directorios de Variables de Entorno");
            while ((i<dirSOvars.size()) && (!sePudo)){
                sePudo=copy(archivoFrom,dirSOvars.get(i)+File.separatorChar+"rxtxSerial.dll");
                i++;
            }
            if (sePudo){
                Logueador.getInstance().agregaAlLog("Se utilizará el directorio "+(i-1)+"/"+dirSOvars.size()+": "+dirSOvars.get(i-1));
                setCurrentVarDir(dirSOvars.get(i-1));
            }
            else{
                Logueador.getInstance().agregaAlLog((i-1)+"/"+dirSOvars.size()+": "+"No pudo copiar los archivos necesarios en ningun directorio");
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
            Logueador.getInstance().agregaAlLog(e.toString());
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
    
    public ArrayList<String> getIconosCatPois(){
            ArrayList<String> fileNamesIconosCat = new ArrayList();
            //escanea en el directorio imgs/iconos los PNG cuyo filename comience con "icono-cat-", y los va guardando en el vector
            // FileFilter that accepts all files except directories
            FileFilter noDirectories = new FileFilter() {
                public boolean accept(File f) {
                    if (!f.isDirectory() && f.getName().toLowerCase().endsWith("png") &&
                            f.getName().toLowerCase().startsWith("icono-cat-")){
                        return true;
                    }
                    else{
                        return false;
                    }                    
                }
            };
            // Directory to list (here: user's home directory)
            File directory = new File(getRutaIconosCatPois());
            // Obtain non-directory files in the directory
            File[] filesInDirectory = directory.listFiles(noDirectories);
            if (filesInDirectory != null){           
                for (File oneFileName : filesInDirectory){
                    fileNamesIconosCat.add(oneFileName.getName());
                }
            }
            return fileNamesIconosCat;
    }    

    /**
     * @return the rutaIconosCatPois
     */
    public String getRutaIconosCatPois() {
        return rutaIconosCatPois;
    }

    /**
     * @param rutaIconosCatPois the rutaIconosCatPois to set
     */
    public void setRutaIconosCatPois(String rutaIconosCatPois) {
        this.rutaIconosCatPois = rutaIconosCatPois;
    }

    private void inicializador() {
        setRutaIconosCatPois("imgs\\iconos\\");
    }
    
    public JLabel getLabelWithImgResized(int w, int h, BufferedImage image) {  
        BufferedImage scaled = scaleImg(image, w, h);  
        JLabel label = new JLabel(new ImageIcon(scaled));  
        label.setPreferredSize(new Dimension(w, h));  
        label.setBorder(BorderFactory.createEtchedBorder());  
        return label;  
    }  
   
    private BufferedImage scaleImg(BufferedImage src, int w, int h) {  
        int type = BufferedImage.TYPE_INT_RGB;  
        BufferedImage dst = new BufferedImage(w, h, type);  
        Graphics2D g2 = dst.createGraphics();  
        // Fill background for scale to fit.  
        g2.setBackground(UIManager.getColor("Panel.background"));  
        g2.clearRect(0,0,w,h);  
        double xScale = (double)w/src.getWidth();  
        double yScale = (double)h/src.getHeight();  
        // Scaling options:  
        // Scale to fit - image just fits in label.  
        double scale = Math.min(xScale, yScale);  
        // Scale to fill - image just fills label.  
        //double scale = Math.max(xScale, yScale);  
        int width  = (int)(scale*src.getWidth());  
        int height = (int)(scale*src.getHeight());  
        int x = (w - width)/2;  
        int y = (h - height)/2;  
        g2.drawImage(src, x, y, width, height, null);  
        g2.dispose();  
        return dst;  
    }
        
}
