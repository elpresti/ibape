/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Necrophagist
 */
public class Cajon {
    
    private int id;
    private int idLance;
    private int idEspecie;
    private int cantidad;

    /**
     * @return the idLance
     */
    public int getIdLance() {
        return idLance;
    }

    /**
     * @param idLance the idLance to set
     */
    public void setIdLance(int idLance) {
        this.idLance = idLance;
    }

    /**
     * @return the idEspecie
     */
    public int getIdEspecie() {
        return idEspecie;
    }

    /**
     * @param idEspecie the idEspecie to set
     */
    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString() {
        return ""+getIdLance();//String.valueOf(this.idLance);//VER esta para probar
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
}
