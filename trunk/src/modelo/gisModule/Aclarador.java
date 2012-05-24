package modelo.gisModule;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Aclarador{
	public BufferedImage aclara(BufferedImage imagen){
		
		int w = imagen.getWidth();
		int h = imagen.getHeight(null);
		
		BufferedImage imgf=new BufferedImage(w,h, BufferedImage.TYPE_INT_RGB);
		int valorRGB;
		int valoresRGB[] = new int[3];
		float valoresHSB[] = new float[3];
		Colores col = new Colores();
		int negro = col.obtieneNumeroColor("Negro");
		int marron = col.obtieneNumeroColor("Marron");
		for(int r=0;r<w;r++){
			
			for(int c=0;c<h;c++){
				valorRGB = imagen.getRGB(r,c);
				
//				System.out.println(r+","+c);
				if(col.obtieneColor(valorRGB)!=negro){
					if(col.obtieneColor(imagen.getRGB(r,c))!=marron){
				
//					if(r==41 && c==190){
//						System.out.println("El color es: "+Colores.decideColor(valoresRGB[0], valoresRGB[1], valoresRGB[2]));
//						int ii=41;
//						int jj=190;
//						Colores col = new Colores();
//						System.out.println(col.coloresMap.get(Colores.obtieneColor(imagen.getRGB(ii, jj))));
//						System.out.println(imagen.getWidth());
//						System.out.println(imagen.getHeight());
//						for(int i=ii;i<ii+40;i++){
//							for(int j=jj;j<(jj+40);j++){
//								imagen.setRGB(i, j, 0x000000);
//							}
//						}
//					
//					}
					valoresRGB = Colores.obtieneRGB(valorRGB);
					Color.RGBtoHSB(valoresRGB[0], valoresRGB[1], valoresRGB[2], valoresHSB);
					
					valoresHSB[1] = valoresHSB[1] - (float) (0.1);
					valoresHSB[2] = valoresHSB[2] + (float) (0.2);
					
					if (valoresHSB[2]>1){
						valoresHSB[2]=1;
					}
					if (valoresHSB[1]<0){
						valoresHSB[1]=0;
					}
									
					if ((valoresHSB[1]>0.4)){
						valoresHSB[1]=(float) 1.0;
						valoresHSB[2]=(float) 1.0;
						
					}
					
					valorRGB = Color.HSBtoRGB(valoresHSB[0], valoresHSB[1], valoresHSB[2]);
					imgf.setRGB(r, c, valorRGB);
					}else{
						imgf.setRGB(r, c, 0x61210B);
					}
				}else{
					imgf.setRGB(r, c, 0x000000);
				}
			}
						
		}
		
		return imgf;
	}
	
}