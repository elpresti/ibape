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
//        getInstance().grabarImagen(imgProcesada);
        //Al resultado lo binarizamos con el umbral que corresponda filtros.Binarizacion(imgProcesada, 20
        imgProcesada = filtros.Binarizacion(imgProcesada, 20);
        imgProcesada = filtros.dilate(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);
        //Creamos la segmentacion para esta imagen con la clase Segmentacion
//        Segmentacion segmentacion = new Segmentacion(getInstance().getAncho(),getInstance().getAlto());
        getInstance().grabarImagen(imgProcesada);
        //Obtenemos bordes de la imagen binarizada segmentacion.Bordes(imgProcesada)
//        imgProcesada = segmentacion.Bordes(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);
        
//        imgProcesada = filtros.erode(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);

        //Armo el arreglo de la posicion del fondo en todo el ancho de la imagen.
       // ArrayList<Integer> fondo = buscaFondo(imgProcesada);
        int fondo[] = new int[967];
        fondo = buscaFondoMejorado(imgProcesada);
        getInstance().grabarImagen(dibujaFondo(imgProcesada, fondo));
        //buscaMarcas(fondo)


        return cantPeces;
    }

    public int[] buscaFondo (BufferedImage img) {

       // ArrayList<Integer> fondo = new ArrayList<Integer>();
        
        ancho = img.getWidth();
		alto = img.getHeight();
//        int valorRGB;
//		int valoresRGB[] = new int[3];
//		float valoresHSB[] = new float[3];
		Colores col = new Colores();
		int fondo[] = new int[967];
//        int negro = col.obtieneNumeroColor("Negro");
//		int marron = col.obtieneNumeroColor("Marron");
//        int rgbs[];
//	    float hsb[];
//        rgbs = new int[3];
//        int value = 0;
//        hsb = new float[3];

        boolean noencontrofondo = true;
        int contAncho = 1;      //maximo 966
        int contAlto = alto-2;    //maximo 635


          

            while (contAncho<ancho )  {

//              Object e = col.obtieneColor(img.getRGB(contAncho, contAlto));
//                if (!(col.getColoresMap().get(e).equals("Negro"))) {
//                System.out.println(col.getColoresMap().get(e) + "ancho " + contAncho + "alto  " + contAlto);
//
                contAlto = alto-2;
                noencontrofondo=true;

                while((contAlto>0) && (noencontrofondo==true)) {

                    int e = col.obtieneColor(img.getRGB(contAncho, contAlto));

 
                     if ((col.getColoresMap().get(e).equals("Negro"))){
               
                            if (((contAncho==1) || ((Math.abs(contAlto-fondo[contAncho-1]))) <5)) {
                                noencontrofondo=false;
                             }
                    }
                    contAlto--;
                }
                if (contAlto <5) {
                   fondo[contAncho]=fondo[contAncho-1];

                }
                else {
                   fondo[contAncho]=contAlto+1;
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

    public int[] buscaFondoMejorado(BufferedImage img) {

        ancho = img.getWidth();
        alto = img.getHeight();
        Colores col = new Colores();
        int fondo[] = new int[ancho];
        boolean noencontrofondo = true;
        int contAncho = 1;      //maximo 966
        int contAlto = alto - 2;    //maximo 635

            while ((contAlto > 0) && (noencontrofondo == true)) {

                int e = col.obtieneColor(img.getRGB(contAncho, contAlto));
                if ((col.getColoresMap().get(e).equals("Negro"))) {
                    noencontrofondo = false;

                }
                contAlto--;
            }
        fondo[contAncho] = contAlto + 2;
        contAncho++;        
        int limSup=0;
        int limInf=0;

        while (contAncho < ancho) {
            limSup=fondo[contAncho-1]-5;
            limInf=fondo[contAncho-1]+5;
            if (todoFondo(img, contAncho, fondo[contAncho-1],limSup, limInf,col)) {
                fondo[contAncho] = limSup;
            } else {
                if (hayHueco(img, contAncho, fondo[contAncho-1], limSup, limInf, col)) {
                    fondo[contAncho] = limInf;
                } else {
                    if (hayBlancoDondeEstoy(img, contAncho, fondo[contAncho-1],col)) {
                        if (hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup, limInf, col) != -1) {
                            fondo[contAncho] = hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup, limInf, col);
                        } else {
                            fondo[contAncho] = fondo[contAncho - 1];
                        }
                    } else {
                        if (hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limSup, limInf, col) != -1) {
                            fondo[contAncho] = hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limSup, limInf, col);
                        } else {
                            fondo[contAncho] = fondo[contAncho - 1];
                        }
                    }
                }
            }
            contAncho++;
        }
        return fondo;
    }

    public int hayNegroArriba(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean noencontrofondo = true;
        alto--;
        while ((alto>limSup) && (noencontrofondo == true)) {
            int e = col.obtieneColor(img.getRGB(ancho, alto));
            if ((col.getColoresMap().get(e).equals("Negro"))) {
                noencontrofondo = false;
            }
            alto--;
        }
        if (noencontrofondo==true) {
            return -1;
        } else{
                alto=alto+2;
                return alto;
              }
    }

    public boolean hayNegroDondeEstoy(BufferedImage img, int ancho, int alto, Colores col){
        int e = col.obtieneColor(img.getRGB(ancho, alto));
        if ((col.getColoresMap().get(e).equals("Negro"))) {
               return true;
        } else return false;
    }

    public int hayNegroAbajo(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean noencontrofondo = true;
        alto++;
        while ((alto<limInf) && (noencontrofondo == true)) {
            int e = col.obtieneColor(img.getRGB(ancho, alto));
            if ((col.getColoresMap().get(e).equals("Negro"))) {
                noencontrofondo = false;
            }
            alto++;
        }
        if (noencontrofondo==true) {
            return -1;
        } else{
                alto=alto-2;
                return alto;
              }
    }

    public int hayBlancoArriba(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean noencontrofondo = true;
        alto--;
        while ((alto > limSup) && (noencontrofondo == true)) {
            int e = col.obtieneColor(img.getRGB(ancho, alto));
            if ((col.getColoresMap().get(e).equals("Blanco"))) {
                noencontrofondo = false;
            }
            alto--;
        }
        if (noencontrofondo == true) {
            return -1;
        } else {
                alto++;
            return alto;
        }
    }

    public boolean hayBlancoDondeEstoy(BufferedImage img, int ancho, int alto, Colores col){
        int e = col.obtieneColor(img.getRGB(ancho, alto));
        if ((col.getColoresMap().get(e).equals("Blanco"))) {
               return true;
        } else return false;
    }

    public int hayBlancoAbajo(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean noencontrofondo = true;
        alto++;
        while ((alto<limInf) && (noencontrofondo == true)) {
            int e = col.obtieneColor(img.getRGB(ancho, alto));
            if ((col.getColoresMap().get(e).equals("Blanco"))) {
                noencontrofondo = false;
            }
            alto++;
        }
        if (noencontrofondo==true) {
            return -1;
        } else { alto--;
            return alto;
        }
    }

    public boolean hayHueco(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean salida=false;
        if ((hayNegroArriba(img, ancho, alto, limSup, limInf, col)!=-1) && (hayNegroDondeEstoy(img, ancho, alto, col))&&(hayNegroAbajo(img, ancho, alto, limSup, limInf, col)!=-1)){
            salida=true;
        }
        return salida;
        
    }
    public boolean todoFondo(BufferedImage img, int ancho, int alto, int limSup, int limInf, Colores col){
        boolean salida=false;
        if ((hayBlancoDondeEstoy(img, ancho, alto, col))&& (hayNegroArriba(img, ancho, alto, limSup, limInf, col)==-1)&&(hayNegroAbajo(img, ancho, alto, limSup, limInf, col)==-1)){
            salida=true;
        }
        return salida;

    }

    public ArrayList<Integer> buscaFondoSegunRango (BufferedImage img) {
        ArrayList<Integer> fondo = new ArrayList();
        //--- metodo pendiente ---
        return fondo;
    }
    
    
    
    public boolean estaEnRangoDelAnterior(BufferedImage img, int pixelY,int valorAcomparar ){
        boolean estaEnElMedio=false;
        int limiteSuperior = (int) (valorAcomparar + (img.getHeight() * 0.01)); // usamos como rango un 1% del alto de la imagen
        int limiteInferior = (int) (valorAcomparar - (img.getHeight() * 0.01)); //estan al revez xq se lee de arriba hacia abajo
        if ((pixelY>limiteInferior) && (pixelY<limiteSuperior)){
            estaEnElMedio=true;
        }
        return estaEnElMedio;
    }
//
//      public boolean compara(String color, BufferedImage img){
//        Colores col = new Colores();
//        boolean bol=false;
//        if (col.getColoresMap().get(col.obtieneColor(img.getRGB(contAncho, contAlto))).equals(color))
//            return true;
//        else return false;
//
//    }

    public int promedio(int[] fondo){
        int cont = 1;       // contador
        int suma = 0;    // suma


        while (fondo[cont] != 0 ) {
            suma += suma+fondo[cont];
            cont++;
        }

        // promedio
        int promedio = suma / (cont-1);

     return promedio;
    }

    public BufferedImage dibujaFondo (BufferedImage img, int[] fondo){
         int cont = 0;      // contador
         int colorRojo = new Color (255,0,0).getRGB();        
         while (cont < fondo.length ) {
            img.setRGB(cont, fondo[cont],colorRojo  );
            cont++;
        }
        return img;
    }


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
