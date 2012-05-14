/*

package vista;

import java.util.Observable;
import java.util.Observer;

public class VentanaObservador  implements Observer {     
  private dataCapture.PuertoSerie spe1 = null;    
  private dataCapture.PuertoSerie spe2 = null;    
  private vista.VentanaPpal vp;
  public VentanaObservador(dataCapture.PuertoSerie spe, dataCapture.PuertoSerie spe2 )
    {
        vp = new vista.VentanaPpal();
        vp.setVisible(true);
        this.spe1 = spe;  
        this.spe2 = spe2;
    }
    @Override
 public void update( Observable obs,Object obj ) 
    {   dataManager.Punto punto=spe1.getPunto();
        if( obs == punto )           
        { 
          vp.setCajaTexto1(spe1.getTxtCuadro());
          vp.setVarLatitud(String.valueOf( punto.getLatitud() ) +" "+punto.getLatHemisf());
          vp.setVarLongitud(String.valueOf(punto.getLongitud()) +" "+punto.getLonHemisf());
          vp.setVarProf(String.valueOf(punto.getProfundidad()));
          vp.setVarVelocidad(String.valueOf(punto.getVelocidad()));
          if (spe2!= null) {
            vp.setCajaTexto2(spe2.getTxtCuadro());
          }
          else
            { if (!(vp.getCajaTexto2().getText().equals(" ----- Puerto 2 sin configurar -----")))
                { vp.setCajaTexto2(" ----- Puerto 2 sin configurar -----"); }                     
            } 
        }                 
    }     
}

*/