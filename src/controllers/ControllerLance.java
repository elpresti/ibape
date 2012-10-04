/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelOpcLances;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dataManager.AdministraCampanias;
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
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            if (BrokerLance.getInstance().getIdLanceEnCurso() < 0) {
                if (JOptionPane.showConfirmDialog(null,
                        "Desea registrar un nuevo lance?",
                        "Registrar lances",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE) == 0) {

                    Lance unLance = new Lance();
                    setEstadoLance(1); //se controla en el gui o aca?
                    modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
                    unLance.setIdCampania(ControllerCampania.getInstance().getIdCampaniaEnCurso());
                    unLance.setPosIniLat(p.getLatitud());
                    unLance.setPosIniLon(p.getLongitud());
                    unLance.setfYHIni(p.getFechaYhora());
                    //los fin los dejo en null
                    unLance.setComentarios("");
                    agregaLance(unLance);
                    PanelOpcLances.getInstance().cargaGrillaLances();
                }
                PanelOpcLances.getInstance().setTxtBtnIniciaLance();
            } else {
                JOptionPane.showMessageDialog(null, "Primero se debe finalizar el lance en curso");
                PanelOpcLances.getInstance().setTxtBtnFinLance();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se puede registrar un lance sin estar en una campaña");
        }




        //solo muestar la pantalla, poner boton del fondo en iniciar lance    
        /*} else {
        //muestra la pantalla y el boton de abajo en finalizar
        if (JOptionPane.showConfirmDialog(null,
        "Desea finalizar el lance actual?",
        "Registrar lances",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE) == 0) {
        
        //actualizo en la base de datos y
        PanelOpcLances.getInstance().cargaGrillaLances();
        } else {
        //actualizo boton
        }
        }*/
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

    public void finalizarLance() {
        /* if (getEstadoLance() == 1) {
        Lance unLance = BrokerLance.getInstance().getLanceFromDB(BrokerLance.getInstance().getIdLanceActual());
        setEstadoLance(0);
        modelo.dataManager.Punto p = modelo.dataManager.Punto.getInstance();
        unLance.setPosFinLat(p.getLatitud());
        unLance.setPosFinLon(p.getLongitud());
        unLance.setfYHFin(p.getFechaYhora());
        //Cual es la diferencia entre guardar lance y finalizar lance?
        unLance.setComentarios(PanelOpcLances.getInstance().getComentarios());
        }*/
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
        if (!BrokerLance.getInstance().deleteLance(unLance)) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el Lance " + unLance.getId());
        } else {
            System.out.println("Lance eliminado: " + unLance.getId());
        }
    }
}
