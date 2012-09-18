/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class UltimaImgProcesada {
    private java.util.Date fechaYhora;
    private ArrayList<modelo.dataManager.Marca> marcas;
    private ArrayList<Float> porcentajesColores;
    private String fileName;
    private static UltimaImgProcesada unicaInstancia;
    private int progresoProcesamiento; //0=sin procesar, 1=ya binarice, 2=ya erosioné y dilaté, 3=ya quite el fondo, 4=ya busque marcas, 5= ya las pinté, 6= fin de procesamiento...
    
    private UltimaImgProcesada (){}
    
    public static UltimaImgProcesada getInstance(){
        if (unicaInstancia == null){
            unicaInstancia=new UltimaImgProcesada();
        }
        return unicaInstancia;
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
        UltimaImgProcesada.getInstance().setProgresoProcesamiento(0);
    }

    /**
     * @return the marcas
     */
    public ArrayList<modelo.dataManager.Marca> getMarcas() {
        return marcas;
    }

    /**
     * @param marcas the marcas to set
     */
    public void setMarcas(ArrayList<modelo.dataManager.Marca> marcas) {
        this.marcas = marcas;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the progresoProcesamiento
     */
    public int getProgresoProcesamiento() {
        return progresoProcesamiento;
    }

    /**
     * @param progresoProcesamiento the progresoProcesamiento to set
     */
    public void setProgresoProcesamiento(int progresoProcesamiento) {
        this.progresoProcesamiento = progresoProcesamiento;
        controllers.ControllerPpal.getInstance().actualizaBarraProgresoProcesaImg(progresoProcesamiento);
    }

    /**
     * @return the porcentajesColores
     */
    public ArrayList<Float> getPorcentajesColores() {
        return porcentajesColores;
    }

    /**
     * @param porcentajesColores the porcentajesColores to set
     */
    public void setPorcentajesColores(ArrayList<Float> porcentajesColores) {
        this.porcentajesColores = porcentajesColores;
    }
}
