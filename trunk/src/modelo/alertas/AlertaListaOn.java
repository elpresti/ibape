/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin
 */
public class AlertaListaOn {
    private Alerta alerta;
    private boolean estadoActivacion;
    private Timestamp fechaActivacion;
    private Timestamp fechaDesactivacion;
    private ArrayList<Object> valores;
    private double latitud;
    private double longitud;
    private int idOcur;

    /**
     * @return the id
     */
    
    /*
    public static void main(String args[]){
        Alerta alerta=new Alerta();
    }
    */
    public AlertaListaOn(){
        
    }
    

    /**
     * @return the alerta
     */
    public Alerta getAlerta() {
        return alerta;
    }

    /**
     * @param alerta the alerta to set
     */
    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    /**
     * @return the estadoActivacion
     */
    public boolean isEstadoActivacion() {
        return estadoActivacion;
    }

    /**
     * @param estadoActivacion the estadoActivacion to set
     */
    public void setEstadoActivacion(boolean estadoActivacion) {
        this.estadoActivacion = estadoActivacion;
    }

    /**
     * @return the fechaActivacion
     */
    public Timestamp getFechaActivacion() {
        return fechaActivacion;
    }

    /**
     * @param fechaActivacion the fechaActivacion to set
     */
    public void setFechaActivacion(Timestamp fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    /**
     * @return the fechaDesactivacion
     */
    public Timestamp getFechaDesactivacion() {
        return fechaDesactivacion;
    }

    /**
     * @param fechaDesactivacion the fechaDesactivacion to set
     */
    public void setFechaDesactivacion(Timestamp fechaDesactivacion) {
        this.fechaDesactivacion = fechaDesactivacion;
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
     * @return the idOcur
     */
    public int getIdOcur() {
        return idOcur;
    }

    /**
     * @param idOcur the idOcur to set
     */
    public void setIdOcur(int idOcur) {
        this.idOcur = idOcur;
    }

    /**
     * @return the valores
     */
    public ArrayList<Object> getValores() {
        return valores;
    }

    /**
     * @param valores the valores to set
     */
    public void setValores(ArrayList<Object> valores) {
        this.valores = valores;
    }
}
