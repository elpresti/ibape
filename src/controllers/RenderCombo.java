/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Component;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Necrophagist
 */
public class RenderCombo extends JLabel implements ListCellRenderer {

    Hashtable<Object, ImageIcon> elementos;
    //ImageIcon imgnull = new ImageIcon(this.getClass().getResource("../imgnull.png"));

    public RenderCombo() {
        elementos = new Hashtable<Object, ImageIcon>();
        for (String rutaIcono : ControllerPpal.getInstance().listadoIconosCatPOI()) {
            //ImageIcon img1 = new ImageIcon(this.getClass().getResource(rutaIcono));
            ImageIcon img1 = new ImageIcon(rutaIcono);
            //etc
            agregarElemento(rutaIcono, img1);
        }

        //ImageIcon img1=new ImageIcon(this.getClass().getResource("../img1.png"));
        //etc
        //agregarElemento("Icono1",img1);
        //etc
    }

    private void agregarElemento(Object o, ImageIcon img) {
        elementos.put(o, img);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (elementos.get(value) != null) {
            setIcon(elementos.get(value));
            setText("" + value);
        } else {
            System.out.println("No se encontro el icono");
            //setIcon(imgnull);
            //setText("" + value);
        }
        return this;
    }
}
