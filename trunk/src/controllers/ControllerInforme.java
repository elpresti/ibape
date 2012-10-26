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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
import modelo.dataCapture.Sistema;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import org.apache.log4j.chainsaw.Main;
import persistencia.BrokerCajon;
import persistencia.BrokerDbMapa;
import persistencia.BrokerDbMapaHistorico;
import persistencia.BrokerEspecie;
import persistencia.BrokerHistoricoPunto;
import persistencia.BrokerHistoricoSondaSet;
import persistencia.Logueador;


/**
 *
 * @author emmmau
 */
public class ControllerInforme {
    static ControllerInforme unicaInstancia;
            
    private ControllerInforme(){
        inicializador();
    }
    
    public static ControllerInforme getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerInforme();
       return unicaInstancia;
    }

    private void inicializador() {
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

    public void abrirPdfGenerado(String ruta){
        try{
            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+ruta); 
            p.waitFor();
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("abrirPdfGenerado(): "+e.toString());
        }
    }
    
    public boolean generaInforme(int idCampania,boolean chkBarco,boolean chkCampana,boolean chkLances,boolean chkCajones,boolean chkCatPois ){
        boolean sepudo = false;
        try {
            ArrayList<modelo.dataManager.Lance> lances = new ArrayList<modelo.dataManager.Lance>();
            ArrayList<modelo.dataManager.CategoriaPoi> catPois = new ArrayList<modelo.dataManager.CategoriaPoi>();
            modelo.dataManager.Campania campania = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampania);
            if (chkLances) {
                lances = persistencia.BrokerLance.getInstance().getLancesCampaniaFromDB(idCampania);
            }else{
                lances = null;
            }
            if (chkCatPois) {
                catPois = ControllerHistorico.getInstance().getCatPOISDeUnaCampFromDB(idCampania);
            }else{
                catPois = null;
            }
            generadorPDF pdf = new generadorPDF();
            pdf.crear_PDF(idCampania,"Informe de Campaña", //titulo del informe
                    Calendar.getInstance().getTime(), //fecha de hoy
                    campania.getBarco(), //nombre del barco
                    campania.getCapitan(), //nombre del capitan
                    campania.getDescripcion(),
                    campania.getFechaInicio(), //fecha de inicio de la campania
                    campania.getFechaFin(), //fecha de fin de la campania
                    lances, //ArrayList de lances de la campaña
                    catPois); //ArrayList de Categoria de POIs de la campaña
            sepudo = true;
            //pdf.crear_PDF(TITULO.getText(), AUTOR.getText(), ASUNTO.getText(), CLAVE.getText(), TEXTO.getText());
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog("ControllerInforme.generaInforme(): " + e.toString());
        }
        return sepudo;
    }


}
class generadorPDF {
 private File ruta_destino=null;
 private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
    public generadorPDF(){
    }
    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
    public void crear_PDF(int idCamp, String titulo,Date fechaHoy,String barco,String capitan,String descripcion,Date fechaInicio,Date fechaFin,
            ArrayList<modelo.dataManager.Lance> lances, ArrayList<modelo.dataManager.CategoriaPoi> catPois){
        //abre ventana de dialogo "guardar"
        Colocar_Destino();
        //si destino es diferente de null
        if(this.ruta_destino!=null){
            try {
                Document mipdf = new Document() {};//se crea instancia del documento
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino + ".pdf"));//se establece una instancia a un documento pdf
                mipdf.open();// se abre el documento
                Image im=null;
                try {
                    im = Image.getInstance(getClass().getResource("/imgs/logoIbapeChico.png"));
               } catch (Exception ex) {
                    Logueador.getInstance().agregaAlLog("generadorPDF.crear_PDF(): "+ex.toString());
               }
        //     java.awt.Image img = (java.awt.Image)im.getImage();
        //     Image imgFinal = Image.getInstance(img);
	       im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP );
	       mipdf.add(im);
               mipdf.add(new Paragraph(titulo, new Font(Font.getFamily("ARIAL"),25,Font.BOLDITALIC,BaseColor.RED)));  //fuente rojo
               mipdf.add(new Paragraph(formato.format(fechaHoy))); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph(" ")); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Barco: "+barco)); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Capitán: "+capitan)); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Descripción: "+descripcion)); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Fecha de Inicio: "+formato.format(fechaInicio))); // se añade el contendio del PDF
               if (fechaFin == null){
                   mipdf.add(new Paragraph("Fecha de Fin: -- EN CURSO -- "));
               }else{
                   mipdf.add(new Paragraph("Fecha de Fin: "+formato.format(fechaFin))); // se añade el contendio del PDF
               }
               mipdf.add(new Paragraph(" "));
               mipdf.add(new Paragraph("Lances:", new Font(Font.getFamily("ARIAL"),16,Font.BOLD,BaseColor.BLACK)));
               if (lances != null){
                   if (lances.size()>0){
                       insertaLancesEnInforme(mipdf, lances);
                   }else{
                       mipdf.add(new Paragraph("-- No hubo --"));
                   }
               }else{
                   mipdf.add(new Paragraph(" ")); //no se pidió incluir este item en el informe
               }
               mipdf.add(new Paragraph(" "));
               if (catPois != null){
                   if (catPois.size()>0){
                       mipdf.add(new Paragraph("Cantidad de POIs registrados (agrupados por Categoria):", 
                               new Font(Font.getFamily("ARIAL"),16,Font.BOLD,BaseColor.BLACK)));
                       mipdf.add(new Paragraph(" "));
                       insertaCatPoisEnInforme(mipdf, catPois, idCamp);
                   }else{
                       mipdf.add(new Paragraph("POIs registrados: No hubo"));
                   }
               }else{
                   mipdf.add(new Paragraph(" ")); //no se pidió incluir este item en el informe
               }    
               mipdf.close(); //se cierra el PDF
               int opcion = JOptionPane.showOptionDialog(null,
                            "Documento PDF creado con exito",
                            "Seleccione una opcion",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{"Aceptar", "Abrir PDF"}, // null para YES, NO y CANCEL
                            null);
                if (opcion==1) {
                    ControllerInforme.getInstance().abrirPdfGenerado(this.ruta_destino.getAbsolutePath());
                }
            } catch (Exception ex) {
                Logueador.getInstance().agregaAlLog("generadorPDF.crear_PDF(): "+ex.toString());
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
    private boolean insertaLancesEnInforme(Document mipdf, ArrayList<modelo.dataManager.Lance> lances) {
        boolean sePudo=false;
        try{
            Image im = null;
            String rutaIconoLances = Sistema.getInstance().getRutaIconosCatPois();
            rutaIconoLances+="\\"+modelo.dataManager.AdministraCatPoi.getInstance().getIconoFileNameCatLances();
            for (modelo.dataManager.Lance lance : lances){
                try {
                    im = Image.getInstance(rutaIconoLances);
                    im.scaleAbsolute(22, 22);
                }catch(Exception ex){
                    Logueador.getInstance().agregaAlLog("generadorPDF.insertaLancesEnInforme(): "+ex.toString());
                }
                im.setAlignment(Image.TEXTWRAP);
                mipdf.add(im);
                mipdf.add(new Paragraph("   Número de Lance: "+lance.getId()));
                mipdf.add(new Paragraph("   Comentarios del Lance: "+lance.getComentarios()));
                ArrayList<modelo.dataManager.Cajon> cajones = BrokerCajon.getInstance().getCajonesLanceFromDB(lance.getId());
                if (cajones.size()>0){
                    mipdf.add(new Paragraph("   Cantidad de cajones obtenidos: "+BrokerCajon.getInstance().getCajonesFromLance(lance.getId())));
                    for (modelo.dataManager.Cajon cajon : cajones){
                        mipdf.add(new Paragraph("               •  "+BrokerEspecie.getInstance().getEspecieFromDB(cajon.getIdEspecie()).getNombre()+
                                ": "+cajon.getCantidad()));
                    }
                }else{
                    mipdf.add(new Paragraph("   Cantidad de cajones obtenidos: 0"));
                }
                mipdf.add(new Paragraph(" "));
                
                PdfPTable tablaLance = new PdfPTable(3); //tabla de tres columnas
                PdfPCell celda = new PdfPCell(new Phrase(" "));celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                celda = new PdfPCell(new Phrase("Al lanzar la red"));celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                celda = new PdfPCell(new Phrase("Al recoger la red"));celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);                
                
                celda = new PdfPCell(new Phrase("Fecha y hora     "));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                
                celda = new PdfPCell(new Phrase(formato.format(lance.getfYHIni())));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                
                celda = new PdfPCell(new Phrase(formato.format(lance.getfYHFin())));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);                
                
                celda = new PdfPCell(new Phrase("Latitud     "));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                
                celda = new PdfPCell(new Phrase(String.valueOf(lance.getPosIniLat())));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);

                celda = new PdfPCell(new Phrase(String.valueOf(String.valueOf(lance.getPosFinLat()))));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);

                celda = new PdfPCell(new Phrase("Longitud     "));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                
                celda = new PdfPCell(new Phrase(String.valueOf(lance.getPosIniLon())));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);

                celda = new PdfPCell(new Phrase(String.valueOf(lance.getPosFinLon())));celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);celda.setBorderColor(BaseColor.WHITE);
                tablaLance.addCell(celda);
                
                mipdf.add(tablaLance);

                PdfPTable tablaSeparador = new PdfPTable(1);
                PdfPCell separador = new PdfPCell(new Paragraph(" ") );
                separador.setBorder(Rectangle.BOTTOM);tablaSeparador.addCell(separador);
                mipdf.add(tablaSeparador);
                mipdf.add(new Paragraph(" "));
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("generadorPDF.insertaLancesEnInforme(): "+e.toString());
        }
        return sePudo;
    }
    private boolean insertaCatPoisEnInforme(Document mipdf, ArrayList<modelo.dataManager.CategoriaPoi> catPois, int idCamp) {
        boolean sePudo=false;
        try{
           Image im = null;
           String rutaIconoCatPois = "";
           for (modelo.dataManager.CategoriaPoi catPoi : catPois){
                rutaIconoCatPois=Sistema.getInstance().getRutaIconosCatPois()+"\\"+catPoi.getPathIcono();
                try {
                    im = Image.getInstance(rutaIconoCatPois);
                    im.scaleAbsolute(15, 15);
                    im.setAlignment(Image.TEXTWRAP); 
                    mipdf.add(im);
                }catch(Exception ex){
                    Logueador.getInstance().agregaAlLog("generadorPDF.insertaCatPoisEnInforme(): "+ex.toString());
                }
                mipdf.add(new Paragraph("   "));
                mipdf.add(new Paragraph("   "+catPoi.getTitulo()+": "+ControllerHistorico.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCamp,catPoi.getId())));
                mipdf.add(new Paragraph("   "));
           }
           mipdf.add(new Paragraph(" "));
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("generadorPDF.insertaCatPoisEnInforme(): "+e.toString());
        }
        return sePudo;
    }
}