/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcLances.java
 *
 * Created on 23/04/2012, 02:31:19
 */
package gui;

import controllers.ControllerCampania;
import controllers.ControllerLance;
import controllers.ControllerPpal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.Cajon;
import modelo.dataManager.CategoriaPoi;
import modelo.dataManager.Especie;
import modelo.dataManager.Lance;
import persistencia.BrokerCajon;
import persistencia.BrokerCampania;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerEspecie;
import persistencia.BrokerLance;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class PanelOpcLances extends javax.swing.JPanel {

    static PanelOpcLances unicaInstancia;
    private boolean modificandoCajon;
    private Cajon tempCajon;
    private DefaultTableModel modeloTablaLances = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{
                "idLance", "Fecha Inicio", "Fecha Fin", "Comentarios", "# Cajones"//, "Acciones"
            }) {

        Class[] types = new Class[]{
            java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class//, java.lang.Boolean.class
        };

        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
    };

    /** Creates new form PanelOpcLances */
    private PanelOpcLances() {
        initComponents();
        inicializador();
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
        panelLances = new org.jdesktop.swingx.JXPanel();
        panelTituloTabla = new org.jdesktop.swingx.JXPanel();
        lblTituloTabla = new org.jdesktop.swingx.JXLabel();
        panelTabla = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaLances = new org.jdesktop.swingx.JXTable();
        panelAccionesLances = new org.jdesktop.swingx.JXPanel();
        lblAccionesLances = new org.jdesktop.swingx.JXLabel();
        panelAcciones = new org.jdesktop.swingx.JXPanel();
        btnModificarLance = new org.jdesktop.swingx.JXHyperlink();
        btnGuardarCajon = new org.jdesktop.swingx.JXHyperlink();
        btnEliminarLance = new org.jdesktop.swingx.JXHyperlink();
        btnInsertarLance = new org.jdesktop.swingx.JXHyperlink();
        panelDatosLance = new org.jdesktop.swingx.JXPanel();
        panelFechaInicio = new org.jdesktop.swingx.JXPanel();
        lblFechaInicio = new org.jdesktop.swingx.JXLabel();
        campoFechaInicio = new javax.swing.JTextField();
        panelFechaFin = new org.jdesktop.swingx.JXPanel();
        lblFechaFin = new org.jdesktop.swingx.JXLabel();
        campoFechaFin = new javax.swing.JTextField();
        panelComentario = new org.jdesktop.swingx.JXPanel();
        lblComentario = new org.jdesktop.swingx.JXLabel();
        campoComentario = new org.jdesktop.swingx.JXTextField();
        panelBtnAgregar = new org.jdesktop.swingx.JXPanel();
        btnAdmCajones = new javax.swing.JButton();
        panelInferior = new org.jdesktop.swingx.JXPanel();
        btnInicFinLance = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 30));

        lblTitulo.setText("Administración de lances");
        lblTitulo.setFont(new java.awt.Font("Arial", 0, 18));
        panelSuperior.add(lblTitulo);

        add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelMedio.setMaximumSize(new java.awt.Dimension(500, 420));
        panelMedio.setMinimumSize(new java.awt.Dimension(500, 420));
        panelMedio.setPreferredSize(new java.awt.Dimension(500, 420));

        panelLances.setMaximumSize(new java.awt.Dimension(500, 400));
        panelLances.setMinimumSize(new java.awt.Dimension(500, 400));
        panelLances.setPreferredSize(new java.awt.Dimension(500, 500));

        panelTituloTabla.setMaximumSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setMinimumSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setPreferredSize(new java.awt.Dimension(500, 30));
        panelTituloTabla.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        lblTituloTabla.setText("Listado de lances durante la campaña:");
        lblTituloTabla.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelTituloTabla.add(lblTituloTabla);

        panelLances.add(panelTituloTabla);

        panelTabla.setMaximumSize(new java.awt.Dimension(500, 190));
        panelTabla.setMinimumSize(new java.awt.Dimension(500, 190));
        panelTabla.setPreferredSize(new java.awt.Dimension(500, 190));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(500, 170));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 170));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 170));

        tablaLances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha inicio", "Fecha fin", "Comentarios"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaLances.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(tablaLances);
        tablaLances.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaLances.getColumnModel().getColumn(0).setMinWidth(30);
        tablaLances.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaLances.getColumnModel().getColumn(1).setMinWidth(30);
        tablaLances.getColumnModel().getColumn(1).setPreferredWidth(30);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        panelLances.add(panelTabla);

        panelAccionesLances.setMaximumSize(new java.awt.Dimension(500, 30));
        panelAccionesLances.setMinimumSize(new java.awt.Dimension(500, 30));
        panelAccionesLances.setPreferredSize(new java.awt.Dimension(500, 30));

        lblAccionesLances.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAccionesLances.setText("Acciones sobre el lance elegido:");
        lblAccionesLances.setFont(new java.awt.Font("Tahoma", 0, 14));

        panelAcciones.setMaximumSize(new java.awt.Dimension(184, 30));
        panelAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        btnModificarLance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-editar.png"))); // NOI18N
        btnModificarLance.setText("");
        btnModificarLance.setToolTipText("Modificar");
        btnModificarLance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnModificarLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarLanceActionPerformed(evt);
            }
        });
        panelAcciones.add(btnModificarLance);

        btnGuardarCajon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-guardar.png"))); // NOI18N
        btnGuardarCajon.setText("");
        btnGuardarCajon.setToolTipText("Guardar cambios");
        btnGuardarCajon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardarCajon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCajonActionPerformed(evt);
            }
        });
        panelAcciones.add(btnGuardarCajon);

        btnEliminarLance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-eliminar.png"))); // NOI18N
        btnEliminarLance.setText("");
        btnEliminarLance.setToolTipText("Eliminar");
        btnEliminarLance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminarLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarLanceActionPerformed(evt);
            }
        });
        panelAcciones.add(btnEliminarLance);

        btnInsertarLance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-insertar.png"))); // NOI18N
        btnInsertarLance.setText("");
        btnInsertarLance.setToolTipText("Agregar");
        btnInsertarLance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnInsertarLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarLanceActionPerformed(evt);
            }
        });
        panelAcciones.add(btnInsertarLance);

        javax.swing.GroupLayout panelAccionesLancesLayout = new javax.swing.GroupLayout(panelAccionesLances);
        panelAccionesLances.setLayout(panelAccionesLancesLayout);
        panelAccionesLancesLayout.setHorizontalGroup(
            panelAccionesLancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLancesLayout.createSequentialGroup()
                .addComponent(lblAccionesLances, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(panelAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelAccionesLancesLayout.setVerticalGroup(
            panelAccionesLancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAccionesLances, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        panelLances.add(panelAccionesLances);

        panelDatosLance.setMaximumSize(new java.awt.Dimension(500, 32767));
        panelDatosLance.setMinimumSize(new java.awt.Dimension(500, 60));
        panelDatosLance.setPreferredSize(new java.awt.Dimension(500, 300));

        panelFechaInicio.setMaximumSize(new java.awt.Dimension(150, 50));
        panelFechaInicio.setMinimumSize(new java.awt.Dimension(150, 50));
        panelFechaInicio.setPreferredSize(new java.awt.Dimension(150, 50));
        panelFechaInicio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        lblFechaInicio.setText("Fecha inicio");
        lblFechaInicio.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelFechaInicio.add(lblFechaInicio);

        campoFechaInicio.setFont(new java.awt.Font("Tahoma", 0, 12));
        campoFechaInicio.setMaximumSize(new java.awt.Dimension(40, 20));
        campoFechaInicio.setMinimumSize(new java.awt.Dimension(40, 20));
        campoFechaInicio.setPreferredSize(new java.awt.Dimension(40, 20));
        panelFechaInicio.add(campoFechaInicio);

        panelDatosLance.add(panelFechaInicio);

        panelFechaFin.setMaximumSize(new java.awt.Dimension(150, 50));
        panelFechaFin.setMinimumSize(new java.awt.Dimension(150, 50));
        panelFechaFin.setPreferredSize(new java.awt.Dimension(150, 50));
        panelFechaFin.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        lblFechaFin.setText("Fecha fin");
        lblFechaFin.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelFechaFin.add(lblFechaFin);

        campoFechaFin.setFont(new java.awt.Font("Tahoma", 0, 12));
        campoFechaFin.setMaximumSize(new java.awt.Dimension(40, 20));
        campoFechaFin.setMinimumSize(new java.awt.Dimension(40, 20));
        campoFechaFin.setPreferredSize(new java.awt.Dimension(40, 20));
        panelFechaFin.add(campoFechaFin);

        panelDatosLance.add(panelFechaFin);

        panelComentario.setMaximumSize(new java.awt.Dimension(470, 50));
        panelComentario.setMinimumSize(new java.awt.Dimension(470, 50));
        panelComentario.setPreferredSize(new java.awt.Dimension(470, 50));
        panelComentario.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        lblComentario.setText("Comentarios sobre el lance:");
        lblComentario.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelComentario.add(lblComentario);

        campoComentario.setPreferredSize(new java.awt.Dimension(300, 20));
        campoComentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoComentarioActionPerformed(evt);
            }
        });
        panelComentario.add(campoComentario);

        panelDatosLance.add(panelComentario);

        panelBtnAgregar.setMaximumSize(new java.awt.Dimension(80, 50));
        panelBtnAgregar.setMinimumSize(new java.awt.Dimension(80, 50));
        panelBtnAgregar.setPreferredSize(new java.awt.Dimension(150, 50));
        panelBtnAgregar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        btnAdmCajones.setFont(new java.awt.Font("Tahoma", 0, 12));
        btnAdmCajones.setText("Agregar cajones");
        btnAdmCajones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmCajonesActionPerformed(evt);
            }
        });
        panelBtnAgregar.add(btnAdmCajones);

        panelDatosLance.add(panelBtnAgregar);

        panelLances.add(panelDatosLance);

        panelMedio.add(panelLances);

        add(panelMedio, java.awt.BorderLayout.CENTER);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 50));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 50));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 50));
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnInicFinLance.setFont(new java.awt.Font("Tahoma", 0, 12));
        btnInicFinLance.setText("Finalizar lance");
        btnInicFinLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicFinLanceActionPerformed(evt);
            }
        });
        panelInferior.add(btnInicFinLance);

        add(panelInferior, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicFinLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicFinLanceActionPerformed
        // TODO add your handling code here:
        //controllers.ControllerLance.getInstance().guardaLance();
    }//GEN-LAST:event_btnInicFinLanceActionPerformed

    private void btnAdmCajonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmCajonesActionPerformed
        // TODO add your handling code here:
        if (tablaLances.getSelectedRowCount() > 0) {
            VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcCajones.getInstance());
            Lance unLance = (Lance) tablaLances.getValueAt(tablaLances.getSelectedRow(), 0);
            PanelOpcCajones.getInstance().setTempLanceSeleccionado(unLance);
            PanelOpcCajones.getInstance().cargaGrillaCajones();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un lance primero");
        }
    }//GEN-LAST:event_btnAdmCajonesActionPerformed

    private void btnModificarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarLanceActionPerformed
        /*  habilitaPanelDatosCajones(true);
        setModificandoCajon(true);
        btnEliminarLance.setEnabled(false);
        btnInsertarLance.setEnabled(false);
        btnModificarLance.setEnabled(false);
        btnGuardarCajon.setEnabled(true);
        //cargo la fila seleccionada en el formulario
        if (tablaLances.getSelectedRowCount() != 0) {
        Cajon unCajon = (Cajon) tablaLances.getValueAt(tablaLances.getSelectedRow(), 0);
        campoFechaInicio.setText(String.valueOf(unCajon.getCantidad()));
        //ver el icono
        setTempCajon(unCajon);
        } else {
        habilitaPanelDatosCajones(false);
        setModificandoCajon(false);
        JOptionPane.showMessageDialog(null, "Seleccione un cajon primero");
        }*/
    }//GEN-LAST:event_btnModificarLanceActionPerformed

    private void btnGuardarCajonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCajonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarCajonActionPerformed

    private void btnEliminarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarLanceActionPerformed
        
        //tambien ahy que eliminar todos los cajones del lance 
        
        /* if (tablaLances.getSelectedRowCount() != 0) {
        int[] listaCajonesSeleccionados = tablaLances.getSelectedRows();
        if (JOptionPane.showConfirmDialog(null,
        "Desea eliminar " + listaCajonesSeleccionados.length + " cajones seleccionados?",
        "Eliminar cajones",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE) == 0) {
        int i = 0;
        while (i < listaCajonesSeleccionados.length) {
        Cajon unCajon = (Cajon) tablaLances.getValueAt(i, 0);
        ControllerLance.getInstance().borrarCajon(unCajon); //lo saca de la lista en memoria
        System.out.println("Se saco de la lista el cajon " + unCajon);
        //ControllerLance.getInstance().eliminaCajon(unCajon);
        i++;
        }
        cargaGrillaCajones();
        }
        }*/
    }//GEN-LAST:event_btnEliminarLanceActionPerformed

    private void btnInsertarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarLanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInsertarLanceActionPerformed

    private void campoComentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoComentarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoComentarioActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmCajones;
    private org.jdesktop.swingx.JXHyperlink btnEliminarLance;
    private org.jdesktop.swingx.JXHyperlink btnGuardarCajon;
    private javax.swing.JButton btnInicFinLance;
    private org.jdesktop.swingx.JXHyperlink btnInsertarLance;
    private org.jdesktop.swingx.JXHyperlink btnModificarLance;
    private org.jdesktop.swingx.JXTextField campoComentario;
    private javax.swing.JTextField campoFechaFin;
    private javax.swing.JTextField campoFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXLabel lblAccionesLances;
    private org.jdesktop.swingx.JXLabel lblComentario;
    private org.jdesktop.swingx.JXLabel lblFechaFin;
    private org.jdesktop.swingx.JXLabel lblFechaInicio;
    private org.jdesktop.swingx.JXLabel lblTitulo;
    private org.jdesktop.swingx.JXLabel lblTituloTabla;
    private org.jdesktop.swingx.JXPanel panelAcciones;
    private org.jdesktop.swingx.JXPanel panelAccionesLances;
    private org.jdesktop.swingx.JXPanel panelBtnAgregar;
    private org.jdesktop.swingx.JXPanel panelComentario;
    private org.jdesktop.swingx.JXPanel panelDatosLance;
    private org.jdesktop.swingx.JXPanel panelFechaFin;
    private org.jdesktop.swingx.JXPanel panelFechaInicio;
    private org.jdesktop.swingx.JXPanel panelInferior;
    private org.jdesktop.swingx.JXPanel panelLances;
    private org.jdesktop.swingx.JXPanel panelMedio;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTabla;
    private org.jdesktop.swingx.JXPanel panelTituloTabla;
    private org.jdesktop.swingx.JXTable tablaLances;
    // End of variables declaration//GEN-END:variables

    public static PanelOpcLances getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new PanelOpcLances();
        }
        return unicaInstancia;
    }

    //main de prueba 
    public static void main(String[] args) {
        javax.swing.JFrame elFrame = new javax.swing.JFrame();
        elFrame.setSize(500, 500);
        PanelOpcLances a = new PanelOpcLances();
        //cargaEspecies();
        elFrame.add(a);
        elFrame.setVisible(true);
    }

    private void inicializador() {
        cargaGrillaLances();
        habilitaPanelDatosCajones(false);
    }

    public void cargaGrillaLances() {
        modeloTablaLances.setRowCount(0);//vacia la tabla
        SimpleDateFormat horaCompleta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Lance unLance : BrokerLance.getInstance().getLancesCampaniaActualFromDB(AdministraCampanias.getInstance().getCampaniaEnCurso().getId())) {
            String fechaFin = "En curso";
            if (unLance.getfYHFin() != null) {
                fechaFin = horaCompleta.format(unLance.getfYHFin());
            }
            modeloTablaLances.addRow(new Object[]{
                        unLance,//<--Aca esta el objeto -muestra idLance
                        horaCompleta.format(unLance.getfYHIni()),
                        fechaFin,
                        unLance.getComentarios()
                    });
        }

        tablaLances.setModel(modeloTablaLances);
        ControllerPpal.getInstance().ocultarColJTable(tablaLances, 0);
    }

    private void habilitaPanelDatosCajones(boolean estado) {
        campoFechaInicio.setEnabled(estado);
        campoFechaInicio.setText("");
        //si lo deshabilito habilito la barra
        if (!estado) {
            btnEliminarLance.setEnabled(!estado);
            btnInsertarLance.setEnabled(!estado);
            btnModificarLance.setEnabled(!estado);
            btnGuardarCajon.setEnabled(estado);
        }
    }

    /**
     * @return the tempCajon
     */
    public Cajon getTempCajon() {
        return tempCajon;
    }

    public void setTxtBtnIniciaLance() {
        btnInicFinLance.setText("Iniciar lance");
    }

    public void setTxtBtnFinLance() {
        btnInicFinLance.setText("Finalizar lance");
    }
}