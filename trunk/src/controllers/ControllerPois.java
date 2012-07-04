/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.dataManager.POI;
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

    private Object[] agregaUnaFilaPOI(int id, String categoria, double latitud, double longitud, Date fechaHora) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[6]; //creamos la fila

        fila[0] = String.valueOf(id);

        if (fechaHora != null) {
            fila[1] = sdf.format(fechaHora);
        }
        fila[2] = categoria;
        String coordenadas = String.valueOf(latitud) + "," + String.valueOf(longitud);
        fila[3] = coordenadas;
        fila[4] = "no esta en el modelo";

        return fila;
    }

    public DefaultTableModel cargaGrillaPOIS() {
      DefaultTableModel dm = new DefaultTableModel();
        //Cabecera
        String[] encabezado = new String[6];
        encabezado[0] = "id";
        encabezado[1] = "Fecha y Hora";
        encabezado[2] = "Categoria";
        encabezado[3] = "Coordenadas";
        encabezado[4] = "Descripcion";
        encabezado[5] = "Acciones";       
        dm.setColumnIdentifiers(encabezado);  
        //Cuerpo
        for (POI p : BrokerPOIs.getInstance().getPOISFromDB()) {
            dm.addRow(agregaUnaFilaPOI(p.getId(), p.getCategoria().getTitulo(), p.getLatitud(), p.getLongitud(), p.getFechaHora()));
        }
      return dm;
    }
}
