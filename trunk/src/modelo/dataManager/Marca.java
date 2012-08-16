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
    private int areaImagen;
    private String areaFisica;
    private int id;
    private int idPois;
    private ArrayList<Point> coordMarca;
    
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

    /**
     * @return the areaImagen
     */
    public int getAreaImagen() {
        return areaImagen;
    }

    /**
     * @param areaImagen the areaImagen to set
     */
    public void setAreaImagen(int areaImagen) {
        this.areaImagen = areaImagen;
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

    /**
     * @return the idPois
     */
    public int getIdPois() {
        return idPois;
    }

    /**
     * @param idPois the idPois to set
     */
    public void setIdPois(int idPois) {
        this.idPois = idPois;
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
     * @return the coordMarca
     */
    public ArrayList<Point> getCoordMarca() {
        return coordMarca;
    }

    /**
     * @param coordMarca the coordMarca to set
     */
    public void setCoordMarca(ArrayList<Point> coordMarca) {
        this.coordMarca = coordMarca;
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
}