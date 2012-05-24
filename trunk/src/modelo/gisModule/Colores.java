package modelo.gisModule;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Colores {
	private Map<Integer, String> coloresMap = new HashMap<Integer,String>();
	public Colores(){
		coloresMap.put(0, "Rojo");
		coloresMap.put(1, "Naranja");
		coloresMap.put(2, "Marron");
		coloresMap.put(3, "Amarillo");
		coloresMap.put(4, "Verde");
		coloresMap.put(5, "Celeste");
		coloresMap.put(6, "Azul");
		coloresMap.put(7, "Violeta");
		coloresMap.put(8, "Rosa");
		coloresMap.put(9, "Blanco");
		coloresMap.put(10, "Gris");
		coloresMap.put(11, "Negro");
		
	}
	
	public int colorAHexadecimal(Integer color){
		Integer valor = null;
		if(color == 0){//Rojo
			valor = 0xFF0000;
		}else if(color == 1){//Naranja
			valor = 0xFF8000;
		}else if(color == 2){//Marron
			valor = 0x61210B;//0x800000;
		}else if(color == 3){//Amarillo
			valor = 0xFFFF00;
		}else if(color == 4){//Verde
			valor = 0x00FF00;
		}else if(color == 5){//Celeste
			valor = 0x00FFFF;
		}else if(color == 6){//Azul
			valor = 0x0000FF;
		}else if(color == 7){//Violeta
			valor = 0xEE82EE;
		}else if(color == 8){//Rosa
			valor = 0xFF1493;
		}else if(color == 9){//Blanco
			valor = 0xFFFFFF;
		}else if(color == 10){//Gris
			valor = 0x848484;
		}else if(color == 11){//Negro
			valor = 0x000000;
		
		}
		return valor;
	}
	
	public Integer obtieneNumeroColor (String nombreColor){
		Integer numColor=null;
		for(int i=0;i<coloresMap.keySet().size();i++){
			if(coloresMap.get(i)==nombreColor){
				numColor=i;
				break;
			}
		}
		return numColor;
	}
	
	public static Integer decideColor(float h, float s, float v){
		Integer color = null;
		float matiz = h*360;
		float saturacion = s*100;
		float brillo = v*100;
		boolean enc = false;
		
		if (((saturacion<9 && brillo>97)||(saturacion<6 && brillo>89))&&!enc){
			//			System.out.println("Color BLANCO");
			color = 9;
			enc=true;
		}else if (((brillo<9)||(saturacion<5)&&(brillo<17))&&!enc){
//			System.out.println("Color NEGRO");
			color = 11;
//			System.out.println("AQUI");
			enc=true;
		}else if (((saturacion<=10 && brillo>30&& brillo<70)||(saturacion<=6 && brillo>30&& brillo<=90)||(saturacion<=20 && brillo<=40)||(saturacion<15 && brillo<44)||(saturacion<10 && brillo<70)||(saturacion<5 && brillo<90)) && !enc){
//			System.out.println("Color GRIS");
			color = 10;
			enc=true;
			
//			//TODO marron
//		}else if ((matiz>25 && matiz<31 && ((saturacion>31 && saturacion<60 && brillo > 30 && brillo < 82)|| 
//				(saturacion>31 && brillo > 30 && brillo < 55)))){
//				color = 2;
//				enc=true;
//		}
//		else if((matiz>12 && matiz<37 && ((saturacion>35 && brillo > 20 && brillo < 74))&&!enc)){
//			if (matiz<25 && brillo>64) color=1;
//			else color = 2; 
//			enc=true;
//		
//		//			System.out.println("Color Marron");
					
					
		}else if (((matiz>=305 && matiz<347) || 
				(matiz>=347 && matiz<353 && saturacion<82) || (matiz>=353 && matiz<356 && saturacion<77) || (matiz>=356 && saturacion<72) ||
				(matiz<7 && saturacion<68))&&!enc)  {
//			System.out.println("Color Rosa");
			color = 8;
			enc=true;
		}else if ((matiz<=14.0 || matiz>=347) &&!enc){
	//			System.out.println("Color ROJO");
				if(brillo<21) color = 11;
				else if(saturacion<39 &&saturacion>15 &&brillo>89) color =8;
				else if(saturacion<=15 &&brillo>90) color =9;
				else if(saturacion<15) color=10;
				else color = 0;
				enc=true;
				
		}else if ((matiz>=14.0 && matiz<41)&&!enc){
	//			System.out.println("Color NARANJA");
				color = 1;
				if(saturacion>45&&brillo>20&&brillo<40) color=2;
				enc=true;
		}else if ((matiz>=41 && matiz<71)&&!enc){
//			System.out.println("Color AMARILLO");
			color = 3;
			if(matiz>63 && brillo<86) color=4;
			enc=true;
		
		
		
			
		}else if ((matiz>=71 && matiz<165)&&!enc){
//			System.out.println("Color Verde");
			color = 4;
			enc=true;
		
		}else if (((matiz>=165 && matiz<190 && brillo>14) || (matiz<208 && matiz>168 && saturacion>15 && saturacion<36&& brillo>80))&&!enc){
//			System.out.println("Color Celeste");
			color = 5;
			enc=true;
		}else if ((matiz>=190 && matiz<262)&&!enc){
//			System.out.println("Color Azul");
			color = 6;
			if((matiz>240 && saturacion<60)||(matiz>250&&saturacion<70)) color = 7;//violeta
			enc=true;
		}else if (((matiz>=262 && matiz<305)|| (matiz>248 && matiz<333 && brillo>15 && brillo<75))&&!enc){
//			System.out.println("Color Violeta");
			color = 7;
			
			enc=true;
		}else{
			color = 11;
		}
		return color;
	}
	
	public static int[] obtieneRGB (int valor){
		int[] values = new int[3];
		values[0]   = (valor & 0x00ff0000) >> 16;
		values[1] = (valor & 0x0000ff00) >> 8;
		values[2]  =  valor & 0x000000ff;
		return values;
	}
	
	public static int getRGB (int[] rgbs){
		int valor;
		valor = rgbs[0] & 0x11ff1111;
		valor = valor &(rgbs[1] & 0x1111ff11);
		valor = valor &(rgbs[2] & 0x111111ff);
		return valor;
	}
	
	public Integer obtieneColor(Integer valor){
		int rgbs[] = new int[3];;
		float hsb[] = new float[3];
		Integer colorpixel = null;
		rgbs = obtieneRGB(valor);
		Color.RGBtoHSB(rgbs[0], rgbs[1], rgbs[2], hsb);
		colorpixel = Colores.decideColor(hsb[0], hsb[1], hsb[2]);
		return colorpixel;
	}
	public Map<Integer, String> getColoresMap(){
		return coloresMap;
	}
}