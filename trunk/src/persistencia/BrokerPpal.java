/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.*;

/**
 *
 * @author Sebastian
 */
public class BrokerPpal {

    static BrokerPpal unicaInstancia;
    private Connection conexion;
    private Statement statement;
    private ResultSet resultSet;
    private boolean conectado;
    private String dbName;

    private BrokerPpal() {
        inicializador();
    }

    public boolean creaConexionNueva() {
        boolean sePudo = false;
        try {
            Class.forName("org.sqlite.JDBC");
            // Observen que dbIbape.db es la base de datos y se crea en el directorio de trabajo 
            setConexion(DriverManager.getConnection("jdbc:sqlite:" + getDbName()));

            setStatement(getConexion().createStatement());            
            
            creaTodasLasTablas();

            getStatement().close();
            getConexion().close();
            sePudo = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return sePudo;
    }    
    
    public void inicializador() {
        setDbName("dbIbape.db");
        setConectado(false);
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
            sePudo = sePudo && crearTablaCampania();
            sePudo = sePudo && crearTablaLance();
            sePudo = sePudo && crearTriggersIdCampania();
            sePudo = sePudo && crearTablaCondicion();                            
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
      

     public static void main(String[] args) {
        boolean a = BrokerPpal.getInstance().creaConexionNueva();
        //BrokerPpal.getInstance().creacionTest();
    }


    public static BrokerPpal getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerPpal();       
       return unicaInstancia;
    }
    
    public boolean crearTablaCondicion(){
        boolean sePudo = false;
        try {
        String codigoCreacion = "CREATE TABLE Condicion ("
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

    public boolean crearTablaCampania(){
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Campania ("
            + "  id          integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  fechainicio    TIMESTAMP NOT NULL,"
            + "  fechafin       TIMESTAMP NOT NULL,"
            + "  pathcampania   nvarchar(200) NOT NULL,"
            + "  barco          nvarchar(50),"
            + "  capitan        nvarchar(50),"
            + "  estado         integer"                    
            + ");";
            getStatement().executeUpdate(codigoCreacion);                        
            
            sePudo=true;
        }
        catch(Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    
    
    public boolean crearTablaLance(){
        boolean sePudo = false;
        try {
            String codigoCreacion = "CREATE TABLE Lance ("
            + "  id          integer PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "  fyhini TIMESTAMP NOT NULL,"
            + "  posinilat      float(30) NOT NULL,"
            + "  posinilon      float(30) NOT NULL,"
            + "  fyhfin TIMESTAMP NOT NULL,"
            + "  posfinlat      float(30) NOT NULL,"
            + "  posfinlon      float(30) NOT NULL,"
            + "  comentarios    nvarchar(500),"
            + "  idcampania     integer NOT NULL,"    
            + "  /* Foreign keys */ "
            + "  FOREIGN KEY (idcampania)"
            + "    REFERENCES Campania(id)"
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
            
            /* ejemplo de TRIGGER ON INSERT, si la clave primaria no permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_lance_idCampania "
            + "BEFORE INSERT ON lance "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campania WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"lance\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /* ejemplo de TRIGGER ON UPDATE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_lance_idCampania "
            + "BEFORE UPDATE ON lance "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((SELECT id FROM campania WHERE id = NEW.idCampania) IS NULL)"
            + "     THEN RAISE(ABORT, 'update on table \"lance\" violates foreign key constraint \"fk_idCampania\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /* ejemplo de TRIGGER ON DELETE, si la clave primaria no permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_lance_idCampania "
            + "BEFORE DELETE ON campania "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((SELECT idCampania FROM lance WHERE idCampania = OLD.id) IS NOT NULL) "
            + "    THEN RAISE(ABORT, 'delete on table \"campania\" violates foreign key constraint \"fk_idCampania\"') "
            + "  END; "
            + "END; ";
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }


            /* ejemplo para borrado en cascada */
//            CREATE TRIGGER fkd_bar_foo_id
//            BEFORE DELETE ON campania
//            FOR EACH ROW BEGIN
//                DELETE from lance WHERE idCampania = OLD.id;
//            END;                    
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;  
            }                
        return sePudo;
    }
    
}
