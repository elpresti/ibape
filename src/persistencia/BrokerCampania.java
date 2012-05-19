/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        //metodo pendiente
        return campanias;
    }

    public modelo.dataManager.Campania getCampaniaFromDb(int id) throws ParseException{
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
                campania.setFechaInicio(rs.getDate("fechainicio"));
                campania.setFechaFin(rs.getDate("fechafin"));
                campania.setFolderHistorico(rs.getString("folderhistorico"));
                // quedan pendiente cargar sus Pois ----------
            }            
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return campania;
    }    

    //-----------------   métodos posiblemente innecesarios   ------------------------
/*    
    public boolean setTodosTienenHistorico(boolean b) {
        boolean sePudo=false;
        System.out.println("setTodosTienenHistorico() Not yet implemented");
        return sePudo;        
    }

    public boolean setTieneHistorico(String folderName) {
        //busca en la tabla campanias la primer entrada que coincidan con este folderName y las marca como tieneHistorico=true
        // si encuentra mas de una entrada las demas las ignora ya que quedaran con tieneHistorico=false y luego seran eliminadas
        boolean sePudo=false;
        System.out.println("setTieneHistorico() Not yet implemented");
        return sePudo; 
    }
    
    public boolean borraNoTienenHistorico() {
        boolean sePudo=false;
        System.out.println("borraNoTienenHistorico() Not yet implemented");
        return sePudo;
    }

    public int[] obtieneTodosLosIds() {        
        System.out.println("obtieneTodosLosIds() Not yet implemented");
        //consulta que me devuelve todos los Ids de las campanias que hay en TablaCampanias
        // int[] ids = new int[resulset.numberOfRows()];
        int[] ids = null;
        return ids;
    }
*/
    //-----------------------------------------------------------------------
    
    public boolean insertCampania(modelo.dataManager.Campania campania){
        boolean sePudo = false;
        try {                    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            
            String sqlQuery = "INSERT INTO Campanias"
                    + "(fechainicio,fechafin,folderhistorico,descripcion,barco,capitan,estado)"
                    + "VALUES"
                    +"("+"'"+sdf.format(campania.getFechaInicio())+"'"+",";

            if (campania.getFechaFin() != null){
                    sqlQuery=sqlQuery+"'"+sdf.format(campania.getFechaFin())+"'"+",";
            } else { sqlQuery=sqlQuery+"null,"; }
            
            sqlQuery=sqlQuery
                    +"'"+campania.getFolderHistorico()+"'"+","
                    +"'"+campania.getDescripcion()+"'"+","
                    +"'"+campania.getBarco()+"'"+","
                    +"'"+campania.getCapitan()+"'"+","
                    + campania.getEstado()
                    + ");";
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            
            String sqlQuery = "UPDATE Campanias "
                    + "SET fechainicio = '"+sdf.format(campania.getFechaInicio())+"'"+", "
                    +" fechafin = ";            
                    if (campania.getFechaFin() != null){
                        sqlQuery=sqlQuery+"'"+sdf.format(campania.getFechaFin())+"'"+",";
                    } else { sqlQuery=sqlQuery+"null,"; }
            
                    sqlQuery=sqlQuery
                    +" folderhistorico = '"+campania.getFolderHistorico()+"'"+","
                    +" descripcion = '"+campania.getDescripcion()+"'"+","
                    +" barco = '"+campania.getBarco()+"'"+","
                    +" capitan = '"+campania.getCapitan()+"'"+","
                    +" estado = "+campania.getEstado()
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