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

import controllers.ControllerLance;
import controllers.ControllerPpal;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dataManager.Campania;
import modelo.dataManager.Lance;
import modelo.dataManager.Punto;
import persistencia.BrokerCajon;
import persistencia.BrokerLance;

/**
 *
 * @author Sebastian
 */
public class PanelOpcLances extends javax.swing.JPanel {

    static PanelOpcLances unicaInstancia;
    private boolean modificandoLance;
    private Lance tempLance;
    private Campania tempCampania;
    private DefaultTableModel modeloTablaLances = new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{
                "idLance", "Fecha Inicio", "Fecha Fin", "Comentarios", "Cant. Cajones"//, "Acciones"
            }) {

        boolean[] canEdit = new boolean[]{
            false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
        }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        panelTodo = new org.jdesktop.swingx.JXPanel();
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
        btnGuardarLance = new org.jdesktop.swingx.JXHyperlink();
        btnEliminarLance = new org.jdesktop.swingx.JXHyperlink();
        btnInsertarLance = new org.jdesktop.swingx.JXHyperlink();
        btnCancelarLance = new javax.swing.JButton();
        panelDatosLance = new org.jdesktop.swingx.JXPanel();
        panelLatitud = new org.jdesktop.swingx.JXPanel();
        lblLatitudI = new org.jdesktop.swingx.JXLabel();
        campoLatitudI = new javax.swing.JTextField();
        lblLatitudF = new org.jdesktop.swingx.JXLabel();
        campoLatitudF = new javax.swing.JTextField();
        panelLongitud = new org.jdesktop.swingx.JXPanel();
        lblLongitudI = new org.jdesktop.swingx.JXLabel();
        campoLongitudI = new javax.swing.JTextField();
        lblLongitudF = new org.jdesktop.swingx.JXLabel();
        campoLongitudF = new javax.swing.JTextField();
        panelFechaInicio = new org.jdesktop.swingx.JXPanel();
        lblFechaInicio = new org.jdesktop.swingx.JXLabel();
        comboFechaInicio = new javax.swing.JSpinner();
        panelFechaFin = new org.jdesktop.swingx.JXPanel();
        lblFechaFin = new org.jdesktop.swingx.JXLabel();
        comboFechaFin = new javax.swing.JSpinner();
        panelComentario = new org.jdesktop.swingx.JXPanel();
        lblComentario = new org.jdesktop.swingx.JXLabel();
        campoComentario = new org.jdesktop.swingx.JXTextField();
        panelBtnAdmCajones = new org.jdesktop.swingx.JXPanel();
        btnAdmCajones = new javax.swing.JButton();
        panelInferior = new org.jdesktop.swingx.JXPanel();
        btnInicFinLance = new javax.swing.JButton();
        btnVolverAdmCampanias = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(520, 500));
        setMinimumSize(new java.awt.Dimension(520, 500));
        setPreferredSize(new java.awt.Dimension(525, 500));
        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(525, 602));

        panelTodo.setPreferredSize(new java.awt.Dimension(500, 600));
        panelTodo.setScrollableTracksViewportHeight(false);
        panelTodo.setLayout(new java.awt.BorderLayout());

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 32767));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 30));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 30));

        lblTitulo.setText("Administración de lances");
        lblTitulo.setFont(new java.awt.Font("Arial", 0, 18));
        lblTitulo.setTextAlignment(org.jdesktop.swingx.JXLabel.TextAlignment.CENTER);
        panelSuperior.add(lblTitulo);

        panelTodo.add(panelSuperior, java.awt.BorderLayout.NORTH);

        panelMedio.setPreferredSize(new java.awt.Dimension(500, 500));

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

        tablaLances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaLances.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablaLancesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablaLances);

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
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
        panelAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 5));

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

        btnGuardarLance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-guardar.png"))); // NOI18N
        btnGuardarLance.setText("");
        btnGuardarLance.setToolTipText("Guardar cambios");
        btnGuardarLance.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnGuardarLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarLanceActionPerformed(evt);
            }
        });
        panelAcciones.add(btnGuardarLance);

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

        btnCancelarLance.setText("Cancelar");
        btnCancelarLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarLanceActionPerformed(evt);
            }
        });
        panelAcciones.add(btnCancelarLance);

        javax.swing.GroupLayout panelAccionesLancesLayout = new javax.swing.GroupLayout(panelAccionesLances);
        panelAccionesLances.setLayout(panelAccionesLancesLayout);
        panelAccionesLancesLayout.setHorizontalGroup(
            panelAccionesLancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLancesLayout.createSequentialGroup()
                .addComponent(lblAccionesLances, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
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
        panelDatosLance.setPreferredSize(new java.awt.Dimension(500, 200));
        panelDatosLance.setLayout(new java.awt.GridLayout(6, 1));

        panelLatitud.setPreferredSize(new java.awt.Dimension(142, 10));

        lblLatitudI.setText("Latitud inicial: ");
        lblLatitudI.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelLatitud.add(lblLatitudI);

        campoLatitudI.setEnabled(false);
        campoLatitudI.setMaximumSize(new java.awt.Dimension(100, 20));
        campoLatitudI.setMinimumSize(new java.awt.Dimension(100, 20));
        campoLatitudI.setPreferredSize(new java.awt.Dimension(100, 20));
        campoLatitudI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoLatitudIKeyPressed(evt);
            }
        });
        panelLatitud.add(campoLatitudI);

        lblLatitudF.setText("Latitud Final: ");
        lblLatitudF.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelLatitud.add(lblLatitudF);

        campoLatitudF.setEnabled(false);
        campoLatitudF.setMaximumSize(new java.awt.Dimension(100, 20));
        campoLatitudF.setMinimumSize(new java.awt.Dimension(100, 20));
        campoLatitudF.setPreferredSize(new java.awt.Dimension(100, 20));
        campoLatitudF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoLatitudFKeyPressed(evt);
            }
        });
        panelLatitud.add(campoLatitudF);

        panelDatosLance.add(panelLatitud);

        lblLongitudI.setText("Longitud inicial:");
        lblLongitudI.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelLongitud.add(lblLongitudI);

        campoLongitudI.setEnabled(false);
        campoLongitudI.setMaximumSize(new java.awt.Dimension(100, 20));
        campoLongitudI.setMinimumSize(new java.awt.Dimension(100, 20));
        campoLongitudI.setPreferredSize(new java.awt.Dimension(100, 20));
        panelLongitud.add(campoLongitudI);

        lblLongitudF.setText("Longitud Final:");
        lblLongitudF.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelLongitud.add(lblLongitudF);

        campoLongitudF.setEnabled(false);
        campoLongitudF.setMaximumSize(new java.awt.Dimension(100, 20));
        campoLongitudF.setMinimumSize(new java.awt.Dimension(100, 20));
        campoLongitudF.setPreferredSize(new java.awt.Dimension(100, 20));
        panelLongitud.add(campoLongitudF);

        panelDatosLance.add(panelLongitud);

        panelFechaInicio.setMaximumSize(new java.awt.Dimension(150, 50));
        panelFechaInicio.setMinimumSize(new java.awt.Dimension(150, 50));
        panelFechaInicio.setPreferredSize(new java.awt.Dimension(150, 50));

        lblFechaInicio.setText("Fecha inicio");
        lblFechaInicio.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelFechaInicio.add(lblFechaInicio);

        comboFechaInicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        comboFechaInicio.setEnabled(false);
        panelFechaInicio.add(comboFechaInicio);

        panelDatosLance.add(panelFechaInicio);

        panelFechaFin.setMaximumSize(new java.awt.Dimension(150, 50));
        panelFechaFin.setMinimumSize(new java.awt.Dimension(150, 50));
        panelFechaFin.setPreferredSize(new java.awt.Dimension(150, 50));

        lblFechaFin.setText("Fecha fin");
        lblFechaFin.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelFechaFin.add(lblFechaFin);

        comboFechaFin.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        comboFechaFin.setEnabled(false);
        panelFechaFin.add(comboFechaFin);

        panelDatosLance.add(panelFechaFin);

        panelComentario.setMaximumSize(new java.awt.Dimension(470, 50));
        panelComentario.setMinimumSize(new java.awt.Dimension(470, 50));
        panelComentario.setPreferredSize(new java.awt.Dimension(470, 50));

        lblComentario.setText("Comentarios sobre el lance:");
        lblComentario.setFont(new java.awt.Font("Tahoma", 0, 12));
        panelComentario.add(lblComentario);

        campoComentario.setEnabled(false);
        campoComentario.setPreferredSize(new java.awt.Dimension(300, 20));
        campoComentario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoComentarioActionPerformed(evt);
            }
        });
        panelComentario.add(campoComentario);

        panelDatosLance.add(panelComentario);

        panelBtnAdmCajones.setMaximumSize(new java.awt.Dimension(80, 50));
        panelBtnAdmCajones.setMinimumSize(new java.awt.Dimension(80, 50));
        panelBtnAdmCajones.setPreferredSize(new java.awt.Dimension(150, 50));

        btnAdmCajones.setText("Administrar cajones");
        btnAdmCajones.setEnabled(false);
        btnAdmCajones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmCajonesActionPerformed(evt);
            }
        });
        panelBtnAdmCajones.add(btnAdmCajones);

        panelDatosLance.add(panelBtnAdmCajones);

        panelLances.add(panelDatosLance);

        panelMedio.add(panelLances);

        panelTodo.add(panelMedio, java.awt.BorderLayout.CENTER);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 50));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 50));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 50));

        btnInicFinLance.setFont(new java.awt.Font("Tahoma", 0, 12));
        btnInicFinLance.setText("Finalizar lance");
        btnInicFinLance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicFinLanceActionPerformed(evt);
            }
        });
        panelInferior.add(btnInicFinLance);

        btnVolverAdmCampanias.setLabel("Volver");
        btnVolverAdmCampanias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverAdmCampaniasActionPerformed(evt);
            }
        });
        panelInferior.add(btnVolverAdmCampanias);

        panelTodo.add(panelInferior, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setViewportView(panelTodo);

        add(jScrollPane2, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicFinLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicFinLanceActionPerformed
        // TODO add your handling code here:
        ControllerLance.getInstance().registrarLance();
    }//GEN-LAST:event_btnInicFinLanceActionPerformed

    private void btnModificarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarLanceActionPerformed
        if (tablaLances.getSelectedRowCount() != 0) {
            habilitaPanelDatosLances(true);
            habilitaCamposLances(true);
            setModificandoLance(true);
            btnEliminarLance.setEnabled(false);
            btnInsertarLance.setEnabled(false);
            btnModificarLance.setEnabled(false);
            btnGuardarLance.setEnabled(true);
            btnCancelarLance.setVisible(true);

            Lance unLance = (Lance) tablaLances.getValueAt(tablaLances.getSelectedRow(), 0);
            campoComentario.setText(String.valueOf(unLance.getComentarios()));
            comboFechaInicio.setValue(new Date(unLance.getfYHIni().getTime()));
            comboFechaFin.setValue(new Date(unLance.getfYHFin().getTime()));

            campoLatitudI.setText(String.valueOf(unLance.getPosIniLat()));
            campoLongitudI.setText(String.valueOf(unLance.getPosIniLon()));
            campoLatitudF.setText(String.valueOf(unLance.getPosFinLat()));
            campoLongitudF.setText(String.valueOf(unLance.getPosFinLon()));

            setTempLance(unLance);
        } else {
            //habilitaPanelDatosLances(false);
            //setModificandoLance(false);
            JOptionPane.showMessageDialog(null, "Seleccione un lance primero");
        }
    }//GEN-LAST:event_btnModificarLanceActionPerformed

    private void btnGuardarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarLanceActionPerformed
        /*if (comboFechaInicio.getDate() == null) {
        JOptionPane.showMessageDialog(null, "Seleccionar fecha de inicio");
        return;
        }
        if (comboFechaFin.getDate() == null) {
        JOptionPane.showMessageDialog(null, "Seleccionar fecha de fin");
        return;
        }*/
        if (campoLatitudI.getText().substring(campoLatitudI.getText().indexOf(".") + 1).contains(".")) {
            JOptionPane.showMessageDialog(null, "Latitud inicial incorrecta");
            return;
        }
        if (campoLongitudI.getText().substring(campoLongitudI.getText().indexOf(".") + 1).contains(".")) {
            JOptionPane.showMessageDialog(null, "Longitud inicial incorrecta");
            return;
        }
        if (campoLatitudI.getText().isEmpty() || Double.valueOf(campoLatitudI.getText()) == 0 || !(Double.valueOf(campoLatitudI.getText()) >= -90 && Double.valueOf(campoLatitudI.getText()) <= 90)) {
            JOptionPane.showMessageDialog(null, "La latitud inicial debe estar entre -90 y 90, y no puede ser igual a 0");
            return;
        }
        if (campoLongitudI.getText().isEmpty() || Double.valueOf(campoLongitudI.getText()) == 0 || !(Double.valueOf(campoLongitudI.getText()) >= -180 && Double.valueOf(campoLongitudI.getText()) <= 180)) {
            JOptionPane.showMessageDialog(null, "La longitud inicial debe estar entre -180 y 180, y no puede ser igual a 0");
            return;
        }
        if (campoLatitudF.getText().substring(campoLatitudF.getText().indexOf(".") + 1).contains(".")) {
            JOptionPane.showMessageDialog(null, "Latitud final incorrecta");
            return;
        }
        if (campoLongitudF.getText().substring(campoLongitudF.getText().indexOf(".") + 1).contains(".")) {
            JOptionPane.showMessageDialog(null, "Longitud final incorrecta");
            return;
        }
        if (campoLatitudF.getText().isEmpty() || Double.valueOf(campoLatitudF.getText()) == 0 || !(Double.valueOf(campoLatitudF.getText()) >= -90 && Double.valueOf(campoLatitudF.getText()) <= 90)) {
            JOptionPane.showMessageDialog(null, "La latitud final debe estar entre -90 y 90, y no puede ser igual a 0");
            return;
        }
        if (campoLongitudF.getText().isEmpty() || Double.valueOf(campoLongitudF.getText()) == 0 || !(Double.valueOf(campoLongitudF.getText()) >= -180 && Double.valueOf(campoLongitudF.getText()) <= 180)) {
            JOptionPane.showMessageDialog(null, "La longitud final debe estar entre -180 y 180, y no puede ser igual a 0");
            return;
        }

        Date fechaInicio = (Date) comboFechaInicio.getValue();
        Date fechaFin = (Date) comboFechaFin.getValue();
        if (fechaInicio.after(fechaFin) || fechaFin.before(fechaInicio)) {
            JOptionPane.showMessageDialog(null, "Las fechas de inicio y/o fin son invalidas");
            return;
        }
        if (modificandoLance) {
            //modifica POI
            Lance unLance = getTempLance();
            unLance.setComentarios("" + campoComentario.getText());
            unLance.setfYHIni(fechaInicio);
            unLance.setfYHFin(fechaFin);

            unLance.setPosIniLat(Double.valueOf(campoLatitudI.getText()));
            unLance.setPosIniLon(Double.valueOf(campoLongitudI.getText()));
            unLance.setPosFinLat(Double.valueOf(campoLatitudF.getText()));
            unLance.setPosFinLon(Double.valueOf(campoLongitudF.getText()));

            controllers.ControllerLance.getInstance().modificaLance(unLance);
            setModificandoLance(false);
        } else {
            // Agrega/inserta lance:
            //controllers.ControllerPois.getInstance().agregaPOI(cP.getId(), campoDescripcionNuevoPoi.getText(), Double.valueOf(campoLatitud.getText()), Double.valueOf(campoLongitud.getText()), null, null);
            ControllerLance.getInstance().agregaLance(getTempCampania().getId(), fechaInicio, fechaFin, Double.valueOf(campoLatitudI.getText()), Double.valueOf(campoLongitudI.getText()), Double.valueOf(campoLatitudF.getText()), Double.valueOf(campoLongitudF.getText()), "" + campoComentario.getText());
        }
        habilitaPanelDatosLances(false);
        habilitaCamposLances(false);
        btnCancelarLance.setVisible(false);
        cargaGrillaLances();
    }//GEN-LAST:event_btnGuardarLanceActionPerformed

    private void btnEliminarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarLanceActionPerformed
        if (tablaLances.getSelectedRowCount() != 0) {
            int[] listaLancesSeleccionados = tablaLances.getSelectedRows();
            if (JOptionPane.showConfirmDialog(null,
                    "Desea eliminar " + listaLancesSeleccionados.length + " lances seleccionados y su pesca?",
                    "Eliminar Lances",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE) == 0) {
                int i = 0;
                while (i < listaLancesSeleccionados.length) {
                    controllers.ControllerLance.getInstance().eliminaLance((Lance) tablaLances.getValueAt(listaLancesSeleccionados[i], 0));
                    i++;
                }
                cargaGrillaLances();
            }
        } else {
            //habilitaPanelDatosLances(false);
            //setModificandoLance(false);
            JOptionPane.showMessageDialog(null, "Seleccione un lance primero");
        }
    }//GEN-LAST:event_btnEliminarLanceActionPerformed

    private void btnInsertarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarLanceActionPerformed
        // TODO add your handling code here:
        if (getTempCampania() != null) {
            habilitaPanelDatosLances(true);
            habilitaCamposLances(true);
            btnModificarLance.setEnabled(false);
            btnEliminarLance.setEnabled(false);
            btnInsertarLance.setEnabled(false);
            btnGuardarLance.setEnabled(true);
            btnCancelarLance.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "No se pueden registrar lances sin estar en una campaña");
        }
    }//GEN-LAST:event_btnInsertarLanceActionPerformed

    private void campoComentarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoComentarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoComentarioActionPerformed

    private void tablaLancesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaLancesMouseReleased
        if (tablaLances.getSelectedRowCount() == 0 || tablaLances.getValueAt(tablaLances.getSelectedRow(), 2) == "En curso") {
            habilitaPanelDatosLances(false);
            btnAdmCajones.setEnabled(false);
        } else {
            habilitaPanelDatosLances(true);
            btnAdmCajones.setEnabled(true);
        }
    }//GEN-LAST:event_tablaLancesMouseReleased

    private void campoLatitudIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLatitudIKeyPressed
        // TODO add your handling code here:}//GEN-LAST:event_campoLatitudIKeyPressed
    }
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

    private void campoLatitudFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLatitudFKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoLatitudFKeyPressed

    private void btnCancelarLanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarLanceActionPerformed
        habilitaPanelDatosLances(false);
        habilitaCamposLances(false);
        btnCancelarLance.setVisible(false);
    }//GEN-LAST:event_btnCancelarLanceActionPerformed

    private void btnVolverAdmCampaniasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverAdmCampaniasActionPerformed
        // TODO add your handling code here:
        modBtnAdmLancesDeUnaCamp(false);
        VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcCampanias.getInstance());
    }//GEN-LAST:event_btnVolverAdmCampaniasActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmCajones;
    private javax.swing.JButton btnCancelarLance;
    private org.jdesktop.swingx.JXHyperlink btnEliminarLance;
    private org.jdesktop.swingx.JXHyperlink btnGuardarLance;
    private javax.swing.JButton btnInicFinLance;
    private org.jdesktop.swingx.JXHyperlink btnInsertarLance;
    private org.jdesktop.swingx.JXHyperlink btnModificarLance;
    private javax.swing.JButton btnVolverAdmCampanias;
    private org.jdesktop.swingx.JXTextField campoComentario;
    private javax.swing.JTextField campoLatitudF;
    private javax.swing.JTextField campoLatitudI;
    private javax.swing.JTextField campoLongitudF;
    private javax.swing.JTextField campoLongitudI;
    private javax.swing.JSpinner comboFechaFin;
    private javax.swing.JSpinner comboFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXLabel lblAccionesLances;
    private org.jdesktop.swingx.JXLabel lblComentario;
    private org.jdesktop.swingx.JXLabel lblFechaFin;
    private org.jdesktop.swingx.JXLabel lblFechaInicio;
    private org.jdesktop.swingx.JXLabel lblLatitudF;
    private org.jdesktop.swingx.JXLabel lblLatitudI;
    private org.jdesktop.swingx.JXLabel lblLongitudF;
    private org.jdesktop.swingx.JXLabel lblLongitudI;
    private org.jdesktop.swingx.JXLabel lblTitulo;
    private org.jdesktop.swingx.JXLabel lblTituloTabla;
    private org.jdesktop.swingx.JXPanel panelAcciones;
    private org.jdesktop.swingx.JXPanel panelAccionesLances;
    private org.jdesktop.swingx.JXPanel panelBtnAdmCajones;
    private org.jdesktop.swingx.JXPanel panelComentario;
    private org.jdesktop.swingx.JXPanel panelDatosLance;
    private org.jdesktop.swingx.JXPanel panelFechaFin;
    private org.jdesktop.swingx.JXPanel panelFechaInicio;
    private org.jdesktop.swingx.JXPanel panelInferior;
    private org.jdesktop.swingx.JXPanel panelLances;
    private org.jdesktop.swingx.JXPanel panelLatitud;
    private org.jdesktop.swingx.JXPanel panelLongitud;
    private org.jdesktop.swingx.JXPanel panelMedio;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTabla;
    private org.jdesktop.swingx.JXPanel panelTituloTabla;
    private org.jdesktop.swingx.JXPanel panelTodo;
    private org.jdesktop.swingx.JXTable tablaLances;
    // End of variables declaration//GEN-END:variables

    public static PanelOpcLances getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new PanelOpcLances();
        }
        return unicaInstancia;
    }

    private void inicializador() {
        cargaGrillaLances();
        habilitaPanelDatosLances(false);
        habilitaCamposLances(false);
        btnCancelarLance.setVisible(false);
        btnVolverAdmCampanias.setVisible(false);
        Cls_ManejoTeclas obj_teclas = new Cls_ManejoTeclas();
        campoLatitudI.addKeyListener(obj_teclas);
        campoLongitudI.addKeyListener(obj_teclas);
        campoLatitudF.addKeyListener(obj_teclas);
        campoLongitudF.addKeyListener(obj_teclas);
    }

    public void cargaCamposLatLongInicio() {
        campoLatitudI.setText(String.valueOf(Punto.getInstance().getLatConNegativo()));
        campoLongitudI.setText(String.valueOf(Punto.getInstance().getLonConNegativo()));
    }

    public void cargaCamposLatLongFin() {
        campoLatitudF.setText(String.valueOf(Punto.getInstance().getLatConNegativo()));
        campoLongitudF.setText(String.valueOf(Punto.getInstance().getLonConNegativo()));
    }

    public void cargaGrillaLances() {
        modeloTablaLances.setRowCount(0);//vacia la tabla
        if (getTempCampania() != null) {
            SimpleDateFormat horaCompleta = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            for (Lance unLance : BrokerLance.getInstance().getLancesCampaniaFromDB(getTempCampania().getId())) {
                String fechaFin = "En curso";
                if (unLance.getfYHFin() != null) {
                    fechaFin = horaCompleta.format(unLance.getfYHFin());
                }
                modeloTablaLances.addRow(new Object[]{
                            unLance,//<--Aca esta el objeto -muestra idLance
                            horaCompleta.format(unLance.getfYHIni()),
                            fechaFin,
                            unLance.getComentarios(),
                            BrokerCajon.getInstance().getCajonesFromLance(unLance.getId())
                        });
            }
        }
        tablaLances.setModel(modeloTablaLances);
        ControllerPpal.getInstance().ocultarColJTable(tablaLances, 0);
    }

    private void habilitaPanelDatosLances(boolean estado) {
        btnEliminarLance.setEnabled(estado);
        btnInsertarLance.setEnabled(true);
        btnModificarLance.setEnabled(estado);
        btnGuardarLance.setEnabled(false);
    }

    private void habilitaCamposLances(boolean estado) {
        comboFechaInicio.setEnabled(estado);
        comboFechaFin.setEnabled(estado);
        campoComentario.setEnabled(estado);
        campoLatitudI.setEnabled(estado);
        campoLongitudI.setEnabled(estado);
        campoLatitudF.setEnabled(estado);
        campoLongitudF.setEnabled(estado);
        campoComentario.setEnabled(estado);
        campoLatitudI.setText("");
        campoLongitudI.setText("");
        campoLatitudF.setText("");
        campoLongitudF.setText("");
        /*if (!estado) {
        comboFechaInicio.setValue(null);
        comboFechaFin.setValue(null);
        }*/
        campoComentario.setText("");
        //btnAdmCajones.setEnabled(estado);

    }

    public void setTxtBtnIniciaLance() {
        btnInicFinLance.setText("Iniciar lance");
    }

    public void setTxtBtnFinLance() {
        btnInicFinLance.setText("Finalizar lance");
    }

    /**
     * @return the tempLance
     */
    public Lance getTempLance() {
        return tempLance;
    }

    /**
     * @param tempLance the tempLance to set
     */
    public void setTempLance(Lance tempLance) {
        this.tempLance = tempLance;
    }

    /**
     * @return the modificandoLance
     */
    public boolean isModificandoLance() {
        return modificandoLance;
    }

    /**
     * @param modificandoLance the modificandoLance to set
     */
    public void setModificandoLance(boolean modificandoLance) {
        this.modificandoLance = modificandoLance;
    }

    /**
     * @return the tempCampania
     */
    public Campania getTempCampania() {
        return tempCampania;
    }

    /**
     * @param tempCampania the tempCampania to set
     */
    public void setTempCampania(Campania tempCampania) {
        this.tempCampania = tempCampania;
    }

    public void modBtnAdmLancesDeUnaCamp(boolean editandoCamp) {
        btnVolverAdmCampanias.setVisible(editandoCamp);
        btnInicFinLance.setVisible(!editandoCamp);
    }
}

class Cls_ManejoTeclas extends KeyAdapter {

    public void keyTyped(KeyEvent ke) {
        char loc_caracter = ke.getKeyChar();
        /*
        if(((loc_caracter < '0') || (loc_caracter > '9' )) && (loc_caracter != KeyEvent.VK_BACK_SPACE) &&
        ((loc_caracter < 'a' ) || (loc_caracter > 'z')) &&
        ((loc_caracter < 'A' ) || (loc_caracter > 'Z')) &&
        (loc_caracter != KeyEvent.VK_ACCEPT) && (loc_caracter != '.') && (loc_caracter != '_'))
        ke.consume();
         */
        if (((loc_caracter < '0') || (loc_caracter > '9')) && (loc_caracter != KeyEvent.VK_BACK_SPACE)
                && (loc_caracter != KeyEvent.VK_ACCEPT) && (loc_caracter != '.') && (loc_caracter != '-')) {
            ke.consume();
        }
    }
}