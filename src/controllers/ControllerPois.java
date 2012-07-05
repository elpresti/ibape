/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
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
            dm.addRow(agregaUnaFilaPOI(p.getId(), p.getFechaHora(), BrokerCategoriasPOI.getInstance().getCatPOIFromDB(p.getIdCategoriaPOI()).getTitulo(), p.getDescripcion(), p.getLatitud(), p.getLongitud()));
        }
        return dm;
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

    private Object[] agregaUnaFilaGenerica(int cantCols, ArrayList unObjeto) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[cantCols]; //creamos la fila
        for (int i=0;i< unObjeto.size();i++){
            if(unObjeto.get(i)!=null){
                fila[i]= unObjeto.get(i);
            }else{
                fila[i]="";
            }           
        }
        return fila;
    }

    public TableModel cargaGrillaCategoriaPOIS() {
        DefaultTableModel dm = new DefaultTableModel();
        //Cabecera
        String[] encabezado = new String[4];
        encabezado[0] = "id";
        encabezado[1] = "Nombre de la categoria";
        encabezado[2] = "Icono";
        encabezado[3] = "Acciones";
        dm.setColumnIdentifiers(encabezado);
        //Cuerpo
        for (CategoriaPoi cP : BrokerCategoriasPOI.getInstance().getCatPOISFromDB()) {
            ArrayList a= new ArrayList();
            a.add(cP.getId());
            a.add(cP.getTitulo());
            a.add(cP.getPathIcono());
            a.add("Acciones");
            dm.addRow(agregaUnaFilaGenerica(4,a));
        }
        return dm;
    }
}
