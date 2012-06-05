/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class Marca {
    private double profundidad;
    private String areaImagen;
    private int id;
    
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
    public String getAreaImagen() {
        return areaImagen;
    }

    /**
     * @param areaImagen the areaImagen to set
     */
    public void setAreaImagen(String areaImagen) {
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
}