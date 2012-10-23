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
        setIdCampaniaElegida(-1);
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
             SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             ArrayList<modelo.dataManager.Lance> lances = new ArrayList<modelo.dataManager.Lance>();
             ArrayList<modelo.dataManager.CategoriaPoi> catPois = new ArrayList<modelo.dataManager.CategoriaPoi>();
             if (idCampania>=0){
               modelo.dataManager.Campania campania = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampania);
               if (chkLances){
                  lances = persistencia.BrokerLance.getInstance().getLancesCampaniaFromDB(idCampania);
               }
               if (chkCatPois){
                  catPois = ControllerHistorico.getInstance().getCatPOISDeUnaCampFromDB(idCampania);
               }
               generadorPDF pdf=new generadorPDF();
               pdf.crear_PDF("Informe de Campaña", //titulo del informe
                       formato.format(Calendar.getInstance().getTime()), //fecha de hoy
                       campania.getBarco(), //nombre del barco
                       campania.getCapitan(), //nombre del capitan
                       campania.getDescripcion(),
                       formato.format(campania.getFechaInicio()), //fecha de inicio de la campania
                       formato.format(campania.getFechaFin()), //fecha de fin de la campania
                       lances, //ArrayList de lances de la campaña
                       catPois); //ArrayList de Categoria de POIs de la campaña
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
 private Date fechaHoy = new Date();
 private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    public generadorPDF(){
    }
    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
    public void crear_PDF(String titulo,String fecha,String barco,String capitan,String descripcion,String fechaIniciostring,String fechaFinstring,
            ArrayList<modelo.dataManager.Lance> lances, ArrayList<modelo.dataManager.CategoriaPoi> catPois){
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
                    Logueador.getInstance().agregaAlLog("generadorPDF.crear_PDF(): "+ex.toString());
               }
        //     java.awt.Image img = (java.awt.Image)im.getImage();
        //     Image imgFinal = Image.getInstance(img);
	       im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP );
	       mipdf.add(im);
//             mipdf.add(new Paragraph(formato.format(fechaHoy)));
               mipdf.add(new Paragraph(titulo, new Font(Font.getFamily("ARIAL"),25,Font.BOLDITALIC,BaseColor.RED)));  //fuente rojo
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
               mipdf.add(new Paragraph("Descripción: "+descripcion)); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Fecha de Inicio: "+fechaIniciostring)); // se añade el contendio del PDF
               mipdf.add(new Paragraph("Fecha de Fin: "+fechaFinstring)); // se añade el contendio del PDF
               mipdf.add(new Paragraph(""));
               if (lances.size()>0){
                   mipdf.add(new Paragraph("Lances:"));
                   insertaLancesEnInforme(mipdf, lances);
               }else{
                   mipdf.add(new Paragraph("Lances: No hubo"));
               }
               mipdf.add(new Paragraph(""));
               if (catPois.size()>0){
                   mipdf.add(new Paragraph("Resumen de POIs registrados (agrupados por Categoria):"));
                   insertaCatPoisEnInforme(mipdf, catPois);
               }else{
                   mipdf.add(new Paragraph("POIs registrados: No hubo"));
               }
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
    private boolean insertaLancesEnInforme(Document mipdf, ArrayList<modelo.dataManager.Lance> lances) {
        boolean sePudo=false;
        try{
            for (modelo.dataManager.Lance lance : lances){
                mipdf.add(new Paragraph(""));
                mipdf.add(new Paragraph(""));
                mipdf.add(new Paragraph("   Número de Lance: "+lance.getId()));
                mipdf.add(new Paragraph("   Comentarios del Lance: "+lance.getComentarios()));
                mipdf.add(new Paragraph(""));
                mipdf.add(new Paragraph("   Datos al lanzar la red:"));
                mipdf.add(new Paragraph("       Fecha y hora: "+lance.getfYHIni()));
                mipdf.add(new Paragraph("       Longitud: "+lance.getPosIniLon()));
                mipdf.add(new Paragraph("       Latitud: "+lance.getPosIniLat()));
                mipdf.add(new Paragraph(""));
                mipdf.add(new Paragraph("   Datos al recoger la red: "));
                mipdf.add(new Paragraph("       Fecha y hora: "+lance.getfYHFin()));
                mipdf.add(new Paragraph("       Longitud: "+lance.getPosFinLon()));
                mipdf.add(new Paragraph("       Latitud: "+lance.getPosFinLat()));
                ArrayList<modelo.dataManager.Cajon> cajones = BrokerCajon.getInstance().getCajonesLanceFromDB(lance.getId());
                if (cajones.size()>0){
                    mipdf.add(new Paragraph("   Cantidad de cajones obtenidos:"));
                    for (modelo.dataManager.Cajon cajon : cajones){
                        mipdf.add(new Paragraph("       "+BrokerEspecie.getInstance().getEspecieFromDB(cajon.getIdEspecie()).getNombre()+
                                ": "+cajon.getCantidad()));
                    }
                }else{
                    mipdf.add(new Paragraph("   Cantidad de cajones obtenidos: 0"));
                }
            }
            
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("generadorPDF.insertaLancesEnInforme(): "+e.toString());
        }
        return sePudo;
    }
    private boolean insertaCatPoisEnInforme(Document mipdf, ArrayList<modelo.dataManager.CategoriaPoi> catPois) {
        boolean sePudo=false;
        try{
            mipdf.add(new Paragraph(""));
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("generadorPDF.insertaCatPoisEnInforme(): "+e.toString());
        }
        return sePudo;
    }
}

