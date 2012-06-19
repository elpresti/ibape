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
public class POI {
    private double latitud;
    private double longitud;
    private java.util.Date fechaHora;    
    private CategoriaPoi categoria;
    private ArrayList<Marca> marcas;
    private String pathImg;
    private int id;
    
    public POI(){        
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

    public double calculaProfPromMarcas(){
        double profProm=0;
        //---------- metodo pendiente --------
        return profProm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


}
