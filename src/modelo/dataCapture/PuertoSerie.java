/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import net.sf.marineapi.nmea.event.SentenceEvent;
import net.sf.marineapi.nmea.event.SentenceListener;
import net.sf.marineapi.nmea.io.SentenceReader;
import net.sf.marineapi.nmea.sentence.*;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import persistencia.BrokerDbMapa;
import persistencia.Logueador;
/**
 *
 * @author Sebastian
 */
abstract class PuertoSerie  extends java.util.Observable implements SentenceListener, Runnable{
    Thread ps;
    private SentenceReader sentenceReader;
    private SerialPort serialPort;
    private String ultimaSentenciaLeida;
    private int cantSentenciasEnCuadro=0;
    private String txtCuadro=new String();
    private String dispositivo="GPS";
    private int nroCom;
    private int velocidadCom=4800;
    private int bitsDeDatosCom=SerialPort.DATABITS_8;
    private int bitsParadaCom=SerialPort.STOPBITS_1;
    private int bitsParidadCom=SerialPort.PARITY_NONE;
    private int estadoConexion=0;
    private boolean estado=false;
    private modelo.dataManager.Punto punto=modelo.dataManager.Punto.getInstance();
        
    public PuertoSerie(){
    }

/*
    public PuertoSerie(int nrocom){
        //inicializacion de variables
        setNroCom(nrocom);
        if (ps == null) {
            ps = new Thread(this);
            ps.start();
        }
    }
*/    
    
    private SerialPort abrirPuertoSerie(){
        try { 
            // Abrir directamente un puerto determinado= tiempo aproximado: 36seg  
            setEstadoConexion(1);
            Logueador.getInstance().agregaAlLog("Tratando de abrir el COM"+getNroCom());
            CommPortIdentifier idCOMX = null;
            try{
                idCOMX = CommPortIdentifier.getPortIdentifier("COM"+getNroCom());
            }
            catch(UnsatisfiedLinkError e){
                if (Sistema.getInstance().estanTodosLosArchivosNecesarios()){
                    Logueador.getInstance().agregaAlLog("Error al intentar abrir el puerto serie especificado\n"+
                            e.toString());
                }
                else{
                    if (Sistema.getInstance().copiarArchivosNecesarios()){
                        JOptionPane.showMessageDialog(null, "IBAPE fue inicializado correctamente, es necesario que reinicie la aplicación");
                        try{
                            idCOMX = CommPortIdentifier.getPortIdentifier("COM"+getNroCom()); //try again
                        }
                        catch(Exception exc){
                            Logueador.getInstance().agregaAlLog("Se cargó la DLL pero aun asi no se pudo abrir el COM solicitado\n"+
                                    exc.toString());
                        } 
                    }                    
                }
            }           
            if (idCOMX != null){
                Logueador.getInstance().agregaAlLog("COM"+getNroCom()+" abierto!");
                setSerialPort((SerialPort) idCOMX.open("SerialIBAPE", 30)); //este .open() [CommPort] genera el segundo thread
                getSerialPort().setSerialPortParams(getVelocidadCom(), getBitsDeDatosCom(),getBitsParadaCom(), getBitsParidadCom());                
            }
            return getSerialPort();
            
        } catch (PortInUseException portInUseExc) {
              JOptionPane.showMessageDialog(null, "El COM"+getNroCom()+" especificado está en uso", "Error al abrir el puerto", JOptionPane.ERROR_MESSAGE);
              Logueador.getInstance().agregaAlLog(portInUseExc.toString());              
              return null;
        } catch (NoSuchPortException noSuchPortExc) {
              JOptionPane.showMessageDialog(null, "El COM"+getNroCom()+" especificado no existe en este equipo", "Error al abrir el puerto", JOptionPane.ERROR_MESSAGE);
              Logueador.getInstance().agregaAlLog(noSuchPortExc.toString());
              return null;
        } catch (Exception e) {
              Logueador.getInstance().agregaAlLog(e.toString());
              JOptionPane.showMessageDialog(null, "Error desconocido al intentar abrir el COM"+getNroCom(), "Error al abrir el puerto", JOptionPane.ERROR_MESSAGE);
              return null;
        }        
    }
    
public void sentenceRead(SentenceEvent event) {
    // here we receive each sentence read from the port
    BrokerDbMapa bmn = BrokerDbMapa.getInstance();
    
    Sentence s = event.getSentence();
    System.out.println(s);
    setTxtCuadro(s.toString());        
    TalkerId t=s.getTalkerId();
    boolean seModifico=false;    
    if (s.getSentenceId().equals("GLL"))
    {  GLLSentence gll= (GLLSentence) s;
       seModifico=seModifico | getPunto().setLatHemisf(String.valueOf(gll.getPosition().getLatHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLatitud(gll.getPosition().getLatitude());
       seModifico=seModifico | getPunto().setLonHemisf(String.valueOf(gll.getPosition().getLonHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLongitud(gll.getPosition().getLongitude());
       if (seModifico && bmn.isUsarMapaNavegacion()){
          persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
       if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosGps()){
          persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("DBT"))
    {  DBTSentence dbt= (DBTSentence) s;
       seModifico=seModifico | getPunto().setProfundidad(dbt.getDepth());
       if (seModifico && bmn.isUsarMapaNavegacion()){
          persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
       if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosSonda()){
          persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("RMC"))
    {  RMCSentence rmc= (RMCSentence) s;
       seModifico=seModifico | getPunto().setLatHemisf(String.valueOf(rmc.getPosition().getLatHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLatitud(rmc.getPosition().getLatitude());
       seModifico=seModifico | getPunto().setLonHemisf(String.valueOf(rmc.getPosition().getLonHemisphere().toChar()));
       seModifico=seModifico | getPunto().setLongitud(rmc.getPosition().getLongitude());
       //seModifico=seModifico | getPunto().setVelocidad(rmc.getSpeed()); //esta velocidad solo la devuelve en nudos
       seModifico=seModifico | getPunto().setRumbo(rmc.getCourse());
       if (seModifico && bmn.isUsarMapaNavegacion()){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
       if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosGps()){
          persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("VTG"))
    {  VTGSentence vtg= (VTGSentence) s;
       seModifico=seModifico | getPunto().setVelocidad(vtg.getSpeedKmh());
       seModifico=seModifico | getPunto().setRumbo(vtg.getTrueCourse());
       if (seModifico && bmn.isUsarMapaNavegacion()){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
       if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosGps()){
          persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
       }
    }
    else
    if (s.getSentenceId().equals("GGA"))
    {  GGASentence gga= (GGASentence) s;
       seModifico=seModifico | getPunto().setAltitud(gga.getAltitude());
       if (seModifico && bmn.isUsarMapaNavegacion()){
           persistencia.BrokerDbMapa.getInstance().insert(punto.getInstance());
       }
       if (seModifico && persistencia.BrokerHistoricoPunto.getInstance().isGuardaDatosGps()){
          persistencia.BrokerHistoricoPunto.getInstance().insertPunto(punto.getInstance());
       }
    }
    
    //this.setSentencia(getSentencia() + event.getSentence());
}

    public void run() {        
        try {
            if ( (modelo.dataCapture.Gps.getInstance().getEstadoConexion() == 1) || 
                 (modelo.dataCapture.Sonda.getInstance().getEstadoConexion() == 1) ){
                    setEstadoConexion(1);
                    ps.sleep(40000); // como vemos que ya hay algun dispositivo intentando conectarse, esperamos 40 segundos a que 
                    // termine ese intento de conexión para proceder con este y evitar posibles excepciones de la DLL
            }
            else{
                if (modelo.dataCapture.PuertosSerieDelSO.getInstance().isLeyendoPuertos()) {
                    setEstadoConexion(1);
                    ps.sleep(100000); // como vemos que hay un escaneo de puertos del SO en curso, aguardamos a q termine para comenzar
                    // este intento de conexión y evitar posibles excepciones de la DLL
                }
            }
            SerialPort sp = abrirPuertoSerie();
            if (sp != null) {
                InputStream is = sp.getInputStream();
                setSentenceReader(new SentenceReader(is));
                getSentenceReader().addSentenceListener(this);
                getSentenceReader().start(); //aca se genera el tercer Thread que consume CPU como loco!
                setEstadoConexion(2);
            }                               //SentenceReader$DataReader.run:327
            else 
            { setEstadoConexion(0); 
              ps = null;
            }
        } catch (Exception e) {            
            System.out.println(e);            
            Logueador.getInstance().agregaAlLog(e.toString());
            //setTxtCuadro(e.toString());  //e.printStackTrace();
            setEstadoConexion(0);
        }
    }

    private void start() {
        if (ps == null) {
            ps = new Thread(this);
            ps.setPriority(Thread.MIN_PRIORITY);
            ps.start();  //primer Thread          
        }        
    }
    public boolean disparaLectura(){
        boolean sePudo = false;
        if (nroCom>0) {
            this.start();
            sePudo = true;
        }    
        return sePudo;
    }

    public boolean detieneLectura(){
        boolean sePudo = false;
        if (!(ps == null)) {            
           disconnect();
           //getSentenceReader().removeSentenceListener(this); //nuevo
           if (!(getSentenceReader()==null)) { 
               getSentenceReader().stop(); 
               setSentenceReader(null);  
           }                    
           ps = null;
           setEstadoConexion(0);
           sePudo=true;
        }
        return sePudo;
    }
    
    public String[] getPuertosSeriesExistentes(){
        String[] puertos = new String[30];
        int i = 0;
        try {
        // Lista de los puertos disponibles en la máquina=tiempo aproximado: 1min 12seg
        Logueador.getInstance().agregaAlLog("--- Obteniendo listado de puertos del Sistema Operativo...");
        Enumeration e = CommPortIdentifier.getPortIdentifiers();
        Logueador.getInstance().agregaAlLog("--- Listado de puertos obtenido ---");
        while( e.hasMoreElements() ) {
          CommPortIdentifier id = (CommPortIdentifier)e.nextElement();
          if( id.getPortType() == CommPortIdentifier.PORT_SERIAL ) {
    //        if( idPuerto.getName().equals("/dev/term/a") ) {  // UNIX
            try {
                //Logueador.getInstance().agregaAlLog(id.getName());
                // Vamos guardando los puertos que se encontraron
                puertos[i] = id.getName();
                i++;
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
    }
    
    public void disconnect() {
        if (getSerialPort() != null) {
            getSerialPort().removeEventListener(); //mata el segundo Thread
            getSerialPort().close();
            //setOutputStream(null);
            setSerialPort(null);
        }
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
    public modelo.dataManager.Punto getPunto() {
        return punto;
    }

    /**
     * @param punto the punto to set
     */
    public void setPunto(modelo.dataManager.Punto punto) {
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
        if (getCantSentenciasEnCuadro()<20){
            cantSentenciasEnCuadro=getCantSentenciasEnCuadro()+1;  }
        else
          { eliminaPrimerSentenciaCuadro(); }
        txtCuadro=this.txtCuadro.concat("\n".concat(txtCuadroo));               
    }
   

    public int getBitsDeDatosCom() {
        return bitsDeDatosCom;
    }

    public void setBitsDeDatosCom(int bitsDeDatosCom) {
        if (bitsDeDatosCom==5){ //bitsDeDatosCom=5
            this.bitsDeDatosCom = SerialPort.DATABITS_5;
        }
        else
          { if (bitsDeDatosCom==6) //bitsDeDatosCom=6
                { this.bitsDeDatosCom = SerialPort.DATABITS_6; }
            else
                { if (bitsDeDatosCom==7) //bitsDeDatosCom=7
                    { this.bitsDeDatosCom = SerialPort.DATABITS_7; }
                else if (bitsDeDatosCom==8) //bitsDeDatosCom=8
                        { this.bitsDeDatosCom = SerialPort.DATABITS_8; }
                }
          }                
    }

    public int getBitsParadaCom() {
        return bitsParadaCom;
    }

    public void setBitsParadaCom(int bitsParadaCom) {
        if (bitsParadaCom==1){ //bitsDeParada=1
            this.bitsParadaCom = SerialPort.STOPBITS_1;
        }
        else
          { if (bitsParadaCom==2) //bitsDeParada=1,5
                { this.bitsParadaCom = SerialPort.STOPBITS_1_5; }
            else
                { if (bitsParadaCom==3) //bitsDeParada=2
                    { this.bitsParadaCom = SerialPort.STOPBITS_2; }
                }
          }
    }

    public int getBitsParidadCom() {
        return bitsParidadCom;
    }

    public void setBitsParidadCom(int bitsParidadCom) {
        if (bitsParidadCom==0){ //paridad=ninguna
            this.bitsParidadCom = SerialPort.PARITY_NONE;
        }
        else
          { if (bitsParidadCom==1) //paridad=impar
                { this.bitsParidadCom = SerialPort.PARITY_ODD; }
            else
                { if (bitsParidadCom==2) //paridad=par
                    { this.bitsParidadCom = SerialPort.PARITY_EVEN; }
                }  
          }
    }    

    public int getVelocidadCom() {
        return velocidadCom;
    }

    public void setVelocidadCom(int velocidadCom) {
        this.velocidadCom = velocidadCom;
    }

    public int getCantSentenciasEnCuadro() {
        return cantSentenciasEnCuadro;
    }

    public void setCantSentenciasEnCuadro(int cantSentenciasEnCuadro) {
        this.cantSentenciasEnCuadro = cantSentenciasEnCuadro;
    }

    private void eliminaPrimerSentenciaCuadro() {
        txtCuadro = txtCuadro.substring(txtCuadro.indexOf("\n"));
    }

    /**
     * @return the dispositivo
     */
    public String getDispositivo() {
        return dispositivo;
    }

    /**
     * @param dispositivo the dispositivo to set
     */
    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    /**
     * @return the serialPort
     */
    public SerialPort getSerialPort() {
        return serialPort;
    }

    /**
     * @param serialPort the serialPort to set
     */
    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    /**
     * @return the sentenceReader
     */
    public SentenceReader getSentenceReader() {
        return sentenceReader;
    }

    /**
     * @param sentenceReader the sentenceReader to set
     */
    public void setSentenceReader(SentenceReader sentenceReader) {
        this.sentenceReader = sentenceReader;
    }

    /**
     * @return the estadoConexion
     */
    public int getEstadoConexion() {
        return estadoConexion;
    }

    /**
     * @param estadoConexion the estadoConexion to set
     */
    public void setEstadoConexion(int estadoConexion) {
        this.estadoConexion = estadoConexion;
        setChanged();
        notifyObservers();
    }

}