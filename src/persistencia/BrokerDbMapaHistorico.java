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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.PuntoHistorico;

/**
 *
 * @author Sebastian
 */
public class BrokerDbMapaHistorico implements Runnable{
    Thread BdbMap;
    static BrokerDbMapaHistorico unicaInstancia; 
    private BrokerDbMapa bmNavegacion = persistencia.BrokerDbMapa.getInstance();
    private Statement statement;
    private ResultSet resultSet;        
    private java.sql.Timestamp ultimoInsert;
    private String tableName;
    
    private BrokerDbMapaHistorico(){
        inicializaBrokerDbMapaHistorico();
    }
    
    public static BrokerDbMapaHistorico getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerDbMapaHistorico();
       return unicaInstancia;
    }
     
    public boolean cargarRecorridoDeCamp(int idCamp){
        boolean sePudo=true;
        if (idCamp>=0){
            ArrayList<PuntoHistorico> puntos = BrokerHistoricoPunto.getInstance().getPuntos(
                    BrokerCampania.getInstance().getCampaniaFromDb(idCamp).getFechaInicio(), 
                    Calendar.getInstance().getTime());
            int i=0;
            while (i<puntos.size() && sePudo){
                sePudo = insert(puntos.get(i));
            }            
        }
        return sePudo;
    }
    
    public boolean insert(PuntoHistorico p) {
        boolean sePudo=false;
        try {     
             if (tablaLista()){
                PreparedStatement preparedStatement = getBmNavegacion().getConnection()
                .prepareStatement("INSERT INTO "+getBmNavegacion().getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getBmNavegacion().getCampoFECHApos()), 
                        new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLATITUDpos()), String.valueOf(p.getLatitud()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLONGITUDpos()), String.valueOf(p.getLongitud()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoPROFUNDIDADpos()), String.valueOf(p.getProfundidad()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoVELOCIDADpos()), String.valueOf(p.getVelocidad()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoTEMPAGUApos()), String.valueOf(p.getTempAgua()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoOBJETOpos()), "PUNTO");
                preparedStatement.setBoolean(Integer.valueOf(getBmNavegacion().getCampoLEIDOpos()), false);
                //preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoCOMENTARIOSpos()),p.getComentarios());
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoKMLpos()), p.convierteAkml(false));
                preparedStatement.executeUpdate();
                sePudo=true;
                setUltimoInsert(new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
             }
             return sePudo;
        }
        catch(Exception e) {
            System.out.println(e);
            return sePudo;
        }
    }    
    
    private void close() {
        try {
                if (getStatement() != null) {
                        getStatement().close();
                }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
   private boolean tablaLista(){
      boolean sePudo=false;
      try{
          if (getBmNavegacion().dbLista()) {
            //si la db está lista con la conexion creada, el statement, las tablas, usuarios, etc... entonces creo la tabla
            //creamos la tabla de PUNTOS, si no existe
            if (!(existeTablaPuntosHistoricos())) {
                String creacion = "CREATE TABLE "+getTableName()+" ("
                +"ID INT NOT NULL AUTO_INCREMENT, "
                +"FECHA DATETIME NULL, "
                +"LATITUD VARCHAR(30) NULL, "        
                +"LONGITUD VARCHAR(30) NULL, "
                +"VELOCIDAD VARCHAR(30) NULL, "
                +"PROFUNDIDAD VARCHAR(30) NULL, " 
                +"OBJETO VARCHAR(40) NULL, "   
                +"TEMPAGUA VARCHAR(40) NULL, "   
                +"COMENTARIOS VARCHAR(400) NULL, "
                +"LEIDO BOOLEAN NOT NULL DEFAULT 0, "
                +"KML TEXT NULL, "
                +" PRIMARY KEY (ID)"
                +");";  
                getBmNavegacion().getStatement().execute(creacion);
            }
            sePudo=true;
          }
      }
      catch(Exception e){
          Logueador.getInstance().agregaAlLog(e.toString());
      }
      return sePudo;
   }

    public void run() {
        try {
            //BdbMap.currentThread().sleep(5000);//sleep for 2000 ms
    /*      Thread esteThread = Thread.currentThread();
            if (BdbMap == esteThread){    
                try{
                    Thread.sleep(4000);
                }
                catch (Exception e){
                    persistencia.Logueador.getInstance().agregaAlLog(e.toString());
                }
            }        
    */
            Thread.sleep(4000);
            tablaLista(); 
        } catch (InterruptedException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
    }

    public boolean disparaEjecucion(){
        boolean sePudo = false;
        if (BdbMap == null) {
            try {                
                inicializaBrokerDbMapaHistorico();
                BdbMap = new Thread(this);
                BdbMap.setPriority(Thread.MIN_PRIORITY);
                BdbMap.start(); 
                sePudo = true; 
            } catch (Exception ex) { 
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
        }
        else{
            sePudo=true;
        }            
        return sePudo;
    }           
    
    public boolean detieneEjecucion(){
        boolean sePudo = false;
        if (!(BdbMap == null)) {
           BdbMap = null;
           sePudo=true;
        }
        return sePudo;
    }


    private boolean inicializaBrokerDbMapaHistorico(){
        boolean sePudo=false;
        setStatement(null);
        java.sql.Timestamp t =new java.sql.Timestamp(new java.util.Date().getTime());
        setUltimoInsert(t);
        setTableName("PuntosHistoricos");
        return sePudo;        
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
     * @return the bmNavegacion
     */
    public BrokerDbMapa getBmNavegacion() {
        return bmNavegacion;
    }

    /**
     * @param bmNavegacion the bmNavegacion to set
     */
    public void setBmNavegacion(BrokerDbMapa bmNavegacion) {
        this.bmNavegacion = bmNavegacion;
    }

    public boolean existeTablaPuntosHistoricos() {
        boolean b=false;
        try{
              ResultSet rs1=getBmNavegacion().getStatement().executeQuery("SELECT * FROM "+getTableName());
              b=true;
              return(b);
            }    	
             catch (Exception e){                
                return b;
            }
    }
    
}