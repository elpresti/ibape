/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
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

        fila[0] = id;

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
        DefaultTableModel modelo = new DefaultTableModel();
//Agregar encabezados
        //Carga titulos de las columnas
        Object[] cabecera = new Object[6];
        cabecera[0] = "id";
        cabecera[1] = "Fecha y Hora";
        cabecera[2] = "Categoria";
        cabecera[3] = "Coordenadas";
        cabecera[4] = "Descripcion";
        cabecera[5] = "Acciones";
        modelo.addRow(cabecera);

        /*NRO_COL_ID_CAMP=0;
        NRO_COL_FECHA_INI=1;
        NRO_COL_FECHA_FIN=2;
        NRO_COL_DURACION=3;
        NRO_COL_NOMBRE_CAMP=4;
        NRO_COL_BARCO=5;
        NRO_COL_CAPITAN=6;
        NRO_COL_ACCIONES=7;
        NRO_COL_TIENE_HISTORICO=8;
        cantColumnas=9; */
        for (POI p : BrokerPOIs.getInstance().getPOISFromDB()) {
            modelo.addRow(agregaUnaFilaPOI(p.getId(), p.getCategoria().getTitulo(), p.getLatitud(), p.getLongitud(), p.getFechaHora()));
        }
        
        return modelo;
    }
}
