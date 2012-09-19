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
import java.awt.Point;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import modelo.dataCapture.DATatlantis;
import modelo.dataManager.Marca;
import modelo.dataManager.SondaSetHistorico;
import modelo.dataManager.UltimaImgProcesada;
import persistencia.Logueador;


/**
 *
 * @author emmmau
 */
public class OperacionesBasicas {
    static OperacionesBasicas unicaInstancia;
    private Detector detector;
    private BufferedImage imagenOriginal;
    private BufferedImage imgProcesada;
    private BufferedImage imgConFondo;
    private int ancho;
    private int alto;
    private Colores color = new Colores();

    private OperacionesBasicas(){
        inicializar();

    }

    public static OperacionesBasicas getInstance(){
        if (unicaInstancia == null) {
           unicaInstancia = new OperacionesBasicas();
        }
        return unicaInstancia;
    }

    public boolean grabarImagen (BufferedImage imagen, String donde){
        boolean sepudo = false;
        try {
            ImageIO.write(imagen, "jpg", new File(donde));
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

/*   
    public static void main(String[] args) throws IOException{
        //Leemos la imagen con obtenerImagen()
        OperacionesBasicas.getInstance().obtenerImagen("imgs\\img1.jpg");
        if (getInstance().imagenApta(getInstance().imagenOriginal)){
             int cantPeces = OperacionesBasicas.getInstance().cuantosPecesHay(getInstance().imagenOriginal);
             ArrayList<Marca> marcas=OperacionesBasicas.getInstance().buscaMarcas(getInstance().getImgProcesada());
             BufferedImage imgConMarcas = getInstance().dibujaMarcasDetectadas(getInstance().imgProcesada,marcas);
             getInstance().grabarImagen(imgConMarcas,"imgs\\imagenMarcas.tmp");
             BufferedImage imgConMarcasRellena = getInstance().rellenaMarcasDetectadas(imgConMarcas, marcas);
             getInstance().grabarImagen(imgConMarcasRellena,"imgs\\imagenMarcasRellenas.tmp");
             BufferedImage imgCOnFondoYMarcasRellenas = getInstance().rellenaMarcasDetectadas(getInstance().getImgConFondo(), marcas);
             getInstance().grabarImagen(imgCOnFondoYMarcasRellenas,"imgs\\imagenConFondoYMarcasRellenas.tmp");
             //OperacionesBasicas.getInstance().getMarcas(imgSoloMarcas);
            }
    }
*/    
    public int cuantosPecesHay(BufferedImage imgOriginal){
        int cantPeces = 0;
        //setProgresoProcesamiento(0); 
        //Creamos los filtros para la imagen con la clase Filtros
        Filtros filtros = new Filtros(getInstance().getAncho(), getInstance().getAlto());
        //La erosionamos con Filtros.erode()
        BufferedImage imgProcesada = filtros.erode(getImagenOriginal());
//        getInstance().grabarImagen(imgProcesada);
        //Al resultado lo binarizamos con el umbral que corresponda filtros.Binarizacion(imgProcesada, 20
        imgProcesada = filtros.Binarizacion(imgProcesada, 20);
        //setProgresoProcesamiento(1);
        imgProcesada = filtros.dilate(imgProcesada);
        //setProgresoProcesamiento(2);
//        getInstance().grabarImagen(imgProcesada);
        //Creamos la segmentacion para esta imagen con la clase Segmentacion
//        Segmentacion segmentacion = new Segmentacion(getInstance().getAncho(),getInstance().getAlto());
        //getInstance().grabarImagen(imgProcesada,"imgs\\imagen.tmp");
        //Obtenemos bordes de la imagen binarizada segmentacion.Bordes(imgProcesada)
//        imgProcesada = segmentacion.Bordes(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);
        
//        imgProcesada = filtros.erode(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);

        //Armo el arreglo de la posicion del fondo en todo el ancho de la imagen.
       // ArrayList<Integer> fondo = buscaFondo(imgProcesada);
        int fondo[] = new int[imgOriginal.getWidth()];
        fondo = buscaFondo(imgProcesada);
        BufferedImage imgConFondo = dibujaFondo(imgProcesada, fondo);

        getInstance().grabarImagen(imgConFondo,"imgs\\imagenConFondo.tmp");
        BufferedImage imagensinhoras = eliminaHoras(imgConFondo);
        getInstance().grabarImagen(imagensinhoras,"imgs\\imagenSinHoras.tmp");
        try {
            setImgConFondo(ImageIO.read(new File("imgs\\imagenSinHoras.tmp")));
        } catch (IOException ex) {
            Logger.getLogger(OperacionesBasicas.class.getName()).log(Level.SEVERE, null, ex);
        }
       // setImgConFondo();
        getInstance().grabarImagen(eliminaFondo(imgConFondo, fondo),"imgs\\imagenSinFondo.tmp");
 
        setImgProcesada(imgConFondo);
        //setImgProcesada();
        //buscaMarcas(fondo)


        return cantPeces;
    }


    public int[] buscaFondo(BufferedImage img) {
        int[] salida = null;
        try{
            ancho = img.getWidth();
            alto = img.getHeight();
            int fondo[] = new int[ancho];
            boolean noencontrofondo = true;
            int contAncho = 1;      //maximo 966
            int contAlto = alto - 2;    //maximo 635

                while ((contAlto > 0) && (noencontrofondo == true)) {

                    int e = getColor().obtieneColor(img.getRGB(contAncho, contAlto));
                    if ((getColor().getColoresMap().get(e).equals("Negro"))) {
    //                    e =getColor().obtieneColor(img.getRGB(contAncho, contAlto-1));
    //                    if ((getColor().getColoresMap().get(e).equals("Negro"))) {
    //                        e =getColor().obtieneColor(img.getRGB(contAncho, contAlto-2));
    //                        if ((getColor().getColoresMap().get(e).equals("Negro"))) {
    //                           e =getColor().obtieneColor(img.getRGB(contAncho, contAlto-3));
    //                           if ((getColor().getColoresMap().get(e).equals("Negro"))) {
    //                               e =getColor().obtieneColor(img.getRGB(contAncho, contAlto-4));
    //                               if ((getColor().getColoresMap().get(e).equals("Negro"))) {
                                        noencontrofondo = false;//verificar si es 0 el fondo y si lo es darle un rango mayor a
                                                                //primer busqueda denuevo
    //                               }
    //                           }
    //                        }
    //                    }
                    }
                    contAlto--;
                }
            contAlto = getCoordYdelPrimerPxDelFondo(img);
            fondo[contAncho] = contAlto + 2;
            contAncho++;        
            int limSup=0;
            int limInf=0;

            while (contAncho < ancho) {
                limSup=fondo[contAncho-1]-5;
                if ((fondo[contAncho-1]+5) < alto){
                    limInf=fondo[contAncho-1]+5;
                }else{
                    limInf=alto-1;
                }
                if (todoFondo(img, contAncho, fondo[contAncho-1],limSup, limInf)){
                    fondo[contAncho] = buscaNegroArriba(img,contAncho, fondo[contAncho-1],limSup, limInf);
                } else {
                    if (hayHueco(img, contAncho, fondo[contAncho-1], limSup, limInf)) {

                        fondo[contAncho] = buscaBlancoAbajo(img,contAncho, fondo[contAncho-1],limSup, limInf);
                    } else {
                        if (hayBlancoDondeEstoy(img, contAncho, fondo[contAncho-1])) {
                            if (hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup) != -1) {
                                fondo[contAncho] = hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup);
                            } else {
                                fondo[contAncho] = fondo[contAncho - 1];
                            }
                        } else {
                            if (hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limInf) != -1) {
                                fondo[contAncho] = hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limInf);
                            } else {
                                fondo[contAncho] = fondo[contAncho - 1];
                            }
                        }
                    }
                }
                contAncho++;
            }
            salida=fondo;
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("buscaFondo(): "+e.toString());
        }
        return salida;
    }

    public int hayNegroArriba(BufferedImage img, int ancho, int alto, int limSup){
        boolean noencontrofondo = true;
        alto--;
        while ((alto>limSup) && (noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Negro"))) {
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

    public boolean hayNegroDondeEstoy(BufferedImage img, int ancho, int alto){
        int e = getColor().obtieneColor(img.getRGB(ancho, alto));
        if ((getColor().getColoresMap().get(e).equals("Negro"))) {
               return true;
        } else return false;
    }

    public int hayNegroAbajo(BufferedImage img, int ancho, int alto, int limInf){
        boolean noencontrofondo = true;
        alto++;
        while ((alto<limInf) && (noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Negro"))) {
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

    public int hayBlancoArriba(BufferedImage img, int ancho, int alto, int limSup){
        boolean noencontrofondo = true;
        alto--;
        while ((alto > limSup) && (noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Blanco"))) {
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

    public boolean hayBlancoDondeEstoy(BufferedImage img, int ancho, int alto){
        int e = getColor().obtieneColor(img.getRGB(ancho, alto));
        if ((getColor().getColoresMap().get(e).equals("Blanco"))) {
               return true;
        } else return false;
    }

    public int hayBlancoAbajo(BufferedImage img, int ancho, int alto, int limInf){
        boolean noencontrofondo = true;
        alto++;
        while ((alto<limInf) && (noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Blanco"))) {
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

    public boolean hayHueco(BufferedImage img, int ancho, int alto, int limSup, int limInf){
        boolean salida=false;
        if ((hayNegroArriba(img, ancho, alto, limSup)!=-1) && (hayNegroDondeEstoy(img, ancho, alto))&&(hayNegroAbajo(img, ancho, alto, limInf)!=-1)){
            salida=true;
        }
        return salida;
        
    }
    public boolean todoFondo(BufferedImage img, int ancho, int alto, int limSup, int limInf){
        boolean salida=false;
        if ((hayBlancoDondeEstoy(img, ancho, alto))&& (hayNegroArriba(img, ancho, alto, limSup)==-1)&&(hayNegroAbajo(img, ancho, alto, limInf)==-1)){
            salida=true;
        }
        return salida;

    }

    public int buscaNegroArriba(BufferedImage img, int ancho, int alto, int limSup, int limInf){
        boolean noencontrofondo = true;
        alto--;
        while ((noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Negro"))) {
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

      public int buscaBlancoAbajo(BufferedImage img, int ancho, int alto, int limSup, int limInf){
        boolean noencontrofondo = true;
        alto++;
        while ((alto<limInf) && (noencontrofondo == true)) {
            int e = getColor().obtieneColor(img.getRGB(ancho, alto));
            if ((getColor().getColoresMap().get(e).equals("Blanco"))) {
                noencontrofondo = false;
            }
            alto++;
        }
        if (noencontrofondo==true) {
            return limInf;
        } else { alto--;
            return alto;
        }
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

    public BufferedImage eliminaFondo(BufferedImage img, int[] fondo){
        int colorNegro = new Color (0,0,0).getRGB();
        ancho = img.getWidth()-1;
        alto = img.getHeight();
        int contAncho = 1;     
        int contAlto = alto - 2;   
        while (contAncho<=ancho){
             contAlto = alto - 2;
             while (contAlto >= fondo[contAncho] ) {
                img.setRGB(contAncho, contAlto, colorNegro);
                contAlto--;
            }
             contAncho++;
        }
        return img;
    }

    public BufferedImage eliminaHoras(BufferedImage img){ //no recorta la img, sino q las pisa con color Negro
        if (img.getHeight()>18){
            int colorNegro = new Color (0,0,0).getRGB();
            for (int x=0;x<img.getWidth();x++){
                for (int y=0;y<18;y++){  //suponemos q la hora ocupa los primeros 17px asique hasta ahi escribiremos con Negro
                    img.setRGB(x,y,colorNegro);
                }
            }            
        }
        return img;
    }

//    public ArrayList<modelo.dataManager.Marca> buscaMarcas(ArrayList<Integer> fondo){
//        ArrayList<modelo.dataManager.Marca> marcas = new ArrayList<modelo.dataManager.Marca>();
//        //Recorremos la imagen de arriba hacia abajo y de izqierda a derecha en busqueda de las marcas. Para esto haremos lo siguiente:
//            //Obtenemso el valor RGB del pixel que esta en x = 0,y=imagen.getAlto()
//            //Si ese valor RGB es blanco, leo el valor RGB del pixel que esta en x=0,y=y-1
//            //Si ese valor RGB no es blanco y >= fondo[x], hay marca
//            //Esto lo repetimos en un while de una condicion:
//                //1:No haber encontrado el borde (Vble booleana)
//         //Esto  lo repetimos hasta llegar a x = imagen.getAncho() incluido
//        return marcas;
//
//    }
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

    public BufferedImage bufferedImageClone(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }    
    
    public ArrayList<Marca> buscaMarcas(BufferedImage imgOriginal){
        ArrayList<Marca> marcas = new ArrayList();
        try{
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(5);//erosionando...
            Filtros filtros = new Filtros(getInstance().getAncho(), getInstance().getAlto());//Creamos los filtros para la imagen con la clase Filtros
            BufferedImage imgProcesada = filtros.erode(imgOriginal);//La erosionamos con Filtros.erode()
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(6);//binarizando...
            imgProcesada = filtros.Binarizacion(imgProcesada, 20);//Al resultado lo binarizamos con el umbral que corresponda
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(7);//dilatando...
            imgProcesada = filtros.dilate(imgProcesada);
            //imgProcesada = filtros.erode(imgProcesada);
            getInstance().grabarImagen(imgProcesada,"imgs\\filteredImg.tmp");
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(8);//buscando fondo...
            int fondo[] = buscaFondo(imgProcesada);//obtengo las coordenadas Y del fondo y lo guardo en el arreglo
            if (fondo != null && fondo.length>0){
                UltimaImgProcesada.getInstance().setProgresoProcesamiento(9);//fondo encontrado, quitandolo y buscando marcas...
                //BufferedImage imgConFondoo = dibujaFondo(imgProcesada, fondo);
                setImgConFondo(bufferedImageClone(imgProcesada)); //la guardo para despues graficar las marcas sobre la img con fondo
                //getInstance().grabarImagen(getImgConFondo(),"imgs\\imagenConFondo.tmp");
                imgProcesada = eliminaFondo(imgProcesada, fondo);
                imgProcesada = eliminaHoras(imgProcesada);
                //getInstance().grabarImagen(imgProcesada,"imgs\\imagenSinFondoNiHoras.tmp");
                //----------- ahora busco las marcas ----------
                Point point = new Point();
                for (int contAncho = 1; contAncho < imgProcesada.getWidth(); contAncho++) {
                    for (int contAlto= fondo[contAncho]; contAlto >(imgProcesada.getHeight()*0.50); contAlto--){//escanea hasta la mitad de la imagen
                        point.setLocation(contAncho, contAlto);             
                        if ((hayBlancoDondeEstoy(imgProcesada, contAncho, contAlto)) && (!perteneceAMarcaExistente(point,marcas))){
                            ArrayList<Point> coordMarca = new ArrayList<Point>();
                            int i=0;
                            coordMarca.add(new Point(contAncho,contAlto));
                            while (i<coordMarca.size()){
                                escaneoADerecha(imgProcesada,coordMarca.get(i),coordMarca);
                                escaneoAIzquierda(imgProcesada,coordMarca.get(i),coordMarca);
                                escaneoAAbajo(imgProcesada,coordMarca.get(i),coordMarca);
                                escaneoAArriba(imgProcesada,coordMarca.get(i),coordMarca);
                                i++;
                            }
                            if (coordMarca.size()>30){ //si la marca se compone de mas de 30 pixeles, la considero Marca
                                Marca marca= new Marca();
                                marca.setCoordMarca(coordMarca);
                                marca.setAreaImagen(String.valueOf(coordMarca.size()));
                                marcas.add(marca);
                            }
                        }
                    }
                }
                UltimaImgProcesada.getInstance().setProgresoProcesamiento(10);//fin del correcto procesamiento de img
            }else{
                UltimaImgProcesada.getInstance().setProgresoProcesamiento(-1);//no se pudo procesar la imagen
            }
        }catch(Exception e){
            Logueador.getInstance().agregaAlLog("buscaMarcas(): "+e.toString());
        }
        return marcas;
    }

    public boolean perteneceAMarcaExistente(Point point,ArrayList<Marca> marcas){
        boolean encontro = false;
        int pos = marcas.size()-1;
        while ((pos>=0) &&(! encontro)){
            ArrayList<Point> coordMarca = marcas.get(pos).getCoordMarca();
            if (coordMarca.contains(point)){
                encontro=true;
            }
            pos--;
        }

        return encontro;

    }

    public void escaneoADerecha(BufferedImage img, Point point,ArrayList coordMarca){
        int posDer = (int) (point.getX() + 1);
        Point point2 = new Point(posDer,(int) point.getY());
        while (hayBlancoDondeEstoy(img, posDer,(int) point2.getY()) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point(posDer,(int) point2.getY()));
            posDer++;
            point2.setLocation(posDer, point2.getY());
        }
    }

    public void escaneoAIzquierda(BufferedImage img, Point point,ArrayList coordMarca){
        int posIzq = (int) (point.getX() - 1);
        Point point2=new Point(posIzq, (int) point.getY());
        while (hayBlancoDondeEstoy(img, posIzq,(int) point2.getY()) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point(posIzq,(int) point2.getY()));
            posIzq--;
            point2.setLocation(posIzq, point2.getY());
        }
    }

    public void escaneoAAbajo(BufferedImage img, Point point,ArrayList coordMarca){
        int posAba = (int) (point.getY() + 1);
        Point point2= new Point((int)point.getX(),posAba);
        while (hayBlancoDondeEstoy(img,(int) point2.getX(),posAba) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point((int)point2.getX(),posAba));
            posAba++;
            point2.setLocation(point2.getX(),posAba);
        }
    }

    public void escaneoAArriba(BufferedImage img, Point point,ArrayList coordMarca){
          int posArr = (int) (point.getY() - 1);
          Point point2 = new Point((int)point.getX(),posArr);
        while (hayBlancoDondeEstoy(img,(int) point2.getX(),posArr) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point((int)point2.getX(),posArr));
            posArr--;
            point2.setLocation(point2.getX(),posArr);
        }
    }

     public BufferedImage dibujaMarcasDetectadas(BufferedImage imgOriginal, ArrayList<Marca> marcas){
         BufferedImage imgConMarcas = imgOriginal;
         int colorRojo = new Color (255,0,0).getRGB();
         marcas.get(1).getCoordMarca().get(1);
         for (Marca m : marcas) {
              int i=0;
              while (i<m.getCoordMarca().size()){
                 if (!hayBlancoDondeEstoy(imgConMarcas, (int) m.getCoordMarca().get(i).getX()+1, (int) m.getCoordMarca().get(i).getY())) {
                    imgConMarcas.setRGB((int) m.getCoordMarca().get(i).getX()+1, (int) m.getCoordMarca().get(i).getY(),colorRojo);
                    }
                    else{
                        if (!hayBlancoDondeEstoy(imgConMarcas, (int) m.getCoordMarca().get(i).getX()-1, (int) m.getCoordMarca().get(i).getY())) {
                        imgConMarcas.setRGB((int) m.getCoordMarca().get(i).getX()-1, (int) m.getCoordMarca().get(i).getY(),colorRojo);
                        }
                        else{
                            if (!hayBlancoDondeEstoy(imgConMarcas, (int) m.getCoordMarca().get(i).getX(), (int) m.getCoordMarca().get(i).getY()+1)) {
                                imgConMarcas.setRGB((int) m.getCoordMarca().get(i).getX(), (int) m.getCoordMarca().get(i).getY()+1,colorRojo);
                            }
                            else{
                                if (!hayBlancoDondeEstoy(imgConMarcas, (int) m.getCoordMarca().get(i).getX(), (int) m.getCoordMarca().get(i).getY()-1)) {
                                    imgConMarcas.setRGB((int) m.getCoordMarca().get(i).getX(), (int) m.getCoordMarca().get(i).getY()-1,colorRojo);
                                }
                        }
                    }
                 }
                 i++;
              }
         }
         return imgConMarcas;
     }

     public BufferedImage rellenaMarcasDetectadas(BufferedImage img, ArrayList<Marca> marcas){
         int colorRojo = new Color (255,0,0).getRGB();
         for (Marca m : marcas) {
              int i=0;
              while (i<m.getCoordMarca().size()){
                   img.setRGB((int) m.getCoordMarca().get(i).getX(), 
                           (int) m.getCoordMarca().get(i).getY(), colorRojo);
                   i++;
              }
         }
         return img; 
     }

     public BufferedImage recortaExpander(BufferedImage imgConExpander){
         //sacamos el expander de la imagen suponiendo q ocupa el ultimo 22% de la imagen, de abajo hacia arriba (estimación basada en ejemplos vistos)
         BufferedImage imgSinExpander = imgConExpander.getSubimage(0, 0, imgConExpander.getWidth(),(int)(imgConExpander.getHeight()*0.78) );
         return imgSinExpander;
     }    
         
     public boolean imagenApta(BufferedImage imgOriginal) throws IOException{
        boolean esApta= false;
        ArrayList<Float> porcentajesPxs = porcentajePxsRojosAmarillosNegros(imgOriginal); 
        UltimaImgProcesada.getInstance().setPorcentajesColores(porcentajesPxs);
        boolean caso1=porcentajesPxs.get(0)<12  && porcentajesPxs.get(1)<10  && porcentajesPxs.get(2)>60;
        boolean caso2=porcentajesPxs.get(0)<=13  && porcentajesPxs.get(1)<4  && porcentajesPxs.get(2)>60;
        if (caso1 || caso2){
            esApta=true;
        }
     return esApta;
     }

     public ArrayList<Float> porcentajePxsRojosAmarillosNegros(BufferedImage img){
         ArrayList<Float> porcentajes=new ArrayList();
         porcentajes.add((float)0); //inicializo posicion 0, q será para porcentaje de px Rojos
         porcentajes.add((float)0); //inicializo posicion 1, q será para porcentaje de px Amarillos
         porcentajes.add((float)0); //inicializo posicion 2, q será para porcentaje de px Negros
         try{
             if (img.getWidth() != 0  && img.getHeight()!=0){
                int cantAmarillos=0;
                int cantRojos=0;
                int cantNegros=0;
                for (int contAncho = 1; contAncho < img.getWidth(); contAncho++) {
                    for (int contAlto= 1; contAlto < img.getHeight(); contAlto++){//contAlto >350
                        int e = getColor().obtieneColor(img.getRGB(contAncho, contAlto));
                        if (getColor().getColoresMap().get(e).equals("Negro")) {
                            cantNegros++;
                        }else{
                            if (getColor().getColoresMap().get(e).equals("Amarillo")){
                                cantAmarillos++;
                            }else{
                                if (getColor().getColoresMap().get(e).equals("Rojo")){
                                    cantRojos++;
                                }
                            }
                        }
                    }
                }
                porcentajes.set(0,(float)((cantRojos*100)/(img.getWidth()*img.getHeight())));
                porcentajes.set(1,(float)((cantAmarillos*100)/(img.getWidth()*img.getHeight())));
                porcentajes.set(2,(float)((cantNegros*100)/(img.getWidth()*img.getHeight())));
             }
         }catch(Exception e){
             Logueador.getInstance().agregaAlLog("porcentajePxsRojosAmarillosNegros(): "+e.toString());
         }
         return porcentajes;
     }


//    public ArrayList<modelo.dataManager.Marca> getMarcas(BufferedImage imgSoloMarcas){
//        ArrayList<modelo.dataManager.Marca> marcas = new ArrayList();
//         -- metodo pendiente --
//
//        Leemos la imagen con obtenerImagen()
//        OperacionesBasicas.getInstance().obtenerImagen(rutaImg);
//        Creamos los filtros para la imagen con la clase Filtros
//        Filtros filtros = new Filtros(getInstance().getAncho(), getInstance().getAlto());
//        BufferedImage imgProcesada = filtros.erode(getImagenOriginal());
//
//        imgProcesada = filtros.Binarizacion(imgProcesada, 20);
//        imgProcesada = filtros.dilate(imgProcesada);
//
//        getInstance().grabarImagen(imgProcesada,"imgs\\imagen.tmp");
//
//        int fondo[] = new int[967];
//        fondo = buscaFondo(imgProcesada);
//        new ArrayList(Arrays.asList(fondo));
//        setImgProcesada(dibujaFondo(imgProcesada, fondo)); //guarda la imagen con fondo
//        getInstance().grabarImagen(getImgProcesada(),"imgs\\imagenConFondo.tmp"); //la guarda en disco
//        setImgProcesada(eliminaFondo(getImgProcesada(), fondo));
//        getInstance().grabarImagen(getImgProcesada(),"imgs\\imagenSinFondo.tmp");
//        setImgProcesada(eliminaHoras(getImgProcesada()));
//        getInstance().grabarImagen(getImgProcesada(),"imgs\\imagenSinHoras.tmp");
//        buscaMarcas(fondo)
//        BufferedImage emma = getImgProcesada();
//
//        return marcas;
//
//     y tambien pensaba q habría q hacer un método tipo el q pintaba de rojo el fondo, pero para las marcas... estaria bueno q la firma sea:
//     dibujaMarcasDetectadas(BufferedImage imgConMarcas, ArrayList<Marca> marcas):BufferedImage
//     yo:  bien
//     Sebastian:  si queres copialo y pegalo por ahi para hacer luego
//     Sebastian ha introducido texto.
//
//     estoy pensando y, como el fondo es variable, para calcular el limite superior hasta el cual leer habría q hacer un método
//     Enviado a la(s) 23:30 del miércoles
//     Sebastian:  la firma podría ser getLimiteSuperiorAanalizar(BufferedImage imgConMarcas, ArrayList fondo):ArrayList
//     el ArrayList q devuelve sería limite superior hasta el cual analizar
//     yo:  con el promedio y algo mas?
//     promedio entre el margen superior de la imagen y el fondo por ahora...
//
//
//
//    }

    /**
     * @return the imgProcesada
     */
    public BufferedImage getImgProcesada() {
        return imgProcesada;
    }

    /**
     * @param imgProcesada the imgProcesada to set
     */
    public void setImgProcesada(BufferedImage imgProcesada) {
        this.imgProcesada = imgProcesada;
    }

    /**
     * @return the color
     */
    public Colores getColor() {
        return color;
    }

    /**
     * @return the imgConFondo
     */
    public BufferedImage getImgConFondo() {
        return imgConFondo;
    }

    /**
     * @param imgConFondo the imgConFondo to set
     */
    public void setImgConFondo(BufferedImage imgConFondo) {
        this.imgConFondo = imgConFondo;
    }
    
    public boolean procesarImagen(String imgFileName){
        boolean sePudo=false;
        try{
            OperacionesBasicas.getInstance().obtenerImagen(imgFileName);
            BufferedImage imgOriginal = getImagenOriginal();
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(4);//cargue en memoria la img, ahora voy a analizar si es apta
            if (imgOriginal != null){
                ArrayList datosPrimerPixel = DATatlantis.getInstance().getDatosFromPixel(imgFileName, 0);
                SondaSetHistorico sshPrimerPx = (SondaSetHistorico)datosPrimerPixel.get(0);
                ArrayList datosUltimoPixel = DATatlantis.getInstance().getDatosFromPixel(imgFileName, imgOriginal.getWidth()-2);
                SondaSetHistorico sshUltimoPx = (SondaSetHistorico)datosUltimoPixel.get(0);
                if (sshPrimerPx.getExpander()>0  || sshUltimoPx.getExpander()>0){//hay expander
                    imgOriginal = recortaExpander(imgOriginal);
                }
                if (imagenApta(imgOriginal)){
                    ArrayList<Marca> marcas= buscaMarcas(imgOriginal);
                    UltimaImgProcesada.getInstance().setMarcas(marcas);
                    if (marcas.size()>0){
                        BufferedImage imgConFondoYMarcasRellenas = rellenaMarcasDetectadas(getImgConFondo(), marcas);
                        grabarImagen(imgConFondoYMarcasRellenas,"imgs\\imgWithDetectedMarks.tmp");
                        sePudo=true;
                    }else{
                        if (UltimaImgProcesada.getInstance().getProgresoProcesamiento() != -1){ //-1=no se pudo procesar la imagen
                            sePudo=true;
                        }else{
                            sePudo=false;
                        }
                    }
                    UltimaImgProcesada.getInstance().setProgresoProcesamiento(11);
                }else{
                    UltimaImgProcesada.getInstance().setProgresoProcesamiento(12);
                }
            }
        }catch(Exception e){
            UltimaImgProcesada.getInstance().setProgresoProcesamiento(-1);
            Logueador.getInstance().agregaAlLog("procesarImagen(): "+e.toString());
        }
        if (sePudo){
            controllers.ControllerNavegacion.getInstance().actualizaGuiProcesamientoImgs();
        }else{
            controllers.ControllerNavegacion.getInstance().errorGuiProcesamientoImgs();
        }        
        return sePudo;
    }

    public int getCoordYdelPrimerPxDelFondo(BufferedImage img){
        int coordYsalida = (int)(img.getHeight()*0.8);//valor por defecto: fondo ubicado en el 20% inferior de la img
        //pendiente
        int posicionX=1; //para hacer el analisis usarmeos pixel X = 1
        int posicionY=img.getHeight();
        boolean encontro = false;
        while (posicionY>0 && !encontro){
            posicionY = hayBlancoArriba(img, posicionX, posicionY, 0);
            if (posicionY > 0){
                if (!esPuntoSubterraneoAislado(img, posicionX, posicionY)){
                    coordYsalida = hayNegroArriba(img, posicionX, posicionY, 0);
                    encontro=true;
                }else{
                    posicionY = hayNegroArriba(img, posicionX, posicionY, 0);
                }
            }
        }
        return coordYsalida;
    }
    
    private boolean esPuntoSubterraneoAislado(BufferedImage img, int coordX, int coordY){
        int hastaXpx=9;
        boolean esPuntoSubterraneoAislado = (hayNegroAderecha(img,coordX,coordY,coordX+hastaXpx) != -1) & 
                (hayNegroArriba(img,coordX,coordY,coordY-hastaXpx) != -1);// && hayNegroAbajo(img,coordX,coordY,coordY+hastaXpx);
        return esPuntoSubterraneoAislado;
    }

    private int hayNegroAderecha(BufferedImage img, int coordX, int coordY, int limAderecha) {
        int salida=-1;
        boolean encontro = false;
        while ((coordX<limAderecha) && (!encontro)) {
            int e = getColor().obtieneColor(img.getRGB(coordX, coordY));
            if ((getColor().getColoresMap().get(e).equals("Negro"))) {
                encontro = true;
            }else{
                coordX++;
            }
        }
        if (encontro) {
            salida = coordX;
        }
        return salida;
    }

}
