/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcAlertas.java
 *
 * Created on 14/04/2012, 02:18:13
 */
package gui;

import controllers.ControllerAlertas;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import modelo.alertas.AdministraAlertas;

/**
 *
 * @author Sebastian
 */
public class PanelOpcAlertas extends javax.swing.JPanel {
    static PanelOpcAlertas unicaInstancia;
    private PanelOpcAlertasAgregaEdita panelAgregaEdita = PanelOpcAlertasAgregaEdita.getInstance();
    private Color colorOriginalBtnIniciar;
    private DefaultTableModel modeloTabla;
    private boolean modificandoAlerta;
    private int NRO_COL_ID_ALERTA;
    private int NRO_COL_ESTADO;
    private int NRO_COL_TITULO;
    private int NRO_COL_MENSAJE;
    private int NRO_COL_FLAGS;
    private int NRO_COL_ACCIONES;
    private int cantColumnas;
    
    
    /** Creates new form PanelOpcAlertas */
    private PanelOpcAlertas() {
        initComponents();        
        add(panelAgregaEdita);
        inicializador();
        panelAgregaEdita.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAlertasPpal = new org.jdesktop.swingx.JXPanel();
        panelTitulo = new org.jdesktop.swingx.JXPanel();
        lblTitulo = new org.jdesktop.swingx.JXLabel();
        panelCentro = new org.jdesktop.swingx.JXPanel();
        panelOpcionesAlertas = new org.jdesktop.swingx.JXPanel();
        chkAlertas = new javax.swing.JCheckBox();
        lblTxtTablaAlertas = new org.jdesktop.swingx.JXLabel();
        panelTablaAlertas = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAlertas = new org.jdesktop.swingx.JXTable();
        panelAccionesCampElegida = new org.jdesktop.swingx.JXPanel();
        lblAccionesAlerta = new org.jdesktop.swingx.JXLabel();
        panelAcciones = new org.jdesktop.swingx.JXPanel();
        btnModificar = new org.jdesktop.swingx.JXHyperlink();
        btnEliminar = new org.jdesktop.swingx.JXHyperlink();
        panelBtnAgregar = new org.jdesktop.swingx.JXPanel();
        btnAgregar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new java.awt.BorderLayout());

        panelAlertasPpal.setLayout(new java.awt.BorderLayout());

        panelTitulo.setMaximumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setMinimumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setPreferredSize(new java.awt.Dimension(500, 40));
        panelTitulo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 10));

        lblTitulo.setText("Alertas");
        lblTitulo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        panelTitulo.add(lblTitulo);

        panelAlertasPpal.add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelCentro.setMaximumSize(new java.awt.Dimension(500, 420));
        panelCentro.setMinimumSize(new java.awt.Dimension(500, 420));
        panelCentro.setPreferredSize(new java.awt.Dimension(500, 420));
        panelCentro.setLayout(new javax.swing.BoxLayout(panelCentro, javax.swing.BoxLayout.PAGE_AXIS));

        panelOpcionesAlertas.setMaximumSize(new java.awt.Dimension(500, 60));
        panelOpcionesAlertas.setMinimumSize(new java.awt.Dimension(500, 60));
        panelOpcionesAlertas.setPreferredSize(new java.awt.Dimension(500, 60));
        panelOpcionesAlertas.setLayout(new java.awt.GridLayout(2, 1));

        chkAlertas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        chkAlertas.setLabel("Usar Alertas");
        chkAlertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAlertasActionPerformed(evt);
            }
        });
        panelOpcionesAlertas.add(chkAlertas);

        lblTxtTablaAlertas.setText(" Tabla de alertas:");
        lblTxtTablaAlertas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelOpcionesAlertas.add(lblTxtTablaAlertas);

        panelCentro.add(panelOpcionesAlertas);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(482, 32767));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(482, 27));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(482, 220));

        tablaAlertas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id_Alerta", "Estado", "Nombre", "Acciones", "Mensaje", "Flags"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaAlertas.setColumnSelectionAllowed(true);
        tablaAlertas.setMaximumSize(new java.awt.Dimension(480, 72));
        tablaAlertas.setMinimumSize(new java.awt.Dimension(480, 72));
        tablaAlertas.setPreferredScrollableViewportSize(new java.awt.Dimension(480, 250));
        tablaAlertas.setPreferredSize(new java.awt.Dimension(480, 72));
        tablaAlertas.getTableHeader().setReorderingAllowed(false);
        tablaAlertas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAlertasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaAlertas);
        tablaAlertas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaAlertas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaAlertas.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaAlertas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaAlertas.getColumnModel().getColumn(1).setMinWidth(60);
        tablaAlertas.getColumnModel().getColumn(1).setPreferredWidth(60);
        tablaAlertas.getColumnModel().getColumn(1).setMaxWidth(60);
        tablaAlertas.getColumnModel().getColumn(2).setResizable(false);
        tablaAlertas.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaAlertas.getColumnModel().getColumn(3).setMinWidth(0);
        tablaAlertas.getColumnModel().getColumn(3).setPreferredWidth(0);
        tablaAlertas.getColumnModel().getColumn(3).setMaxWidth(0);
        tablaAlertas.getColumnModel().getColumn(4).setMinWidth(0);
        tablaAlertas.getColumnModel().getColumn(4).setPreferredWidth(0);
        tablaAlertas.getColumnModel().getColumn(4).setMaxWidth(0);
        tablaAlertas.getColumnModel().getColumn(5).setMinWidth(0);
        tablaAlertas.getColumnModel().getColumn(5).setPreferredWidth(0);
        tablaAlertas.getColumnModel().getColumn(5).setMaxWidth(0);

        panelTablaAlertas.add(jScrollPane1);

        panelAccionesCampElegida.setMaximumSize(new java.awt.Dimension(450, 30));
        panelAccionesCampElegida.setMinimumSize(new java.awt.Dimension(450, 30));
        panelAccionesCampElegida.setPreferredSize(new java.awt.Dimension(450, 30));
        panelAccionesCampElegida.setLayout(new java.awt.GridLayout(1, 2));

        lblAccionesAlerta.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAccionesAlerta.setText("Acciones sobre la alerta elegida:");
        lblAccionesAlerta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        panelAccionesCampElegida.add(lblAccionesAlerta);

        panelAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-editar.png"))); // NOI18N
        btnModificar.setText("");
        btnModificar.setToolTipText("Modificar campaña");
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        panelAcciones.add(btnModificar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/tabla-icono-eliminar.png"))); // NOI18N
        btnEliminar.setText("");
        btnEliminar.setToolTipText("Eliminar campaña");
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        panelAcciones.add(btnEliminar);

        panelAccionesCampElegida.add(panelAcciones);

        panelTablaAlertas.add(panelAccionesCampElegida);

        panelCentro.add(panelTablaAlertas);

        panelAlertasPpal.add(panelCentro, java.awt.BorderLayout.CENTER);

        panelBtnAgregar.setMaximumSize(new java.awt.Dimension(500, 40));
        panelBtnAgregar.setMinimumSize(new java.awt.Dimension(500, 40));
        panelBtnAgregar.setPreferredSize(new java.awt.Dimension(500, 40));

        btnAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnAgregar.setText("Agregar alerta");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        panelBtnAgregar.add(btnAgregar);

        panelAlertasPpal.add(panelBtnAgregar, java.awt.BorderLayout.SOUTH);

        add(panelAlertasPpal, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
// TODO add your handling code here:
        getPanelAlertasPpal().setVisible(false);                    
        panelAgregaEdita.setVisible(true);
        setModificandoAlerta(false);
}//GEN-LAST:event_btnAgregarActionPerformed

private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
   int filaSeleccionada = tablaAlertas.getSelectedRow();
    if (filaSeleccionada>=0){
         setModificandoAlerta(true);
         setGuiModificarFilaElegida();
   }

    

}//GEN-LAST:event_btnModificarActionPerformed

private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    String txtPregunta = "Está por eliminar una alerta del disco, está usted seguro?";
    if (JOptionPane.showConfirmDialog(null, txtPregunta, "Eliminar alerta seleccionada", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
        controllers.ControllerAlertas.getInstance().borrarAlerta(getIdDeAlertaSeleccionada());
        controlaPanelAccionesAlerta();
    }
}//GEN-LAST:event_btnEliminarActionPerformed

private void chkAlertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAlertasActionPerformed
        ControllerAlertas cAlertas=ControllerAlertas.getInstance();
        cAlertas.setEstadoAlertas(this.getChkAlertas().isSelected());
}//GEN-LAST:event_chkAlertasActionPerformed

private void tablaAlertasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAlertasMouseClicked
    controlaPanelAccionesAlerta();
}//GEN-LAST:event_tablaAlertasMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private org.jdesktop.swingx.JXHyperlink btnEliminar;
    private org.jdesktop.swingx.JXHyperlink btnModificar;
    private javax.swing.JCheckBox chkAlertas;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXLabel lblAccionesAlerta;
    private org.jdesktop.swingx.JXLabel lblTitulo;
    private org.jdesktop.swingx.JXLabel lblTxtTablaAlertas;
    private org.jdesktop.swingx.JXPanel panelAcciones;
    private org.jdesktop.swingx.JXPanel panelAccionesCampElegida;
    private org.jdesktop.swingx.JXPanel panelAlertasPpal;
    private org.jdesktop.swingx.JXPanel panelBtnAgregar;
    private org.jdesktop.swingx.JXPanel panelCentro;
    private org.jdesktop.swingx.JXPanel panelOpcionesAlertas;
    private org.jdesktop.swingx.JXPanel panelTablaAlertas;
    private org.jdesktop.swingx.JXPanel panelTitulo;
    private org.jdesktop.swingx.JXTable tablaAlertas;
    // End of variables declaration//GEN-END:variables

    public JCheckBox getChkAlertas() {
        return chkAlertas;
    }

    public void setChkAlertas(JCheckBox chkAlertas) {
        this.chkAlertas = chkAlertas;
    }

    public static PanelOpcAlertas getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelOpcAlertas();
       }
       return unicaInstancia;
    }

/*
    public boolean actualizaDatosPanelAlertas(String estado){
        boolean sePudo=false;
        //-- Actualiza los campos que corresponden del Document
        try {
            Element raizConfiguracionIbape= getDocBrokerConfig().getRootElement();
            Element parametros=raizConfiguracionIbape.getChild("Parametros");        
            parametros.getChild("PanelAlertas").setAttribute("Estado",estado);
            sePudo=true;
            }
        catch (Exception e) 
            {  Logueador.getInstance().agregaAlLog(e.toString()); }
        return sePudo;
    }    
*/

    /**
     * @return the panelAlertasPpal
     */
    public org.jdesktop.swingx.JXPanel getPanelAlertasPpal() {
        return panelAlertasPpal;
    }

    /**
     * @param panelAlertasPpal the panelAlertasPpal to set
     */
    public void setPanelAlertasPpal(org.jdesktop.swingx.JXPanel panelAlertasPpal) {
        this.panelAlertasPpal = panelAlertasPpal;
    }

    public void cargaGrillaAlertas() {
        vaciaTabla();
        ArrayList<modelo.alertas.Alerta> alertas = modelo.alertas.AdministraAlertas.getInstance().getAlertas();
        if ((alertas == null) || (alertas.isEmpty()) ) {
            modelo.alertas.AdministraAlertas.getInstance().leerAlertasDeLaDB();
            alertas = modelo.alertas.AdministraAlertas.getInstance().getAlertas();            
        }        
        if ((!(alertas == null)) && (alertas.size() > 0)) {
            // while (), pongo cada objeto Alerta en la grilla de alertas                    
            int i = 0;
            while (i < alertas.size()) {
                agregaUnaFilaAlerta(
                        alertas.get(i).getId(),
                        alertas.get(i).getTitulo(),
                        alertas.get(i).isEstado(),
                        alertas.get(i).getMensaje(),
                        alertas.get(i).getFlagsAcciones()
                        );
                i++;
            }
            
        }
    }
    
    public void agregaUnaFilaAlerta(int id, String titulo, boolean estado, String mensaje,
            int flags) {
        Object[] fila = new Object[cantColumnas]; //creamos la fila
        fila[NRO_COL_ACCIONES]=panelAcciones;
        fila[NRO_COL_ID_ALERTA]=id;
        fila[NRO_COL_ESTADO]=estado;
        fila[NRO_COL_TITULO]=titulo;
        fila[NRO_COL_MENSAJE]=mensaje;
        fila[NRO_COL_FLAGS]=flags;

        modeloTabla.addRow(fila);
    }

    private void inicializador() {
        //String[] columnas = new String[cantColumnas];
        //columnas[NRO_COL_ID_CAMP]="ID";
        //columnas[NRO_COL_ACCIONES]="Acciones";
        //columnas[NRO_COL_BARCO]="Barco";
        //columnas[NRO_COL_CAPITAN]="Capitan";
        //columnas[NRO_COL_DURACION]="Duracion";
        //columnas[NRO_COL_FECHA_FIN]="Fecha Fin";
        //columnas[NRO_COL_FECHA_INI]="Fecha Inicio";
        //columnas[NRO_COL_NOMBRE_CAMP]="Nombre campaña";        
        //modeloTabla = new javax.swing.table.TableModel(new Object[][]{},columnas);        
        //tablaCampanias.setModel(modeloTabla);
                                       
        //tablaCampanias.setDefaultRenderer(Object.class, new PanelOpcCampaniasAcciones());
        //tablaCampanias.setDefaultEditor(Object.class, new PanelOpcCampaniasAcciones());
/*
        TableColumn columnaAcciones = new TableColumn();
        columnaAcciones.setHeaderValue("Acciones!");
        columnaAcciones.setMinWidth(100);
        columnaAcciones.setPreferredWidth(100);
        columnaAcciones.setCellEditor(new PanelOpcCampaniasAcciones());
        columnaAcciones.setCellRenderer(new PanelOpcCampaniasAcciones());        
        tablaCampanias.addColumn(columnaAcciones);        
        tablaCampanias.setEditingColumn(6);
        //tablaCampanias.setEditingRow(0);
*/        
        modificandoAlerta=false;
        NRO_COL_ID_ALERTA=0;
        NRO_COL_ESTADO=1;
        NRO_COL_TITULO=2;
        NRO_COL_MENSAJE=3;
        NRO_COL_ACCIONES=5;
        NRO_COL_FLAGS=4;
        cantColumnas=6;        
        modeloTabla = (DefaultTableModel) tablaAlertas.getModel();
        tablaAlertas.setModel(modeloTabla);
        cargaIconosDeBotones();
        cargaGrillaAlertas();        
        
        controlaPanelAccionesAlerta();
 
    }

    public void vaciaTabla() {                
        modeloTabla.setRowCount(0);
    }

       public void setGuiModificarFilaElegida() {
       int filaSeleccionada = tablaAlertas.getSelectedRow();
       if (filaSeleccionada>=0){
             PanelOpcAlertasAgregaEdita.getInstance().cargaAlertaEditar(getIdDeAlertaSeleccionada());
             getPanelAlertasPpal().setVisible(false);                    
             panelAgregaEdita.setVisible(true);
       }
    }

       
    private void controlaPanelAccionesAlerta() {
    
        boolean estado;
        
        if ((modeloTabla.getRowCount()>0) && (getIdDeAlertaSeleccionada()>=0)){
           estado = true;
        }
        else { estado = false; }
        lblAccionesAlerta.setEnabled(estado);
        btnEliminar.setEnabled(estado);
        btnModificar.setEnabled(estado);  
        if (isModificandoAlerta()){          
            btnModificar.setVisible(false);
            btnEliminar.setVisible(false);
            tablaAlertas.setEnabled(false);
        }
        else
            { 
              btnModificar.setVisible(true);
              btnEliminar.setVisible(true);
              tablaAlertas.setEnabled(true);            
            }
    }

    private int getIdDeAlertaSeleccionada() {
        int salida = -1;
        int filaSeleccionada = tablaAlertas.getSelectedRow();
        if (filaSeleccionada>=0){
            salida = (Integer) modeloTabla.getValueAt(filaSeleccionada, NRO_COL_ID_ALERTA);
        }                
        return salida;
    }

        public boolean isModificandoAlerta() {
        return modificandoAlerta;
    }

    public void setModificandoAlerta(boolean modificandoAlerta) {
        this.modificandoAlerta = modificandoAlerta;
    }

    private void cargaIconosDeBotones() {
        //btnModificar.setIcon(new javax.swing.ImageIcon("imgs//iconos//tabla-icono-editar.png"));
        //btnEliminar.setIcon(new javax.swing.ImageIcon("imgs//iconos//tabla-icono-eliminar.png"));
    }
    
    public void habilitaTablaAlertas(){
        tablaAlertas.enable();
    }
    
    public void deshabilitaTablaAlertas(){
        tablaAlertas.disable();
    }
    
    /*
 //codigo de prueba para poder probar un panel simplemente haciendo "Run File" sobre su clase    
     public static void main(String[] args) {
        javax.swing.JFrame elFrame = new javax.swing.JFrame();
        JPanel elPanel= new PanelOpcAlertas();
        elFrame.setSize(500,500);
        elFrame.add(elPanel); 
        elFrame.setVisible(true);
    }
     * */
     
 

    
}
