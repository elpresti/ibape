/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Calendar;
import persistencia.BrokerHistorico;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class AdministraCampanias {
    static AdministraCampanias unicaInstancia;
    private Campania campaniaEnCurso;
    private ArrayList<Campania> campanias=new ArrayList();
    
    private AdministraCampanias(){
    }
    
    
    public static AdministraCampanias getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new AdministraCampanias();          
       }       
       return unicaInstancia;
    }     
    
    public boolean agregarCampania(Campania nuevaCamp){
        boolean sePudo=false;        
        try {
            //if que chequea si existe el directorio "Historico", si no existe lo crea
            //crea la entrada en la TablaCampanias y obtiene del campo folderName el nombre autogenerado, si no puede termina con exception
            //crea un directorio con el nombre de folderName, si ya existe lo renombra agregandole 
            //algo y actualiza su folderName en la tabla, si no puede termina con exception y borra su entrada en TablaCampanias
            //crea la dbHistorico.db en este directorio para alojar el historico, 
                //si no puede termina con exception, borra su directorio creado y luego su entrada en TablaCampanias
            //agrega el objeto nuevaCamp al ArrayList de campanias
            if (persistencia.BrokerCampania.getInstance().insertCampania(nuevaCamp)){
                nuevaCamp.setId(persistencia.BrokerCampania.getInstance().getIdUltimoInsert());
                campanias.add(nuevaCamp);
                setCampaniaEnCurso(nuevaCamp);
                sePudo = true;
            }
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean modificarCampania(Campania campModificada){
        boolean sePudo=false;        
        try {
            //busca el objeto en TablaCampanias campModificada.getId() y le actualiza sus valores, si no puede termina con exception
            //busca el objeto en el ArrayList campanias segun campModificada.getId(), si no encuentra termina con exception
            //si encuentra le actualiza sus valores, sino exception            
            persistencia.BrokerCampania.getInstance().updateCampania(campModificada);
            //la buscamos en el ArrayList de campanias y la borramos para agregar la modificada
            campanias.remove(getCampania(campModificada.getId()));
            campanias.add(campModificada);                        
            sePudo=true;
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean eliminarCampania(Campania campAeliminar){
        boolean sePudo=false;
        try {
              //si la campAeliminar tiene historico, lo borra
              boolean borraHistorico = true;
              if (AdministraCampanias.getInstance().verificaSiTieneHistorico(campAeliminar.getId())){

                 if (persistencia.BrokerHistoricoPunto.getInstance().borraHistorico(campAeliminar.getId())){
                    Logueador.getInstance().agregaAlLog("Se borró el Historico de la Campaña especificada");
                 }
                 else { 
                     Logueador.getInstance().agregaAlLog("No se pudo borrar el historico de la Campaña especificada"); 
                     borraHistorico = false;
                 }
                 
              }
              //borra el objeto con ID campAeliminar.getId() de la TablaCampanias
              boolean borraCampDeTabla = persistencia.BrokerCampania.getInstance().deleteCampania(campAeliminar);            
              //borra el objeto con ID campAeliminar.getId() del ArrayList campanias (osea, de memoria)
              campanias.remove(campAeliminar);

              Logueador.getInstance().agregaAlLog("Se borró de la Base de Datos la campaña especificada");
              sePudo=borraCampDeTabla && borraHistorico;
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean finalizarCampaniaEnCurso(){
        boolean sePudo=false;
        try {
            //traigo de memoria el objeto que representa la campañas en curso
            Campania campEnCurso = getCampaniaEnCurso();
            //lo quito de la lista de campañas en memoria
            campanias.remove(campEnCurso);
            //le grabo la fecha de finalización actual
            campEnCurso.setFechaFin(Calendar.getInstance().getTime());
            //lo actualizo en la base de datos
            persistencia.BrokerCampania.getInstance().updateCampania(campEnCurso);
            //lo vuelvo a agregar actualizado a la lista de campañas en memoria
            campanias.add(campEnCurso);
            //dejo vacía la campaña en curso
            setCampaniaEnCurso(null);
            sePudo=true;
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    /**
     * @return the campanias
     */
    public ArrayList<Campania> getCampanias() {
        return campanias;
    }

    /**
     * @param campanias the campanias to set
     */
    public void setCampanias(ArrayList<Campania> campanias) {
        this.campanias = campanias;
    }

    public boolean leerCampaniasDeLaDB() {
        boolean sePudo=false;
        try {
            setCampanias(persistencia.BrokerCampania.getInstance().getCampaniasFromDB());
            //verificamos si está en disco el historico de las campañas que dicen tener historico            
            for (int i=0;i<getCampanias().size();i++){
                if (!(verificaSiTieneHistorico(getCampanias().get(i).getId()))){
                    getCampanias().get(i).setFolderHistorico(null);
                }
            }
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public boolean verificaSiTieneHistorico(int idCampania){
        boolean tieneHistorico=false;
        Campania campania = getCampania(idCampania);
        if (campania.getFolderHistorico() != null && (campania.getFolderHistorico().length()>0)){
            String folderHistorico = campania.getFolderHistorico();
            File historico = new File(persistencia.BrokerHistoricoPunto.getInstance().getFolderNameHistorico()+"\\" + folderHistorico + "\\" + persistencia.BrokerHistoricoPunto.getInstance().getDbFileName());
            if (historico.exists()) {
                tieneHistorico = true;
            }            
        }
        return tieneHistorico;
    }
        
    public Campania getCampania(int id){
        Campania campania = null;
        //busca el objeto campania con el id solicitado; si no lo encuentra devuelve null
        int i = 0;
        while ((i<getCampanias().size()) && (campania == null)){
            if (getCampanias().get(i).getId() == id){
                campania=getCampanias().get(i);
            }
            i++;
        }
        return campania;
    }

    /**
     * @return the campaniaEnCurso
     */
    public Campania getCampaniaEnCurso() {
        return campaniaEnCurso;
    }

    /**
     * @param campaniaEnCurso the campaniaEnCurso to set
     */
    public void setCampaniaEnCurso(Campania campaniaEnCurso) {
        this.campaniaEnCurso = campaniaEnCurso;
    }
    
}
