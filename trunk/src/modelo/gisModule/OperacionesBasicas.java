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
import modelo.dataManager.Marca;


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

   
    public static void main(String[] args){

         int cantPeces = OperacionesBasicas.getInstance().cuantosPecesHay("imgs\\img1.jpg");
         ArrayList<Marca> marcas=OperacionesBasicas.getInstance().buscaMarcas();
         BufferedImage imgConMarcas = getInstance().dibujaMarcasDetectadas(getInstance().imgProcesada,marcas);
         getInstance().grabarImagen(imgConMarcas,"imgs\\imagenMarcas.tmp");
         BufferedImage imgConMarcasRellena = getInstance().rellenaMarcasDetectadas(imgConMarcas, marcas);
         getInstance().grabarImagen(imgConMarcasRellena,"imgs\\imagenMarcasRellenas.tmp");
         BufferedImage imgCOnFondoYMarcasRellenas = getInstance().rellenaMarcasDetectadas(getInstance().getImgConFondo(), marcas);
         getInstance().grabarImagen(imgCOnFondoYMarcasRellenas,"imgs\\imagenConFondoYMarcasRellenas.tmp");
         //OperacionesBasicas.getInstance().getMarcas(imgSoloMarcas);

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
        getInstance().grabarImagen(imgProcesada,"imgs\\imagen.tmp");
        //Obtenemos bordes de la imagen binarizada segmentacion.Bordes(imgProcesada)
//        imgProcesada = segmentacion.Bordes(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);
        
//        imgProcesada = filtros.erode(imgProcesada);
//        getInstance().grabarImagen(imgProcesada);

        //Armo el arreglo de la posicion del fondo en todo el ancho de la imagen.
       // ArrayList<Integer> fondo = buscaFondo(imgProcesada);
        int fondo[] = new int[967];
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
        int prom = promedio(fondo);
        //buscaMarcas(fondo)


        return cantPeces;
    }


    public int[] buscaFondo(BufferedImage img) {

        ancho = img.getWidth();
        alto = img.getHeight();
        int fondo[] = new int[ancho];
        boolean noencontrofondo = true;
        int contAncho = 1;      //maximo 966
        int contAlto = alto - 2;    //maximo 635

            while ((contAlto > 0) && (noencontrofondo == true)) {

                int e = getColor().obtieneColor(img.getRGB(contAncho, contAlto));
                if ((getColor().getColoresMap().get(e).equals("Negro"))) {
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
            if (todoFondo(img, contAncho, fondo[contAncho-1],limSup, limInf)){
                fondo[contAncho] = buscaNegroArriba(img,contAncho, fondo[contAncho-1],limSup, limInf);
            } else {
                if (hayHueco(img, contAncho, fondo[contAncho-1], limSup, limInf)) {
                    
                    fondo[contAncho] = buscaBlancoAbajo(img,contAncho, fondo[contAncho-1],limSup, limInf);
                } else {
                    if (hayBlancoDondeEstoy(img, contAncho, fondo[contAncho-1])) {
                        if (hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup, limInf) != -1) {
                            fondo[contAncho] = hayNegroArriba(img, contAncho, fondo[contAncho-1], limSup, limInf);
                        } else {
                            fondo[contAncho] = fondo[contAncho - 1];
                        }
                    } else {
                        if (hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limSup, limInf) != -1) {
                            fondo[contAncho] = hayBlancoAbajo(img, contAncho, fondo[contAncho-1], limSup, limInf);
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

    public int hayNegroArriba(BufferedImage img, int ancho, int alto, int limSup, int limInf){
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

    public int hayNegroAbajo(BufferedImage img, int ancho, int alto, int limSup, int limInf){
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

    public int hayBlancoArriba(BufferedImage img, int ancho, int alto, int limSup, int limInf){
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

    public int hayBlancoAbajo(BufferedImage img, int ancho, int alto, int limSup, int limInf){
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
        if ((hayNegroArriba(img, ancho, alto, limSup, limInf)!=-1) && (hayNegroDondeEstoy(img, ancho, alto))&&(hayNegroAbajo(img, ancho, alto, limSup, limInf)!=-1)){
            salida=true;
        }
        return salida;
        
    }
    public boolean todoFondo(BufferedImage img, int ancho, int alto, int limSup, int limInf){
        boolean salida=false;
        if ((hayBlancoDondeEstoy(img, ancho, alto))&& (hayNegroArriba(img, ancho, alto, limSup, limInf)==-1)&&(hayNegroAbajo(img, ancho, alto, limSup, limInf)==-1)){
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

    public int promedio(int[] fondo){
        int cont = 1;       // contador
        int suma = 0;    // suma


        while (cont < fondo.length ) {
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

    public BufferedImage eliminaFondo(BufferedImage img, int[] fondo){
        int colorNegro = new Color (0,0,0).getRGB();
        ancho = img.getWidth();
        alto = img.getHeight();
        int contAncho = 1;     
        int contAlto = alto - 2;   
        while (contAncho<=966){
             contAlto = alto - 2;
             while (contAlto >= fondo[contAncho] ) {
                img.setRGB(contAncho, contAlto, colorNegro);
                contAlto--;
            }
             contAncho++;
        }
        return img;
    }

        public BufferedImage eliminaHoras(BufferedImage img){
        int colorNegro = new Color (0,0,0).getRGB();
        alto = 1;
        int contAncho = 1;      
        int contAlto = alto;
        while (contAncho<=966){
             contAlto = alto;
             while (contAlto <= 20 ) {
                img.setRGB(contAncho, contAlto, colorNegro);
                contAlto++;
            }
             contAncho++;
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

    public ArrayList<Marca> buscaMarcas(){
        ArrayList<Marca> marcas = new ArrayList();
        BufferedImage img = getImgProcesada();

        Point point = new Point();



        for (int contAncho = 1; contAncho < img.getWidth(); contAncho++) {

            for (int contAlto= img.getHeight()-1; contAlto >350; contAlto--){
                point.setLocation(contAncho, contAlto);             
                    if ((hayBlancoDondeEstoy(img, contAncho, contAlto)) && (!perteneceAMarcaExistente(point,marcas))){
                    ArrayList<Point> coordMarca = new ArrayList<Point>();
                    int i=0;
                    coordMarca.add(new Point(contAncho,contAlto));
                    while (i<coordMarca.size()){
                        escaneoADerecha(coordMarca.get(i),coordMarca);
                        escaneoAIzquierda(coordMarca.get(i),coordMarca);
                        escaneoAAbajo(coordMarca.get(i),coordMarca);
                        escaneoAArriba(coordMarca.get(i),coordMarca);
                        i++;
                    }
                    if (coordMarca.size()>30){
                         Marca marca= new Marca();
                         marca.setCoordMarca(coordMarca);
                         marca.setAreaImagen(String.valueOf(coordMarca.size()));
                         marcas.add(marca);
                        }
                    }                
           }
         
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

    public void escaneoADerecha(Point point,ArrayList coordMarca){
        int posDer = (int) (point.getX() + 1);
        Point point2 = new Point(posDer,(int) point.getY());
        while (hayBlancoDondeEstoy(imgProcesada, posDer,(int) point2.getY()) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point(posDer,(int) point2.getY()));
            posDer++;
            point2.setLocation(posDer, point2.getY());
        }
    }

    public void escaneoAIzquierda(Point point,ArrayList coordMarca){
        int posIzq = (int) (point.getX() - 1);
        Point point2=new Point(posIzq, (int) point.getY());
        while (hayBlancoDondeEstoy(imgProcesada, posIzq,(int) point2.getY()) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point(posIzq,(int) point2.getY()));
            posIzq--;
            point2.setLocation(posIzq, point2.getY());
        }
    }

    public void escaneoAAbajo(Point point,ArrayList coordMarca){
        int posAba = (int) (point.getY() + 1);
        Point point2= new Point((int)point.getX(),posAba);
        while (hayBlancoDondeEstoy(imgProcesada,(int) point2.getX(),posAba) &&(!coordMarca.contains(point2))) {
            coordMarca.add(new Point((int)point2.getX(),posAba));
            posAba++;
            point2.setLocation(point2.getX(),posAba);
        }
    }

    public void escaneoAArriba(Point point,ArrayList coordMarca){
          int posArr = (int) (point.getY() - 1);
          Point point2 = new Point((int)point.getX(),posArr);
        while (hayBlancoDondeEstoy(imgProcesada,(int) point2.getX(),posArr) &&(!coordMarca.contains(point2))) {
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
                        i++;}
             }
         return imgConMarcas;

     }

         public BufferedImage rellenaMarcasDetectadas(BufferedImage imgOriginal, ArrayList<Marca> marcas){
         BufferedImage imgConMarcasRellena = imgOriginal;
         int colorRojo = new Color (255,0,0).getRGB();
         marcas.get(1).getCoordMarca().get(1);
         for (Marca m : marcas) {
              int i=0;
              while (i<m.getCoordMarca().size()){
                   imgConMarcasRellena.setRGB((int) m.getCoordMarca().get(i).getX(), (int) m.getCoordMarca().get(i).getY(),colorRojo);
                   i++;}
             }
         return imgConMarcasRellena;

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

}
