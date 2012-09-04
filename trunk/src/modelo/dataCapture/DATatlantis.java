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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.SondaSetHistorico;
import persistencia.Logueador;



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
    private static final byte NRO_COL_FRECUENCIA=0;
    private static final byte NRO_COL_GANANCIA=1;
    private static final byte NRO_COL_STC=2;
    private static final byte NRO_COL_LW=3;
    private static final byte NRO_COL_GS=4;
    private static final byte NRO_COL_ESCALA=5;
    private static final byte NRO_COL_SHIFT=6;
    private static final byte NRO_COL_EXPANDER=7;
    private static final byte NRO_COL_UNIDAD=8;
    private static final byte NRO_COL_UNIDAD_MEDIDA=9;
    private static final byte NRO_COL_HORA=10;
    private static final byte NRO_COL_LATITUD=11;
    private static final byte NRO_COL_EO=12;
    private static final byte NRO_COL_LONGITUD=13;
    private static final byte NRO_COL_NS=14;
    private static final byte NRO_COL_VELOCIDAD=15;
    private static final byte NRO_COL_RUMBO=16;
    private static final byte NRO_COL_FECHA=17;
    private static final byte NRO_COL_VELOCIDADPROM=18;
    private static final byte NRO_COL_PROFUNDIDAD=19;
    private static final byte NRO_COL_TEMPERATURA=20;    
    
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
    
//http://stackoverflow.com/questions/1026761/how-to-convert-a-byte-array-to-its-numeric-value-java

    public ArrayList<SondaSetHistorico> leerDat(String rutaFileDat){
      File fileDat = new File(rutaFileDat);
      ArrayList<SondaSetHistorico> sondaSets=new ArrayList();
      try{
        //Stream para leer archivo
        FileInputStream inDat = new FileInputStream(fileDat);
        boolean todoOk=true;
        //int proximoByte=-1;
        ByteBuffer bb;
        byte[] buffer= new byte[1];
        int byteLeido = avanzaNulos(inDat);
        todoOk = todoOk && byteLeido != -1;
        if (todoOk){
          SondaSetHistorico unSsh = new SondaSetHistorico();
          ArrayList nroLeido = new ArrayList();
          int i=0;
          while (byteLeido>-1){
             nroLeido.add(byteLeido);
             byteLeido=inDat.read();
          }
          while (byteLeido>0){//leo la variable desconocida nro 6, supongo q será de tipo int
            //bb = ByteBuffer.wrap(nroLeido);
            //unSsh.setVarDesconocida6(bb.getInt());
          }
          //byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable desconocida nro 7, supongo q será de tipo int
            //bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            //unSondaSet.setVarDesconocida7(bb.getInt());
          }
          byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable FRECUENCIA, supongo q será de tipo int
            //bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            //unSondaSet.setFrecuencia(bb.getInt());
          }
          byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable GANANCIA, supongo q será de tipo int
            //bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            //unSondaSet.setGanancia(bb.getInt());
          }
          
        }  
        //se cierra archivo
        //fileDat.close();
        sondaSets=new ArrayList();
      }
      catch(Exception e){
        Logueador.getInstance().agregaAlLog("Error leerDat(): "+e.toString());
      }
      return sondaSets;
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

    public static void main(String args[]){
        try{
            String ruta="D:\\Dropbox\\NetBeansProjects\\IBAPE\\Historico\\camp12\\-0001-260411-142357";
            // 1001 0010 1110 = 2350 / 3730
            //DATatlantis dat = new DATatlantis();
            //dat.leerDat("D:\\Dropbox\\NetBeansProjects\\IBAPE\\Historico\\camp12\\-0001-260411-142357");
            File archivo = new File(ruta);
            FileReaderAsBlocks frab = new FileReaderAsBlocks(archivo,1);
            ArrayList valores = new ArrayList();
            int i=0;
            while (!frab.isEOF()){    
                byte[] valorLeido = frab.readBytes();
                if (valorLeido[0] != 0){
                    valores.add(valorLeido);
                }
                i++;
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}




/**
Esta es una clase que hize, para leer archivos en bloques de bytes, espero te sirva. Saludos!
*/
/**
*
* @author Disegni
*/
class FileReaderAsBlocks {

/**
* atributos de case para lectura de archivo
*/
FileInputStream _fileInput;
BufferedInputStream _stream;
int _longReaded; // ultima longitud de bytes leída

File _file;
int _blockLength; // tamaño del espacio de bytes
byte [] _block; // espacio de bytes de lectura
/**
* Constructor:
* @param file, hace referencia al archivo que se leerá
* @param blocksLength, tamaño del bloque de lectura
*/

public FileReaderAsBlocks( File file , int blocksLength ) throws FileNotFoundException{
_file = file;
_blockLength = blocksLength;
// se crea el espacio de bytes, en el que almacenarán las lecturas
_block = new byte[blocksLength];
_longReaded = 1;
open();
}
private void open() throws FileNotFoundException{
//Se prepara el archivo para lectura
_fileInput = new FileInputStream(_file);
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