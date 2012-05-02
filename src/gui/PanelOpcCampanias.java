/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcCampanias.java
 *
 * Created on 19/04/2012, 15:41:25
 */
package gui;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Sebastian
 */
public class PanelOpcCampanias extends javax.swing.JPanel {
    static PanelOpcCampanias unicaInstancia;
    boolean hayCampaniaEnCurso=false;
    Color colorOriginalBtn;
    //</editor-fold>
    /** Creates new form PanelOpcCampanias */
    private PanelOpcCampanias() {
        initComponents();
        colorOriginalBtn = btnComenzarFinalizarCampania.getBackground();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitulo = new org.jdesktop.swingx.JXPanel();
        lblTituloCampanias = new org.jdesktop.swingx.JXLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelCentro = new org.jdesktop.swingx.JXPanel();
        panelTablaCampanias = new org.jdesktop.swingx.JXPanel();
        panelTituloTabla = new org.jdesktop.swingx.JXPanel();
        lblTituloTabla = new org.jdesktop.swingx.JXLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCampanias = new org.jdesktop.swingx.JXTable();
        panelNuevaCampania = new org.jdesktop.swingx.JXPanel();
        panelTituloNuevaCampania = new org.jdesktop.swingx.JXPanel();
        lblNuevaCampania = new org.jdesktop.swingx.JXLabel();
        panelDatosNuevaCampania = new org.jdesktop.swingx.JXPanel();
        panelNombreCamp = new org.jdesktop.swingx.JXPanel();
        panelLblNombre = new org.jdesktop.swingx.JXPanel();
        lblNombre = new org.jdesktop.swingx.JXLabel();
        panelCampoNombre = new org.jdesktop.swingx.JXPanel();
        campoNombreCampania = new javax.swing.JTextField();
        panelBarcoCamp = new org.jdesktop.swingx.JXPanel();
        panelLbBarco = new org.jdesktop.swingx.JXPanel();
        lblBarco = new org.jdesktop.swingx.JXLabel();
        panelCampoBarco = new org.jdesktop.swingx.JXPanel();
        campoBarcoCampania = new javax.swing.JTextField();
        panelCapitanCamp = new org.jdesktop.swingx.JXPanel();
        panelLblCapitan = new org.jdesktop.swingx.JXPanel();
        lblCapitan = new org.jdesktop.swingx.JXLabel();
        panelCampoCapitan = new org.jdesktop.swingx.JXPanel();
        campoCapitanCampania = new javax.swing.JTextField();
        panelBtnCampania = new org.jdesktop.swingx.JXPanel();
        btnComenzarFinalizarCampania = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelTitulo.setMaximumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setMinimumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setPreferredSize(new java.awt.Dimension(500, 40));
        panelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        lblTituloCampanias.setText("Campañas");
        lblTituloCampanias.setFont(new java.awt.Font("Arial", 0, 18));
        panelTitulo.add(lblTituloCampanias);

        add(panelTitulo, java.awt.BorderLayout.NORTH);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(480, 410));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(480, 410));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(480, 410));

        panelCentro.setMaximumSize(new java.awt.Dimension(460, 410));
        panelCentro.setMinimumSize(new java.awt.Dimension(460, 410));
        panelCentro.setPreferredSize(new java.awt.Dimension(460, 410));
        panelCentro.setScrollableTracksViewportHeight(false);
        panelCentro.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelTablaCampanias.setMaximumSize(new java.awt.Dimension(450, 250));
        panelTablaCampanias.setMinimumSize(new java.awt.Dimension(450, 250));
        panelTablaCampanias.setPreferredSize(new java.awt.Dimension(450, 250));

        panelTituloTabla.setMaximumSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setMinimumSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setPreferredSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTituloTabla.setText("Listado de campañas en disco:");
        lblTituloTabla.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelTituloTabla.add(lblTituloTabla);

        panelTablaCampanias.add(panelTituloTabla);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(450, 220));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(450, 220));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 220));

        tablaCampanias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Fechas", "Nombre de Campaña", "Resumen", "Barco", "Capitan", "Acciones"
            }
        ));
        tablaCampanias.setPreferredScrollableViewportSize(new java.awt.Dimension(450, 360));
        tablaCampanias.setPreferredSize(new java.awt.Dimension(450, 72));
        jScrollPane1.setViewportView(tablaCampanias);
        tablaCampanias.getColumnModel().getColumn(1).setMinWidth(110);
        tablaCampanias.getColumnModel().getColumn(1).setPreferredWidth(110);
        tablaCampanias.getColumnModel().getColumn(5).setMinWidth(60);
        tablaCampanias.getColumnModel().getColumn(5).setPreferredWidth(60);
        tablaCampanias.getColumnModel().getColumn(5).setMaxWidth(60);

        panelTablaCampanias.add(jScrollPane1);

        panelCentro.add(panelTablaCampanias);

        panelNuevaCampania.setMaximumSize(new java.awt.Dimension(450, 160));
        panelNuevaCampania.setMinimumSize(new java.awt.Dimension(450, 160));
        panelNuevaCampania.setPreferredSize(new java.awt.Dimension(450, 160));
        panelNuevaCampania.setLayout(new java.awt.BorderLayout());

        panelTituloNuevaCampania.setMaximumSize(new java.awt.Dimension(450, 30));
        panelTituloNuevaCampania.setMinimumSize(new java.awt.Dimension(450, 30));
        panelTituloNuevaCampania.setPreferredSize(new java.awt.Dimension(450, 30));
        panelTituloNuevaCampania.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        lblNuevaCampania.setText("Nueva Campaña:");
        lblNuevaCampania.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelTituloNuevaCampania.add(lblNuevaCampania);

        panelNuevaCampania.add(panelTituloNuevaCampania, java.awt.BorderLayout.NORTH);

        panelDatosNuevaCampania.setMaximumSize(new java.awt.Dimension(450, 130));
        panelDatosNuevaCampania.setMinimumSize(new java.awt.Dimension(450, 130));
        panelDatosNuevaCampania.setPreferredSize(new java.awt.Dimension(450, 130));
        panelDatosNuevaCampania.setLayout(new java.awt.GridLayout(3, 1));

        panelNombreCamp.setMaximumSize(new java.awt.Dimension(450, 43));
        panelNombreCamp.setMinimumSize(new java.awt.Dimension(450, 43));
        panelNombreCamp.setPreferredSize(new java.awt.Dimension(450, 43));
        panelNombreCamp.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        panelLblNombre.setMaximumSize(new java.awt.Dimension(100, 43));
        panelLblNombre.setMinimumSize(new java.awt.Dimension(100, 43));
        panelLblNombre.setPreferredSize(new java.awt.Dimension(100, 43));
        panelLblNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 10));

        lblNombre.setText("Nombre");
        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelLblNombre.add(lblNombre);

        panelNombreCamp.add(panelLblNombre);

        panelCampoNombre.setMaximumSize(new java.awt.Dimension(350, 43));
        panelCampoNombre.setMinimumSize(new java.awt.Dimension(350, 43));
        panelCampoNombre.setPreferredSize(new java.awt.Dimension(350, 43));
        panelCampoNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        campoNombreCampania.setFont(new java.awt.Font("Tahoma", 0, 12));
        campoNombreCampania.setMaximumSize(new java.awt.Dimension(200, 21));
        campoNombreCampania.setMinimumSize(new java.awt.Dimension(200, 21));
        campoNombreCampania.setPreferredSize(new java.awt.Dimension(200, 21));
        panelCampoNombre.add(campoNombreCampania);

        panelNombreCamp.add(panelCampoNombre);

        panelDatosNuevaCampania.add(panelNombreCamp);

        panelBarcoCamp.setMaximumSize(new java.awt.Dimension(450, 43));
        panelBarcoCamp.setMinimumSize(new java.awt.Dimension(450, 43));
        panelBarcoCamp.setPreferredSize(new java.awt.Dimension(450, 43));
        panelBarcoCamp.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        panelLbBarco.setMaximumSize(new java.awt.Dimension(100, 43));
        panelLbBarco.setMinimumSize(new java.awt.Dimension(100, 43));
        panelLbBarco.setPreferredSize(new java.awt.Dimension(100, 43));
        panelLbBarco.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 10));

        lblBarco.setText("Barco");
        lblBarco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelLbBarco.add(lblBarco);

        panelBarcoCamp.add(panelLbBarco);

        panelCampoBarco.setMaximumSize(new java.awt.Dimension(350, 43));
        panelCampoBarco.setMinimumSize(new java.awt.Dimension(350, 43));
        panelCampoBarco.setPreferredSize(new java.awt.Dimension(350, 43));
        panelCampoBarco.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        campoBarcoCampania.setFont(new java.awt.Font("Tahoma", 0, 12));
        campoBarcoCampania.setMaximumSize(new java.awt.Dimension(200, 21));
        campoBarcoCampania.setMinimumSize(new java.awt.Dimension(200, 21));
        campoBarcoCampania.setPreferredSize(new java.awt.Dimension(200, 21));
        panelCampoBarco.add(campoBarcoCampania);

        panelBarcoCamp.add(panelCampoBarco);

        panelDatosNuevaCampania.add(panelBarcoCamp);

        panelCapitanCamp.setMaximumSize(new java.awt.Dimension(450, 43));
        panelCapitanCamp.setMinimumSize(new java.awt.Dimension(450, 43));
        panelCapitanCamp.setPreferredSize(new java.awt.Dimension(450, 43));
        panelCapitanCamp.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        panelLblCapitan.setMaximumSize(new java.awt.Dimension(100, 43));
        panelLblCapitan.setMinimumSize(new java.awt.Dimension(100, 43));
        panelLblCapitan.setPreferredSize(new java.awt.Dimension(100, 43));
        panelLblCapitan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 10));

        lblCapitan.setText("Capitan");
        lblCapitan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelLblCapitan.add(lblCapitan);

        panelCapitanCamp.add(panelLblCapitan);

        panelCampoCapitan.setMaximumSize(new java.awt.Dimension(350, 43));
        panelCampoCapitan.setMinimumSize(new java.awt.Dimension(350, 43));
        panelCampoCapitan.setPreferredSize(new java.awt.Dimension(350, 43));
        panelCampoCapitan.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        campoCapitanCampania.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        campoCapitanCampania.setMaximumSize(new java.awt.Dimension(200, 21));
        campoCapitanCampania.setMinimumSize(new java.awt.Dimension(200, 21));
        campoCapitanCampania.setPreferredSize(new java.awt.Dimension(200, 21));
        panelCampoCapitan.add(campoCapitanCampania);

        panelCapitanCamp.add(panelCampoCapitan);

        panelDatosNuevaCampania.add(panelCapitanCamp);

        panelNuevaCampania.add(panelDatosNuevaCampania, java.awt.BorderLayout.CENTER);

        panelCentro.add(panelNuevaCampania);

        jScrollPane2.setViewportView(panelCentro);

        add(jScrollPane2, java.awt.BorderLayout.LINE_END);

        panelBtnCampania.setMaximumSize(new java.awt.Dimension(500, 50));
        panelBtnCampania.setMinimumSize(new java.awt.Dimension(500, 50));
        panelBtnCampania.setPreferredSize(new java.awt.Dimension(500, 50));
        panelBtnCampania.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnComenzarFinalizarCampania.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnComenzarFinalizarCampania.setText("Iniciar campaña");
        btnComenzarFinalizarCampania.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComenzarFinalizarCampaniaActionPerformed(evt);
            }
        });
        panelBtnCampania.add(btnComenzarFinalizarCampania);

        add(panelBtnCampania, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

private void btnComenzarFinalizarCampaniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComenzarFinalizarCampaniaActionPerformed
    
    if (hayCampaniaEnCurso) {        
        if (JOptionPane.showConfirmDialog(null,
                "Atención! Está por finalizar la campaña en curso, está seguro?",
                "Finalizar campaña", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
                ) == 0) {
        controlaPanelNuevaCampania(hayCampaniaEnCurso);
        campoBarcoCampania.setText("");
        campoCapitanCampania.setText("");
        campoNombreCampania.setText("");
        btnComenzarFinalizarCampania.setText("Iniciar nueva campaña");
        btnComenzarFinalizarCampania.setBackground(colorOriginalBtn);        
        hayCampaniaEnCurso=false;
        }
    }
    else
    {
        boolean camposRequeridos = campoBarcoCampania.getText().length() > 2 && 
                campoCapitanCampania.getText().length() > 2  &&
                campoNombreCampania.getText().length() > 2;
        if (camposRequeridos) {            
            controlaPanelNuevaCampania(hayCampaniaEnCurso);
            btnComenzarFinalizarCampania.setText("Finalizar campaña");
            btnComenzarFinalizarCampania.setBackground(Color.orange);
            hayCampaniaEnCurso=true;
        }
        else
        { JOptionPane.showMessageDialog(null,"Complete los campos de Nueva campaña con datos válidos"); }
    }
        
}//GEN-LAST:event_btnComenzarFinalizarCampaniaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComenzarFinalizarCampania;
    private javax.swing.JTextField campoBarcoCampania;
    private javax.swing.JTextField campoCapitanCampania;
    private javax.swing.JTextField campoNombreCampania;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXLabel lblBarco;
    private org.jdesktop.swingx.JXLabel lblCapitan;
    private org.jdesktop.swingx.JXLabel lblNombre;
    private org.jdesktop.swingx.JXLabel lblNuevaCampania;
    private org.jdesktop.swingx.JXLabel lblTituloCampanias;
    private org.jdesktop.swingx.JXLabel lblTituloTabla;
    private org.jdesktop.swingx.JXPanel panelBarcoCamp;
    private org.jdesktop.swingx.JXPanel panelBtnCampania;
    private org.jdesktop.swingx.JXPanel panelCampoBarco;
    private org.jdesktop.swingx.JXPanel panelCampoCapitan;
    private org.jdesktop.swingx.JXPanel panelCampoNombre;
    private org.jdesktop.swingx.JXPanel panelCapitanCamp;
    private org.jdesktop.swingx.JXPanel panelCentro;
    private org.jdesktop.swingx.JXPanel panelDatosNuevaCampania;
    private org.jdesktop.swingx.JXPanel panelLbBarco;
    private org.jdesktop.swingx.JXPanel panelLblCapitan;
    private org.jdesktop.swingx.JXPanel panelLblNombre;
    private org.jdesktop.swingx.JXPanel panelNombreCamp;
    private org.jdesktop.swingx.JXPanel panelNuevaCampania;
    private org.jdesktop.swingx.JXPanel panelTablaCampanias;
    private org.jdesktop.swingx.JXPanel panelTitulo;
    private org.jdesktop.swingx.JXPanel panelTituloNuevaCampania;
    private org.jdesktop.swingx.JXPanel panelTituloTabla;
    private org.jdesktop.swingx.JXTable tablaCampanias;
    // End of variables declaration//GEN-END:variables
    
    private void controlaPanelNuevaCampania(boolean estado){
        lblNuevaCampania.setEnabled(estado);
        lblNombre.setEnabled(estado);
        campoNombreCampania.setEnabled(estado);
        lblBarco.setEnabled(estado);
        campoBarcoCampania.setEnabled(estado);
        lblCapitan.setEnabled(estado);
        campoCapitanCampania.setEnabled(estado);        
    }

    public static PanelOpcCampanias getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelOpcCampanias();          
       }
       return unicaInstancia;
    }    
    
/* codigo de prueba para poder probar un panel simplemente haciendo "Run File" sobre su clase 
   
     public static void main(String[] args) {
        javax.swing.JFrame elFrame = new javax.swing.JFrame();
        JPanel elPanel= new PanelOpcCampanias();
        elFrame.setSize(500,500);
        elFrame.add(elPanel); 
        elFrame.setVisible(true);
    }
*/ 
}