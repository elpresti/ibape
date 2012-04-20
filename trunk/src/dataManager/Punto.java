/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataManager;
import java.util.Observable;
/**
 *
 * @author Sebastian
 */
public class Punto extends Observable{
    private java.sql.Timestamp fechaYhora;
    private double latitud=0;
    private String latHemisf="S";
    private double latConNegativo=0;
    private double longitud=0;
    private String lonHemisf="W";
    private double lonConNegativo=0;
    private double altitud=0;
    private double rumbo=0;
    private double velocidad=0;
    private double profundidad=0;
    private double velocidadAgua=0;
    private double tempAgua=0;    
    private String objeto="BARCO";
    private String comentarios="S/C";
    
    static Punto unicaInstancia;
    
    private Punto (){}
    
    public static Punto getInstance(){
        if (unicaInstancia == null){
            unicaInstancia=new Punto();
        }
        return unicaInstancia;
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
        //...metodo pendiente        
        salida=//"<?xml version='1.0' encoding='UTF-8'?>"
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\" xmlns:kml=\"http://www.opengis.net/kml/2.2\" xmlns:atom=\"http://www.w3.org/2005/Atom\">"
        //"<kml xmlns=\"http://www.opengis.net/kml/2.2\">"
        +"<Document>";
        if (conCamara){
          salida=salida
            +"<Camera>"
              +"<longitude>"+(getLonConNegativo()*0.99999)+"</longitude>"
              +"<latitude>"+(getLatConNegativo()*1.00005)+"</latitude>"
              +"<altitude>50</altitude>"
              +"<heading>0</heading>"   //gira el ojo a la derecha (positivo) a la izquierda (negativo) 
              +"<tilt>70</tilt>" //angulo de vision del ojo. 0= vista vertical a la tirra (desde arriba), 75=vista con 75° de inclinacion
            +"</Camera>";        
        }
        java.sql.Timestamp fechaYhoraActual=getFechaYhora();
        salida=salida
          +"<Placemark>"
            +"<name>"+fechaYhoraActual.getHours()+":"+fechaYhoraActual.getMinutes()+":"+fechaYhoraActual.getSeconds()+"</name>"
            +"<description>"
               + "<![CDATA[<div>"
                  + "Datos de este punto "
                  + "<br>  <strong>- Fecha y hora:</strong> "+getFechaYhora()+" hs"
                  + "<br>  <strong>- Latitud:</strong> "+getLatitud()+" "+getLatHemisf()
                  + "<br>  <strong>- Longitud:</strong> "+getLongitud()+" "+getLonHemisf()                  
                  + "<br>  <strong>- Rumbo:</strong> "+getRumbo()+"° "
                  + "<br>  <strong>- Velocidad:</strong> "+getVelocidad()+" kmph"
                  + "<br>  <strong>- Profundidad:</strong> "+getProfundidad()+" m"                
                  + "<br><br>Esto es una url: <a href=\"http://www.google.com\" target=\"_blank\">Google!</a>"
               + "</div>]]>"
            + "</description>"
            +"<Point>"
              +"<coordinates>"+getLonConNegativo()+","+getLatConNegativo()+","+getAltitud()+"</coordinates>"  
            +"</Point>"
          +"</Placemark>"
        +"</Document>"                
                
        +"</kml>";    
        return salida;
    }


    /**
     * @return the fechaYhora
     */
    public java.sql.Timestamp getFechaYhora() {
        fechaYhora= new java.sql.Timestamp(new java.util.Date().getTime());
        return fechaYhora;
    }

    /**
     * @param fechaYhora the fechaYhora to set
     */
    public void setFechaYhora(java.sql.Timestamp fechaYhora) {
        this.fechaYhora = fechaYhora;
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
    public boolean setLatitud(double latitud) {
       latitud=truncaNro(latitud, 10);
       boolean seModifico=false;
       if (this.latitud != latitud) {
          this.latitud = latitud;
          String latString = "";
          if (getLatHemisf().equalsIgnoreCase("S"))
              { latString=latString+"-"; }
          setLatConNegativo(Double.valueOf(latString+String.valueOf(latitud)));
          setChanged();
          notifyObservers();
          seModifico=true;
       }
       return seModifico;
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
    public boolean setLongitud(double longitud) {
       longitud=truncaNro(longitud, 10);
       boolean seModifico=false;         
       if (this.longitud != longitud) {
          this.longitud = longitud;
          String lonString = "";
          if (getLonHemisf().equalsIgnoreCase("W"))
              { lonString=lonString+"-"; }
          setLonConNegativo(Double.valueOf(lonString+String.valueOf(longitud)));                   
          setChanged();
          notifyObservers();
          seModifico=true;
       }
       return seModifico;
    }

    public double truncaNro(double nroEntrada,int cantTotalDeCaracteres){
        String tmp=String.valueOf(nroEntrada);        
        if (tmp.length()>cantTotalDeCaracteres) { 
            tmp = tmp.substring(0, cantTotalDeCaracteres); 
        }
        return Double.valueOf(tmp);
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
    public boolean setRumbo(double rumbo) {
        boolean seModifico=false;         
        if (this.rumbo != rumbo) {
            this.rumbo = rumbo;
            setChanged();        
            notifyObservers();                     
            seModifico=true;
        }
        return seModifico;
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
    public boolean setVelocidad(double velocidad) {
        boolean seModifico=false; 
        if (this.velocidad != velocidad){
            this.velocidad = velocidad;    
            setChanged();
            notifyObservers();            
            seModifico=true;
        }
        return seModifico;
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
    public boolean setProfundidad(double profundidad) {
        boolean seModifico=false;         
        if (this.profundidad != profundidad){
            this.profundidad = profundidad;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;
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
    public boolean setVelocidadAgua(double velocidadAgua) {
        boolean seModifico=false; 
        if (this.velocidadAgua != velocidadAgua) {
            this.velocidadAgua = velocidadAgua;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;        
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
    public boolean setTempAgua(double tempAgua) {
        boolean seModifico=false; 
        if (this.tempAgua != tempAgua) {
            this.tempAgua = tempAgua;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;
    }

    /**
     * @return the objeto
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     * @param objeto the objeto to set
     */
    public boolean setObjeto(String objeto) {
        boolean seModifico=false;         
        if (!(this.objeto.equals(objeto))){
            this.objeto = objeto;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;
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
    public boolean setComentarios(String comentarios) {
        boolean seModifico=false; 
        if (!(this.comentarios.equals(comentarios))){
            this.comentarios = comentarios;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;
    }

    /**
     * @return the latHemisf
     */
    public String getLatHemisf() {
        return latHemisf;
    }

    /**
     * @param latHemisf the latHemisf to set
     */
    public boolean setLatHemisf(String latHemisf) {
        boolean seModifico=false; 
        if (!(this.latHemisf.equals(latHemisf))){
            this.latHemisf = latHemisf;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;        
    }

    /**
     * @return the lonHemisf
     */
    public String getLonHemisf() {
        return lonHemisf;
    }

    /**
     * @param lonHemisf the lonHemisf to set
     */
    public boolean setLonHemisf(String lonHemisf) {
        boolean seModifico=false; 
        if (!(this.lonHemisf.equals(lonHemisf))){
            this.lonHemisf = lonHemisf;
            setChanged();
            notifyObservers();
            seModifico=true;
        }
        return seModifico;        
    }

    /**
     * @return the latConNegativo
     */
    public double getLatConNegativo() {
        return latConNegativo;
    }

    /**
     * @param latConNegativo the latConNegativo to set
     */
    public void setLatConNegativo(double latConNegativo) {
        this.latConNegativo = latConNegativo;
    }

    /**
     * @return the lonConNegativo
     */
    public double getLonConNegativo() {
        return lonConNegativo;
    }

    /**
     * @param lonConNegativo the lonConNegativo to set
     */
    public void setLonConNegativo(double lonConNegativo) {
        this.lonConNegativo = lonConNegativo;
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

}
