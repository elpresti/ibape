/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.File;
import java.util.ArrayList;
import modelo.dataManager.Campania;
import modelo.dataManager.Punto;
import java.sql.*;
import modelo.dataManager.AdministraCampanias;

/**
 *
 * @author Sebastian
 */
public class BrokerHistorico {
    Thread BdbMap;
    static BrokerHistorico unicaInstancia;
    private boolean guardaDatosGps;
    private boolean guardaDatosSonda;
    private boolean guardaDatosPeces;
    private boolean guardaDatosSondaSets;
    private Campania campania;
    private Connection conexion;
    private Statement statement;
    private ResultSet resultSet;
    private String dbFileName;
    private String folderNameHistorico;
    
    
    private BrokerHistorico(){
        inicializaBrokerHistorico();
    }
    
    public static BrokerHistorico getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerHistorico();       
       return unicaInstancia;
    }    
    
    /**
     * @return the guardaDatosGps
     */
    public boolean isGuardaDatosGps() {
        return guardaDatosGps;
    }

    /**
     * @param guardaDatosGps the guardaDatosGps to set
     */
    public void setGuardaDatosGps(boolean guardaDatosGps) {
        this.guardaDatosGps = guardaDatosGps;
    }

    /**
     * @return the guardaDatosSonda
     */
    public boolean isGuardaDatosSonda() {
        return guardaDatosSonda;
    }

    /**
     * @param guardaDatosSonda the guardaDatosSonda to set
     */
    public void setGuardaDatosSonda(boolean guardaDatosSonda) {
        this.guardaDatosSonda = guardaDatosSonda;
    }

    /**
     * @return the guardaDatosPeces
     */
    public boolean isGuardaDatosPeces() {
        return guardaDatosPeces;
    }

    /**
     * @param guardaDatosPeces the guardaDatosPeces to set
     */
    public void setGuardaDatosPeces(boolean guardaDatosPeces) {
        this.guardaDatosPeces = guardaDatosPeces;
    }

    /**
     * @return the guardaDatosSondaSets
     */
    public boolean isGuardaDatosSondaSets() {
        return guardaDatosSondaSets;
    }

    /**
     * @param guardaDatosSondaSets the guardaDatosSondaSets to set
     */
    public void setGuardaDatosSondaSets(boolean guardaDatosSondaSets) {
        this.guardaDatosSondaSets = guardaDatosSondaSets;
    }

    /**
     * @return the campania
     */
    public Campania getCampania() {
        return campania;
    }

    /**
     * @param campania the campania to set
     */
    public void setCampania(Campania campania) {
        this.campania = campania;
    }
    
    public boolean insertPunto(Punto p) {
        boolean sePudo=false;
        // --- metodo pendiente ---
        return sePudo;
    }
    
    public boolean updatePunto(Punto p) {
        boolean sePudo=false;
        // --- metodo pendiente ---
        return sePudo;
    }
    
    public boolean deletePunto(Punto p) {
        boolean sePudo=false;
        // --- metodo pendiente ---
        return sePudo;
    }

    public ArrayList<Punto> getPuntos(java.util.Date fechaDesde, java.util.Date fechaHasta){
        ArrayList<Punto> recorrido = new ArrayList();            
        // --- método pendiente ---
        return recorrido;
    }    
    
    public boolean creaConexionNueva() {
        boolean sePudo = false;
        try {
            Campania campEnCurso = AdministraCampanias.getInstance().getCampaniaEnCurso();
            boolean yaTeniaHistorico=false;
            //si la campaña en curso no tiene dbhistorico, la creamos
            if (!(AdministraCampanias.getInstance().verificaSiTieneHistorico(campEnCurso.getId()))){
                if (!(creaFoldersHistorico(campEnCurso.getId()))){
                    return false;
                }
            }
            else { yaTeniaHistorico = true; }

            Class.forName("org.sqlite.JDBC");
            // Observar que el archivo .db es la base de datos del Historico y se crea en el directorio de Historico de la campania en curso
            setConexion(DriverManager.getConnection("jdbc:sqlite:" + getFolderNameHistorico()+"\\"+getCampaignFolderName(campEnCurso.getId())+"\\"+ getDbFileName()));

            setStatement(getConexion().createStatement());            
            
            if (!(yaTeniaHistorico)) { creaTodasLasTablas(); }
            //getStatement().close();
            //getConexion().close();
            sePudo = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return sePudo;
    }    

    public boolean creaFoldersHistorico(int idCampania){
        boolean sePudo=false;
        try {
            modelo.dataManager.Campania campania = AdministraCampanias.getInstance().getCampania(idCampania);
            File foldersHistorico = new File(getFolderNameHistorico()+"\\"+getCampaignFolderName(campania.getId()));
            foldersHistorico.mkdirs();
            campania.setFolderHistorico(getCampaignFolderName(campania.getId()));
            //actualizo la campania en memoria
            AdministraCampanias.getInstance().modificarCampania(campania);
            //actualizo la campania en la tabla campanias
            persistencia.BrokerCampania.getInstance().updateCampania(campania);
            sePudo=true;
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    
    private void close() {
/*                
        try {
                if (getResultSet() != null) {
                        getResultSet().close();
                }

                if (getStatement() != null) {
                        getStatement().close();
                }

                if (connect != null) {
                        connect.close();
                }
        } catch (Exception e) {
            System.out.println(e);
        }
*/
    }
    
    public void run() {
        // --- método pendiente ---
        try {            

        } catch (Exception e) {            
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        // ------------------------
    }
    
    public boolean disparaEjecucion(){
        boolean sePudo = false;
/*        
        if (!(isGuardaDatosGps())) {
            if (BdbMap == null) {
                inicializaBrokerHistorico();
                BdbMap = new Thread(this);
                BdbMap.setPriority(Thread.MIN_PRIORITY);
                BdbMap.start();
                sePudo=true;
            }
        }    
*/
        return sePudo;
    }           
    
    public boolean detieneEjecucion(){
        boolean sePudo = false;
/*        
        if (!(BdbMap == null)) {
           BdbMap = null;
           sePudo=true;
        }
*/        
        return sePudo;
    }           


    private void inicializaBrokerHistorico(){                                       
        setGuardaDatosGps(false);
        setGuardaDatosSonda(false);
        setGuardaDatosPeces(false);
        setGuardaDatosSondaSets(false);
        setFolderNameHistorico("Historico");
        setCampania(AdministraCampanias.getInstance().getCampaniaEnCurso());
        setDbFileName("historico.db");        
}    
                

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * @param conexion the conexion to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * @return the resultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * @param resultSet the resultSet to set
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    /**
     * @return the dbName
     */
    public String getDbFileName() {
        return dbFileName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbFileName(String dbName) {
        this.dbFileName = dbName;
    }
    
    private String getCampaignFolderName(int id) {
        return "camp"+id;
    }

    private boolean creaTodasLasTablas() {
        boolean sePudo = true; 
        try {        
            // Creacion de Tablas            
            sePudo = sePudo && crearTablaPuntos();
            sePudo = sePudo && crearTablaSondaSets();
            
            //Creacion de Triggers           
            sePudo = sePudo && crearTriggersIdSondaSets();            
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;
            }

        return sePudo;
    }

    private boolean crearTablaPuntos() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Puntos ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  fechaYhora         TIMESTAMP NOT NULL,"
            + "  latitud            REAL NOT NULL,"
            + "  latHemisferio      CHAR NOT NULL,"
            + "  longitud           REAL NOT NULL,"
            + "  lonHemisferio      CHAR NOT NULL,"
            + "  altitud            REAL,"
            + "  velocidad          REAL,"
            + "  rumbo              REAL,"
            + "  profundidad        REAL,"
            + "  velocidadAgua      REAL,"
            + "  tempAgua           REAL,"
            + "  idSondaSets        integer NOT NULL,"
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idSondaSets)"
            + "    REFERENCES SondaSets(id)"
            + ");";            
  
            getStatement().executeUpdate(codigoCreacion);
            sePudo=true;
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }         

    private boolean crearTablaSondaSets() {
//frec,gain,stc,lw,gs, escala,shift,Eexp,unidad de medida,unidad,hora,lat,e/o,long,n/s,Velocidad,rumbo,fecha,velocidad promedio,profundidad ,c
// 0,   30,  30,  22,  1,   7000,0,   2,   40,  0,   201035, 4156.9628S, W, 06139.5662W, S, 010., 111., 100511, 10,  6207,  18,
//Escala,Eexp,profundidad,shift        estan expresadas en centimetros
//temperatura esta expresada en grados Celsius        
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE SondaSets ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  frecuencia         integer," //LOW/MID/HIGH
            + "  ganancia           integer,"
            + "  stc                integer," //STC (Valor de Ganancia variable)
            + "  lineaBlanca        integer," //WL (White Line)                    
            + "  velPantalla        integer," //GS (G.SPEEP: Velocidad Pantalla)                    
            + "  escala             integer," //Escala (Unit) [centimetros]
            + "  shift              integer," //Desplazamiento [centimetros]
            + "  expander           integer," //Tamaño de expander del fondo [centimetros]                    
            + "  unidadMedida       integer," //???????
            + "  unidad             integer" //???????
            + ");";            
            getStatement().executeUpdate(codigoCreacion);
            sePudo=true;
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;        
    }    

    private boolean crearTriggersIdSondaSets() {
        boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idSondaSets -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_punto_idSondaSets "
            + "BEFORE INSERT ON puntos "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM SondaSets WHERE id = NEW.idSondaSets) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"puntos\" violates foreign key constraint \"fk_idSondaSets\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_punto_idSondaSets "
            + "BEFORE UPDATE ON puntos "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM SondaSets WHERE id = NEW.idSondaSets) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"puntos\" violates foreign key constraint \"fk_idSondaSets\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_punto_idSondaSets "
            + "BEFORE DELETE ON SondaSets "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idSondaSets FROM puntos WHERE idSondaSets = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"SondaSets\" violates foreign key constraint \"fk_idSondaSets\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_puntos_SondaSets_id "
            + "BEFORE DELETE ON SondaSets "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from puntos WHERE idSondaSets = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
    }
    
    public static void main(String[] args) {
        controllers.ControllerCampania.getInstance().nuevaCampania("Campaña de prueba", "Gavilan", "El Marisco 1");
        BrokerHistorico.getInstance().creaConexionNueva();
    }

    /**
     * @return the folderHistorico
     */
    public String getFolderNameHistorico() {
        return folderNameHistorico;
    }

    /**
     * @param folderHistorico the folderHistorico to set
     */
    public void setFolderNameHistorico(String folderHistorico) {
        this.folderNameHistorico = folderHistorico;
    }
    
    
}