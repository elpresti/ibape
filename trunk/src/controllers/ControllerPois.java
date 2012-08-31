/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import modelo.dataManager.POI;
import modelo.dataManager.Punto;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerPOIs;

/**
 *
 * @author Necrophagist
 */
public class ControllerPois {

    static ControllerPois unicaInstancia;

    public static ControllerPois getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ControllerPois();
        }
        return unicaInstancia;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> cargaCategoriasPOI() {
        return persistencia.BrokerCategoriasPOI.getInstance().getCatPOISFromDB();
    }

    public void agregaPOI(int idCategoriaPOI, String descripcion, double latitud, double longitud, String pathImgSonda) {
            modelo.dataManager.POI p = new POI();
            p.setFechaHora(Calendar.getInstance().getTime());//fecha y hora actual
            p.setIdCategoriaPOI(idCategoriaPOI);
            p.setLatitud(latitud);
            p.setLongitud(longitud);
            p.setDescripcion(descripcion);
            p.setMarcas(null);//ver
            p.setPathImg("editar en controllerPOI -pathImgSonda");//ver
            p.setIdCampania(AdministraCampanias.getInstance().getCampaniaEnCurso().getId());
            BrokerPOIs.getInstance().insertPOI(p);
    }

    public void agregaCategoriaPOI(String titulo, String path) {
        modelo.dataManager.CategoriaPoi cP = new CategoriaPoi();
        cP.setTitulo(titulo);
        cP.setPathIcono(path);
        BrokerCategoriasPOI.getInstance().insertCategoriaPOI(cP);
    }

    public void eliminaPOI(POI unPoi) {
        if (!BrokerPOIs.getInstance().deletePOI(unPoi)) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el POI " + unPoi.getId());
        } else {
            System.out.println("Poi eliminado: " + unPoi.getId());
        }
    }

    public void modificaPOI(POI unPoi) {
        if (!BrokerPOIs.getInstance().updatePOI(unPoi)) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar el POI " + unPoi.getId());
        } else {
            System.out.println("Poi modificado: " + unPoi.getId());
        }
    }

    public void eliminaCategoriaPOI(CategoriaPoi unaCategoriaPoi) {
        if (!BrokerCategoriasPOI.getInstance().deleteCategoriaPOI(unaCategoriaPoi)) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar la categoria POI " + unaCategoriaPoi.getId());
        } else {
            System.out.println("CatPoi eliminado: " + unaCategoriaPoi.getId());
        }
    }

    public boolean isCategoriaPOILibre(int idCategoriaPOI) {
        return BrokerPOIs.getInstance().getPOISFromDBSegunCat(idCategoriaPOI).isEmpty();
    }
        public ArrayList<String> listadoIconosCatPOI() {
        String rutaIconos = modelo.dataCapture.Sistema.getInstance().getRutaIconosCatPois();

        File dir = new File(rutaIconos);
        ArrayList<String> listRutaIconos = new ArrayList();
        String[] chld = dir.list();

        //filtrado
        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith("png");
            }
        };
        chld = dir.list(filter);

        if (chld == null) {
            System.out.println("La ruta no existe o no se puede acceder.");
        } else {
            for (int i = 0; i < chld.length; i++) {
                listRutaIconos.add(rutaIconos + chld[i]);
            }
        }
        return listRutaIconos;
    }

    public void modificaCategoriaPOI(CategoriaPoi unaCatPOI) {
        if (!BrokerCategoriasPOI.getInstance().updateCategoriaPOI(unaCatPOI)) {
            JOptionPane.showMessageDialog(null, "No se pudo modificar la categoria POI " + unaCatPOI.getId());
        } else {
            System.out.println("CatPoi modificada: " + unaCatPOI.getId());
        }
    }
}
