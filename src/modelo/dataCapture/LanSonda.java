/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.SondaSetHistorico;
import modelo.gisModule.OperacionesBasicas;
import persistencia.BrokerHistoricoSondaSet;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class LanSonda extends java.util.Observable implements Runnable {
    Thread lanThread;
    static final int BUFFER_SIZE = 2048;
    static final byte[] buffer = new byte[BUFFER_SIZE];
    static LanSonda unicaInstancia;
    private int estadoConexion; // 0=desconectado, 1=conectando, 2=conectado
    private String carpetaHistoricoLocal;
    private String carpetaHistoricoRemoto;
    private java.util.Date fyhUltimaLecturaRemota;
    private java.util.Date fYhArchivoRemotoMasRecientementeModificado;

    private LanSonda() {
        inicializar();
    }

    public static LanSonda getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new LanSonda();
        }
        return unicaInstancia;
    }

    public boolean verificaConexionAequipo2() {
        boolean sePudo = false;
        try {
            //extrae la IP de la rutaSondaImgs e intenta establecer una conexion con el equipo remoto,
            //si no hay exception --> salida=true;
            InetAddress IPaddress = InetAddress.getByName(getCarpetaHistoricoRemoto());
            sePudo = IPaddress.isReachable(5000); //5000=tiempo de espera por respuesta del equipo
            /*//test
            if (sePudo) {
                System.out.println("El equipo responde -" + IPaddress);
            } else {
                System.out.println("El equipo NO responde-" + IPaddress);
            }*/
        } catch (IOException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }

    
    public boolean verificaConexionAequipo() {
        boolean sePudo = false;
        //verifica si se pude abrir la carpeta remota, del equipo remoto
        try{
            File dirRemoto = new File(getCarpetaHistoricoRemoto());
            if (dirRemoto.list() != null){
                sePudo = true;
            }
        }    
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
/*    
    public ArrayList<String> hayArchivosNuevos() {
        ArrayList<String> archivosNuevos = new ArrayList();
        //verifica si existen archivos nuevos, si hay devuelve sus filename en el vector,
        //sino devuelve el vector con archivosNuevos.size()=0
        File dirLocal = new File(getCarpetaHistoricoLocal());
        String[] archivosL = dirLocal.list();
        File dirRemoto = new File(getCarpetaHistoricoRemoto());
        String[] archivosR = dirRemoto.list();      

        if (archivosR == null) {
            System.out.println("No hay ficheros en el directorio especificado");
        } else {
            if (archivosL.length>0){
                //Date fechaUltimoLocal = verFecha(getCarpetaHistoricoLocal() + "\\" + archivosL[archivosL.length]);
                Date fechaUltimoLocal = getfYhArchivoRemotoMasRecientementeModificado();
                for (int x = 0; x <= archivosR.length - 1; x++) {
                    Date fechaUltimoRemoto = verFecha(getCarpetaHistoricoRemoto() + "\\" + archivosR[x].toString());
                    if (fechaUltimoRemoto.compareTo(fechaUltimoLocal) > 0) {
                        //System.out.println(archivosR[x]); //archivo remoto que no esta en local (fechaR>ultimo(fechaL))
                        archivosNuevos.add(archivosR[x]);
                    }
                }
            }
            else{
                for (int x = 0; x <= archivosR.length - 1; x++) {
                    //System.out.println(archivosR[x]); //archivo remoto que no esta en local (fechaR>ultimo(fechaL))
                    archivosNuevos.add(archivosR[x]);
                }
            }
        }
        return archivosNuevos;
    }
*/
    
    private void inicializar() {
        setEstadoConexion(0);
    }

    private boolean copiarArchivosRemotos(ArrayList<File> archivos) {
        boolean sePudo = false;
        //intenta listar archivos de la rutaIP especificada y si lo logra hace setHistoricoRemoto(rutaSondaImgs),
        //si no hay exception --> salida=true;
        int x = 0;
        while (x < archivos.size()) {
            try {
                String from = getCarpetaHistoricoRemoto() + "\\" + archivos.get(x).getName();
                String to = getCarpetaHistoricoLocal() + "\\" + archivos.get(x).getName();
                copy(from, to);
            } catch (IOException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
            x++;
        }
        if (archivos.size()>0) {
            setfYhArchivoRemotoMasRecientementeModificado(new Date(archivos.get(0).lastModified()));
            sePudo = true;
        }
        return sePudo;
    }

    private Date verFecha(String archivo) {
        File arch = new File(archivo);
        long ms = arch.lastModified();
        Date d = new Date(ms);
        return d;
    }

    public void copy(String from, String to) throws IOException {
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
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public ArrayList<File> getArchivosNuevos(){
        String rutaCarpeta = getCarpetaHistoricoRemoto();
        //Date fechaDeModificacion = getFyhUltimaLecturaRemota();
        //primero: obtengo la fecha de modificacion del archivo mas reciente en el directorio local
        File[] archivosLocales = getArchivosOrdenadosPorFechaModificacion(getCarpetaHistoricoLocal(),true);
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null){
            archivosLocales = excluirArchivosDeSistema(archivosLocales);
        }        
        Date fYhArchivoLocalMasNuevo = null;
        if ((archivosLocales != null) && (archivosLocales.length>0)) {
            if (getfYhArchivoRemotoMasRecientementeModificado() != null){
                fYhArchivoLocalMasNuevo = getfYhArchivoRemotoMasRecientementeModificado() ;
            }
            else{
                fYhArchivoLocalMasNuevo = new Date( archivosLocales[0].lastModified() );
            }
            
        }
        //segundo: obtengo los archivos del directorio remoto, ordenados por fecha de modificacion
        ArrayList<File> archivos = new ArrayList();       
        File[] filesInDirectory = getArchivosOrdenadosPorFechaModificacion(rutaCarpeta,true);
        // Ponemos en el vector de archivos nuevos los que tengan fecha de modificación mayor a la especificada
        if (filesInDirectory.length>0){
            boolean archivosViejos=false;
            int i =0;
            //como en filesInDirectory los tengo ordenados por fecha de modificacion descendente, 
            // guardo solo los nuevos y termino cuando encuentre los viejos
            while (i<filesInDirectory.length && (!(archivosViejos))){
                File file = filesInDirectory[i];
                    if (fYhArchivoLocalMasNuevo != null){
                        if ((file.lastModified() - fYhArchivoLocalMasNuevo.getTime()) > 0){
                            if (esArchivoDeHistorico(file)){
                                archivos.add(file);                                
                            }
                        }
                        else
                        { archivosViejos = true; }                    
                    }
                    else
                    {   if (esArchivoDeHistorico(file)){
                            archivos.add(file); 
                        }
                    }
                i++;
            }
        }
        return archivos;
    }

    /**
     * @return the estadoConexion
     */
    public int getEstadoConexion() {
        return estadoConexion;
    }

    /**
     * @param estadoConexion the estadoConexion to set
     */
    public void setEstadoConexion(int estadoConexion) {
        this.estadoConexion = estadoConexion;
        setChanged();
        notifyObservers();        
    }

    /**
     * @return the carpetaHistoricoLocal
     */
    public String getCarpetaHistoricoLocal() {
        return carpetaHistoricoLocal;
    }

    /**
     * @param carpetaHistoricoLocal the carpetaHistoricoLocal to set
     */
    public void setCarpetaHistoricoLocal(String carpetaHistoricoLocal) {
        this.carpetaHistoricoLocal = carpetaHistoricoLocal;
    }

    /**
     * @return the carpetaHistoricoRemoto
     */
    public String getCarpetaHistoricoRemoto() {
        return carpetaHistoricoRemoto;
    }

    /**
     * @param carpetaHistoricoRemoto the carpetaHistoricoRemoto to set
     */
    public void setCarpetaHistoricoRemoto(String carpetaHistoricoRemoto) {
        this.carpetaHistoricoRemoto = carpetaHistoricoRemoto;
    }

    /**
     * @return the fyhUltimaLecturaRemota
     */
    public java.util.Date getFyhUltimaLecturaRemota() {
        return fyhUltimaLecturaRemota;
    }

    /**
     * @param fyhUltimaLecturaRemota the fyhUltimaLecturaRemota to set
     */
    public void setFyhUltimaLecturaRemota(java.util.Date fyhUltimaLecturaRemota) {
        this.fyhUltimaLecturaRemota = fyhUltimaLecturaRemota;
        //System.out.println("Ultima vez que se leyo la carpeta remota: "+fyhUltimaLecturaRemota.toString());
        setChanged();
        notifyObservers();        
    }    

    public void run(){
        setEstadoConexion(1);
        if (verificaConexionAequipo()){
            setEstadoConexion(2);
            Thread esteThread = Thread.currentThread();
            while ((lanThread == esteThread) && (getEstadoConexion()==2)){    
                try{
                    if (verificaConexionAequipo()){
                        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null && AdministraCampanias.getInstance().getCampaniaEnCurso().getEstado()==1){
                            setEstadoConexion(3);
                            ArrayList<File> archivosNuevos = getArchivosNuevos();
                            setFyhUltimaLecturaRemota(Calendar.getInstance().getTime());
                            if (archivosNuevos.size()>0){
                                if (copiarArchivosRemotos(archivosNuevos)){
                                    int i = 0;
                                    String rutaAcsv =null;
                                    while (i<archivosNuevos.size()){
                                        if (archivosNuevos.get(i).getName().toLowerCase().contains(".jpg")){//si el archivo nuevo es un JPG
                                            //rutaAcsv = Csv.getInstance().getCsvFromJpg(archivosNuevos.get(i).getName().toLowerCase()); //busco su DAT y genero el CSV
                                            LeeDatYprocesaImg ldypi = new LeeDatYprocesaImg();
                                            ldypi.setImgFileName(AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+
                                                    archivosNuevos.get(i).getName().toLowerCase());
                                            ldypi.start();
                                            /*
                                            if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosGps()){
                                                persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
                                            }
                                            */
                                        }
                                        i++;
                                    }
                                }
                            }
                            setEstadoConexion(2);
                        }
                    }
                    else { 
                        setEstadoConexion(0);
                        setEstadoConexion(4);
                    }
                    Thread.sleep(30000); //dispara la rutina de chequeo cada XX segundos
                }
                catch (Exception e) {
                    persistencia.Logueador.getInstance().agregaAlLog(e.toString());
                }
            }
        }
        else
            {   setEstadoConexion(0);
                setEstadoConexion(4); }
        lanThread=null;
        setEstadoConexion(0);
    }
            
    public void start(){
        if (lanThread == null){
            lanThread = new Thread(this);
            lanThread.setPriority(Thread.MIN_PRIORITY);
            lanThread.start();
        }
    }    
/*
    public static void main(String[] args) {
        //tests        
            //LanSonda.getInstance().setCarpetaHistoricoLocal("C:\\Users\\Necrophagist\\Desktop\\Tesis\\asd");
            //LanSonda.getInstance().setCarpetaHistoricoRemoto("C:\\Users\\Necrophagist\\Desktop\\Tesis\\asd1");
        // OK - LanSonda.getInstance().verificaConexionAequipo("NECROPHAGIST-PC");
        // OK - LanSonda.getInstance().verificaAccesoAcarpetaRemota("\\\\192.168.1.102\\Users\\Necrophagist\\Desktop\\Tesis\\asd"); //tiene que empezar con las 4\
        // OK - LanSonda.getInstance().hayArchivosNuevos();
            //LanSonda.getInstance().copiarArchivosRemotos(LanSonda.getInstance().hayArchivosNuevos());
        getInstance().setCarpetaHistoricoLocal("\\Historico");
        //getInstance().setCarpetaHistoricoRemoto("\\\\192.168.169.128\\Compartida");
        getInstance().setCarpetaHistoricoRemoto("\\\\192.168.0.102\\2011");
        getInstance().start();
    }
*/
    public boolean disparaLectura() {
        boolean sePudo=false;
        if ((getCarpetaHistoricoLocal().length()>1) && (getCarpetaHistoricoRemoto().length()>1)) {
            this.start();
            sePudo = true;
        }    
        return sePudo;
    }

    public boolean detieneLectura() {
        boolean sePudo=false;
        if (lanThread != null) {
            lanThread=null;
            setEstadoConexion(0);
            sePudo = true;
        }    
        return sePudo;
    }    

    private File[] getArchivosOrdenadosPorFechaModificacion(String rutaCarpeta, boolean ascendente) {
        // FileFilter that accepts all files except directories
        FileFilter noDirectories = new FileFilter() {
            public boolean accept(File f) {
                return !f.isDirectory();
            }
        };
        // Comparator for modification date in descending order
        Comparator<File> descendingOnModificationDate =
            new Comparator<File>() {
                public int compare(File f1, File f2) {
                    long diff = f1.lastModified() - f2.lastModified();
                    int returnValue;
                    if (diff < 0L) {
                        returnValue = -1;
                    } else if (diff > 0L) {
                        returnValue = +1;
                    } else {
                        assert diff == 0L;
                        returnValue = 0;
                    }
                    /*
                    if (ascendente){
                        return +returnValue; // +returnValue for ascending order
                    }
                    else { return -returnValue; } // -returnValue for descending order
                    */ 
                    return -returnValue;
                }
            };
        // Directory to list (here: user's home directory)
        File directory = new File(rutaCarpeta);
        // Obtain non-directory files in the directory
        File[] filesInDirectory = directory.listFiles(noDirectories);
        if (filesInDirectory != null){           
            // Sort the list on modification date in descending order
            Arrays.sort(filesInDirectory, descendingOnModificationDate);            
        }
        return filesInDirectory;
    }
    
    public boolean guardaSondaSets(ArrayList<SondaSetHistorico> sondaSetsLeidos){
        boolean sePudo = false;
        try{
            for (int i = 0; i<sondaSetsLeidos.size();i++){
                BrokerHistoricoSondaSet.getInstance().insertSondaSet(sondaSetsLeidos.get(i));
            }
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }    

    private boolean hayArchivoCsv(ArrayList<File> archivosNuevos) {
        boolean hayCsv = false; 
        int i = 0;
        while ((i<archivosNuevos.size()) && (!(hayCsv))){
            if (archivosNuevos.get(i).getName().equals(Csv.getInstance().getCsvFileName())){
                hayCsv=true;
            }
            i++;    
        }
        return hayCsv;
    }

    private File[] excluirArchivosDeSistema(File[] archivosLocales) {
        File[] listadoSinArchivosDeSistema = null; 
        if (archivosLocales != null){ 
            ArrayList<File> lista = new ArrayList<File>(Arrays.asList(archivosLocales)); 
            String rutaDBhistorico = 
                    persistencia.BrokerHistoricoPunto.getInstance().getFolderNameHistorico()+"\\"+
                    AdministraCampanias.getInstance().getCampaniaEnCurso().getFolderHistorico()+"\\"+
                    persistencia.BrokerHistoricoPunto.getInstance().getDbFileName();
            lista.remove(new File(rutaDBhistorico)); //sacamos la DB
            //lista.remove(new File(LanSonda.getInstance().getCarpetaHistoricoLocal()+"\\"+Csv.getInstance().getConversorFileName()));
            if (lista.size()>0){
                listadoSinArchivosDeSistema = lista.toArray(new File[lista.size()]);                        
            }
        }
        return listadoSinArchivosDeSistema;
    }

    /**
     * @return the fYhArchivoRemotoMasRecientementeModificado
     */
    public java.util.Date getfYhArchivoRemotoMasRecientementeModificado() {
        return fYhArchivoRemotoMasRecientementeModificado;
    }

    /**
     * @param fYhArchivoRemotoMasRecientementeModificado the fYhArchivoRemotoMasRecientementeModificado to set
     */
    public void setfYhArchivoRemotoMasRecientementeModificado(java.util.Date fYhArchivoRemotoMasRecientementeModificado) {
        this.fYhArchivoRemotoMasRecientementeModificado = fYhArchivoRemotoMasRecientementeModificado;
    }

    private boolean esArchivoDeHistorico(File file) {
        boolean esArchivoDeHistorico=false;
        if ( file.getName().toLowerCase().contains(".dat") || 
             file.getName().toLowerCase().contains(".jpg") ||
             file.getName().toLowerCase().contains(".txt")
           ){
            esArchivoDeHistorico = true;
        }         
        return esArchivoDeHistorico;
    }
    
}

class LeeDatYprocesaImg implements Runnable{
    Thread thLdypi;
    private String imgFileName;
    public void run() { 
        boolean sePudo = false;
        try{String datFileName = imgFileName.toLowerCase().replace(".jpg",".dat");
            File imgFile = new File(imgFileName);
            File datFile = new File(datFileName);
            if (imgFile.exists() && datFile.exists()){
                if (DATatlantis.getInstance().leerDat(datFileName)){
                   if (OperacionesBasicas.getInstance().procesarImagen(imgFileName)){
                       sePudo=true;
                   }
                }
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("LeeDatYprocesaImg: "+e.toString());
        }
        if (sePudo){
            controllers.ControllerNavegacion.getInstance().actualizaGuiProcesamientoImgs();
        }else{
            controllers.ControllerNavegacion.getInstance().errorGuiProcesamientoImgs();
        }
        
        thLdypi = null;
    }
    public void start() {
        if (thLdypi == null) {
            thLdypi = new Thread(this);
            thLdypi.setPriority(Thread.MIN_PRIORITY);
            thLdypi.start();
        }
    }
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    public String getImgFileName() {
        return imgFileName;
    }
}