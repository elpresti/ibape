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
public class BrokerEspecie extends BrokerPpal {

    static BrokerEspecie unicaInstancia;

    public static BrokerEspecie getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerEspecie();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Especies");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.Especie> getEspeciesFromDB() {
        ArrayList<modelo.dataManager.Especie> especies = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Especies");
            while (rs.next()) {
                modelo.dataManager.Especie esp = new modelo.dataManager.Especie();
                // Get the data from the row using the column name
                esp.setId(rs.getInt("id"));
                esp.setNombre(rs.getString("nombre"));
                especies.add(esp);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return especies;
    }

    public modelo.dataManager.Especie getEspecieFromDB(int id) {
        modelo.dataManager.Especie esp = null;
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Especies WHERE id= " + id);
            if (rs != null) {
                // Get the data from the row using the column name
                esp.setId(rs.getInt("id"));
                esp.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return esp;
    }
    //ABM no aplica se carga de la base
}
