/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class Splash extends javax.swing.JFrame {

    /**
     * Creates new form Splash
     */
    public Splash() {
        VentanaIbape.getInstance();        
        initComponents();
        this.setVisible(true);
        panelLogo.setOpaque(false);
        //panelLogo.setForeground(null);
        panelBarraProgreso.setOpaque(false);        
        barraProgreso.setBorderPainted(true);
        barraProgreso.setForeground(new Color(50,50,153,100));
        barraProgreso.setStringPainted(true);
        //this.setSize(500, 500); 
        int posicionX = (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(getWidth()/2);
        int posicionY = Toolkit.getDefaultToolkit().getScreenSize().height/2-(getHeight()/2);
        this.setLocation(posicionX,posicionY);                
        run();        
        setVisible(false);
        dispose();
        VentanaIbape.getInstance().setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogo = new javax.swing.JPanel();
        lblLogo = new org.jdesktop.swingx.JXLabel();
        barraProgreso = new javax.swing.JProgressBar();
        panelBarraProgreso = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelLogo.setLayout(new java.awt.BorderLayout());

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoIbape.png"))); // NOI18N
        panelLogo.add(lblLogo, java.awt.BorderLayout.PAGE_START);
        panelLogo.add(barraProgreso, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelLogo, java.awt.BorderLayout.PAGE_END);

        panelBarraProgreso.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelBarraProgreso, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private org.jdesktop.swingx.JXLabel lblLogo;
    private javax.swing.JPanel panelBarraProgreso;
    private javax.swing.JPanel panelLogo;
    // End of variables declaration//GEN-END:variables


    public void run(){
        for (int i = 1;i<=13;i++){
            barraProgreso.setValue(i*10);
            try {
                Thread.sleep(300); 
            } catch (InterruptedException ex) {
                Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    
    /**
     * @param args the command line arguments
     */
/*    
    public static void main(String args[]) {       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash();
            }
        });
    }
*/
    public static void main(String args[]) {
        new Splash();
    }
    
}
