/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class LanSonda implements Runnable {
    Thread lanThread;
    static final int BUFFER_SIZE = 2048;
    static final byte[] buffer = new byte[BUFFER_SIZE];
    static LanSonda unicaInstancia;
    private int estadoConexion; // 0=desconectado, 1=conectando, 2=conectado
    private String carpetaHistoricoLocal;
    private String carpetaHistoricoRemoto;
    private java.util.Date fyhUltimaLecturaRemota;

    private LanSonda() {
        inicializar();
    }

    public static LanSonda getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new LanSonda();
        }
        return unicaInstancia;
    }

    public boolean verificaConexionAequipo() {
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

    public String[] hayArchivosNuevos() {
        //verifica si existen archivos nuevos, si hay devuelve sus filename en el vector,
        //sino devuelve el vector con archivosNuevos.size()=0
        File dirLocal = new File(getCarpetaHistoricoLocal());
        String[] archivosL = dirLocal.list();
        File dirRemoto = new File(getCarpetaHistoricoRemoto());
        String[] archivosR = dirRemoto.list();
        String[] archivosNuevos = {""};

        if (archivosR == null) {
            System.out.println("No hay ficheros en el directorio especificado");
        } else {
            Date fechaUltimoLocal = verFecha(getCarpetaHistoricoLocal() + "\\" + archivosL[archivosL.length - 1]);
            int i = 0;
            for (int x = 0; x <= archivosR.length - 1; x++) {
                Date fechaUltimoRemoto = verFecha(getCarpetaHistoricoRemoto() + "\\" + archivosR[x].toString());
                if (fechaUltimoRemoto.compareTo(fechaUltimoLocal) > 0) {
                    //System.out.println(archivosR[x]); //archivo remoto que no esta en local (fechaR>ultimo(fechaL))
                    archivosNuevos[i] = archivosR[x];
                    i++;
                }
            }
        }
        return archivosNuevos;
    }

    private void inicializar() {
        setEstadoConexion(0);
    }

    private boolean copiarArchivosRemotos(String[] archivos) {
        boolean sePudo = false;
        //intenta listar archivos de la rutaIP especificada y si lo logra hace setHistoricoRemoto(rutaSondaImgs),
        //si no hay exception --> salida=true;
        int x = 0;
        while (x <= archivos.length - 1) {
            try {
                String from = getCarpetaHistoricoRemoto() + "\\" + archivos[x];
                String to = getCarpetaHistoricoLocal() + "\\" + archivos[x];
                copy(from, to);
            } catch (IOException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
            x++;
        }
        if (x == archivos.length - 1) {
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

    public static void copy(String from, String to) throws IOException {
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
        System.out.println("Ultima vez que se leyo la carpeta remota: "+fyhUltimaLecturaRemota.toString());
    }

    public void run(){
        setEstadoConexion(1);
        if (verificaConexionAequipo()){
            setEstadoConexion(2);
            Thread esteThread = Thread.currentThread();
            while ((lanThread == esteThread) && (getEstadoConexion()==2)){    
                try{
                    if (verificaConexionAequipo()){
                        String[] archivosNuevos = hayArchivosNuevos();
                        setFyhUltimaLecturaRemota(Calendar.getInstance().getTime());
                        if (archivosNuevos.length>0){
                            copiarArchivosRemotos(archivosNuevos);                            
                        }
                        Thread.sleep(10000); //dispara la rutina de chequeo cada 10seg
                    }
                    else { setEstadoConexion(0); }                    
                }
                catch (Exception e)
                { persistencia.Logueador.getInstance().agregaAlLog(e.toString()); }
            }            
        }
        else
            { setEstadoConexion(0); }
        lanThread=null;
    }
            
    public void start(){
        if (lanThread == null){
            lanThread = new Thread(this);
            lanThread.setPriority(Thread.MIN_PRIORITY);
            lanThread.start();
        }
    }
    
    

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

    
}