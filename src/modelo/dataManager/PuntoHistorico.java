/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class PuntoHistorico {
    private int id;
    private java.sql.Timestamp fechaYhora;
    private double latitud;
    private double longitud;
    private double altitud;
    private double rumbo;
    private double velocidad;
    private double profundidad;
    private double velocidadAgua;
    private double tempAgua;
    private String comentarios;

    public PuntoHistorico(){        
    }

    /**
     * @return the fechaYhora
     */
    public java.util.Date getFechaYhora() {        
        return new java.util.Date(fechaYhora.getTime());
    }

    /**
     * @param fechaYhora the fechaYhora to set
     */
    public void setFechaYhora(java.util.Date fechaYhora) {
        this.fechaYhora = new java.sql.Timestamp(fechaYhora.getTime());
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
     * @return the altitud
     */
    public double getAltitud() {
        return altitud;
    }

    /**
     * @param altitud the altitud to set
     */
    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    /**
     * @return the rumbo
     */
    public double getRumbo() {
        return rumbo;
    }

    /**
     * @param rumbo the rumbo to set
     */
    public void setRumbo(double rumbo) {
        this.rumbo = rumbo;
    }

    /**
     * @return the velocidad
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
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
     * @return the velocidadAgua
     */
    public double getVelocidadAgua() {
        return velocidadAgua;
    }

    /**
     * @param velocidadAgua the velocidadAgua to set
     */
    public void setVelocidadAgua(double velocidadAgua) {
        this.velocidadAgua = velocidadAgua;
    }

    /**
     * @return the tempAgua
     */
    public double getTempAgua() {
        return tempAgua;
    }

    /**
     * @param tempAgua the tempAgua to set
     */
    public void setTempAgua(double tempAgua) {
        this.tempAgua = tempAgua;
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
    

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    
}
