package modelo.dataCapture;

/**
 *
 * @author Sebastian
 */
public class Sonda extends PuertoSerie{
    static Sonda unicaInstancia;    

    private Sonda(){
        setDispositivo("Sonda");
    }

    public static Sonda getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new Sonda();
       return unicaInstancia;
    }        
   
}