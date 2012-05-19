/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;

/**
 *
 * @author Sebastian
 */
public class ControllerCampania {
    static ControllerCampania unicaInstancia;
   
    public static ControllerCampania getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerCampania();
       return unicaInstancia;
    }

    public void mostrarCampanias() {
        gui.PanelOpcCampanias.getInstance().vaciaTabla();
        if (modelo.dataManager.AdministraCampanias.getInstance().getCampanias() == null) {
            modelo.dataManager.AdministraCampanias.getInstance().leerCampaniasDeLaDB();
        }
        ArrayList<modelo.dataManager.Campania> campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();
        if (campanias != null){
            // while (), pongo cada objeto Campania en la grilla de campanias        
            int i = 0;
            while (i < campanias.size()) {
                gui.PanelOpcCampanias.getInstance().agregaUnaFilaCampania(
                        campanias.get(i).getId(),
                        campanias.get(i).getDescripcion(),
                        campanias.get(i).getBarco(),
                        campanias.get(i).getCapitan(),
                        campanias.get(i).getEstado(),
                        campanias.get(i).getFechaInicio(),
                        campanias.get(i).getFechaFin(),
                        (campanias.get(i).getFolderHistorico() != null && campanias.get(i).getFolderHistorico().length() > 0)
                        );
                i++;
            }
        }
    }

    public boolean nuevaCampania(String descripcion, String capitan, String barco){
        boolean sePudo=false;
        if ((descripcion.length()>1) && (capitan.length()>1) && (barco.length()>1)){
            modelo.dataManager.Campania campania = new modelo.dataManager.Campania();
            campania.setBarco(barco);
            campania.setFechaInicio(Calendar.getInstance().getTime());
            campania.setCapitan(capitan);
            campania.setEstado(0);
            campania.setDescripcion(descripcion);
            if (modelo.dataManager.AdministraCampanias.getInstance().agregarCampania(campania)) {
                sePudo=true;
                mostrarCampanias();
            }
        }
        return sePudo;
    }
    
    public boolean borrarCampania(int idCampania){
        boolean sePudo=false;
        if (AdministraCampanias.getInstance().eliminarCampania(AdministraCampanias.getInstance().getCampania(idCampania))){
            mostrarCampanias();            
            sePudo=true;
        }        
        return sePudo;
    }

    public boolean modificarCampania(int id, String nombre, String capitan, String barco){
        boolean sePudo=false;
        if ((nombre.length()>1) && (capitan.length()>1) && (barco.length()>1)){
            //traigo la campania vieja y le cargo los valores nuevos
            modelo.dataManager.Campania campania = modelo.dataManager.AdministraCampanias.getInstance().getCampania(id);
            campania.setBarco(barco);            
            campania.setCapitan(capitan);
            campania.setDescripcion(nombre);
            if (modelo.dataManager.AdministraCampanias.getInstance().modificarCampania(campania)) {             
                mostrarCampanias();
                sePudo=true;                
            }
        }
        return sePudo;
    }

}