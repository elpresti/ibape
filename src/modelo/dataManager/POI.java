/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

import java.util.ArrayList;

/**
 *
 * @author Sebastian
 */
public class POI {

    private int id;
    private int idCategoriaPOI;
    private int idCampania;
    private double latitud;
    private double longitud;
    private String pathImg;
    private java.util.Date fechaHora;
    private ArrayList<Marca> marcas;
    private String descripcion;
    private CategoriaPoi categoria;

    public POI() {
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the fechaHora
     */
    public java.util.Date getFechaHora() {
        return fechaHora;
    }

    /**
     * @param fechaHora the fechaHora to set
     */
    public void setFechaHora(java.util.Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * @return the pathImg
     */
    public String getPathImg() {
        return pathImg;
    }

    /**
     * @param pathImg the pathImg to set
     */
    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    /**
     * @return the marcas
     */
    public ArrayList<Marca> getMarcas() {
        return marcas;
    }

    /**
     * @param marcas the marcas to set
     */
    public void setMarcas(ArrayList<Marca> marcas) {
        this.marcas = marcas;
    }

    public double calculaProfPromMarcas() {
        double profProm = 0;
        //---------- metodo pendiente --------
        return profProm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    /**
     * @return the idCategoriaPOI
     */
    public int getIdCategoriaPOI() {
        return idCategoriaPOI;
    }

    /**
     * @param idCategoriaPOI the idCategoriaPOI to set
     */
    public void setIdCategoriaPOI(int idCategoriaPOI) {
        this.idCategoriaPOI = idCategoriaPOI;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the idCampania
     */
    public int getIdCampania() {
        return idCampania;
    }

    /**
     * @param idCampania the idCampania to set
     */
    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }

    /**
     * @return the categoria
     */
    public CategoriaPoi getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(CategoriaPoi categoria) {
        this.categoria = categoria;
    }
    
    public String convierteAkml(boolean conCamara) {
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
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
        +"<Document>";
        if (conCamara){
          salida=salida
            +"<Camera>"
              +"<longitude>"+(getLongitud()*0.99999)+"</longitude>"
              +"<latitude>"+(getLatitud()*1.00005)+"</latitude>"
              +"<altitude>50</altitude>"
              +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
              +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
            +"</Camera>";        
        }
        java.sql.Timestamp fechaYhoraActual=new java.sql.Timestamp(getFechaHora().getTime());
        salida=salida
          +"<Placemark>"
            +"<name>"+fechaYhoraActual.getHours()+":"+fechaYhoraActual.getMinutes()+":"+fechaYhoraActual.getSeconds()+"</name>"
            +"<description>"
               + "<![CDATA[<div>"
                  + "Datos de este punto "
                  + "<br>  <strong>- Fecha y hora:</strong> "+getFechaHora()+" hs"
                  + "<br>  <strong>- Latitud:</strong> "+getLatitud()
                  + "<br>  <strong>- Longitud:</strong> "+getLongitud()
                  //+ "<br>  <strong>- Rumbo:</strong> "+getRumbo()+"° "
                  //+ "<br>  <strong>- Velocidad:</strong> "+getVelocidad()+" kmph"
                  //+ "<br>  <strong>- Profundidad:</strong> "+getProfundidad()+" m"                
                  + "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
               + "</div>]]>"
            + "</description>"
            +"<Point>"
                //+"<gx:altitudeMode>absolute</gx:altitudeMode>" //clampToGround, relativeToGround, absolute
                +"<coordinates>"+getLongitud()+","+getLatitud()+",0</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }
    
}
