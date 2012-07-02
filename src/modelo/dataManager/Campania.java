/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Campania {
    private int id;
    private String descripcion;
    private String barco;
    private String capitan;
    private java.util.Date fechaInicio;
    private java.util.Date fechaFin;
    private String folderHistorico;
    private int estado; //2=Pausada 1=en curso  0=finalizada
    private POI ultimoPoiConImg;
    private ArrayList<POI> pois;

    public Campania(){        
    }

    /**
     * @return the barco
     */
    public String getBarco() {
        return barco;
    }

    /**
     * @param barco the barco to set
     */
    public void setBarco(String barco) {
        this.barco = barco;
    }

    /**
     * @return the capitan
     */
    public String getCapitan() {
        return capitan;
    }

    /**
     * @param capitan the capitan to set
     */
    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null){ 
            persistencia.BrokerCampania.getInstance().updateCampania(this);
        }        
    }

    /**
     * @return the ultimoPoiConImg
     */
    public POI getUltimoPoiConImg() {
        return ultimoPoiConImg;
    }

    /**
     * @param ultimoPoiConImg the ultimoPoiConImg to set
     */
    public void setUltimoPoiConImg(POI ultimoPoiConImg) {
        this.ultimoPoiConImg = ultimoPoiConImg;
    }

    /**
     * @return the pois
     */
    public ArrayList<POI> getPois() {
        return pois;
    }

    /**
     * @param pois the pois to set
     */
    public void setPois(ArrayList<POI> pois) {
        this.pois = pois;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public boolean agregarPoi(POI nuevoPoi){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public boolean modificarPoi(POI poiAmodificar){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public boolean eliminarPoi(POI poiAeliminar){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public ArrayList<POI> obtenerPois(ArrayList<CategoriaPoi> cats){
        ArrayList<POI> poisEncontrados=new ArrayList<POI>();
        //método que obtiene el listado de Pois que sean de las categorias solicitadas
        
        //-------- metodo pendiente --------
        return poisEncontrados;
    }

    /**
     * @return the fechaInicio
     */
    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public java.util.Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(java.util.Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the folderHistorico
     */
    public String getFolderHistorico() {
        return folderHistorico;
    }

    /**
     * @param folderHistorico the folderHistorico to set
     */
    public void setFolderHistorico(String folderHistorico) {
        this.folderHistorico = folderHistorico;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    
}