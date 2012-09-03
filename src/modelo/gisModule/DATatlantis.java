/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import com.csvreader.CsvReader;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    public ArrayList<SondaSetHistorico> leerDat(File fileDat){
      ArrayList<SondaSetHistorico> sondaSets=new ArrayList();
      try{
        //Stream para leer archivo
        FileInputStream inDat =FileInputStream(fileDat);
        boolean todoOk=true;
        int proximoByte;
        ByteBuffer bb;
        int byteLeido = avanzaNulos(inDat);
        todoOk = todoOk && proximoByte != -1;
        if (todoOk){
          SondaSetHistorico unSondaSetHistorico=new SondaSetHistorico();
          if (byteLeido>0){//leo la variable desconocida nro 6, supongo q será de tipo int
            bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            unSondaSet.setVarDesconocida6(bb.getInt());
          }
          byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable desconocida nro 7, supongo q será de tipo int
            bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            unSondaSet.setVarDesconocida7(bb.getInt());
          }
          byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable FRECUENCIA, supongo q será de tipo int
            bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            unSondaSet.setFrecuencia(bb.getInt());
          }
          byteLeido=avanzaNulos(inDat);
          todoOk= todoOk && byteLeido != -1;
          if (todoOk){//leo la variable GANANCIA, supongo q será de tipo int
            bb = ByteBuffer.wrap(new byte[] {0, 0, 0, byteLeido});
            unSondaSet.setGanancia(bb.getInt());
          }
        }  
        //se cierra archivo
        fileDat.close();
        sePudo=true;
      }
      catch(Exception e){
        Logueador.getInstance().agregaLinea("Error leerDat(): "+e.toString());
      }
      return sePudo;
    }
    
    public int avanzaNulos(FileInputStream inDat){
      int ultimoNoNulo=-1;
      try{
        int byteLeido=inDat.read();
        while(byteLeido!=-1 && byteLeido==0){//tiene q avanzar siempre q no sea EOF y haya nulos
          byteLeido=inDat.read();
        }
        ultimoNoNulo=byteLeido;
      }
      catch(Exception e){
        Logueador.getInstance().agregaLinea("Error avanzaNulos(): "+e.toString());
      }
      return sePudo;
    }
    
}