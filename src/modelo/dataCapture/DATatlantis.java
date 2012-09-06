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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.PuntoHistorico;
import modelo.dataManager.SondaSetHistorico;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.SevenZipNativeInitializationException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
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

    private String ultimoDatLeido;
    private String datFileName;
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
            File fileDat = new File(rutaFileDat);
            ArrayList<modelo.dataManager.SondaSetHistorico> sondaSets = new ArrayList();
            ArrayList<modelo.dataManager.PuntoHistorico> puntos = new ArrayList();
            FileReaderAsBlocks frab = new FileReaderAsBlocks(fileDat,1);
            ArrayList<byte[]> parametrosByteDeUnPixel = new ArrayList<byte[]>();
            int i=0;
            while (!frab.isEOF()){
                if (parametrosByteDeUnPixel.size()<29){ //para cada pixel hay 28 valores escritos consecutivamente
                    byte[] byteLeido = frab.readBytes();
                    byte[] valorEncontrado;
                    if (byteLeido[0] != 0){
                        valorEncontrado = DATatlantis.getInstance().getValorEncontrado(frab,byteLeido[0]);
                        parametrosByteDeUnPixel.add(valorEncontrado);
                    }
                }else{
                   sondaSets.add(DATatlantis.getInstance().getSondaSetHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                   puntos.add(DATatlantis.getInstance().getPuntoHistoricoFromValoresLeidos(parametrosByteDeUnPixel));
                   parametrosByteDeUnPixel = new ArrayList();
                }
                i++;
            }
            valoresPorPixel.add(sondaSets);
            valoresPorPixel.add(puntos);
        }catch(Exception e){
            System.out.println(e);
        }
        return valoresPorPixel;
    }
    
    public int avanzaNulos(FileInputStream inDat){
      int ultimoNoNulo=-1;
      byte[] buffer = new byte[1];
      try{ 
        int byteLeido=inDat.read(buffer);
        int i = 0;
        while(byteLeido!=-1 && byteLeido==0){//tiene q avanzar siempre q no sea EOF y haya nulos
          byteLeido=inDat.read(buffer);
          i++;
        }
        if (byteLeido != -1){
            ultimoNoNulo= (byte) byteLeido;
        }
      }
      catch(Exception e){
        Logueador.getInstance().agregaAlLog("Error avanzaNulos(): "+e.toString());
      }
      return ultimoNoNulo;
    }

    private byte[] getValorEncontrado(FileReaderAsBlocks frab, byte valorLeido) {
        ArrayList<Byte> valorEncontrado = new ArrayList<Byte>();
        try{
            valorEncontrado.add(valorLeido);
            byte[] nuevoValorLeido = frab.readBytes();
            while (!frab.isEOF() && nuevoValorLeido[0] != 0){
                valorEncontrado.add(nuevoValorLeido[0]);
                nuevoValorLeido = frab.readBytes();
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

class SevenZipJBindingInitCheck {
    public static void main(String[] args) {
        try {
            SevenZip.initSevenZipFromPlatformJAR();
            System.out.println("7-Zip-JBinding library was initialized");
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