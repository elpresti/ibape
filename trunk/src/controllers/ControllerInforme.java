/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import gui.PanelHistorico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import org.apache.log4j.chainsaw.Main;
import persistencia.BrokerDbMapa;
import persistencia.BrokerDbMapaHistorico;
import persistencia.BrokerHistoricoPunto;
import persistencia.BrokerHistoricoSondaSet;
import persistencia.Logueador;


/**
 *
 * @author emmmau
 */
public class ControllerInforme {
    static ControllerInforme unicaInstancia;
    private BrokerHistoricoPunto brokerHistoricoPunto;
    private BrokerHistoricoSondaSet brokerHistoricoSondaSet;
    private int idCampaniaElegida;
            
    private ControllerInforme(){
        inicializador();
    }
    
    public static ControllerInforme getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerInforme();
       return unicaInstancia;
    }

    private void inicializador() {
        brokerHistoricoPunto = BrokerHistoricoPunto.getInstance();
        brokerHistoricoSondaSet = BrokerHistoricoSondaSet.getInstance();
        setIdCampaniaElegida(-1);
    }

    public void configuraBrokerHistorico() {
        brokerHistoricoPunto.setGuardaDatosGps(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosSonda(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosPeces(gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().isSelected());
        brokerHistoricoSondaSet.setGuardaDatosSondaSets(gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().isSelected());
    }

    public void cargaGrillaCampanias() {
        gui.PanelOpcInformes.getInstance().vaciaTabla();
        int cantConHistorico=0;
        ArrayList<modelo.dataManager.Campania> campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();
        if ((campanias == null) || (campanias.isEmpty()) ) {
            modelo.dataManager.AdministraCampanias.getInstance().leerCampaniasDeLaDB();
            campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();            
        }
        if ((!(campanias == null)) && (campanias.size() > 0)) {
            // while (), pongo cada objeto Campania en la grilla de campanias                    
            int i = 0;
            boolean tieneHistorico;            
            while (i < campanias.size()) {
                tieneHistorico = (campanias.get(i).getFolderHistorico() != null && campanias.get(i).getFolderHistorico().length() > 0);
                if (tieneHistorico){
                    gui.PanelOpcInformes.getInstance().agregaUnaFilaCampania(
                            campanias.get(i).getId(),
                            campanias.get(i).getDescripcion(),
                            campanias.get(i).getBarco(),
                            campanias.get(i).getCapitan(),
                            campanias.get(i).getEstado(),
                            campanias.get(i).getFechaInicio(),
                            campanias.get(i).getFechaFin(),
                            campanias.get(i).getCantTotalCajones()
                            );
                    cantConHistorico++;
                }
                i++;
            }
        }
        if (cantConHistorico == 0) {
            gui.PanelOpcInformes.getInstance().agregaUnaFilaCampania(-1, "No hay campañas con Historico",
                    null, null, 0, null, null, 0);
            gui.PanelOpcInformes.getInstance().habilitaPanelTablaCampanias(false);
        } else {
            gui.PanelOpcInformes.getInstance().habilitaPanelTablaCampanias(true);
        }
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            gui.PanelOpcInformes.getInstance().marcaCampaniaEnCurso();
        }
    }

    public int getCantPuntosHistoricos(int idCamp){
        return BrokerHistoricoPunto.getInstance().cuantosPuntosTiene(idCamp);
    }        

    public boolean iniciaServerYabreBrowser() {
        boolean sePudo=false;
        try{
            if (ControllerPpal.getInstance().abrirWebServer()){
                if (BrokerDbMapa.getInstance().disparaEjecucion()){
                    if (persistencia.BrokerDbMapaHistorico.getInstance().disparaEjecucion()){                        
                        //do what you want to do after sleeptig
                        modelo.gisModule.Browser.getInstance().setUrlTemp(modelo.gisModule.Browser.getInstance().getUrl()+"/historico.php");
                        modelo.gisModule.Browser.getInstance().start();
                        //modelo.gisModule.Browser.getInstance().abrirPaginaEnPestania(modelo.gisModule.Browser.getInstance().getUrlTemp());
                        sePudo=true;
                    
                    }
                    else{
                        restauraBtnIniciarMapa();
                    }
                }
                else{
                    restauraBtnIniciarMapa();
                }
            }
            else{
                restauraBtnIniciarMapa();
            }
        }
        catch(Exception e){
            restauraBtnIniciarMapa();
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }
    
    public void restauraBtnIniciarMapa(){
        PanelHistorico.getInstance().restauraBtnIniciarMapa();
        PanelHistorico.getInstance().seteaBotonesMapa();    
    }

    public void restauraBtnGraficarDatos(){
        PanelHistorico.getInstance().restauraBtnGraficarDatos();
    }

    public void cargaRecorridoEnMapa(int idCampaniaElegida) {
        setIdCampaniaElegida(idCampaniaElegida);
        if (!BrokerDbMapaHistorico.getInstance().cargarRecorridoDeCamp(getIdCampaniaElegida())){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa");
        }
    }

    public void cargaPoisEnMapa(int idCampaniaElegida, ArrayList<Integer> categoriasSeleccionadas) {
        if (!BrokerDbMapaHistorico.getInstance().cargarPoisDeCamp(idCampaniaElegida,categoriasSeleccionadas)){
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los datos en el Mapa");
        }
    }

    public void actualizaDatosEnGui() {
        controllers.ControllerInforme.getInstance().cargaGrillaCampanias();
        //PanelHistorico.getInstance().inicializaTablaCategoriasPois(); 
        //PanelHistorico.getInstance().seteaBotonesMapa();
    }

    public ArrayList<CategoriaPoi> getCatPOISDeUnaCampFromDB(int idCampaniaElegida) {
        return  persistencia.BrokerCategoriasPOI.getInstance().getCatPOISDeUnaCampFromDB(idCampaniaElegida);
    }

    public Object getCantPOISDeUnaCampSegunCatPoi(int idCampaniaElegida, int idCategoria) {
        return persistencia.BrokerPOIs.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCampaniaElegida, idCategoria);
    }

    /**
     * @return the idCampaniaElegida
     */
    public int getIdCampaniaElegida() {
        return idCampaniaElegida;
    }

    /**
     * @param idCampaniaElegida the idCampaniaElegida to set
     */
    public void setIdCampaniaElegida(int idCampaniaElegida) {
        this.idCampaniaElegida = idCampaniaElegida;
    }

    public void vaciaMapaHistorico() {
        BrokerDbMapaHistorico.getInstance().vaciaMapaHistorico();
    }

    public boolean generaInforme(int idCampania,boolean chkBarco,boolean chkCampana,boolean chkLances,boolean chkCajones,boolean chkCatPois ){
         boolean sepudo=false;
         try{

             SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
             String fecha= (formato.format(Calendar.getInstance().getTime()));
             String barco= "",capitan= "",descripcion= "",fechaIniciostring= "",fechaFinstring="";
             int idCampaniaElegida2 = idCampania;
             if (idCampaniaElegida2>=0){

               if (chkBarco){
                   barco = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaElegida2).getBarco();
                   capitan = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaElegida2).getCapitan();
               }
               if (chkCampana){

                   descripcion = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaElegida2).getDescripcion();
                   Date fechaInicio = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaElegida2).getFechaInicio();
                   Date fechaFin= modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaElegida2).getFechaFin();
                   fechaIniciostring=formato.format(fechaInicio);
                   fechaFinstring=formato.format(fechaFin);
               }
               if (chkLances){
                   persistencia.BrokerLance.getInstance().getLancesCampaniaFromDB(idCampaniaElegida2);
               }
               if (chkCajones){
      //             BrokerCajon.getInstance().getCajonesFromDB().;
      //             getCajonesLanceFromDB(int idLance):ArrayList<modelo.dataManager.Cajon>
               }
               if (chkCatPois){
        //           ControllerHistorico.getInstance().getCatPOISDeUnaCampFromDB(idCampaniaElegida2);
        //           para obtener la cant de puntos de cada categoria usar el método
        //           ControllerHistorico.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCampaniaint,CP.getId());
               }

          generadorPDF pdf=new generadorPDF();
          pdf.crear_PDF("Informe de pesca",fecha,barco,capitan,descripcion,fechaIniciostring,fechaFinstring);
          sepudo = true;
          //pdf.crear_PDF(TITULO.getText(), AUTOR.getText(), ASUNTO.getText(), CLAVE.getText(), TEXTO.getText());

          }
             } catch(Exception e){
             Logueador.getInstance().agregaAlLog("ControllerInforme.generaInforme(): "+e.toString());
         }
         return sepudo;
    }


}
class generadorPDF {

 private File ruta_destino=null;
 private Font fuenteRojo25= new Font(Font.getFamily("ARIAL"),25,Font.BOLDITALIC,BaseColor.RED);
 private Date fechaHoy = new Date();
 private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public generadorPDF(){
    }

    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
    public void crear_PDF(String titulo,String fecha,String barco,String capitan,String descripcion,String fechaIniciostring,String fechaFinstring){
        //abre ventana de dialogo "guardar"
        Colocar_Destino();
        //si destino es diferente de null
        if(this.ruta_destino!=null){
            try {
                // se crea instancia del documento
                Document mipdf = new Document() {};
                // se establece una instancia a un documento pdf
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino + ".pdf"));
                mipdf.open();// se abre el documento
                Image im=null;
                String vacio=" ";
                try {
                    im = Image.getInstance("src\\imgs\\logoIbapeChico.png");
             //         im = new javax.swing.ImageIcon(getClass().getResource("/imgs/logoIbapeChico.png"));
               } catch (Exception ex) {
                    Logueador.getInstance().agregaAlLog("generadorPDF.crear_PDF: "+ex.toString());
                }
        //        java.awt.Image img = (java.awt.Image)im.getImage();
        //        Image imgFinal = Image.getInstance(img);
	            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP );
	            mipdf.add(im);
//                mipdf.add(new Paragraph(formato.format(fechaHoy)));
                mipdf.add(new Paragraph(titulo, fuenteRojo25));
                if (!fecha.isEmpty()){
                    mipdf.add(new Paragraph(fecha)); // se añade el contendio del PDF
                }
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph("Barco: "+barco)); // se añade el contendio del PDF
                mipdf.add(new Paragraph("Capitán: "+capitan)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(descripcion)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(fechaIniciostring)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(fechaFinstring)); // se añade el contendio del PDF
                mipdf.close(); //se cierra el PDF&
                JOptionPane.showMessageDialog(null,"Documento PDF creado con exito");
            } catch (DocumentException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /* abre la ventana de dialogo GUARDAR*/
    public void Colocar_Destino(){
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");
       JFileChooser fileChooser = new JFileChooser();
       fileChooser.setFileFilter(filter);
       int result = fileChooser.showSaveDialog(null);
       if ( result == JFileChooser.APPROVE_OPTION ){
           this.ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
        }
    }
}

