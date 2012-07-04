/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dataManager.CategoriaPoi;

/**
 *
 * @author Necrophagist
 */
public class BrokerCategoriasPOI extends BrokerPpal {

    static BrokerCategoriasPOI unicaInstancia;

    public static BrokerCategoriasPOI getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerCategoriasPOI();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE CategoriasPoi");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> getCatPOISFromDB() {
        ArrayList<modelo.dataManager.CategoriaPoi> CatPOIS = new ArrayList();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM CategoriasPoi");
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
        modelo.dataManager.CategoriaPoi CatPOI = new CategoriaPoi();
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs;
        try {
            rs = getStatement().executeQuery("SELECT * FROM CategoriasPoi WHERE id = " + id);
            if (rs != null) {        
                // Get the data from the row using the column name
                CatPOI.setId(rs.getInt("id"));
                CatPOI.setPathIcono(rs.getString("pathIcono"));
                CatPOI.setTitulo(rs.getString("titulo"));
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
                titulo = "'" + categoriaPOI.getTitulo() + "'";
            }
            String pathIcono = null;
            if (categoriaPOI.getPathIcono() != null) {
                pathIcono = "'" + categoriaPOI.getPathIcono() + "'";
            }

            sqlQuery = "INSERT INTO CategoriasPoi "
                    + "(titulo,pathIcono) "
                    + "VALUES "
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
            String sqlQuery = "DELETE FROM CategoriasPoi "
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
            sqlQuery = "Update CategoriasPoi "
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
 
    /*
    //main de prueba
    public static void main(String[] args) {
        BrokerCategoriasPOI.getInstance().creaArchivoDb();
        BrokerCategoriasPOI.getInstance().crearTablaCajones();
        BrokerCategoriasPOI.getInstance().crearTablaCategoriasPoi();         
        BrokerCategoriasPOI.getInstance().creaConexionNueva();    
         BrokerCategoriasPOI.getInstance().getCatPOISFromDB();   
        CategoriaPoi a=new CategoriaPoi();
        a.setPathIcono("c:\\a");
        a.setTitulo("Test");
        BrokerCategoriasPOI.getInstance().insertCategoriaPOI(a);       
    }*/
}
