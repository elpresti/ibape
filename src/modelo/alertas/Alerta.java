/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

import java.util.List;

/**
 *
 * @author Martin
 */
public class Alerta {
    private static int id;
    private static boolean estado;
    private static String titulo;
    private static String mensaje;
    private static int flagsAcciones;    
    private static List<Condicion> condiciones;

    /**
     * @return the id
     */
    
    /*
    public static void main(String args[]){
        Alerta alerta=new Alerta();
    }
    */
    
    
    public int getId() {
        return id;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param aMensaje the mensaje to set
     */
    public void setMensaje(String aMensaje) {
        mensaje = aMensaje;
    }

    /**
     * @return the flagsAcciones
     */
    public int getFlagsAcciones() {
        return flagsAcciones;
    }

    /**
     * @param aFlagsAcciones the flagsAcciones to set
     */
    public void setFlagsAcciones(int aFlagsAcciones) {
        flagsAcciones = aFlagsAcciones;
    }

    /**
     * @param aId the id to set
     */
    public void setId(int aId) {
        id = aId;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param aEstado the estado to set
     */
    public void setEstado(boolean aEstado) {
        estado = aEstado;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param aTitulo the titulo to set
     */
    public void setTitulo(String aTitulo) {
        titulo = aTitulo;
    }

    /**
     * @return the condiciones
     */
    public List<Condicion> getCondiciones() {
        return condiciones;
    }

    /**
     * @param aCondiciones the condiciones to set
     */
    public void setCondiciones(List<Condicion> aCondiciones) {
        condiciones = aCondiciones;
    }
    
    public boolean agregarCondicion(Condicion c){
        return true;
    }
    public boolean modificarCondicion(Condicion c){
        return true;
    }
    public boolean eliminarCondicion(Condicion c){
        return true;
    }
}
