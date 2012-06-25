/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.alertas;
import controllers.ControllerAlertas;
import java.util.List;
import persistencia.Logueador;
/**
 *
 * @author Martin
 */
public class AdministraAlertas {
    private static List<Alerta> alertas;
    private static AdministraAlertas unicaInstancia;
    private static boolean estadoAlertas;

    /**
     * @return the alertas
     */
    
    private AdministraAlertas(){
        leerAlertasDeLaDB();     
    }
    
    public static AdministraAlertas getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new AdministraAlertas();          
       }       
       return unicaInstancia;
    }

    /**
     * @return the estadoAlertas
     */
    public static boolean isEstadoAlertas() {
        return estadoAlertas;
    }

    /**
     * @param aEstadoAlertas the estadoAlertas to set
     */
    public static void setEstadoAlertas(boolean aEstadoAlertas) {
        estadoAlertas = aEstadoAlertas;
    }
    
    public List<Alerta> getAlertas() {
        return alertas;
    }
    
    public List<Alerta> getAlertasActivas() {
        return alertas;
    }

    /**
     * @param aAlertas the alertas to set
     */
    public void setAlertas(List<Alerta> aAlertas) {
        alertas = aAlertas;
    }
    
    public boolean agregarAlerta(Alerta a){
        boolean sePudo=false;        
        try {
            if (persistencia.BrokerAlertas.getInstance().insertAlerta(a)){
                a.setId(persistencia.BrokerAlertas.getInstance().getIdUltimoInsert());
                alertas.add(a);
                sePudo = true;
            }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean eliminarAlerta(Alerta alertaAeliminar){
               boolean sePudo=false;
        try {
         
              //borra el objeto con ID alertaAeliminar.getId() de la Tabla Alertas
              boolean borraAlertaDeTabla = persistencia.BrokerAlertas.getInstance().deleteAlerta(alertaAeliminar);            
              //borra el objeto con ID campAeliminar.getId() del ArrayList campanias (osea, de memoria)
              alertas.remove(alertaAeliminar);

              Logueador.getInstance().agregaAlLog("Se borró de la Base de Datos la alerta especificada");
              sePudo=borraAlertaDeTabla;
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    
    public boolean modificarAlerta(Alerta a){
        boolean sePudo=false;        
        try {           
            persistencia.BrokerAlertas.getInstance().updateAlerta(a);
            alertas.remove(getAlerta(a.getId()));
            alertas.add(a);                        
            sePudo=true;
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    

        public boolean leerAlertasDeLaDB() {
        boolean sePudo=false;
        try {
            setAlertas(persistencia.BrokerAlertas.getInstance().getAlertasFromDB());
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    public Alerta getAlerta(int id){
        Alerta alerta = null;
        //busca el objeto alerta con el id solicitado; si no lo encuentra devuelve null
        int i = 0;
        while ((i<getAlertas().size()) && (alerta == null)){
            if (getAlertas().get(i).getId() == id){
                alerta=getAlertas().get(i);
            }
            i++;
        }
        return alerta;
    }    
        
   /*   
    public static void main(String args[]){
        AdministraAlertas alertas=new AdministraAlertas();
    }
   */
}
