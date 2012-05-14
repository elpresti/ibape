/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dataManager;

/**
 *
 * @author Sebastian
 */
public class CategoriaPoi {
    private String titulo;
    private String pathIcono;
    
    public CategoriaPoi(){
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the pathIcono
     */
    public String getPathIcono() {
        return pathIcono;
    }

    /**
     * @param pathIcono the pathIcono to set
     */
    public void setPathIcono(String pathIcono) {
        this.pathIcono = pathIcono;
    }
    
}
