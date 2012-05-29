/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;
import java.util.List;
/**
 *
 * @author Martin
 */
public class ManejadorAlertas {
    private static List<Alerta> alertas;

    /**
     * @return the alertas
     */
    public static List<Alerta> getAlertas() {
        return alertas;
    }
    
    public static List<Alerta> getAlertasActivas() {
        return alertas;
    }

    /**
     * @param aAlertas the alertas to set
     */
    public static void setAlertas(List<Alerta> aAlertas) {
        alertas = aAlertas;
    }
    
    public boolean agregarAlerta(Alerta a){
        return true;
    }
    public boolean eliminarAlerta(Alerta a){
        return true;
    }
    public boolean modificarAlerta(Alerta a){
        return true;
    }
    public static boolean estadoServicio(){
        return true;
    }
}
