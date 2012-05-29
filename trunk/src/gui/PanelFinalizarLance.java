/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelFinalizarLance.java
 *
 * Created on 23/04/2012, 02:31:19
 */
package gui;

/**
 *
 * @author Sebastian
 */
public class PanelFinalizarLance extends javax.swing.JPanel {
    static PanelFinalizarLance unicaInstancia;
    /** Creates new form PanelFinalizarLance */
    private PanelFinalizarLance() {
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
        panelCajones = new org.jdesktop.swingx.JXPanel();
        panelTituloTabla = new org.jdesktop.swingx.JXPanel();
        lblTituloTabla = new org.jdesktop.swingx.JXLabel();
        panelTabla = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCajones = new org.jdesktop.swingx.JXTable();
        panelEspecie = new org.jdesktop.swingx.JXPanel();
        panelCantCajones = new org.jdesktop.swingx.JXPanel();
        lblCantCajones = new org.jdesktop.swingx.JXLabel();
        campoCajones = new javax.swing.JTextField();
        panelComboEspecie = new org.jdesktop.swingx.JXPanel();
        lblEspecie = new org.jdesktop.swingx.JXLabel();
        comboEspecies = new javax.swing.JComboBox();
        panelBtnAgregar = new org.jdesktop.swingx.JXPanel();
        jButton1 = new javax.swing.JButton();
        panelSuceso = new org.jdesktop.swingx.JXPanel();
        lblSuceso = new org.jdesktop.swingx.JXLabel();
        comboSuceso = new javax.swing.JComboBox();
        panelComentarios = new org.jdesktop.swingx.JXPanel();
        lblComentarios = new org.jdesktop.swingx.JXLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        panelInferior = new org.jdesktop.swingx.JXPanel();
        btnGuardarLance = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 30));

        lblTitulo.setText("Finalizar lance");
        lblTitulo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        panelSuperior.add(lblTitulo);

        add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelMedio.setMaximumSize(new java.awt.Dimension(500, 420));
        panelMedio.setMinimumSize(new java.awt.Dimension(500, 420));
        panelMedio.setPreferredSize(new java.awt.Dimension(500, 420));
        panelMedio.setLayout(new java.awt.BorderLayout());

        panelCajones.setMaximumSize(new java.awt.Dimension(500, 250));
        panelCajones.setMinimumSize(new java.awt.Dimension(500, 250));
        panelCajones.setPreferredSize(new java.awt.Dimension(500, 250));
        panelCajones.setLayout(new java.awt.BorderLayout());

        panelTituloTabla.setMaximumSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setMinimumSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setPreferredSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        lblTituloTabla.setText("Cajones recogidos en el lance:");
        lblTituloTabla.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelTituloTabla.add(lblTituloTabla);

        panelCajones.add(panelTituloTabla, java.awt.BorderLayout.NORTH);

        panelTabla.setMaximumSize(new java.awt.Dimension(500, 170));
        panelTabla.setMinimumSize(new java.awt.Dimension(500, 170));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(500, 170));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 170));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 170));

        tablaCajones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "# Cajones", "Especie", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaCajones);
        tablaCajones.getColumnModel().getColumn(0).setMinWidth(40);
        tablaCajones.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaCajones.getColumnModel().getColumn(1).setMinWidth(350);
        tablaCajones.getColumnModel().getColumn(1).setPreferredWidth(350);
        tablaCajones.getColumnModel().getColumn(2).setMinWidth(30);
        tablaCajones.getColumnModel().getColumn(2).setPreferredWidth(30);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTablaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
            .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelTablaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelCajones.add(panelTabla, java.awt.BorderLayout.CENTER);

        panelEspecie.setMaximumSize(new java.awt.Dimension(500, 50));
        panelEspecie.setMinimumSize(new java.awt.Dimension(500, 50));
        panelEspecie.setPreferredSize(new java.awt.Dimension(500, 50));
        panelEspecie.setLayout(new javax.swing.BoxLayout(panelEspecie, javax.swing.BoxLayout.LINE_AXIS));

        panelCantCajones.setMaximumSize(new java.awt.Dimension(150, 50));
        panelCantCajones.setMinimumSize(new java.awt.Dimension(150, 50));
        panelCantCajones.setPreferredSize(new java.awt.Dimension(150, 50));
        panelCantCajones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        lblCantCajones.setText("Cant. Cajones");
        lblCantCajones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelCantCajones.add(lblCantCajones);

        campoCajones.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        campoCajones.setMaximumSize(new java.awt.Dimension(40, 20));
        campoCajones.setMinimumSize(new java.awt.Dimension(40, 20));
        campoCajones.setPreferredSize(new java.awt.Dimension(40, 20));
        panelCantCajones.add(campoCajones);

        panelEspecie.add(panelCantCajones);

        panelComboEspecie.setMaximumSize(new java.awt.Dimension(270, 50));
        panelComboEspecie.setMinimumSize(new java.awt.Dimension(270, 50));
        panelComboEspecie.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        lblEspecie.setText("Especie");
        lblEspecie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelComboEspecie.add(lblEspecie);

        comboEspecies.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboEspecies.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Abadejo", "Anchoita", "Merluza", "Calamar" }));
        comboEspecies.setMaximumSize(new java.awt.Dimension(200, 20));
        comboEspecies.setMinimumSize(new java.awt.Dimension(200, 20));
        comboEspecies.setPreferredSize(new java.awt.Dimension(200, 20));
        panelComboEspecie.add(comboEspecies);

        panelEspecie.add(panelComboEspecie);

        panelBtnAgregar.setMaximumSize(new java.awt.Dimension(80, 50));
        panelBtnAgregar.setMinimumSize(new java.awt.Dimension(80, 50));
        panelBtnAgregar.setPreferredSize(new java.awt.Dimension(80, 50));
        panelBtnAgregar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Agregar");
        panelBtnAgregar.add(jButton1);

        panelEspecie.add(panelBtnAgregar);

        panelCajones.add(panelEspecie, java.awt.BorderLayout.SOUTH);

        panelMedio.add(panelCajones, java.awt.BorderLayout.NORTH);

        panelSuceso.setMaximumSize(new java.awt.Dimension(500, 50));
        panelSuceso.setMinimumSize(new java.awt.Dimension(500, 50));
        panelSuceso.setPreferredSize(new java.awt.Dimension(500, 50));
        panelSuceso.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));

        lblSuceso.setText("Suceso ocurrido");
        lblSuceso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelSuceso.add(lblSuceso);

        comboSuceso.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboSuceso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lista categorias POI" }));
        comboSuceso.setMaximumSize(new java.awt.Dimension(200, 20));
        comboSuceso.setMinimumSize(new java.awt.Dimension(200, 20));
        comboSuceso.setPreferredSize(new java.awt.Dimension(200, 20));
        panelSuceso.add(comboSuceso);

        panelMedio.add(panelSuceso, java.awt.BorderLayout.CENTER);

        panelComentarios.setMaximumSize(new java.awt.Dimension(500, 120));
        panelComentarios.setMinimumSize(new java.awt.Dimension(500, 120));
        panelComentarios.setPreferredSize(new java.awt.Dimension(500, 120));
        panelComentarios.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lblComentarios.setText("Comentarios del lance:");
        lblComentarios.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelComentarios.add(lblComentarios);

        jScrollPane2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(500, 70));

        txtComentarios.setColumns(20);
        txtComentarios.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtComentarios.setRows(5);
        jScrollPane2.setViewportView(txtComentarios);

        panelComentarios.add(jScrollPane2);

        panelMedio.add(panelComentarios, java.awt.BorderLayout.SOUTH);

        add(panelMedio, java.awt.BorderLayout.CENTER);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 50));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 50));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 50));
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnGuardarLance.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnGuardarLance.setText("Guardar lance");
        panelInferior.add(btnGuardarLance);

        add(panelInferior, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarLance;
    private javax.swing.JTextField campoCajones;
    private javax.swing.JComboBox comboEspecies;
    private javax.swing.JComboBox comboSuceso;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXLabel lblCantCajones;
    private org.jdesktop.swingx.JXLabel lblComentarios;
    private org.jdesktop.swingx.JXLabel lblEspecie;
    private org.jdesktop.swingx.JXLabel lblSuceso;
    private org.jdesktop.swingx.JXLabel lblTitulo;
    private org.jdesktop.swingx.JXLabel lblTituloTabla;
    private org.jdesktop.swingx.JXPanel panelBtnAgregar;
    private org.jdesktop.swingx.JXPanel panelCajones;
    private org.jdesktop.swingx.JXPanel panelCantCajones;
    private org.jdesktop.swingx.JXPanel panelComboEspecie;
    private org.jdesktop.swingx.JXPanel panelComentarios;
    private org.jdesktop.swingx.JXPanel panelEspecie;
    private org.jdesktop.swingx.JXPanel panelInferior;
    private org.jdesktop.swingx.JXPanel panelMedio;
    private org.jdesktop.swingx.JXPanel panelSuceso;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTabla;
    private org.jdesktop.swingx.JXPanel panelTituloTabla;
    private org.jdesktop.swingx.JXTable tablaCajones;
    private javax.swing.JTextArea txtComentarios;
    // End of variables declaration//GEN-END:variables


    public static PanelFinalizarLance getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelFinalizarLance();          
       }
       return unicaInstancia;
    }

}