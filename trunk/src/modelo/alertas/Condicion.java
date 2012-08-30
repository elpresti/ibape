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
    private String valorMinimo;
    private String valorMaximo;
    private String descripcion;
    private int id;
    private int idVariable;
    private int idRelacion;
    private int idAlerta;

    /**
     * @return the valorMinimo
     */
    public String getValorMinimo() {
        return valorMinimo;
    }
    
    public Condicion(int id,int idVariable,int idRelacion,String valorMinimo,String valorMaximo,String descripcion){
        
        this.id=id;
        this.idRelacion=idRelacion;
        this.idVariable=idVariable;
        this.valorMinimo=valorMinimo;
        this.valorMaximo=valorMaximo;
        this.descripcion=descripcion;
 
    }
    
    /**
     * @param aValorMinimo the valorMinimo to set
     */
    public void setValorMinimo(String aValorMinimo) {
        valorMinimo = aValorMinimo;
    }

    /**
     * @return the valorMaximo
     */
    public String getValorMaximo() {
        return valorMaximo;
    }

    /**
     * @param aValorMaximo the valorMaximo to set
     */
    public void setValorMaximo(String aValorMaximo) {
        valorMaximo = aValorMaximo;
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

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the idRelacion
     */
    public int getIdRelacion() {
        return idRelacion;
    }

    /**
     * @param idRelacion the idRelacion to set
     */
    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }

    /**
     * @return the idAlerta
     */
    public int getIdAlerta() {
        return idAlerta;
    }

    /**
     * @param idAlerta the idAlerta to set
     */
    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
    }

    /**
     * @param idVariable the idVariable to set
     */
    public void setIdVariable(int idVariable) {
        this.idVariable = idVariable;
    }
}
