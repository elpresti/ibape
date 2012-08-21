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
public class BrokerCajon extends BrokerPpal {

    static BrokerCajon unicaInstancia;

    public static BrokerCajon getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerCajon();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Cajones");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.Cajon> getCajonesFromDB() {
        ArrayList<modelo.dataManager.Cajon> cajones = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Cajones");
            while (rs.next()) {
                modelo.dataManager.Cajon cajon = new modelo.dataManager.Cajon();
                // Get the data from the row using the column name
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));

                cajones.add(cajon);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return cajones;
    }

    public ArrayList<modelo.dataManager.Cajon> getCajonesLanceFromDB(int idLance) {
        ArrayList<modelo.dataManager.Cajon> cajones = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Cajones WHERE idLance = " + idLance);
            while (rs.next()) {
                modelo.dataManager.Cajon cajon = new modelo.dataManager.Cajon();
                // Get the data from the row using the column name
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));
                cajones.add(cajon);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return cajones;
    }

    public modelo.dataManager.Cajon getCajonFromDB(int id) {
        modelo.dataManager.Cajon cajon = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Cajones WHERE id = " + id);
            if (rs.next()) {
                // Get the data from the row using the column name
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return cajon;
    }

    public boolean insertCajon(modelo.dataManager.Cajon cajon) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            sqlQuery = "INSERT INTO Cajones "
                    + "(idLance, idEspecie, cantidad) "
                    + "VALUES "
                    + "(" + cajon.getIdLance() + "," + cajon.getIdEspecie() + "," + cajon.getCantidad() + ")";
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

    public boolean deleteCajon(modelo.dataManager.Cajon cajon) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM CategoriasPoi "
                    + "WHERE idLance= " + cajon.getIdLance() + "AND idEspecie= " + cajon.getIdEspecie();
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }

        return sePudo;
    }
//Sin update, borrar y agregar solamente
}
