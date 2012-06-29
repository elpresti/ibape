/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.dataCapture.Csv;
import modelo.dataManager.SondaSetHistorico;

/**
 *
 * @author Sebastian
 */
public class BrokerHistoricoSondaSet extends BrokerHistorico {
    private boolean guardaDatosSondaSets;
    private PreparedStatement psInsert;
    private PreparedStatement psUpdate;
    private PreparedStatement psDelete;    
    static BrokerHistoricoSondaSet unicaInstancia;
    
    private BrokerHistoricoSondaSet(){
        inicializador();
    }
    
    
    public static BrokerHistoricoSondaSet getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new BrokerHistoricoSondaSet();       
       return unicaInstancia;
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
    
    public boolean insertSondaSet(SondaSetHistorico sondaSetNuevo){
        boolean sePudo=false;
        try {
             if (sondaSetNuevo.getUsadoDesde() != null){
                 getPsInsert().setLong(1, sondaSetNuevo.getUsadoDesde().getTime());
             }
             if (sondaSetNuevo.getUsadoHasta() != null){
                 getPsInsert().setLong(2, sondaSetNuevo.getUsadoHasta().getTime());
             }             
             getPsInsert().setInt(3,sondaSetNuevo.getFrecuencia());
             getPsInsert().setInt(4, sondaSetNuevo.getGanancia());
             getPsInsert().setInt(5,sondaSetNuevo.getStc());
             getPsInsert().setInt(6, sondaSetNuevo.getLineaBlanca());
             getPsInsert().setInt(7,sondaSetNuevo.getVelPantalla());
             getPsInsert().setInt(8, sondaSetNuevo.getEscala());
             getPsInsert().setInt(9,sondaSetNuevo.getShift());
             getPsInsert().setInt(10,sondaSetNuevo.getExpander());
             getPsInsert().setInt(11,sondaSetNuevo.getUnidadDeEscala());
             System.out.println("Insert SSH: "+getPsInsert().toString());
             if (getPsInsert().executeUpdate() > 0) {
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
             if (sondaSetModificado.getUsadoDesde() != null){
                 getPsUpdate().setLong(1, sondaSetModificado.getUsadoDesde().getTime());
             }
             if (sondaSetModificado.getUsadoHasta() != null){
                 getPsUpdate().setLong(2, sondaSetModificado.getUsadoHasta().getTime());
             }  
            getPsUpdate().setInt(3, sondaSetModificado.getEscala());
            getPsUpdate().setInt(4,sondaSetModificado.getExpander());
            getPsUpdate().setInt(5,sondaSetModificado.getFrecuencia());
            getPsUpdate().setInt(6, sondaSetModificado.getGanancia());
            getPsUpdate().setInt(7, sondaSetModificado.getLineaBlanca());
            getPsUpdate().setInt(8,sondaSetModificado.getShift());
            getPsUpdate().setInt(9,sondaSetModificado.getStc());
            getPsUpdate().setInt(10,sondaSetModificado.getUnidadDeEscala());
            getPsUpdate().setInt(11,sondaSetModificado.getVelPantalla());
            getPsUpdate().setInt(12,sondaSetModificado.getId());
            
            System.out.println("UPDATE SSH: "+getPsUpdate().toString());
            if (getPsUpdate().executeUpdate() > 0) {
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
            getPsDelete().setInt(1, sondaSetAeliminar.getId());
            System.out.println("DELETE SSH: "+getPsDelete().toString());
            if (getPsDelete().executeUpdate() > 0) {
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
    
    public void inicializador(){
        setGuardaDatosSondaSets(false);
    }
        
    public int getIdUltimoInsert(){
        int ultimoId=0;
        try {            
            // Se obtiene la clave generada
            ResultSet rs = getPsInsert().getGeneratedKeys();
            while (rs.next()) {            
                ultimoId = rs.getInt(1);            
            }
            rs.close();
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return ultimoId;        
    }

    /**
     * @return the psInsert
     */
    public PreparedStatement getPsInsert() {
        if (psInsert == null){
            try {
                setPsInsert(getConexion().prepareStatement(
                            "INSERT INTO SondaSets "
                            + " (usadoDesde,usadoHasta,frecuencia,ganancia,stc,lineaBlanca,velPantalla,escala,shift,expander,unidad) "
                            + " VALUES "            
                            +" (?,?,?,?,?,?,?,?,?,?,?) ",
                            PreparedStatement.RETURN_GENERATED_KEYS));
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
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
        if (psUpdate == null){
            try {
                setPsUpdate(getConexion().prepareStatement(
                        "UPDATE SondaSets "
                        + "SET usadoDesde = ?, "
                            +" usadoHasta = ?, "
                            +" escala = ?, "
                            +" expander = ?, "
                            +" frecuencia = ?, "
                            +" ganancia = ?, "
                            +" lineaBlanca = ?, "
                            +" shift = ?, "
                            +" stc = ?, "
                            +" unidad = ?, "
                            +" velPantalla = ? "
                            +" WHERE id = ? "));
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
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
        if (psDelete == null){
            try {
                setPsDelete(getConexion().prepareStatement(
                            "DELETE FROM SondaSets "
                            + "WHERE id = ?"));
            } catch (SQLException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }
        }        
        return psDelete;
    }

    /**
     * @param psDelete the psDelete to set
     */
    public void setPsDelete(PreparedStatement psDelete) {
        this.psDelete = psDelete;
    }

    public boolean actualizaSondaSetsActual(String rutaCsv) {
        boolean sePudo = false;
        try{
            ArrayList<SondaSetHistorico> ssh = Csv.getInstance().getSondaSetsFromCsv(rutaCsv);
            if (ssh.size()>0){
                SondaSetHistorico ultimoSsh;
                if (ssh.size()>1){
                    ultimoSsh = ssh.get(ssh.size()-1);
                }
                else{
                    ultimoSsh = ssh.get(0);
                }
                modelo.dataManager.SondaSet.getInstance().setEscala(ultimoSsh.getEscala());
                modelo.dataManager.SondaSet.getInstance().setExpander(ultimoSsh.getExpander());
                modelo.dataManager.SondaSet.getInstance().setFrecuencia(ultimoSsh.getFrecuencia());
                modelo.dataManager.SondaSet.getInstance().setGanancia(ultimoSsh.getGanancia());
                modelo.dataManager.SondaSet.getInstance().setLineaBlanca(ultimoSsh.getLineaBlanca());
                modelo.dataManager.SondaSet.getInstance().setShift(ultimoSsh.getShift());
                modelo.dataManager.SondaSet.getInstance().setStc(ultimoSsh.getStc());
                modelo.dataManager.SondaSet.getInstance().setUnidadDeEscala(ultimoSsh.getUnidadDeEscala());
                modelo.dataManager.SondaSet.getInstance().setUsadoDesde(ultimoSsh.getUsadoDesde());
                modelo.dataManager.SondaSet.getInstance().setUsadoHasta(ultimoSsh.getUsadoHasta());
                modelo.dataManager.SondaSet.getInstance().setVelPantalla(ultimoSsh.getVelPantalla());
            } 
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
}