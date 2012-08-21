/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import controllers.ControllerCampania;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.Punto;
import modelo.dataManager.PuntoHistorico;

/**
 *
 * @author Sebastian
 */
public class BrokerHistoricoPunto extends BrokerHistorico {

    private boolean guardaDatosGps;
    private boolean guardaDatosSonda;
    private boolean guardaDatosPeces;
    private Date fYhUltimoInsert;
    private PreparedStatement psInsert;
    private PreparedStatement psUpdate;
    private PreparedStatement psDelete;
    static BrokerHistoricoPunto unicaInstancia;
    
    private BrokerHistoricoPunto() {
        inicializador();
    }
    
    public static BrokerHistoricoPunto getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerHistoricoPunto();       
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
    
    public boolean insertPunto(Punto pto) {
        boolean sePudo = false;        
        boolean tiempoMinimoCumplido=(Calendar.getInstance().getTime().getTime() - getfYhUltimoInsert().getTime()) >= 15000;//10000=10seg.
        boolean datosMinimos = pto.getLatitud()>0  && pto.getLongitud()>0;
        if (tiempoMinimoCumplido && datosMinimos) {
            try {
                ControllerCampania.getInstance().setEstadoHistoricoDeCampEnCurso(3);
                if (pto.getFechaYhora() != null){ 
                    getPsInsert().setLong(1, pto.getFechaYhora().getTime());
                }
                getPsInsert().setDouble(2,pto.getLatConNegativo());//guardamos la latitud en Grados Decimales
                getPsInsert().setDouble(3,pto.getLonConNegativo());//guardamos la longitud en Grados Decimales --> Valor decimal = grados + (minutos/60) + (y 3600 segundos)
                getPsInsert().setDouble(4,pto.getAltitud());
                getPsInsert().setDouble(5,pto.getVelocidad());
                getPsInsert().setDouble(6,pto.getRumbo());
                getPsInsert().setDouble(7,pto.getProfundidad());
                getPsInsert().setDouble(8,pto.getVelocidadAgua());
                getPsInsert().setDouble(9,pto.getTempAgua());
                System.out.println("Insert PH: "+getPsInsert().toString());
                if (getPsInsert().executeUpdate() > 0) {
                    setfYhUltimoInsert(Calendar.getInstance().getTime());                    
                    sePudo = true;
                }
                ControllerCampania.getInstance().setEstadoHistoricoDeCampEnCurso(2);
            } catch (SQLException ex) {
                ControllerCampania.getInstance().setEstadoHistoricoDeCampEnCurso(0);
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
        }
        try{//ya la use, asique cierro Statements usados
            if (getPsInsert() != null){
                getPsInsert().close();psInsert = null;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    
    public boolean insertPunto(PuntoHistorico ph) {
        boolean sePudo = false;
        boolean tiempoMinimoCumplido=(Calendar.getInstance().getTime().getTime() - getfYhUltimoInsert().getTime()) >= 15000;//10000=10seg.
        boolean datosMinimos = ph.getLatitud()>0  && ph.getLongitud()>0;
        if (tiempoMinimoCumplido && datosMinimos) {         
            try {
                getPsInsert().setLong(1, Calendar.getInstance().getTime().getTime());          
                getPsInsert().setDouble(2,ph.getLatitud());//guardamos la latitud en Grados Decimales
                getPsInsert().setDouble(3,ph.getLongitud());//guardamos la longitud en Grados Decimales --> Valor decimal = grados + (minutos/60) + (y 3600 segundos)
                getPsInsert().setDouble(4,ph.getAltitud());
                getPsInsert().setDouble(5,ph.getVelocidad());
                getPsInsert().setDouble(6,ph.getRumbo());
                getPsInsert().setDouble(7,ph.getProfundidad());
                getPsInsert().setDouble(8,ph.getVelocidadAgua());
                getPsInsert().setDouble(9,ph.getTempAgua());

                System.out.println("Insert PH: "+getPsInsert().toString());
                if (getPsInsert().executeUpdate() > 0) {
                    sePudo = true;
                }
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
        }
        try{//ya la use, asique cierro Statements usados
            if (getPsInsert() != null){
                getPsInsert().close();psInsert = null;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public boolean updatePunto(PuntoHistorico ph) {
        boolean sePudo=false;
        try {
            if (ph.getFechaYhora() != null){
                getPsUpdate().setLong(1, ph.getFechaYhora().getTime());
            }
            getPsUpdate().setDouble(2, ph.getLatitud());//guardamos la latitud en Grados Decimales
            getPsUpdate().setDouble(3, ph.getLongitud());//guardamos la longitud en Grados Decimales
            getPsUpdate().setDouble(4, ph.getAltitud());
            getPsUpdate().setDouble(5, ph.getVelocidad());
            getPsUpdate().setDouble(6, ph.getRumbo());
            getPsUpdate().setDouble(7, ph.getProfundidad());
            getPsUpdate().setDouble(8, ph.getVelocidadAgua());
            getPsUpdate().setDouble(9, ph.getTempAgua());
            getPsUpdate().setInt(10, ph.getId());

            System.out.println("UPDATE PH: "+getPsUpdate().toString());
            if (getPsUpdate().executeUpdate() > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        try{//ya la use, asique cierro Statements usados
            if (getPsUpdate() != null){
                getPsUpdate().close();psUpdate = null;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public boolean deletePunto(PuntoHistorico ph) {
        boolean sePudo=false;
        try {
            getPsDelete().setInt(1,ph.getId());
            System.out.println("DELETE PH: "+getPsDelete().toString());
            if (getPsDelete().executeUpdate() > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        try{//ya la use, asique cierro Statements usados
            if (getPsDelete() != null){
                getPsDelete().close();psDelete = null;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public ArrayList<PuntoHistorico> getPuntos(java.util.Date fechaDesde, java.util.Date fechaHasta){
        ArrayList<PuntoHistorico> recorrido = new ArrayList();
        ResultSet rs = null;
        try {
            if (getReadCampania() != null  &&  creaConexionParaLeerHistorico(getReadCampania().getId())){                
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
                rs = getReadStatement().executeQuery(sqlQuery);            
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
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        
        //ya la use, asique cierro ResultSets y Statements usados
        try{
            if (rs != null){
                rs.close();
            }
            if (getReadStatement() != null){
                getReadStatement().close();setReadStatement(null);
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return recorrido;
    }    

    public void inicializador() {
        setGuardaDatosGps(false);
        setGuardaDatosSonda(false);
        setGuardaDatosPeces(false);
        setfYhUltimoInsert(Calendar.getInstance().getTime());        
    }

    /**
     * @return the psInsert
     */
    public PreparedStatement getPsInsert() {
        try {
            if (psInsert == null){
                    setPsInsert(getConexion().prepareStatement(
                                "INSERT INTO Puntos "
                                + " (fechaYhora,latitud,longitud,altitud,velocidad,rumbo,profundidad,velocidadAgua,tempAgua) "
                                + " VALUES "            
                                +" (?,?,?,?,?,?,?,?,?) ",
                                PreparedStatement.RETURN_GENERATED_KEYS));
            }  
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return psInsert;
    }

    /**
     * @param psInsert the psInsert to set
     */
    public void setPsInsert(PreparedStatement psInsert) {
        this.psInsert = psInsert;
    }

    /**
     * @return the psUpdate
     */
    public PreparedStatement getPsUpdate() {
        try {
            if (psUpdate == null || psUpdate.isClosed()){
                setPsUpdate(getConexion().prepareStatement(
                        "UPDATE Puntos "
                        + "SET fechaYhora = ?, "
                            +" latitud = ?, "
                            +" longitud = ?, "
                            +" altitud = ?, "
                            +" velocidad = ?, "
                            +" rumbo = ?, "
                            +" profundidad = ?, "
                            +" velocidadAgua = ?, "
                            +" tempAgua = ? "            
                            +" WHERE id = ? "));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return psUpdate;
    }

    /**
     * @param psUpdate the psUpdate to set
     */
    public void setPsUpdate(PreparedStatement psUpdate) {
        this.psUpdate = psUpdate;
    }

    /**
     * @return the psDelete
     */
    public PreparedStatement getPsDelete() {
        try {
            if (psDelete == null || psDelete.isClosed()){
                setPsDelete(getConexion().prepareStatement(
                            "DELETE FROM Puntos "
                            + "WHERE id = ?"));
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return psDelete;
    }

    /**
     * @param psDelete the psDelete to set
     */
    public void setPsDelete(PreparedStatement psDelete) {
        this.psDelete = psDelete;
    }

    public int getIdUltimoInsert(){
        int ultimoId=0;
        ResultSet rs = null;
        try {            
            // Se obtiene la clave generada
            rs = getPsInsert().getGeneratedKeys();
            while (rs.next()) {            
                ultimoId = rs.getInt(1);            
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        try{//ya la use, asique cierro ResultSets y Statements usados
            if (rs != null){
                rs.close();
            }
            if (getPsInsert() != null){
                getPsInsert().close();psInsert = null;
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return ultimoId;        
    }

    /**
     * @return the fYhUltimoInsert
     */
    public Date getfYhUltimoInsert() {
        return fYhUltimoInsert;
    }

    /**
     * @param fYhUltimoInsert the fYhUltimoInsert to set
     */
    public void setfYhUltimoInsert(Date fYhUltimoInsert) {
        this.fYhUltimoInsert = fYhUltimoInsert;
    }
    
    public int cuantosPuntosTiene(int idCampania){
        int cantPuntos = 0;
        try{
            if (creaConexionParaLeerHistorico(idCampania)){
                String sql = "SELECT count() as Cantidad FROM Puntos";
                PreparedStatement ps = getReadConection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    cantPuntos = rs.getInt("Cantidad");
                    ps.close();
                    getReadConection().close();
                }                
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.getStackTrace().toString());
        }
        return cantPuntos;
    }
    
}