/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import dataManager.Punto;
/**
 *
 * @author Sebastian
 */
public class BrokerDbMapa {
    static BrokerDbMapa unicaInstancia;    
    private boolean conectado=false;
    private boolean dbExiste=false;
    private boolean tablaExiste=false;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;        
    private java.sql.Timestamp ultimoInsert=new java.sql.Timestamp(new java.util.Date().getTime());
    private String direcWebServer = "localhost";
    private int puertoDbMapa = 7188;
    private int puertoWebServer = 4001;
    private String db_user = "root";
    private String db_passwd = ""; //ó "password";
    private String tableName = "PUNTOS";
    private String dbName = "basedatos";    
    private String url = "jdbc:mysql://"+getDirecWebServer()+":"+getPuertoDbMapa(); // ó "jdbc:mysql://localhost:3307/";
    //orden de los campos de la estructura de la tabla
    private int campoIDpos = 0;
    private int campoFECHApos = 1;
    private int campoLATITUDpos = 2;
    private int campoLONGITUDpos = 3;
    private int campoVELOCIDADpos = 4;
    private int campoPROFUNDIDADpos = 5;
    private int campoOBJETOpos = 6;
    private int campoTEMPAGUApos = 7;
    private int campoCOMENTARIOSpos = 8;
    private int campoLEIDOpos = 9;
    private int campoKMLpos = 10;
    
    private BrokerDbMapa(){
        dbLista();
    }
    
    public static BrokerDbMapa getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerDbMapa();       
       return unicaInstancia;
    }
       
    public boolean insert(Punto p) {
        boolean sePudo=false;
        try {     
             if (dbLista()){
                 java.sql.Timestamp fechaYhoraActual = p.getFechaYhora();
                 boolean conCamara=true;
                 if ((fechaYhoraActual.getTime() - getUltimoInsert().getTime())>=5000){ //5000=5seg.
                    PreparedStatement preparedStatement = getConnection()
                    .prepareStatement("insert into "+getDbName()+"."+getTableName()+" values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");          
                    preparedStatement.setTimestamp(Integer.valueOf(getCampoFECHApos()), fechaYhoraActual);
                    preparedStatement.setString(Integer.valueOf(getCampoLATITUDpos()), String.valueOf(p.getLatConNegativo()));
                    preparedStatement.setString(Integer.valueOf(getCampoLONGITUDpos()), String.valueOf(p.getLonConNegativo()));
                    preparedStatement.setString(Integer.valueOf(getCampoPROFUNDIDADpos()), String.valueOf(p.getProfundidad()));
                    preparedStatement.setString(Integer.valueOf(getCampoVELOCIDADpos()), String.valueOf(p.getVelocidad()));
                    preparedStatement.setString(Integer.valueOf(getCampoTEMPAGUApos()), String.valueOf(p.getTempAgua()));
                    preparedStatement.setString(Integer.valueOf(getCampoOBJETOpos()), String.valueOf(p.getObjeto()));
                    preparedStatement.setBoolean(Integer.valueOf(getCampoLEIDOpos()), false);
                    preparedStatement.setString(Integer.valueOf(getCampoCOMENTARIOSpos()),p.getComentarios());
                    preparedStatement.setString(Integer.valueOf(getCampoKMLpos()), p.convierteAkml(conCamara));
                    preparedStatement.executeUpdate();
                    sePudo=true;
                    setUltimoInsert(fechaYhoraActual);
                 }
             }
             return sePudo;
        }
        catch(Exception e) {
            System.out.println(e);
            return sePudo;
        }
    }

    public boolean modify(Punto p) {
        boolean sePudo=false;
        //...metodo pendiente
        return sePudo;
    }

    public boolean delete(Punto p) {
        boolean sePudo=false;
        //...metodo pendiente
        return sePudo;
    }
    
    public Punto[] readDb(int cantMax) {
        Punto[] salida=new Punto[4];       
        boolean sePudo=false;
        //...metodo pendiente
        return salida;
    }
    
    
    private void close() {
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
    }
    
   private boolean dbLista(){
      boolean salida=false;
      if (!(isConectado() && isDbExiste() && isTablaExiste())) {
         try {
             if (creaDbyTabla()) {
                System.out.println("ConexionDBmapa:OK, Db:OK, Tabla:OK");
                salida=true;
             }
             else { salida=false; }
         } catch (Exception e){             
             System.out.println("Error al conectar y crear la db y la tabla! ExisteConexionDbMapa:"+isConectado()+", ExisteDbMapa:"+isDbExiste()+", ExisteTablaMapa:"+isTablaExiste());
         }
      }
      else { salida=true; }
      return salida;
   }

    public boolean existeTabla(){
        boolean b=false;
        try{
              ResultSet rs1=getStatement().executeQuery("SELECT * FROM "+getTableName());
              getStatement().execute("TRUNCATE TABLE "+getTableName());
              b=true;
              return(b);
            }    	
             catch (Exception e){
                return b;
            }
    }

    public boolean existeDb(){
        boolean b=false;
        try{
             getStatement().executeQuery("use "+getDbName()+";");
             b=true;
             return b;
            }
             catch (Exception e){
                return b;
            }
    }

    public boolean creaDbyTabla() throws Exception {
        boolean db = false;
        boolean tabla = false;

        try {
            // Statements allow to issue SQL queries to the database
            setStatement(getConnection().createStatement());
            boolean a;
            String creacion;                      

            //creamos la base, si no existe
            if (!(this.existeDb())) {
                creacion = "create database "+getDbName()+";";
                getStatement().execute(creacion);
                setDbExiste(true);
                creacion = "use "+getDbName()+";";
                setResultSet(getStatement().executeQuery(creacion));
            }
            else { setDbExiste(true); }

            //creamos la tabla de PUNTOS, si no existe
            if (!(this.existeTabla())) {
                creacion = "CREATE TABLE "+getTableName()+" ("
                +"ID INT NOT NULL AUTO_INCREMENT, "
                +"FECHA DATETIME NOT NULL, "
                +"LATITUD VARCHAR(30) NOT NULL, "        
                +"LONGITUD VARCHAR(30) NOT NULL, "
                +"VELOCIDAD VARCHAR(30) NOT NULL, "
                +"PROFUNDIDAD VARCHAR(30) NOT NULL, " 
                +"OBJETO VARCHAR(40) NOT NULL, "   
                +"TEMPAGUA VARCHAR(40) NOT NULL, "   
                +"COMENTARIOS VARCHAR(400) NOT NULL, "
                +"LEIDO BOOLEAN NOT NULL DEFAULT 0, "
                +"KML TEXT NOT NULL, "
                +" PRIMARY KEY (ID)"
                +");";  
                a = getStatement().execute(creacion);
                setTablaExiste(true);
            }
            else { setTablaExiste(true); }
        }
        catch (Exception e) {
            throw e;
        } finally {
            close();
        }                        
        return (isConectado() && isDbExiste() && isTablaExiste());
    }

    public Connection getConnection() {
        try {
            if ((connect == null) || (connect.isClosed())){
                try {
                    // This will load the MySQL driver, each DB has its own driver
                    Class.forName("com.mysql.jdbc.Driver");
                    // Setup the connection with the DB
                    //connect = DriverManager.getConnection("jdbc:mysql://localhost:7188/?user=root&password=");
                    connect = DriverManager.getConnection(getUrl(), getDb_user(), getDb_passwd());
                    setConectado(true);
                } catch (ClassNotFoundException ex) {
                    setConectado(false);
                    System.out.println("Error! No se encontró la libreria para acceder a la base de datos del mapa");
                    //Logger.getLogger(BrokerDbMapa.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    setConectado(false);
                    System.out.println("Error! No se pudo conectar a la base de datos del mapa");
                    //Logger.getLogger(BrokerDbMapa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            System.out.println("No se pudo establecer conctacto con el servidor de base de datos");
            Logger.getLogger(BrokerDbMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the db_user
     */
    public String getDb_user() {
        return db_user;
    }

    /**
     * @param db_user the db_user to set
     */
    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    /**
     * @return the db_passwd
     */
    public String getDb_passwd() {
        return db_passwd;
    }

    /**
     * @param db_passwd the db_passwd to set
     */
    public void setDb_passwd(String db_passwd) {
        this.db_passwd = db_passwd;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    /**
     * @return the campoIDpos
     */
    public int getCampoIDpos() {
        return campoIDpos;
    }

    /**
     * @return the campoFECHApos
     */
    public int getCampoFECHApos() {
        return campoFECHApos;
    }

    /**
     * @return the campoLATITUDpos
     */
    public int getCampoLATITUDpos() {
        return campoLATITUDpos;
    }

    /**
     * @return the campoLONGITUDpos
     */
    public int getCampoLONGITUDpos() {
        return campoLONGITUDpos;
    }

    /**
     * @return the campoVELOCIDADpos
     */
    public int getCampoVELOCIDADpos() {
        return campoVELOCIDADpos;
    }

    /**
     * @return the campoPROFUNDIDADpos
     */
    public int getCampoPROFUNDIDADpos() {
        return campoPROFUNDIDADpos;
    }

    /**
     * @return the campoOBJETOpos
     */
    public int getCampoOBJETOpos() {
        return campoOBJETOpos;
    }

    /**
     * @return the campoCOMENTARIOSpos
     */
    public int getCampoCOMENTARIOSpos() {
        return campoCOMENTARIOSpos;
    }

    /**
     * @return the campoLEIDOpos
     */
    public int getCampoLEIDOpos() {
        return campoLEIDOpos;
    }

    /**
     * @return the campoKMLpos
     */
    public int getCampoKMLpos() {
        return campoKMLpos;
    }

    /**
     * @return the campoTEMPAGUApos
     */
    public int getCampoTEMPAGUApos() {
        return campoTEMPAGUApos;
    }

    /**
     * @param campoTEMPAGUApos the campoTEMPAGUApos to set
     */
    public void setCampoTEMPAGUApos(int campoTEMPAGUApos) {
        this.campoTEMPAGUApos = campoTEMPAGUApos;
    }

    /**
     * @return the ultimoInsert
     */
    public java.sql.Timestamp getUltimoInsert() {
        return ultimoInsert;
    }

    /**
     * @param ultimoInsert the ultimoInsert to set
     */
    public void setUltimoInsert(java.sql.Timestamp ultimoInsert) {
        this.ultimoInsert = ultimoInsert;
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
     * @return the dbExiste
     */
    public boolean isDbExiste() {
        return dbExiste;
    }

    /**
     * @param dbExiste the dbExiste to set
     */
    public void setDbExiste(boolean dbExiste) {
        this.dbExiste = dbExiste;
    }

    /**
     * @return the tablaExiste
     */
    public boolean isTablaExiste() {
        return tablaExiste;
    }

    /**
     * @param tablaExiste the tablaExiste to set
     */
    public void setTablaExiste(boolean tablaExiste) {
        this.tablaExiste = tablaExiste;
    }

    /**
     * @return the direcWebServer
     */
    public String getDirecWebServer() {
        return direcWebServer;
    }

    /**
     * @param direcWebServer the direcWebServer to set
     */
    public void setDirecWebServer(String direcWebServer) {
        this.direcWebServer = direcWebServer;
    }

    /**
     * @return the puertoWebServer
     */
    public int getPuertoWebServer() {
        return puertoWebServer;
    }

    /**
     * @param puertoWebServer the puertoWebServer to set
     */
    public void setPuertoWebServer(int puertoWebServer) {
        this.puertoWebServer = puertoWebServer;
    }

    /**
     * @return the puertoDbMapa
     */
    public int getPuertoDbMapa() {
        return puertoDbMapa;
    }

    /**
     * @param puertoDbMapa the puertoDbMapa to set
     */
    public void setPuertoDbMapa(int puertoDbMapa) {
        this.puertoDbMapa = puertoDbMapa;
    }
    
    
    
}