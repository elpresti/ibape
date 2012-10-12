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
public class AdministraCatPoi {
    private ArrayList<CategoriaPoi> categorias;
    static AdministraCatPoi unicaInstancia;
    //atributos de la categoria reservada "Imgs con marcas"
    private int idCatImgsConMarcas;
    private String nombreCatImgsConMarcas;
    private String iconoFileNameCatImgsConMarcas;
    //atributos de la categoria reservada "Lances"
    private int idCatLances;
    private String nombreCatLances;
    private String iconoFileNameCatLances;
    
    
    private AdministraCatPoi(){
        inicializador();
    }
    
    
    public static AdministraCatPoi getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new AdministraCatPoi();          
       }       
       return unicaInstancia;
    }     
    
    public boolean agregarCategoria(CategoriaPoi nuevaCat){
        boolean sePudo=false;
        //--------- método pendiente -------------
        return sePudo;
    }
    
    public boolean modificarCategoria(CategoriaPoi catModificada){
        boolean sePudo=false;
        //--------- método pendiente -------------
        return sePudo;
    }
    
    public boolean eliminarCategoria(CategoriaPoi catAeliminar){
        boolean sePudo=false;
        //--------- método pendiente -------------
        return sePudo;
    }    

    /**
     * @return the nombreCatImgsConMarcas
     */
    public String getNombreCatImgsConMarcas() {
        return nombreCatImgsConMarcas;
    }

    /**
     * @param nombreCatImgsConMarcas the nombreCatImgsConMarcas to set
     */
    public void setNombreCatImgsConMarcas(String nombreCatImgsConMarcas) {
        this.nombreCatImgsConMarcas = nombreCatImgsConMarcas;
    }

    /**
     * @return the idCatImgsConMarcas
     */
    public int getIdCatImgsConMarcas() {
        return idCatImgsConMarcas;
    }

    /**
     * @param idCatImgsConMarcas the idCatImgsConMarcas to set
     */
    public void setIdCatImgsConMarcas(int idCatImgsConMarcas) {
        this.idCatImgsConMarcas = idCatImgsConMarcas;
    }

    private void inicializador() {
        setIdCatImgsConMarcas(-1);
        setNombreCatImgsConMarcas("Imagenes con Marcas");
        setIconoFileNameCatImgsConMarcas("icono-cat-peces50x50.png");
        setIdCatLances(-2);
        setNombreCatLances("Lances");
        setIconoFileNameCatLances("icono-cat-red50x50.png");
    }

    /**
     * @return the iconoFileNameCatImgsConMarcas
     */
    public String getIconoFileNameCatImgsConMarcas() {
        return iconoFileNameCatImgsConMarcas;
    }

    /**
     * @param iconoFileNameCatImgsConMarcas the iconoFileNameCatImgsConMarcas to set
     */
    public void setIconoFileNameCatImgsConMarcas(String iconoFileNameCatImgsConMarcas) {
        this.iconoFileNameCatImgsConMarcas = iconoFileNameCatImgsConMarcas;
    }

    /**
     * @return the idCatLances
     */
    public int getIdCatLances() {
        return idCatLances;
    }

    /**
     * @param idCatLances the idCatLances to set
     */
    public void setIdCatLances(int idCatLances) {
        this.idCatLances = idCatLances;
    }

    /**
     * @return the nombreCatLances
     */
    public String getNombreCatLances() {
        return nombreCatLances;
    }

    /**
     * @param nombreCatLances the nombreCatLances to set
     */
    public void setNombreCatLances(String nombreCatLances) {
        this.nombreCatLances = nombreCatLances;
    }

    /**
     * @return the iconoFileNameCatLances
     */
    public String getIconoFileNameCatLances() {
        return iconoFileNameCatLances;
    }

    /**
     * @param iconoFileNameCatLances the iconoFileNameCatLances to set
     */
    public void setIconoFileNameCatLances(String iconoFileNameCatLances) {
        this.iconoFileNameCatLances = iconoFileNameCatLances;
    }

}
