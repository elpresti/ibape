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
    private String fileName;
    private static UltimaImgProcesada unicaInstancia;
    
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
}
