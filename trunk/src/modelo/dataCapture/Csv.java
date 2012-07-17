/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dataManager.SondaSet;
import modelo.dataManager.SondaSetHistorico;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class Csv {
    private static Csv unicaInstancia;
    private String ultimoCsvLeido;
    private String csvFileName;
    private static final byte NRO_COL_FRECUENCIA=0;
    private static final byte NRO_COL_GANANCIA=1;
    private static final byte NRO_COL_STC=2;
    private static final byte NRO_COL_LW=3;
    private static final byte NRO_COL_GS=4;
    private static final byte NRO_COL_ESCALA=5;
    private static final byte NRO_COL_SHIFT=6;
    private static final byte NRO_COL_EXPANDER=7;
    private static final byte NRO_COL_UNIDAD=8;
    //private static final byte NRO_COL_UNIDAD_MEDIDA=9;
    private static final byte NRO_COL_HORA=9;
    private static final byte NRO_COL_LATITUD=10;
    private static final byte NRO_COL_EO=11;
    private static final byte NRO_COL_LONGITUD=12;
    private static final byte NRO_COL_NS=13;
    private static final byte NRO_COL_VELOCIDAD=14;
    private static final byte NRO_COL_RUMBO=15;
    private static final byte NRO_COL_FECHA=16;
    private static final byte NRO_COL_VELOCIDADPROM=17;
    private static final byte NRO_COL_PROFUNDIDAD=18;
    private static final byte NRO_COL_TEMPERATURA=19;    
    
    private Csv(){
        inicializador();
    }
    
    public static Csv getInstance(){
        if (unicaInstancia == null){
            unicaInstancia = new Csv();
        }
        return unicaInstancia;
    }

    public ArrayList<SondaSetHistorico> getSondaSetsFromCsv(String rutaCsv){
        ArrayList<SondaSetHistorico> sshDistintos = new ArrayList();
        try {            
            CsvReader sondaSets = new CsvReader(rutaCsv);
            try {                 
                 SondaSetHistorico ssAnterior = new SondaSetHistorico();
                 int i=0;
                 int frecuenciaLeida;
                 int gananciaLeida;
                 int stcLeido;
                 int lwLeido;
                 int gsLeido;
                 int escalaLeida;
                 int expanderLeido;
                 int shiftLeido;
                 int unidadLeida;
                 Date fechaYhoraLeida;
                 while (sondaSets.readRecord()) {
                    if (sondaSets.getColumnCount()>1) {
                        frecuenciaLeida = Integer.parseInt(sondaSets.get(NRO_COL_FRECUENCIA).trim());
                        gananciaLeida = Integer.parseInt(sondaSets.get(NRO_COL_GANANCIA).trim());
                        stcLeido = Integer.parseInt(sondaSets.get(NRO_COL_STC).trim());
                        lwLeido = Integer.parseInt(sondaSets.get(NRO_COL_LW).trim());
                        gsLeido = Integer.parseInt(sondaSets.get(NRO_COL_GS).trim());
                        escalaLeida = Integer.parseInt(sondaSets.get(NRO_COL_ESCALA).trim());
                        expanderLeido = Integer.parseInt(sondaSets.get(NRO_COL_EXPANDER).trim());
                        shiftLeido = Integer.parseInt(sondaSets.get(NRO_COL_SHIFT).trim());
                        unidadLeida = Integer.parseInt(sondaSets.get(NRO_COL_UNIDAD).trim());
                        fechaYhoraLeida = armaDate(Integer.parseInt(sondaSets.get(NRO_COL_FECHA).trim()),
                                    Integer.parseInt(sondaSets.get(NRO_COL_HORA).trim()));                        
                        if (frecuenciaLeida != ssAnterior.getFrecuencia() || 
                            gananciaLeida != ssAnterior.getGanancia() || 
                            stcLeido != ssAnterior.getStc() || 
                            lwLeido != ssAnterior.getLineaBlanca() || 
                            gsLeido != ssAnterior.getVelPantalla() || 
                            escalaLeida != ssAnterior.getEscala() || 
                            expanderLeido != ssAnterior.getExpander() ||                                 
                            shiftLeido != ssAnterior.getShift() || 
                            unidadLeida != ssAnterior.getUnidadDeEscala()
                                ){  
                            SondaSetHistorico unSondaSet = new SondaSetHistorico();
                            unSondaSet.setFrecuencia(frecuenciaLeida);
                            unSondaSet.setGanancia(gananciaLeida);
                            unSondaSet.setStc(stcLeido);
                            unSondaSet.setLineaBlanca(lwLeido);
                            unSondaSet.setVelPantalla(gsLeido);
                            unSondaSet.setEscala(escalaLeida);
                            unSondaSet.setShift(shiftLeido);
                            unSondaSet.setExpander(expanderLeido);
                            unSondaSet.setUnidadDeEscala(unidadLeida);
                            unSondaSet.setUsadoDesde(fechaYhoraLeida);
                            unSondaSet.setUsadoHasta(fechaYhoraLeida);
                            sshDistintos.add(unSondaSet);
                            ssAnterior = unSondaSet;
                        }
                        else
                        {   sshDistintos.get(sshDistintos.size()-1).setUsadoHasta(fechaYhoraLeida);      }
                    }
                    i++;
                 }
                 sondaSets.close();
            } catch (IOException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }                        
        } catch (FileNotFoundException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sshDistintos;
    }
    
    /**
     * @return the ultimoCsvLeido
     */
    public String getUltimoCsvLeido() {
        return ultimoCsvLeido;
    }

    /**
     * @param ultimoCsvLeido the ultimoCsvLeido to set
     */
    public void setUltimoCsvLeido(String ultimoCsvLeido) {
        this.ultimoCsvLeido = ultimoCsvLeido;
    }

    private Date armaDate(int fecha, int horario) {
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

    
/*    
    public static void main(String[] args){        
        ArrayList<SondaSetHistorico> sshDistintos = Csv.getInstance().getSondaSetsFromCsv("Historico\\camp34\\TestValoresCsv.txt");
        //sePudo = sePudo;
    }
*/

    /**
     * @return the csvFileName
     */
    public String getCsvFileName() {
        return csvFileName;
    }

    /**
     * @param csvFileName the csvFileName to set
     */
    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    private void inicializador() {
        setCsvFileName("valores.txt");
    }
    
    public DatosPixelX getDatosDeUnPixelX(String rutaJpg,int pixelX){
        //el procesador de imagenes le pasa a un método de la clase CSV los siguientes parametros:
            // su fileName.jpg
            // el Integer del pixel x del cual quiere saber la fecha y hora
        // el método devuelve un objeto con todos los datos que hay en el pixel x solicitado, o null si no lo encuentra
        DatosPixelX datosPixelX = null;
        try{            
            String rutaCsv = getCsvFromJpg(rutaJpg);
            if (rutaCsv != null){
                CsvReader docCsv = new CsvReader(rutaCsv);
                //agregar las contantes de columna de las columnas que faltan
                //leer del CSV el X solicitado
                boolean encontroFila = false;
                boolean pudoLeerFila = docCsv.readRecord();
                while ( (pudoLeerFila) && (!encontroFila)){
                    if (docCsv.getCurrentRecord() == pixelX){
                        encontroFila = true;
                        docCsv.readRecord();
                    }
                    else
                      { pudoLeerFila = docCsv.skipLine(); }
                }
                if (docCsv.getColumnCount()>1) {
                    //crear un objeto de tipo DatosPixelX                
                    datosPixelX = new DatosPixelX();//creo el objeto
                    //cargar esos datos en datosPixelX
                    datosPixelX.setEo(docCsv.get(NRO_COL_EO).trim().charAt(0));
                    datosPixelX.setEscala(Integer.parseInt(docCsv.get(NRO_COL_ESCALA).trim()));
                    datosPixelX.setExpander(Integer.parseInt(docCsv.get(NRO_COL_EXPANDER).trim()));
                    datosPixelX.setFrecuencia(Integer.parseInt(docCsv.get(NRO_COL_FRECUENCIA).trim()));
                    datosPixelX.setGanacia(Integer.parseInt(docCsv.get(NRO_COL_GANANCIA).trim()));
                    datosPixelX.setGs(Integer.parseInt(docCsv.get(NRO_COL_GS).trim()));
                    datosPixelX.setLw(Integer.parseInt(docCsv.get(NRO_COL_LW).trim()));
                    datosPixelX.setNs(docCsv.get(NRO_COL_NS).trim().charAt(0));
                    datosPixelX.setProfundidad(Double.parseDouble(docCsv.get(NRO_COL_PROFUNDIDAD).trim()));
                    datosPixelX.setRumbo(Double.parseDouble(docCsv.get(NRO_COL_RUMBO).trim()));
                    datosPixelX.setShift(Integer.parseInt(docCsv.get(NRO_COL_SHIFT).trim()));
                    datosPixelX.setStc(Integer.parseInt(docCsv.get(NRO_COL_STC).trim()));
                    datosPixelX.setTempAgua(Double.parseDouble(docCsv.get(NRO_COL_TEMPERATURA).trim()));
                    datosPixelX.setUnidad(Integer.parseInt(docCsv.get(NRO_COL_UNIDAD).trim()));
                    datosPixelX.setVelocidad(Double.parseDouble(docCsv.get(NRO_COL_VELOCIDAD).trim()));
                    datosPixelX.setVelocidadProm(Double.parseDouble(docCsv.get(NRO_COL_VELOCIDADPROM).trim()));
                    datosPixelX.setFechaYhora(                    
                                    armaDate(Integer.parseInt(docCsv.get(NRO_COL_FECHA).trim()),
                                    Integer.parseInt(docCsv.get(NRO_COL_HORA).trim()))
                            );
                    String latitud = docCsv.get(NRO_COL_LATITUD).trim();
                    if (latitud.length()>0){
                        datosPixelX.setLatitud(Double.parseDouble(latitud.substring(0, latitud.length()-1)));
                    }
                    String longitud = docCsv.get(NRO_COL_LONGITUD).trim();
                    if (longitud.length()>0){
                        datosPixelX.setLongitud(Double.parseDouble(longitud.substring(0, longitud.length()-1)));
                    }           
                }                
            }            
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return datosPixelX;
    }

    private String getCsvFromJpg(String rutaJpg) {
        String rutaAcsv= null;
        try{
            String idJpg = getIdFromFileName(rutaJpg);
            if (creaCarpetaTmpYcopiaArchivos(idJpg)){
                if (disparaEjecucionConversor(idJpg)){
                    if (modelo.dataCapture.LanSonda.getInstance().getCarpetaHistoricoLocal() != null){
                        //poner en rutaAcsv la ruta a valores.txt
                        rutaAcsv = modelo.dataCapture.LanSonda.getInstance().getCarpetaHistoricoLocal();
                        rutaAcsv += "\\"+idJpg;                        
                        rutaAcsv += "\\"+getCsvFileName();
                    }
                }
            }
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return rutaAcsv;
    }

    private boolean creaCarpetaTmpYcopiaArchivos(String idJpg) {
        boolean sePudo=false;
        //crear una carpeta temporal con el ID del JPG recibido dentro
        //poner en esta carpeta el EXE del conversor DATaCSV,
        //poner el DAT que corresponda al JPG recivido
        return sePudo;
    }

    private boolean disparaEjecucionConversor(String idJpg) {
        boolean sePudo=false;
        //ejecutar el conversor pasandole el ID del JPG
        //esperar que genere valores.txt y corroborar que tenga contenido (ej: file.size()>50kb)
        return sePudo;
    }

    private String getIdFromFileName(String rutaJpg) {
        // obtengo el ID del filename del JPG
        String idJpg = rutaJpg.substring(rutaJpg.indexOf("-")+1); // le saco el primer guion
        idJpg = idJpg.substring(0, rutaJpg.indexOf("-")); //obtengo sus primeros 4 digitos q representan el ID
        return idJpg;
    }

}