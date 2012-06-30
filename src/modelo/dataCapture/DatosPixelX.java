/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Sebastian
 */
public class DatosPixelX {
    private int frecuencia;
    private int ganacia;
    private int stc;
    private int lw;
    private int gs;
    private int escala;
    private int shift;
    private int expander;
    private int unidad;
    private double latitud;
    private char eo;
    private double longitud;
    private char ns;
    private double velocidad;
    private double rumbo;
    private Date fechaYhora;
    private double velocidadProm;
    private double profundidad;
    private double tempAgua;

    /**
     * @return the frecuencia
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the ganacia
     */
    public int getGanacia() {
        return ganacia;
    }

    /**
     * @param ganacia the ganacia to set
     */
    public void setGanacia(int ganacia) {
        this.ganacia = ganacia;
    }

    /**
     * @return the stc
     */
    public int getStc() {
        return stc;
    }

    /**
     * @param stc the stc to set
     */
    public void setStc(int stc) {
        this.stc = stc;
    }

    /**
     * @return the lw
     */
    public int getLw() {
        return lw;
    }

    /**
     * @param lw the lw to set
     */
    public void setLw(int lw) {
        this.lw = lw;
    }

    /**
     * @return the gs
     */
    public int getGs() {
        return gs;
    }

    /**
     * @param gs the gs to set
     */
    public void setGs(int gs) {
        this.gs = gs;
    }

    /**
     * @return the escala
     */
    public int getEscala() {
        return escala;
    }

    /**
     * @param escala the escala to set
     */
    public void setEscala(int escala) {
        this.escala = escala;
    }

    /**
     * @return the shift
     */
    public int getShift() {
        return shift;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * @return the expander
     */
    public int getExpander() {
        return expander;
    }

    /**
     * @param expander the expander to set
     */
    public void setExpander(int expander) {
        this.expander = expander;
    }

    /**
     * @return the unidad
     */
    public int getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(int unidad) {
        this.unidad = unidad;
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
     * @return the eo
     */
    public char getEo() {
        return eo;
    }

    /**
     * @param eo the eo to set
     */
    public void setEo(char eo) {
        this.eo = eo;
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
     * @return the ns
     */
    public char getNs() {
        return ns;
    }

    /**
     * @param ns the ns to set
     */
    public void setNs(char ns) {
        this.ns = ns;
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
     * @return the fechaYhora
     */
    public Date getFechaYhora() {
        return fechaYhora;
    }

    /**
     * @param fechaYhora the fechaYhora to set
     */
    public void setFechaYhora(Date fechaYhora) {
        this.fechaYhora = fechaYhora;
    }

    /**
     * @return the velocidadProm
     */
    public double getVelocidadProm() {
        return velocidadProm;
    }

    /**
     * @param velocidadProm the velocidadProm to set
     */
    public void setVelocidadProm(double velocidadProm) {
        this.velocidadProm = velocidadProm;
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
}