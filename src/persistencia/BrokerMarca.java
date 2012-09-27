/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
                marca.setFechaYhora(rs.getDate("fechaYhora"));
                marca.setImgFileName(rs.getString("imgFileName"));
                marca.setLatitud(rs.getDouble("latitud"));
                marca.setLongitud(rs.getDouble("longitud"));
                marca.setPxXenImg(rs.getInt("pxXenImg"));
                marca.setPxYenImg(rs.getInt("pxYenImg"));
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
                //marca.setAreaImagen(rs.getString("areaImagen"));

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
                marca = new modelo.dataManager.Marca();
                marca.setId(rs.getInt("id"));
                marca.setProfundidad(rs.getDouble("profundidad"));
                marca.setFechaYhora(rs.getDate("fechaYhora"));
                marca.setImgFileName(rs.getString("imgFileName"));
                marca.setLatitud(rs.getDouble("latitud"));
                marca.setLongitud(rs.getDouble("longitud"));
                marca.setPxXenImg(rs.getInt("pxXenImg"));
                marca.setPxYenImg(rs.getInt("pxYenImg"));
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
            String imgFileName = null;
            if (marca.getImgFileName() != null) {
                imgFileName = "'" + marca.getImgFileName() + "'";
            }
            String fechaYhora = null;
            if (marca.getFechaYhora() != null) {
                fechaYhora = "" + marca.getFechaYhora().getTime() + "";
            }
            sqlQuery = "INSERT INTO Marcas"
                    + "(imgFileName,fechaYhora,profundidad,pxXenImg,pxYenImg,latitud,longitud)"
                    + "VALUES"
                    + "(" + imgFileName + "," + fechaYhora+ "," + marca.getProfundidad() 
                        + "," + marca.getPxXenImg() + "," + marca.getPxYenImg()
                        + "," + marca.getLatitud()+ "," + marca.getLongitud()+")";
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

/*
    public static void main(String args[]){
        modelo.dataManager.Marca marca = new modelo.dataManager.Marca();
        marca.setImgFileName("-0118-270411-045001.jpg");
        marca.setFechaYhora(Calendar.getInstance().getTime());
        marca.setLatitud(-43.12345);
        marca.setLongitud(-33.12345);
        marca.setProfundidad(78.5432);
        marca.setPxXenImg(134);
        marca.setPxYenImg(456);
        marca.setId(2);
        boolean resultado = BrokerMarca.getInstance().deleteMarca(marca);
        resultado = BrokerMarca.getInstance().insertMarca(marca);
        ArrayList<modelo.dataManager.Marca> marcas = BrokerMarca.getInstance().getMarcasFromDB();
        modelo.dataManager.Marca unaMarca = BrokerMarca.getInstance().getMarcaFromDB(1);
    }
*/

}


