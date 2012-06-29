/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dataManager.Lance;

/**
 *
 * @author Necrophagist
 */
public class BrokerLance extends BrokerPpal {

    static BrokerLance unicaInstancia;

    public static BrokerLance getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerLance();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Lances");
        return sePudo;
    }

    public void insertLance(Lance lance) {
    }

    public ArrayList<modelo.dataManager.Lance> getLancesCampaniaActualFromDB(int idCamp) {
        ArrayList<modelo.dataManager.Lance> lances = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Lances WHERE idCampania= " + idCamp);
            while (rs.next()) {
                modelo.dataManager.Lance lanc = new modelo.dataManager.Lance();
                // Get the data from the row using the column name
                lanc.setId(rs.getInt("id"));
                lanc.setIdCampania(rs.getInt("idCampania"));
                lanc.setPosIniLat(rs.getDouble("posIniLat"));
                lanc.setPosIniLon(rs.getDouble("posIniLong"));
                lanc.setPosFinLat(rs.getDouble("posFinLat"));
                lanc.setPosFinLat(rs.getDouble("posFinLong"));                
                lanc.setfYHIni(rs.getDate("fYHIni"));
                lanc.setfYHFin(rs.getDate("fYHFin"));                
                lanc.setComentarios(rs.getString("comentarios"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return lances;
    }

    public modelo.dataManager.Lance getLanceFromDB(int idLance) {
        modelo.dataManager.Lance unlance = null;
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Lances WHERE idLance= " + idLance);
            if (rs != null) {
                // Get the data from the row using the column name
                unlance.setId(rs.getInt("id"));
                unlance.setIdCampania(rs.getInt("idCampania"));
                unlance.setPosIniLat(rs.getDouble("posIniLat"));
                unlance.setPosIniLon(rs.getDouble("posIniLong"));
                unlance.setPosFinLat(rs.getDouble("posFinLat"));
                unlance.setPosFinLat(rs.getDouble("posFinLong"));                
                unlance.setfYHIni(rs.getDate("fYHIni"));
                unlance.setfYHFin(rs.getDate("fYHFin"));                
                unlance.setComentarios(rs.getString("comentarios"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return unlance;
    }
    
      public int getIdLanceActual() {
        int idUltLance = -1;
        String sqlQuery = "SELECT max(id) from Lances";
        System.out.println("Select: " + sqlQuery);
        try {
            ResultSet rs = getStatement().executeQuery(sqlQuery);
            if (rs != null) {
                idUltLance = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return idUltLance;
    }
}
