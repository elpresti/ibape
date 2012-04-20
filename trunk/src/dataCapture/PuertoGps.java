/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataCapture;

/**
 *
 * @author Sebastian
 */
public class PuertoGps{

    static PuertoGps unicaInstancia;    

    private PuertoGps(){}

    public static PuertoGps getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new PuertoGps();
       return unicaInstancia;
    }

    public boolean poneDatosEnPunto(){
        boolean sePudo=false;

        return sePudo;
    }
}

