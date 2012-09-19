/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
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
        System.out.println("codigo pendiente de: TRUNCATE TABLE CategoriasPoi");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> getCatPOISFromDB() {
        ArrayList<modelo.dataManager.CategoriaPoi> CatPOIS = new ArrayList();
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM CategoriasPoi");
            while (rs.next()) {
                modelo.dataManager.CategoriaPoi catPoi = new modelo.dataManager.CategoriaPoi();
                // Get the data from the row using the column name
                catPoi.setId(0 + rs.getInt("id"));
                catPoi.setPathIcono("" + rs.getString("fileNameIcono"));
                catPoi.setTitulo("" + rs.getString("titulo"));

                CatPOIS.add(catPoi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (rs != null){
                rs.close();
            }
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }        
        return CatPOIS;
    }

    public modelo.dataManager.CategoriaPoi getCatPOIFromDB(int id) {
        modelo.dataManager.CategoriaPoi CatPOI = new CategoriaPoi();
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs=null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM CategoriasPoi WHERE id = " + id);
            if (rs.next()) {
                // Get the data from the row using the column name
                CatPOI.setId(0 + rs.getInt("id"));
                CatPOI.setPathIcono("" + rs.getString("fileNameIcono"));
                CatPOI.setTitulo("" + rs.getString("titulo"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (rs != null){
                rs.close();
            }
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }        
        return CatPOI;
    }

    public boolean insertCategoriaPOI(modelo.dataManager.CategoriaPoi categoriaPOI) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs = null;
        try {
            String titulo = null;
            if (categoriaPOI.getTitulo() != null) {
                titulo = "'" + categoriaPOI.getTitulo() + "'";
            }
            String fileNameIcono = null;
            if (categoriaPOI.getPathIcono() != null) {
                fileNameIcono = categoriaPOI.getPathIcono().substring(categoriaPOI.getPathIcono().lastIndexOf("\\")+1);
                fileNameIcono = "'" + fileNameIcono + "'";
            }

            sqlQuery = "INSERT INTO CategoriasPoi "
                    + "(titulo,fileNameIcono) "
                    + "VALUES "
                    + "(" + titulo + "," + fileNameIcono + ")";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            } else {
                sePudo = false;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (rs != null){
                rs.close();
            }
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
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
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }        
        return sePudo;
    }

    public boolean updateCategoriaPOI(modelo.dataManager.CategoriaPoi categoriaPOI) {
        boolean sePudo = false;
        String sqlQuery = "";
        try {
            String fileNameIcono = categoriaPOI.getPathIcono().substring(categoriaPOI.getPathIcono().lastIndexOf("\\")+1);
            sqlQuery = "Update CategoriasPoi "
                    + "SET titulo='" + categoriaPOI.getTitulo() + "', "
                    + "fileNameIcono='" + fileNameIcono + "' "
                    + "WHERE "
                    + "id=" + categoriaPOI.getId();
            System.out.println("Update CatPOI: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            } else {
                sePudo = false;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public ArrayList<modelo.dataManager.CategoriaPoi> getCatPOISDeUnaCampFromDB(int idDeCampania) {
        //ArrayList<modelo.dataManager.CategoriaPoi> catPoisDeEstaCampania = new ArrayList();
        // --- metodo pendiente que debería devolver un arraylist con las CATEGORIAS de los POIs que pertenezcan a la campaña de ID especificado por parametro
        // -> probar usando JOINs, capaz sale mas facil que volver a llamar a getPOISDeUnaCampFromDB() para iterarlos y obtener su id de CatPoi
        // -> tener en cuenta que puede haber una campaña en curso, por lo tanto tendra fecha de inicio pero no de fin, en este caso
        // asumir como fecha final la fecha actual Calendar.getInstance().getTime();
        // -> validar el parametro de entrada y todo lo q pueda fallar
        // -> hacer el SELECT usando el objeto PreparedStatement
        HashSet lista = new HashSet(); 
        for (modelo.dataManager.POI unPOI : BrokerPOIs.getInstance().getPOISDeUnaCampFromDB(idDeCampania)) {  
            lista.add(unPOI.getCategoria());
        }
        ArrayList<modelo.dataManager.CategoriaPoi> catPoisDeEstaCampania = new ArrayList<modelo.dataManager.CategoriaPoi>(lista);
        return catPoisDeEstaCampania;
    }


/*
    //main de prueba
    public static void main(String[] args) {
        ArrayList<modelo.dataManager.CategoriaPoi> a = BrokerCategoriasPOI.getInstance().getCatPOISDeUnaCampFromDB(1);
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).getTitulo());
        }
    }*/
} 
