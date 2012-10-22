/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelOpcLances;
import gui.PanelSelector;
import gui.VentanaIbape;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.Cajon;
import modelo.dataManager.Especie;
import modelo.dataManager.Lance;
import modelo.dataManager.Punto;
import persistencia.BrokerCajon;
import persistencia.BrokerEspecie;
import persistencia.BrokerLance;
import persistencia.BrokerPOIs;
import persistencia.Logueador;

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
        // if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {

        PanelOpcLances.getInstance().setTxtBtnIniciaLance();
        PanelSelector.getInstance().setTxtBtnIniciaLance();
        if (JOptionPane.showConfirmDialog(null,
                "Desea registrar un nuevo lance?", //<---
                "Registrar lances",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == 0) {
            PanelOpcLances.getInstance().cargaCamposLatLongInicio();
            Lance unLance = new Lance();
            unLance.setIdCampania(ControllerCampania.getInstance().getIdCampaniaEnCurso());
            if (Punto.getInstance().getLatConNegativo() == 0 || !(Punto.getInstance().getLatConNegativo() >= -90 && Punto.getInstance().getLatConNegativo() <= 90)) {
                JOptionPane.showMessageDialog(null, "NO GPS-La latitud debe estar entre -90 y 90, y no puede ser igual a 0");
                return;
            } else {
                unLance.setPosIniLat(Punto.getInstance().getLatConNegativo());
            }
            if (Punto.getInstance().getLonConNegativo() == 0 || !(Punto.getInstance().getLonConNegativo() >= -180 && Punto.getInstance().getLonConNegativo() <= 180)) {
                JOptionPane.showMessageDialog(null, "NO GPS-La longitud debe estar entre -180 y 180, y no puede ser igual a 0");
                return;
            } else {
                unLance.setPosIniLon(Punto.getInstance().getLonConNegativo());
            }
            unLance.setfYHIni(Punto.getInstance().getFechaYhora());
            //los fin los dejo en null
            unLance.setComentarios("");
            agregaLance(unLance);
            PanelOpcLances.getInstance().cargaGrillaLances();
            PanelOpcLances.getInstance().setTxtBtnFinLance();
            PanelSelector.getInstance().setTxtBtnFinLance();
        }
        /*} else {
        JOptionPane.showMessageDialog(null, "No se pueden registrar lances sin estar en una campaña");
        }*/
    }

    public void finalizarLance() {
        // if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
        PanelOpcLances.getInstance().setTxtBtnFinLance();
        PanelSelector.getInstance().setTxtBtnFinLance();
        if (JOptionPane.showConfirmDialog(null,
                "Desea finalizar el lance en curso?", //<---
                "Finalizar lances",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE) == 0) {
            PanelOpcLances.getInstance().cargaCamposLatLongFin();
            Lance unLance = BrokerLance.getInstance().getLanceFromDB(BrokerLance.getInstance().getIdLanceEnCurso());
            if (unLance.getfYHFin() == null) {
                if (Punto.getInstance().getLatConNegativo() == 0 || !(Punto.getInstance().getLatConNegativo() >= -90 && Punto.getInstance().getLatConNegativo() <= 90)) {
                    JOptionPane.showMessageDialog(null, "NO GPS-La latitud debe estar entre -90 y 90, y no puede ser igual a 0");
                    return;
                } else {
                    unLance.setPosFinLat(Punto.getInstance().getLatConNegativo());
                }
                if (Punto.getInstance().getLonConNegativo() == 0 || !(Punto.getInstance().getLonConNegativo() >= -180 && Punto.getInstance().getLonConNegativo() <= 180)) {
                    JOptionPane.showMessageDialog(null, "NO GPS-La longitud debe estar entre -180 y 180, y no puede ser igual a 0");
                    return;
                } else {
                    unLance.setPosFinLon(Punto.getInstance().getLonConNegativo());
                }
                unLance.setfYHFin(Punto.getInstance().getFechaYhora());
            } else {
                return;
            }
            modificaLance(unLance);
            ControllerPois.getInstance().guardaPoisDelLance(unLance);
            PanelOpcLances.getInstance().cargaGrillaLances();
            PanelOpcLances.getInstance().setTxtBtnIniciaLance();
            PanelSelector.getInstance().setTxtBtnIniciaLance();
        }
        /* } else {
        JOptionPane.showMessageDialog(null, "No se pueden finalizar lances sin estar en una campaña");
        }*/
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
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            PanelOpcLances.getInstance().setTempCampania(AdministraCampanias.getInstance().getCampaniaEnCurso());
            PanelOpcLances.getInstance().modBtnAdmLancesDeUnaCamp(false);
            if (BrokerLance.getInstance().getIdLanceEnCurso() < 0) {
                iniciaLance();
            } else {
                finalizarLance();
            }
        } else {
            PanelOpcLances.getInstance().setTempCampania(null);
            PanelOpcLances.getInstance().cargaGrillaLances();
            JOptionPane.showMessageDialog(null, "No se pueden registrar lances directos sin estar en una campaña");
        }

    }

    public void cierraAppMientrasHayLance() {
        //supongo que se controla que estoy en curso afuera para ocultar el boton
        if (BrokerLance.getInstance().getIdLanceEnCurso() > 0) {
            /* if (JOptionPane.showConfirmDialog(null,
            "Se detecto un lance en curso ¿Desea finalizarlo?", //<---
            "Finalizar lance",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE) == 0) {*/
            finalizarLance();
            /* }*/
        }
    }

    public void agregaLance(int idCamp, Date fechaInicio, Date fechaFin, Double latIni, Double lonIni, Double latFin, Double lonFin, String comentarios) {
        Lance unLance = new Lance();
        unLance.setIdCampania(idCamp);
        unLance.setComentarios(comentarios);
        unLance.setfYHIni(fechaInicio);
        unLance.setfYHFin(fechaFin);
        unLance.setPosIniLat(latIni);
        unLance.setPosIniLon(lonIni);
        unLance.setPosFinLat(latFin);
        unLance.setPosFinLon(lonFin);

        BrokerLance.getInstance().insertLance(unLance);
    }

    public boolean modificaLancesDeCampania(int idDeCampaniaSeleccionada) {
        boolean sePudo = false;
        try {
            if (idDeCampaniaSeleccionada >= 0) {
                modelo.dataManager.Campania campElegida = AdministraCampanias.getInstance().getCampania(idDeCampaniaSeleccionada);
                VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcLances.getInstance());
                PanelOpcLances.getInstance().modBtnAdmLancesDeUnaCamp(true);
                PanelOpcLances.getInstance().setTempCampania(campElegida);
                PanelOpcLances.getInstance().cargaGrillaLances();
                sePudo = true;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("modificaLancesDeCampania(): " + e.toString());
        }
        return sePudo;
    }
}
