/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

/**
 *
 * @author Sebastian
 */
public class BrokerCampania extends BrokerPpal{
    static BrokerCampania unicaInstancia;
    
    public static BrokerCampania getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new BrokerCampania();          
       }       
       return unicaInstancia;
    }

    public boolean vaciaTablaCampanias() {
        boolean sePudo=false;
        System.out.println("vaciaTablaCampanias() Not yet implemented");
        return sePudo;
    }

    public boolean setTodosVerificados(boolean b) {
        boolean sePudo=false;
        System.out.println("setTodosVerificados() Not yet implemented");
        return sePudo;        
    }

    public boolean setVerificado(String folderName) {
        //busca en la tabla campanias la primer entrada que coincidan con este folderName y las marca como Verificado=true
        // si encuentra mas de una entrada las demas las ignora ya que quedaran con verificado=false y luego seran eliminadas
        boolean sePudo=false;
        System.out.println("setVerificado() Not yet implemented");
        return sePudo; 
    }
    
    public modelo.dataManager.Campania getCampaniaFromDb(int id){
        modelo.dataManager.Campania campania = null;
        //buscar en la base la campania.id que coincida con el id pasado por parametro
        return campania;
    }

    public boolean borraNoVerificados() {
        boolean sePudo=false;
        System.out.println("borraNoVerificados() Not yet implemented");
        return sePudo;
    }

    public int[] obtieneTodosLosIds() {        
        System.out.println("obtieneTodosLosIds() Not yet implemented");
        //consulta que me devuelve todos los Ids de las campanias que hay en TablaCampanias
        // int[] ids = new int[resulset.numberOfRows()];
        int[] ids = null;
        return ids;
    }
    
}