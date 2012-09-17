package modelo.gisModule;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.BufferedImage;
import java.awt.Color;
 
 
 
public class Filtros {
   
    private int ancho;
    private int alto;
    private int color[];
   
    public Filtros(int ancho , int alto) {
       
        this.ancho = ancho;
        this.alto = alto;
        
        color = new int [2];
   
     	// Blanco ...
        color[0] = new Color (255,255,255).getRGB();
           
        // Negro ...
        color[1] = new Color (0,0,0).getRGB();
 
   
   
    }
 
	public BufferedImage erode(BufferedImage img)   {
	 
	// Cargar Matrix al Kernel 
	        KernelJAI kernel = new KernelJAI(3, 3, new float[]{
	                                                            0, 0, 0,
	                                                            1, 1, 0,
	                                                            1, 1, 0,
	                                                            
	                                                            });
	        ParameterBlock pb = new ParameterBlock();
	        pb.addSource(img);
	        pb.add(kernel);
	        return JAI.create("erode", pb).getAsBufferedImage();
	}



	public BufferedImage dilate(BufferedImage img)   {
		 
		// Cargar Matrix al Kernel 
		        KernelJAI kernel = new KernelJAI(3, 3, new float[]{
		                                                            0, 0, 0,
		                                                            1, 1, 0,
		                                                            1, 1, 0,
		                                                            
		                                                            });
		        ParameterBlock pb = new ParameterBlock();
		        pb.addSource(img);
		        pb.add(kernel);
		        return JAI.create("dilate", pb).getAsBufferedImage();
	}






	//Conversion de imagen a escala de grises
	public  BufferedImage Binarizacion (BufferedImage ImageBfrd , int n ) {
            long Umbral = 500000;
            BufferedImage Nueva_Imagen = new BufferedImage(ImageBfrd.getWidth(),ImageBfrd.getHeight(),BufferedImage.TYPE_INT_RGB) ;
            for (int i = 0; i <ImageBfrd.getWidth() ; i ++) {
                for ( int j = 0; j <ImageBfrd.getHeight() ; j ++ ) {
                    if ( ImageBfrd.getRGB(i,j) <-Umbral * n) {
                        Nueva_Imagen.setRGB(i,j,color[1]);
                    } else {
                        Nueva_Imagen.setRGB(i,j,color[0]);
                    }
                }
            }
	    //ImagenOriginal = Nueva_Imagen;
	    return Nueva_Imagen;
	}

}