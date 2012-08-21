/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelFinalizarLance;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.dataManager.Cajon;
import modelo.dataManager.Especie;
import modelo.dataManager.Lance;
import persistencia.BrokerCajon;
import persistencia.BrokerCampania;
import persistencia.BrokerEspecie;
import persistencia.BrokerLance;

/**
 *
 * @author Necrophagist
 */
public class ControllerLance {

    static ControllerLance unicaInstancia;
    private int estadoLance; //1=en curso 0=sin lance
    private ArrayList<Cajon> listadoCajones = new ArrayList(); //los cajones de la campania actual, se podrian asignar a un lance o no
    public ArrayList<Especie> listadoEspecies = BrokerEspecie.getInstance().getEspeciesFromDB(); //listado de las especies guardadas, mas facil para mostrar/guardar

    public static ControllerLance getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ControllerLance();
        }
        return unicaInstancia;
    }

    public ArrayList<Especie> getListadoEspecies() {
        return listadoEspecies;
    }

    public void addCajon(Cajon unCajon) {
        getListadoCajones().add(unCajon);
    }

    public void iniciaLance() {
        if (getEstadoLance() == 0) {
            Lance unLance = new Lance();
            setEstadoLance(1); //se controla en el gui o aca?
            modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
            unLance.setIdCampania(BrokerCampania.getInstance().getIdUltimoInsert()); //id de la campania actual 
            unLance.setPosIniLat(p.getLatitud());
            unLance.setPosIniLon(p.getLongitud());
            unLance.setfYHIni(p.getFechaYhora());
        }
    }
       
    private void guardaCajones(ArrayList<Cajon> cajones, int nroLance) {
        //de donde sacaria el nro de lance al que pertenece= o selecciono un lance, recupero el id y guardo
        //los cajones o no asigno a cada lance, va todo en la campania ->lo hago sin idLance ahora
        try {
            for (Cajon i : cajones) {
                i.setIdLance(nroLance);
                BrokerCajon.getInstance().insertCajon(i);
            }
        } catch (Exception e) {
        }
    }
   
    public void guardaLance() {
        guardaCajones(getListadoCajones(), BrokerLance.getInstance().getIdLanceActual());
        
    }

    public void finalizarLance() {
        if (getEstadoLance() == 1) {
            Lance unLance = BrokerLance.getInstance().getLanceFromDB(BrokerLance.getInstance().getIdLanceActual());
            setEstadoLance(0);
            modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
            unLance.setPosFinLat(p.getLatitud());
            unLance.setPosFinLon(p.getLongitud());
            unLance.setfYHFin(p.getFechaYhora());
            //Cual es la diferencia entre guardar lance y finalizar lance?
            unLance.setComentarios(PanelFinalizarLance.getInstance().getComentarios());
        }
    }

    /**
     * @return the estadoLance
     */
    public int getEstadoLance() {
        return estadoLance;
    }

    /**
     * @param estadoLance the estadoLance to set
     */
    public void setEstadoLance(int estadoLance) {
        this.estadoLance = estadoLance;
    }

    public void borrarCajon(Cajon unCajon) {
        getListadoCajones().remove(unCajon);
    }

    /**
     * @return the listadoCajones
     */
    public ArrayList<Cajon> getListadoCajones() {
        return listadoCajones;
    }

}
