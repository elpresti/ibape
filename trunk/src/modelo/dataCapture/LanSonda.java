/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataCapture;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class LanSonda {
    static LanSonda unicaInstancia;
   
    
    
    
    public static LanSonda getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new LanSonda();
       return unicaInstancia;
    }           
    
    public boolean verificaConexionAequipo(String rutaSondaImgs){        
        boolean sePudo=false;
        //extrae la IP de la rutaSondaImgs e intenta establecer una conexion con el equipo remoto, si no hay exception --> salida=true;        
        return sePudo;
    }       

    public boolean verificaAccesoAcarpetaRemota(String rutaSondaImgs){
        boolean sePudo=false;
        //intenta listar archivos de la rutaIP especificada, si no hay exception --> salida=true;       
        return sePudo;
    }    

    public ArrayList<String> hayArchivosNuevos(){
        ArrayList<String> archivosNuevos=new ArrayList<String>();
        //verifica si existen archivos nuevos, si hay devuelve sus filename en el vector, sino devuelve el vector con archivosNuevos.size()=0
        return archivosNuevos;
    }
    
}