/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.dataManager.Marca;
import modelo.dataManager.POI;
import sun.text.normalizer.Trie.DataManipulate;

/**
 *
 * @author Necrophagist
 */
public class BrokerPOIs extends BrokerPpal {

    static BrokerPOIs unicaInstancia;

    public static BrokerPOIs getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerPOIs();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Pois");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.POI> getPOISFromDB() {
        ArrayList<modelo.dataManager.POI> POIs = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Pois");
            while (rs.next()) {
                modelo.dataManager.POI poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("posLat"));
                poi.setLongitud(rs.getDouble("posLon"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("pathImg"));
                poi.setIdCampania(rs.getInt("idCampania"));
                poi.setDescripcion(rs.getString("descripcion"));

                //poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                //ver if null
                poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

                POIs.add(poi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return POIs;
    }

    public modelo.dataManager.POI getPOIFromDB(int id) {
        modelo.dataManager.POI poi = new modelo.dataManager.POI();
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Pois WHERE id = " + id);
            if (rs != null) {
                poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("posLat"));
                poi.setLongitud(rs.getDouble("posLon"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("pathImg"));
                poi.setIdCampania(rs.getInt("idCampania"));
                poi.setDescripcion(rs.getString("descripcion"));
                //poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

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
            String PathImg = "";
            if (poi.getPathImg() != null) {
                PathImg = "'" + poi.getPathImg() + "'";
            }

            String Desc = "";
            if (poi.getDescripcion() != null) {
                Desc = "'" + poi.getDescripcion() + "'";
            }

            String fechaHora = null;
            if (poi.getFechaHora() != null) {
                fechaHora = "" + poi.getFechaHora().getTime() + "";
            }

            sqlQuery = "INSERT INTO Pois "
                    + "(posLat,posLon,fechaHora,pathImg,idCategoriaPOI,IdCampania,descripcion)"
                    + " VALUES "
                    + "(" + poi.getLatitud() + "," + poi.getLongitud() + "," + fechaHora
                    + "," + PathImg + "," + poi.getIdCategoriaPOI()/*poi.getCategoria().getId()*/
                    + "," + "-1" + "," + Desc + ")";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;//sin las marcas
                if (poi.getMarcas() != null) {
                    //recuperar el id para insertar las marcas
                    sqlQuery = "SELECT max(id) from Pois";
                    System.out.println("Select: " + sqlQuery);
                    rs = getStatement().executeQuery(sqlQuery);
                    if (rs != null) {
                        int idPOI = rs.getInt("id");
                        for (Marca m : poi.getMarcas()) {
                            //if (m.getId() == 0) {
                            m.setIdPois(idPOI);
                            BrokerMarca.getInstance().insertMarca(m);
                            //}
                        }
                    }
                    sePudo = true;
                } else {
                    sePudo = false; //marcas=null
                }
            } else {
                sePudo = false;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }

    public boolean updatePOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            String PathImg = null;
            if (poi.getPathImg() != null) {
                PathImg = "'" + poi.getPathImg() + "'";
            }

            sqlQuery = "UPDATE Pois SET"
                    + "pathImg= " + PathImg + ", "
                    + "categoria=" + poi.getIdCategoriaPOI()/*poi.getCategoria().getId()*/ + ", "
                    + " WHERE "
                    + "id=" + poi.getId();
            System.out.println("update: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            } else {
                sePudo = false;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }

    public boolean deletePOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM Pois "
                    + "WHERE id = " + poi.getId();
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
}