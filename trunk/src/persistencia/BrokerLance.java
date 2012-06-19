/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

/**
 *
 * @author Necrophagist
 */
public class BrokerLance extends BrokerPpal{

    static BrokerLance unicaInstancia;

    public static BrokerLance getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new BrokerLance();
        }
        return unicaInstancia;
    }

    public boolean vaciaTabla() {
        boolean sePudo = false;
        System.out.println("TRUNCATE TABLE Lances");
        return sePudo;
    }
    
    
    
}
