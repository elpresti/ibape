/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

/**
 *
 * @author Martin
 */
public class Variable {
    private static String nombre;
    private static String opcionesCombo;
    private static int cantValores;

    /**
     * @return the nombre
     */
    public static String getNombre() {
        return nombre;
    }

    /**
     * @param aNombre the nombre to set
     */
    public static void setNombre(String aNombre) {
        nombre = aNombre;
    }

    /**
     * @return the opcionesCombo
     */
    public static String getOpcionesCombo() {
        return opcionesCombo;
    }

    /**
     * @param aOpcionesCombo the opcionesCombo to set
     */
    public static void setOpcionesCombo(String aOpcionesCombo) {
        opcionesCombo = aOpcionesCombo;
    }

    /**
     * @return the cantValores
     */
    public static int getCantValores() {
        return cantValores;
    }

    /**
     * @param aCantValores the cantValores to set
     */
    public static void setCantValores(int aCantValores) {
        cantValores = aCantValores;
    }
}
