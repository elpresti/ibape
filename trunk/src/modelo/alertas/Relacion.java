/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

/**
 *
 * @author Martin
 */
public class Relacion {
    private int id;
    private String descripcion;
    private int cantValores;

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

    /**
     * @return the cantValores
     */
    public int getCantValores() {
        return cantValores;
    }

    /**
     * @param cantValores the cantValores to set
     */
    public void setCantValores(int cantValores) {
        this.cantValores = cantValores;
    }
}
