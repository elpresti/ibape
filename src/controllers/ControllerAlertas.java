/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.AlertWin;
import gui.PanelAlertaActiva;
import gui.PanelOpcAlertas;
import gui.PanelSelector;
import gui.VentanaIbape;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.alertas.AdministraAlertas;
import modelo.alertas.Alerta;
import modelo.alertas.AlertaListaOn;
import modelo.alertas.Condicion;
import modelo.alertas.Relacion;
import modelo.alertas.Variable;
import modelo.dataManager.POI;
import modelo.dataManager.Punto;
import modelo.dataManager.SondaSet;
import modelo.dataManager.UltimaImgProcesada;
import org.jdom.Element;
import persistencia.BrokerConfig;
import persistencia.Logueador;

/**
 *
 * @author Martin
 */
public class ControllerAlertas extends Observable implements Observer{
    static ControllerAlertas unicaInstancia;


    
    private ArrayList<Variable> variables;
    private ArrayList<Relacion> relaciones;
    private ArrayList<Condicion> condicionesAct=new ArrayList<Condicion>();
    private Condicion condicionAct;
    private Relacion relacionAct;
    private Variable variableAct;
    private Alerta alertaAct;
    private int idUltAlertaInsertada;
    private Punto punto=modelo.dataManager.Punto.getInstance();
    private SondaSet sonda=modelo.dataManager.SondaSet.getInstance();
    private UltimaImgProcesada ultImg=modelo.dataManager.UltimaImgProcesada.getInstance();
    private AdministraAlertas administraAlertas=modelo.alertas.AdministraAlertas.getInstance();
    private ArrayList<Alerta> alertasEnFuncionamiento= new ArrayList<Alerta>();
    private ArrayList<AlertaListaOn> alertasActivadas= new ArrayList<AlertaListaOn>();
    private boolean estadoAlertas=AdministraAlertas.getInstance().isEstadoAlertas();
    private int idOcurProv;
    private int indexVariable;
    private static int indexProfundidad=1;
    private static int indexCantDeMarcas=2;
    private static int indexLatitud=3;
    private static int indexLongitud=4;
    private static int indexVelocidad=5;
    private static int indexRumbo=6;
    private static int indexVelocidadAgua=7;
    private static int indexTempAgua=8;
    private static int indexFechaYhora=9;
    private int cantOcurNoVistas;
    private boolean deteccionAlertas;
    
   
    public static ControllerAlertas getInstance() {
       if (unicaInstancia == null){
          unicaInstancia = new ControllerAlertas();
       }
       return unicaInstancia;
    }

    private ControllerAlertas(){

        idOcurProv=10000;
        //gui.PanelOpcConfiguracion.getInstance();
    }
    
    /**
     * @return the variables
     */
    public ArrayList<Variable> getVariables() {
        return variables;
    }

        /**
     * @return the indexProfundidad
     */
    public static int getIndexProfundidad() {
        return indexProfundidad;
    }

    /**
     * @return the indexCantDeMarcas
     */
    public static int getIndexCantDeMarcas() {
        return indexCantDeMarcas;
    }

    /**
     * @return the indexLatitud
     */
    public static int getIndexLatitud() {
        return indexLatitud;
    }

    /**
     * @return the indexLongitud
     */
    public static int getIndexLongitud() {
        return indexLongitud;
    }

    /**
     * @return the indexVelocidad
     */
    public static int getIndexVelocidad() {
        return indexVelocidad;
    }

    /**
     * @return the indexRumbo
     */
    public static int getIndexRumbo() {
        return indexRumbo;
    }

    /**
     * @return the indexVelocidadAgua
     */
    public static int getIndexVelocidadAgua() {
        return indexVelocidadAgua;
    }

    /**
     * @return the indexTempAgua
     */
    public static int getIndexTempAgua() {
        return indexTempAgua;
    }

    public static int getIndexFechaYhora() {
        return indexFechaYhora;
    }

  
    /**
     * @return the relaciones
     */
    public ArrayList<Relacion> getRelaciones() {
        return relaciones;
    }

    /**
     * @param aRelaciones the relaciones to set
     */
    public void setRelaciones(ArrayList<Relacion> aRelaciones) {
        relaciones = aRelaciones;
    }

    /**
     * @param aVariables the variables to set
     */
    public boolean leeVariablesDB() {
        boolean sePudo=false;
        try{
        variables=persistencia.BrokerAlertas.getInstance().getVariablesFromDB();
        sePudo=true;
        }catch (Exception e){
            sePudo=false;
        }
        return sePudo;
    }

    public boolean nuevaAlerta(String nombre, String mensaje, String estado, ArrayList<modelo.alertas.Condicion> condiciones){
        boolean sePudo=false;
        boolean est=false;
        if ((nombre.length()>1) && (mensaje.length()>1) && (!condiciones.isEmpty())){
            modelo.alertas.Alerta alerta = new modelo.alertas.Alerta();
            alerta.setTitulo(nombre);
            alerta.setMensaje(mensaje);
            if (estado.matches("Activada")){
                est=true;
            }else{
                est=false;
            }
            alerta.setEstado(est);
            for (modelo.alertas.Condicion c:condiciones){
                alerta.agregarCondicion(c);
            }
           
            if (modelo.alertas.AdministraAlertas.getInstance().agregarAlerta(alerta)) {
                sePudo=true;
                gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
            }
        }
        return sePudo;
    }
    
    public static boolean borrarAlerta(int idAlerta){
        boolean sePudo=false;
        if (AdministraAlertas.getInstance().eliminarAlerta(AdministraAlertas.getInstance().getAlerta(idAlerta))){
            gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
            sePudo=true;
        }        
        return sePudo;
    }

    public boolean modificarAlerta(int id, String nombre, String mensaje, String estado,ArrayList<Condicion> condiciones){
        boolean sePudo=false;
        boolean est;
        if ((nombre.length()>1) && (mensaje.length()>1)){
            //traigo la alerta a modificar y le cargo los valores nuevos
            modelo.alertas.Alerta alerta = modelo.alertas.AdministraAlertas.getInstance().getAlerta(id);
            alerta.setTitulo(nombre);            
            alerta.setMensaje(mensaje);             
            if (estado.matches("Activada")){
                est=true;
            }else{
                est=false;
            }
            alerta.setEstado(est); 
            alerta.setCondiciones(condiciones);
            if (modelo.alertas.AdministraAlertas.getInstance().modificarAlerta(alerta)) {             
                gui.PanelOpcAlertas.getInstance().cargaGrillaAlertas();
                alertasOn();
                sePudo=true;                
            }
            
        }
        return sePudo;
    }
    
    public boolean leeDocYseteaPanelOpcAlertas(){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document        
        try {
            Element raizConfiguracionIbape= BrokerConfig.getInstance().getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            setEstadoAlertas(parametros.getChild("PanelAlertas").getAttribute("Estado").getBooleanValue());
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    

    public void setEstadoAlertas(boolean estado) {
        
        modelo.alertas.AdministraAlertas.getInstance().setEstadoAlertas(estado);   
        persistencia.BrokerConfig.getInstance().actualizaDatosPanelAlertas(String.valueOf(estado));
        estadoAlertas=estado;
        if (estado){   
            gui.PanelOpcAlertas.getInstance().habilitaTablaAlertas();
            gui.PanelBarraDeEstado.getInstance().setAlertasActivadas();
        } else {
            gui.PanelOpcAlertas.getInstance().deshabilitaTablaAlertas();
            gui.PanelBarraDeEstado.getInstance().setAlertasDesactivadas();
        }
        persistencia.BrokerConfig.getInstance().guardaConfiguracion();
                    
    }

    public boolean leeRelacionesDB(int index) {
        boolean sePudo=false;
        try{
        relaciones=persistencia.BrokerAlertas.getInstance().getRelacionesFromDB(index);
        sePudo=true;
        }catch (Exception e){
            sePudo=false;
        }
        return sePudo;
    }

    public void agregaCondicionAct(int idProvCondicion,int idRelacion,int idVariable,String valMin, String valMax,String descripcion){
    
    if (condicionesAct!=null){
        boolean encontro=false;    
        int i=0;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (idProvCondicion==getCondicionesAct().get(i).getId()){
                encontro=true;
                getCondicionesAct().get(i).setIdRelacion(idRelacion);
                getCondicionesAct().get(i).setDescripcion(descripcion);
                getCondicionesAct().get(i).setValorMaximo(valMax);
                getCondicionesAct().get(i).setValorMinimo(valMin);
                getCondicionesAct().get(i).setIdVariable(idVariable);
            }
            i++;
        }
        if (!encontro){    
            
            Condicion condicion=new Condicion(idProvCondicion,idVariable,idRelacion,valMin,valMax,descripcion);
            getCondicionesAct().add(condicion);
        }
    }else{
            setCondicionesAct(new ArrayList<Condicion>());
            Condicion condicion=new Condicion(idProvCondicion,idVariable,idRelacion,valMin,valMax,descripcion);
            getCondicionesAct().add(condicion);
    }
    }

    public void cambiaDatosActuales(int indexCondicion) {
        boolean encontro=false;
        int i=0;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (indexCondicion==getCondicionesAct().get(i).getId()){
                encontro=true;
                setCondicionAct(getCondicionesAct().get(i));
                setRelacionAct(getRelacionConId(condicionAct.getIdRelacion()));
                setVariableAct(getVariableConId(condicionAct.getIdVariable()));               
            }
            i++;
        }
    }

    /**
     * @return the relacionAct
     */
    public Relacion getRelacionAct() {
        return relacionAct;
    }
 
    public Variable getVariableConId(int index){
    int i=0;
    Variable variable=new Variable();
    boolean encontro=false;
        while ((!encontro)&&(i<=variables.size()-1)){
            if (variables.get(i).getId()==index){
                encontro=true;
                variable= variables.get(i);
            }
            i++;
        }
        return variable;
    }
    
    public Relacion getRelacionConId(int index){
    int i=0;
    Relacion relacion=new Relacion();
    boolean encontro=false;
        while ((!encontro)&&(i<=relaciones.size()-1)){
            if (relaciones.get(i).getId()==index){
                encontro=true;
                relacion= relaciones.get(i);
            }
            i++;
        }
        return relacion;
    }

    /**
     * @param relacionAct the relacionAct to set
     */
    public void setRelacionAct(Relacion relacionAct) {
        this.relacionAct = relacionAct;
    }

    /**
     * @return the variableAct
     */
    public Variable getVariableAct() {
        return variableAct;
    }

    /**
     * @param variableAct the variableAct to set
     */
    public void setVariableAct(Variable variableAct) {
        this.variableAct = variableAct;
    }
    


    /**
     * @return the alertaAct
     */
    public Alerta getAlertaAct() {
        return alertaAct;
    }

    /**
     * @param alertaAct the alertaAct to set
     */
    public void setAlertaAct(Alerta alertaAct) {
        this.alertaAct = alertaAct;
    }

    /**
     * @return the condicionAct
     */
    public Condicion getCondicionAct() {
        return condicionAct;
    }

    /**
     * @param condicionAct the condicionAct to set
     */
    public void setCondicionAct(Condicion condicionAct) {
        this.condicionAct = condicionAct;
    }

    public void borraCondicionListaAct (int idCondicion){
    int i=0;
    boolean encontro=false;
        while ((!encontro)&&(i<=getCondicionesAct().size()-1)){
            if (getCondicionesAct().get(i).getId()==idCondicion){
                encontro=true;
                getCondicionesAct().remove(i);
            }
            i++;
        }

    }
    
    public void borrarCondicion(int idDeCondicionSeleccionada) {
        borraCondicionListaAct(idDeCondicionSeleccionada);
    }

    /**
     * @return the condicionesAct
     */
    public ArrayList<Condicion> getCondicionesAct() {
        return condicionesAct;
    }

    /**
     * @param condicionesAct the condicionesAct to set
     */
    public void setCondicionesAct(ArrayList<Condicion> condicionesAct) {
        this.condicionesAct = condicionesAct;
    }

    /**
     * @return the idUltAlertaInsertada
     */
    public int getIdUltAlertaInsertada() {
        return idUltAlertaInsertada;
    }

    /**
     * @param idUltAlertaInsertada the idUltAlertaInsertada to set
     */
    public void setIdUltAlertaInsertada(int idUltAlertaInsertada) {
        this.idUltAlertaInsertada = idUltAlertaInsertada;
    }

    public void update(Observable o, Object arg) {
    if (arg!=null && isDeteccionAlertas()){ 
          String indexS=(String) arg.toString();
          int index=Integer.parseInt(indexS);
          if (o == punto){
                AnalizaActivaciones thAnalizaActivaciones = new AnalizaActivaciones();
                thAnalizaActivaciones.setIndex(index);
                thAnalizaActivaciones.start();
          }
          else
              if (o == sonda){

              }
              else 
                  if (o == administraAlertas){

                  }else if (o==ultImg){
                    if (index==indexCantDeMarcas){
                        analizaActivacionesCantDeMarcas();
                    }

                    }
        }
        }
    
    public void alertasOn() {
        
   

        if (modelo.alertas.AdministraAlertas.getInstance().isEstadoAlertas()){
            
            if ((modelo.alertas.AdministraAlertas.getInstance().getAlertas() == null) || (modelo.alertas.AdministraAlertas.getInstance().getAlertas().isEmpty()) ) {
                modelo.alertas.AdministraAlertas.getInstance().leerAlertasDeLaDB();

            }
            setDeteccionAlertas(false);
            alertasEnFuncionamiento = modelo.alertas.AdministraAlertas.getInstance().getAlertasEnFuncionamiento();            
            leerOcurAlertasDeLaDB();
            calculaCantOcurNoVistas();
            setDeteccionAlertas(true);
        }
    
}

        public boolean leerOcurAlertasDeLaDB() {
        boolean sePudo=false;
        try {
            setAlertasActivadas(persistencia.BrokerAlertas.getInstance().getOcurAlertasFromDB());
            sePudo=true;
        }
        catch (Exception e){
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }    
    
    public void analizaActivacionesProfundidad() {
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var profundidad
                if ((condiciones.get(f).getIdVariable()==indexProfundidad)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            activacionAnt=existeAlertaActiva(al);            
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());                    
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
    
                }
            }else{
                if (activacionAnt){
                    int idOcurDesactivada=desactivaAlerta(al,fecha);
                    persistencia.BrokerAlertas.getInstance().actualizaOcurAlerta(idOcurDesactivada,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    private boolean analizaCondiciones(ArrayList<Condicion> condiciones) {
        int f=0;
        boolean cumpleTodo=true;
        while ((f<condiciones.size()) && (cumpleTodo)){
            Condicion c=condiciones.get(f);
                if ((c.getIdVariable()==indexProfundidad)){
                    cumpleTodo=cumpleTodo && cumpleCondicionProfundidad(c);               
                }else if((c.getIdVariable()==indexCantDeMarcas)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionCantDeMarcas(c);
                }else if((c.getIdVariable()==indexLatitud)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionLatitud(c);
                }else if((c.getIdVariable()==indexLongitud)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionLongitud(c);
                }else if((c.getIdVariable()==indexVelocidad)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionVelocidad(c);
                }else if((c.getIdVariable()==indexRumbo)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionRumbo(c);
                }else if((c.getIdVariable()==indexVelocidadAgua)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionVelocidadAgua(c);
                }else if((c.getIdVariable()==indexTempAgua)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionTemperaturaAgua(c);
                }else if((c.getIdVariable()==indexFechaYhora)) {
                    cumpleTodo=cumpleTodo && cumpleCondicionFechaYhora(c);
                }
            f++;
        }
        return cumpleTodo;
    }

    private boolean cumpleCondicionProfundidad(Condicion c) {
        boolean cumpleTodo=true;
        if (c.getIdRelacion()==1){
                        if ((Float.parseFloat(c.getValorMinimo())==(float) punto.getProfundidad())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getProfundidad()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Float.parseFloat(c.getValorMinimo())>=(float) punto.getProfundidad()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{
                                if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getProfundidad() && Float.parseFloat(c.getValorMaximo())>=(float) punto.getProfundidad()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
        }

    public void accionesAlIniciar() {
        leeDocYseteaPanelOpcAlertas();               
        administraAlertas.addObserver(this);
        punto.addObserver(this);
        sonda.addObserver(this);
        AlertWin.getInstance();
        alertasOn();
        
    }
    

    private boolean existeAlertaActiva(Alerta a) {
        boolean existe=false;
        int i=getAlertasActivadas().size()-1;
        while ((i>=0)){
            if (getAlertasActivadas().get(i).getAlerta().getId()==a.getId() && getAlertasActivadas().get(i).isEstadoActivacion()==true){
                existe=true;
            }
            i--;
        }
        return existe;
    }

    public boolean getEstadoAlertas() {
        return estadoAlertas;
    }

    public String creaMensaje() {
        String mensaje="";
        int f=0;
        while (f<condicionesAct.size()){
                Condicion c=condicionesAct.get(f);
                mensaje=mensaje+" - "+c.getDescripcion();
                f++;
            }
        return mensaje;
          
        }

    private boolean cumpleCondicionCantDeMarcas(Condicion c) {
        boolean cumpleTodo=true;
        if (c.getIdRelacion()==1){
                        if ((Float.parseFloat(c.getValorMinimo())==ultImg.getMarcas().size())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Float.parseFloat(c.getValorMinimo())<=ultImg.getMarcas().size()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Float.parseFloat(c.getValorMinimo())>=ultImg.getMarcas().size()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{
                                if (Float.parseFloat(c.getValorMinimo())<=ultImg.getMarcas().size() && Float.parseFloat(c.getValorMaximo())>=ultImg.getMarcas().size()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }

    private boolean cumpleCondicionLatitud(Condicion c) {
    boolean cumpleTodo=true;
    String strMin=c.getValorMinimo();
    String strMax=c.getValorMaximo();
    if ((strMin.substring(0,1)).equals("S")){
         strMin=strMin.replace("S ","-");
    }else{
         strMin=strMin.replace("N ","");
    }
    
    if ((strMax.substring(0,1)).equals("S")){
         strMax=strMax.replace("S ","-");
    }else{
         strMax=strMax.replace("N ","");
    }
    
        if (c.getIdRelacion()==1){
                        if ((Double.parseDouble(strMin)==punto.getLatConNegativo())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Double.parseDouble(strMin)<=punto.getLatConNegativo()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Double.parseDouble(strMin)>=punto.getLatConNegativo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{


                                if (Double.parseDouble(strMin)<=punto.getLatConNegativo() && Double.parseDouble(strMax)>=punto.getLatConNegativo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }

    private boolean cumpleCondicionLongitud(Condicion c) {
    boolean cumpleTodo=true;
    String strMin=c.getValorMinimo();
    String strMax=c.getValorMaximo();
    if ((strMin.substring(0,1)).equals("W")){
         strMin=strMin.replace("W ","-");
    }else{
         strMin=strMin.replace("E ","");
    }
    
    if ((strMax.substring(0,1)).equals("W")){
         strMax=strMax.replace("W ","-");
    }else{
         strMax=strMax.replace("E ","");
    }
    
        if (c.getIdRelacion()==1){
                        if ((Double.parseDouble(strMin)==punto.getLonConNegativo())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Double.parseDouble(strMin)<=punto.getLonConNegativo()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Double.parseDouble(strMin)>=punto.getLonConNegativo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{


                                if (Double.parseDouble(strMin)<=punto.getLonConNegativo() && Double.parseDouble(strMax)>=punto.getLonConNegativo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }

    private boolean cumpleCondicionFechaYhora(Condicion c) {
        boolean cumpleTodo=true;
        if (c.getIdRelacion()==9){
                        if ((Timestamp.valueOf(c.getValorMinimo())==punto.getFechaYhora())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Timestamp.valueOf(c.getValorMinimo()).before((punto.getFechaYhora()))){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Timestamp.valueOf(c.getValorMinimo()).after((punto.getFechaYhora()))){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{
                                if ((Timestamp.valueOf(c.getValorMinimo()).before((punto.getFechaYhora()))) && (Timestamp.valueOf(c.getValorMaximo()).after((punto.getFechaYhora())))){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }


    private boolean cumpleCondicionTemperaturaAgua(Condicion c) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean cumpleCondicionVelocidadAgua(Condicion c) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean cumpleCondicionRumbo(Condicion c) {
    boolean cumpleTodo=true;
        if (c.getIdRelacion()==1){
                        if ((Float.parseFloat(c.getValorMinimo())==(float) punto.getRumbo())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getRumbo()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Float.parseFloat(c.getValorMinimo())>=(float) punto.getRumbo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{
                                if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getRumbo() && Float.parseFloat(c.getValorMaximo())>=(float) punto.getRumbo()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }

    private boolean cumpleCondicionVelocidad(Condicion c) {
        boolean cumpleTodo=true;
        if (c.getIdRelacion()==1){
                        if ((Float.parseFloat(c.getValorMinimo())==(float) punto.getVelocidad())){
                            cumpleTodo=cumpleTodo && true;
                        }else{
                            cumpleTodo=cumpleTodo && false;
                        }
                    }else{
                        if (c.getIdRelacion()==2){
                            if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getVelocidad()){
                                cumpleTodo=cumpleTodo && true;
                            }else{
                                cumpleTodo=cumpleTodo && false;
                            }
                        }else{
                            if (c.getIdRelacion()==3){
                                if (Float.parseFloat(c.getValorMinimo())>=(float) punto.getVelocidad()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }else{
                                if (Float.parseFloat(c.getValorMinimo())<=(float) punto.getVelocidad() && Float.parseFloat(c.getValorMaximo())>=(float) punto.getVelocidad()){
                                    cumpleTodo=cumpleTodo && true;
                                }else{
                                    cumpleTodo=cumpleTodo && false;
                                }
                            }   
                        }
        }
        return cumpleTodo;
    }

    public void analizaActivacionesLatitud() {
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacionAnt=existeAlertaActiva(alertasEnFuncionamiento.get(i));
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var Latitud
                if ((condiciones.get(f).getIdVariable()==indexLatitud)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{                   
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());    
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
                }
            }else{
                if (activacionAnt){
                    desactivaAlerta(al,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    public void analizaActivacionesLongitud() {
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacionAnt=existeAlertaActiva(alertasEnFuncionamiento.get(i));
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var Longitud
                if ((condiciones.get(f).getIdVariable()==indexLongitud)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{                   
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());    
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
                }
            }else{
                if (activacionAnt){
                    desactivaAlerta(al,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    public void analizaActivacionesVelocidad() {
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacionAnt=existeAlertaActiva(alertasEnFuncionamiento.get(i));
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var Velocidad
                if ((condiciones.get(f).getIdVariable()==indexVelocidad)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{                   
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
                }
            }else{
                if (activacionAnt){
                    desactivaAlerta(al,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    public void analizaActivacionesRumbo() {
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacionAnt=existeAlertaActiva(alertasEnFuncionamiento.get(i));
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var Rumbo
                if ((condiciones.get(f).getIdVariable()==indexRumbo)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{                   
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
                }
            }else{
                if (activacionAnt){
                    desactivaAlerta(al,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    public void analizaActivacionesvelocidadAgua() {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public void analizaActivacionesTempAgua() {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public void analizaActivacionesFechaYhora() { 
        
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacionAnt=existeAlertaActiva(alertasEnFuncionamiento.get(i));
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var fechaYhora
                if ((condiciones.get(f).getIdVariable()==indexFechaYhora)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            Timestamp fecha=modelo.dataManager.Punto.getInstance().getFechaYhora();
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{                   
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
                }
            }else{
                if (activacionAnt){
                    getAlertasActivadas().remove(al);
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }

    public void analizaActivacionesCantDeMarcas() {
    
        ArrayList<Condicion> condiciones;
        boolean activacion;
        boolean activacionAnt;
        int i = 0;
        while ((i<alertasEnFuncionamiento.size())){
            condiciones=alertasEnFuncionamiento.get(i).getCondiciones();
            int f=0;
            activacion=false;
            while ((f<condiciones.size())){//busco alertas que contengan alguna de sus condiciones la var cant. de marcas
                if ((condiciones.get(f).getIdVariable()==indexCantDeMarcas)){
                    activacion=analizaCondiciones(condiciones);
                }
                f++;
            }
            Alerta al=alertasEnFuncionamiento.get(i);
            activacionAnt=existeAlertaActiva(al);            
            Timestamp fecha=new java.sql.Timestamp(new java.util.Date().getTime());
            
            if (activacion){
                if (activacionAnt){
                    //Alerta continua activada
                }else{
                    AlertaListaOn alertaListaOn=new AlertaListaOn();
                    alertaListaOn.setAlerta(al);
                    alertaListaOn.setEstadoActivacion(true);
                    alertaListaOn.setFechaActivacion(fecha);
                    alertaListaOn.setVista(0);
                    setCantOcurNoVistas(getCantOcurNoVistas()+1);
                    alertaListaOn.setLatitud(modelo.dataManager.Punto.getInstance().getLatitud());
                    alertaListaOn.setLongitud(modelo.dataManager.Punto.getInstance().getLongitud());
                    alertaListaOn.setValores(obtieneValorVariablesAct());
                    int id=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(alertaListaOn);
                    alertaListaOn.setIdOcur(id);
                    getAlertasActivadas().add(alertaListaOn);
                    AlertWin.getInstance().setIdShowing(id);                    
                    setChanged();
                    notifyObservers();
                    AlertWin.getInstance().setIntNew(true);
                    AlertWin.getInstance().muestraAlerta();
    
                }
            }else{
                if (activacionAnt){
                    desactivaAlerta(al,fecha);
                    setChanged();
                    notifyObservers();
                }else{
                    //Alerta continua desactivada
                }
            }
            i++;
        }

    }
/*
    public void actualizaHoraSistema() throws InterruptedException{
        int segundos=1;
        while(true){
            modelo.dataManager.Punto.getInstance().setFechaYhora(new java.sql.Timestamp(new java.util.Date().getTime()));
            Thread.sleep (segundos*1000);
        } 
    }
*/
    private int desactivaAlerta(Alerta al,Timestamp fechaDes) {
        boolean desactivacion=false;
        int result=-1;
        int i=getAlertasActivadas().size()-1;
        while ((i>=0) && (!desactivacion)){
            if (getAlertasActivadas().get(i).getAlerta().getId()==al.getId()){
                getAlertasActivadas().get(i).setEstadoActivacion(false);
                getAlertasActivadas().get(i).setFechaDesactivacion(fechaDes);
                desactivacion=true;
                result=getAlertasActivadas().get(i).getIdOcur();
            }
            i--;
        }
        return result;
    }

    private void actualizaDatosAlertWin() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return the alertasActivadas
     */
    public ArrayList<AlertaListaOn> getAlertasActivadas() {
        return alertasActivadas;
    }

    /**
     * @param alertasActivadas the alertasActivadas to set
     */
    public void setAlertasActivadas(ArrayList<AlertaListaOn> alertasActivadas) {
        this.alertasActivadas = alertasActivadas;
    }

    public void muestraOcurSig(int idShowing) {
        int i=0;
        boolean found=false;
        while ((i<getAlertasActivadas().size())&&(!found)){
            if (getAlertasActivadas().get(i).getIdOcur()==idShowing){
                found=true;
            }
            i++;
        }
        if (i<getAlertasActivadas().size()){
            gui.AlertWin.getInstance().setIdShowing(getAlertasActivadas().get(i).getIdOcur());
            setChanged();
            notifyObservers();
        }
    }
    
    public void muestraOcurAnt(int idShowing) {
        int i=getAlertasActivadas().size()-1;
        boolean found=false;
        while ((i>=0)&&(!found)){
            if (getAlertasActivadas().get(i).getIdOcur()==idShowing){
                found=true;
            }
            i--;
        }
        if (i>=0){
            gui.AlertWin.getInstance().setIdShowing(getAlertasActivadas().get(i).getIdOcur());
            setChanged();
            notifyObservers();
        }
      
    }

    public AlertaListaOn getAlertaShowing(int idShowing) {
        int i=getAlertasActivadas().size()-1;
        int idAnt=getAlertasActivadas().size()-1;
        while (i>=0){
            if (getAlertasActivadas().get(i).getIdOcur()==idShowing){
                idAnt=i;                
            }
            i--;
        }
        return getAlertasActivadas().get(idAnt);
    }
    
        public int getIndexAlertaShowing(int idShowing) {
        int i=getAlertasActivadas().size()-1;
        boolean encontro=false;
        int idAnt=getAlertasActivadas().size()-1;
        while ((i>=0) && (!encontro)){
            if (getAlertasActivadas().get(i).getIdOcur()==idShowing){
                idAnt=i;
                encontro=true;
            }
            i--;
        }
        return idAnt;
    }
    
/*
    public boolean guardaOcurAlertas() {
        
        boolean sePudo=true;
        boolean result=false;
        int i=0;
        while ((i<getAlertasActivadas().size()-1) && (sePudo)){
            if (getAlertasActivadas().get(i).getIdOcur()>=10000){
                result=persistencia.BrokerAlertas.getInstance().guardaOcurAlerta(getAlertasActivadas().get(i));
                sePudo=sePudo && result;
            }
            i++;
        }
        return sePudo;
    }
*/

    public void muestraOcurUlt() {
        int i=getAlertasActivadas().size()-1;
        if (i>=0){
            gui.AlertWin.getInstance().setIdShowing(getAlertasActivadas().get(i).getIdOcur());
            setChanged();
            notifyObservers();
            AlertWin.getInstance().muestraAlertaSinPreload();
        }
      
    }

    /**
     * @return the alertasActivadasVista
     */
   
    public void calculaCantOcurNoVistas() {
        int i=0;
        int cant=0;
        while (i<=getAlertasActivadas().size()-1){
            if (getAlertasActivadas().get(i).getVista()==0){
               cant=cant++;
            }
            i++;
        }
        setCantOcurNoVistas(cant); 
    }
    
    public boolean actualizaEstadoVistaOcur(int idOcur){

        return persistencia.BrokerAlertas.getInstance().actualizaValorVistaOcurAlerta(idOcur);
    }

    /**
     * @return the cantOcurNoVistas
     */
    public int getCantOcurNoVistas() {
        return cantOcurNoVistas;
    }

    /**
     * @param cantOcurNoVistas the cantOcurNoVistas to set
     */
    public void setCantOcurNoVistas(int cantOcurNoVistas) {
    if (cantOcurNoVistas!=this.cantOcurNoVistas){
        this.cantOcurNoVistas = cantOcurNoVistas;
        gui.PanelOpcAlertas.getInstance().actualizaLabelCantOcurNoVistas();
    }

    }    

    /**
     * @return the deteccionAlertas
     */
    public boolean isDeteccionAlertas() {
        return deteccionAlertas;
    }

    /**
     * @param deteccionAlertas the deteccionAlertas to set
     */
    public void setDeteccionAlertas(boolean deteccionAlertas) {
        this.deteccionAlertas = deteccionAlertas;
    }

    private ArrayList<Object> obtieneValorVariablesAct() {
    ArrayList<Object> valores=new ArrayList<Object>();
   
    if (!(Punto.getInstance()==null)){
         valores.add(Punto.getInstance().getProfundidad());
    }else{
        valores.add(0);
    }
    
    if ((!(ultImg==null)) && (!(ultImg.getMarcas()==null))){
         valores.add(ultImg.getMarcas().size());
    }else{
        valores.add(0);
    }

    if (!(Punto.getInstance()==null)){
        valores.add(Punto.getInstance().getLatConNegativo());
        valores.add(Punto.getInstance().getLonConNegativo());
        valores.add(Punto.getInstance().getVelocidad());
        valores.add(Punto.getInstance().getRumbo());
        valores.add(Punto.getInstance().getVelocidadAgua());
        valores.add(Punto.getInstance().getTempAgua());
        valores.add(Punto.getInstance().getFechaYhora());
    }else{
        valores.add(0.0);
        valores.add(0.0);
        valores.add(0.0);
        valores.add(0.0);
        valores.add(0.0);
        valores.add(0.0);
        valores.add("");
    }

        return valores;
    }
    /**
     * @return the estadoAlertas
     */
    public boolean isEstadoAlertas() {
        return estadoAlertas;
    }
}
    
class AnalizaActivaciones implements Runnable{
    Thread thAa;
    private int index;
    public void run() { 
        try{
            if (index==ControllerAlertas.getInstance().getIndexProfundidad()){
                ControllerAlertas.getInstance().analizaActivacionesProfundidad(); 
            }else if (index==ControllerAlertas.getInstance().getIndexLatitud()){
                ControllerAlertas.getInstance().analizaActivacionesLatitud();
            }else if (index==ControllerAlertas.getInstance().getIndexLongitud()){
                ControllerAlertas.getInstance().analizaActivacionesLongitud();
            }else if (index==ControllerAlertas.getInstance().getIndexVelocidad()){
                ControllerAlertas.getInstance().analizaActivacionesVelocidad();
            }else if (index==ControllerAlertas.getInstance().getIndexRumbo()){
                ControllerAlertas.getInstance().analizaActivacionesRumbo();
            }else if (index==ControllerAlertas.getInstance().getIndexVelocidadAgua()){
                ControllerAlertas.getInstance().analizaActivacionesvelocidadAgua();
            }else if (index==ControllerAlertas.getInstance().getIndexTempAgua()){
                ControllerAlertas.getInstance().analizaActivacionesTempAgua();
            }else if (index==ControllerAlertas.getInstance().getIndexFechaYhora()){
                ControllerAlertas.getInstance().analizaActivacionesFechaYhora();
            }            
        }catch(Exception e){
            //Logueador.getInstance().agregaAlLog("AnalizaActivaciones.run(): "+e.toString());
        }        
        thAa = null;
    }
    public void start() {
        if (thAa == null) {
            thAa = new Thread(this);
            thAa.setPriority(Thread.MIN_PRIORITY);
            thAa.start();
        }
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getImgFileName() {
        return index;
    }
}    
    
