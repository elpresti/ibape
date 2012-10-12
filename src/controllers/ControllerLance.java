/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelOpcLances;
import gui.PanelSelector;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.dataManager.Cajon;
import modelo.dataManager.Especie;
import modelo.dataManager.Lance;
import modelo.dataManager.Punto;
import persistencia.BrokerCajon;
import persistencia.BrokerEspecie;
import persistencia.BrokerLance;
import persistencia.BrokerPOIs;

/**
 *
 * @author Necrophagist
 */
public class ControllerLance {

    static ControllerLance unicaInstancia;
    //private int estadoLance; //1=en curso 0=sin lance
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

    public void iniciaLance() {
        //if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
        //  if (ControllerCampania.getInstance().getEstadoCampaniaEnCurso() == 1) { //tengo una campaña en curso
        //  if (BrokerLance.getInstance().getIdLanceEnCurso() < 0) { //tengo un lance en curso
        PanelOpcLances.getInstance().setTxtBtnIniciaLance();
        PanelSelector.getInstance().setTxtBtnIniciaLance();
        if (JOptionPane.showConfirmDialog(null,
                "Desea registrar un nuevo lance?", //<---
                "Registrar lances",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == 0) {

            Lance unLance = new Lance();
            unLance.setIdCampania(ControllerCampania.getInstance().getIdCampaniaEnCurso());
            unLance.setPosIniLat(Punto.getInstance().getLatConNegativo());
            unLance.setPosIniLon(Punto.getInstance().getLonConNegativo());
            unLance.setfYHIni(Punto.getInstance().getFechaYhora());
            //los fin los dejo en null
            unLance.setComentarios("");
            agregaLance(unLance);
            PanelOpcLances.getInstance().cargaGrillaLances();
            PanelOpcLances.getInstance().setTxtBtnFinLance();
        }
    }

    public void finalizarLance() {
        PanelOpcLances.getInstance().setTxtBtnFinLance();
        PanelSelector.getInstance().setTxtBtnFinLance();
        if (JOptionPane.showConfirmDialog(null,
                "Desea finalizar el lance en curso?", //<---
                "Finalizar lances",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == 0) {
            Lance unLance = BrokerLance.getInstance().getLanceFromDB(BrokerLance.getInstance().getIdLanceEnCurso());
            if (unLance.getfYHFin() == null) {
                unLance.setPosFinLat(Punto.getInstance().getLatConNegativo());
                unLance.setPosFinLon(Punto.getInstance().getLonConNegativo());
                unLance.setfYHFin(Punto.getInstance().getFechaYhora());
            } else {
                return;
            }
            modificaLance(unLance);
            ControllerPois.getInstance().guardaPoisDelLance(unLance);
            PanelOpcLances.getInstance().cargaGrillaLances();
            PanelOpcLances.getInstance().setTxtBtnIniciaLance();
        }

    }

    private void agregaLance(Lance unLance) {
        BrokerLance.getInstance().insertLance(unLance);
    }

    public void modificaCajon(Cajon unCajon) {
        //No se implementa porque es mas practico eliminar el cajon y crearlo devuelta
    }

    public void agregaCajon(Cajon unCajon) {
        //aca se deberia chekear que este todo bien
        if (!BrokerCajon.getInstance().insertCajon(unCajon)) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el Cajon ");
        } else {
            System.out.println("Cajon agregado");
        }
    }

    public void eliminaCajon(Cajon unCajon) {
        if (!BrokerCajon.getInstance().deleteCajon(unCajon)) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el Cajon " + unCajon.getId());
        } else {
            System.out.println("Cajon eliminado: " + unCajon.getId());
        }
    }

    public void modificaLance(Lance unLance) {
        if (!BrokerLance.getInstance().updateLance(unLance)) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el Lance " + unLance.getId());
        } else {
            System.out.println("Lance modificado: " + unLance.getId());
        }
    }

    public void eliminaLance(Lance unLance) {
        if (!BrokerLance.getInstance().deleteLance(unLance) || !BrokerPOIs.getInstance().deletePoisFromLance(unLance.getId())) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el Lance " + unLance.getId());
        } else {
            System.out.println("Lance eliminado: " + unLance.getId());
        }
    }

    public void registrarLance() {
        //supongo que se controla que estoy en curso afuera para ocultar el boton
        if (BrokerLance.getInstance().getIdLanceEnCurso() < 0) {
            iniciaLance();
        } else {
            finalizarLance();
        }
    }
}
