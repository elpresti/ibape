/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import controllers.ControllerNavegacion;
import controllers.ControllerPois;
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
    private int progresoProcesamiento;
    //Progreso=-2: LECTURA DAT: error al leer el DAT
    //Progreso=-1: PROCESA IMG: error al procesar la imagen
    //Progreso= 1: LECTURA DAT: inicio el analisis del DAT y empiezo a descomprimir
    //Progreso= 2: LECTURA DAT: ya descomprimí, ahora leo el archivo por bloques
    //Progreso= 3: LECTURA DAT: fin! lectura DAT exitosa, comienza procesamiento de img
    //Progreso= 4: PROCESA IMG: cargue en memoria la img, ahora voy a analizar si es apta
    //Progreso= 5: PROCESA IMG: erosionando...
    //Progreso= 6: PROCESA IMG: binarizando...
    //Progreso= 7: PROCESA IMG: dilatando...
    //Progreso= 8: PROCESA IMG: buscando fondo...
    //Progreso= 9: PROCESA IMG: fondo encontrado, quitandolo y buscando marcas...
    //Progreso=10: PROCESA IMG: fin del correcto procesamiento de img
    //Progreso=11: PROCESA IMG: se procesó con exito y se encontraron marcas
    //Progreso=12: PROCESA IMG: imagen no apta para el procesamiento
    //Progreso=13: PROCESA IMG: se procesó con exito pero no se encontraron marcas

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
        if (marcas.size()>0){
            ControllerPois.getInstance().guardaPoiDeImgConMarcas();
        }
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
