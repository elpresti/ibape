/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 */
public class ControllerCampania {
    static ControllerCampania unicaInstancia;
    
    
    
    
    public boolean nuevaCampania(String nombre, String capitan, String barco){
        boolean sePudo=false;
        // ----------- metodo pendiente ------------
        if ((nombre.length()>1) && (capitan.length()>1) && (barco.length()>1)){
            modelo.dataManager.Campania campania = new modelo.dataManager.Campania();
            campania.setBarco(barco);
            campania.setCapitan(capitan);
            campania.setEstado(0);
            campania.setNombre(nombre);
            if (!(modelo.dataManager.AdministraCampanias.getInstance().agregarCampania(campania))) {
                String mensaje = "Hubo un error al guardar la campaña y no se iniciará";
                //JOptionPane.showMessageDialog(null, mensaje);
                persistencia.Logueador.getInstance().agregaAlLog(mensaje);
            }
            else
                { sePudo=true;  }
        }
        return sePudo;
    }

    public static ControllerCampania getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerCampania();
       return unicaInstancia;
    }



}