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
    private String nombreCatImgsConMarcas;
    private int idCatImgsConMarcas;
    private String iconoFileNameCatImgsConMarcas;
    
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

}
