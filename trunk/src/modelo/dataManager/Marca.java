/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class Marca {
    private double profundidad;
    private java.util.Date fechaYhora;
    private String areaFisica;
    private int id;
    private double latitud;
    private double longitud;
    private int pxXenImg;
    private int pxYenImg;
    private String imgFileName;
    private ArrayList<Point> areaEnImg;
    
    public Marca(){        
    }

    /**
     * @return the profundidad
     */
    public double getProfundidad() {
        return profundidad;
    }

    /**
     * @param profundidad the profundidad to set
     */
    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    public double calculaProf() {
        double profProm=0;
        //----------- metodo pendiente ------------
        return profProm;
    }

    public void setId(int id) {
        this.id =id;
    }

    public int getId() {
        return this.id;
    }
    
    public double getLatitudEstimada(){
        double latEstimada = 0;
        // --- metodo pendiente, que solicita al procesador de imagenes que estime 
        // la latitud de esta marca
        //el procesador de imagenes le pasa a un método de la clase CSV los siguientes parametros:
            // su fileName.jpg
            // el Integer del pixel x del cual quiere saber la fecha y hora
        // el método devuelve un Date con la fecha y hora del pixel x solicitado, o null si no lo encuentra
        
        return latEstimada;
    }

    /**
     * @return the areaEnImg
     */
    public ArrayList<Point> getAreaEnImg() {
        return areaEnImg;
    }

    /**
     * @param areaEnImg the areaEnImg to set
     */
    public void setAreaEnImg(ArrayList<Point> areaEnImg) {
        this.areaEnImg = areaEnImg;
    }

    /**
     * @return the areaFisica
     */
    public String getAreaFisica() {
        return areaFisica;
    }

    /**
     * @param areaFisica the areaFisica to set
     */
    public void setAreaFisica(String areaFisica) {
        this.areaFisica = areaFisica;
    }

    /**
     * @return the fechaYhora
     */
    public java.util.Date getFechaYhora() {
        return fechaYhora;
    }

    /**
     * @param fechaYhora the fechaYhora to set
     */
    public void setFechaYhora(java.util.Date fechaYhora) {
        this.fechaYhora = fechaYhora;
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
     * @return the pxXenImg
     */
    public int getPxXenImg() {
        return pxXenImg;
    }

    /**
     * @param pxXenImg the pxXenImg to set
     */
    public void setPxXenImg(int pxXenImg) {
        this.pxXenImg = pxXenImg;
    }

    /**
     * @return the pxYenImg
     */
    public int getPxYenImg() {
        return pxYenImg;
    }

    /**
     * @param pxYenImg the pxYenImg to set
     */
    public void setPxYenImg(int pxYenImg) {
        this.pxYenImg = pxYenImg;
    }

    /**
     * @return the imgFileName
     */
    public String getImgFileName() {
        return imgFileName;
    }

    /**
     * @param imgFileName the imgFileName to set
     */
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

}