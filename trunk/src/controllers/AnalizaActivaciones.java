/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import persistencia.Logueador;

/**
 *
 * @author Martin
 */
public class AnalizaActivaciones implements Runnable{
    Thread thAa;
    private int index;
    public void run() { 
        try{
            if (index==ControllerAlertas.getInstance().getIndexProfundidad()){
                ControllerAlertas.getInstance().analizaActivacionesProfundidad(); 
            }else if (index==ControllerAlertas.getInstance().getIndexLatitud()){
                ControllerAlertas.getInstance().analizaActivacionesLatitud();
            }else if (index==ControllerAlertas.getInstance().getIndexLongitud()){
                ControllerAlertas.getInstance().analizaActivacionesLongitud();
            }else if (index==ControllerAlertas.getInstance().getIndexVelocidad()){
                ControllerAlertas.getInstance().analizaActivacionesVelocidad();
            }else if (index==ControllerAlertas.getInstance().getIndexRumbo()){
                ControllerAlertas.getInstance().analizaActivacionesRumbo();
            }else if (index==ControllerAlertas.getInstance().getIndexVelocidadAgua()){
                ControllerAlertas.getInstance().analizaActivacionesvelocidadAgua();
            }else if (index==ControllerAlertas.getInstance().getIndexTempAgua()){
                ControllerAlertas.getInstance().analizaActivacionesTempAgua();
            }else if (index==ControllerAlertas.getInstance().getIndexFechaYhora()){
                ControllerAlertas.getInstance().analizaActivacionesFechaYhora();
            }            
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("Clase AnalizaActivaciones: "+e.toString());
        }        
        thAa = null;
    }
    public void start() {
        if (thAa == null) {
            thAa = new Thread(this);
            thAa.setPriority(Thread.MIN_PRIORITY);
            thAa.start();
        }
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getImgFileName() {
        return index;
    }
}
