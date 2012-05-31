/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.gisModule;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.Color;

/**
 *
 * @author emmmau
 */
public class OperacionesBasicas {
    static OperacionesBasicas unicaInstancia;
    private Detector detector;
    private BufferedImage imagenOriginal;
    private int ancho;
    private int alto;

    private OperacionesBasicas(){
        inicializar();

    }

    public static OperacionesBasicas getInstance(){
        if (unicaInstancia == null) {
           unicaInstancia = new OperacionesBasicas();
        }
        return unicaInstancia;
    }

    public boolean grabarImagen (BufferedImage imagen){
        boolean sepudo = false;
        try {
            ImageIO.write(imagen, "jpg", new File("imgs\\imagen.tmp"));
            sepudo = true;
        } catch (IOException ex) {
            Logger.getLogger(OperacionesBasicas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sepudo;
    }

    public BufferedImage cargarImagen (String rutaImagen) {
        getDetector().obtenerImagen(rutaImagen);
        return getDetector().getImagenOriginal();
    }

    /**
     * @return the detector
     */
    public Detector getDetector() {
        return detector;
    }

    /**
     * @param detector the detector to set
     */
    public void setDetector(Detector detector) {
        this.detector = detector;
    }

    private void inicializar() {
        setDetector(new Detector());
    }

    public void obtenerImagen(String ruta){
		try {
			setImagenOriginal(ImageIO.read(new File(ruta)));

		}catch (IOException e) {
			e.printStackTrace();
		}

		setAncho(getImagenOriginal().getWidth());
		setAlto(getImagenOriginal().getHeight(null));
	}

    public static void main(String[] args){

        int cantPeces = OperacionesBasicas.getInstance().cuantosPecesHay("imgs\\img1.jpg");

//        //Cargamos  la imagen en un objeto BufferImage
//        OperacionesBasicas.getInstance().obtenerImagen("imgs\\img1.jpg");
//        //Creamos los filtro para esta imagen
//        Filtros filtros = new Filtros(getInstance().getAncho(),getInstance().getAlto());
//        //metodo con ancho de 550 recorta imagen
//        //Filtros filtros = new Filtros(getInstance().getAncho(),550);
//        //La erosionamos
//        BufferedImage imgProcesada = filtros.erode(getInstance().getImagenOriginal());
//        //Al resultado lo binarizamos con un umbral de 20
//        imgProcesada = filtros.Binarizacion(imgProcesada, 20);
//
//        Segmentacion segmentacion = new Segmentacion(getInstance().getAncho(),getInstance().getAlto());
//        imgProcesada = segmentacion.Bordes(imgProcesada);
//        //grabamos la imagen en disco
//        getInstance().grabarImagen(imgProcesada);
    }

    public int cuantosPecesHay(String rutaImg){
        int cantPeces = 0;

        //Leemos la imagen con obtenerImagen()
        OperacionesBasicas.getInstance().obtenerImagen(rutaImg);
        //Creamos los filtros para la imagen con la clase Filtros
        Filtros filtros = new Filtros(getInstance().getAncho(), getInstance().getAlto());
        //La erosionamos con Filtros.erode()
        BufferedImage imgProcesada = filtros.erode(getImagenOriginal());
        //Al resultado lo binarizamos con el umbral que corresponda filtros.Binarizacion(imgProcesada, 20
        imgProcesada = filtros.Binarizacion(imgProcesada, 20);
        //Creamos la segmentacion para esta imagen con la clase Segmentacion
      //  Segmentacion segmentacion = new Segmentacion(getInstance().getAncho(),getInstance().getAlto());
        //Obtenemos bordes de la imagen binarizada segmentacion.Bordes(imgProcesada)
      //  imgProcesada = segmentacion.Bordes(imgProcesada);

        imgProcesada = filtros.erode(imgProcesada);
        getInstance().grabarImagen(imgProcesada);

        //Armo el arreglo de la posicion del fondo en todo el ancho de la imagen.
        ArrayList<Integer> fondo = buscaFondo(imgProcesada);

        //buscaMarcas(fondo)


        return cantPeces;
    }

    public ArrayList<Integer> buscaFondo (BufferedImage img) {

        ArrayList<Integer> fondo = new ArrayList<Integer>();
        int ancho = img.getWidth();
		int alto = img.getHeight();
//        int valorRGB;
//		int valoresRGB[] = new int[3];
//		float valoresHSB[] = new float[3];
		Colores col = new Colores();
//		int negro = col.obtieneNumeroColor("Negro");
//		int marron = col.obtieneNumeroColor("Marron");
//        int rgbs[];
//	    float hsb[];
//        rgbs = new int[3];
//        int value = 0;
//        hsb = new float[3];

        boolean noencontrofondo = true;
        int contAncho = 1;
        int contAlto = alto;



            while (contAncho<ancho)  {


                contAlto = alto;
			    while((contAlto>0) && (noencontrofondo=true)) {

             //   if compara("Blanco") {
                    Object e2 = col.getColoresMap().get(col.obtieneColor(img.getRGB(contAncho, contAlto)));

                    boolean e = (e2.equals("Blanco"));

                    
                


                contAlto--;
                }

            contAncho++;
            }

            
        

//				if(col.obtieneColor(valorRGB)!=negro){
//					if(col.obtieneColor(imagen.getRGB(contAncho,contAlto))!=marron){

        //Recorremos la imagen de abajo a arriba y de izquierda a derecha en busqueda del borde. Para esto haremos lo sigueinte:
            //Creamos un Vector del tamanio de imagen.getAncho()
   //     ArrayList<Integer> fondo = new ArrayList();
            //Obtenemos el valor RGB del pixel que esta en x=0,y=0
            //Si ese valor RGB es blanco, leo el valor RGB del pixel que esta en x=0,y=y+1
            //Si ese valor RGB no es blanco, guardo el valor y en  Vector[x] (Altura del borde)
            //Esto lo repetimos en un while de dos condiciones and:
                //1:No haber encontrado el borde (Vble booleana)
                //2: y <= imagen.getAlto()
         //Esto  lo repetimos hasta llegar a x = imagen.getAncho() incluido
        //Ahora tenemos en el Vector la posicion del fondo de toda la imagen
        


        return fondo;
    }
//
//      public boolean compara(String color){
//        Colores col = new Colores();
//        boolean bol=false;
//        if (col.getColoresMap().get(col.obtieneColor(img.getRGB(contAncho, contAlto))).equals(color))
//            return true;
//        else return false;
//
//    }

    public ArrayList<modelo.dataManager.Marca> buscaMarcas(ArrayList<Integer> fondo){
        ArrayList<modelo.dataManager.Marca> marcas = new ArrayList<modelo.dataManager.Marca>();
        //Recorremos la imagen de arriba hacia abajo y de izqierda a derecha en busqueda de las marcas. Para esto haremos lo siguiente:
            //Obtenemso el valor RGB del pixel que esta en x = 0,y=imagen.getAlto()
            //Si ese valor RGB es blanco, leo el valor RGB del pixel que esta en x=0,y=y-1
            //Si ese valor RGB no es blanco y >= fondo[x], hay marca
            //Esto lo repetimos en un while de una condicion:
                //1:No haber encontrado el borde (Vble booleana)
         //Esto  lo repetimos hasta llegar a x = imagen.getAncho() incluido
        return marcas;

    }
    /**
     * @return the imagenOriginal
     */
    public BufferedImage getImagenOriginal() {
        return imagenOriginal;
    }

    /**
     * @param imagenOriginal the imagenOriginal to set
     */
    public void setImagenOriginal(BufferedImage imagenOriginal) {
        this.imagenOriginal = imagenOriginal;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.alto = alto;
    }
}
