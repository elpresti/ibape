/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class AdministraCampanias {
    static AdministraCampanias unicaInstancia;
    private Campania campaniaEnCurso;
    private ArrayList<Campania> campanias;
    
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
        }
        catch (Exception e)
            { Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean eliminarCampania(Campania campAeliminar){
        boolean sePudo=false;
        // --------- método pendiente -----------
        return sePudo;
    }
    
    public boolean finalizarCampaniaEnCurso(){
        boolean sePudo=false;
        // --------- método pendiente -----------
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

    public boolean leerCampaniasDelDisco(){
        boolean sePudo=false;
        // metodo pendiente que lee las campanias del disco, transforma cada
        // una en un objeto de tipo Campania y la agrega al ArrayList "campanias"
        campanias.clear();
        try {
            //if que chequea si existe el directorio "Historico"
            File pathHistorico = new File("Historico");
            if (pathHistorico.exists()){               
                //tilda el atributo verificado=false de todas las entradas de TablaCampanias
                persistencia.BrokerCampania.getInstance().setTodosVerificados(false);
                //obtiene las carpetas del directorio
                FileFilter fileFilter = new FileFilter() {
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                };
                File[] dirs = pathHistorico.listFiles(fileFilter);
                // por cada carpeta leida chequea que el nombre corresponda a alguna
                // entrada de TablaCampanias y la marca verificado=true
                for (int i=0;i<dirs.length;i++){
                    persistencia.BrokerCampania.getInstance().setVerificado(dirs[i].getName());
                }
                // elimina de TablaCampanias todas las verificado=false;
                persistencia.BrokerCampania.getInstance().borraNoVerificados();
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
    
}
