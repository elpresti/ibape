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
import modelo.alertas.Alerta;
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
                //Ahora obtengo el id de la alerta insertada para insertar en la tabla condiciones
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
                        + "(idVariable,idAlerta,valor1,valor2,relacion,descripcion)"
                        + "VALUES"
                        +"("+c.getIdVariable()+","+alerta.getId()+","+c.getValorMinimo()+","+c.getValorMaximo()+","+c.getRelacion()+","+c.getDescripcion()+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}
                        
                        /*
                        
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
                        */
                   }
                   //Continuacion for
                   
                }
                
                }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
   
   public boolean insertaVariables(){
        boolean sePudo = false;
        String sqlQuery="";
        try {                                
            sqlQuery = "INSERT INTO Variables"
            + "(id,nombre,unidad)"
            + "VALUES"
            +"(1,'Profundidad','mts')";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            sqlQuery = "INSERT INTO Variables"
            + "(id,nombre,unidad)"
            + "VALUES"
            +"(2,'Distancia','mts')";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            sqlQuery = "INSERT INTO Variables"
            + "(id,nombre,unidad)"
            + "VALUES"
            +"(3,'Cantidad de Marcas','marcas')";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            }catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }
        return sePudo;
    }
   
   public boolean insertaRelaciones(){
        boolean sePudo = false;
        String sqlQuery="";
        try {                                
            sqlQuery = "INSERT INTO Relaciones"
            + "(id,descripcion,cantValores)"
            + "VALUES"
            +"(1,'igual a',1)";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            sqlQuery = "INSERT INTO Relaciones"
            + "(id,descripcion,cantValores)"
            + "VALUES"
            +"(2,'mayor o igual que',1)";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            sqlQuery = "INSERT INTO Relaciones"
            + "(id,descripcion,cantValores)"
            + "VALUES"
            +"(3,'menor o igual que',1)";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            sqlQuery = "INSERT INTO Relaciones"
            + "(id,descripcion,cantValores)"
            + "VALUES"
            +"(4,'entre',2)";
                System.out.println("Insert: "+sqlQuery);
                if (getStatement().executeUpdate(sqlQuery) > 0) {
                    sePudo = true;
                }else{sePudo=false;}
                
            }catch (SQLException ex) {
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
        
                //Borro todas las condiciones correspondientes a esta alerta
                
                sqlQuery = "DELETE FROM Condiciones "
                            + "WHERE (idAlerta = "+alerta.hashCode()+")";
                            System.out.println("DELETE: "+sqlQuery);
                            if (getStatement().executeUpdate(sqlQuery) > 0) {
                                sePudo = true;
                            }
                
                //Inserto condiciones en Tabla de Condiciones
 
               for (Condicion c:alerta.getCondiciones()){        
                        
                        sqlQuery = "INSERT INTO Condiciones"
                        + "(idVariable,idAlerta,valor1,valor2,relacion,descripcion)"
                        + "VALUES"
                        +"("+c.getIdVariable()+","+alerta.getId()+","+c.getValorMinimo()+","+c.getValorMaximo()+","+c.getRelacion()+","+c.getDescripcion()+")";
                        System.out.println("Insert: "+sqlQuery);
                        if (getStatement().executeUpdate(sqlQuery) > 0) {
                            sePudo = true;
                        }else{sePudo=false;}
                            
                   //Continuacion for
                   
                   }
                   /* "Comentado posterior a reforma de modelo de Datos
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
                */
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

   public ArrayList<modelo.alertas.Alerta> getAlertasFromDB(){
        ArrayList<modelo.alertas.Alerta> alertas = new ArrayList();        
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM Alertas");
            while (rs.next()) {                
                modelo.alertas.Alerta alerta = new modelo.alertas.Alerta();
                // Get the data from the row using the column name
                alerta.setId(rs.getInt("id"));
                alerta.setEstado(rs.getBoolean("estado"));
                alerta.setTitulo(rs.getString("titulo"));
                alerta.setMensaje(rs.getString("mensaje"));
                alerta.setFlagsAcciones(rs.getInt("flagsAcciones"));
                // Queda pendiente cargar sus Condiciones ----------
                alertas.add(alerta);
            }
        } catch (SQLException ex) {
            Logueador.getInstance().agregaAlLog(ex.toString());
        }        
        return alertas;
    }
    
}
