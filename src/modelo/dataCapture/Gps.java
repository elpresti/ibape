package modelo.dataCapture;

/**
 *
 * @author Sebastian
 */
public class Gps extends PuertoSerie{

    static Gps unicaInstancia;    

    private Gps(){
        setDispositivo("Gps");
    }

    public static Gps getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new Gps();
       return unicaInstancia;
    }

}