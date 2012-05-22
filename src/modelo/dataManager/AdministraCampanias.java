/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Calendar;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class AdministraCampanias {
    static AdministraCampanias unicaInstancia;
    private Campania campaniaEnCurso;
    private ArrayList<Campania> campanias=new ArrayList();
    private String historicoFileName="historico.db";
    
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
            //borra el objeto con ID campAeliminar.getId() de la TablaCampanias
            persistencia.BrokerCampania.getInstance().deleteCampania(campAeliminar);
            //borra el objeto con ID campAeliminar.getId() del ArrayList campanias
            campanias.remove(getCampania(campAeliminar.getId()));
            sePudo=true;
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
                if (getCampanias().get(i).getFolderHistorico() != null && (getCampanias().get(i).getFolderHistorico().length()>0)){
                    if (!(verificaSiTieneHistorico(getCampanias().get(i).getId()))){
                        getCampanias().get(i).setFolderHistorico(null);
                    }
                }
            }
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
  
/*    
    public boolean leerCampaniasDelDisco(){  // <---- método posiblemente innecesario
        boolean sePudo=false;
        // metodo pendiente que lee las campanias del disco, transforma cada
        // una en un objeto de tipo Campania y la agrega al ArrayList "campanias"
        campanias.clear();
        try {
            //if que chequea si existe el directorio "Historico"
            File pathHistorico = new File("Historico");
            if (pathHistorico.exists()){               
                //tilda el atributo tieneHistorico=false de todas las entradas de TablaCampanias
                persistencia.BrokerCampania.getInstance().setTodosTienenHistorico(false);
                //obtiene las carpetas del directorio
                FileFilter fileFilter = new FileFilter() {
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                };
                File[] dirs = pathHistorico.listFiles(fileFilter);
                // por cada carpeta leida chequea que el nombre corresponda a alguna
                // entrada de TablaCampanias y la marca tieneHistorico=true
                for (int i=0;i<dirs.length;i++){
                    persistencia.BrokerCampania.getInstance().setTieneHistorico(dirs[i].getName());
                }
                // elimina de TablaCampanias todas las tieneHistorico=false;
                persistencia.BrokerCampania.getInstance().borraNoTienenHistorico();
                int[] idsCampania = persistencia.BrokerCampania.getInstance().obtieneTodosLosIds();
                for (int i=0;i<idsCampania.length;i++){
                    campanias.add(persistencia.BrokerCampania.getInstance().getCampaniaFromDb(idsCampania[i]));
                }
            }
            else
              { //si no existe termina y vacía la TablaCampanias
                persistencia.BrokerCampania.getInstance().vaciaTablaCampanias(); }
            //sePudo=true;
        }
        catch (Exception e ){
            Logueador.getInstance().agregaAlLog(e.toString());
        }                
        return sePudo;
    }
*/
    
    public boolean verificaSiTieneHistorico(int idCampania){
        boolean tieneHistorico=false;
        String folderHistorico = getCampania(idCampania).getFolderHistorico();
        File historico = new File("Historico\\" + folderHistorico + "\\" + getHistoricoFileName());
        if (historico.exists()) {
            tieneHistorico = true;
        }
        return tieneHistorico;
    }
    
    public boolean creaFileHistorico(int idCampania){
        boolean sePudo=false;
        try {
            modelo.dataManager.Campania campania = getCampania(idCampania);
            String folderHistorico = "camp"+campania.getId();
            File historico = new File("Historico\\"+folderHistorico+"\\"+getHistoricoFileName());
            historico.mkdirs();
            campania.setFolderHistorico(folderHistorico);
            persistencia.BrokerCampania.getInstance().updateCampania(campania);
            sePudo=true;
        }
        catch(Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
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
     * @return the historicoFileName
     */
    public String getHistoricoFileName() {
        return historicoFileName;
    }

    /**
     * @param historicoFileName the historicoFileName to set
     */
    public void setHistoricoFileName(String historicoFileName) {
        this.historicoFileName = historicoFileName;
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
