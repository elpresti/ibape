/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.gisModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import modelo.dataCapture.Sistema;
import modelo.dataManager.POI;
import modelo.dataManager.PuntoHistorico;

/**
 *
 * @author Sebastian
 */
public class GeneradorKML {
    private static GeneradorKML unicaInstancia;
    
    private GeneradorKML(){
    }
    
    public static GeneradorKML getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new GeneradorKML();
       return unicaInstancia;
    }    
    
    public String conviertePuntosARecorridoKml(boolean conCamara, ArrayList<modelo.dataManager.PuntoHistorico> puntos) {
            String salida = "";        
        if (puntos.size()>0){            
            //Cosas pendientes de incluir:
                // - Miniatura imagen de la sonda
                // - Cantidad de marcas encontradas
                // - Alertas (todas)

            //Preset de camara 1 = vista aerea trasera:
            //Longitud:getLonConNegativo()*1.00003  Latitud:getLatConNegativo()*1.00006  altitude:50  heading:35  tilt:75
            //Preset de camara 2 = vista aerea lateral derecha:
            //Longitud:getLonConNegativo()*0.99999  Latitud:getLatConNegativo()*1.00005  altitude:50  heading:0  tilt:70
            // punto de ejemplo para calibrar posicion de camara: setLonConNegativo(-56.85432); setLatConNegativo(-37.11671); 
            salida=
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">"
            +"<Document>"
                +"<name>Codigo KML</name>"
                +"<Style id=\"yellowLineGreenPoly\">"
                   +"<LineStyle>"
                     +"<color>7f00ffff</color>"
                     +"<width>4</width>"
                   +"</LineStyle>"
                   +"<PolyStyle>"
                   +   "<color>7f00ff00</color>"
                   +"</PolyStyle>"
                +"</Style>";
                    
            if (conCamara){
            salida=salida
                +"<Camera>"
                +"<longitude>"+(puntos.get(puntos.size()-1).getLongitud()*0.99999)+"</longitude>"
                +"<latitude>"+(puntos.get(puntos.size()-1).getLatitud()*1.00005)+"</latitude>"
                +"<altitude>50</altitude>"
                +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
                +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
                +"</Camera>";        
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            salida=salida
            +"<Placemark id=\"recorrido\">"// id=tramo+"+strFechaYhoraPrimero+" - "+strFechaYhoraUltimo+">"
                +"<name>"+sdf.format(puntos.get(0).getFechaYhora())+"  -  "+sdf.format(puntos.get(puntos.size()-1).getFechaYhora())+"</name>"
                +"<visibility>1</visibility>" 
                +"<description>Recorrido entre "+sdf.format(puntos.get(0).getFechaYhora())+"  -  "+sdf.format(puntos.get(puntos.size()-1).getFechaYhora())+"</description>"
                +"<styleUrl>#yellowLineGreenPoly</styleUrl>"
                +"<LineString>"
                  +"<extrude>1</extrude>"
                  +"<tessellate>1</tessellate>"
                  +"<altitudeMode>absolute</altitudeMode>"                    
                      +"<coordinates>";
            int i=0;
            while (i<puntos.size()){
                salida+=puntos.get(i).getLongitud()+","+puntos.get(i).getLatitud()+",0 ";//"+puntos.get(i).getAltitud()+" ";
                i++;
            }
            salida+=
                       "</coordinates>"
                  +"</LineString>"
            +"</Placemark>"
            +"</Document>"                

            +"</kml>";    
        }        
        return salida;
    }

    public String getKmlStylesFromCatPois(ArrayList<POI> pois){
        String salida="";
        ArrayList<String> categorias=new ArrayList();
        int i = 0;
        for (POI poi : pois){
            if (Sistema.getInstance().pathIconoEsValido(poi.getCategoria().getPathIcono())  &&  !categorias.contains(poi.getCategoria().getPathIcono())) {
                salida+=
                "<Style id=\""+poi.getCategoria().getPathIcono()+"\">"
               +"<IconStyle>"
               +   "<Icon>"
               +     "<href>http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"
                        +persistencia.BrokerDbMapa.getInstance().getPuertoWebServer()+"/imgs/"
                        +poi.getCategoria().getPathIcono()
               +     "</href>"
               +   "</Icon>"
               +"</IconStyle>"
               +"</Style>";
                categorias.add(poi.getCategoria().getPathIcono());
            }
            i++;
        }
        return salida;
    }


    public String getKmlStyleFromCatPoi(POI poi){
        String salida="";    
        if (poi != null){
            if (Sistema.getInstance().pathIconoEsValido(poi.getCategoria().getPathIcono())) {
                salida+=
                "<Style id=\""+poi.getCategoria().getPathIcono()+"\">"
               +"<IconStyle>"
               +   "<Icon>"
               +     "<href>http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"
                        +persistencia.BrokerDbMapa.getInstance().getPuertoWebServer()+"/imgs/"
                        +poi.getCategoria().getPathIcono()
               +     "</href>"
               +   "</Icon>"
               +"</IconStyle>"
               +"</Style>";
            }
        }
        return salida;
    }
    
    
    public String conviertePuntoHistoricoAkml(modelo.dataManager.PuntoHistorico punto, boolean conCamara) {
        //Cosas pendientes de incluir:
            // - Miniatura imagen de la sonda
            // - Cantidad de marcas encontradas
            // - Alertas (todas)
        
        //Preset de camara 1 = vista aerea trasera:
        //Longitud:getLonConNegativo()*1.00003  Latitud:getLatConNegativo()*1.00006  altitude:50  heading:35  tilt:75
        //Preset de camara 2 = vista aerea lateral derecha:
        //Longitud:getLonConNegativo()*0.99999  Latitud:getLatConNegativo()*1.00005  altitude:50  heading:0  tilt:70
        // punto de ejemplo para calibrar posicion de camara: setLonConNegativo(-56.85432); setLatConNegativo(-37.11671);
        String salida = "";
        salida=
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">"
        +"<Document>";
        if (conCamara){
          salida=salida
            +"<Camera>"
              +"<longitude>"+(punto.getLongitud()*0.99999)+"</longitude>"
              +"<latitude>"+(punto.getLatitud()*1.00005)+"</latitude>"
              +"<altitude>50</altitude>"
              +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
              +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
            +"</Camera>";        
        }
        java.sql.Timestamp fechaYhoraActual=new java.sql.Timestamp(punto.getFechaYhora().getTime());
        salida=salida
          +"<Placemark>"
            +"<name>"+fechaYhoraActual.getHours()+":"+fechaYhoraActual.getMinutes()+":"+fechaYhoraActual.getSeconds()+"</name>"
            +"<description>"
               + "<![CDATA[<div>"
                  + "Datos de este punto "
                  + "<br>  <strong>- Fecha y hora:</strong> "+punto.getFechaYhora()+" hs"
                  + "<br>  <strong>- Latitud:</strong> "+punto.getLatitud()
                  + "<br>  <strong>- Longitud:</strong> "+punto.getLongitud()
                  + "<br>  <strong>- Rumbo:</strong> "+punto.getRumbo()+"° "
                  + "<br>  <strong>- Velocidad:</strong> "+punto.getVelocidad()+" kmph"
                  + "<br>  <strong>- Profundidad:</strong> "+punto.getProfundidad()+" m"                
                  + "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
               + "</div>]]>"
            + "</description>"
            +"<Point>"
                //+"<gx:altitudeMode>absolute</gx:altitudeMode>" //clampToGround, relativeToGround, absolute
                +"<coordinates>"+punto.getLongitud()+","+punto.getLatitud()+","+punto.getAltitud()+"</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }

    
    public String conviertePOIaKml(modelo.dataManager.POI poi, boolean conCamara) {
        //Cosas pendientes de incluir:
            // - Miniatura imagen de la sonda
            // - Cantidad de marcas encontradas
            // - Alertas (todas)
        
        //Preset de camara 1 = vista aerea trasera:
        //Longitud:getLonConNegativo()*1.00003  Latitud:getLatConNegativo()*1.00006  altitude:50  heading:35  tilt:75
        //Preset de camara 2 = vista aerea lateral derecha:
        //Longitud:getLonConNegativo()*0.99999  Latitud:getLatConNegativo()*1.00005  altitude:50  heading:0  tilt:70
        // punto de ejemplo para calibrar posicion de camara: setLonConNegativo(-56.85432); setLatConNegativo(-37.11671);
        String salida = ""; 
        salida=
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">"
        +"<Document>";
        salida+=getKmlStyleFromCatPoi(poi);
        if (conCamara){
          salida=salida
            +"<Camera>"
              +"<longitude>"+poi.getLongitud()+"</longitude>"
              +"<latitude>"+poi.getLatitud()+"</latitude>"
              +"<altitude>50</altitude>"
              +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
              +"<tilt>0</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
            +"</Camera>";        
        }
        java.sql.Timestamp fechaYhora=new java.sql.Timestamp(poi.getFechaHora().getTime());
        String horaStr=fechaYhora.getHours()+":"+fechaYhora.getMinutes()+":"+fechaYhora.getSeconds();
        salida=salida
          +"<Placemark>"
            +"<name>"+horaStr+"</name>";
        if (Sistema.getInstance().pathIconoEsValido(poi.getCategoria().getPathIcono())){
            salida+="<styleUrl>#"+poi.getCategoria().getPathIcono()+"</styleUrl>";
        }
        salida+="<description>"
               + "<![CDATA[<div>"
                  + "<table border=0>"
                    + "<tr>"
                    +    "<td valign=\"top\">"
                            + "Datos de este punto "
                            + "<br>  <strong>- Fecha y hora:</strong> "+poi.getFechaHora()+" "+horaStr+" hs"
                            + "<br>  <strong>- Latitud:</strong> "+poi.getLatitud()
                            + "<br>  <strong>- Longitud:</strong> "+poi.getLongitud()
                            + "<br>  <strong>- Categoria de POI:</strong> "+poi.getCategoria().getTitulo()
                            + "<br>  <strong>- Descripcion:</strong> "+poi.getDescripcion()
                    +    "</td>"
                    +    "<td valign=\"top\" align=\"right\">";
                    if (Sistema.getInstance().pathIconoEsValido(poi.getCategoria().getPathIcono())){
                            salida+= "<img src=\"http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"
                                    +persistencia.BrokerDbMapa.getInstance().getPuertoWebServer()+"/imgs/"
                                    +poi.getCategoria().getPathIcono()+"\">";                        
                    }
                    salida+=
                         "</td>"                
                    + "</tr>"
                  + "</table>" 
               + "</div>]]>"
            + "</description>"
            +"<Point>"
                //+"<gx:altitudeMode>absolute</gx:altitudeMode>" //clampToGround, relativeToGround, absolute
                +"<coordinates>"+poi.getLongitud()+","+poi.getLatitud()+",0</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }

    public String conviertePuntoNavegacionAkml(modelo.dataManager.Punto punto, boolean conCamara) {
        //Cosas pendientes de incluir:
            // - Miniatura imagen de la sonda
            // - Cantidad de marcas encontradas
            // - Alertas (todas)
        
        //Preset de camara 1 = vista aerea trasera:
        //Longitud:getLonConNegativo()*1.00003  Latitud:getLatConNegativo()*1.00006  altitude:50  heading:35  tilt:75
        //Preset de camara 2 = vista aerea lateral derecha:
        //Longitud:getLonConNegativo()*0.99999  Latitud:getLatConNegativo()*1.00005  altitude:50  heading:0  tilt:70
        // punto de ejemplo para calibrar posicion de camara: setLonConNegativo(-56.85432); setLatConNegativo(-37.11671);
        String salida = "";
        //...metodo pendiente        
        salida=//"<?xml version='1.0' encoding='UTF-8'?>"
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
        //"<kml xmlns=\"http://www.opengis.net/kml/2.2\">"
        +"<Document>";
        if (conCamara){
          salida=salida
            +"<Camera>"
              +"<longitude>"+(punto.getLonConNegativo()*0.99999)+"</longitude>"
              +"<latitude>"+(punto.getLatConNegativo()*1.00005)+"</latitude>"
              +"<altitude>50</altitude>"
              +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
              +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
            +"</Camera>";        
        }
        java.sql.Timestamp fechaYhoraActual=punto.getFechaYhora();
        salida=salida
          +"<Placemark>"
            +"<name>"+fechaYhoraActual.getHours()+":"+fechaYhoraActual.getMinutes()+":"+fechaYhoraActual.getSeconds()+"</name>"
            +"<description>"
               + "<![CDATA[<div>"
                  + "Datos de este punto "
                  + "<br>  <strong>- Fecha y hora:</strong> "+punto.getFechaYhora()+" hs"
                  + "<br>  <strong>- Latitud:</strong> "+punto.getLatitud()+" "+punto.getLatHemisf()
                  + "<br>  <strong>- Longitud:</strong> "+punto.getLongitud()+" "+punto.getLonHemisf()                  
                  + "<br>  <strong>- Rumbo:</strong> "+punto.getRumbo()+"° "
                  + "<br>  <strong>- Velocidad:</strong> "+punto.getVelocidad()+" kmph"
                  + "<br>  <strong>- Profundidad:</strong> "+punto.getProfundidad()+" m"                
                  + "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
               + "</div>]]>"
            + "</description>"
            +"<Point>"
                //+"<gx:altitudeMode>absolute</gx:altitudeMode>" //clampToGround, relativeToGround, absolute
                +"<coordinates>"+punto.getLonConNegativo()+","+punto.getLatConNegativo()+","+punto.getAltitud()+"</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }

    
}
