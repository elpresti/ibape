/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
 public abstract class BrokerPpal {

    static BrokerPpal unicaInstancia;
    private Connection conexion;
    private Statement statement;
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
            if (archivoDb.exists()) { yaExiste=true; } 
            Class.forName("org.sqlite.JDBC");
            // Observen que dbIbape.db es la base de datos y se crea en el directorio de trabajo 
            setConexion(DriverManager.getConnection("jdbc:sqlite:" + getDbName()));

            setStatement(getConexion().createStatement());            
            
            if (!(yaExiste)) { creaTodasLasTablas(); }
            //getStatement().close();
            //getConexion().close();
            sePudo = true;
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
            sePudo = sePudo && crearTablaCondiciones(); 
            sePudo = sePudo && crearTablaAlertas(); 
            sePudo = sePudo && crearTablaCondicionesPorAlerta(); 
            
            //Creacion de Triggers
            
            sePudo = sePudo && crearTriggersIdCampania();
            sePudo = sePudo && crearTriggersIdLance();
            sePudo = sePudo && crearTriggersIdEspecie();
            sePudo = sePudo && crearTriggersIdPois();
            sePudo = sePudo && crearTriggersIdAlerta();
            
            //Analizar si es necesario crear triggers para las FK restantes, por ahora, considero que no.
            
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;
            }

        return sePudo;
    }
    
    public boolean creaArchivoDb() {
        boolean sePudo= false;
        
        
        return sePudo;
    }
      
/*
     public static void main(String[] args) {
        //boolean a = BrokerPpal.getInstance().creaConexionNueva();
         BrokerCampania brokerCampania = new BrokerCampania();
        //BrokerPpal.getInstance().creacionTest();
    }
*/
    
/*
    public static BrokerPpal getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerPpal();       
       return unicaInstancia;
    }
*/
    
    public boolean crearTablaCondiciones(){
        boolean sePudo = false;
        try {
        String codigoCreacion = "CREATE TABLE Condiciones ("
            + "  id          integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  valor1      float(50) NOT NULL,"
            + "  valor2      float(50) NOT NULL,"
            + "  relacion    nvarchar(50) NOT NULL,"
            + "  idVariable  integer NOT NULL,"
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idVariable)"
            + "    REFERENCES Variable(id)"
            + ");";
            getStatement().executeUpdate(codigoCreacion);
            
            //codigo de los 4 triggers de la FK idVariable
            
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    public boolean crearTablaCampanias(){
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
            
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    
    
    public boolean crearTablaLances(){
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Lances ("
            + "  id             integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  fyhini         TIMESTAMP NOT NULL,"
            + "  posinilat      float(30) NOT NULL,"
            + "  posinilon      float(30) NOT NULL,"
            + "  fyhfin         TIMESTAMP NOT NULL,"
            + "  posfinlat      float(30) NOT NULL,"
            + "  posfinlon      float(30) NOT NULL,"
            + "  comentarios    nvarchar(500),"
            + "  idcampania     integer NOT NULL,"    
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idcampania)"
            + "    REFERENCES Campanias(id)"
            + ");";
            getStatement().executeUpdate(codigoCreacion);                        
            
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    
    public boolean crearTriggersIdCampania(){
        boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idCampania -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_lance_idCampania "
            + "BEFORE INSERT ON lances "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"lances\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            codigoCreacion=
            "CREATE TRIGGER fki_pois_idCampania "
            + "BEFORE INSERT ON pois "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"pois\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
            
            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_lance_idCampania "
            + "BEFORE UPDATE ON lances "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"lances\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";           

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
            
            codigoCreacion=
            "CREATE TRIGGER fku_pois_idCampania "
            + "BEFORE UPDATE ON pois "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campanias WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"pois\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
          

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_lance_idCampania "
            + "BEFORE DELETE ON campanias "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idCampania FROM lances WHERE idCampania = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"campanias\" violates foreign key constraint \"fk_idCampania\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            codigoCreacion=
            "CREATE TRIGGER fkd_pois_idCampania "
            + "BEFORE DELETE ON campanias "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idCampania FROM pois WHERE idCampania = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"campanias\" violates foreign key constraint \"fk_idCampania\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
            
            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_lances_campanias_id "
            + "BEFORE DELETE ON campanias "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from lances WHERE idCampania = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
            
            codigoCreacion=
            "CREATE TRIGGER fkd_pois_campanias_id "
            + "BEFORE DELETE ON campanias "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from pois WHERE idCampania = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
            
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
    }
    
    public boolean crearTablaPois(){
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Pois ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  idcategoriapoi     integer NOT NULL,"                        
            + "  idcampania         integer NOT NULL,"    
            + "  posLat             float(30) NOT NULL,"
            + "  posLon             float(30) NOT NULL,"
            + "  pathimg            nvarchar(100),"                    
            + "  fechahora          TIMESTAMP NOT NULL,"
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idcampania)"
            + "    REFERENCES Campanias(id)"
            + "  FOREIGN KEY (idcategoriapoi)"
            + "    REFERENCES CategoriasPoi(id)"                    
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public int getIdUltimoInsert(){
        int salida = 0;
        try {
            ResultSet rs = getStatement().getGeneratedKeys();            
            if (rs.next()) {
                salida = rs.getInt(1);
            }            
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return salida;
    }

    public boolean crearTablaCajones() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Cajones ("
            + "  idLance            integer NOT NULL,"
            + "  idEspecie          integer NOT NULL,"                        
            + "  cantidad           integer NOT NULL,"
            + "  PRIMARY KEY        (idLance,idEspecie),"
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idLance)"
            + "    REFERENCES Lances(id)"
            + "  FOREIGN KEY (idEspecie)"
            + "    REFERENCES Especies(id)"                    
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
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
                sePudo=true;
            }
            catch(Exception e)
                { Logueador.getInstance().agregaAlLog(e.toString()); }
            return sePudo;
        }

    private boolean crearTablaMarcas() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Marcas ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  idPois             integer NOT NULL,"                        
            + "  prof               float NOT NULL," 
            + "  areaFisica         nvarchar(100) NOT NULL," 
            + "  areaImagen         nvarchar(100) NOT NULL," 
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idPois)"
            + "    REFERENCES Pois(id)"                   
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    public boolean crearTablaCategoriasPoi() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE CategoriasPoi ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  titulo             nvarchar(100) NOT NULL,"                        
            + "  pathIcono          nvarchar(300) NOT NULL"                   
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    private boolean crearTablaCondicionesPorAlerta() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE CondicionesPorAlerta ("
            + "  idCondicion        integer NOT NULL,"
            + "  idAlerta           integer NOT NULL,"                        
            + "  PRIMARY KEY        (idCondicion,idAlerta),"
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idCondicion)"
            + "    REFERENCES Condiciones(id)"
            + "  FOREIGN KEY (idAlerta)"
            + "    REFERENCES Alertas(id)"                    
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    private boolean crearTablaVariables() {
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Variables ("
            + "  id                 integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  nombre             nvarchar(100) NOT NULL,"
            + "  opcionesCombo      nvarchar(100) NOT NULL,"
            + "  cantValores        int NOT NULL"                   
            + ");";
            getStatement().executeUpdate(codigoCreacion);                                    
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
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
            
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    private boolean crearTriggersIdLance() {
           boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idLance -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_cajones_idLance "
            + "BEFORE INSERT ON cajones "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM lances WHERE id = NEW.idLance) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"Cajones\" violates foreign key constraint \"fk_idLance\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_cajones_idLance "
            + "BEFORE UPDATE ON cajones "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM lances WHERE id = NEW.idLance) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"cajones\" violates foreign key constraint \"fk_idLance\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_cajones_idLance "
            + "BEFORE DELETE ON lances "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idLance FROM cajones WHERE idLance = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"lances\" violates foreign key constraint \"fk_idLance\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_cajones_lances_id "
            + "BEFORE DELETE ON lances "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from cajones WHERE idLance = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
    }

    private boolean crearTriggersIdEspecie() {
        boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idEspecie -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_cajones_idEspecie "
            + "BEFORE INSERT ON cajones "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM especies WHERE id = NEW.idEspecie) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"Cajones\" violates foreign key constraint \"fk_idEspecie\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_cajones_idEspecie "
            + "BEFORE UPDATE ON cajones "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM especies WHERE id = NEW.idEspecie) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"cajones\" violates foreign key constraint \"fk_idEspecie\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_cajones_idEspecie "
            + "BEFORE DELETE ON especies "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idEspecie FROM cajones WHERE idEspecie = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"especies\" violates foreign key constraint \"fk_idEspecie\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_cajones_especies_id "
            + "BEFORE DELETE ON especies "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from cajones WHERE idEspecie = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
    }

    private boolean crearTriggersIdPois() {
    boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idPois -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_marcas_idPois "
            + "BEFORE INSERT ON marcas "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM pois WHERE id = NEW.idPois) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"marcas\" violates foreign key constraint \"fk_idPois\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_marcas_idPois "
            + "BEFORE UPDATE ON marcas "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM pois WHERE id = NEW.idPois) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"marcas\" violates foreign key constraint \"fk_idPois\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_marcas_idPois "
            + "BEFORE DELETE ON pois "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idPois FROM marcas WHERE idPois = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"pois\" violates foreign key constraint \"fk_idPois\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_marcas_pois_id "
            + "BEFORE DELETE ON pois "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from marcas WHERE idPois = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;

    }

    private boolean crearTriggersIdAlerta() {
        boolean sePudo=true;
        try {                                                
            //---------------- triggers de la FK idAlerta -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_CondicionesPorAlerta_idAlerta "
            + "BEFORE INSERT ON CondicionesPorAlerta "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM Alertas WHERE id = NEW.idAlerta) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"CondicionesPorAlerta\" violates foreign key constraint \"fk_idAlerta\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_CondicionesPorAlerta_idAlerta "
            + "BEFORE UPDATE ON CondicionesPorAlerta "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM Alertas WHERE id = NEW.idAlerta) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"CondicionesPorAlerta\" violates foreign key constraint \"fk_idAlerta\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_CondicionesPorAlerta_idAlerta "
            + "BEFORE DELETE ON Alertas "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idAlerta FROM CondicionesPorAlerta WHERE idAlerta = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"Alertas\" violates foreign key constraint \"fk_idAlerta\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /*Borrado en cascada */
            codigoCreacion=
            "CREATE TRIGGER fkd_CondicionesPorAlerta_Alerta_id "
            + "BEFORE DELETE ON Alertas "
            + "FOR EACH ROW BEGIN "
            + "   DELETE from CondicionesPorAlerta WHERE idAlerta = OLD.id; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
}

}


