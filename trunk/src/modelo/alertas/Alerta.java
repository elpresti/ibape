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
    
    
    public static int getId() {
        return id;
    }

    /**
     * @return the mensaje
     */
    public static String getMensaje() {
        return mensaje;
    }

    /**
     * @param aMensaje the mensaje to set
     */
    public static void setMensaje(String aMensaje) {
        mensaje = aMensaje;
    }

    /**
     * @return the flagsAcciones
     */
    public static int getFlagsAcciones() {
        return flagsAcciones;
    }

    /**
     * @param aFlagsAcciones the flagsAcciones to set
     */
    public static void setFlagsAcciones(int aFlagsAcciones) {
        flagsAcciones = aFlagsAcciones;
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
    
    public boolean agregarCondicion(String variable, String relacion, float valorMin, float valorMax){
        return true;
    }
    public boolean modificarCondicion(Condicion c){
        return true;
    }
    public boolean eliminarCondicion(Condicion c){
        return true;
    }
}
