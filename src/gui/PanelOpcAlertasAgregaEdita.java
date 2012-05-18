/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcAlertasAgregaEdita.java
 *
 * Created on 15/04/2012, 01:29:40
 */
package gui;

/**
 *
 * @author Sebastian
 */
public class PanelOpcAlertasAgregaEdita extends javax.swing.JPanel {
    static PanelOpcAlertasAgregaEdita unicaInstancia;

    /** Creates new form PanelOpcAlertasAgregaEdita */
    private PanelOpcAlertasAgregaEdita() {
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

        panelAgregaEdita = new org.jdesktop.swingx.JXPanel();
        panelSuperior = new org.jdesktop.swingx.JXPanel();
        lblAlertaNueva = new org.jdesktop.swingx.JXLabel();
        panelMedio = new org.jdesktop.swingx.JXPanel();
        panelDatosAlerta = new org.jdesktop.swingx.JXPanel();
        panelNombre = new org.jdesktop.swingx.JXPanel();
        panelLblNombre = new org.jdesktop.swingx.JXPanel();
        lblNombre = new org.jdesktop.swingx.JXLabel();
        panelCampoNombre = new org.jdesktop.swingx.JXPanel();
        campoNombre = new javax.swing.JTextField();
        panelEstado = new org.jdesktop.swingx.JXPanel();
        panelLblEstado = new org.jdesktop.swingx.JXPanel();
        lblEstado = new org.jdesktop.swingx.JXLabel();
        panelComboEstado = new org.jdesktop.swingx.JXPanel();
        comboEstado = new javax.swing.JComboBox();
        panelTxtCondiciones = new org.jdesktop.swingx.JXPanel();
        lblCondiciones = new org.jdesktop.swingx.JXLabel();
        panelTablaCondiciones = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCondiciones = new org.jdesktop.swingx.JXTable();
        panelNuevaCondicion = new org.jdesktop.swingx.JXPanel();
        panelTituloCondicion = new org.jdesktop.swingx.JXPanel();
        lblTituloNuevaCondicion = new org.jdesktop.swingx.JXLabel();
        panelDatosCondicion = new org.jdesktop.swingx.JXPanel();
        panelVariable = new org.jdesktop.swingx.JXPanel();
        panelLblVariable = new org.jdesktop.swingx.JXPanel();
        lblVariable = new org.jdesktop.swingx.JXLabel();
        panelComboVariable = new org.jdesktop.swingx.JXPanel();
        jComboBox1 = new javax.swing.JComboBox();
        panelRelacion = new org.jdesktop.swingx.JXPanel();
        panelLblRelacion = new org.jdesktop.swingx.JXPanel();
        lblRelacion = new org.jdesktop.swingx.JXLabel();
        panelComboRelacion = new org.jdesktop.swingx.JXPanel();
        jComboBox2 = new javax.swing.JComboBox();
        panelValMin = new org.jdesktop.swingx.JXPanel();
        panelLblValMin = new org.jdesktop.swingx.JXPanel();
        lblValMin = new org.jdesktop.swingx.JXLabel();
        panelCampoValMin = new org.jdesktop.swingx.JXPanel();
        jTextField1 = new javax.swing.JTextField();
        panelValMax = new org.jdesktop.swingx.JXPanel();
        panelLblValMax = new org.jdesktop.swingx.JXPanel();
        lblValMax = new org.jdesktop.swingx.JXLabel();
        panelCampoValMax = new org.jdesktop.swingx.JXPanel();
        jTextField2 = new javax.swing.JTextField();
        panelBtnAgrega = new org.jdesktop.swingx.JXPanel();
        btnAgregar = new javax.swing.JButton();
        panelInferior = new org.jdesktop.swingx.JXPanel();
        btnVolver = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 600));
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelAgregaEdita.setPreferredSize(new java.awt.Dimension(500, 600));
        panelAgregaEdita.setLayout(new java.awt.BorderLayout());

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 30));

        lblAlertaNueva.setText("Alerta Nueva");
        lblAlertaNueva.setFont(new java.awt.Font("Tahoma", 0, 18));
        panelSuperior.add(lblAlertaNueva);

        panelAgregaEdita.add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelMedio.setMaximumSize(new java.awt.Dimension(500, 520));
        panelMedio.setMinimumSize(new java.awt.Dimension(500, 520));
        panelMedio.setLayout(new java.awt.BorderLayout());

        panelDatosAlerta.setMaximumSize(new java.awt.Dimension(500, 100));
        panelDatosAlerta.setMinimumSize(new java.awt.Dimension(500, 100));
        panelDatosAlerta.setLayout(new java.awt.GridLayout(3, 1));

        panelNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        panelLblNombre.setMaximumSize(new java.awt.Dimension(100, 33));
        panelLblNombre.setMinimumSize(new java.awt.Dimension(100, 33));
        panelLblNombre.setPreferredSize(new java.awt.Dimension(100, 33));
        panelLblNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblNombre.setText("Nombre");
        lblNombre.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblNombre.add(lblNombre);

        panelNombre.add(panelLblNombre);

        panelCampoNombre.setMaximumSize(new java.awt.Dimension(400, 33));
        panelCampoNombre.setMinimumSize(new java.awt.Dimension(400, 33));
        panelCampoNombre.setPreferredSize(new java.awt.Dimension(400, 33));
        panelCampoNombre.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        campoNombre.setFont(new java.awt.Font("Tahoma", 0, 14));
        campoNombre.setText("Ingrese aqui el nombre de la alerta");
        campoNombre.setMaximumSize(new java.awt.Dimension(300, 23));
        campoNombre.setMinimumSize(new java.awt.Dimension(300, 23));
        campoNombre.setPreferredSize(new java.awt.Dimension(300, 23));
        panelCampoNombre.add(campoNombre);

        panelNombre.add(panelCampoNombre);

        panelDatosAlerta.add(panelNombre);

        panelEstado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        panelLblEstado.setMaximumSize(new java.awt.Dimension(100, 33));
        panelLblEstado.setMinimumSize(new java.awt.Dimension(100, 33));
        panelLblEstado.setPreferredSize(new java.awt.Dimension(100, 33));
        panelLblEstado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblEstado.setText("Estado");
        lblEstado.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblEstado.add(lblEstado);

        panelEstado.add(panelLblEstado);

        panelComboEstado.setMaximumSize(new java.awt.Dimension(400, 33));
        panelComboEstado.setMinimumSize(new java.awt.Dimension(400, 33));
        panelComboEstado.setPreferredSize(new java.awt.Dimension(400, 33));
        panelComboEstado.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        comboEstado.setFont(new java.awt.Font("Tahoma", 0, 14));
        comboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Desactivada", "Activada" }));
        comboEstado.setMaximumSize(new java.awt.Dimension(150, 23));
        comboEstado.setMinimumSize(new java.awt.Dimension(150, 23));
        comboEstado.setPreferredSize(new java.awt.Dimension(150, 23));
        panelComboEstado.add(comboEstado);

        panelEstado.add(panelComboEstado);

        panelDatosAlerta.add(panelEstado);

        panelTxtCondiciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblCondiciones.setText("Condiciones que deben cumplirse para que ocurra la alerta:");
        lblCondiciones.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelTxtCondiciones.add(lblCondiciones);

        panelDatosAlerta.add(panelTxtCondiciones);

        panelMedio.add(panelDatosAlerta, java.awt.BorderLayout.PAGE_START);

        panelTablaCondiciones.setMaximumSize(new java.awt.Dimension(500, 220));
        panelTablaCondiciones.setMinimumSize(new java.awt.Dimension(500, 220));
        panelTablaCondiciones.setPreferredSize(new java.awt.Dimension(500, 220));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 200));

        tablaCondiciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Descripcion", "Accion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaCondiciones);
        tablaCondiciones.getColumnModel().getColumn(0).setMinWidth(400);
        tablaCondiciones.getColumnModel().getColumn(0).setPreferredWidth(400);
        tablaCondiciones.getColumnModel().getColumn(0).setMaxWidth(400);
        tablaCondiciones.getColumnModel().getColumn(1).setResizable(false);
        tablaCondiciones.getColumnModel().getColumn(1).setPreferredWidth(50);

        panelTablaCondiciones.add(jScrollPane1);

        panelMedio.add(panelTablaCondiciones, java.awt.BorderLayout.CENTER);

        panelNuevaCondicion.setPreferredSize(new java.awt.Dimension(500, 200));
        panelNuevaCondicion.setLayout(new javax.swing.BoxLayout(panelNuevaCondicion, javax.swing.BoxLayout.PAGE_AXIS));

        panelTituloCondicion.setMaximumSize(new java.awt.Dimension(500, 30));
        panelTituloCondicion.setMinimumSize(new java.awt.Dimension(500, 30));
        panelTituloCondicion.setPreferredSize(new java.awt.Dimension(500, 30));
        panelTituloCondicion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblTituloNuevaCondicion.setText("Nueva Condición:");
        lblTituloNuevaCondicion.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelTituloCondicion.add(lblTituloNuevaCondicion);

        panelNuevaCondicion.add(panelTituloCondicion);

        panelDatosCondicion.setMaximumSize(new java.awt.Dimension(500, 170));
        panelDatosCondicion.setMinimumSize(new java.awt.Dimension(500, 170));
        panelDatosCondicion.setLayout(new java.awt.GridLayout(5, 1));

        panelVariable.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelLblVariable.setMaximumSize(new java.awt.Dimension(150, 34));
        panelLblVariable.setMinimumSize(new java.awt.Dimension(150, 34));
        panelLblVariable.setPreferredSize(new java.awt.Dimension(150, 34));
        panelLblVariable.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblVariable.setText("Variable a monitorear");
        lblVariable.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblVariable.add(lblVariable);

        panelVariable.add(panelLblVariable);

        panelComboVariable.setMaximumSize(new java.awt.Dimension(350, 34));
        panelComboVariable.setMinimumSize(new java.awt.Dimension(350, 34));
        panelComboVariable.setPreferredSize(new java.awt.Dimension(350, 34));
        panelComboVariable.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Profundidad", "Latitud", "Longitud", "Fecha actual", "Hora actual", "Temperatura" }));
        jComboBox1.setMaximumSize(new java.awt.Dimension(200, 23));
        jComboBox1.setMinimumSize(new java.awt.Dimension(200, 23));
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 23));
        panelComboVariable.add(jComboBox1);

        panelVariable.add(panelComboVariable);

        panelDatosCondicion.add(panelVariable);

        panelRelacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelLblRelacion.setMaximumSize(new java.awt.Dimension(150, 34));
        panelLblRelacion.setMinimumSize(new java.awt.Dimension(150, 34));
        panelLblRelacion.setPreferredSize(new java.awt.Dimension(150, 34));
        panelLblRelacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblRelacion.setText("Relacion de variable");
        lblRelacion.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblRelacion.add(lblRelacion);

        panelRelacion.add(panelLblRelacion);

        panelComboRelacion.setMaximumSize(new java.awt.Dimension(350, 34));
        panelComboRelacion.setMinimumSize(new java.awt.Dimension(350, 34));
        panelComboRelacion.setPreferredSize(new java.awt.Dimension(350, 34));
        panelComboRelacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jComboBox2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Igual a", "Mayor a", "Menor a", "Entre" }));
        jComboBox2.setMaximumSize(new java.awt.Dimension(150, 23));
        jComboBox2.setMinimumSize(new java.awt.Dimension(150, 23));
        jComboBox2.setPreferredSize(new java.awt.Dimension(150, 23));
        panelComboRelacion.add(jComboBox2);

        panelRelacion.add(panelComboRelacion);

        panelDatosCondicion.add(panelRelacion);

        panelValMin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelLblValMin.setMaximumSize(new java.awt.Dimension(150, 34));
        panelLblValMin.setMinimumSize(new java.awt.Dimension(150, 34));
        panelLblValMin.setPreferredSize(new java.awt.Dimension(150, 34));
        panelLblValMin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblValMin.setText("Valor mínimo");
        lblValMin.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblValMin.add(lblValMin);

        panelValMin.add(panelLblValMin);

        panelCampoValMin.setMaximumSize(new java.awt.Dimension(350, 34));
        panelCampoValMin.setMinimumSize(new java.awt.Dimension(350, 34));
        panelCampoValMin.setPreferredSize(new java.awt.Dimension(350, 34));
        panelCampoValMin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14));
        jTextField1.setMaximumSize(new java.awt.Dimension(150, 23));
        jTextField1.setMinimumSize(new java.awt.Dimension(150, 23));
        jTextField1.setPreferredSize(new java.awt.Dimension(150, 23));
        panelCampoValMin.add(jTextField1);

        panelValMin.add(panelCampoValMin);

        panelDatosCondicion.add(panelValMin);

        panelValMax.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelLblValMax.setMaximumSize(new java.awt.Dimension(150, 34));
        panelLblValMax.setMinimumSize(new java.awt.Dimension(150, 34));
        panelLblValMax.setPreferredSize(new java.awt.Dimension(150, 34));
        panelLblValMax.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        lblValMax.setText("Valor máximo");
        lblValMax.setFont(new java.awt.Font("Tahoma", 0, 14));
        panelLblValMax.add(lblValMax);

        panelValMax.add(panelLblValMax);

        panelCampoValMax.setMaximumSize(new java.awt.Dimension(350, 34));
        panelCampoValMax.setMinimumSize(new java.awt.Dimension(350, 34));
        panelCampoValMax.setPreferredSize(new java.awt.Dimension(350, 34));
        panelCampoValMax.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14));
        jTextField2.setMaximumSize(new java.awt.Dimension(150, 23));
        jTextField2.setMinimumSize(new java.awt.Dimension(150, 23));
        jTextField2.setPreferredSize(new java.awt.Dimension(150, 23));
        panelCampoValMax.add(jTextField2);

        panelValMax.add(panelCampoValMax);

        panelDatosCondicion.add(panelValMax);

        panelBtnAgrega.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAgregar.setText("Agregar Condición");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        panelBtnAgrega.add(btnAgregar);

        panelDatosCondicion.add(panelBtnAgrega);

        panelNuevaCondicion.add(panelDatosCondicion);

        panelMedio.add(panelNuevaCondicion, java.awt.BorderLayout.PAGE_END);

        panelAgregaEdita.add(panelMedio, java.awt.BorderLayout.CENTER);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 50));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 50));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 50));
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        btnVolver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        panelInferior.add(btnVolver);

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14));
        btnGuardar.setText("Guardar");
        panelInferior.add(btnGuardar);

        panelAgregaEdita.add(panelInferior, java.awt.BorderLayout.SOUTH);

        add(panelAgregaEdita, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_btnAgregarActionPerformed

private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
// TODO add your handling code here:
    setVisible(false);
    PanelOpcAlertas.getInstance().getPanelAlertasPpal().setVisible(true);
}//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JComboBox comboEstado;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private org.jdesktop.swingx.JXLabel lblAlertaNueva;
    private org.jdesktop.swingx.JXLabel lblCondiciones;
    private org.jdesktop.swingx.JXLabel lblEstado;
    private org.jdesktop.swingx.JXLabel lblNombre;
    private org.jdesktop.swingx.JXLabel lblRelacion;
    private org.jdesktop.swingx.JXLabel lblTituloNuevaCondicion;
    private org.jdesktop.swingx.JXLabel lblValMax;
    private org.jdesktop.swingx.JXLabel lblValMin;
    private org.jdesktop.swingx.JXLabel lblVariable;
    private org.jdesktop.swingx.JXPanel panelAgregaEdita;
    private org.jdesktop.swingx.JXPanel panelBtnAgrega;
    private org.jdesktop.swingx.JXPanel panelCampoNombre;
    private org.jdesktop.swingx.JXPanel panelCampoValMax;
    private org.jdesktop.swingx.JXPanel panelCampoValMin;
    private org.jdesktop.swingx.JXPanel panelComboEstado;
    private org.jdesktop.swingx.JXPanel panelComboRelacion;
    private org.jdesktop.swingx.JXPanel panelComboVariable;
    private org.jdesktop.swingx.JXPanel panelDatosAlerta;
    private org.jdesktop.swingx.JXPanel panelDatosCondicion;
    private org.jdesktop.swingx.JXPanel panelEstado;
    private org.jdesktop.swingx.JXPanel panelInferior;
    private org.jdesktop.swingx.JXPanel panelLblEstado;
    private org.jdesktop.swingx.JXPanel panelLblNombre;
    private org.jdesktop.swingx.JXPanel panelLblRelacion;
    private org.jdesktop.swingx.JXPanel panelLblValMax;
    private org.jdesktop.swingx.JXPanel panelLblValMin;
    private org.jdesktop.swingx.JXPanel panelLblVariable;
    private org.jdesktop.swingx.JXPanel panelMedio;
    private org.jdesktop.swingx.JXPanel panelNombre;
    private org.jdesktop.swingx.JXPanel panelNuevaCondicion;
    private org.jdesktop.swingx.JXPanel panelRelacion;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTablaCondiciones;
    private org.jdesktop.swingx.JXPanel panelTituloCondicion;
    private org.jdesktop.swingx.JXPanel panelTxtCondiciones;
    private org.jdesktop.swingx.JXPanel panelValMax;
    private org.jdesktop.swingx.JXPanel panelValMin;
    private org.jdesktop.swingx.JXPanel panelVariable;
    private org.jdesktop.swingx.JXTable tablaCondiciones;
    // End of variables declaration//GEN-END:variables
 
    public static PanelOpcAlertasAgregaEdita getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelOpcAlertasAgregaEdita();          
       }
       return unicaInstancia;
    }

}