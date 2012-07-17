/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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

    private Object[] agregaUnaFilaPOI(int id, Date fechaHora, String categoria, String descripcion, double latitud, double longitud) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[6]; //creamos la fila
        fila[0] = String.valueOf(id); //ver el override de tosring y donde se guarda el objeto
        if (fechaHora != null) {
            fila[1] = sdf.format(fechaHora);
        }
        fila[2] = categoria;
        String coordenadas = String.valueOf(latitud) + "," + String.valueOf(longitud);
        fila[3] = coordenadas;
        fila[4] = descripcion;
        fila[5] = "";

        return fila;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> cargaCategoriasPOI() {
        return persistencia.BrokerCategoriasPOI.getInstance().getCatPOISFromDB();
    }

    public void agregaPOI(int idCategoriaPOI, String descripcion) {
        modelo.dataManager.POI p = new POI();
        p.setFechaHora(Punto.getInstance().getFechaYhora());
        p.setIdCategoriaPOI(idCategoriaPOI);
        p.setLatitud(Punto.getInstance().getLatitud());
        p.setLongitud(Punto.getInstance().getLongitud());
        p.setDescripcion(descripcion);
        p.setMarcas(null);
        p.setPathImg(null);

        BrokerPOIs.getInstance().insertPOI(p);
    }

    public void agregaCategoriaPOI(String titulo) {
        modelo.dataManager.CategoriaPoi cP = new CategoriaPoi();
        cP.setTitulo(titulo);
        cP.setPathIcono("ver null en el broker");
        BrokerCategoriasPOI.getInstance().insertCategoriaPOI(cP);
    }
}
