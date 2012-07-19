/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class PuntoHistorico {
    private int id;
    private java.sql.Timestamp fechaYhora;
    private double latitud;
    private double longitud;
    private double altitud;
    private double rumbo;
    private double velocidad;
    private double profundidad;
    private double velocidadAgua;
    private double tempAgua;
    private String comentarios;

    public PuntoHistorico(){        
    }

    /**
     * @return the fechaYhora
     */
    public java.util.Date getFechaYhora() {        
        return new java.util.Date(fechaYhora.getTime());
    }

    /**
     * @param fechaYhora the fechaYhora to set
     */
    public void setFechaYhora(java.util.Date fechaYhora) {
        this.fechaYhora = new java.sql.Timestamp(fechaYhora.getTime());
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
     * @return the altitud
     */
    public double getAltitud() {
        return altitud;
    }

    /**
     * @param altitud the altitud to set
     */
    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    /**
     * @return the rumbo
     */
    public double getRumbo() {
        return rumbo;
    }

    /**
     * @param rumbo the rumbo to set
     */
    public void setRumbo(double rumbo) {
        this.rumbo = rumbo;
    }

    /**
     * @return the velocidad
     */
    public double getVelocidad() {
        return velocidad;
    }

    /**
     * @param velocidad the velocidad to set
     */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * @return the profundidad
     */
    public double getProfundidad() {
        return profundidad;
    }

    /**
     * @param profundidad the profundidad to set
     */
    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    /**
     * @return the velocidadAgua
     */
    public double getVelocidadAgua() {
        return velocidadAgua;
    }

    /**
     * @param velocidadAgua the velocidadAgua to set
     */
    public void setVelocidadAgua(double velocidadAgua) {
        this.velocidadAgua = velocidadAgua;
    }

    /**
     * @return the tempAgua
     */
    public double getTempAgua() {
        return tempAgua;
    }

    /**
     * @param tempAgua the tempAgua to set
     */
    public void setTempAgua(double tempAgua) {
        this.tempAgua = tempAgua;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
        java.sql.Timestamp fechaYhoraActual=new java.sql.Timestamp(getFechaYhora().getTime());
        salida=salida
          +"<Placemark>"
            +"<name>"+fechaYhoraActual.getHours()+":"+fechaYhoraActual.getMinutes()+":"+fechaYhoraActual.getSeconds()+"</name>"
            +"<description>"
               + "<![CDATA[<div>"
                  + "Datos de este punto "
                  + "<br>  <strong>- Fecha y hora:</strong> "+getFechaYhora()+" hs"
                  + "<br>  <strong>- Latitud:</strong> "+getLatitud()
                  + "<br>  <strong>- Longitud:</strong> "+getLongitud()
                  + "<br>  <strong>- Rumbo:</strong> "+getRumbo()+"° "
                  + "<br>  <strong>- Velocidad:</strong> "+getVelocidad()+" kmph"
                  + "<br>  <strong>- Profundidad:</strong> "+getProfundidad()+" m"                
                  + "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
               + "</div>]]>"
            + "</description>"
            +"<Point>"
                //+"<gx:altitudeMode>absolute</gx:altitudeMode>" //clampToGround, relativeToGround, absolute
                +"<coordinates>"+getLongitud()+","+getLatitud()+","+getAltitud()+"</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }

    /**
     * @return the comentarios
     */
    public String getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    
}
