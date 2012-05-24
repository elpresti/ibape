package modelo.gisModule;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Detector {
	static 
	{ 
	      System.setProperty("com.sun.media.jai.disableMediaLib", "true"); 
	} 
	int rgbs[];
	float hsb[];
	Integer colorpixel = null;
	int pixelesAnalizados;
	int ganador;
	
	JLabel lblDetecta;
	Colores color;
	Segmentacion seg;
	Aclarador aclarador;
	Filtros fil;
	int value;
	private BufferedImage imagenOriginal;
    private int ancho;
    private int alto;
    private BufferedImage imgErosion;
    private BufferedImage imgAclarada;
    private BufferedImage imagenCuantizada;
	
	Map<Integer,List<Pixel>> mapa;

	
	
	int contador[];
	
	public Detector(){
		
		
		
		aclarador = new Aclarador();
		rgbs = new int[3];
		hsb = new float[3];
		color = new Colores();
		contador = new int[12];
		for(int i=0;i<12;i++){
			contador[i] = 0;
		}
	}
	
	

	
	public BufferedImage getImagenOriginal(){
		return imagenOriginal;
	}
	
	public BufferedImage getImagenErosion(){
		return getImgErosion();
	}
	
	public BufferedImage getImagenAclarada(){
		return getImgAclarada();
	}
	
	public int[] getContadorPixeles(){
		return contador;
	}
	
	public int getPixelesAnalizados(){
		return pixelesAnalizados;
	}
	
	public Map<Integer,List<Pixel>> getMapa(){
		return mapa;
	}
	
	public void obtenerImagen(String ruta){
		try {
			setImagenOriginal(ImageIO.read(new File(ruta)));
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		setAncho(imagenOriginal.getWidth());
		setAlto(imagenOriginal.getHeight(null));
	}
	
	public BufferedImage ejecutaErosion(){
//		obtenerImagen(ruta);
		setImagenCuantizada(new BufferedImage(getAncho(), getAlto(), BufferedImage.TYPE_INT_RGB));
		seg = new Segmentacion(getAncho(), getAlto());
		fil = new Filtros(getAncho(), getAlto());
		setImgErosion(fil.erode(imagenOriginal));
				
		setImgAclarada(aclarador.aclara(getImgErosion()));
//        try {
//            //PlanarImage convolveImage = JAI.create("convolve", input, kernel);
//            //JAI.create("filestore", imgAclarada, "D:\\2.JPG","JPG");
//            ImageIO.write(imgAclarada, "jpg", new File("d:\\foto2.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(Detector.class.getName()).log(Level.SEVERE, null, ex);
//        }
		mapa = Segmentacion.segmentaFinal(getImgAclarada(), getImagenCuantizada());
	return getImgAclarada();
     // return getImgErosion();
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

    /**
     * @return the imgErosion
     */
    public BufferedImage getImgErosion() {
        return imgErosion;
    }

    /**
     * @param imgErosion the imgErosion to set
     */
    public void setImgErosion(BufferedImage imgErosion) {
        this.imgErosion = imgErosion;
    }

    /**
     * @return the imgAclarada
     */
    public BufferedImage getImgAclarada() {
        return imgAclarada;
    }

    /**
     * @param imgAclarada the imgAclarada to set
     */
    public void setImgAclarada(BufferedImage imgAclarada) {
        this.imgAclarada = imgAclarada;
    }

    /**
     * @return the imagenCuantizada2
     */
    public BufferedImage getImagenCuantizada() {
        return imagenCuantizada;
    }

    /**
     * @param imagenCuantizada2 the imagenCuantizada2 to set
     */
    public void setImagenCuantizada(BufferedImage imagenCuantizada) {
        this.imagenCuantizada = imagenCuantizada;
    }

	
//
//	public void ejecutaMedia(String ruta){
//		obtenerImagen(ruta);
//		imagenCuantizada2 = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
//		seg = new Segmentacion(ancho,alto);
//		fil = new Filtros(ancho, alto);
//		BufferedImage imgMedia;
//
//		imgMedia = seg.filtroMedia(imagenOriginal);
//		imgAclarada = aclarador.aclara(imgMedia);
//		mapa = Segmentacion.segmentaFinal(imgAclarada, imagenCuantizada2);
//
////		for(int i=1; i<ancho; i++){
////			if(i%21==0){
////				for(int j=1; j<alto;j++){
////					if(j%21==0){
////						value =  imgAclarada.getRGB(i,j);
////						rgbs = Colores.obtieneRGB(value);
////						Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
////						colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
////						contador[colorpixel]++;
////					}
////				}
////			}
////		}
////
////		ganador=0;
////		pixelesAnalizados=0;
////
////		for(int i=0;i<12;i++){
////			pixelesAnalizados+=contador[i];
////			if(contador[i]>ganador){
////				ganador=contador[i];
////				colorpixel=i;
////			}
////		}
////		s = new Sonido(colorpixel);
////		return lblDetecta = new JLabel("Color "+new Colores().getColoresMap().get(colorpixel)+". Aparicion: "+(100*ganador)/pixelesAnalizados+"%",JLabel.CENTER);
//	}
//
//
//	public void ejecutaMediana(String ruta){
//		obtenerImagen(ruta);
//		imagenCuantizada2 = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
//		seg = new Segmentacion(ancho,alto);
//		fil = new Filtros(ancho, alto);
//		BufferedImage imgMediana;
//
//		imgMediana = seg.filtroMediana(imagenOriginal);
//		imgAclarada = aclarador.aclara(imgMediana);
//		mapa = Segmentacion.segmentaFinal(imgAclarada, imagenCuantizada2);
////		for(int i=1; i<ancho; i++){
////			if(i%21==0){
////				for(int j=1; j<alto;j++){
////					if(j%21==0){
////						value =  imgAclarada.getRGB(i,j);
////						rgbs = Colores.obtieneRGB(value);
////						Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
////						colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
////						contador[colorpixel]++;
////					}
////				}
////			}
////		}
////
////		ganador=0;
////		pixelesAnalizados=0;
////
////		for(int i=0;i<12;i++){
////			pixelesAnalizados+=contador[i];
////			if(contador[i]>ganador){
////				ganador=contador[i];
////				colorpixel=i;
////			}
////		}
////		s = new Sonido(colorpixel);
////		return lblDetecta = new JLabel("Color "+new Colores().getColoresMap().get(colorpixel)+". Aparicion: "+(100*ganador)/pixelesAnalizados+"%",JLabel.CENTER);
//	}
//
//	///////////////////////////
//
//	public void ejecutaApertura(String ruta) {
//		obtenerImagen(ruta);
//		imagenCuantizada2 = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
//		seg = new Segmentacion(ancho,alto);
//		fil = new Filtros(ancho, alto);
//		BufferedImage imgApertura;
//
//		imgErosion = fil.erode(imagenOriginal);
//		imgApertura = fil.dilate(imgErosion);
//		imgAclarada = aclarador.aclara(imgApertura);
//		mapa = Segmentacion.segmentaFinal(imgAclarada, imagenCuantizada2);
////		for(int i=1; i<ancho; i++){
////			if(i%21==0){
////				for(int j=1; j<alto;j++){
////					if(j%21==0){
////						value =  imgAclarada.getRGB(i,j);
////						rgbs = Colores.obtieneRGB(value);
////						Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
////						colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
////						contador[colorpixel]++;
////					}
////				}
////			}
////		}
////
////		ganador=0;
////		pixelesAnalizados=0;
////
////		for(int i=0;i<12;i++){
////			pixelesAnalizados+=contador[i];
////			if(contador[i]>ganador){
////				ganador=contador[i];
////				colorpixel=i;
////			}
////		}
////		s = new Sonido(colorpixel);
////		return lblDetecta = new JLabel("Color "+new Colores().getColoresMap().get(colorpixel)+". Aparicion: "+(100*ganador)/pixelesAnalizados+"%",JLabel.CENTER);
//	}
//
//
//	public void ejecutaCierre(String ruta) {
//		obtenerImagen(ruta);
//		imagenCuantizada2 = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
//		seg = new Segmentacion(ancho,alto);
//		fil = new Filtros(ancho, alto);
//		BufferedImage imgCierre;
//		BufferedImage imgDilatacion;
//
//		imgDilatacion = fil.dilate(imagenOriginal);
//		imgCierre = fil.erode(imgDilatacion);
//		imgAclarada = aclarador.aclara(imgCierre);
//		mapa = Segmentacion.segmentaFinal(imgAclarada, imagenCuantizada2);
////		for(int i=1; i<ancho; i++){
////			if(i%21==0){
////				for(int j=1; j<alto;j++){
////					if(j%21==0){
////						value =  imgAclarada.getRGB(i,j);
////						rgbs = Colores.obtieneRGB(value);
////						Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
////						colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
////						contador[colorpixel]++;
////					}
////				}
////			}
////		}
////
////		ganador=0;
////		pixelesAnalizados=0;
////
////		for(int i=0;i<12;i++){
////			pixelesAnalizados+=contador[i];
////			if(contador[i]>ganador){
////				ganador=contador[i];
////				colorpixel=i;
////			}
////		}
////		s = new Sonido(colorpixel);
////		return lblDetecta = new JLabel("Color "+new Colores().getColoresMap().get(colorpixel)+". Aparicion: "+(100*ganador)/pixelesAnalizados+"%",JLabel.CENTER);
//	}
//
//
//	public void ejecutaDilatacion(String ruta) {
//		BufferedImage imgDilatacion;
//		obtenerImagen(ruta);
//		imagenCuantizada2 = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
//		seg = new Segmentacion(ancho,alto);
//		fil = new Filtros(ancho, alto);
//		imgDilatacion = fil.dilate(imagenOriginal);
//
////			imgErosion = fil.dilate(imgErosion);
//
//		imgAclarada = aclarador.aclara(imgDilatacion);
//		mapa = Segmentacion.segmentaFinal(imgAclarada, imagenCuantizada2);
////		for(int i=1; i<ancho; i++){
////			if(i%21==0){
////				for(int j=1; j<alto;j++){
////					if(j%21==0){
////						value =  imgAclarada.getRGB(i,j);
////						rgbs = Colores.obtieneRGB(value);
////						Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
////						colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
////						contador[colorpixel]++;
////					}
////				}
////			}
////		}
////
////		ganador=0;
////		pixelesAnalizados=0;
////
////		for(int i=0;i<12;i++){
////			pixelesAnalizados+=contador[i];
////			if(contador[i]>ganador){
////				ganador=contador[i];
////				colorpixel=i;
////			}
////		}
////		s = new Sonido(colorpixel);
////		return lblDetecta = new JLabel("Color "+new Colores().getColoresMap().get(colorpixel)+". Aparicion: "+(100*ganador)/pixelesAnalizados+"%",JLabel.CENTER);
//	}
//
//
}
	
