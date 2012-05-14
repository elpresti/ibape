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
    
    private AdministraCatPoi(){        
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
}
