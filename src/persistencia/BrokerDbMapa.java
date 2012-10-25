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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.Punto;
import javax.swing.JOptionPane;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.POI;
import modelo.dataManager.PuntoHistorico;
    
    
    
    /**
 *
 * @author Sebastian
 */
public class BrokerDbMapa implements Runnable{
    Thread BdbMap;
    static BrokerDbMapa unicaInstancia; 
    private boolean usarMapaNavegacion;
    private boolean conectado;
    private boolean dbExiste;
    private boolean tablaExiste;
    private boolean inicializandoDb;
    private boolean conCamara;
    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;        
    private java.sql.Timestamp ultimoInsert;
    private String direcWebServer;
    private int puertoDbMapa;
    private int puertoWebServer;
    private String db_user;
    private String db_passwd; 
    private String tableName;
    private String dbName;    
    private String url;
    //orden de los campos de la estructura de la tabla
    private int campoIDpos;
    private int campoFECHApos;
    private int campoLATITUDpos;
    private int campoLONGITUDpos;
    private int campoVELOCIDADpos;
    private int campoPROFUNDIDADpos;
    private int campoOBJETOpos;
    private int campoTEMPAGUApos;
    private int campoCOMENTARIOSpos;
    private int campoLEIDOpos;
    private int campoKMLpos;
    
    private BrokerDbMapa(){
        inicializaBrokerDbMapa();
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
                    preparedStatement.setString(Integer.valueOf(getCampoKMLpos()), 
                            modelo.gisModule.GeneradorKML.getInstance().conviertePuntoNavegacionAkml(p,isConCamara()));
                    preparedStatement.executeUpdate();
                    sePudo=true;
                    setUltimoInsert(fechaYhoraActual);
                 }
             }
             return sePudo;
        }
        catch(Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            return sePudo;
        }
    }

    public boolean update(Punto p) {
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
    
   public boolean dbLista(){
      boolean salida=false;
      if (isInicializandoDb()){
          try{
              Thread.sleep(2000);//lo aguanto 2 segundos
          }catch(Exception e){
              Logueador.getInstance().agregaAlLog("BrokerDbMapa.dbLista(): "+e.toString());
          }
      }
      if (!(isConectado() && isDbExiste() && isTablaExiste())) {
         try {
             if (creaDbyTabla()) {
                String mensaje = "ConexionDBmapa:OK, Db:OK, Tabla:OK";
                System.out.println(mensaje);
                Logueador.getInstance().agregaAlLog(mensaje);
                salida=true;
             }
             else { salida=false; }
         } catch (Exception e){             
             String mensaje = "BrokerDbMapa.dbLista(): Error al conectar y crear la db y la tabla! ExisteConexionDbMapa:"+isConectado()+", ExisteDbMapa:"+isDbExiste()+", ExisteTablaMapa:"+isTablaExiste();
             System.out.println(mensaje);
             Logueador.getInstance().agregaAlLog(mensaje+"  \n"+e.toString());
         }
      }
      else { salida=true; }
      return salida;
   }

    public boolean existeTabla(){
        boolean b=false;
        try{
              ResultSet rs1=getStatement().executeQuery("SELECT * FROM "+getTableName());
              //getStatement().execute("TRUNCATE TABLE "+getTableName());
              b=true;
              return(b);
            }    	
             catch (Exception e){
                return b;
            }
    }

    public boolean existeDb(){
        boolean existe=false;
        try{
             getStatement().executeQuery("use "+getDbName()+";");
             existe=true;
        }
         catch (Exception e){
             //Logueador.getInstance().agregaAlLog("BrokerDbMapa.existeDb(): (Existe la db? NO)"+e.toString());
             //no existe
        }
        return existe;
    }

    public boolean creaDbyTabla() {
        boolean db = false;
        boolean tabla = false;

        try {
            // Statements allow to issue SQL queries to the database
            setInicializandoDb(true);
            if (!(isConectado() && isDbExiste() && isTablaExiste())){
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
                    +"OBJETO VARCHAR(40), "   
                    +"TEMPAGUA VARCHAR(40), "   
                    +"COMENTARIOS TEXT, "
                    +"LEIDO BOOLEAN NOT NULL DEFAULT 0, "
                    +"KML TEXT NOT NULL, "
                    +" PRIMARY KEY (ID)"
                    +");";  
                    a = getStatement().execute(creacion);
                    setTablaExiste(true);
                }
                else { setTablaExiste(true); }
            }
        }
        catch (Exception e) {
            //Logueador.getInstance().agregaAlLog("BrokerDbMapa.creaDbyTabla(): "+e.toString());
            Logueador.getInstance().agregaAlLog("Abriendo Mapa, aguarde un instante...");
            try{
                Thread.sleep(2000);
            }catch(Exception ex){
                Logueador.getInstance().agregaAlLog("BrokerDbMapa.creaDbyTabla(): "+ex.toString());
            }
            
        } finally {
            setInicializandoDb(false);
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

    public void run() {
        try {         
            BdbMap.sleep(5000);//espero 5 segundos para darle tiempo a q levante el webserver
            dbLista();
            //if (!(dbLista())) {
            //    JOptionPane.showMessageDialog(null, "No se pudo crear y conectar a la DB Mapa Navegacion");
            //}
        } catch (Exception e) {            
            System.out.println(e);            
            Logueador.getInstance().agregaAlLog(e.toString());
            //setTxtCuadro(e.toString());  //e.printStackTrace();
        }
        BdbMap=null;
    }

    public boolean disparaEjecucion(){
        boolean sePudo = false;
        if (!(isUsarMapaNavegacion())) {
            if (BdbMap == null) {
                try {
                    inicializaBrokerDbMapa();
                    BdbMap = new Thread(this);
                    BdbMap.setPriority(Thread.MIN_PRIORITY);
                    //BdbMap.currentThread().sleep(3000); //
                    BdbMap.start();
                } catch (Exception ex) {
                    Logueador.getInstance().agregaAlLog(ex.toString());
                }
            }
            sePudo=true;
        }
        return sePudo;
    }
    
    public boolean detieneEjecucion(){
        boolean sePudo = false;
        if (!(BdbMap == null)) {
           BdbMap = null;
        }
        sePudo=true;        
        return sePudo;
    }


    private boolean inicializaBrokerDbMapa(){
        boolean sePudo=false;                                     
        setInicializandoDb(false);
        setUsarMapaNavegacion(false);
        setConectado(false);
        setDbExiste(false);
        setTablaExiste(false);
        setConCamara(true);
        connect = null;
        setStatement(null);
        setResultSet(null);
        java.sql.Timestamp t =new java.sql.Timestamp(new java.util.Date().getTime()); setUltimoInsert(t);        
        setDirecWebServer("localhost");
        setPuertoDbMapa(7188);
        setPuertoWebServer(4001);
        setDb_user("root");
        setDb_passwd(""); //ó "password";
        setTableName("PUNTOS");
        setDbName("basedatos");
        setUrl("jdbc:mysql://"+getDirecWebServer()+":"+getPuertoDbMapa()); // ó "jdbc:mysql://localhost:3307/";
        //orden de los campos de la estructura de la tabla
        setCampoIDpos(0);
        setCampoFECHApos(1);
        setCampoLATITUDpos(2);
        setCampoLONGITUDpos(3);
        setCampoVELOCIDADpos(4);
        setCampoPROFUNDIDADpos(5);
        setCampoOBJETOpos(6);
        setCampoTEMPAGUApos(7);
        setCampoCOMENTARIOSpos(8);
        setCampoLEIDOpos(9);
        setCampoKMLpos(10);        
        return sePudo;        
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
     * @return the campoIDpos
     */
    public int getCampoIDpos() {
        return campoIDpos;
    }

    /**
     * @param campoIDpos the campoIDpos to set
     */
    public void setCampoIDpos(int campoIDpos) {
        this.campoIDpos = campoIDpos;
    }

    /**
     * @return the campoFECHApos
     */
    public int getCampoFECHApos() {
        return campoFECHApos;
    }

    /**
     * @param campoFECHApos the campoFECHApos to set
     */
    public void setCampoFECHApos(int campoFECHApos) {
        this.campoFECHApos = campoFECHApos;
    }

    /**
     * @return the campoLATITUDpos
     */
    public int getCampoLATITUDpos() {
        return campoLATITUDpos;
    }

    /**
     * @param campoLATITUDpos the campoLATITUDpos to set
     */
    public void setCampoLATITUDpos(int campoLATITUDpos) {
        this.campoLATITUDpos = campoLATITUDpos;
    }

    /**
     * @return the campoLONGITUDpos
     */
    public int getCampoLONGITUDpos() {
        return campoLONGITUDpos;
    }

    /**
     * @param campoLONGITUDpos the campoLONGITUDpos to set
     */
    public void setCampoLONGITUDpos(int campoLONGITUDpos) {
        this.campoLONGITUDpos = campoLONGITUDpos;
    }

    /**
     * @return the campoVELOCIDADpos
     */
    public int getCampoVELOCIDADpos() {
        return campoVELOCIDADpos;
    }

    /**
     * @param campoVELOCIDADpos the campoVELOCIDADpos to set
     */
    public void setCampoVELOCIDADpos(int campoVELOCIDADpos) {
        this.campoVELOCIDADpos = campoVELOCIDADpos;
    }

    /**
     * @return the campoPROFUNDIDADpos
     */
    public int getCampoPROFUNDIDADpos() {
        return campoPROFUNDIDADpos;
    }

    /**
     * @param campoPROFUNDIDADpos the campoPROFUNDIDADpos to set
     */
    public void setCampoPROFUNDIDADpos(int campoPROFUNDIDADpos) {
        this.campoPROFUNDIDADpos = campoPROFUNDIDADpos;
    }

    /**
     * @return the campoOBJETOpos
     */
    public int getCampoOBJETOpos() {
        return campoOBJETOpos;
    }

    /**
     * @param campoOBJETOpos the campoOBJETOpos to set
     */
    public void setCampoOBJETOpos(int campoOBJETOpos) {
        this.campoOBJETOpos = campoOBJETOpos;
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
     * @return the campoCOMENTARIOSpos
     */
    public int getCampoCOMENTARIOSpos() {
        return campoCOMENTARIOSpos;
    }

    /**
     * @param campoCOMENTARIOSpos the campoCOMENTARIOSpos to set
     */
    public void setCampoCOMENTARIOSpos(int campoCOMENTARIOSpos) {
        this.campoCOMENTARIOSpos = campoCOMENTARIOSpos;
    }

    /**
     * @return the campoLEIDOpos
     */
    public int getCampoLEIDOpos() {
        return campoLEIDOpos;
    }

    /**
     * @param campoLEIDOpos the campoLEIDOpos to set
     */
    public void setCampoLEIDOpos(int campoLEIDOpos) {
        this.campoLEIDOpos = campoLEIDOpos;
    }

    /**
     * @return the campoKMLpos
     */
    public int getCampoKMLpos() {
        return campoKMLpos;
    }

    /**
     * @param campoKMLpos the campoKMLpos to set
     */
    public void setCampoKMLpos(int campoKMLpos) {
        this.campoKMLpos = campoKMLpos;
    }
    
/**
     * @return the statement
     */
    public Statement getStatement() {
        try {
            if (statement.isClosed()){
                setStatement(getConnection().createStatement());
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
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
     * @return the url
     */

    /**
     * @return the usarMapaNavegacion
     */
    public boolean isUsarMapaNavegacion() {
        return usarMapaNavegacion;
    }

    /**
     * @param usarMapaNavegacion the usarMapaNavegacion to set
     */
    public void setUsarMapaNavegacion(boolean usarMapaNavegacion) {
        this.usarMapaNavegacion = usarMapaNavegacion;
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
     * @return the conCamara
     */
    public boolean isConCamara() {
        return conCamara;
    }

    /**
     * @param conCamara the conCamara to set
     */
    public void setConCamara(boolean conCamara) {
        this.conCamara = conCamara;
    }

    public boolean cargarPoisDeCamp(ArrayList<Integer> categoriasSeleccionadas) {
        boolean sePudo= false;
        try{
            int i = 0;
            while (i<categoriasSeleccionadas.size()){
                ArrayList<modelo.dataManager.POI> poisDeCiertaCategoria = BrokerPOIs.getInstance().getPOISDeUnaCampSegunCatPoi(
                        AdministraCampanias.getInstance().getCampaniaEnCurso().getId(), categoriasSeleccionadas.get(i));
                if (poisDeCiertaCategoria.size()>0){
                    int w = 0;
                    while (w<poisDeCiertaCategoria.size()-1){
                        insertPoi(poisDeCiertaCategoria.get(w),false);
                        w++;
                    }
                    insertPoi(poisDeCiertaCategoria.get(w),true);
                }
                i++;
            }
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    private boolean insertPoi(POI punto, boolean moverCamAestePoi) {
        boolean sePudo=false;
        try {     
             if (dbLista() && punto != null){
                PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO "+getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getCampoFECHApos()), 
                        new Timestamp(punto.getFechaHora().getTime()));
                preparedStatement.setString(Integer.valueOf(getCampoLATITUDpos()), String.valueOf(punto.getLatitud()));
                preparedStatement.setString(Integer.valueOf(getCampoLONGITUDpos()), String.valueOf(punto.getLongitud()));
                preparedStatement.setString(Integer.valueOf(getCampoPROFUNDIDADpos()), "");//String.valueOf(punto.getProfundidad()));
                preparedStatement.setString(Integer.valueOf(getCampoVELOCIDADpos()), "");//String.valueOf(punto.getVelocidad()));
                preparedStatement.setString(Integer.valueOf(getCampoTEMPAGUApos()), "");//String.valueOf(punto.getTempAgua()));
                preparedStatement.setString(Integer.valueOf(getCampoOBJETOpos()), "POI Navegacion");
                preparedStatement.setBoolean(Integer.valueOf(getCampoLEIDOpos()), false);
                preparedStatement.setString(Integer.valueOf(getCampoCOMENTARIOSpos()),punto.getDescripcion());
                preparedStatement.setString(Integer.valueOf(getCampoKMLpos()), 
                        modelo.gisModule.GeneradorKML.getInstance().conviertePOIaKml(punto,moverCamAestePoi));
                preparedStatement.executeUpdate();  
                setUltimoInsert(new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
                preparedStatement.close();
                sePudo=true;
             }
        }
        catch(Exception e) {            
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean cargarRecorridoDeCamp() {
        boolean sePudo=true;
        int idCamp= controllers.ControllerCampania.getInstance().getIdCampaniaEnCurso();
        if (idCamp>=0){
            modelo.dataManager.Campania campElegida = BrokerCampania.getInstance().getCampaniaFromDb(idCamp);
            BrokerHistorico.setReadCampania(campElegida);
            ArrayList<PuntoHistorico> puntos = new ArrayList();
            if (campElegida.getFechaFin() != null){
                puntos = BrokerHistoricoPunto.getInstance().getPuntos( 
                        campElegida.getFechaInicio(),campElegida.getFechaFin());
            }
            else{
                puntos = BrokerHistoricoPunto.getInstance().getPuntos(
                        campElegida.getFechaInicio(),Calendar.getInstance().getTime());
            }
            int i=0;
            sePudo = insertRecorrido(puntos);
        }
        return sePudo;
    }

    private boolean insertRecorrido(ArrayList<PuntoHistorico> puntos) {
        boolean sePudo=false;
        try {     
             if (dbLista() && puntos.size()>0){
                PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO "+getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getCampoFECHApos()), 
                        new Timestamp(puntos.get(0).getFechaYhora().getTime()));
                preparedStatement.setString(Integer.valueOf(getCampoLATITUDpos()), String.valueOf(puntos.get(0).getLatitud()));
                preparedStatement.setString(Integer.valueOf(getCampoLONGITUDpos()), String.valueOf(puntos.get(0).getLongitud()));
                preparedStatement.setString(Integer.valueOf(getCampoPROFUNDIDADpos()), String.valueOf(puntos.get(0).getProfundidad()));
                preparedStatement.setString(Integer.valueOf(getCampoVELOCIDADpos()), String.valueOf(puntos.get(0).getVelocidad()));
                preparedStatement.setString(Integer.valueOf(getCampoTEMPAGUApos()), String.valueOf(puntos.get(0).getTempAgua()));
                preparedStatement.setString(Integer.valueOf(getCampoOBJETOpos()), "RECORRIDO Navegacion");
                preparedStatement.setBoolean(Integer.valueOf(getCampoLEIDOpos()), false);
                preparedStatement.setString(Integer.valueOf(getCampoCOMENTARIOSpos()),puntos.get(0).getComentarios());
                preparedStatement.setString(Integer.valueOf(getCampoKMLpos()),
                        modelo.gisModule.GeneradorKML.getInstance().conviertePuntosARecorridoKmlGxTrack(true,puntos));
                preparedStatement.executeUpdate(); 
                setUltimoInsert(new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
                preparedStatement.close();
                sePudo=true;
             }
        }
        catch(Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;        
    }

    
    public boolean vaciaMapaNavegacion() {
        boolean sePudo=false;
        try {     
             if (dbLista()){
                PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO "+getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getCampoFECHApos()), 
                        new Timestamp(Calendar.getInstance().getTime().getTime()));
                preparedStatement.setString(Integer.valueOf(getCampoLATITUDpos()), "0");
                preparedStatement.setString(Integer.valueOf(getCampoLONGITUDpos()), "0");
                preparedStatement.setString(Integer.valueOf(getCampoPROFUNDIDADpos()), "0");
                preparedStatement.setString(Integer.valueOf(getCampoVELOCIDADpos()), "0");
                preparedStatement.setString(Integer.valueOf(getCampoTEMPAGUApos()), "0");
                preparedStatement.setString(Integer.valueOf(getCampoOBJETOpos()), "vaciarMapa()");
                preparedStatement.setBoolean(Integer.valueOf(getCampoLEIDOpos()), false);
                preparedStatement.setString(Integer.valueOf(getCampoCOMENTARIOSpos()),"");
                preparedStatement.setString(Integer.valueOf(getCampoKMLpos()), "");
                preparedStatement.executeUpdate();
                setUltimoInsert(new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
                preparedStatement.close();
                sePudo=true;
             }             
        }
        catch(Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    /**
     * @return the inicializandoDb
     */
    public boolean isInicializandoDb() {
        return inicializandoDb;
    }

    /**
     * @param inicializandoDb the inicializandoDb to set
     */
    public void setInicializandoDb(boolean inicializandoDb) {
        this.inicializandoDb = inicializandoDb;
    }
    
    
    
}