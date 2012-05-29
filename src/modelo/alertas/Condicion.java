/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;

/**
 *
 * @author Martin
 */

public class Condicion {
    private float valorMinimo;
    private float valorMaximo;
    private String relacion;
    private int id;
    private int idVariable;

    /**
     * @return the valorMinimo
     */
    public float getValorMinimo() {
        return valorMinimo;
    }

    /**
     * @param aValorMinimo the valorMinimo to set
     */
    public void setValorMinimo(float aValorMinimo) {
        valorMinimo = aValorMinimo;
    }

    /**
     * @return the valorMaximo
     */
    public float getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param aValorMaximo the valorMaximo to set
     */
    public void setValorMaximo(float aValorMaximo) {
        valorMaximo = aValorMaximo;
    }

    /**
     * @return the relacion
     */
    public String getRelacion() {
        return relacion;
    }

    /**
     * @param aRelacion the relacion to set
     */
    public void setRelacion(String aRelacion) {
        relacion = aRelacion;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the idVariable
     */
    public int getIdVariable() {
        return idVariable;
    }
}
