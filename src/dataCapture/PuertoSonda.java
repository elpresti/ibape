/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataCapture;

/**
 *
 * @author Sebastian
 */
public class PuertoSonda{
    static PuertoSonda unicaInstancia;    

    private PuertoSonda(){}

    public static PuertoSonda getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new PuertoSonda();
       return unicaInstancia;
    }        
    
    public boolean poneDatosEnPunto(){
        boolean sePudo=false;
        
        return sePudo;
    }     
}