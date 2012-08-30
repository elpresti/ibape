/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;

/**
 *
 * @author Sebastian
 */
public class POI extends Observable{

    private int id;
    private int idCategoriaPOI;
    private int idCampania;
    private double latitud;
    private double longitud;
    private String pathImg;
    private java.util.Date fechaHora;
    private ArrayList<Marca> marcas;
    private String descripcion;
    private CategoriaPoi categoria;

    public POI() {
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the fechaHora
     */
    public java.util.Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public void setFechaHora(java.util.Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the pathImg
     */
    public String getPathImg() {
        return pathImg;
    }

    /**
     * @param pathImg the pathImg to set
     */
    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    /**
     * @return the marcas
     */
    public ArrayList<Marca> getMarcas() {
        return marcas;
    }

    /**
     * @param marcas the marcas to set
     */
    public void setMarcas(ArrayList<Marca> marcas) {
        this.marcas = marcas;
    }

    public double calculaProfPromMarcas() {
        double profProm = 0;
        //---------- metodo pendiente --------
        return profProm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * @return the idCategoriaPOI
     */
    public int getIdCategoriaPOI() {
        return idCategoriaPOI;
    }

    /**
     * @param idCategoriaPOI the idCategoriaPOI to set
     */
    public void setIdCategoriaPOI(int idCategoriaPOI) {
        this.idCategoriaPOI = idCategoriaPOI;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the idCampania
     */
    public int getIdCampania() {
        return idCampania;
    }

    /**
     * @param idCampania the idCampania to set
     */
    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }

    /**
     * @return the categoria
     */
    public CategoriaPoi getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaPoi categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}
