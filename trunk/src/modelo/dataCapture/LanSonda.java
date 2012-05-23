/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class LanSonda {
    static LanSonda unicaInstancia;
    private int estadoConexion; //0=desconectado, 1=conectando, 2=conectado
    private String carpetaHistoricoLocal;
    private String carpetaHistoricoRemoto;
    
    private LanSonda(){
        inicializar();
    }
    
    
    
    public static LanSonda getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new LanSonda();
       return unicaInstancia;
    }           
    
    public boolean verificaConexionAequipo(String rutaSondaImgs){        
        boolean sePudo=false;
        //extrae la IP de la rutaSondaImgs e intenta establecer una conexion con el equipo remoto, si no hay exception --> salida=true;        
        return sePudo;
    }       

    public boolean verificaAccesoAcarpetaRemota(String rutaSondaImgs){
        boolean sePudo=false;
        //intenta listar archivos de la rutaIP especificada y si lo logra hace setHistoricoRemoto(rutaSondaImgs), si no hay exception --> salida=true;
        return sePudo;
    }    

    public ArrayList<String> hayArchivosNuevos(){
        ArrayList<String> archivosNuevos=new ArrayList<String>();
        //verifica si existen archivos nuevos, si hay devuelve sus filename en el vector, sino devuelve el vector con archivosNuevos.size()=0
        return archivosNuevos;
    }

    private void inicializar() {
        //trabajo de hoy
        setEstadoConexion(0);
    }
    
    private boolean copiarArchivosRemotos(ArrayList<String> fileNames){
        boolean sePudo=false;
        //metodo que intenta acceder a los archivos especificados por parámetro y si lo logra intenta copiarlos a la carpeta de historico local
        return sePudo;
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
    
}