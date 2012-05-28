/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.ArrayList;
import modelo.dataManager.Campania;
import modelo.dataManager.Punto;
import java.sql.*;

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
    private String dbName;
    
    
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
    
    public boolean creaDbYtablas() {
        boolean sePudo=false;
        // --- metodo pendiente ---
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


    private boolean inicializaBrokerHistorico(){
        boolean sePudo=false;                                     
/*        
        connect = null;
        setStatement(null);
        setResultSet(null);
        java.sql.Timestamp t =new java.sql.Timestamp(new java.util.Date().getTime()); setUltimoInsert(t);        
        setTableName("PUNTOS");
        setDbName("dbHistorico.db");
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
*/
        return sePudo;
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
    public String getDbName() {
        return dbName;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
}