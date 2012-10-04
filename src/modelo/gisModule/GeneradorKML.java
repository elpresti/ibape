/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.gisModule;

import java.io.StringReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.dataCapture.Sistema;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.AdministraCatPoi;
import modelo.dataManager.POI;
import modelo.dataManager.PuntoHistorico;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import persistencia.Logueador;

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
                  //+"<altitudeMode>absolute</altitudeMode>"
                  +"<altitudeMode>clampToGround</altitudeMode>"  
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

    public String conviertePuntosARecorridoKmlGxTrack(boolean conCamara, ArrayList<modelo.dataManager.PuntoHistorico> puntos) {
            String salida = "";        
        if (puntos.size()>0){
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
                +"<altitude>100</altitude>" 
                +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
                +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
                +"</Camera>";        
            }              
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
            long duracion = puntos.get(puntos.size()-1).getFechaYhora().getTime()-puntos.get(0).getFechaYhora().getTime();
            salida=salida
            +"<Placemark id=\"recorridoGxTrack\">"// id=tramo+"+strFechaYhoraPrimero+" - "+strFechaYhoraUltimo+">"
                +"<name>IBaPE</name>"
//                +"<visibility>1</visibility>" 
                +"<description>"
                    + "<![CDATA[<div>Recorrido del barco entre "+sdf.format(puntos.get(0).getFechaYhora())+"  y  "+sdf.format(puntos.get(puntos.size()-1).getFechaYhora())
                    + "<br><strong>Duración del recorrido:</strong> "+(duracion/(1000 * 60 * 60))+" hs = "+(duracion/(1000 * 60))+" min"
                    + "<br><strong>Campaña:</strong> "+AdministraCampanias.getInstance().getCampaniaEnCurso().getDescripcion()
                    + "<br><strong>Barco:</strong> "+AdministraCampanias.getInstance().getCampaniaEnCurso().getBarco()
                    + "<br><strong>Capitan:</strong> "+AdministraCampanias.getInstance().getCampaniaEnCurso().getCapitan()
                + "]]> </description>"
                +"<styleUrl>#yellowLineGreenPoly</styleUrl>"
                +"<gx:Track>"
                  //+"<altitudeMode>absolute</altitudeMode>";
                  +"<altitudeMode>clampToGround</altitudeMode>";  
            int i=0;
            while (i<puntos.size()){
                salida+="<when>"+sdfFecha.format(puntos.get(i).getFechaYhora())+"T"+sdfHora.format(puntos.get(i).getFechaYhora())+"Z</when>";
                i++;
            }
            i=0;
            while (i<puntos.size()){
                salida+="<gx:coord>"+puntos.get(i).getLongitud()+" "+puntos.get(i).getLatitud()+" 0"+"</gx:coord>";
                i++;
            }
            salida+=
                    "<Model  id=\"modeloBarcoPesquero\">"
                        +"<gx:altitudeMode>relativeToSeaFloor</gx:altitudeMode>"
                        +"<Orientation>"
                        +"  <heading>90</heading>"
                        +"  <tilt>0</tilt>"
                        +"  <roll>0.0</roll>"
                        +"</Orientation>"
                        +"<Link>"
                            +"<href>http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"
                        +persistencia.BrokerDbMapa.getInstance().getPuertoWebServer()+"/imgs/pesqueroGrande.dae</href>"
                        +"</Link>"
                    +"</Model>"
                    +"</gx:Track>";
            salida+="</Placemark>"
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
        String htmlImgSonda = "";
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
               + "<![CDATA[";
        salida+= getContenidoHtmlDelGlobo(poi);
        salida +=
               "]]>"
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
                  //+ "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
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

    public String getContenidoHtmlDelGlobo(modelo.dataManager.POI poi){
        String htmlImgSonda="";
        java.sql.Timestamp fechaYhora=new java.sql.Timestamp(poi.getFechaHora().getTime());
        String horaStr=fechaYhora.getHours()+":"+fechaYhora.getMinutes()+":"+fechaYhora.getSeconds();
        String contenidoHtml=
                "<div>"
                  + "<table border=0>"
                    + "<tr>"
                    +    "<td valign=\"top\">"
                            + "Datos de este punto "
                            + "<br>  <strong>- Fecha y hora:</strong> "+poi.getFechaHora()+" "+horaStr+" hs"
                            + "<br>  <strong>- Latitud:</strong> "+poi.getLatitud()
                            + "<br>  <strong>- Longitud:</strong> "+poi.getLongitud()
                            + "<br>  <strong>- Categoria de POI:</strong> "+poi.getCategoria().getTitulo();
        if (poi.getIdCategoriaPOI() != AdministraCatPoi.getInstance().getIdCatImgsConMarcas()){
                    contenidoHtml += "<br>  <strong>- Descripcion:</strong> "+poi.getDescripcion();
        }else{
            //SAXBuilder se encarga de cargar el archivo XML del disco o de un String
            // Creamos el builder basado en SAX      
            SAXBuilder builder = new SAXBuilder();  
            try {
                // Construimos el arbol DOM a partir del fichero xml  y Cargamos el documento
                Document contenidoXML = builder.build(new StringReader(poi.getDescripcion()));
                // Obtenemos la etiqueta raíz  
                Element raiz = contenidoXML.getRootElement();  
                // Recorremos los hijos de la etiqueta raíz  
                List<Element> hijosRaiz = raiz.getChildren();  
                for(Element parametro: hijosRaiz){
                    // Obtenemos el nombre y su contenido de tipo texto  
                    String nombre = parametro.getAttribute("nombre").getValue();
                    String valor = parametro.getAttribute("valor").getValue();
                    if (parametro.getName() == "imgFileName"){
                        htmlImgSonda += "<a href=\""+modelo.gisModule.Browser.getInstance().getUrl()+"/getImage.php?ruta="+System.getProperty("user.dir")+"\\"+valor+"\" target=\"_blank\">";
                        htmlImgSonda += "<img src='"+modelo.gisModule.Browser.getInstance().getUrl()+"/getImage.php?ruta="+System.getProperty("user.dir")+"\\"+valor+"' height='200' width='350'/>";
                        htmlImgSonda += "</a>";
                    }else{
                        contenidoHtml += "<br> <strong>- "+nombre+" :</strong> "+valor;
                    }
                }
            }catch(Exception e){
                Logueador.getInstance().agregaAlLog("conviertePOIaKml(): "+e.toString());
            }
        }
        contenidoHtml+=  "</td>"
                    +    "<td valign=\"top\" align=\"right\">";
                    if (htmlImgSonda.length()==0){
                        if (Sistema.getInstance().pathIconoEsValido(poi.getCategoria().getPathIcono())){
                        contenidoHtml+= "<img src=\"http://"+persistencia.BrokerDbMapa.getInstance().getDirecWebServer()+":"
                                        +persistencia.BrokerDbMapa.getInstance().getPuertoWebServer()+"/imgs/"
                                        +poi.getCategoria().getPathIcono()+"\">";                        
                        }
                    }else{
                        contenidoHtml+=htmlImgSonda;
                    }
                    contenidoHtml+=
                         "</td>"                
                    + "</tr>"
                  + "</table>" 
               + "</div>";
        return contenidoHtml;
    }
}
