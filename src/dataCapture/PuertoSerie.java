/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataCapture;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.*;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Observable;
/**
 *
 * @author Sebastian
 */
public class PuertoSerie implements SentenceListener, Runnable{
    Thread ps;
    private String ultimaSentenciaLeida;
    private String txtCuadro=new String();
    private int nroCom;
    private boolean estadoConexion=false;
    private boolean estado=false;
    private dataManager.Punto punto=dataManager.Punto.getInstance();
        
    public PuertoSerie(int nrocom){
        //inicializacion de variables
        setNroCom(nrocom);
        if (ps == null) {
            ps = new Thread(this);
            ps.start();
        }
    }
    
    private SerialPort getSerialPort(){
        try { 
            // Abrir directamente un puerto determinado= tiempo aproximado: 36seg            
            System.out.println("--- Tratando de abrir el COM16 ---");
            setTxtCuadro("--- Tratando de abrir el COM16 ---");
            CommPortIdentifier idCOM16 = CommPortIdentifier.getPortIdentifier("COM16");
            System.out.println("--- COM16 abierto! ---");
            setTxtCuadro("--- COM16 abierto! ---");
            SerialPort sp = (SerialPort) idCOM16.open("SerialIBAPE", 30);
            sp.setSerialPortParams(4800, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);            
            return sp;
        } catch (Exception e) {
           System.out.println(e);setTxtCuadro(e.toString());
           return null;
        }
/*
        // Lista de los puertos disponibles en la máquina=tiempo aproximado: 1min 12seg
        System.out.println("--- Obteniendo listado de puertos del Sistema Operativo...");        
        setTxtCuadro("--- Obteniendo listado de puertos del Sistema Operativo...");                    
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        System.out.println("--- Listado de puertos obtenido ---");
        setTxtCuadro("--- Listado de puertos obtenido ---");
        int $nro=1;
        while( e.hasMoreElements() ) {
          CommPortIdentifier id = (CommPortIdentifier)e.nextElement();
          if( id.getPortType() == CommPortIdentifier.PORT_SERIAL ) {
    //        if( idPuerto.getName().equals("/dev/term/a") ) {  // UNIX
            try {
                $nro++;
                System.out.println(id.getName());
                setTxtCuadro(id.getName());
                if( id.getName().equals("COM".concat(String.valueOf(getNroCom())))) {           // WINDOWS
                  // Lector del puerto, se quedará esperando a que llegue algo al puerto
                   SerialPort sp = (SerialPort) id.open("SerialExample", 30);
                   sp.setSerialPortParams(4800, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);                
                   return sp;
                  }
                }
            catch (Exception eee) { 
                System.err.println(eee);
                setTxtCuadro(eee.toString());
                }
          }        
        }
     } catch (Exception e) {
        //e.printStackTrace();
        System.out.println(e);
        setTxtCuadro(e.toString());
     }
     return null;        
*/
  }
public void sentenceRead(SentenceEvent event) {
    // here we receive each sentence read from the port
    Sentence s = event.getSentence();
    System.out.println(s); 
    setTxtCuadro(s.toString());        
    TalkerId t=s.getTalkerId();
    boolean seModifico=false;    
    if (s.getSentenceId().equals("GLL"))
    {  GLLSentence gll= (GLLSentence) s;
       seModifico=seModifico | getPunto().setLatitud(gll.getPosition().getLatitude());
       seModifico=seModifico | getPunto().setLatHemisf(String.valueOf(gll.getPosition().getLatHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLongitud(gll.getPosition().getLongitude());
       seModifico=seModifico | getPunto().setLonHemisf(String.valueOf(gll.getPosition().getLonHemisphere().toChar()));
       if (seModifico){
          persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("DBT"))
    {  DBTSentence dbt= (DBTSentence) s;
       seModifico=seModifico | getPunto().setProfundidad(dbt.getDepth());
       if (seModifico){
          persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("RMC"))
    {  RMCSentence rmc= (RMCSentence) s;
       seModifico=seModifico | getPunto().setLatitud(rmc.getPosition().getLatitude());
       seModifico=seModifico | getPunto().setLatHemisf(String.valueOf(rmc.getPosition().getLatHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLongitud(rmc.getPosition().getLongitude());
       seModifico=seModifico | getPunto().setLonHemisf(String.valueOf(rmc.getPosition().getLonHemisphere().toChar()));
       seModifico=seModifico | getPunto().setVelocidad(rmc.getSpeed());
       seModifico=seModifico | getPunto().setRumbo(rmc.getCourse());
       if (seModifico){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("VTG"))
    {  VTGSentence vtg= (VTGSentence) s;
       seModifico=seModifico | getPunto().setVelocidad(vtg.getSpeedKmh());
       seModifico=seModifico | getPunto().setRumbo(vtg.getTrueCourse());
       if (seModifico){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("GGA"))
    {  GGASentence gga= (GGASentence) s;
       seModifico=seModifico | getPunto().setVelocidad(gga.getAltitude());
       if (seModifico){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
    }    
    
    //this.setSentencia(getSentencia() + event.getSentence());
}

    public void readingStarted() {        
        setEstado(true);
    }        
    
    public void readingStopped() {
        setEstado(false);
    }    
    
    public void readingPaused() {
        setEstado(false);
    }    
   
        
    /**
     * @return the nroCom
     */
    public int getNroCom() {
        return nroCom;
    }

    /**
     * @param nroCom the nroCom to set
     */
    public void setNroCom(int nroCom) {
        this.nroCom = nroCom;
    }

    /**
     * @return the punto
     */
    public dataManager.Punto getPunto() {
        return punto;
    }

    /**
     * @param punto the punto to set
     */
    public void setPunto(dataManager.Punto punto) {
        this.punto = punto;
    }

    /**
     * @return the ultimaSentenciaLeida
     */
    public String getUltimaSentenciaLeida() {
        return ultimaSentenciaLeida;
    }

    /**
     * @param ultimaSentenciaLeida the ultimaSentenciaLeida to set
     */
    public void setUltimaSentenciaLeida(String ultimaSentenciaLeida) {
        this.ultimaSentenciaLeida = ultimaSentenciaLeida;
    }

    /**
     * @return the estadoConexion
     */
    public boolean isEstadoConexion() {
        return estadoConexion;
    }

    /**
     * @param estadoConexion the estadoConexion to set
     */
    public void setEstadoConexion(boolean estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTxtCuadro() {
        String tmp=txtCuadro;
        txtCuadro="";
        return tmp;
    }

    /**
     * @param txtCuadro the txtCuadro to set
     */
    public void setTxtCuadro(String txtCuadroo) {
        txtCuadro=this.txtCuadro.concat("\n".concat(txtCuadroo));               
    }
    
    public void run() {
        try {
            SerialPort sp = getSerialPort();
            if (sp != null) {
                InputStream is = sp.getInputStream();
                SentenceReader sr = new SentenceReader(is);
                sr.addSentenceListener(this);
                sr.start();
            }            
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println(e);
            setTxtCuadro(e.toString());
        }        
    }
}