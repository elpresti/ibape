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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.PuntoHistorico;
import modelo.dataManager.SondaSetHistorico;

/**
 *
 * @author Sebastian
 */
public abstract class BrokerHistorico implements Runnable{
    private static Thread dbHthread;
    private static Campania campania;//en curso
    private static Campania readCampania;//para leer de historico
    private static Connection conexion;
    private static Connection readConection; //conexion exclusiva para lectura de Historico
    private static Statement statement;
    private static Statement readStatement; //statement exclusivo para lectura de Historico
    private static String dbFileName;
    private static String folderNameHistorico;    

    /**
     * @return the readCampania
     */
    public static Campania getReadCampania() {
        return readCampania;
    }

    /**
     * @param aReadCampania the readCampania to set
     */
    public static void setReadCampania(Campania aReadCampania) {
        readCampania = aReadCampania;
    }
    
    public BrokerHistorico(){
        inicializaBrokerHistorico();
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
        
    private boolean creaConexionNueva() {
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
            modelo.dataCapture.LanSonda.getInstance().setCarpetaHistoricoLocal(
                    persistencia.BrokerHistoricoSondaSet.getInstance().getFolderNameHistorico() + "\\"
                    + AdministraCampanias.getInstance().getCampaniaEnCurso().getFolderHistorico());
            sePudo = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return sePudo;
    }

    public boolean creaConexionParaLeerHistorico(int idCampania) {
        boolean sePudo = false;
        try {
            //corroboro que la campaña especificada tenga historico
            if (AdministraCampanias.getInstance().verificaSiTieneHistorico(idCampania)){
                //si tiene, cierro las conexiones abiertas en caso de q esten abiertas
                if ((getReadConection() != null) && (!getReadConection().isClosed())){
                    getReadConection().close();
                }
                //if ((getReadStatement() != null) && (!getReadStatement().isClosed())){
                if (getReadStatement() != null){
                    getReadStatement().close();
                }
                Class.forName("org.sqlite.JDBC");
                // Observar que el archivo .db es la base de datos del Historico y se crea en el directorio de Historico de la campania en curso
                setReadConection(DriverManager.getConnection("jdbc:sqlite:" + getFolderNameHistorico()+"\\"+getCampaignFolderName(idCampania)+"\\"+ getDbFileName()));
                setReadStatement(getReadConection().createStatement());
                sePudo = true;
            }
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
    
    public void closeDbConnection() {   
        try {
                if (statement != null) {
                        getStatement().close();
                }

                if (conexion != null) {
                        getConexion().close();
                }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
            System.out.println(e);
        }
    }
    
    public void run() {
        try {            
              creaConexionNueva();
        } catch (Exception e) {            
              Logueador.getInstance().agregaAlLog(e.toString());
        }
    }
    
    public boolean disparaEjecucion(){
        boolean sePudo = false;
        if (dbHthread == null) { 
            setCampania(AdministraCampanias.getInstance().getCampaniaEnCurso());
            dbHthread = new Thread(this);
            dbHthread.setPriority(Thread.MIN_PRIORITY);
            dbHthread.start();
            sePudo = true;
        }
        return sePudo;
    }           
        
    public boolean detieneEjecucion(){
        boolean sePudo = false;
        persistencia.BrokerHistoricoPunto.getInstance().inicializador();
        persistencia.BrokerHistoricoSondaSet.getInstance().inicializador();
        dbHthread = null;
        return sePudo;
    }           

    public boolean estaLogueando(){
        return dbHthread != null;
    }

    private void inicializaBrokerHistorico(){
        setFolderNameHistorico("Historico");
        setCampania(AdministraCampanias.getInstance().getCampaniaEnCurso());
        setDbFileName("historico.db");        
}                

    /**
     * @return the conexion
     */
    public Connection getConexion() {
        try {
            if ((conexion == null) || (conexion != null && conexion.isClosed())){
                creaConexionNueva();
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
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
            //sePudo = sePudo && crearTriggersIdSondaSets(); ya no xq 
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); 
              sePudo=false;
            }

        return sePudo;
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
        boolean sePudo = true;
        try{
             File[] ficheros = directorio.listFiles();
             for (int x=0;x<ficheros.length;x++){
                if (ficheros[x].isDirectory()) {
                borrarDirectorio(ficheros[x]);
                }
                sePudo=sePudo && ficheros[x].delete();                 
             }
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
            sePudo=false;
        }
        return sePudo;
    }
    
    public boolean crearTablaPuntos() {
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
            + "  tempAgua           REAL"
//            + ",  idSondaSets        integer NULL,"
//          + "  /* Foreign keys */ "
//          + "  FOREIGN KEY (idSondaSets)"
//          + "    REFERENCES SondaSets(id)"            
            + ");";
            getStatement().executeUpdate(codigoCreacion);
            sePudo=true;
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        try{//ya la use, asique cierro Statements usados
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTablaSondaSets() {
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
        try{//ya la use, asique cierro Statements usados
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean crearTriggersIdSondaSets() {
        boolean sePudo=true;
        try {
            //---------------- triggers de la FK idSondaSets -----------------------
            
            /*TRIGGER ON INSERT, si la clave primaria SI permite nulos */
            String codigoCreacion=
            " CREATE TRIGGER fki_punto_idSondaSets "
            + " BEFORE INSERT ON puntos "
            + " FOR EACH ROW "
            + " BEGIN "
            + "  SELECT CASE"
            + "     WHEN ((new.idSondaSets IS NOT NULL) AND "
            + "          ((SELECT id FROM SondaSets WHERE id = NEW.idSondaSets) IS NULL)) THEN "
            + "     RAISE(ABORT, 'insert on table \"puntos\" violates foreign key constraint \"fki_punto_idSondaSets\"') "
            + "  END; "
            + " END; ";
            
            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON UPDATE, si la clave primaria SI permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fku_punto_idSondaSets "
            + "BEFORE UPDATE ON puntos "
            + "FOR EACH ROW BEGIN"
            + "  SELECT CASE"
            + "     WHEN ((new.idSondaSets IS NOT NULL) AND "
            + "          ((SELECT id FROM SondaSets WHERE id = NEW.idSondaSets) IS NULL)) "
            + "     THEN RAISE(ABORT, 'update on table \"puntos\" violates foreign key constraint \"fku_punto_idSondaSets\"')"
            + "  END; "
            + "END;";

            if (!(getStatement().executeUpdate(codigoCreacion)==0)) { sePudo=sePudo && false; }

            /*TRIGGER ON DELETE, si la clave primaria SI permite nulos */
            codigoCreacion=
            "CREATE TRIGGER fkd_punto_idSondaSets "
            + "BEFORE DELETE ON SondaSets "
            + "FOR EACH ROW BEGIN "
            + "  SELECT CASE "
            + "    WHEN ((new.idSondaSets IS NOT NULL) AND "
            + "         ((SELECT idSondaSets FROM puntos WHERE idSondaSets = OLD.id) IS NOT NULL)) "
            + "    THEN RAISE(ABORT, 'delete on table \"SondaSets\" violates foreign key constraint \"fkd_punto_idSondaSets\"') "
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
        try{//ya la use, asique cierro Statements usados
            if (getStatement() != null){
                getStatement().close();
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }    

    /**
     * @return the readConexion
     */
    public static Connection getReadConection() {
        return readConection;
    }

    /**
     * @param aReadConexion the readConexion to set
     */
    public static void setReadConection(Connection aReadConection) {
        readConection = aReadConection;
    }

    /**
     * @return the readStatement
     */
    public static Statement getReadStatement() {
        return readStatement;
    }

    /**
     * @param aReadStatement the readStatement to set
     */
    public static void setReadStatement(Statement aReadStatement) {
        readStatement = aReadStatement;
    }

    
/*    
    public static void main(String[] args) {
        "THEN": 
        //método para testear ABM de Puntos y ABM de SondaSets
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

        BrokerHistoricoPunto brokerPuntoHistorico = BrokerHistoricoPunto.getInstance();        
        
        brokerPuntoHistorico.insertPunto(ph); //A-Punto

        ph.setId(brokerPuntoHistorico.getIdUltimoInsert());
        
        ArrayList<PuntoHistorico> puntos = brokerPuntoHistorico.getPuntos(null, null);
        
        ph.setTempAgua(246);       
        ph.setProfundidad(12);
        
        brokerPuntoHistorico.updatePunto(ph); //M-Punto
        
        puntos = brokerPuntoHistorico.getPuntos(null, null);
        
        brokerPuntoHistorico.deletePunto(ph);  //B-Punto
        
        puntos = brokerPuntoHistorico.getPuntos(null, null);
        
        BrokerHistoricoSondaSet brokerSsh = BrokerHistoricoSondaSet.getInstance();
        
        SondaSetHistorico sondaSet = new SondaSetHistorico();
        sondaSet.setGanancia(10);
        sondaSet.setEscala(3);
        sondaSet.setShift(300);
        brokerSsh.insertSondaSet(sondaSet); //A-SondaSet
        sondaSet.setGanancia(99);
        brokerSsh.insertSondaSet(sondaSet); //A-SondaSet
        
        boolean sonIguales = brokerSsh.comparaSondaSetsIguales(sondaSet, sondaSet);
        SondaSetHistorico sondaSet2 = new SondaSetHistorico();
        sondaSet2.setGanancia(77);
        sondaSet2.setEscala(55);
        sondaSet2.setShift(44);        
        
        sonIguales = brokerSsh.comparaSondaSetsIguales(sondaSet, sondaSet2);
        sondaSet.setId(2);
        sondaSet.setGanancia(23);
        brokerSsh.updateSondaSet(sondaSet); //M-SondaSet
        SondaSetHistorico ssHistorico = brokerSsh.getSondaSet(1);
        SondaSetHistorico ssHistoricoId2 = brokerSsh.getUltimoSondaSet();
        brokerSsh.deleteSondaSet(ssHistoricoId2);       //B-SondaSet 
    }
*/
    
}