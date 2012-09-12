/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import com.csvreader.CsvReader;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.PuntoHistorico;
import modelo.dataManager.SondaSetHistorico;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import persistencia.Logueador;







/**
Esta es una clase que hize, para leer archivos en bloques de bytes, espero te sirva. Saludos!
*/




/**
 *
 * @author Sebastian
 */
public class DATatlantis{
    private static DATatlantis unicaInstancia;
    private byte[] datosDescomprimidos;
    private String ultimoDatLeido;
    private Date fechaYhoraUltimoDatLeido;
    private ArrayList datosFromDat;
    private String datFileName;
    private int indiceDat;
    private String conversorFileName;
    private static final byte NRO_COL_DESC6=0;
    private static final byte NRO_COL_DESC7=1;
    private static final byte NRO_COL_FRECUENCIA=2;
    private static final byte NRO_COL_GANANCIA=3;
    private static final byte NRO_COL_STC=4;
    private static final byte NRO_COL_LW=5;
    private static final byte NRO_COL_GS=6;
    private static final byte NRO_COL_ESCALA=7;
    private static final byte NRO_COL_SHIFT=8;
    private static final byte NRO_COL_EXPANDER=9;
    private static final byte NRO_COL_DESC2=10;
    private static final byte NRO_COL_VELOCIDADPROM=11;
    private static final byte NRO_COL_DESC5=12;
    private static final byte NRO_COL_PROFUNDIDAD=14;
    private static final byte NRO_COL_UNIDAD=15;
    private static final byte NRO_COL_UNIDAD_MEDIDA=16;
    private static final byte NRO_COL_DESC1=17;
    private static final byte NRO_COL_DESC3=18;
    private static final byte NRO_COL_TEMPERATURA=18;//mentira, no se cual de las variables leidas es la temperatura
    private static final byte NRO_COL_HORA=19;
    private static final byte NRO_COL_DESC4=20;
    private static final byte NRO_COL_LATITUD=21;
    private static final byte NRO_COL_EO=22;
    private static final byte NRO_COL_LONGITUD=23;
    private static final byte NRO_COL_NS=24;
    private static final byte NRO_COL_VELOCIDAD=25;
    private static final byte NRO_COL_RUMBO=26;
    private static final byte NRO_COL_FECHA=27;
    private static final byte NRO_COL_DESC8=28;
    private static final byte NRO_COL_DESC9=29;
    
    
    private DATatlantis(){
        inicializador();
    }
    
    public static DATatlantis getInstance(){
        if (unicaInstancia == null){
            unicaInstancia = new DATatlantis();
        }
        return unicaInstancia;
    }

    private void inicializador(){
        setDatosDescomprimidos(null);
    }

    private modelo.dataManager.DatosDesconocidosFromDat getDatosDesconocidosFromValoresLeidos(ArrayList valoresByteDeUnPixel) {
        modelo.dataManager.DatosDesconocidosFromDat ddfd = new modelo.dataManager.DatosDesconocidosFromDat();
        ddfd.setVarDesconocida1((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC1))));
        ddfd.setVarDesconocida2((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC2))));
        ddfd.setVarDesconocida3((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC3))));
        ddfd.setVarDesconocida4((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC4))));
        ddfd.setVarDesconocida5((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC5))));
        ddfd.setVarDesconocida6((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC6))));
        ddfd.setVarDesconocida7((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC7))));
        ddfd.setVarDesconocida8((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC8))));
        //ddfd.setVarDesconocida9((int)(getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_DESC9))));
        return ddfd;
    }
    
    private SondaSetHistorico getSondaSetHistoricoFromValoresLeidos(ArrayList valoresByteDeUnPixel) {
        SondaSetHistorico ssh = new SondaSetHistorico();
        ssh.setEscala((int)(2.5*getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_ESCALA))));
        ssh.setExpander((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_EXPANDER)));
        ssh.setFrecuencia((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_FRECUENCIA)));
        ssh.setGanancia((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_GANANCIA)));
        ssh.setLineaBlanca((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_LW)));
        ssh.setShift((int)(2.5*getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_SHIFT))));
        ssh.setStc((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_STC)));
        ssh.setUnidadDeEscala((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_UNIDAD_MEDIDA)));
        ssh.setVelPantalla((int)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_GS)));
        return ssh;
    }

    private PuntoHistorico getPuntoHistoricoFromValoresLeidos(ArrayList valoresByteDeUnPixel) {
        PuntoHistorico ph = new PuntoHistorico();
        ph.setFechaYhora(armaDate(Integer.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_FECHA))), 
                Integer.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_HORA)))));
        ph.setLatitud(getLatEnGradosDecimalesFromString(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_LATITUD))));
        ph.setLongitud(getLonEnGradosDecimalesFromString(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_LONGITUD))));
        ph.setProfundidad((double)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_PROFUNDIDAD)));
        ph.setRumbo(Double.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_RUMBO))));
        ph.setVelocidad(Double.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_VELOCIDAD))));
        return ph;
    }
   
    private long getLongFromByteArray(byte[] byteArray){
        long salida = 0;
        try{
            byte[] nro = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
            if (byteArray.length<= nro.length){
                for (int i=0;i<byteArray.length;i++){
                    nro[nro.length-i-1] = byteArray[i];
                }
                ByteBuffer bb = ByteBuffer.wrap(nro);
                salida =bb.getLong();
           }else{
                //int i = 0;//el valor leido es demaciado grande y no puede ser convertido
           }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("getLongFromByteArray(): "+e.toString());
        }
        return salida;
    }

    private String getStringFromByteArray(byte[] byteArray){        
        String salida = "";
        try{
            salida = new String (byteArray);;
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("getStringFromByteArray(): "+e.toString());
        }
        return salida;
    }
    
//http://stackoverflow.com/questions/1026761/how-to-convert-a-byte-array-to-its-numeric-value-java
//leer cada pixel por bloques de (confirmadisimo!!! --->((((bloques de 184bytes!)))))/187 bytes---> 1px = 184b de DAT | 
//    antes de empezar hay 177nulos
//177920    / 456px ancho= 84088b 
//          / 967px ancho = 178112b
    public boolean leerDat(String rutaFileDat){
        boolean sePudo=false;
        String datFileNamee = rutaFileDat.toLowerCase().substring(rutaFileDat.lastIndexOf("\\")+1,rutaFileDat.length());
        ArrayList valoresPorPixel = new ArrayList();
        ArrayList pixelesConErrorAlGuardarValores = new ArrayList();
        ArrayList<modelo.dataManager.SondaSetHistorico> sondaSets = new ArrayList();
        ArrayList<modelo.dataManager.PuntoHistorico> puntos = new ArrayList();
        ArrayList<modelo.dataManager.DatosDesconocidosFromDat> datosDesconocidos = new ArrayList();
        try{
            if (getUltimoDatLeido()== null || (!getUltimoDatLeido().toLowerCase().equals(datFileNamee))){
                if (decompressData(rutaFileDat)){
                    ArrayList<byte[]> parametrosByteDeUnPixel = new ArrayList<byte[]>();
                    setIndiceDat(0);
                    while (getIndiceDat()<getDatosDescomprimidos().length){
                        if (parametrosByteDeUnPixel.size()<29){ //para cada pixel hay 28 valores escritos consecutivamente
                            byte byteLeido = getDatosDescomprimidos()[getIndiceDat()];
                            byte[] valorEncontrado;
                            if (byteLeido != 0){
                                valorEncontrado = getValorEncontrado(byteLeido);
                                parametrosByteDeUnPixel.add(valorEncontrado);
                            }
                        }else{
                            setIndiceDat(getIndiceDat()+10);//salteo el posible valor erroneo q aveces hay al final
                            try{
                                sondaSets.add(getSondaSetHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                                puntos.add(getPuntoHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                                datosDesconocidos.add(getDatosDesconocidosFromValoresLeidos(parametrosByteDeUnPixel));
                            }catch(Exception e){
                                pixelesConErrorAlGuardarValores.add(sondaSets.size()-1);
                            }
                            parametrosByteDeUnPixel = new ArrayList();
                        }
                        setIndiceDat(getIndiceDat()+1);
                    }
                    valoresPorPixel.add(sondaSets);
                    valoresPorPixel.add(puntos);
                    valoresPorPixel.add(datosDesconocidos);
                    sePudo=true;
                }else{
                    Logueador.getInstance().agregaAlLog("leerDat(): Error al descomprimir DAT");
                }
            }else{
                sePudo=true;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        if (pixelesConErrorAlGuardarValores.size()>0){
            Logueador.getInstance().agregaAlLog("Error al intentar guardar datos de parametros leidos de "+(pixelesConErrorAlGuardarValores.size())+" pixeles");
        }else{
            setUltimoDatLeido(rutaFileDat.substring(rutaFileDat.lastIndexOf("\\")+1,rutaFileDat.length()));
            setFechaYhoraUltimoDatLeido(Calendar.getInstance().getTime());
            setDatosFromDat(valoresPorPixel);
            if (sondaSets.size()>0){
               SondaSetHistorico ultimoSsh = sondaSets.get(sondaSets.size()-1);
               ultimoSsh.setUsadoDesde(puntos.get(0).getFechaYhora());//mentira
               ultimoSsh.setUsadoHasta(puntos.get(sondaSets.size()-1).getFechaYhora());
               actualizaSondaSet(ultimoSsh);
            }
        }
        if (!sePudo){
           controllers.ControllerNavegacion.getInstance().errorGuiProcesamientoImgs();
        }
        return sePudo;
    }

    private byte[] getValorEncontrado(byte valorLeido) {
        ArrayList<Byte> valorEncontrado = new ArrayList<Byte>();
        try{
            valorEncontrado.add(valorLeido);
            setIndiceDat(getIndiceDat()+1);
            byte nuevoValorLeido;
            boolean salir = false;
            while (getIndiceDat()<getDatosDescomprimidos().length && 
                    !salir){
                nuevoValorLeido = getDatosDescomprimidos()[getIndiceDat()];
                if (nuevoValorLeido !=0){
                    valorEncontrado.add(nuevoValorLeido);
                    setIndiceDat(getIndiceDat()+1);
                }else{
                    salir=true;
                }
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("getValorEncontrado(): "+e.toString());
        }
        byte[] data = new byte[valorEncontrado.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) valorEncontrado.get(i);
        }        
        return data;
    }
    
    public Date armaDate(int fecha, int horario) {
        int hora = Integer.parseInt(String.valueOf(horario).substring(0, 2));
        int minutos = Integer.parseInt(String.valueOf(horario).substring(2, 4));
        int segundos = Integer.parseInt(String.valueOf(horario).substring(4, 6));
        int dia = Integer.parseInt(String.valueOf(fecha).substring(0, 2));
        int mes = Integer.parseInt(String.valueOf(fecha).substring(2, 4));
        int anio = Integer.parseInt(String.valueOf(fecha).substring(4, 6));
        
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes-1, dia, hora, minutos, segundos);        
        return calendario.getTime();
    }
    
    public double getLatEnGradosDecimalesFromString(String stringFromByteArray) {
       String latitudStr = stringFromByteArray.substring(0,stringFromByteArray.length()-1);
       String latHemisf = stringFromByteArray.substring(stringFromByteArray.length()-1,stringFromByteArray.length());
       double latitud = Double.valueOf(latitudStr.substring(0, 2)) + (Double.valueOf(latitudStr.substring(2, latitudStr.length()))/60);
       if (latHemisf.toLowerCase().contains("s")){
           latitud=latitud*(-1);
       }
       return latitud;
    }

    public double getLonEnGradosDecimalesFromString(String stringFromByteArray) {
       String longitudStr = stringFromByteArray.substring(0,stringFromByteArray.length()-1);
       String lonHemisf = stringFromByteArray.substring(stringFromByteArray.length()-1,stringFromByteArray.length());
       double longitud = Double.valueOf(longitudStr.substring(0, 3)) + (Double.valueOf(longitudStr.substring(3, longitudStr.length()))/60);
       if (lonHemisf.toLowerCase().contains("w")){
           longitud=longitud*(-1);
       }
       return longitud;
    }

    public byte[] byteArrayConcat(byte[] array1, byte[] array2){
        if (array1==null){
            array1= new byte[0];
        }
        if (array2==null){
            array2=new byte[0];
        }
        byte[] salida = new byte[array1.length + array2.length]; 
        System.arraycopy(array1, 0, salida, 0, array1.length);
        System.arraycopy(array2, 0, salida, array1.length, array2.length);        
        return salida;
    }
    
    public boolean decompressData(String rutaDat) {
        boolean sePudo=false;
        setDatosDescomprimidos(null);
        ISevenZipInArchive inArchive = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(rutaDat, "r");
            inArchive = SevenZip.openInArchive(null, // autodetect archive type
                    new RandomAccessFileInStream(randomAccessFile));
            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            ISimpleInArchiveItem item = simpleInArchive.getArchiveItem(0);
            final int[] hash = new int[] { 0 };
            final long[] sizeArray = new long[1];
            ExtractOperationResult result = item.extractSlow(new ISequentialOutStream() {
                public int write(byte[] data) throws SevenZipException {
                    hash[0] ^= Arrays.hashCode(data); // Consume data
                    sizeArray[0] += data.length; 
                    if (data != null && data.length>0){
                       setDatosDescomprimidos(byteArrayConcat(getDatosDescomprimidos(), data));
                    }
                    return data.length; // Return amount of consumed data
                }
            });
            if (result == ExtractOperationResult.OK) {
                sePudo=true;
            } else {
                setDatosDescomprimidos(null);
                Logueador.getInstance().agregaAlLog("Error extracting item: " + result.toString());
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("Error occurs: " + e);
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException e) {
                    Logueador.getInstance().agregaAlLog("Error closing archive: " + e);
                }
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    Logueador.getInstance().agregaAlLog("Error closing file: " + e);
                }
            }
        }
        return sePudo;
    }

    /**
     * @return the datosDescomprimidos
     */
    public byte[] getDatosDescomprimidos() {
        return datosDescomprimidos;
    }

    /**
     * @param datosDescomprimidos the datosDescomprimidos to set
     */
    public void setDatosDescomprimidos(byte[] datosDescomprimidos) {
        this.datosDescomprimidos = datosDescomprimidos;
    }

    /**
     * @return the indiceDat
     */
    public int getIndiceDat() {
        return indiceDat;
    }

    /**
     * @param indiceDat the indiceDat to set
     */
    public void setIndiceDat(int indiceDat) {
        this.indiceDat = indiceDat;
    }

    public ArrayList getDatosFromPixel(String jpgFileName, int nroPixelX){
        ArrayList datosPixelX=new ArrayList();
        String fileNameDelDat = jpgFileName.toLowerCase().replace(".jpg", ".dat");
        //System.getProperty("user.dir")+"\\"+LanSonda.getInstance().getCarpetaHistoricoLocal()+"\\"+Csv.getInstance().getConversorFileName();
        String rutaDat = AdministraCampanias.getInstance().getFullFolderHistoricoDeCampActual()+"\\"+fileNameDelDat;
        //"D:\\Facultad\\Proyecto Final\\E-Naval\\Historicos\\SOUNDER1\\History\\-0001-260411-142357.dat";        
        if (!DATatlantis.getInstance().getUltimoDatLeido().toLowerCase().equals(fileNameDelDat)){
            if (DATatlantis.getInstance().leerDat(rutaDat)){
                datosPixelX.add(((ArrayList<modelo.dataManager.SondaSetHistorico>)DATatlantis.getInstance().getDatosFromDat().get(0)).get(indiceDat));
                datosPixelX.add(((ArrayList<modelo.dataManager.PuntoHistorico>)DATatlantis.getInstance().getDatosFromDat().get(1)).get(indiceDat));
                datosPixelX.add(((ArrayList<modelo.dataManager.DatosDesconocidosFromDat>)DATatlantis.getInstance().getDatosFromDat().get(2)).get(indiceDat));
            }
        }else{
            datosPixelX.add(((ArrayList<modelo.dataManager.SondaSetHistorico>)DATatlantis.getInstance().getDatosFromDat().get(0)).get(indiceDat));
            datosPixelX.add(((ArrayList<modelo.dataManager.PuntoHistorico>)DATatlantis.getInstance().getDatosFromDat().get(1)).get(indiceDat));
            datosPixelX.add(((ArrayList<modelo.dataManager.DatosDesconocidosFromDat>)DATatlantis.getInstance().getDatosFromDat().get(2)).get(indiceDat));
        }
        return datosPixelX;
    }

    /**
     * @return the datosFromDat
     */
    public ArrayList getDatosFromDat() {
        return datosFromDat;
    }

    /**
     * @param datosFromDat the datosFromDat to set
     */
    public void setDatosFromDat(ArrayList datosFromDat) {
        this.datosFromDat = datosFromDat;
    }

    /**
     * @return the fechaYhoraUltimoDatLeido
     */
    public Date getFechaYhoraUltimoDatLeido() {
        return fechaYhoraUltimoDatLeido;
    }

    /**
     * @param fechaYhoraUltimoDatLeido the fechaYhoraUltimoDatLeido to set
     */
    public void setFechaYhoraUltimoDatLeido(Date fechaYhoraUltimoDatLeido) {
        this.fechaYhoraUltimoDatLeido = fechaYhoraUltimoDatLeido;
    }

    /**
     * @return the ultimoDatLeido
     */
    public String getUltimoDatLeido() {
        return ultimoDatLeido;
    }

    /**
     * @param ultimoDatLeido the ultimoDatLeido to set
     */
    public void setUltimoDatLeido(String ultimoDatLeido) {
        this.ultimoDatLeido = ultimoDatLeido;
    }

    private boolean actualizaSondaSet(SondaSetHistorico ultimoSsh) {
        boolean sePudo = false;
        try{
            if (ultimoSsh != null){
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
                modelo.dataManager.SondaSet.getInstance().setPixelXdesde(ultimoSsh.getPixelXdesde());
                modelo.dataManager.SondaSet.getInstance().setPixelXhasta(ultimoSsh.getPixelXhasta());
                modelo.dataManager.SondaSet.getInstance().setVelPantalla(ultimoSsh.getVelPantalla());
                sePudo=true;
/*
                if (persistencia.BrokerHistoricoSondaSet.getInstance().isGuardaDatosSondaSets()){
                   if (!(LanSonda.getInstance().guardaSondaSets(ssh))){ //si está habilitado el logueo de SondaSets, guardo los cambios en la dbHistorico de esta campania
                      Logueador.getInstance().agregaAlLog("No se pudieron guardar los últimos Presets leidos de la Sonda");                      
                   }else{
                       sePudo=true;
                   }
                }else{
                    sePudo=true;
                }
*/
            }
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
}