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
public class BrokerMarca extends BrokerPpal {

    static BrokerMarca unicaInstancia;

    public static BrokerMarca getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerMarca();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Marcas");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.Marca> getMarcasFromDB() {
        ArrayList<modelo.dataManager.Marca> Marcas = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Marcas");
            while (rs.next()) {
                modelo.dataManager.Marca marca = new modelo.dataManager.Marca();
                // Get the data from the row using the column name
                marca.setId(rs.getInt("id"));
                marca.setProfundidad(rs.getDouble("profundidad"));
                marca.setAreaImagen(rs.getString("areaImagen"));

                Marcas.add(marca);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return Marcas;
    }
    
    public ArrayList<modelo.dataManager.Marca> getMarcasPOIFromDB(int idPOI) {
        ArrayList<modelo.dataManager.Marca> Marcas = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Marcas WHERE id = " + idPOI);
            while (rs.next()) {
                modelo.dataManager.Marca marca = new modelo.dataManager.Marca();
                // Get the data from the row using the column name
                marca.setId(rs.getInt("id"));
                marca.setProfundidad(rs.getDouble("profundidad"));
                marca.setAreaImagen(rs.getString("areaImagen"));

                Marcas.add(marca);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return Marcas;
    }
    public modelo.dataManager.Marca getMarcaFromDB(int id) {
        modelo.dataManager.Marca marca = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Marcas WHERE id = " + id);
            if (rs != null) {
                //modelo.dataManager.Marca marca = new modelo.dataManager.Marca();
                // Get the data from the row using the column name
                marca.setId(rs.getInt("id"));
                marca.setProfundidad(rs.getDouble("profundidad"));
                marca.setAreaImagen(rs.getString("areaImagen"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return marca;
    }

    public boolean insertMarca(modelo.dataManager.Marca marca) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            String areaImagen = null;
            if (marca.getAreaImagen()!= null) {
                areaImagen = "'" + marca.getAreaImagen() + "'";
            }
            sqlQuery = "INSERT INTO Marcas"
                    + "(areaImagen,profundidad)"
                    + "VALUES"
                    + "(" + areaImagen + "," + marca.getProfundidad() + ")";
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

    public boolean deleteMarca(modelo.dataManager.Marca marca) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM Marcas "
                    + "WHERE id = " + marca.getId();
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
