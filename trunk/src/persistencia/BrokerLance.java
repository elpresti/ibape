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

    public ArrayList<modelo.dataManager.Lance> getLancesCampaniaActualFromDB(int idCamp) {
        ArrayList<modelo.dataManager.Lance> lances = new ArrayList();
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Lances WHERE idCampania= " + idCamp);
            while (rs.next()) {
                modelo.dataManager.Lance unLance = new modelo.dataManager.Lance();
                // Get the data from the row using the column name
                unLance.setId(rs.getInt("id"));
                unLance.setIdCampania(rs.getInt("idCampania"));
                unLance.setPosIniLat(rs.getDouble("posIniLat"));
                unLance.setPosIniLon(rs.getDouble("posIniLon"));
                unLance.setPosFinLat(rs.getDouble("posFinLat"));
                unLance.setPosFinLat(rs.getDouble("posFinLon"));
                unLance.setfYHIni(rs.getDate("fYHIni"));
                unLance.setfYHFin(rs.getDate("fYHFin"));
                unLance.setComentarios(rs.getString("comentarios"));
                lances.add(unLance);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getLancesCampaniaActualFromDB" + ex.toString());
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
            Logueador.getInstance().agregaAlLog("getLancesCampaniaActualFromDB" + e.toString());
        }
        return lances;
    }

    public modelo.dataManager.Lance getLanceFromDB(int idLance) {
        modelo.dataManager.Lance unlance = null;
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Lances WHERE idLance= " + idLance);
            if (rs.next()) {
                // Get the data from the row using the column name
                unlance.setId(rs.getInt("id"));
                unlance.setIdCampania(rs.getInt("idCampania"));
                unlance.setPosIniLat(rs.getDouble("posIniLat"));
                unlance.setPosIniLon(rs.getDouble("posIniLon"));
                unlance.setPosFinLat(rs.getDouble("posFinLat"));
                unlance.setPosFinLat(rs.getDouble("posFinLon"));
                unlance.setfYHIni(rs.getDate("fYHIni"));
                unlance.setfYHFin(rs.getDate("fYHFin"));
                unlance.setComentarios(rs.getString("comentarios"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getLanceFromDB" + ex.toString());
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
            Logueador.getInstance().agregaAlLog("getLanceFromDB" + e.toString());
        }
        return unlance;
    }

    public int getIdLanceEnCurso() {
        int idUltLance = -1;
        String sqlQuery = "SELECT id,fyHFin from Lances order by ROWID DESC limit 1;";
        System.out.println("Select: " + sqlQuery);
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery(sqlQuery);
            if (rs.next()) {
                if (rs.getDate("fYHFin") == null) {
                    idUltLance = rs.getInt("id");//si no esta finalizado devuelvo el id sino qda -1   
                }
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("getIdLanceEnCurso" + ex.toString());
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
            Logueador.getInstance().agregaAlLog("getIdLanceEnCurso" + e.toString());
        }
        return idUltLance;
    }

    public boolean insertLance(Lance unLance) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs = null;

        String fechaHoraFin = null;
        if (unLance.getfYHFin() != null) {
            fechaHoraFin = "" + unLance.getfYHFin().getTime() + "";
        }

        try {
            sqlQuery = "INSERT INTO Lances "
                    + "(idCampania,fYHIni,posIniLat,posIniLon,fYHFin,posFinLat,posFinLon,comentarios)"
                    + " VALUES "
                    + "(" + unLance.getIdCampania() + "," + unLance.getfYHIni().getTime() + "," + unLance.getPosIniLat()
                    + "," + unLance.getPosIniLon() + "," + fechaHoraFin + "," + unLance.getPosFinLat()
                    + "," + unLance.getPosFinLon() + ",'" + unLance.getComentarios() + "')";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog("insertLance" + ex.toString());
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
            Logueador.getInstance().agregaAlLog("insertLance" + e.toString());
        }
        return sePudo;
    }

    public boolean updateLance(Lance unLance) {
        boolean sePudo = false;
        String sqlQuery = "";
        try {
            sqlQuery = "UPDATE Lances SET "
                    + " comentarios= '" + unLance.getComentarios() + "'"
                    + " WHERE id=" + unLance.getId();
            System.out.println("update: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            } else {
                sePudo = false;
            }

            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean deleteLance(Lance unLance) {
        boolean sePudo = false;
        try {
            String sqlQuery = "BEGIN TRANSACTION; "
                    + "DELETE FROM Cajones WHERE idLance= " + unLance.getId() + "; "
                    + "DELETE FROM Lances "
                    + "WHERE id = " + unLance.getId() + "; ";
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                getStatement().executeUpdate("END TRANSACTION;");
                sePudo = true;
            } else {
                getStatement().executeUpdate("ROLLBACK TRANSACTION;");
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try {
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
}
