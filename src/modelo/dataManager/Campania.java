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
    private String nombre;
    private String barco;
    private String capitan;
    private java.util.Date fechaInicio;
    private java.util.Date fechaFin;
    private int estado;
    private Poi ultimoPoiConImg;
    private ArrayList<Poi> pois;

    public Campania(){        
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    }

    /**
     * @return the ultimoPoiConImg
     */
    public Poi getUltimoPoiConImg() {
        return ultimoPoiConImg;
    }

    /**
     * @param ultimoPoiConImg the ultimoPoiConImg to set
     */
    public void setUltimoPoiConImg(Poi ultimoPoiConImg) {
        this.ultimoPoiConImg = ultimoPoiConImg;
    }

    /**
     * @return the pois
     */
    public ArrayList<Poi> getPois() {
        return pois;
    }

    /**
     * @param pois the pois to set
     */
    public void setPois(ArrayList<Poi> pois) {
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
    
    public boolean agregarPoi(Poi nuevoPoi){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public boolean modificarPoi(Poi poiAmodificar){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public boolean eliminarPoi(Poi poiAeliminar){
        boolean sePudo=false;
        //-------- metodo pendiente --------
        return sePudo;
    }
    
    public ArrayList<Poi> obtenerPois(ArrayList<CategoriaPoi> cats){
        ArrayList<Poi> poisEncontrados=new ArrayList<Poi>();
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


    
}