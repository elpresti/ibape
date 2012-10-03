/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sebastian
 */
public abstract class BrokerPpal {

    static BrokerPpal unicaInstancia;
    private Connection conexion;
    private Statement statement;
    private Statement statementAlerta;
    private ResultSet resultSet;
    private boolean conectado;
    private String dbName;

    public BrokerPpal() {
        inicializador();
    }

    public boolean creaConexionNueva() {
        boolean sePudo = false;
        try {
            File archivoDb = new File(getDbName());
            boolean yaExiste = false;
            if (archivoDb.exists()) {
                yaExiste = true;
            }
            Class.forName("org.sqlite.JDBC");
            // Observen que dbIbape.db es la base de datos y se crea en el directorio de trabajo 
            setConexion(DriverManager.getConnection("jdbc:sqlite:" + getDbName()));

            setStatement(getConexion().createStatement());

            if (!(yaExiste)) {
                if (creaTodasLasTablas()){
                    sePudo = true;
                }
            }
            //getStatement().close();
            //getConexion().close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return sePudo;
    }

    public void inicializador() {
        setDbName("dbIbape.db");
        setConectado(false);
        this.creaConexionNueva();
    }

    /**
     * @return the connect
     */
    public Connection getConexion() {
        try {
            if ((conexion == null) || (conexion != null && conexion.isClosed())) {
                creaConexionNueva();
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return conexion;
    }

    /**
     * @param connect the connect to set
     */
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        if (statement == null) {
            try {
                setStatement(getConexion().createStatement());
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
        }
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
     * @return the conectado
     */
    public boolean isConectado() {
        return conectado;
    }

    /**
     * @param conectado the conectado to set
     */
    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean creaTodasLasTablas() {
        boolean sePudo = true;
        try {
            // Creacion de Tablas            
            sePudo = sePudo && crearTablaCampanias();
            sePudo = sePudo && crearTablaLances();
            sePudo = sePudo && crearTablaEspecies();
            sePudo = sePudo && crearTablaCajones();
            sePudo = sePudo && crearTablaPois();
            sePudo = sePudo && crearTablaMarcas();
            sePudo = sePudo && crearTablaCategoriasPoi();
            sePudo = sePudo && crearTablaVariables();
            sePudo = sePudo && crearTablaAlertas();
            sePudo = sePudo && crearTablaRelaciones();
            sePudo = sePudo && crearTablaCondiciones();
            sePudo = sePudo && crearTablaRelacionesXVariables();
            sePudo = sePudo && crearTablaOcurAlertas();


            //Creacion de Triggers

            sePudo = sePudo && crearTriggersIdCampania();
            sePudo = sePudo && crearTriggersIdLance();
            sePudo = sePudo && crearTriggersIdEspecie();
//          sePudo = sePudo && crearTriggersIdPois();
            sePudo = sePudo && crearTriggersIdAlerta();

            //Analizar si es necesario crear triggers para las FK restantes, por ahora, considero que no.

            //Inserción de datos estáticos en la BD

            sePudo = sePudo && persistencia.BrokerAlertas.getInstance().insertaRelaciones();
            sePudo = sePudo && persistencia.BrokerAlertas.getInstance().insertaVariables();
            sePudo = sePudo && persistencia.BrokerAlertas.getInstance().insertaRelacionesxVariables();
            
            //sePudo = sePudo && persistencia.BrokerPOIs.getInstance().insertaCatPoi();
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }

        return sePudo;
    }

    public boolean creaArchivoDb() {
        boolean sePudo = false;


        return sePudo;
    }

    /*
    public static void main(String[] args) {
    //boolean a = BrokerPpal.getInstance().creaConexionNueva();
    //BrokerCampania brokerCampania = new BrokerCampania();
    BrokerAlertas brokerAlertas = new BrokerAlertas();
    //BrokerPpal.getInstance().creacionTest();
    }
     */
    public boolean crearTablaCondiciones() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Condiciones ("
                    + "  id          integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  valor1      nvarchar(50) NOT NULL,"
                    + "  valor2      nvarchar(50) NOT NULL,"
                    + "  idVariable  integer NOT NULL,"
                    + "  idAlerta    integer NOT NULL,"
                    + "  idRelacion  integer NOT NULL,"
                    + "  descripcion nvarchar(200) NOT NULL,"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idVariable)"
                    + "    REFERENCES Variables(id)"
                    + "  FOREIGN KEY (idAlerta)"
                    + "    REFERENCES Alertas(id)"
                    + "  FOREIGN KEY (idRelacion)"
                    + "    REFERENCES Relaciones(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);

            //codigo de los 4 triggers de la FK idVariable

            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaCampanias() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Campanias ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  fechainicio        TIMESTAMP NOT NULL,"
                    + "  fechafin           TIMESTAMP NULL,"
                    + "  barco              nvarchar(50),"
                    + "  capitan            nvarchar(50),"
                    + "  descripcion        nvarchar(100),"
                    + "  folderhistorico    nvarchar(200) NULL,"
                    + "  estado             integer"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);

            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaLances() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Lances ("
                    + "  id             integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  fyHIni         TIMESTAMP NOT NULL,"
                    + "  posIniLat      float(30) NOT NULL,"
                    + "  posIniLon      float(30) NOT NULL,"
                    + "  fyHFin         TIMESTAMP,"
                    + "  posFinLat      float(30),"
                    + "  posFinLon      float(30),"
                    + "  comentarios    nvarchar(500),"
                    + "  idCampania     integer NOT NULL,"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idCampania)"
                    + "    REFERENCES Campanias(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);

            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro Statements usados, para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTriggersIdCampania() {
        boolean sePudo = true;
        try {
            //---------------- triggers de la FK idCampania -----------------------

            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion =
                    "CREATE TRIGGER fki_lance_idCampania "
                    + "BEFORE INSERT ON lances "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"lances\" violates foreign key constraint \"fk_idCampania\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            codigoCreacion =
                    "CREATE TRIGGER fki_pois_idCampania "
                    + "BEFORE INSERT ON pois "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"pois\" violates foreign key constraint \"fk_idCampania\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fku_lance_idCampania "
                    + "BEFORE UPDATE ON lances "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"lances\" violates foreign key constraint \"fk_idCampania\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            codigoCreacion =
                    "CREATE TRIGGER fku_pois_idCampania "
                    + "BEFORE UPDATE ON pois "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"pois\" violates foreign key constraint \"fk_idCampania\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fkd_lance_idCampania "
                    + "BEFORE DELETE ON campanias "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idCampania FROM lances WHERE idCampania = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"campanias\" violates foreign key constraint \"fk_idCampania\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            codigoCreacion =
                    "CREATE TRIGGER fkd_pois_idCampania "
                    + "BEFORE DELETE ON campanias "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idCampania FROM pois WHERE idCampania = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"campanias\" violates foreign key constraint \"fk_idCampania\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*Borrado en cascada */
            codigoCreacion =
                    "CREATE TRIGGER fkd_lances_campanias_id "
                    + "BEFORE DELETE ON campanias "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from lances WHERE idCampania = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            codigoCreacion =
                    "CREATE TRIGGER fkd_pois_campanias_id "
                    + "BEFORE DELETE ON campanias "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from pois WHERE idCampania = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaPois() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Pois ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  idCategoriaPoi     integer NOT NULL,"
                    + "  idCampania         integer NOT NULL,"
                    + "  posLat             float(30) NOT NULL,"
                    + "  posLon             float(30) NOT NULL,"
                    + "  pathImg            nvarchar(100),"
                    + "  fechaHora          TIMESTAMP NOT NULL,"
                    + "  descripcion        nvarchar(500),"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idCampania)"
                    + "    REFERENCES Campanias(id)"
                    + "  FOREIGN KEY (idCategoriaPoi)"
                    + "    REFERENCES CategoriasPoi(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro Statements usados, para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public int getIdUltimoInsert() {
        int salida = 0;
        ResultSet rs = null;
        try {
            rs = getStatement().getGeneratedKeys();
            if (rs.next()) {
                salida = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (rs != null) {
                rs.close();
            }
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return salida;
    }

    public boolean crearTablaCajones() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Cajones ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  idLance            integer NOT NULL,"
                    + "  idEspecie          integer NOT NULL,"
                    + "  cantidad           integer NOT NULL,"
                    //+ "  PRIMARY KEY        (idLance,idEspecie),"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idLance)"
                    + "    REFERENCES Lances(id)"
                    + "  FOREIGN KEY (idEspecie)"
                    + "    REFERENCES Especies(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaEspecies() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Especies ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  nombre             nvarchar(100)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            //codigoCreacion = "insert into Especies (nombre) values ('This is sample data',3)";
            getStatement().executeUpdate("insert into Especies (nombre) values ('abadejo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('anchoa')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('anchoíta')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('atún')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('besugo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('bacalao')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('brótola')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('caballa')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('cazón')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('cornalito')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('corvinas')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('escualo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('gatuzo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('jurel')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('lisa')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('lenguado')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('mero')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('merluza')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('mojarra')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('palometa')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pejerrey')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pescadilla')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pez palo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pez gallo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pez elefante')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('pez limón')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('polaca')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('róbalo')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('sardina')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('tararira')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('tiburón')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('salmon')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('otros')");
            getStatement().executeUpdate("insert into Especies (nombre) values ('variada')");
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTablaMarcas() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Marcas ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
//                  + "  idPois             integer NOT NULL,"
                    + "  latitud            float(30) NOT NULL,"
                    + "  longitud           float(30) NOT NULL,"
                    + "  fechaYhora         TIMESTAMP NOT NULL,"
                    + "  profundidad        float NULL,"
                    + "  areaFisica         nvarchar(100) NULL,"
                    + "  pxXenImg           integer NOT NULL,"
                    + "  pxYenImg           integer NOT NULL,"
                    + "  imgFileName        nvarchar(100) NOT NULL"
//                  + "  /* Foreign keys */ "
//                  + "  FOREIGN KEY (idPois)"
//                  + "    REFERENCES Pois(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaCategoriasPoi() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE CategoriasPoi ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  titulo             nvarchar(100) NOT NULL,"
                    + "  fileNameIcono      nvarchar(300) NOT NULL"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTablaVariables() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Variables ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  nombre             nvarchar(100) NOT NULL,"
                    + "  unidad             nvarchar(100) NOT NULL,"
                    + "  ejemplo            nvarchar(100) NOT NULL"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTablaAlertas() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Alertas ("
                    + "  id             integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  titulo         nvarchar(100) NOT NULL,"
                    + "  estado         boolean NOT NULL,"
                    + "  mensaje        nvarchar(300) NOT NULL,"
                    + "  flagsAcciones  int NOT NULL"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);

            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTriggersIdLance() {
        boolean sePudo = true;
        try {
            //---------------- triggers de la FK idLance -----------------------

            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion =
                    "CREATE TRIGGER fki_cajones_idLance "
                    + "BEFORE INSERT ON cajones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM lances WHERE id = NEW.idLance) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"Cajones\" violates foreign key constraint \"fk_idLance\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fku_cajones_idLance "
                    + "BEFORE UPDATE ON cajones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM lances WHERE id = NEW.idLance) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"cajones\" violates foreign key constraint \"fk_idLance\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fkd_cajones_idLance "
                    + "BEFORE DELETE ON lances "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idLance FROM cajones WHERE idLance = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"lances\" violates foreign key constraint \"fk_idLance\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*Borrado en cascada */
            codigoCreacion =
                    "CREATE TRIGGER fkd_cajones_lances_id "
                    + "BEFORE DELETE ON lances "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from cajones WHERE idLance = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTriggersIdEspecie() {
        boolean sePudo = true;
        try {
            //---------------- triggers de la FK idEspecie -----------------------

            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion =
                    "CREATE TRIGGER fki_cajones_idEspecie "
                    + "BEFORE INSERT ON cajones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM especies WHERE id = NEW.idEspecie) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"Cajones\" violates foreign key constraint \"fk_idEspecie\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fku_cajones_idEspecie "
                    + "BEFORE UPDATE ON cajones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM especies WHERE id = NEW.idEspecie) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"cajones\" violates foreign key constraint \"fk_idEspecie\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fkd_cajones_idEspecie "
                    + "BEFORE DELETE ON especies "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idEspecie FROM cajones WHERE idEspecie = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"especies\" violates foreign key constraint \"fk_idEspecie\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*Borrado en cascada */
            codigoCreacion =
                    "CREATE TRIGGER fkd_cajones_especies_id "
                    + "BEFORE DELETE ON especies "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from cajones WHERE idEspecie = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTriggersIdPois() {
        boolean sePudo = true;
        try {
            //---------------- triggers de la FK idPois -----------------------

            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion =
                    "CREATE TRIGGER fki_marcas_idPois "
                    + "BEFORE INSERT ON marcas "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM pois WHERE id = NEW.idPois) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"marcas\" violates foreign key constraint \"fk_idPois\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fku_marcas_idPois "
                    + "BEFORE UPDATE ON marcas "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM pois WHERE id = NEW.idPois) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"marcas\" violates foreign key constraint \"fk_idPois\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fkd_marcas_idPois "
                    + "BEFORE DELETE ON pois "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idPois FROM marcas WHERE idPois = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"pois\" violates foreign key constraint \"fk_idPois\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            
            /*Borrado en cascada */
            codigoCreacion =
                    "CREATE TRIGGER fkd_marcas_pois_id "
                    + "BEFORE DELETE ON pois "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from marcas WHERE idPois = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;

    }

    private boolean crearTriggersIdAlerta() {
        boolean sePudo = true;
        try {
            //---------------- triggers de la FK idAlerta -----------------------

            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion =
                    "CREATE TRIGGER fki_Condiciones_idAlerta "
                    + "BEFORE INSERT ON Condiciones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM Alertas WHERE id = NEW.idAlerta) IS NULL)"
                    + "     THEN RAISE(ABORT, 'insert on table \"Condiciones\" violates foreign key constraint \"fk_idAlerta\"')"
                    + "  END; "
                    + "END;";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fku_Condiciones_idAlerta "
                    + "BEFORE UPDATE ON Condiciones "
                    + "FOR EACH ROW BEGIN"
                    + "  SELECT CASE"
                    + "     WHEN ((SELECT id FROM Alertas WHERE id = NEW.idAlerta) IS NULL)"
                    + "     THEN RAISE(ABORT, 'update on table \"Condiciones\" violates foreign key constraint \"fk_idAlerta\"')"
                    + "  END; "
                    + "END;";

            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion =
                    "CREATE TRIGGER fkd_Condiciones_idAlerta "
                    + "BEFORE DELETE ON Alertas "
                    + "FOR EACH ROW BEGIN "
                    + "  SELECT CASE "
                    + "    WHEN ((SELECT idAlerta FROM Condiciones WHERE idAlerta = OLD.id) IS NOT NULL) "
                    + "    THEN RAISE(ABORT, 'delete on table \"Alertas\" violates foreign key constraint \"fk_idAlerta\"') "
                    + "  END; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
            /*Borrado en cascada */
            codigoCreacion =
                    "CREATE TRIGGER fkd_Condiciones_Alerta_id "
                    + "BEFORE DELETE ON Alertas "
                    + "FOR EACH ROW BEGIN "
                    + "   DELETE from Condiciones WHERE idAlerta = OLD.id; "
                    + "END; ";
            if (getStatement().executeUpdate(codigoCreacion) == 0){
                sePudo = sePudo && false;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo = false;
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTablaRelaciones() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Relaciones ("
                    + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  descripcion        nvarchar(100) NOT NULL,"
                    + "  cantValores        nvarchar(100) NOT NULL"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean crearTablaRelacionesXVariables() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE RelacionesXVariables ("
                    + "  idVariable         integer NOT NULL,"
                    + "  idRelacion         integer NOT NULL,"
                    + "  PRIMARY KEY        (idVariable,idRelacion)"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idVariable)"
                    + "    REFERENCES Variables(id)"
                    + "  FOREIGN KEY (idRelacion)"
                    + "    REFERENCES Relaciones(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try {//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null) {
                getStatement().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public void closeDbConnection() {
        try {
            if (getStatement() != null) {
                getStatement().close();
            }

            if (getConexion() != null) {
                getConexion().close();
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            System.out.println(e);
        }
    }

    private boolean crearTablaOcurAlertas() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE OcurAlertas ("
                    + "  id          integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "  valor1      nvarchar(50) NULL,"
                    + "  valor2      nvarchar(50) NULL,"
                    + "  valor3      nvarchar(50) NULL,"
                    + "  valor4      nvarchar(50) NULL,"
                    + "  valor5      nvarchar(50) NULL,"
                    + "  valor6      nvarchar(50) NULL,"
                    + "  valor7      nvarchar(50) NULL,"
                    + "  valor8      nvarchar(50) NULL,"
                    + "  valor9      nvarchar(50) NULL,"
                    + "  latitud     float(30) NOT NULL,"
                    + "  longitud    float(30) NOT NULL,"                    
                    + "  fyhini      TIMESTAMP NOT NULL,"
                    + "  fyhfin      TIMESTAMP NOT NULL,"
                    + "  idAlerta    integer NOT NULL,"
                    + "  /* Foreign keys */ "
                    + "  FOREIGN KEY (idAlerta)"
                    + "    REFERENCES Alertas(id)"
                    + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try{//ya la use, asique cierro ResultSets y Statements usados para evitar la excepcion DatabaseLocked
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
     
}
