/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcExportar.java
 *
 * Created on 23/04/2012, 19:56:57
 */
package gui;

/**
 *
 * @author Sebastian
 */
public class PanelOpcExportar extends javax.swing.JPanel {
    static PanelOpcExportar unicaInstancia;

    /** Creates new form PanelOpcExportar */
    private PanelOpcExportar() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new org.jdesktop.swingx.JXPanel();
        lblTitulo = new org.jdesktop.swingx.JXLabel();
        panelMedio = new org.jdesktop.swingx.JXPanel();
        panelTablaCampanias = new org.jdesktop.swingx.JXPanel();
        panelTituloTabla = new org.jdesktop.swingx.JXPanel();
        lblTituloTabla = new org.jdesktop.swingx.JXLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCampanias = new org.jdesktop.swingx.JXTable();
        panelParametros = new org.jdesktop.swingx.JXPanel();
        panelRuta = new org.jdesktop.swingx.JXPanel();
        panelLblRuta = new org.jdesktop.swingx.JXPanel();
        lblRuta = new org.jdesktop.swingx.JXLabel();
        panelCampoRuta = new org.jdesktop.swingx.JXPanel();
        campoRuta = new javax.swing.JTextField();
        btnExaminar = new javax.swing.JButton();
        panelFormato = new org.jdesktop.swingx.JXPanel();
        panelLblFormato = new org.jdesktop.swingx.JXPanel();
        lblFormato = new org.jdesktop.swingx.JXLabel();
        panelComboFormato = new org.jdesktop.swingx.JXPanel();
        comboFormato = new javax.swing.JComboBox();
        panelInferior = new org.jdesktop.swingx.JXPanel();
        btnExportar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 30));

        lblTitulo.setText("Exportar datos");
        lblTitulo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        panelSuperior.add(lblTitulo);

        add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelTablaCampanias.setMaximumSize(new java.awt.Dimension(450, 180));
        panelTablaCampanias.setMinimumSize(new java.awt.Dimension(450, 180));
        panelTablaCampanias.setPreferredSize(new java.awt.Dimension(450, 180));

        panelTituloTabla.setMaximumSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setMinimumSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setPreferredSize(new java.awt.Dimension(450, 30));
        panelTituloTabla.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTituloTabla.setText("Elija la campaña que desea exportar:");
        lblTituloTabla.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelTituloTabla.add(lblTituloTabla);

        panelTablaCampanias.add(panelTituloTabla);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(450, 220));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(450, 220));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 135));

        tablaCampanias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Elegir", "Fechas", "Campaña", "Resumen", "Pesca", "Alertas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaCampanias);
        tablaCampanias.getColumnModel().getColumn(0).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(0).setPreferredWidth(20);
        tablaCampanias.getColumnModel().getColumn(4).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(4).setPreferredWidth(20);
        tablaCampanias.getColumnModel().getColumn(5).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(5).setPreferredWidth(20);

        panelTablaCampanias.add(jScrollPane1);

        panelMedio.add(panelTablaCampanias);

        panelParametros.setMaximumSize(new java.awt.Dimension(500, 100));
        panelParametros.setMinimumSize(new java.awt.Dimension(500, 100));
        panelParametros.setPreferredSize(new java.awt.Dimension(500, 100));
        panelParametros.setLayout(new java.awt.GridLayout(2, 1));

        panelRuta.setMaximumSize(new java.awt.Dimension(500, 50));
        panelRuta.setMinimumSize(new java.awt.Dimension(500, 50));
        panelRuta.setPreferredSize(new java.awt.Dimension(500, 50));
        panelRuta.setLayout(new javax.swing.BoxLayout(panelRuta, javax.swing.BoxLayout.LINE_AXIS));

        panelLblRuta.setMaximumSize(new java.awt.Dimension(150, 50));
        panelLblRuta.setMinimumSize(new java.awt.Dimension(150, 50));
        panelLblRuta.setPreferredSize(new java.awt.Dimension(150, 50));
        panelLblRuta.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 15));

        lblRuta.setText("Ruta");
        lblRuta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelLblRuta.add(lblRuta);

        panelRuta.add(panelLblRuta);

        panelCampoRuta.setMaximumSize(new java.awt.Dimension(350, 50));
        panelCampoRuta.setMinimumSize(new java.awt.Dimension(350, 50));
        panelCampoRuta.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 12));

        campoRuta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        campoRuta.setMaximumSize(new java.awt.Dimension(200, 20));
        campoRuta.setMinimumSize(new java.awt.Dimension(200, 20));
        campoRuta.setPreferredSize(new java.awt.Dimension(200, 20));
        panelCampoRuta.add(campoRuta);

        btnExaminar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExaminar.setText("Examinar");
        btnExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarActionPerformed(evt);
            }
        });
        panelCampoRuta.add(btnExaminar);

        panelRuta.add(panelCampoRuta);

        panelParametros.add(panelRuta);

        panelFormato.setMaximumSize(new java.awt.Dimension(500, 50));
        panelFormato.setMinimumSize(new java.awt.Dimension(500, 50));
        panelFormato.setPreferredSize(new java.awt.Dimension(500, 50));
        panelFormato.setLayout(new javax.swing.BoxLayout(panelFormato, javax.swing.BoxLayout.LINE_AXIS));

        panelLblFormato.setMaximumSize(new java.awt.Dimension(150, 50));
        panelLblFormato.setMinimumSize(new java.awt.Dimension(150, 50));
        panelLblFormato.setPreferredSize(new java.awt.Dimension(150, 50));
        panelLblFormato.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 15));

        lblFormato.setText("Formato");
        lblFormato.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelLblFormato.add(lblFormato);

        panelFormato.add(panelLblFormato);

        panelComboFormato.setMaximumSize(new java.awt.Dimension(350, 50));
        panelComboFormato.setMinimumSize(new java.awt.Dimension(350, 50));
        panelComboFormato.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 13));

        comboFormato.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboFormato.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".kml", ".xml" }));
        comboFormato.setMaximumSize(new java.awt.Dimension(100, 20));
        comboFormato.setMinimumSize(new java.awt.Dimension(100, 20));
        comboFormato.setPreferredSize(new java.awt.Dimension(100, 20));
        panelComboFormato.add(comboFormato);

        panelFormato.add(panelComboFormato);

        panelParametros.add(panelFormato);

        panelMedio.add(panelParametros);

        add(panelMedio, java.awt.BorderLayout.CENTER);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 50));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 50));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 50));
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnExportar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExportar.setText("Exportar");
        panelInferior.add(btnExportar);

        add(panelInferior, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

private void btnExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_btnExaminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExaminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JTextField campoRuta;
    private javax.swing.JComboBox comboFormato;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXLabel lblFormato;
    private org.jdesktop.swingx.JXLabel lblRuta;
    private org.jdesktop.swingx.JXLabel lblTitulo;
    private org.jdesktop.swingx.JXLabel lblTituloTabla;
    private org.jdesktop.swingx.JXPanel panelCampoRuta;
    private org.jdesktop.swingx.JXPanel panelComboFormato;
    private org.jdesktop.swingx.JXPanel panelFormato;
    private org.jdesktop.swingx.JXPanel panelInferior;
    private org.jdesktop.swingx.JXPanel panelLblFormato;
    private org.jdesktop.swingx.JXPanel panelLblRuta;
    private org.jdesktop.swingx.JXPanel panelMedio;
    private org.jdesktop.swingx.JXPanel panelParametros;
    private org.jdesktop.swingx.JXPanel panelRuta;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTablaCampanias;
    private org.jdesktop.swingx.JXPanel panelTituloTabla;
    private org.jdesktop.swingx.JXTable tablaCampanias;
    // End of variables declaration//GEN-END:variables

    
    public static PanelOpcExportar getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelOpcExportar();          
       }
       return unicaInstancia;
    }

}
