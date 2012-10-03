/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import gui.PanelOpcLances;
import persistencia.BrokerCampania;

/**
 *
 * @author Necrophagist
 */
public class Lance {

    private int id;
    private int idCampania;
    private java.util.Date fYHIni;
    private java.util.Date fYHFin;
    private double posIniLat;
    private double posIniLon;
    private double posFinLat;
    private double posFinLon;
    private String comentarios;
    private int estadoLance; //0=finalizado 1=en curso

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
     * @return the fYHIni
     */
    public java.util.Date getfYHIni() {
        return fYHIni;
    }

    /**
     * @param fYHIni the fYHIni to set
     */
    public void setfYHIni(java.util.Date fYHIni) {
        this.fYHIni = fYHIni;
    }

    /**
     * @return the fYHFin
     */
    public java.util.Date getfYHFin() {
        return fYHFin;
    }

    /**
     * @param fYHFin the fYHFin to set
     */
    public void setfYHFin(java.util.Date fYHFin) {
        this.fYHFin = fYHFin;
    }

    /**
     * @return the posIniLat
     */
    public double getPosIniLat() {
        return posIniLat;
    }

    /**
     * @param posIniLat the posIniLat to set
     */
    public void setPosIniLat(double posIniLat) {
        this.posIniLat = posIniLat;
    }

    /**
     * @return the posIniLon
     */
    public double getPosIniLon() {
        return posIniLon;
    }

    /**
     * @param posIniLon the posIniLon to set
     */
    public void setPosIniLon(double posIniLon) {
        this.posIniLon = posIniLon;
    }

    /**
     * @return the posFinLat
     */
    public double getPosFinLat() {
        return posFinLat;
    }

    /**
     * @param posFinLat the posFinLat to set
     */
    public void setPosFinLat(double posFinLat) {
        this.posFinLat = posFinLat;
    }

    /**
     * @return the posFinLon
     */
    public double getPosFinLon() {
        return posFinLon;
    }

    /**
     * @param posFinLon the posFinLon to set
     */
    public void setPosFinLon(double posFinLon) {
        this.posFinLon = posFinLon;
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

    /**
     * @return the estadoLance
     */
    public int getEstadoLance() {
        return estadoLance;
    }

    /**
     * @param estadoLance the estadoLance to set
     */
    public void setEstadoLance(int estadoLance) {
        this.estadoLance = estadoLance;
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}
