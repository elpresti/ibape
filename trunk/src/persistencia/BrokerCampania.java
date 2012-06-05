/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class BrokerCampania extends BrokerPpal{
    static BrokerCampania unicaInstancia;
    
    public static BrokerCampania getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new BrokerCampania();          
       }       
       return unicaInstancia;
    }

    public boolean vaciaTablaCampanias() {
        boolean sePudo=false;
        System.out.println("vaciaTablaCampanias() Not yet implemented");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.Campania> getCampaniasFromDB(){
        ArrayList<modelo.dataManager.Campania> campanias = new ArrayList();        
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Campanias");
            while (rs.next()) {                
                modelo.dataManager.Campania campania = new modelo.dataManager.Campania();
                // Get the data from the row using the column name
                campania.setId(rs.getInt("id"));
                campania.setBarco(rs.getString("barco"));
                campania.setCapitan(rs.getString("capitan"));
                campania.setDescripcion(rs.getString("descripcion"));
                campania.setEstado(rs.getInt("estado"));
                campania.setFolderHistorico(rs.getString("folderhistorico"));
                campania.setFechaInicio(rs.getTimestamp("fechainicio"));
                campania.setFechaFin(rs.getTimestamp("fechafin"));
                // quedan pendiente cargar sus Pois ----------
                campanias.add(campania);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        
        return campanias;
    }

    public modelo.dataManager.Campania getCampaniaFromDb(int id) {
        modelo.dataManager.Campania campania = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Campanias WHERE id = "+id);
            if (rs != null) {
                campania = new modelo.dataManager.Campania();
                // Get the data from the row using the column name
                campania.setId(rs.getInt("id"));
                campania.setBarco(rs.getString("barco"));
                campania.setCapitan(rs.getString("capitan"));
                campania.setDescripcion(rs.getString("descripcion"));
                campania.setEstado(rs.getInt("estado"));
                try {
                    campania.setFechaInicio(rs.getDate("fechainicio"));
                    campania.setFechaFin(rs.getDate("fechafin"));                    
                }
                catch (Exception e){
                    Logueador.getInstance().agregaAlLog(e.toString());
                }
                campania.setFolderHistorico(rs.getString("folderhistorico"));
                // quedan pendiente cargar sus Pois ----------
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return campania;
    }
    
    public boolean insertCampania(modelo.dataManager.Campania campania){
        boolean sePudo = false;
        try {                                

            String fechaInicio = null;
            if (campania.getFechaInicio() != null) {
                fechaInicio = ""+campania.getFechaInicio().getTime()+"";
            }            
            
            String fechaFin = null;
            if (campania.getFechaFin() != null) {
                fechaFin = ""+campania.getFechaFin().getTime()+"";
            }               
            
            String folderHistorico = null;
            if (campania.getFolderHistorico() != null){
                folderHistorico = "'"+campania.getFolderHistorico()+"'";
            }
            
            String descripcion = null;
            if (campania.getDescripcion() != null){
                descripcion = "'"+campania.getDescripcion()+"'";
            }

            String barco = null;
            if (campania.getBarco() != null){
                barco = "'"+campania.getBarco()+"'";
            }

            String capitan = null;
            if (campania.getCapitan() != null){
                capitan = "'"+campania.getCapitan()+"'";
            }

            String estado = String.valueOf(campania.getEstado());

            String sqlQuery = "INSERT INTO Campanias"
                    + "(fechainicio,fechafin,folderhistorico,descripcion,barco,capitan,estado)"
                    + "VALUES"
                    +"("+fechaInicio+","+fechaFin+","+folderHistorico+","+descripcion+","+barco+","+capitan+","+estado+")";
            System.out.println("Insert: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }

    public boolean updateCampania(modelo.dataManager.Campania campania){
        boolean sePudo = false;
        try {         
            String fechaInicio = null;
            if (campania.getFechaInicio() != null) {
                fechaInicio = ""+campania.getFechaInicio().getTime()+"";
            }                        
            String fechaFin = null;
            if (campania.getFechaFin() != null) {
                fechaFin = ""+campania.getFechaFin().getTime()+"";
            }                           
            String folderHistorico = null;
            if (campania.getFolderHistorico() != null){
                folderHistorico = "'"+campania.getFolderHistorico()+"'";
            }            
            String descripcion = null;
            if (campania.getDescripcion() != null){
                descripcion = "'"+campania.getDescripcion()+"'";
            }
            String barco = null;
            if (campania.getBarco() != null){
                barco = "'"+campania.getBarco()+"'";
            }
            String capitan = null;
            if (campania.getCapitan() != null){
                capitan = "'"+campania.getCapitan()+"'";
            }           
            String estado = String.valueOf(campania.getEstado());
            
            String sqlQuery = "UPDATE Campanias "
                    + "SET fechainicio = "+fechaInicio+", "
                    +" fechafin = "+fechaFin+", "
                    +" folderhistorico = "+folderHistorico+","
                    +" descripcion = "+descripcion+","
                    +" barco = "+barco+","
                    +" capitan = "+capitan+","
                    +" estado = "+estado
                    +" WHERE id = "+campania.getId();
            System.out.println("UPDATE: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }

        return sePudo;
    }
    
    public boolean deleteCampania(modelo.dataManager.Campania campania){
        boolean sePudo = false;

        try {        
            String sqlQuery = "DELETE FROM Campanias "
                    + "WHERE id = "+campania.getId();
            System.out.println("DELETE: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        
        
        return sePudo;
    }        

}