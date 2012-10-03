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
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Cajones");
            while (rs.next()) {
                modelo.dataManager.Cajon cajon = new modelo.dataManager.Cajon();
                // Get the data from the row using the column name
                cajon.setId(rs.getInt("id"));
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));

                cajones.add(cajon);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getCajonesFromDB"+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("getCajonesFromDB"+e.toString());
        }
        return cajones;
    }

    public ArrayList<modelo.dataManager.Cajon> getCajonesLanceFromDB(int idLance) {
        ArrayList<modelo.dataManager.Cajon> cajones = new ArrayList();
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Cajones WHERE idLance = " + idLance);
            while (rs.next()) {
                modelo.dataManager.Cajon cajon = new modelo.dataManager.Cajon();
                // Get the data from the row using the column name
                cajon.setId(rs.getInt("id"));
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));
                cajones.add(cajon);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getCajonesLanceFromDB(): "+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("getCajonesLanceFromDB(): "+e.toString());
        }
        return cajones;
    }

     public int getCajonesFromLance(int idLance) {
        int total=0;
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT SUM(cantidad) as cantidad FROM Cajones WHERE idLance = " + idLance);
            while (rs.next()) {
                total= total+rs.getInt("cantidad");
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getCajonesFromLance(): "+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("getCajonesFromLance(): "+e.toString());
        }
        return total;
    }

    
    public modelo.dataManager.Cajon getCajonFromDB(int id) {
        modelo.dataManager.Cajon cajon = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Cajones WHERE id = " + id);
            if (rs.next()) {
                // Get the data from the row using the column name
                cajon.setId(rs.getInt("id"));
                cajon.setIdLance(rs.getInt("idLance"));
                cajon.setIdEspecie(rs.getInt("idEspecie"));
                cajon.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getCajonFromDB:"+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("getCajonFromDB:"+e.toString());
        }
        return cajon;
    }

    public boolean insertCajon(modelo.dataManager.Cajon cajon) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs = null;
        try {
            sqlQuery = "INSERT INTO Cajones "
                    + "(idLance, idEspecie, cantidad) "
                    + "VALUES "
                    + "(" + cajon.getIdLance() + "," + cajon.getIdEspecie() + "," + cajon.getCantidad() + ")";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("insertCajon:"+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("insertCajon:"+e.toString());
        }
        return sePudo;
    }

    public boolean deleteCajon(modelo.dataManager.Cajon cajon) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM Cajones WHERE id= " + cajon.getId();
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("deleteCajon:"+ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("deleteCajon:"+e.toString());
        }
        return sePudo;
    }
//Sin update, borrar y agregar solamente
}
