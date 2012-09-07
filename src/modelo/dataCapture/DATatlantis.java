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
    private String datFileName;
    private int indiceDat;
    private String conversorFileName;
    private String sevenZLibFileName;
    private static final byte NRO_COL_FRECUENCIA=2;
    private static final byte NRO_COL_GANANCIA=3;
    private static final byte NRO_COL_STC=4;
    private static final byte NRO_COL_LW=5;
    private static final byte NRO_COL_GS=6;
    private static final byte NRO_COL_ESCALA=7;
    private static final byte NRO_COL_SHIFT=8;
    private static final byte NRO_COL_EXPANDER=9;
    private static final byte NRO_COL_UNIDAD=15;
    private static final byte NRO_COL_UNIDAD_MEDIDA=16;
    private static final byte NRO_COL_HORA=19;
    private static final byte NRO_COL_LATITUD=21;
    private static final byte NRO_COL_EO=22;
    private static final byte NRO_COL_LONGITUD=23;
    private static final byte NRO_COL_NS=24;
    private static final byte NRO_COL_VELOCIDAD=25;
    private static final byte NRO_COL_RUMBO=26;
    private static final byte NRO_COL_FECHA=27;
    private static final byte NRO_COL_VELOCIDADPROM=11;
    private static final byte NRO_COL_PROFUNDIDAD=14;
    private static final byte NRO_COL_TEMPERATURA=18;//mentira, no se cual de las variables leidas es la temperatura
    
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
            for (int i=0;i<byteArray.length;i++){
                nro[nro.length-i-1] = byteArray[i];
            }            
            ByteBuffer bb = ByteBuffer.wrap(nro);
            salida =bb.getLong();
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

    public ArrayList leerDat(String rutaFileDat){
        ArrayList valoresPorPixel = new ArrayList();
        try{
            if (decompressData(rutaFileDat)){ 
                ArrayList<modelo.dataManager.SondaSetHistorico> sondaSets = new ArrayList();
                ArrayList<modelo.dataManager.PuntoHistorico> puntos = new ArrayList();           
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
                            if (sondaSets.size()==131){
                                sondaSets.size();
                             }
                            sondaSets.add(getSondaSetHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                            puntos.add(getPuntoHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                        }catch(Exception e){
                            Logueador.getInstance().agregaAlLog("Error al intentar guardar datos de parametros byte leidos del pixel "+(sondaSets.size()-1));
                        }
                        parametrosByteDeUnPixel = new ArrayList();
                    }
                    setIndiceDat(getIndiceDat()+1);
                }
                valoresPorPixel.add(sondaSets);
                valoresPorPixel.add(puntos);                
            }else{
                Logueador.getInstance().agregaAlLog("leerDat(): Error al descomprimir DAT");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return valoresPorPixel;
    }
 
/*
    public int avanzaNulos(){
      int ultimoNoNulo=-1;
      try{ 
           byte byteLeido;
           boolean salir = false;
           setIndiceDat(getIndiceDat()+1);
           while (getIndiceDat()<getDatosDescomprimidos().length && 
                   !salir){
               byteLeido = getDatosDescomprimidos()[getIndiceDat()];
               if (byteLeido !=0){
                   salir=true;
               }else{
                   setIndiceDat(getIndiceDat()+1);
               }
           }
           if (salir){
              ultimoNoNulo= (byte) byteLeido;
        }
      }
      catch(Exception e){
        Logueador.getInstance().agregaAlLog("Error avanzaNulos(): "+e.toString());
      }
      return ultimoNoNulo;
    }
*/
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

    public static void main(String args[]){
        //ExtractItemsSimple eis = new ExtractItemsSimple();
        //eis.disparar();
        DATatlantis dat = new DATatlantis();
        dat.leerDat("D:\\Dropbox\\NetBeansProjects\\IBAPE\\Historico\\camp12\\-0001-260411-142357.dat");
    }
}

class FileReaderAsBlocks {
    FileInputStream _fileInput;
    BufferedInputStream _stream;
    int _longReaded; // ultima longitud de bytes leída
    File _file;
    int _blockLength; // tamaño del espacio de bytes
    byte [] _block; // espacio de bytes de lectura

    public FileReaderAsBlocks( File file , int blocksLength ) throws FileNotFoundException{
        _file = file; //file, hace referencia al archivo que se leerá
        _blockLength = blocksLength; //blocksLength, tamaño del bloque de lectura
        _block = new byte[blocksLength];// se crea el espacio de bytes, en el que almacenarán las lecturas
        _longReaded = 1;
        open();
    }
    private void open() throws FileNotFoundException{
        _fileInput = new FileInputStream(_file);//Se prepara el archivo para lectura
        _stream = new BufferedInputStream(_fileInput);
    }
    public byte [] readBytes() throws IOException{
        if(_longReaded > 0){
            _longReaded = _stream.read(_block);
            if(!(_longReaded > 0)){
                close();
                return new byte [0];
            }
        }
        byte [] aux = new byte[_longReaded];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = _block[i];
        }
        return aux;
    }
    public String readString() throws IOException{
        return new String(readBytes(),"UTF8");
    }
    public boolean isEOF(){
        return (_longReaded > 0)?false:true;
    }
    private void close() throws IOException{
        _stream.close();
    }
}
/*
class SevenZipJBindingInitCheck {
    public static void main(String[] args) throws SevenZipException, FileNotFoundException {
        try {
            SevenZip.initSevenZipFromPlatformJAR();
            System.out.println("7-Zip-JBinding library was initialized");
            SevenZipJBindingInitCheck sevenZbinding = new SevenZipJBindingInitCheck();
            sevenZbinding.openArchive("D:\\Dropbox\\NetBeansProjects\\IBAPE\\Historico\\camp12\\-0001-260411-142357.dat");
        } catch (SevenZipNativeInitializationException e) {
            e.printStackTrace();
        }
    }
    
    public void openArchive(String archiveFilename) throws SevenZipException, FileNotFoundException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(archiveFilename, "r");
        ISevenZipInArchive inArchive = SevenZip.openInArchive(null, // Choose format automatically
                new RandomAccessFileInStream(randomAccessFile));
        inArchive.getNumberOfItems();
        //http://sevenzipjbind.sourceforge.net/first_steps.html
    }
}

*/
class ExtractItemsSimple {
    //public static void main(String[] args) {
    public void disparar(){
        String rutaDat = "D:\\Dropbox\\NetBeansProjects\\IBAPE\\Historico\\camp12\\-0001-260411-142357.dat";
//        if (jajajaja.length == 0) {
//            System.out.println("Usage: java ExtractItemsSimple <archive-name>");
//            return;
//        }
        RandomAccessFile randomAccessFile = null;
        ISevenZipInArchive inArchive = null;
        try {
            randomAccessFile = new RandomAccessFile(rutaDat, "r");
            inArchive = SevenZip.openInArchive(null, // autodetect archive type
                    new RandomAccessFileInStream(randomAccessFile));

            // Getting simple interface of the archive inArchive
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

            System.out.println("   Hash   |    Size    | Filename");
            System.out.println("----------+------------+---------");

            for (ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                final int[] hash = new int[] { 0 };
                if (!item.isFolder()) {
                    ExtractOperationResult result;

                    final long[] sizeArray = new long[1];
                    result = item.extractSlow(new ISequentialOutStream() {
                        public int write(byte[] data) throws SevenZipException {
                            hash[0] ^= Arrays.hashCode(data); // Consume data
                            sizeArray[0] += data.length;
                            return data.length; // Return amount of consumed data
                        }
                    });
                    if (result == ExtractOperationResult.OK) {
                        System.out.println(String.format("%9X | %10s | %s", // 
                                hash[0], sizeArray[0], item.getPath()));
                    } else {
                        System.err.println("Error extracting item: " + result);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurs: " + e);
            System.exit(1);
        } finally {
            if (inArchive != null) {
                try {
                    inArchive.close();
                } catch (SevenZipException e) {
                    System.err.println("Error closing archive: " + e);
                }
            }
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e);
                }
            }
        }
    }
}
/*
*/