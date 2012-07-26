/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Sebastian
 */
public class BrokerConfig {    
    static BrokerConfig unicaInstancia;
    Document docBrokerConfig;
    String fileName="config.dat";
    
    private BrokerConfig() {
        
    }    
    
    public FileInputStream abreArchivoConfig() throws JDOMException{
                //Intentamos abrir el archivo, y si no existe lo creamos
                String ruta = System.getProperty("user.dir")+"\\"+getFileName();
                FileInputStream archivo=null;
                boolean exists = (new java.io.File(ruta)).exists(); 
                if (exists) { 
                    try { archivo = new FileInputStream(ruta); }
                    catch (IOException e) { 
                        System.out.println(e); 
                        Logueador.getInstance().agregaAlLog("No se pudo abrir "+ruta); 
                    }
                }
                else {
                    try { java.io.File file = new java.io.File(ruta);
                          // Create file if it does not exist
                          boolean success = file.createNewFile();
                          boolean sePuedeLeer = file.canRead();
                          try { archivo = new FileInputStream(ruta); 
                                // Creamos el builder basado en SAX      
                                SAXBuilder builder = new SAXBuilder();  
                                // Construimos el arbol DOM
                                //setDocCcf(builder.build(archivo));
                                setDocBrokerConfig(new Document());
                                // Le agregamos el root element que corresponda y lo guardamos
                                Element rootElement = new Element("ConfiguracionIbape");
                                rootElement.addContent(armaNuevoElementConfig());
                                getDocBrokerConfig().addContent(rootElement);
                                guardaConfiguracion();
                                setDocBrokerConfig(null);
                          }
                          catch (IOException e) { 
                              System.out.println(e); 
                              Logueador.getInstance().agregaAlLog("No se pudo abrir "+ruta); 
                          }
                    } catch (IOException e) {
                        System.out.println(e); 
                        Logueador.getInstance().agregaAlLog("No se pudo crear "+ruta);
                    }
                 }
             return archivo;   
        }

    public boolean guardaConfiguracion() { 
            boolean sePudo=false;
            // Vamos a serializar el XML. Lo primero es obtener el formato de salida  
            // Partimos del "Formato bonito", aunque también existe el plano y el compacto  
            Format format = Format.getPrettyFormat();  
            // Creamos el serializador con el formato deseado  
            XMLOutputter xmloutputter = new XMLOutputter(format);  
            // Serializamos el document parseado  
            //String docStr = xmloutputter.outputString(docCcf);          
            try{
                FileOutputStream file = new FileOutputStream(getFileName());
                xmloutputter.output(getDocBrokerConfig(),file);
                file.flush();
                file.close();
                //xmloutputter.output(this.pagDTO.getDocCcf(),System.out);
                Logueador.getInstance().agregaAlLog("Se guardaron los cambios en el archivo de configuración");
                //setNuevoCcf(false);
                //leeArchivoConfig();
                //leeDocYactualizaGui();                
                sePudo=true;
            }catch(Exception e){
                System.out.println(e.toString());
                Logueador.getInstance().agregaAlLog("No se pudieron guardar los datos en el archivo. Error: "+e.toString());
            }
            return sePudo;
    }
    
        
    public void leeDocYactualizaGui() {
        
    }
    
            
    public boolean leeArchivoConfig(){
        boolean sePudo=false;
        //SAXBuilder se encarga de cargar el archivo XML del disco o de un String
        // Creamos el builder basado en SAX      
        SAXBuilder builder = new SAXBuilder();  
        try {
            //abrimos el archivo
            FileInputStream archivo = abreArchivoConfig();
            // Construimos el arbol DOM a partir del fichero xml  y Cargamos el documento
             setDocBrokerConfig(builder.build(archivo));
             // Obtenemos la etiqueta raíz  
             Element raiz = getDocBrokerConfig().getRootElement();  
             // Recorremos los hijos de la etiqueta raíz  
             List<Element> hijosRaiz = raiz.getChildren();  
             for(Element parametro: hijosRaiz){
                // Obtenemos el nombre y su contenido de tipo texto  
                String nombre = parametro.getName();  
                String texto = parametro.getValue();       
                System.out.println("\n Archivo de configuración leido");
                //getModeloListaCcf().addElement(nombre+id+descripcion);
                //getDocBrokerConfig().addElement(elemCcfDto);                
             }   
             sePudo=true;
            }catch (JDOMException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            } catch (IOException ex) {
                Logueador.getInstance().agregaAlLog(ex.toString());
            }       
        return sePudo;        
    }
           
    public Element armaNuevoElementConfig(){
        Element elemConfig = new Element("Parametros");
        
        Element gpsConfig =  new Element("PanelConfiguracion-GPS");
        gpsConfig.setAttribute("Estado","true");
        gpsConfig.setAttribute("ComboPuerto","COM 2");
        gpsConfig.setAttribute("ComboVelocidad","4800");
        gpsConfig.setAttribute("ComboBitsDatos","8");
        gpsConfig.setAttribute("ComboParidad","Ninguno");
        gpsConfig.setAttribute("Autoconexion","false");
        elemConfig.addContent(gpsConfig);
                
        Element sondaConfig =  new Element("PanelConfiguracion-Sonda");
        sondaConfig.setAttribute("Estado","false");
        sondaConfig.setAttribute("ComboPuerto","COM 3");
        sondaConfig.setAttribute("ComboVelocidad","9600");
        sondaConfig.setAttribute("ComboBitsDatos","7");
        sondaConfig.setAttribute("ComboParidad","Ninguno");
        sondaConfig.setAttribute("Autoconexion","false");
        elemConfig.addContent(sondaConfig);
        
        Element lanConfig =  new Element("PanelConfiguracion-LAN");
        lanConfig.setAttribute("Estado","false");
        lanConfig.setAttribute("ruta","Complete este campo con la ruta...");
        lanConfig.setAttribute("Autoconexion","false");
        elemConfig.addContent(lanConfig);

        Element unidades =  new Element("PanelConfiguracion-Unidades");
        unidades.setAttribute("ComboDistancia","Kilometros");
        unidades.setAttribute("ComboVelocidad","Km/h");
        unidades.setAttribute("ComboTemp","Celcius");        
        elemConfig.addContent(unidades);

        Element historico =  new Element("PanelConfiguracion-Historico");
        historico.setAttribute("GuardarDatosGpsSonda","true");
        historico.setAttribute("GuardarDatosProcImg","false");        
        historico.setAttribute("GuardarDatosConfigSonda","true");        
        elemConfig.addContent(historico);
        
        Element alertas =  new Element("PanelAlertas");
        alertas.setAttribute("Estado","true");
        elemConfig.addContent(alertas);

        return elemConfig;
    }

    public Element armaElementConfigTrayendoDelGUI(){
        Element elemConfig = new Element("Parametros");
        
        //Traemos los valores actuales del Panel Configuracion
        gui.PanelOpcConfiguracion i = gui.PanelOpcConfiguracion.getInstance();
        gui.PanelOpcCampanias c = gui.PanelOpcCampanias.getInstance();
        Element gpsConfig =  new Element("PanelConfiguracion-GPS");
        gpsConfig.setAttribute("Estado",String.valueOf(i.getChkEstadoGps().isSelected()));
        gpsConfig.setAttribute("ComboPuerto",i.getComboPuertoGps().getSelectedItem().toString());
        gpsConfig.setAttribute("ComboVelocidad",i.getComboVelocidadGps().getSelectedItem().toString());
        gpsConfig.setAttribute("ComboBitsDatos",i.getComboBitsDatosGps().getSelectedItem().toString());
        gpsConfig.setAttribute("ComboParidad",i.getComboParidadGps().getSelectedItem().toString());
        gpsConfig.setAttribute("Autoconexion",String.valueOf(i.getChkAutoConectaGps().isSelected()));
        elemConfig.addContent(gpsConfig);
                
        Element sondaConfig =  new Element("PanelConfiguracion-Sonda");
        sondaConfig.setAttribute("Estado",String.valueOf(i.getChkEstadoSonda().isSelected()));
        sondaConfig.setAttribute("ComboPuerto",i.getComboPuertoSonda().getSelectedItem().toString());
        sondaConfig.setAttribute("ComboVelocidad",i.getComboVelocidadSonda().getSelectedItem().toString());
        sondaConfig.setAttribute("ComboBitsDatos",i.getComboBitsDatosSonda().getSelectedItem().toString());
        sondaConfig.setAttribute("ComboParidad",i.getComboParidadSonda().getSelectedItem().toString());
        sondaConfig.setAttribute("Autoconexion",String.valueOf(i.getChkAutoConectaSonda().isSelected()));
        elemConfig.addContent(sondaConfig);
        
        Element lanConfig =  new Element("PanelConfiguracion-LAN");
        lanConfig.setAttribute("Estado",String.valueOf(i.getChkEstadoLan().isSelected()));
        lanConfig.setAttribute("ruta",i.getCampoRutaHistorico().getText());
        gpsConfig.setAttribute("Autoconexion",String.valueOf(i.getChkAutoConectaLan().isSelected()));
        elemConfig.addContent(lanConfig);

        Element unidades =  new Element("PanelConfiguracion-Unidades");
        unidades.setAttribute("ComboDistancia",i.getComboDistancia().getSelectedItem().toString());
        unidades.setAttribute("ComboVelocidad",i.getComboVelocidad().getSelectedItem().toString());
        unidades.setAttribute("ComboTemp",i.getComboTemp().getSelectedItem().toString());
        elemConfig.addContent(unidades);

        Element historico =  new Element("PanelConfiguracion-Historico");
        historico.setAttribute("GuardarDatosGpsSonda",String.valueOf(c.getChkHistoricoGpsSonda().isSelected()));
        historico.setAttribute("GuardarDatosProcImg",String.valueOf(c.getChkHistoricoPeces().isSelected()));
        historico.setAttribute("GuardarDatosConfigSonda",String.valueOf(c.getChkHistoricoSondaSets().isSelected()));
        elemConfig.addContent(historico);
        
        Element alertas =  new Element("PanelConfiguracion-Alertas");
        alertas.setAttribute("Estado",String.valueOf(gui.PanelOpcAlertas.getInstance().getChkAlertas().isSelected()));
        elemConfig.addContent(alertas);

        return elemConfig;
    }
    
    public boolean actualizaDatosPanelConfig_Gps(String estado, String comboPuerto, 
            String comboVelocidad, String comboBitsDatos,String comboParidad,String autoconexion){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelConfiguracion-GPS").setAttribute("Estado",estado);
            if (comboPuerto.length()<8){ //para no guardar el estado de lectura de puertos
                parametros.getChild("PanelConfiguracion-GPS").setAttribute("ComboPuerto",comboPuerto);
            }
            parametros.getChild("PanelConfiguracion-GPS").setAttribute("ComboVelocidad",comboVelocidad);
            parametros.getChild("PanelConfiguracion-GPS").setAttribute("ComboBitsDatos",comboBitsDatos);
            parametros.getChild("PanelConfiguracion-GPS").setAttribute("ComboParidad",comboParidad);
            parametros.getChild("PanelConfiguracion-GPS").setAttribute("Autoconexion",autoconexion);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }                
        return sePudo;
    }
    
    public boolean actualizaDatosPanelConfig_Sonda(String estado, String comboPuerto, 
            String comboVelocidad, String comboBitsDatos,String comboParidad,String autoconexion){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelConfiguracion-Sonda").setAttribute("Estado",estado);
            if (comboPuerto.length()<8){ //para no guardar el estado de lectura de puertos
                parametros.getChild("PanelConfiguracion-Sonda").setAttribute("ComboPuerto",comboPuerto);
            }
            parametros.getChild("PanelConfiguracion-Sonda").setAttribute("ComboVelocidad",comboVelocidad);
            parametros.getChild("PanelConfiguracion-Sonda").setAttribute("ComboBitsDatos",comboBitsDatos);
            parametros.getChild("PanelConfiguracion-Sonda").setAttribute("ComboParidad",comboParidad);        
            parametros.getChild("PanelConfiguracion-Sonda").setAttribute("Autoconexion",autoconexion);        
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }
    
    public boolean actualizaDatosPanelConfig_Lan(String estado, String ruta,String autoconexion){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelConfiguracion-LAN").setAttribute("Estado",estado);
            parametros.getChild("PanelConfiguracion-LAN").setAttribute("ruta",ruta);
            parametros.getChild("PanelConfiguracion-LAN").setAttribute("Autoconexion",autoconexion);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    

    public boolean actualizaDatosPanelConfig_Unidades(String comboDistancia,
            String comboVelocidad, String comboTemp){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelConfiguracion-Unidades").setAttribute("ComboDistancia",comboDistancia);
            parametros.getChild("PanelConfiguracion-Unidades").setAttribute("ComboVelocidad",comboVelocidad);
            parametros.getChild("PanelConfiguracion-Unidades").setAttribute("ComboTemp",comboTemp);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    

    public boolean actualizaDatosPanelConfig_Historico(String guardarDatosGpsSonda,
            String guardarDatosProcImg, String guardarDatosConfigSonda){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelConfiguracion-Historico").setAttribute("GuardarDatosGpsSonda",guardarDatosGpsSonda);
            parametros.getChild("PanelConfiguracion-Historico").setAttribute("GuardarDatosProcImg",guardarDatosProcImg);
            parametros.getChild("PanelConfiguracion-Historico").setAttribute("GuardarDatosConfigSonda",guardarDatosConfigSonda);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }

    public boolean actualizaDatosPanelAlertas(String estado){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelAlertas").setAttribute("Estado",estado);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    
    
    public Document getDocBrokerConfig() {
        return docBrokerConfig;
    }

    public void setDocBrokerConfig(Document docBrokerConfig) {
        this.docBrokerConfig = docBrokerConfig;
    }   
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
   }    
        
    public static BrokerConfig getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new BrokerConfig();          
       }
       return unicaInstancia;
    }        
}
