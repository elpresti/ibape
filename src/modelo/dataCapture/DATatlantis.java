/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import modelo.dataManager.PuntoHistorico;
import modelo.dataManager.SondaSetHistorico;
import modelo.dataManager.UltimaImgProcesada;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import persistencia.Logueador;

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
    private byte NRO_COL_DESC1=0;
    private int[] BYTES_DESC1={0,3};
    private byte NRO_COL_DESC2=1;
    private int[] BYTES_DESC2={4,7};
    private byte NRO_COL_FRECUENCIA=2;
    private int[] BYTES_FRECUENCIA={8,11};
    private byte NRO_COL_GANANCIA=3;
    private int[] BYTES_GANANCIA={12,15};
    private byte NRO_COL_STC=4;
    private int[] BYTES_STC={16,19};
    private byte NRO_COL_LW=5;
    private int[] BYTES_LW={20,23};
    private byte NRO_COL_GS=6;
    private int[] BYTES_GS={24,27};
    private byte NRO_COL_DESC3=7;
    private int[] BYTES_DESC3={28,31};
    private byte NRO_COL_DESC4;
    private int[] BYTES_DESC4={32,35};
    private byte NRO_COL_ESCALA=7;
    private int[] BYTES_ESCALA={36,39};
    private byte NRO_COL_SHIFT=8;
    private int[] BYTES_SHIFT={40,43};
    private byte NRO_COL_EXPANDER=9;
    private int[] BYTES_EXPANDER={44,47};
    private byte NRO_COL_DESC5=10;
    private int[] BYTES_DESC5={48,51};
    private byte NRO_COL_VELOCIDADPROM=11;
    private int[] BYTES_VELOCIDADPROM={52,55};
    private byte NRO_COL_DESC6=12;
    private int[] BYTES_DESC6={56,59};
    private byte NRO_COL_FRECLTR=12;
    private int[] BYTES_FRECLTR={60,63};
    private byte NRO_COL_PROFUNDIDAD=14;
    private int[] BYTES_PROFUNDIDAD={78,81};
    private byte NRO_COL_UNIDAD=15;
    private int[] BYTES_UNIDAD={88,91};
    private byte NRO_COL_UNIDAD_MEDIDA=16;
    private int[] BYTES_UNIDAD_MEDIDA={92,95};
    private byte NRO_COL_DESC7=17;
    private int[] BYTES_DESC7={96,99};
    private byte NRO_COL_DESC8=18;
    private int[] BYTES_DESC8={100,103};
    private byte NRO_COL_TEMPERATURA=18;//mentira, no se cual de las variables leidas es la temperatura
    private int[] BYTES_TEMPERATURA={,};
    private byte NRO_COL_HORA=19;
    private int[] BYTES_HORA={112,117};
    private byte NRO_COL_DESC9=20;
    private int[] BYTES_DESC9={119,119};
    private byte NRO_COL_LATITUD=21;
    private int[] BYTES_LATITUD={121,130};
    private byte NRO_COL_EO=22;
    private int[] BYTES_EO={132,132};
    private byte NRO_COL_LONGITUD=23;
    private int[] BYTES_LONGITUD={134,144};
    private byte NRO_COL_NS=24;
    private int[] BYTES_NS={146,146};
    private byte NRO_COL_VELOCIDAD=25;
    private int[] BYTES_VELOCIDAD={148,153};
    private byte NRO_COL_RUMBO=26;
    private int[] BYTES_RUMBO={154,159};
    private byte NRO_COL_FECHA=27;
    private int[] BYTES_FECHA={160,165};
    private byte NRO_COL_DESC10=28;
    private int[] BYTES_DESC10={68,75};
    private byte NRO_COL_DESC11=29;
    private int[] BYTES_DESC11={77,82};
    
    
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

    private modelo.dataManager.DatosDesconocidosFromDat getDatosDesconocidosFromValoresLeidos2(byte[] valoresByteDeUnPixel) {
        modelo.dataManager.DatosDesconocidosFromDat ddfd = new modelo.dataManager.DatosDesconocidosFromDat();
        ddfd.setVarDesconocida1((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC1[0],BYTES_DESC1[1]+1))));
        ddfd.setVarDesconocida2((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC2[0],BYTES_DESC2[1]+1))));
        ddfd.setVarDesconocida3((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC3[0],BYTES_DESC3[1]+1))));
        ddfd.setVarDesconocida4((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC4[0],BYTES_DESC4[1]+1))));
        ddfd.setVarDesconocida5((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC5[0],BYTES_DESC5[1]+1))));
        ddfd.setVarDesconocida6((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC6[0],BYTES_DESC6[1]+1))));
        ddfd.setVarDesconocida7((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC7[0],BYTES_DESC7[1]+1))));
        ddfd.setVarDesconocida8((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC8[0],BYTES_DESC8[1]+1))));
        ddfd.setVarDesconocida9((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC9[0],BYTES_DESC9[1]+1))));
        ddfd.setVarDesconocida10((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC10[0],BYTES_DESC10[1]+1))));
        ddfd.setVarDesconocida11((int)(getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_DESC11[0],BYTES_DESC11[1]+1))));
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

    private SondaSetHistorico getSondaSetHistoricoFromValoresLeidos2(byte[] valoresByteDeUnPixel) {
        SondaSetHistorico ssh = new SondaSetHistorico();
        ssh.setEscala((int)(2.5*getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_ESCALA[0],BYTES_ESCALA[1]+1))));
        ssh.setExpander((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_EXPANDER[0],BYTES_EXPANDER[1]+1)));
        ssh.setFrecuencia((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_FRECUENCIA[0],BYTES_FRECUENCIA[1]+1)));
        ssh.setGanancia((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_GANANCIA[0],BYTES_GANANCIA[1]+1)));
        ssh.setLineaBlanca((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_LW[0],BYTES_LW[1]+1)));
        ssh.setShift((int)(2.5*getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_SHIFT[0],BYTES_SHIFT[1]+1))));
        ssh.setStc((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_STC[0],BYTES_STC[1]+1)));
        ssh.setUnidadDeEscala((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_UNIDAD_MEDIDA[0],BYTES_UNIDAD_MEDIDA[1]+1)));
        ssh.setVelPantalla((int)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_GS[0],BYTES_GS[1]+1)));
        return ssh;
    }
    
    private PuntoHistorico getPuntoHistoricoFromValoresLeidos(ArrayList valoresByteDeUnPixel) {
        PuntoHistorico ph = new PuntoHistorico();
        ph.setFechaYhora(armaDate(Integer.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_FECHA))), 
                Integer.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_HORA)))));
        ph.setLatitud(Sistema.getInstance().getLatEnGradosDecimalesFromString(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_LATITUD))));
        ph.setLongitud(Sistema.getInstance().getLonEnGradosDecimalesFromString(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_LONGITUD))));
        ph.setProfundidad((double)getLongFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_PROFUNDIDAD)));
        ph.setRumbo(Double.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_RUMBO))));
        ph.setVelocidad(Double.valueOf(getStringFromByteArray((byte[]) valoresByteDeUnPixel.get(NRO_COL_VELOCIDAD))));
        return ph;
    }

    private PuntoHistorico getPuntoHistoricoFromValoresLeidos2(byte[] valoresByteDeUnPixel) {
        PuntoHistorico ph = new PuntoHistorico();
        ph.setFechaYhora(armaDate(Integer.valueOf(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_FECHA[0],BYTES_FECHA[1]+1))), 
                Integer.valueOf(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_HORA[0],BYTES_HORA[1]+1)))));
        ph.setLatitud(Sistema.getInstance().getLatEnGradosDecimalesFromString(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_LATITUD[0],BYTES_LATITUD[1]+1))));
        ph.setLongitud(Sistema.getInstance().getLonEnGradosDecimalesFromString(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_LONGITUD[0],BYTES_LONGITUD[1]+1))));
        ph.setProfundidad((double)getLongFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_PROFUNDIDAD[0],BYTES_PROFUNDIDAD[1]+1)));
        ph.setRumbo(Double.valueOf(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_RUMBO[0],BYTES_RUMBO[1]+1))));
        ph.setVelocidad(Double.valueOf(getStringFromByteArray(Arrays.copyOfRange(valoresByteDeUnPixel, BYTES_VELOCIDAD[0],BYTES_VELOCIDAD[1]+1))));
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
                int i = 0;//el valor leido es demaciado grande y no puede ser convertido
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

//leer cada pixel por bloques de (confirmadisimo!!! --->((((bloques de 184bytes!)))))/187 bytes---> 1px = 184b de DAT | 
//    antes de empezar hay 177nulos    //177920    / 456px ancho= 84088b    //          / 967px ancho = 178112b
    public boolean leerDatPorBloques(String rutaFileDat){
        boolean sePudo=false;
        String datFileNamee = rutaFileDat.toLowerCase().substring(rutaFileDat.lastIndexOf("\\")+1,rutaFileDat.length());
        UltimaImgProcesada.getInstance().setFileName(datFileNamee.replace(".dat", ".jpg"));
        UltimaImgProcesada.getInstance().setFechaYhora(Calendar.getInstance().getTime());
        UltimaImgProcesada.getInstance().setProgresoProcesamiento(1);//inicio el analisis del DAT y empiezo a descomprimir
	int tamanioBloque=184;
        ArrayList valoresPorPixel = new ArrayList();
        ArrayList pixelesConErrorAlGuardarValores = new ArrayList();
        ArrayList<modelo.dataManager.SondaSetHistorico> sondaSets = new ArrayList();
        ArrayList<modelo.dataManager.PuntoHistorico> puntos = new ArrayList();
        ArrayList<modelo.dataManager.DatosDesconocidosFromDat> datosDesconocidos = new ArrayList();
        try{
            if (getUltimoDatLeido()== null || (!getUltimoDatLeido().toLowerCase().equals(datFileNamee))){
                if (decompressData(rutaFileDat)){
                    UltimaImgProcesada.getInstance().setProgresoProcesamiento(2);//ya descomprimí, ahora leo el archivo por bloques
                    ArrayList<byte[]> parametrosByteDeUnPixel = new ArrayList<byte[]>();
                    setIndiceDat(184);//antes 178
                    while ((getIndiceDat()+tamanioBloque)<getDatosDescomprimidos().length){
			byte [] unBloque = Arrays.copyOfRange(getDatosDescomprimidos(), getIndiceDat(), (getIndiceDat()+tamanioBloque));
                        try{
                            sondaSets.add(getSondaSetHistoricoFromValoresLeidos2(unBloque));
                            puntos.add(getPuntoHistoricoFromValoresLeidos2(unBloque));
                            datosDesconocidos.add(getDatosDesconocidosFromValoresLeidos2(unBloque));
                        }catch(Exception e){
                            pixelesConErrorAlGuardarValores.add(sondaSets.size()-1);
                        }
                        setIndiceDat(getIndiceDat()+tamanioBloque);
                    }
                    valoresPorPixel.add(sondaSets);
                    valoresPorPixel.add(puntos);
                    valoresPorPixel.add(datosDesconocidos);
                    sePudo=true;
                }else{
                    Logueador.getInstance().agregaAlLog("leerDatPorBloques(): Error al descomprimir DAT");
                }
                if (pixelesConErrorAlGuardarValores.size()>0){
                    Logueador.getInstance().agregaAlLog("Error al intentar guardar datos de parametros leidos de "+(pixelesConErrorAlGuardarValores.size())+" pixeles");
                    sePudo=false;//error al leer el DAT
                }else{
                    setUltimoDatLeido(datFileNamee);
                    setFechaYhoraUltimoDatLeido(Calendar.getInstance().getTime());
                    setDatosFromDat(valoresPorPixel);
                    if (sondaSets.size()>0){
                        SondaSetHistorico ultimoSsh = sondaSets.get(sondaSets.size()-1);
                        ultimoSsh.setUsadoDesde(puntos.get(0).getFechaYhora());//mentira
                        ultimoSsh.setUsadoHasta(puntos.get(sondaSets.size()-1).getFechaYhora());
                        actualizaSondaSet(ultimoSsh);
                    }
                }
            }else{
                sePudo=true;
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("leerDatPorBloques(): "+e.toString());
        }
        if (!sePudo){
           UltimaImgProcesada.getInstance().setProgresoProcesamiento(-2);//error al leer el DAT
           controllers.ControllerNavegacion.getInstance().errorGuiProcesamientoImgs();
        }else{
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(3);//fin: lectura DAT exitosa, comienza procesamiento de img
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
        int hora,minutos,segundos,dia,mes,anio;
        //extraigo el horario
        float cociente = horario / 10000;
        int resto =  horario % 10000;
        hora = (int) cociente;
        cociente = resto / 100;
        resto = resto % 100;
        minutos = (int) cociente;
        segundos = resto;
        //extraigo la fecha
        cociente = fecha / 10000;
        resto =  fecha % 10000;
        dia = (int) cociente;
        cociente = resto / 100;
        resto = resto % 100;
        mes = (int) cociente;
        anio = resto;
        Calendar calendario = Calendar.getInstance();
        calendario.set(2000+anio, mes-1, dia, hora, minutos, segundos);        
        return calendario.getTime();
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
        try{ 
            String fileNameDelDat = jpgFileName.toLowerCase().substring(jpgFileName.lastIndexOf("\\")+1,jpgFileName.length());
            fileNameDelDat = fileNameDelDat.toLowerCase().replace(".jpg", ".dat");
            if (!DATatlantis.getInstance().getUltimoDatLeido().toLowerCase().equals(fileNameDelDat)){
                if (DATatlantis.getInstance().leerDatPorBloques(fileNameDelDat)){
                    datosPixelX.add(((ArrayList<modelo.dataManager.SondaSetHistorico>)DATatlantis.getInstance().getDatosFromDat().get(0)).get(nroPixelX));
                    datosPixelX.add(((ArrayList<modelo.dataManager.PuntoHistorico>)DATatlantis.getInstance().getDatosFromDat().get(1)).get(nroPixelX));
                    datosPixelX.add(((ArrayList<modelo.dataManager.DatosDesconocidosFromDat>)DATatlantis.getInstance().getDatosFromDat().get(2)).get(nroPixelX));
                }
            }else{
                datosPixelX.add(((ArrayList<modelo.dataManager.SondaSetHistorico>)DATatlantis.getInstance().getDatosFromDat().get(0)).get(nroPixelX));
                datosPixelX.add(((ArrayList<modelo.dataManager.PuntoHistorico>)DATatlantis.getInstance().getDatosFromDat().get(1)).get(nroPixelX));
                datosPixelX.add(((ArrayList<modelo.dataManager.DatosDesconocidosFromDat>)DATatlantis.getInstance().getDatosFromDat().get(2)).get(nroPixelX));
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("getDatosFromPixel(): "+e.toString());
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