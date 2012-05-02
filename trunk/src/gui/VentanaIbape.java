/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VentanaIbape.java
 *
 * Created on 24/04/2012, 10:55:56
 */
package gui;

import java.awt.Dimension;
import java.util.Calendar;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;
import persistencia.BrokerConfig;
import persistencia.Logueador;
/**
 *
 * @author Sebastian
 */
public class VentanaIbape extends JXFrame {
    static VentanaIbape unicaInstancia;
    private MultiSplitLayout multiSplitLayout; 
    
    /** Creates new form VentanaIbape */
    private VentanaIbape() {
        setLookAndFeel();
        initComponents();
        inicializador();
        configuraEventoAlSalir();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPanePpal = new javax.swing.JSplitPane();
        panelBarraEstado = new org.jdesktop.swingx.JXPanel();
        lblBarraEstado = new org.jdesktop.swingx.JXLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(520, 560));

        splitPanePpal.setDividerSize(9);
        splitPanePpal.setPreferredSize(new java.awt.Dimension(700, 530));
        getContentPane().add(splitPanePpal, java.awt.BorderLayout.PAGE_START);

        panelBarraEstado.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBarraEstado.setMaximumSize(new java.awt.Dimension(32767, 30));
        panelBarraEstado.setMinimumSize(new java.awt.Dimension(0, 20));
        panelBarraEstado.setPreferredSize(new java.awt.Dimension(700, 30));
        panelBarraEstado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblBarraEstado.setText("--- Barra de Estado ---");
        lblBarraEstado.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelBarraEstado.add(lblBarraEstado);

        getContentPane().add(panelBarraEstado, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                VentanaIbape.getInstance().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXLabel lblBarraEstado;
    private org.jdesktop.swingx.JXPanel panelBarraEstado;
    private javax.swing.JSplitPane splitPanePpal;
    // End of variables declaration//GEN-END:variables


    private void inicializador() {
        //Create a split pane with the two scroll panes in it.
        //splitPane = new JSplitPane(JsplitPane.HORIZONTAL_SPLIT);
        
        //Configuramos los parametros del Logger
        PropertyConfigurator.configure( System.getProperty("user.dir")+ "\\lib\\log4j.properties");
        
        //Leemos el archivo de configuracion
        BrokerConfig.getInstance().leeArchivoConfig();
                                
        JPanel panelIzquierdo = PanelSelector.getInstance();
        JPanel panelDerecho = PanelOpcConfiguracion.getInstance();
        
        getSplitPanePpal().setLeftComponent(panelIzquierdo);
        ponerEnPanelDerecho(panelDerecho);
        getSplitPanePpal().setOneTouchExpandable(true);

    }

    /**
     * @return the SplitPanePpal
     */
    public javax.swing.JSplitPane getSplitPanePpal() {
        return splitPanePpal;
    }

    public static VentanaIbape getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new VentanaIbape();          
       }
       return unicaInstancia;
    }

    /**
     * @return the jXLabel1
     */
    public org.jdesktop.swingx.JXLabel getLblBarraEstado() {
        return lblBarraEstado;
    }

    /**
     * @param jXLabel1 the jXLabel1 to set
     */
    public void setLblBarraEstado(org.jdesktop.swingx.JXLabel jXLabel1) {
        this.lblBarraEstado = jXLabel1;
    }
    

    private boolean setLookAndFeel(){
        boolean sePudo=false;        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);            
            sePudo=true;             
        }
        catch (Exception e)
           { e.printStackTrace();  }
        return sePudo;
    }

    public void ponerEnPanelDerecho(JPanel panelNuevo) {
        getSplitPanePpal().setRightComponent(panelNuevo);
        getSplitPanePpal().setDividerLocation(170);
        getSplitPanePpal().setDividerSize(8);        
    }
    
    public void setTextoEnBarraEstado(String texto) {
        getLblBarraEstado().setText(texto);
    }
    

    
    public void actualizaConfiguracionGui(){
        
    }
    
    public void configuraEventoAlSalir(){
        //capturamos el evento de cierre para disparar las acciones que corresponden
        this.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
           public void windowClosing(java.awt.event.WindowEvent evt){
              controllers.ControladorPpal.getInstance().accionesAlSalir(); 
              System.exit(0);
           }
        });        
    }
    
}