/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.chainsaw.Main;


//import javax.swing.text.Document;


/**
 *
 * @author emmmau
 */
public class generadorPDF {

 private File ruta_destino=null;
 private Font fuenteRojo25= new Font(Font.getFamily("ARIAL"),25,Font.BOLDITALIC,BaseColor.RED);
 private Date fechaHoy = new Date();
 private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public generadorPDF(){
    }

    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
    public void crear_PDF(String titulo,String ubicacion,String fecha,String barco,String capitan,String nombreCampania,String descripcion,String fechaIniciostring,String fechaFinstring){
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
                } catch (BadElementException ex) {
                    Logger.getLogger(generadorPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(generadorPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(generadorPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
	            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP );
	            mipdf.add(im);
//                mipdf.add(new Paragraph(formato.format(fechaHoy)));
//                mipdf.add(new Paragraph(titulo, fuenteRojo25));
                mipdf.add(new Paragraph(ubicacion+","+fecha)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(vacio)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(barco)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(capitan)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(descripcion)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(fechaIniciostring)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(fechaFinstring)); // se añade el contendio del PDF
                mipdf.add(new Paragraph(nombreCampania)); // se añade el contendio del PDF
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
