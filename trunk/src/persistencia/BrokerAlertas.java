/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import modelo.alertas.Condicion;
import persistencia.BrokerPpal;

/**
 *
 * @author Martin
 */
public class BrokerAlertas extends BrokerPpal{
    static BrokerAlertas unicaInstancia;
  
    /*
       public static void main(String args[]){
        BrokerPpal brokerPpal=BrokerAlertas.getInstance();
    }
      
     */
    
    public static BrokerAlertas getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new BrokerAlertas();          
       }       
       return unicaInstancia;
    }
    
   public boolean insertAlerta(modelo.alertas.Alerta alerta){
        boolean sePudo = false;
        int idAlerta=0;
        int idCondicion=0;
        String sqlQuery="";
        ResultSet rs;
        try {                                
            String titulo=null;
            if (alerta.getTitulo()!=null) {
                titulo = ""+alerta.getTitulo()+"";
            }
            String mensaje="";
            if (alerta.getMensaje()!=null) {
                mensaje = ""+alerta.getMensaje()+"";
            }  
            int flagsAcciones=alerta.getFlagsAcciones();
            String estado = String.valueOf(alerta.isEstado());
            if(alerta.getCondiciones()!=null){
                        sqlQuery = "INSERT INTO Alertas"
                        + "(estado,titulo,mensaje,flagsAcciones)"
                        + "VALUES"
                        +"("+estado+","+titulo+","+mensaje+","+flagsAcciones+")";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
            }
                //Ahora obtengo el id de la alerta insertada para insertar en la tabla alertasXcondiciones
                if (sePudo){
                sqlQuery = "SELECT max(id) from alertas";
                System.out.println("Select: "+sqlQuery);
                rs= getStatement().executeQuery(sqlQuery);
                if ( rs != null) {
                    idAlerta=rs.getInt("id");
                    sePudo = true;
                }else{sePudo=false;}
                //Inserto condiciones en Tabla de Condiciones
               boolean ok=true; 
               for (Condicion c:alerta.getCondiciones()){
                   if (c.getId()==0){
                    sqlQuery = "INSERT INTO Condiciones"
                        + "(idVariable,valor1,valor2,relacion)"
                        + "VALUES"
                        +"("+c.getIdVariable()+","+c.getValorMinimo()+","+c.getValorMaximo()+","+c.getRelacion()+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}
                            //Ahora obtengo el id de la condicion insertada para insertar en la tabla alertasXcondiciones
                            if (sePudo){
                                sqlQuery = "SELECT max(id) from condiciones";
                                System.out.println("Select: "+sqlQuery);
                                rs= getStatement().executeQuery(sqlQuery);
                                if ( rs != null) {
                                    idCondicion=rs.getInt("id");
                                    sePudo = true;
                                }else{sePudo=false;}
                            }
                        // Ya con los 2 Ids necesarios inserto en alertasXcondiciones
                        if (sePudo){
                            sqlQuery = "INSERT INTO CondicionesPorAlerta"
                        + "(idCondicion,idAlerta)"
                        + "VALUES"
                        +"("+idCondicion+","+idAlerta+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}      
                        }
                        
                   }else{
                       //En caso de que la condicion ya exista entonces inserto en alertasXcondiciones con el Id ya existente
                       if (sePudo){
                            sqlQuery = "INSERT INTO CondicionesPorAlerta"
                        + "(idCondicion,idAlerta)"
                        + "VALUES"
                        +"("+c.getId()+","+idAlerta+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}                    
                        }
                   }
                   //Continuacion for
                   
                }
                
                }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
    
public boolean updateAlerta(modelo.alertas.Alerta alerta){
        List<Integer> deleteList=null;
        boolean sePudo = false;
        int idAlerta=0;
        int idCondicion=0;
        String sqlQuery="";
        ResultSet rs;
        try {                                
            String titulo=null;
            if (alerta.getTitulo()!=null) {
                titulo = ""+alerta.getTitulo()+"";
            }                     
            String estado = String.valueOf(alerta.isEstado());
            if(alerta.getCondiciones()!=null){
                        sqlQuery ="Update Alertas "
                        + "SET estado ="+alerta.isEstado()+", "
                        + "mensaje ="+alerta.getMensaje()+", "
                        + "flagsAcciones ="+alerta.getFlagsAcciones()+", "
                        + "titulo ="+alerta.getTitulo()+" WHERE "
                        + "id="+alerta.getId();
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
        
                //Inserto condiciones en Tabla de Condiciones
 
               for (Condicion c:alerta.getCondiciones()){
                   if (c.getId()==0){
                    sqlQuery = "INSERT INTO Condiciones"
                        + "(idVariable,valor1,valor2,relacion)"
                        + "VALUES"
                        +"("+c.getIdVariable()+","+c.getValorMinimo()+","+c.getValorMaximo()+","+c.getRelacion()+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}
                            //Ahora obtengo el id de la condicion insertada para insertar en la tabla alertasXcondiciones
                            if (sePudo){
                                sqlQuery = "SELECT max(id) from condiciones";
                                System.out.println("Select: "+sqlQuery);
                                rs= getStatement().executeQuery(sqlQuery);
                                if ( rs != null) {
                                    idCondicion=rs.getInt("id");
                                    sePudo = true;
                                }else{sePudo=false;}
                            }
                        // Ya con los 2 Ids necesarios inserto en alertasXcondiciones
                        if (sePudo){
                            sqlQuery = "INSERT INTO CondicionesPorAlerta"
                        + "(idCondicion,idAlerta)"
                        + "VALUES"
                        +"("+idCondicion+","+idAlerta+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}      
                        }
                        
                   }else{
                       //En caso de que la condicion ya exista (en condiciones) entonces verifico si no existe e inserto en alertasXcondiciones con el Id ya existente
                       if (sePudo){
                            sqlQuery = "SELECT * FROM CondicionesPorAlerta WHERE "
                        + "(idCondicion="+idCondicion+" AND "+"idAlerta="+idAlerta+")";
                        System.out.println("Select: "+sqlQuery);
                        if (getStatement().executeQuery(sqlQuery) ==null) {
                            //En este caso inserto un registro en la tabla
                            sqlQuery = "INSERT INTO CondicionesPorAlerta"
                            + "(idCondicion,idAlerta)"
                            + "VALUES"
                            +"("+idCondicion+","+idAlerta+")";
                            System.out.println("Insert: "+sqlQuery);
                            if (getStatement().executeUpdate(sqlQuery) > 0) {
                                sePudo = true;
                            }else{sePudo=false;}    
                        }else{//En este caso no hago nada porque ya existe el registro   
                        }
                       }                    
                   }
                   //Continuacion for
                   
                   }
                   //Obtengo todas los idCondicion de la tabla condicionesPorAlerta para ver
                   //si se eliminaron condiciones de esta alerta y en ese caso eliminarlas de la base
                   boolean esta=false;
                   sqlQuery = "SELECT idCondicion FROM CondicionesPorAlerta WHERE "
                   +"idAlerta="+idAlerta+")";
                   System.out.println("Select: "+sqlQuery);
                   rs=getStatement().executeQuery(sqlQuery);
                   if (rs!=null) {
                       while (rs.next()){
                           esta=false;
                           for (Condicion c:alerta.getCondiciones()){
                               if (rs.getInt("IdCondicion")==c.getId()){
                                   esta=true;
                               } 
                           }
                           if (!esta){
                               //En el caso de que la condicion este en la bd pero no en la lista entonces la agrego a la lista de condiciones a eliminar
                               deleteList.add(idCondicion);
                           }
                           
                       } 
                       //Elimino todos los registros en CondicionesPorAlertas que coincidan con los datos en deleteList
                       
                       for (int i=0;i<=deleteList.size();i++){
                            sqlQuery = "DELETE FROM CondicionesPorAlertas "
                            + "WHERE (idCondicion = "+deleteList.get(i)+") AND (idAlerta="+alerta.getId()+")";
                            System.out.println("DELETE: "+sqlQuery);
                            if (getStatement().executeUpdate(sqlQuery) > 0) {
                                sePudo = true;
                            }
                        }
                   
                }
                
            }    
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }

public boolean deleteAlerta(modelo.alertas.Alerta alerta){
        boolean sePudo = false;

        try {        
            String sqlQuery = "DELETE FROM Alertas "
                    + "WHERE id = "+alerta.getId();
            System.out.println("DELETE: "+sqlQuery);
            if (getStatement().executeUpdate(sqlQuery) > 0) {
                sePudo = true;
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        
        
        return sePudo;
    }
    
}
