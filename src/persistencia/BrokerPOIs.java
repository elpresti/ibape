/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import modelo.dataManager.AdministraCatPoi;
import modelo.dataManager.Campania;
import modelo.dataManager.Marca;
import modelo.dataManager.POI;
import modelo.gisModule.GeneradorKML;
//import sun.text.normalizer.Trie.DataManipulate;

/**
 *
 * @author Necrophagist
 */
public class BrokerPOIs extends BrokerPpal {

    static BrokerPOIs unicaInstancia;
    private PreparedStatement psSelectPoisXIdCampania;

    public static BrokerPOIs getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerPOIs();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Pois");
        return sePudo;
    }

    public ArrayList<modelo.dataManager.POI> getPOISFromDB() {
        ArrayList<modelo.dataManager.POI> POIs = new ArrayList();
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Pois");
            while (rs.next()) {
                modelo.dataManager.POI poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("posLat"));
                poi.setLongitud(rs.getDouble("posLon"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("pathImg"));
                poi.setIdCampania(rs.getInt("idCampania"));
                poi.setDescripcion(rs.getString("descripcion"));

                //poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                //ver if null
                poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

                POIs.add(poi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return POIs;
    }

    public modelo.dataManager.POI getPOIFromDB(int id) {
        modelo.dataManager.POI poi = new modelo.dataManager.POI();
        //buscar en la base la campania.id que coincida con el id pasado por parametro        
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Pois WHERE id = " + id);
            if (rs.next()) {
                poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("posLat"));
                poi.setLongitud(rs.getDouble("posLon"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("pathImg"));
                poi.setIdCampania(rs.getInt("idCampania"));
                poi.setDescripcion(rs.getString("descripcion"));
                //poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

            }

            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }

        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return poi;
    }

    public boolean insertPOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs = null;
        try {
            String PathImg = "";
            if (poi.getPathImg() != null) {
                PathImg = "'" + poi.getPathImg() + "'";
            }

            String Desc = "";
            if (poi.getDescripcion() != null) {
                Desc = "'" + poi.getDescripcion() + "'";
            }

            String fechaHora = null;
            if (poi.getFechaHora() != null) {
                fechaHora = "" + poi.getFechaHora().getTime() + "";
            }

            sqlQuery = "INSERT INTO Pois "
                    //+ "(posLat,posLon,fechaHora,pathImg,idCategoriaPOI,IdCampania,descripcion)"
                    + "(posLat,posLon,fechaHora,idCategoriaPOI,IdCampania,descripcion)"
                    + " VALUES "
                    + "(" + poi.getLatitud() + "," + poi.getLongitud() + "," + fechaHora
                    //+ "," + PathImg + "," + poi.getIdCategoriaPOI()/*poi.getCategoria().getId()*/
                    + "," + poi.getIdCategoriaPOI()
                    + "," + poi.getIdCampania() + "," + Desc + ")";
            System.out.println("Insert: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                //sePudo = true;//sin las marcas
                /*
                if (poi.getMarcas() != null) {
                //recuperar el id para insertar las marcas
                sqlQuery = "SELECT max(id) from Pois";
                System.out.println("Select: " + sqlQuery);
                rs = getStatement().executeQuery(sqlQuery);
                if (rs.next()) {
                int idPOI = rs.getInt("id");
                for (Marca m : poi.getMarcas()) {
                //if (m.getId() == 0) {
                m.setIdPois(idPOI);
                BrokerMarca.getInstance().insertMarca(m);
                //}
                }
                }
                }
                 */
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean updatePOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        String sqlQuery = "";
        ResultSet rs;
        try {
            String PathImg = null;
            if (poi.getPathImg() != null) {
                PathImg = "'" + poi.getPathImg() + "'";
            }
            sqlQuery = "UPDATE Pois SET "
                    + "pathImg= " + PathImg + ", "
                    + "posLat= " + poi.getLatitud() + ", "
                    + "posLon= " + poi.getLongitud() + ", "
                    + "idCategoriaPOI=" + poi.getIdCategoriaPOI()/*poi.getCategoria().getId()*/ + ", "
                    + "descripcion= '" + poi.getDescripcion() + "' "
                    + " WHERE "
                    + "id=" + poi.getId();
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

    public boolean deletePOI(modelo.dataManager.POI poi) {
        boolean sePudo = false;
        try {
            String sqlQuery = "DELETE FROM Pois "
                    + "WHERE id = " + poi.getId();
            System.out.println("DELETE: " + sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
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

    public ArrayList<modelo.dataManager.POI> getPOISDeUnaCampFromDB(int idDeCampania) {
        ArrayList<modelo.dataManager.POI> poisDeEstaCampania = new ArrayList();
        // --- metodo pendiente que debería devolver un arraylist de los POIs que pertenezcan a la campaña de ID especificado por parametro
        // -> para saber si un POI pertenece a una campaña debo armar la consulta de SQL solicitando aquellos POIs cuya fecha
        // se encuentre entre la fecha de inicio y fin de campaña
        // -> tener en cuenta que puede haber una campaña en curso, por lo tanto tendra fecha de inicio pero no de fin, en este caso
        // asumir como fecha final la fecha actual Calendar.getInstance().getTime();
        // -> validar el parametro de entrada y todo lo q pueda fallar
        // -> hacer el SELECT usando el objeto PreparedStatement
        try {

            Campania unaCamp = BrokerCampania.getInstance().getCampaniaFromDb(idDeCampania);

            if (unaCamp != null) {
                java.sql.Date fechaInicio = null;
                java.sql.Date fechaFin = null;
                fechaInicio = new java.sql.Date(unaCamp.getFechaInicio().getTime());
                if (unaCamp.getEstado() == 1 && unaCamp.getFechaFin() != null) {//campania finalizada
                    fechaInicio = new java.sql.Date(unaCamp.getFechaFin().getTime());
                } else {
                    fechaFin = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                }


                ResultSet rs = getStatement().executeQuery(
                        "SELECT * FROM Pois "
                        + "WHERE idCampania=" + idDeCampania + " "
                        + "OR fechaHora "
                        + "BETWEEN " + fechaInicio + " AND " + fechaFin + " "
                        + "ORDER BY fechaHora ASC");


                //ResultSet rs = getPsSelectPoisXIdCampania().executeQuery();
                //if (rs.first() == true) {

                while (rs.next()) {
                    modelo.dataManager.POI poi = new modelo.dataManager.POI();
                    // Get the data from the row using the column name
                    poi.setId(rs.getInt("id"));
                    poi.setLatitud(rs.getDouble("posLat"));
                    poi.setLongitud(rs.getDouble("posLon"));
                    poi.setFechaHora(rs.getDate("fechaHora"));
                    poi.setPathImg(rs.getString("pathImg"));
                    poi.setIdCampania(rs.getInt("idCampania"));
                    poi.setDescripcion(rs.getString("descripcion"));
//--
                    poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                    //poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                    //ver if null
                    poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

                    poisDeEstaCampania.add(poi);
                }
                //}
                if (rs != null) {
                    rs.close();
                }
                if (getStatement() != null) {
                    getStatement().close();
                }
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
        return poisDeEstaCampania;
    }

    //main de prueba
/*    
    public static void main(String[] args) {
    ArrayList a = BrokerPOIs.getInstance().getPOISDeUnaCampFromDB(2);
    }
     */
    //este queda??? ---> si, queda
    public ArrayList<modelo.dataManager.POI> getPOISDeUnaCampSegunCatPoi(int idDeCampania, int idDeCatPois) {
        ArrayList<modelo.dataManager.POI> poisDeEstaCampania = new ArrayList();
        ResultSet rs = null;
        if (idDeCampania >= 0) {
            try {
                Campania laCampania = BrokerCampania.getInstance().getCampaniaFromDb(idDeCampania);
                if (laCampania != null) {
                    PreparedStatement psSelect = getConexion().prepareStatement(
                            "SELECT * FROM Pois "
                            + "WHERE (idCampania= ? "
                            + "OR fechaHora "
                            + "BETWEEN ? AND ? ) AND idCategoriaPoi=? "
                            + "ORDER BY fechaHora ASC");
                    psSelect.setInt(1, idDeCampania);
                    psSelect.setDate(2, new java.sql.Date(laCampania.getFechaInicio().getTime()));
                    if (laCampania.getEstado() == 0) {//campania finalizada
                        psSelect.setDate(3, new java.sql.Date(laCampania.getFechaFin().getTime()));
                    } else {
                        psSelect.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                    }
                    psSelect.setInt(4, idDeCatPois);
                    System.out.println("Select : " + psSelect.toString());
                    rs = psSelect.executeQuery();
                    while (rs.next()) {
                        modelo.dataManager.POI poi = new modelo.dataManager.POI();
                        // Get the data from the row using the column name
                        poi.setId(rs.getInt("id"));
                        poi.setLatitud(rs.getDouble("posLat"));
                        poi.setLongitud(rs.getDouble("posLon"));
                        poi.setFechaHora(rs.getDate("fechaHora"));
                        poi.setPathImg(rs.getString("pathImg"));
                        poi.setIdCampania(rs.getInt("idCampania"));
                        poi.setDescripcion(rs.getString("descripcion"));
                        poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                        poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                        //ver if null
                        poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

                        poisDeEstaCampania.add(poi);
                    }
                }
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return poisDeEstaCampania;
    }

    public int getCantPOISDeUnaCampSegunCatPoi(int idDeCampania, int idDeCatPois) {
        int cantPoisDeEstaCampania = 0;
        ResultSet rs = null;
        if (idDeCampania >= 0) {
            try {
                Campania laCampania = BrokerCampania.getInstance().getCampaniaFromDb(idDeCampania);
                if (laCampania != null) {
                    PreparedStatement psSelect = getConexion().prepareStatement(
                            "SELECT count() FROM Pois "
                            + "WHERE idCampania= ? AND "
                            + "idCategoriaPoi=? ");
                    /*  si quisieramos determinar los POIs de una campaña segun las fechas, este sería el código:
                    + "WHERE (idCampania= ? OR fechaHora  BETWEEN ? AND ? ) AND "
                    psSelect.setDate(2, new java.sql.Date(laCampania.getFechaInicio().getTime()));
                    if (laCampania.getEstado() == 1 && laCampania.getFechaFin() != null) {//campania finalizada
                    psSelect.setDate(3, new java.sql.Date(laCampania.getFechaFin().getTime()));
                    } else {
                    psSelect.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
                    }
                     */
                    psSelect.setInt(1, idDeCampania);
                    psSelect.setInt(2, idDeCatPois);
                    System.out.println("Select : " + psSelect.toString());
                    rs = psSelect.executeQuery();
                    if (rs.next()) {
                        cantPoisDeEstaCampania = rs.getInt(1);
                    }
                }
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return cantPoisDeEstaCampania;
    }

    public ArrayList<modelo.dataManager.POI> getPOISFromDBSegunCat(int idCategoriaPOI) {
        ArrayList<modelo.dataManager.POI> POIs = new ArrayList();
        ResultSet rs = null;
        try {
            rs = getStatement().executeQuery("SELECT * FROM Pois WHERE idCategoriaPoi=" + idCategoriaPOI);
            while (rs.next()) {
                modelo.dataManager.POI poi = new modelo.dataManager.POI();
                // Get the data from the row using the column name
                poi.setId(rs.getInt("id"));
                poi.setLatitud(rs.getDouble("posLat"));
                poi.setLongitud(rs.getDouble("posLon"));
                poi.setFechaHora(rs.getDate("fechaHora"));
                poi.setPathImg(rs.getString("pathImg"));
                poi.setIdCampania(rs.getInt("idCampania"));
                poi.setDescripcion(rs.getString("descripcion"));

                //poi.setCategoria(BrokerCategoriasPOI.getInstance().getCatPOIFromDB(rs.getInt("idcategoriapoi")));
                poi.setIdCategoriaPOI(rs.getInt("idCategoriaPoi"));
                //ver if null
                poi.setMarcas(BrokerMarca.getInstance().getMarcasPOIFromDB(rs.getInt("id")));

                POIs.add(poi);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
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
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return POIs;
    }
    
    public boolean deletePoisFromLance(int idLance){
        boolean sePudo=false;
        try{
            ArrayList<modelo.dataManager.POI> poisDeLances = getPOISFromDBSegunCat(AdministraCatPoi.getInstance().getIdCatLances());
            int cantPoisEncontrados = 0;
            for (modelo.dataManager.POI poi: poisDeLances){
                String idLanceDeUnPoi = GeneradorKML.getInstance().getValorDeParametroFromXML(poi.getDescripcion(), "idLance");
                if ((idLanceDeUnPoi.length()>0)  &&  (Integer.valueOf(idLanceDeUnPoi)==idLance)){
                    POI poiAborrar = new POI();
                    poiAborrar.setId(idLance);
                    if (deletePOI(poi)){
                        cantPoisEncontrados++;
                    }
                    if (cantPoisEncontrados==2){
                        break;
                    }
                }
            }
            sePudo=(cantPoisEncontrados==0) || (cantPoisEncontrados==2);
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("deletePoisFromLance(): "+e.toString());
        }
        return sePudo;
    }
    /* No se usa por ahora.
    boolean insertaCatPoi() {
    boolean sePudo = false;
    String sqlQuery="";
    try {                                
    sqlQuery = "INSERT INTO CategoriasPoi"
    + "(id,titulo,fileNameIcono)"
    + "VALUES"
    +"(99,'Alerta','icono-alertas.png')";
    System.out.println("Insert: "+sqlQuery);
    if (getStatement().executeUpdate(sqlQuery) > 0) {
    sePudo = true;
    }else{sePudo=false;}
    
    
    
    }catch (SQLException ex) {
    Logueador.getInstance().agregaAlLog(ex.toString());
    }
    try{//ya la use, asique cierro ResultSets y Statements usados, para evitar la excepcion DatabaseLocked
    if (getStatement() != null){
    getStatement().close();
    }
    }
    catch(Exception e){
    Logueador.getInstance().agregaAlLog(e.toString());
    }
    return sePudo;
    }
     */
}