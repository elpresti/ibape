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
    
    
}