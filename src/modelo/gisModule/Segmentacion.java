package modelo.gisModule;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.util.*;
import java.util.List;
 
public class Segmentacion {
 
	private int ancho;
	private int alto;
	private int Test;
	 
	private int mitad = 0, pivote = 0;
	private BufferedImage ImagenOriginal;
	 
	private int color[];
	Colores colores;
 
	public Segmentacion(int ancho , int alto) {
       
        this.ancho = ancho;
        this.alto = alto;
       
        color = new int [6];
       
        // Rojo ...
        color[0] = new Color (255,0,0).getRGB();
   
        // Verde ...
        color[1] = new Color (0,255,0).getRGB();
       
        // Azul ...
        color[2] = new Color (0,0,255).getRGB();
       
        // Amarillo ...
        color[3] = new Color (255,255,0).getRGB();
       
        // Blanco ...
        color[4] = new Color (255,255,255).getRGB();
           
        // Negro ...
        color[5] = new Color (0,0,0).getRGB();
       
    } 



	public Segmentacion() {
		// TODO Auto-generated constructor stub
	}



	public BufferedImage recuadraImagen (BufferedImage ImageBfrd) {
		   
		   
	    for (int i = 0; i <ancho ; i ++) {
	   
		    ImageBfrd.setRGB(i,0,color[4]);
		    ImageBfrd.setRGB(i,alto-1,color[4]);
		}
	    for (int j = 0; j <alto ; j ++) {
	    	   
		    ImageBfrd.setRGB(0,j,color[4]);
		    ImageBfrd.setRGB(ancho-1,j,color[4]);
		}
	    
	   
	   
	 
	   
	    return ImageBfrd;
	}



	public BufferedImage escalaDeGrisesConColor(BufferedImage imagen, BufferedImage imagenGrisClaro, int color){
		int w = imagen.getWidth();
		int h = imagen.getHeight(null);
		Integer colorPintar= color;
		colores = new Colores();
		BufferedImage imagenRGB=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		if(colores.obtieneNumeroColor("Negro")==color){
			colorPintar = colores.obtieneNumeroColor("Blanco");
		}
		else if(colores.obtieneNumeroColor("Blanco")==color){
			colorPintar = colores.obtieneNumeroColor("Negro");
		}
		else if(colores.obtieneNumeroColor("Gris")==color){
			colorPintar = colores.obtieneNumeroColor("Negro");
		}
		for(int c=0;c<w;c++){
			for(int r=0;r<h;r++){
				if(colores.obtieneColor(imagen.getRGB(c, r)) == color){
//					System.out.println(colores.obtieneColor(imgg.getRGB(c, r)));
					imagenRGB.setRGB(c, r, new Colores().colorAHexadecimal(colorPintar));
					
				}else
					imagenRGB.setRGB(c, r, imagenGrisClaro.getRGB(c,r));
				
			}
		}
		return imagenRGB;
	}
	
	
	public BufferedImage escalaDeGrisesEnRGB(BufferedImage imagen){
//		int w = imagen.getWidth();
//		int h = imagen.getHeight(null);
//		BufferedImage imgg=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//		Graphics g = imgg.getGraphics();  
//		g.drawImage(imagen, 0, 0, null);
//		return imgg;
		int w = imagen.getWidth();
		int h = imagen.getHeight(null);
		BufferedImage imgg=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for(int c=0;c<w;c++){
			for(int r=0;r<h;r++){
				imgg.setRGB(c, r, imagen.getRGB(c,r));
			}
		}
		return imgg;
	}
	

	public BufferedImage escalaDeGrises(BufferedImage imagen){
		int w = imagen.getWidth();
		int h = imagen.getHeight(null);
		BufferedImage imgg=new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		for(int c=0;c<w;c++){
			for(int r=0;r<h;r++){
				imgg.setRGB(c, r, imagen.getRGB(c,r));
			}
		}
		return imgg;
	}



	public BufferedImage Bordes (BufferedImage ImageBfrd ) {
		   
	    BufferedImage Nueva_Imagen = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
	//    BufferedImage Nueva_Imagen = ImagenOriginal;
	 
	    for (int i = 1; i <ancho - 1 ; i ++) {
	        for ( int j = 1; j <alto - 1 ; j ++ ) {
	        	if (ImageBfrd.getRGB(i,j) == -1 && ImageBfrd.getRGB(i-1,j) != -1 && ImageBfrd.getRGB(i+1,j) == -1) {
			   
			            Nueva_Imagen.setRGB(i,j,color[0]);
			            
			   
			    } else if (ImageBfrd.getRGB(i,j) == -1 && ImageBfrd.getRGB(i+1,j) != -1 && ImageBfrd.getRGB(i-1,j) == -1) {
			       
			            Nueva_Imagen.setRGB(i,j,color[0]);
			   
			   
			    } else if (ImageBfrd.getRGB(i,j) == -1 && ImageBfrd.getRGB(i,j-1) != -1 && ImageBfrd.getRGB(i,j+1) == -1) {
			       
			            Nueva_Imagen.setRGB(i,j,color[0]);
			   
			   
			    } else if (ImageBfrd.getRGB(i,j) == -1 && ImageBfrd.getRGB(i,j+1) != -1 && ImageBfrd.getRGB(i,j-1) == -1) {
			       
			            Nueva_Imagen.setRGB(i,j,color[0]);
			   
			    }  else 
			    	Nueva_Imagen.setRGB(i,j,color[4]);
			 }
	    }
	   
	    return Nueva_Imagen;   
	}


	public  BufferedImage Binarizacion (BufferedImage ImageBfrd , int n ) {
	    
		int Contador = 0 ;
	    long Umbral = 500000;
		ancho = ImageBfrd.getWidth();
		alto = ImageBfrd.getHeight(null);
	   
		BufferedImage Nueva_Imagen = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB) ;
		//BufferedImage Nueva_Imagen = ImageBfrd;
		for (int i = 0; i <ancho ; i ++) {
	        for ( int j = 0; j <alto ; j ++ ) {
	        	if ( ImageBfrd.getRGB(i,j) <-Umbral * n) {
	               
	                Contador ++;
	                Nueva_Imagen.setRGB(i,j,color[5]);
	           
	            } else {
	           
	                Nueva_Imagen.setRGB(i,j,color[4]);
	           
	            } 
	       
	        } 
	   
	    }
	
		return Nueva_Imagen;
	
	}


////////////////////////////////////////////////////////




	public static Map<Integer,List<Pixel>> segmentaFinal (BufferedImage img, BufferedImage img2){
		Map<Integer,List<Pixel>> mapa = new HashMap<Integer,List<Pixel>>();
		Integer colorpixel = null;
		Colores col = new Colores();
		for(int i=0;i<img.getWidth();i++){
			for(int j=0;j<img.getHeight(null);j++){
				colorpixel = col.obtieneColor(img.getRGB(i, j));
				Pixel p = new Pixel();
				p.setX(i);
				p.setY(j);
				p.setColor(colorpixel);
				if(mapa.containsKey(colorpixel)){
					List<Pixel> l = mapa.get(colorpixel);
					l.add(p);
					mapa.put(colorpixel,l);
				}else{
					List<Pixel> list = new LinkedList<Pixel>();
					list.add(p);
					mapa.put(colorpixel, list);
				}
				img2.setRGB(i, j, new Colores().colorAHexadecimal(colorpixel));
			}
		}
		return mapa;
	}






	public BufferedImage  filtroMedia (BufferedImage img){
		BufferedImage imgMedia = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
		int ventanaDePixeles[] = new int[9]; 
		int rgbs[] = new int[3];
		float[] hsbvals = new float[3];
		int mediaR=0;
		int mediaG=0;
		int mediaB=0;
		for(int i=1;i<ancho-1;i++){
			for(int j=1;j<alto-1;j++){
				
					ventanaDePixeles[0] = img.getRGB(i-1,j-1);
					ventanaDePixeles[1] = img.getRGB(i-1,j);
					ventanaDePixeles[2] = img.getRGB(i-1,j+1);
					ventanaDePixeles[3] = img.getRGB(i,j-1);
					ventanaDePixeles[4] = 2*img.getRGB(i,j);
					ventanaDePixeles[5] = img.getRGB(i,j+1);
					ventanaDePixeles[6] = img.getRGB(i+1,j-1);
					ventanaDePixeles[7] = img.getRGB(i+1,j);
					ventanaDePixeles[8] = img.getRGB(i+1,j+1);
					
				for(int k=0;k<9;k++){	
					rgbs = Colores.obtieneRGB(ventanaDePixeles[k]);
					mediaR +=rgbs[0];
					mediaG +=rgbs[1];
					mediaB +=rgbs[2];
				}
				mediaR=mediaR/10;
				mediaG=mediaG/10;
				mediaB=mediaB/10;
				Color.RGBtoHSB(mediaR, mediaG, mediaB, hsbvals);
				imgMedia.setRGB(i,j,Color.HSBtoRGB(hsbvals[0], hsbvals[1], hsbvals[2])); 
	
			}
		}
		return imgMedia;
	}



	public BufferedImage  filtroMediana (BufferedImage img){
		BufferedImage imgMediana = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB);
		int mediana[] = new int[100];
		
		for(int i=1;i<ancho-1;i++){
			for(int j=1;j<alto-1;j++){
				mediana[0]=img.getRGB(i-1,j-1); 
				mediana[1]=img.getRGB(i-1,j); 
				mediana[2]=img.getRGB(i-1,j+1); 
				mediana[3]=img.getRGB(i,j-1); 
				mediana[4]=img.getRGB(i,j);
				mediana[5]=img.getRGB(i,j+1);
				mediana[6]=img.getRGB(i+1,j-1);
				mediana[7]=img.getRGB(i+1,j);
				mediana[8]=img.getRGB(i+1,j+1);
				ordenaQuicksort(mediana);	
					
				imgMediana.setRGB(i,j,mediana[4]); 
	
			}
		}
		return imgMediana;
	}



	public void ordenaQuicksort (int [] array){
		QuickSort(array, 0, array.length-1);
	}








   
   
       
	public int[] ObtenerVector (BufferedImage ImagenRGB ) {
	   
	    int [] VectorRGB  = new int[alto*ancho];
	    int Contador = 0 ;
	    
	        for (int i = 0; i <ancho ; i ++) {
	        for ( int j = 0; j <alto ; j ++ ) {
	           
	           VectorRGB[Contador] = ImagenRGB.getRGB(i,j);
	              Contador ++ ;
	 
	        }
	        }
	       
	       
	        return VectorRGB;   
	}
 
 
	public int [] QuickSort(int [] Arreglo,  int Izq, int Der) {
		mitad = 0;
	    int i = Izq;
	    int j = Der;
	
	 
	mitad = Math.round(( Izq + Der )/2);
	    pivote = Arreglo[mitad];
	    while (i<=j) {
	    //Recorremos primero de Izq a Der, hasta que un elemento de la Izquierda sea Mayor al Pivote.
	        while (Arreglo[i]<pivote) {
	            i++;
	       
	        }
	        while (Arreglo[j]>pivote) {
	    //Despues a la Der a Izq, hasta que un elemento de la Derecha sea Menor al Pivote.
	            j--;
	           
	           
	        }
	       
	       
	    // Si un elemento de la Izq es Menor o Igual a un ELemento de la Der , estos se Intercambian...
	        if (i<=j) {
	            
	            int Temp = Arreglo[i];
	            Arreglo[i] = Arreglo[j];
	            Arreglo[j] = Temp;
	           
	           
	            i++;
	            j--;
	        } 
	    } 
	       
	       
	    if ( Izq <j ) {
	       
	        QuickSort(Arreglo, Izq, j);
	       
	    }
	    if (i <Der ) {
	       
	        QuickSort(Arreglo, i, Der);
	    }
	    return Arreglo;
	 
	} 
   
 
   
	public BufferedImage  VectorToImagen(int[] VectorRGB) {
	   
	    int Contador = 0 ;
	   
	    BufferedImage Nueva_Imagen = new BufferedImage(ancho,alto,BufferedImage.TYPE_INT_RGB) ;
	       
	       
	        for (int i = 0; i <ancho ; i ++) {
	        for ( int j = 0; j <alto ; j ++ ) {
	           
	           Nueva_Imagen.setRGB(i,j,VectorRGB[Contador]);
	              Contador ++ ;
	 
	        }
	        }
	       
	    return Nueva_Imagen;
	   
	}  
	
}