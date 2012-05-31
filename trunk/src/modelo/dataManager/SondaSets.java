/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class SondaSets {
    private int frecuencia; // LOW/1=MID/HIGH (rango de valores posibles: 0-255)
    private int ganancia; // (rango de valores posibles: 0-255)
    private int stc; // STC (Valor de Ganancia variable) (rango de valores posibles: 0-255)
    private int lineaBlanca; // WL (White Line) (rango de valores posibles: 0-255)
    private int velPantalla; // GS (G.SPEEP: Velocidad Pantalla) (rango de valores posibles: 0-255, 2=1/2)
    private int escala; // Escala (Unit) [centimetros] (rango de valores posibles: 200-600000)  (este valor multiplicado por 2.5 da la escala en centímetros)
    private int shift; // Desplazamiento [centimetros] (rango de valores posibles: 0-59800) (este valor multiplicado por 2.5 da el desplazamiento de 0 en centímetros)
    private int expander; // Tamaño de expander del fondo (rango de valores posibles: 0-32, 0=OFF) (valor en metros del expander)
    private int unidadDeEscala; // 0= metros ,1= brasas, y 2= pies usados para calcular la escala
    static SondaSets unicaInstancia;
    
    private SondaSets (){}
    
    public static SondaSets getInstance(){
        if (unicaInstancia == null){
            unicaInstancia=new SondaSets();
        }
        return unicaInstancia;
    }

    /**
     * @return the frecuencia
     */
    public int getFrecuencia() {
        return frecuencia;
    }

    /**
     * @param frecuencia the frecuencia to set
     */
    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    /**
     * @return the ganancia
     */
    public int getGanancia() {
        return ganancia;
    }

    /**
     * @param ganancia the ganancia to set
     */
    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }

    /**
     * @return the stc
     */
    public int getStc() {
        return stc;
    }

    /**
     * @param stc the stc to set
     */
    public void setStc(int stc) {
        this.stc = stc;
    }

    /**
     * @return the lineaBlanca
     */
    public int getLineaBlanca() {
        return lineaBlanca;
    }

    /**
     * @param lineaBlanca the lineaBlanca to set
     */
    public void setLineaBlanca(int lineaBlanca) {
        this.lineaBlanca = lineaBlanca;
    }

    /**
     * @return the velPantalla
     */
    public int getVelPantalla() {
        return velPantalla;
    }

    /**
     * @param velPantalla the velPantalla to set
     */
    public void setVelPantalla(int velPantalla) {
        this.velPantalla = velPantalla;
    }

    /**
     * @return the escala
     */
    public int getEscala() {
        return escala;
    }

    /**
     * @param escala the escala to set
     */
    public void setEscala(int escala) {
        this.escala = escala;
    }

    /**
     * @return the shift
     */
    public int getShift() {
        return shift;
    }

    /**
     * @param shift the shift to set
     */
    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * @return the expander
     */
    public int getExpander() {
        return expander;
    }

    /**
     * @param expander the expander to set
     */
    public void setExpander(int expander) {
        this.expander = expander;
    }

    /**
     * @return the unidadDeEscala
     */
    public int getUnidadDeEscala() {
        return unidadDeEscala;
    }

    /**
     * @param unidadDeEscala the unidadDeEscala to set
     */
    public void setUnidadDeEscala(int unidadDeEscala) {
        this.unidadDeEscala = unidadDeEscala;
    }




}
