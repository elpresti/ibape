/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import gui.PanelBarraDeEstado;
import java.util.Calendar;
import org.apache.log4j.Logger;
/**
 *
 * @author Sebastian
 */
public class Logueador {
    private Logger log = Logger.getLogger("ConsolaUsuario");    
    static Logueador unicaInstancia;    
    
    private Logueador(){
        
    }
    
    public static Logueador getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new Logueador();          
       }
       return unicaInstancia;
    } 

    public void agregaAlLog(String txt){
        PanelBarraDeEstado.getInstance().mostrarMensaje(txt);
        Calendar calender = Calendar.getInstance();
        String fechaYhora= calender.get(Calendar.YEAR)+"/"+calender.get(Calendar.MONTH)+"/"+calender.get(Calendar.DAY_OF_MONTH)+" "+calender.get(Calendar.HOUR_OF_DAY)+":"+calender.get(Calendar.MINUTE)+":"+calender.get(Calendar.SECOND);
        String txtListo = fechaYhora + ": "+txt;
        log.info(txtListo);        
    }    
}
