/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class Variable {
    private int id;
    private String nombre;
    private String unidad;
    private String ejemplo;
    

    /**
     * @return the nombre
     */

    public  String getNombre() {
        return nombre;
    }

    /**
     * @param aNombre the nombre to set
     */
    public void setNombre(String aNombre) {
        nombre = aNombre;
    }



    /**
     * @return the id
     */
    public  int getId() {
        return id;
    }

    /**
     * @param aId the id to set
     */
    public  void setId(int aId) {
        id = aId;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param aUnidad the unidad to set
     */
    public void setUnidad(String aUnidad) {
        unidad = aUnidad;
    }

    /**
     * @return the ejemplo
     */
    public String getEjemplo() {
        return ejemplo;
    }

    /**
     * @param ejemplo the ejemplo to set
     */
    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }
}
