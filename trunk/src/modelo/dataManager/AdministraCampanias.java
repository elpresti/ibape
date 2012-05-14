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
public class AdministraCampanias {
    static AdministraCampanias unicaInstancia;
    private Campania campaniaEnCurso;
    private ArrayList<Campania> campanias;
    
    private AdministraCampanias(){        
    }
    
    
    public static AdministraCampanias getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new AdministraCampanias();          
       }       
       return unicaInstancia;
    }     
    
    public boolean agregarCampania(Campania nuevaCamp){
        boolean sePudo=false;
        // --------- método pendiente -----------
        return sePudo;
    }
    
    public boolean modificarCampania(Campania campAmodificar){
        boolean sePudo=false;
        // --------- método pendiente -----------
        return sePudo;
    }
    
    public boolean eliminarCampania(Campania campAeliminar){
        boolean sePudo=false;
        // --------- método pendiente -----------
        return sePudo;
    }
    
    public boolean finalizarCampaniaEnCurso(){
        boolean sePudo=false;
        // --------- método pendiente -----------
        return sePudo;
    }   

    /**
     * @return the campanias
     */
    public ArrayList<Campania> getCampanias() {
        return campanias;
    }

    /**
     * @param campanias the campanias to set
     */
    public void setCampanias(ArrayList<Campania> campanias) {
        this.campanias = campanias;
    }

    public boolean leerCampaniasDelDisco(){
        boolean sePudo=false;
        // metodo pendiente que lee las campanias del disco, transforma cada
        // una en un objeto de tipo Campania y la agrega al ArrayList "campanias"
        return sePudo;
    }
    
}
