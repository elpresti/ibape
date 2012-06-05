/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dataManager.Marca;

/**
 *
 * @author Necrophagist
 */
public class BrokerPOI extends BrokerPpal {

    static BrokerPOI unicaInstancia;

    public static BrokerPOI getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerPOI();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE POI");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.POI> getPOISFromDB() {
        ArrayList<modelo.dataManager.POI> POIs = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM POI");
            while (rs.next()) {
                modelo.dataManager.POI poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("latitud"));
                poi.setLongitud(rs.getDouble("longitud"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("patImg"));

                poi.setCategoria(BrokerCategoriaPOI.getInstance().getCatPOIFromDB(rs.getInt("categoria")));
                poi.setMarcas(BrokerMarca.getInstance().getMarcasFromDB());

                POIs.add(poi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return POIs;
    }

    public modelo.dataManager.POI getPOIFromDB(int id) {
        modelo.dataManager.POI poi = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM POI WHERE id = " + id);
            if (rs != null) {
                poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("latitud"));
                poi.setLongitud(rs.getDouble("longitud"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("patImg"));

                poi.setCategoria(BrokerCategoriaPOI.getInstance().getCatPOIFromDB(rs.getInt("categoria")));
                poi.setMarcas(BrokerMarca.getInstance().getMarcasFromDB());

            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return poi;
    }

    public boolean insertPOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            /*
            String titulo = null;
            if (categoriaPOI.getTitulo() != null) {
            titulo = "" + categoriaPOI.getTitulo() + "";
            }
             */ //No veo para que esta

            sqlQuery = "INSERT INTO POI"
                    + "(latitud,longitud,fechaHora,pathImg,categoria)"
                    + "VALUES"
                    + "(" + poi.getLatitud() + "," + poi.getLongitud() + "," + poi.getFechaHora()
                    + "," + poi.getPathImg() + "," + poi.getCategoria().getId()
                    + ")";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;//sin las marcas
                //recuperar el id para insertar las marcas
                sqlQuery = "SELECT max(id) from POI";
                System.out.println("Select: " + sqlQuery);
                rs = getStatement().executeQuery(sqlQuery);
                if (rs != null) {
                    int idPOI = rs.getInt("id");

                    for (Marca m : poi.getMarcas()) {
                        if (m.getId() == 0) {
                            /*sqlQuery = "INSERT INTO Marca"
                                    + "(idPOI,valor1,valor2,relacion)"
                                    + "VALUES"
                                    + "(" + c.getIdVariable() + "," + c.getValorMinimo() + "," + c.getValorMaximo() + "," + c.getRelacion() + ")";
                            System.out.println("Insert: " + sqlQuery);*/
                            m.setId(idPOI);
                            BrokerMarca.getInstance().insertMarca(m); //va bien esto? me parece mejor
                        }
                    }

                    sePudo = true;

                } else {
                    sePudo = false;
                }


            } else {
                sePudo = false;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
}
