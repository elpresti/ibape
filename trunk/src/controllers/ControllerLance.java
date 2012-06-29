/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelFinalizarLance;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dataManager.Cajon;
import modelo.dataManager.Lance;
import persistencia.BrokerCajon;
import persistencia.BrokerCampania;
import persistencia.BrokerLance;
import persistencia.Logueador;

/**
 *
 * @author Necrophagist
 */
public class ControllerLance {

    static ControllerLance unicaInstancia;

    public static ControllerLance getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ControllerLance();
        }
        return unicaInstancia;
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

    public void iniciaLance() {
        Lance unLance = new Lance();
        unLance.setEstadoLance(1); //se controla en el gui o aca?
        modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
        unLance.setIdCampania(BrokerCampania.getInstance().getIdUltimoInsert()); //id de la campania actual 
        unLance.setPosIniLat(p.getLatitud());
        unLance.setPosIniLon(p.getLongitud());
        unLance.setfYHIni(p.getFechaYhora());
    }

    public void guardaLance() {
        guardaCajones(PanelFinalizarLance.getInstance().getCajones(), BrokerLance.getInstance().getIdLanceActual());
    }

    public void finalizarLance() {
        /*this.setEstadoLance(0);
        modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
        this.setPosFinLat(p.getLatitud());
        this.setPosFinLon(p.getLongitud());
        this.setfYHFin(p.getFechaYhora());
        this.setComentarios("obtener de panelfinalizalance");*/
    }
}
