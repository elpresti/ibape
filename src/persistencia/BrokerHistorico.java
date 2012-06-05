/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.File;
import java.util.ArrayList;
import modelo.dataManager.Campania;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.PuntoHistorico;
import modelo.dataManager.SondaSetHistorico;

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
    
    public boolean insertPunto(PuntoHistorico ph) {
        boolean sePudo = false;
        try {
            String fechaYhora = null;
            if (ph.getFechaYhora() != null) {
                fechaYhora = ""+ph.getFechaYhora().getTime()+"";
            }            
            String altitud = String.valueOf(ph.getAltitud());
            String latitud = String.valueOf(ph.getLatitud()); //guardamos la latitud en Grados Decimales
            String longitud = String.valueOf(ph.getLongitud()); //guardamos la longitud en Grados Decimales --> Valor decimal = grados + (minutos/60) + (y 3600 segundos)
            String profundidad = String.valueOf(ph.getProfundidad());
            String rumbo = String.valueOf(ph.getRumbo());
            String tempAgua = String.valueOf(ph.getTempAgua());
            String velocidad = String.valueOf(ph.getVelocidad());
            String velocidadAgua = String.valueOf(ph.getVelocidadAgua());           

            String sqlQuery = "INSERT INTO Puntos"
                    + "(fechaYhora,latitud,longitud,altitud,velocidad,rumbo,profundidad,velocidadAgua,tempAgua)"
                    + "VALUES"
                    +"("+fechaYhora+","+latitud+","+longitud+","+altitud+","+velocidad+","+rumbo+","+profundidad+","+velocidadAgua+","+tempAgua+")";
            System.out.println("Insert PH: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
    
    public boolean updatePunto(PuntoHistorico ph) {
        boolean sePudo=false;
        try {       
            String fechaYhora = null;
            if (ph.getFechaYhora() != null) {
                fechaYhora = ""+ph.getFechaYhora().getTime()+"";
            }            
            String altitud = String.valueOf(ph.getAltitud());
            String latitud = String.valueOf(ph.getLatitud()); //guardamos la latitud en Grados Decimales
            String longitud = String.valueOf(ph.getLongitud()); //guardamos la longitud en Grados Decimales
            String profundidad = String.valueOf(ph.getProfundidad());
            String rumbo = String.valueOf(ph.getRumbo());
            String tempAgua = String.valueOf(ph.getTempAgua());
            String velocidad = String.valueOf(ph.getVelocidad());
            String velocidadAgua = String.valueOf(ph.getVelocidadAgua());           
            
            String sqlQuery = "UPDATE Puntos "
                    + "SET fechaYhora = "+fechaYhora+", "
                    +" latitud = "+latitud+", "
                    +" longitud = "+longitud+","
                    +" altitud = "+altitud+","
                    +" velocidad = "+velocidad+","
                    +" rumbo = "+rumbo+","
                    +" profundidad = "+profundidad+","
                    +" velocidadAgua = "+velocidadAgua+","
                    +" tempAgua = "+tempAgua                    
                    +" WHERE id = "+ph.getId();
            System.out.println("UPDATE PH: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
    
    public boolean deletePunto(PuntoHistorico ph) {
        boolean sePudo=false;
        try {        
            String sqlQuery = "DELETE FROM Puntos "
                    + "WHERE id = "+ph.getId();
            System.out.println("DELETE PH: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }

        return sePudo;
    }

    public ArrayList<PuntoHistorico> getPuntos(java.util.Date fechaDesde, java.util.Date fechaHasta){
        ArrayList<PuntoHistorico> recorrido = new ArrayList();            
        try {
            Calendar calendario = Calendar.getInstance();
            if (fechaHasta == null){                
                fechaHasta = calendario.getTime();
            }
            if (fechaDesde == null){                
                calendario.set(1970, 1, 1);
                fechaDesde = calendario.getTime();
            }
            String sqlQuery=
                    "  SELECT * FROM Puntos  "
                    + "WHERE fechaYhora "
                    + "BETWEEN "+ fechaDesde.getTime() + " AND "+ fechaHasta.getTime() +" "
                    + "ORDER BY fechaYhora ASC";
            System.out.println(sqlQuery);
            ResultSet rs = getStatement().executeQuery(sqlQuery);            
            while (rs.next()) {
                PuntoHistorico ph = new PuntoHistorico();
                // Get the data from the row using the column name
                ph.setId(rs.getInt("id"));
                ph.setAltitud(rs.getDouble("altitud"));
                ph.setFechaYhora(rs.getTimestamp("fechaYhora"));
                ph.setLatitud(rs.getDouble("latitud"));
                ph.setLongitud(rs.getDouble("longitud"));
                ph.setProfundidad(rs.getDouble("profundidad"));
                ph.setRumbo(rs.getDouble("rumbo"));
                ph.setTempAgua(rs.getDouble("tempAgua"));
                ph.setVelocidad(rs.getDouble("velocidad"));
                ph.setVelocidadAgua(rs.getDouble("velocidadAgua"));
                recorrido.add(ph);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        

        return recorrido;
    }    
    
    public boolean insertSondaSet(SondaSetHistorico sondaSetNuevo){
        boolean sePudo=false;
        try {
            String usadoDesde = null;
            if (sondaSetNuevo.getUsadoDesde() != null) {
                usadoDesde = ""+sondaSetNuevo.getUsadoDesde().getTime()+"";
            }
            String usadoHasta = null;
            if (sondaSetNuevo.getUsadoHasta() != null) {
                usadoHasta = ""+sondaSetNuevo.getUsadoHasta().getTime()+"";
            }            
            String escala = String.valueOf(sondaSetNuevo.getEscala());
            String expander = String.valueOf(sondaSetNuevo.getExpander());
            String frecuencia = String.valueOf(sondaSetNuevo.getFrecuencia());
            String ganancia = String.valueOf(sondaSetNuevo.getGanancia());
            String lineaBlanca = String.valueOf(sondaSetNuevo.getLineaBlanca());
            String shift = String.valueOf(sondaSetNuevo.getShift());
            String stc = String.valueOf(sondaSetNuevo.getStc());
            String unidadDeEscala = String.valueOf(sondaSetNuevo.getUnidadDeEscala());
            String velPantalla = String.valueOf(sondaSetNuevo.getVelPantalla());
            
            String sqlQuery = "INSERT INTO SondaSets"
                    + "(usadoDesde,usadoHasta,frecuencia,ganancia,stc,lineaBlanca,velPantalla,escala,shift,expander,unidad)"
                    + "VALUES"
                    +"("+usadoDesde+","+usadoHasta+","+frecuencia+","+ganancia+","+stc+","+lineaBlanca+","+velPantalla+","+escala+","+shift+","+expander+","+unidadDeEscala+")";
            System.out.println("Insert SS: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
    
    public boolean updateSondaSet(SondaSetHistorico sondaSetModificado){
        boolean sePudo=false;
        try {         
            String usadoDesde = null;
            if (sondaSetModificado.getUsadoDesde() != null) {
                usadoDesde = ""+sondaSetModificado.getUsadoDesde().getTime()+"";
            }
            String usadoHasta = null;
            if (sondaSetModificado.getUsadoHasta() != null) {
                usadoHasta = ""+sondaSetModificado.getUsadoHasta().getTime()+"";
            }            
            String escala = String.valueOf(sondaSetModificado.getEscala());
            String expander = String.valueOf(sondaSetModificado.getExpander());
            String frecuencia = String.valueOf(sondaSetModificado.getFrecuencia());
            String ganancia = String.valueOf(sondaSetModificado.getGanancia());
            String lineaBlanca = String.valueOf(sondaSetModificado.getLineaBlanca());
            String shift = String.valueOf(sondaSetModificado.getShift());
            String stc = String.valueOf(sondaSetModificado.getStc());
            String unidadDeEscala = String.valueOf(sondaSetModificado.getUnidadDeEscala());
            String velPantalla = String.valueOf(sondaSetModificado.getVelPantalla());
            
            String sqlQuery = "UPDATE SondaSets "
                    + "SET usadoDesde = "+usadoDesde+", "
                    +" usadoHasta = "+usadoHasta+", "
                    +" escala = "+escala+","
                    +" expander = "+expander+","
                    +" frecuencia = "+frecuencia+","
                    +" ganancia = "+ganancia+","
                    +" lineaBlanca = "+lineaBlanca+","
                    +" shift = "+shift+","
                    +" stc = "+stc+","
                    +" unidad = "+unidadDeEscala+","
                    +" velPantalla = "+velPantalla
                    +" WHERE id = "+sondaSetModificado.getId();
            System.out.println("UPDATE SS: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }

        return sePudo;
    }
    
    public boolean deleteSondaSet(SondaSetHistorico sondaSetAeliminar){
        boolean sePudo=false;
        try {        
            String sqlQuery = "DELETE FROM SondaSets "
                    + "WHERE id = "+sondaSetAeliminar.getId();
            System.out.println("DELETE SS: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        

        return sePudo;
    }
    
    public SondaSetHistorico getSondaSet(int idSondaSetRequerido){
        SondaSetHistorico ssHistorico = null;
        try {
            String sqlQuery=
                    "  SELECT * FROM SondaSets  "
                    + "WHERE id = "+idSondaSetRequerido;
            System.out.println(sqlQuery);
            ResultSet rs = getStatement().executeQuery(sqlQuery);
            if (rs.next()) {
                ssHistorico = new SondaSetHistorico();
                // Get the data from the row using the column name
                ssHistorico.setId(rs.getInt("id"));
                ssHistorico.setEscala(rs.getInt("escala"));
                ssHistorico.setExpander(rs.getInt("expander"));
                ssHistorico.setFrecuencia(rs.getInt("frecuencia"));
                ssHistorico.setGanancia(rs.getInt("ganancia"));
                ssHistorico.setLineaBlanca(rs.getInt("lineaBlanca"));
                ssHistorico.setShift(rs.getInt("shift"));
                ssHistorico.setStc(rs.getInt("stc"));
                ssHistorico.setUnidadDeEscala(rs.getInt("unidadDeEscala"));
                ssHistorico.setUsadoDesde(rs.getTimestamp("usadoDesde"));
                ssHistorico.setUsadoHasta(rs.getTimestamp("usadoHasta"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return ssHistorico;        
    }
    
    public boolean comparaSondaSetsIguales(SondaSetHistorico sondaSet1, SondaSetHistorico sondaSet2){
        boolean sonIguales=true;
        sonIguales = sonIguales && (sondaSet1.getEscala() == sondaSet2.getEscala());
        sonIguales = sonIguales && (sondaSet1.getExpander() == sondaSet2.getExpander());
        sonIguales = sonIguales && (sondaSet1.getFrecuencia() == sondaSet2.getFrecuencia());
        sonIguales = sonIguales && (sondaSet1.getGanancia() == sondaSet2.getGanancia());
        sonIguales = sonIguales && (sondaSet1.getLineaBlanca() == sondaSet2.getLineaBlanca());
        sonIguales = sonIguales && (sondaSet1.getShift() == sondaSet2.getShift());
        sonIguales = sonIguales && (sondaSet1.getStc() == sondaSet2.getStc());
        sonIguales = sonIguales && (sondaSet1.getUnidadDeEscala() == sondaSet2.getUnidadDeEscala());
        sonIguales = sonIguales && (sondaSet1.getVelPantalla() == sondaSet2.getVelPantalla());
        return sonIguales;
    }
    
    public SondaSetHistorico getUltimoSondaSet(){
        SondaSetHistorico ssHistorico = null;
        try {
            String sqlQuery=
                    "  SELECT * FROM SondaSets  "
                    + " ORDER BY id DESC LIMIT 1";
            System.out.println(sqlQuery);
            ResultSet rs = getStatement().executeQuery(sqlQuery);
            if (rs.next()) {
                ssHistorico = new SondaSetHistorico();
                // Get the data from the row using the column name
                ssHistorico.setId(rs.getInt("id"));
                ssHistorico.setEscala(rs.getInt("escala"));
                ssHistorico.setExpander(rs.getInt("expander"));
                ssHistorico.setFrecuencia(rs.getInt("frecuencia"));
                ssHistorico.setGanancia(rs.getInt("ganancia"));
                ssHistorico.setLineaBlanca(rs.getInt("lineaBlanca"));
                ssHistorico.setShift(rs.getInt("shift"));
                ssHistorico.setStc(rs.getInt("stc"));
                ssHistorico.setUnidadDeEscala(rs.getInt("unidadDeEscala"));
                ssHistorico.setUsadoDesde(rs.getTimestamp("usadoDesde"));
                ssHistorico.setUsadoHasta(rs.getTimestamp("usadoHasta"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return ssHistorico;
    }
    
    public boolean creaConexionNueva() {
        boolean sePudo = false;
        try {
            setCampania(AdministraCampanias.getInstance().getCampaniaEnCurso());
            boolean yaTeniaHistorico=false;
            //si la campaña en curso no tiene dbhistorico, la creamos
            if (!(AdministraCampanias.getInstance().verificaSiTieneHistorico(getCampania().getId()))){
                if (!(creaFoldersHistorico())){
                    return false;
                }
            }
            else { yaTeniaHistorico = true; }

            Class.forName("org.sqlite.JDBC");
            // Observar que el archivo .db es la base de datos del Historico y se crea en el directorio de Historico de la campania en curso
            setConexion(DriverManager.getConnection("jdbc:sqlite:" + getFolderNameHistorico()+"\\"+getCampaignFolderName(getCampania().getId())+"\\"+ getDbFileName()));

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

    public boolean creaFoldersHistorico(){
        boolean sePudo=false;
        if (getCampania() != null){            
            try {
                //modelo.dataManager.Campania campania = AdministraCampanias.getInstance().getCampania(idCampania);
                File foldersHistorico = new File(getFolderNameHistorico()+"\\"+getCampaignFolderName(getCampania().getId()));
                foldersHistorico.mkdirs();
                getCampania().setFolderHistorico(getCampaignFolderName(getCampania().getId()));
                //actualizo la campania en memoria y en la tabla Campanias
                AdministraCampanias.getInstance().modificarCampania(getCampania());
                sePudo=true;
            }
            catch(Exception e){
                Logueador.getInstance().agregaAlLog(e.toString());
            }
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
        if (statement == null){
            creaConexionNueva();
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
            //+ "  latHemisferio      CHAR NOT NULL,"
            + "  longitud           REAL NOT NULL,"
            //+ "  lonHemisferio      CHAR NOT NULL,"
            + "  altitud            REAL,"
            + "  velocidad          REAL,"
            + "  rumbo              REAL,"
            + "  profundidad        REAL,"
            + "  velocidadAgua      REAL,"
            + "  tempAgua           REAL,"
            + "  idSondaSets        integer NULL,"
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
            + "  usadoDesde         TIMESTAMP," //LOW/MID/HIGH
            + "  usadoHasta         TIMESTAMP," //LOW/MID/HIGH        
            + "  frecuencia         integer," //LOW/MID/HIGH
            + "  ganancia           integer,"
            + "  stc                integer," //STC (Valor de Ganancia variable)
            + "  lineaBlanca        integer," //WL (White Line)                    
            + "  velPantalla        integer," //GS (G.SPEEP: Velocidad Pantalla)                    
            + "  escala             integer," //Escala (Unit) [centimetros]
            + "  shift              integer," //Desplazamiento [centimetros]
            + "  expander           integer," //Tamaño de expander del fondo [centimetros]
            + "  unidad             integer" //0= metros ,1= brasas, y 2= pies, para calcular la escala
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
            
            /*TRIGGER ON INSERT, si la clave primaria SI permite nulos */
            String codigoCreacion=
            "CREATE TRIGGER fki_punto_idSondaSets "
            + "BEFORE INSERT ON puntos "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((new.foo_id IS NOT NULL) AND "
            + " ((SELECT id FROM SondaSets WHERE id = NEW.idSondaSets) IS NULL)"
            + "     THEN RAISE(ABORT, 'insert on table \"puntos\" violates foreign key constraint \"fki_idSondaSets\"')"
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
        controllers.ControllerCampania.getInstance().nuevaCampania("Campaña de prueba","Probando", "BrokerHistorico");
        PuntoHistorico ph = new PuntoHistorico();
        ph.setAltitud(10);
        Calendar calendario = Calendar.getInstance();
        ph.setFechaYhora(calendario.getTime());
        ph.setAltitud(10);
        ph.setLatitud(-32.456754);
        ph.setLongitud(-43.221254);
        ph.setProfundidad(23.211);
        ph.setRumbo(34.2355);
        ph.setTempAgua(-3.22);
        ph.setVelocidad(21.322);
        ph.setVelocidadAgua(2.54);

        getInstance().insertPunto(ph);

        ph.setId(getInstance().getIdUltimoInsert());
        
        ArrayList<PuntoHistorico> puntos = getInstance().getPuntos(null, null);
        
        ph.setTempAgua(246);       
        ph.setProfundidad(12);
        
        getInstance().updatePunto(ph);
        
        puntos = getInstance().getPuntos(null, null);
        
        //getInstance().deletePunto(ph);
        
        //puntos = getInstance().getPuntos(null, null);
        
        SondaSetHistorico sondaSet = new SondaSetHistorico();
        sondaSet.setGanancia(10);
        sondaSet.setEscala(3);
        sondaSet.setShift(300);
        getInstance().insertSondaSet(sondaSet);
        sondaSet.setGanancia(99);
        getInstance().insertSondaSet(sondaSet);
        
        boolean sonIguales = getInstance().comparaSondaSetsIguales(sondaSet, sondaSet);
        SondaSetHistorico sondaSet2 = new SondaSetHistorico();
        sondaSet2.setGanancia(77);
        sondaSet2.setEscala(55);
        sondaSet2.setShift(44);        
        
        sonIguales = getInstance().comparaSondaSetsIguales(sondaSet, sondaSet2);
        sondaSet.setId(2);
        sondaSet.setGanancia(23);
        getInstance().updateSondaSet(sondaSet);
        SondaSetHistorico ssHistorico = getInstance().getSondaSet(1);
        SondaSetHistorico ssHistoricoId2 = getInstance().getUltimoSondaSet();
        getInstance().deleteSondaSet(ssHistoricoId2);                                
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

    public boolean borraHistorico(int idCampaniaAborrar){
        boolean sePudo=false;
        //de la campaña especificada, borra su carpeta de historico completa
        try {
            modelo.dataManager.Campania campania = AdministraCampanias.getInstance().getCampania(idCampaniaAborrar);
            File folderHistorico = new File(getFolderNameHistorico()+"\\"+campania.getFolderHistorico());
            borrarDirectorio(folderHistorico);
            if (folderHistorico.delete()){
                campania.setFolderHistorico(null);
                //actualizo la campania en memoria y en la tabla Campanias
                AdministraCampanias.getInstance().modificarCampania(campania);
                sePudo=true;                
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }    
        return sePudo;
    }
    
    public boolean borrarDirectorio (File directorio){
        boolean sePudo = false;
        try{
             File[] ficheros = directorio.listFiles();
             for (int x=0;x<ficheros.length;x++){
                if (ficheros[x].isDirectory()) {
                borrarDirectorio(ficheros[x]);
                }
                ficheros[x].delete();                 
             }
             sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
}