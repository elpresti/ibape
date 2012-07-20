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
            vaciaMapaHistorico();
            ArrayList<PuntoHistorico> puntos = BrokerHistoricoPunto.getInstance().getPuntos(
                    BrokerCampania.getInstance().getCampaniaFromDb(idCamp).getFechaInicio(), 
                    Calendar.getInstance().getTime());
            int i=0;
            /*
            while (i<puntos.size() && sePudo){
                sePudo = insert(puntos.get(i));
                i++;
            }
            */
            sePudo = insertPuntos(puntos);
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
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoCOMENTARIOSpos()),p.getComentarios());
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoKMLpos()), p.convierteAkml(true));
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

    public boolean insertPuntos(ArrayList<modelo.dataManager.PuntoHistorico> puntos) {
        boolean sePudo=false;
        try {     
             if (tablaLista() && puntos.size()>0){
                PreparedStatement preparedStatement = getBmNavegacion().getConnection()
                .prepareStatement("INSERT INTO "+getBmNavegacion().getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getBmNavegacion().getCampoFECHApos()), 
                        new Timestamp(puntos.get(0).getFechaYhora().getTime()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLATITUDpos()), String.valueOf(puntos.get(0).getLatitud()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLONGITUDpos()), String.valueOf(puntos.get(0).getLongitud()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoPROFUNDIDADpos()), String.valueOf(puntos.get(0).getProfundidad()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoVELOCIDADpos()), String.valueOf(puntos.get(0).getVelocidad()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoTEMPAGUApos()), String.valueOf(puntos.get(0).getTempAgua()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoOBJETOpos()), "PUNTO Historico");
                preparedStatement.setBoolean(Integer.valueOf(getBmNavegacion().getCampoLEIDOpos()), false);
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoCOMENTARIOSpos()),puntos.get(0).getComentarios());
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoKMLpos()), BrokerDbMapaHistorico.getInstance().conviertePuntosAkml(true,puntos));
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
        BdbMap=null;
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
    
    public String conviertePuntosAkml(boolean conCamara, ArrayList<modelo.dataManager.PuntoHistorico> puntos) {
            String salida = "";        
        if (puntos.size()>0){            
            //Cosas pendientes de incluir:
                // - Miniatura imagen de la sonda
                // - Cantidad de marcas encontradas
                // - Alertas (todas)

            //Preset de camara 1 = vista aerea trasera:
            //Longitud:getLonConNegativo()*1.00003  Latitud:getLatConNegativo()*1.00006  altitude:50  heading:35  tilt:75
            //Preset de camara 2 = vista aerea lateral derecha:
            //Longitud:getLonConNegativo()*0.99999  Latitud:getLatConNegativo()*1.00005  altitude:50  heading:0  tilt:70
            // punto de ejemplo para calibrar posicion de camara: setLonConNegativo(-56.85432); setLatConNegativo(-37.11671); 
            salida=
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
            +"<Document>"
                +"<name>Codigo KML</name>"
                +"<Style id=\"yellowLineGreenPoly\">"
                   +"<LineStyle>"
                     +"<color>7f00ffff</color>"
                     +"<width>4</width>"
                   +"</LineStyle>"
                   +"<PolyStyle>"
                   +   "<color>7f00ff00</color>"
                   +"</PolyStyle>"
                +"</Style>";
                    
            if (conCamara){
            salida=salida
                +"<Camera>"
                +"<longitude>"+(puntos.get(puntos.size()-1).getLongitud()*0.99999)+"</longitude>"
                +"<latitude>"+(puntos.get(puntos.size()-1).getLatitud()*1.00005)+"</latitude>"
                +"<altitude>50</altitude>"
                +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
                +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
                +"</Camera>";        
            }
            java.sql.Timestamp fechaYhoraPrimero=new java.sql.Timestamp(puntos.get(0).getFechaYhora().getTime());
            String strFechaYhoraPrimero = fechaYhoraPrimero.getYear()+"/"+fechaYhoraPrimero.getMonth()+"/"+fechaYhoraPrimero.getSeconds()+" "+fechaYhoraPrimero.getHours()+":"+fechaYhoraPrimero.getMinutes()+":"+fechaYhoraPrimero.getSeconds();
            java.sql.Timestamp fechaYhoraUltimo=new java.sql.Timestamp(puntos.get(puntos.size()-1).getFechaYhora().getTime());
            String strFechaYhoraUltimo = fechaYhoraUltimo.getYear()+"/"+fechaYhoraUltimo.getMonth()+"/"+fechaYhoraUltimo.getSeconds()+" "+fechaYhoraUltimo.getHours()+":"+fechaYhoraUltimo.getMinutes()+":"+fechaYhoraUltimo.getSeconds();
            salida=salida
            +"<Placemark>"// id=tramo+"+strFechaYhoraPrimero+" - "+strFechaYhoraUltimo+">"
                +"<name>"+strFechaYhoraPrimero+"  -  "+strFechaYhoraUltimo+"</name>"
                +"<visibility>1</visibility>"
                +"<description>Recorrido entre "+strFechaYhoraPrimero+"  -  "+strFechaYhoraUltimo+"</description>"
                +"<styleUrl>#yellowLineGreenPoly</styleUrl>"
                +"<LineString>"
                  +"<extrude>1</extrude>"
                  +"<tessellate>1</tessellate>"
                  +"<altitudeMode>absolute</altitudeMode>"                    
                      +"<coordinates>";
            int i=0;
            while (i<puntos.size()){
                salida+=puntos.get(i).getLongitud()+","+puntos.get(i).getLatitud()+","+puntos.get(i).getAltitud()+" ";
                i++;
            }
            salida+=
                       "</coordinates>"
                  +"</LineString>"
            +"</Placemark>"
            +"</Document>"                

            +"</kml>";    
        }        
        return salida;
    }
    
    public boolean vaciaMapaHistorico(){    
        boolean sePudo=false;
        try {     
             if (tablaLista()){
                PreparedStatement preparedStatement = getBmNavegacion().getConnection()
                .prepareStatement("INSERT INTO "+getBmNavegacion().getDbName()+"."+getTableName()+
                        " values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setTimestamp(Integer.valueOf(getBmNavegacion().getCampoFECHApos()), 
                        new Timestamp(Calendar.getInstance().getTime().getTime()));
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLATITUDpos()), "0");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoLONGITUDpos()), "0");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoPROFUNDIDADpos()), "0");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoVELOCIDADpos()), "0");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoTEMPAGUApos()), "0");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoOBJETOpos()), "vaciarMapa()");
                preparedStatement.setBoolean(Integer.valueOf(getBmNavegacion().getCampoLEIDOpos()), false);
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoCOMENTARIOSpos()),"");
                preparedStatement.setString(Integer.valueOf(getBmNavegacion().getCampoKMLpos()), "");
                preparedStatement.executeUpdate();                 
                setUltimoInsert(new Timestamp(java.util.Calendar.getInstance().getTime().getTime()));
                sePudo=true;
             }             
        }
        catch(Exception e) {
            System.out.println(e);
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
}