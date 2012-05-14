/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class FacadeGui {
    static FacadeGui unicaInstancia;
    private AdministraCampanias adminCamp;

    private FacadeGui(){        
    }    
    
    public static FacadeGui getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new FacadeGui();          
       }
       return unicaInstancia;
    }
    
    /**
     * @return the adminCamp
     */
    public AdministraCampanias getAdminCamp() {
        return adminCamp;
    }

    /**
     * @param adminCamp the adminCamp to set
     */
    public void setAdminCamp(AdministraCampanias adminCamp) {
        this.adminCamp = adminCamp;
    }

   
}