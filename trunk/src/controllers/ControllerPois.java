/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.AdministraCatPoi;
import modelo.dataManager.CategoriaPoi;
import modelo.dataManager.Lance;
import modelo.dataManager.Marca;
import modelo.dataManager.POI;
import modelo.dataManager.UltimaImgProcesada;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerPOIs;
import persistencia.Logueador;

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

    public void agregaPOI(int idCategoriaPOI, String descripcion, double latitud, double longitud, String pathImgSonda, java.util.Date fechaYhora) {
        modelo.dataManager.POI p = new POI();
        //p.addObserver(controllers.ControllerAlertas.getInstance()); //Modulo Alertas
        if (fechaYhora == null) {
            p.setFechaHora(Calendar.getInstance().getTime());//fecha y hora actual
        } else {
            p.setFechaHora(fechaYhora);
        }
        p.setIdCategoriaPOI(idCategoriaPOI);
        p.setLatitud(latitud);
        p.setLongitud(longitud);
        p.setDescripcion(descripcion);
        p.setMarcas(null);//ver
        p.setPathImg(null);//editar en controllerPOI -pathImgSonda-->ver
        p.setIdCampania(AdministraCampanias.getInstance().getCampaniaEnCurso().getId());
        BrokerPOIs.getInstance().insertPOI(p);
    }

    public void agregaCategoriaPOI(String titulo, String path) {
        modelo.dataManager.CategoriaPoi cP = new CategoriaPoi();
        cP.setTitulo(titulo);
        cP.setPathIcono(path);
        BrokerCategoriasPOI.getInstance().insertCategoriaPOI(cP);
    }

    public void agregaCategoriaPOI(int id, String titulo, String path) {
        modelo.dataManager.CategoriaPoi cP = new CategoriaPoi();
        cP.setId(id);
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

        for (int i = 1; i < chld.length; i++) { //todos a minusculas
            chld[i] = chld[i].toLowerCase();
        }

        //filtrado
        FilenameFilter filterInicio = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.startsWith("icono-cat-");
            }
        };
        FilenameFilter filterFin = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith("png");
            }
        };
        chld = dir.list(filterFin);
        chld = dir.list(filterInicio);

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

    public void actualizarCategoria(CategoriaPoi categoriaActual, CategoriaPoi categoriaNueva) {
        //tendria que ser una transaccion
        for (POI unPOI : BrokerPOIs.getInstance().getPOISFromDBSegunCat(categoriaActual.getId())) {
            unPOI.setIdCategoriaPOI(categoriaNueva.getId());
            ControllerPois.getInstance().modificaPOI(unPOI);
        }
    }

    public boolean existeCategoria(int id) {
        boolean encontro = false;
        ArrayList<modelo.dataManager.CategoriaPoi> categorias = cargaCategoriasPOI();
        int i = 0;
        while (i < categorias.size() && !encontro) {
            if (categorias.get(i).getId() == id) {
                encontro = true;
            } else {
                i++;
            }
        }
        return encontro;
    }

    public boolean guardaPoiDeImgConMarcas() {
        //intentará guardar un POI de img con marcas
        boolean sePudo = false;
        try {
            if (!ControllerPois.getInstance().existeCategoria(AdministraCatPoi.getInstance().getIdCatImgsConMarcas())) {
                ControllerPois.getInstance().agregaCategoriaPOI(AdministraCatPoi.getInstance().getIdCatImgsConMarcas(), AdministraCatPoi.getInstance().getNombreCatImgsConMarcas(), AdministraCatPoi.getInstance().getIconoFileNameCatImgsConMarcas());
            }
            Marca ultimaMarca = UltimaImgProcesada.getInstance().getMarcas().get(UltimaImgProcesada.getInstance().getMarcas().size() - 1);
            String descripcionPoi = "<datosImgProcesada>";
            descripcionPoi += "<cantMarcas nombre=\"Marcas encontradas: \" valor=\"" + UltimaImgProcesada.getInstance().getMarcas().size() + "\" />";
            String rutaImg = AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual() + "\\";
            rutaImg += UltimaImgProcesada.getInstance().getFileName();
            descripcionPoi += "<imgFileName nombre=\"Imagen sin procesar: \" valor=\"" + rutaImg + "\" />";
            descripcionPoi += "</datosImgProcesada>";
            ControllerPois.getInstance().agregaPOI(AdministraCatPoi.getInstance().getIdCatImgsConMarcas(), descripcionPoi, ultimaMarca.getLatitud(), ultimaMarca.getLongitud(), null, ultimaMarca.getFechaYhora());
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("guardaPoiDeImgConMarcas(): " + e.toString());
        }
        return sePudo;
    }

    public boolean guardaPoisDelLance(Lance unLance) {
        //intentará guardar un POI de un Lance finalizado
        boolean sePudo = false;
        try {
            if (!ControllerPois.getInstance().existeCategoria(AdministraCatPoi.getInstance().getIdCatLances())) {
                ControllerPois.getInstance().agregaCategoriaPOI(AdministraCatPoi.getInstance().getIdCatLances(), AdministraCatPoi.getInstance().getNombreCatLances(), AdministraCatPoi.getInstance().getIconoFileNameCatLances());
            }
            //primero inserto el POI de INICIO del lance, junto con sus datos
            String descripcionPoi =
                    "<datosLance>";
            descripcionPoi += "<tituloLance nombre=\"&lt;br&gt;AQUI SE LANZÓ LA RED\" valor=\"\" />";
            descripcionPoi += "<finLance nombre=\"Estado de Lance\" valor=\"false\" />";
            descripcionPoi += "<idLance nombre=\"Numero de Lance\" valor=\"" + unLance.getId() + "\" />";
            descripcionPoi += "</datosLance>";
            ControllerPois.getInstance().agregaPOI(AdministraCatPoi.getInstance().getIdCatLances(), descripcionPoi, unLance.getPosIniLat(), unLance.getPosIniLon(), null, unLance.getfYHIni());
            //luego inserto el POI de FIN del lance, junto con sus datos
            descripcionPoi = "<datosLance>";
            descripcionPoi += "<tituloLance nombre=\"&lt;br&gt;AQUI SE RECOGIÓ LA RED\" valor=\"\" />";
            descripcionPoi += "<finLance nombre=\"Estado de Lance\" valor=\"true\" />";
            descripcionPoi += "<idLance nombre=\"Numero de Lance\" valor=\"" + unLance.getId() + "\" />";
            descripcionPoi += "</datosLance>";
            ControllerPois.getInstance().agregaPOI(AdministraCatPoi.getInstance().getIdCatLances(), descripcionPoi, unLance.getPosFinLat(), unLance.getPosFinLon(), null, unLance.getfYHFin());
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("guardaPoisDelLance(): " + e.toString());
        }
        return sePudo;
    }
}
