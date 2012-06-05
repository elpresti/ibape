/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Necrophagist
 */
public class BrokerCategoriaPOI extends BrokerPpal {

    static BrokerCategoriaPOI unicaInstancia;

    public static BrokerCategoriaPOI getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerCategoriaPOI();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE CategoriaPoi");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> getCatPOISFromDB() {
        ArrayList<modelo.dataManager.CategoriaPoi> CatPOIS = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM CategoriaPoi");
            while (rs.next()) {
                modelo.dataManager.CategoriaPoi catPoi = new modelo.dataManager.CategoriaPoi();
                // Get the data from the row using the column name
                catPoi.setId(rs.getInt("id"));
                catPoi.setPathIcono(rs.getString("pathIcono"));
                catPoi.setTitulo(rs.getString("titulo"));

                CatPOIS.add(catPoi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return CatPOIS;
    }

    public modelo.dataManager.CategoriaPoi getCatPOIFromDB(int id) {
        modelo.dataManager.CategoriaPoi CatPOI = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM POI WHERE id = " + id);
            if (rs != null) {
                modelo.dataManager.CategoriaPoi catPoi = new modelo.dataManager.CategoriaPoi();
                // Get the data from the row using the column name
                catPoi.setId(rs.getInt("id"));
                catPoi.setPathIcono(rs.getString("pathIcono"));
                catPoi.setTitulo(rs.getString("titulo"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return CatPOI;
    }

    public boolean insertCategoriaPOI(modelo.dataManager.CategoriaPoi categoriaPOI) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            String titulo = null;
            if (categoriaPOI.getTitulo() != null) {
                titulo = "" + categoriaPOI.getTitulo() + "";
            }
            String pathIcono = null;
            if (categoriaPOI.getPathIcono() != null) {
                pathIcono = "" + categoriaPOI.getPathIcono() + "";
            }

            sqlQuery = "INSERT INTO CategoriaPoi"
                    + "(titulo,pathIcono)"
                    + "VALUES"
                    + "(" + titulo + "," + pathIcono + ")";
            System.out.println("Insert: " + sqlQuery);
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

    public boolean deleteCategoriaPOI(modelo.dataManager.CategoriaPoi categoriaPOI) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM CategoriaPoi "
                    + "WHERE id = " + categoriaPOI.getId();
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }

        return sePudo;
    }

    public boolean updateCategoriaPOI(modelo.dataManager.CategoriaPoi categoriaPOI) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            sqlQuery = "Update CategoriaPoi "
                    + "SET titulo =" + categoriaPOI.getTitulo() + ", "
                    + "pathIcono =" + categoriaPOI.getPathIcono() + ", "
                    + " WHERE "
                    + "id=" + categoriaPOI.getId();
            System.out.println("Insert: " + sqlQuery);
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
}
