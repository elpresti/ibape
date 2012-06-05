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
        
    }
    
    public static Csv getInstance(){
        if (unicaInstancia == null){
            unicaInstancia = new Csv();
        }
        return unicaInstancia;
    }

    public ArrayList<SondaSetHistorico> leerCsv(String rutaCsv){
        ArrayList<SondaSetHistorico> sshDistintos = new ArrayList();
        try {            
            CsvReader sondaSets = new CsvReader(rutaCsv);
            try {                 
                 SondaSetHistorico ssAnterior = new SondaSetHistorico();
                 int i=0;
                 while (sondaSets.readRecord()) {
                    if (Integer.parseInt(sondaSets.get(NRO_COL_FRECUENCIA).trim()) != ssAnterior.getFrecuencia() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_GANANCIA).trim()) != ssAnterior.getGanancia() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_STC).trim()) != ssAnterior.getStc() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_LW).trim()) != ssAnterior.getLineaBlanca() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_GS).trim()) != ssAnterior.getVelPantalla() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_ESCALA).trim()) != ssAnterior.getEscala() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_SHIFT).trim()) != ssAnterior.getShift() || 
                        Integer.parseInt(sondaSets.get(NRO_COL_UNIDAD).trim()) != ssAnterior.getUnidadDeEscala()
                            ){                        
                        ssAnterior.setFrecuencia(Integer.parseInt(sondaSets.get(NRO_COL_FRECUENCIA).trim()));
                        ssAnterior.setGanancia(Integer.parseInt(sondaSets.get(NRO_COL_GANANCIA).trim()));
                        ssAnterior.setStc(Integer.parseInt(sondaSets.get(NRO_COL_STC).trim()));
                        ssAnterior.setLineaBlanca(Integer.parseInt(sondaSets.get(NRO_COL_LW).trim()));
                        ssAnterior.setVelPantalla(Integer.parseInt(sondaSets.get(NRO_COL_GS).trim()));
                        ssAnterior.setEscala(Integer.parseInt(sondaSets.get(NRO_COL_ESCALA).trim()));
                        ssAnterior.setShift(Integer.parseInt(sondaSets.get(NRO_COL_SHIFT).trim()));
                        ssAnterior.setUnidadDeEscala(Integer.parseInt(sondaSets.get(NRO_COL_UNIDAD).trim()));
                        Date fechaYhora = armaDate(Integer.parseInt(sondaSets.get(NRO_COL_FECHA).trim()),Integer.parseInt(sondaSets.get(NRO_COL_HORA).trim()));
                        ssAnterior.setUsadoDesde(fechaYhora);
                        ssAnterior.setUsadoHasta(fechaYhora);
                        sshDistintos.add(ssAnterior);
                    }
                    else
                      { Date fechaYhora = armaDate(Integer.parseInt(sondaSets.get(NRO_COL_FECHA).trim()),Integer.parseInt(sondaSets.get(NRO_COL_HORA).trim()));
                        sshDistintos.get(sshDistintos.size()-1).setUsadoHasta(fechaYhora);
                      }
/*                        
                    String frecuencia = sondaSets.get(NRO_COL_FRECUENCIA);
                    String ganancia = sondaSets.get(NRO_COL_GANANCIA);
                    String stc = sondaSets.get(NRO_COL_STC);
                    String lw = sondaSets.get(NRO_COL_LW);
                    String gs = sondaSets.get(NRO_COL_GS);
                    String escala = sondaSets.get(NRO_COL_ESCALA);
                    String shift = sondaSets.get(NRO_COL_SHIFT);
                    String expander = sondaSets.get(NRO_COL_EXPANDER);
                    String unidad = sondaSets.get(NRO_COL_UNIDAD);
                    String hora = sondaSets.get(NRO_COL_HORA);
                    String fecha = sondaSets.get(NRO_COL_FECHA);
*/
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
    
    public static void main(String[] args){
        Csv csv = getInstance();
        ArrayList<SondaSetHistorico> sshDistintos = csv.leerCsv("Historico\\camp34\\TestValoresCsv.txt");
        //sePudo = sePudo;
    }
    
}
