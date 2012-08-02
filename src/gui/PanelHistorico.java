/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelHistorico.java
 *
 * Created on 11/04/2012, 14:48:19
 */
package gui;

import controllers.ControllerCampania;
import controllers.ControllerHistorico;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import modelo.dataManager.CategoriaPoi;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Sebastian
 */
public class PanelHistorico extends javax.swing.JPanel {
    static PanelHistorico unicaInstancia;
    private DefaultTableModel modeloTabla;
    private ButtonGroup grupoElijeCampania;
    private ArrayList<Integer> categoriasSeleccionadas=new ArrayList();
    private String txtBtnIniciaMapa;
    private int idCampaniaElegida;
    private int NRO_COL_ID_CAMP;
    private int NRO_COL_ELEGIR;
    private int NRO_COL_FECHA_INI;
    private int NRO_COL_FECHA_FIN;
    private int NRO_COL_BARCO;
    private int NRO_COL_CAJONES;
    private int NRO_COL_CAPITAN;
    private int NRO_COL_NOMBRE_CAMP;
    private int cantColumnas;
    
    
    /** Creates new form PanelHistorico */
    private PanelHistorico() {
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

        panelTitulo = new javax.swing.JPanel();
        lblTituloHistorico = new java.awt.Label();
        panelCampanias = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCampanias = new org.jdesktop.swingx.JXTable();
        panelInfoMapa = new javax.swing.JPanel();
        panelSuperior = new javax.swing.JPanel();
        lblTxtDatosMapa = new java.awt.Label();
        panelRecorrido = new org.jdesktop.swingx.JXPanel();
        chkRecorrido = new javax.swing.JCheckBox();
        lblCantPuntosRecorrido = new org.jdesktop.swingx.JXLabel();
        chkPoisTodos = new javax.swing.JCheckBox();
        lblTxtTablaCatPois = new java.awt.Label();
        panelInferior = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCatPois = new org.jdesktop.swingx.JXTable();
        panelBtnGraficar = new javax.swing.JPanel();
        btnGraficarDatos = new javax.swing.JButton();
        btnIniciarMapaHistorico = new javax.swing.JButton();
        btnDetenerMapaHistorico = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        panelTitulo.setMaximumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setMinimumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setPreferredSize(new java.awt.Dimension(500, 40));

        lblTituloHistorico.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblTituloHistorico.setText("Datos Historicos");
        panelTitulo.add(lblTituloHistorico);

        add(panelTitulo);

        panelCampanias.setMaximumSize(new java.awt.Dimension(500, 200));
        panelCampanias.setMinimumSize(new java.awt.Dimension(500, 200));
        panelCampanias.setPreferredSize(new java.awt.Dimension(500, 200));

        tablaCampanias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Elegir", "Fecha Inicio", "Fecha Fin", "Pesca [cajones]", "Barco", "Capitan", "Nombre de Campaña"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaCampanias.setMinimumSize(new java.awt.Dimension(500, 200));
        tablaCampanias.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 200));
        tablaCampanias.setPreferredSize(new java.awt.Dimension(500, 200));
        jScrollPane1.setViewportView(tablaCampanias);
        tablaCampanias.getColumnModel().getColumn(0).setMinWidth(0);
        tablaCampanias.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaCampanias.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaCampanias.getColumnModel().getColumn(1).setMinWidth(30);
        tablaCampanias.getColumnModel().getColumn(1).setPreferredWidth(30);
        tablaCampanias.getColumnModel().getColumn(1).setMaxWidth(200);
        tablaCampanias.getColumnModel().getColumn(2).setMinWidth(80);
        tablaCampanias.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaCampanias.getColumnModel().getColumn(2).setMaxWidth(200);
        tablaCampanias.getColumnModel().getColumn(3).setMinWidth(80);
        tablaCampanias.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaCampanias.getColumnModel().getColumn(3).setMaxWidth(200);
        tablaCampanias.getColumnModel().getColumn(4).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(4).setPreferredWidth(20);
        tablaCampanias.getColumnModel().getColumn(4).setMaxWidth(200);
        tablaCampanias.getColumnModel().getColumn(5).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(5).setPreferredWidth(20);
        tablaCampanias.getColumnModel().getColumn(6).setMinWidth(20);
        tablaCampanias.getColumnModel().getColumn(6).setPreferredWidth(20);

        panelCampanias.add(jScrollPane1);

        add(panelCampanias);

        panelInfoMapa.setMaximumSize(new java.awt.Dimension(500, 220));
        panelInfoMapa.setMinimumSize(new java.awt.Dimension(500, 220));
        panelInfoMapa.setPreferredSize(new java.awt.Dimension(500, 220));
        panelInfoMapa.setLayout(new javax.swing.BoxLayout(panelInfoMapa, javax.swing.BoxLayout.PAGE_AXIS));

        panelSuperior.setMaximumSize(new java.awt.Dimension(500, 100));
        panelSuperior.setMinimumSize(new java.awt.Dimension(500, 100));
        panelSuperior.setPreferredSize(new java.awt.Dimension(500, 100));
        panelSuperior.setLayout(new java.awt.GridLayout(4, 1));

        lblTxtDatosMapa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTxtDatosMapa.setText("Datos mostrados en el mapa:");
        panelSuperior.add(lblTxtDatosMapa);

        panelRecorrido.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        chkRecorrido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkRecorrido.setText("Recorrido");
        chkRecorrido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRecorridoActionPerformed(evt);
            }
        });
        panelRecorrido.add(chkRecorrido);

        lblCantPuntosRecorrido.setForeground(new java.awt.Color(0, 204, 51));
        lblCantPuntosRecorrido.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        panelRecorrido.add(lblCantPuntosRecorrido);

        panelSuperior.add(panelRecorrido);

        chkPoisTodos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkPoisTodos.setText("Puntos de Interes (POI) de todas las campañas");
        chkPoisTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPoisTodosActionPerformed(evt);
            }
        });
        panelSuperior.add(chkPoisTodos);

        lblTxtTablaCatPois.setAlignment(java.awt.Label.CENTER);
        lblTxtTablaCatPois.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblTxtTablaCatPois.setText("Seleccione las categorias de POIs que desea ver en el mapa");
        panelSuperior.add(lblTxtTablaCatPois);

        panelInfoMapa.add(panelSuperior);

        panelInferior.setMaximumSize(new java.awt.Dimension(500, 120));
        panelInferior.setMinimumSize(new java.awt.Dimension(500, 120));
        panelInferior.setPreferredSize(new java.awt.Dimension(500, 120));
        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        jScrollPane2.setMaximumSize(new java.awt.Dimension(450, 120));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(450, 120));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(450, 120));

        tablaCatPois.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Elegir", "Icono", "Categorias de POI"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaCatPois);
        tablaCatPois.getColumnModel().getColumn(0).setResizable(false);
        tablaCatPois.getColumnModel().getColumn(0).setPreferredWidth(0);
        tablaCatPois.getColumnModel().getColumn(1).setMinWidth(40);
        tablaCatPois.getColumnModel().getColumn(1).setPreferredWidth(40);
        tablaCatPois.getColumnModel().getColumn(1).setMaxWidth(40);
        tablaCatPois.getColumnModel().getColumn(2).setMinWidth(50);
        tablaCatPois.getColumnModel().getColumn(2).setPreferredWidth(50);
        tablaCatPois.getColumnModel().getColumn(2).setMaxWidth(50);
        tablaCatPois.getColumnModel().getColumn(3).setPreferredWidth(200);

        panelInferior.add(jScrollPane2);

        panelInfoMapa.add(panelInferior);

        add(panelInfoMapa);

        panelBtnGraficar.setMaximumSize(new java.awt.Dimension(500, 40));
        panelBtnGraficar.setMinimumSize(new java.awt.Dimension(500, 40));
        panelBtnGraficar.setPreferredSize(new java.awt.Dimension(500, 40));
        panelBtnGraficar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnGraficarDatos.setText("Graficar datos");
        btnGraficarDatos.setMaximumSize(new java.awt.Dimension(130, 23));
        btnGraficarDatos.setMinimumSize(new java.awt.Dimension(130, 23));
        btnGraficarDatos.setPreferredSize(new java.awt.Dimension(130, 23));
        btnGraficarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarDatosActionPerformed(evt);
            }
        });
        panelBtnGraficar.add(btnGraficarDatos);

        btnIniciarMapaHistorico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnIniciarMapaHistorico.setLabel("Iniciar Mapa Historico");
        btnIniciarMapaHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarMapaHistoricoActionPerformed(evt);
            }
        });
        panelBtnGraficar.add(btnIniciarMapaHistorico);

        btnDetenerMapaHistorico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDetenerMapaHistorico.setLabel("Detener Mapa Historico");
        btnDetenerMapaHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetenerMapaHistoricoActionPerformed(evt);
            }
        });
        panelBtnGraficar.add(btnDetenerMapaHistorico);

        add(panelBtnGraficar);
    }// </editor-fold>//GEN-END:initComponents

    private void chkPoisTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPoisTodosActionPerformed
        if ((chkPoisTodos.isSelected()) || (chkRecorrido.isSelected())){
            habilitaBtnGraficarDatos(true);
        }
        else{
            habilitaBtnGraficarDatos(false);
        }
        if (chkPoisTodos.isSelected()){
            habilitaPanelTablaCatPois(true);            
        }
        else{
            habilitaPanelTablaCatPois(false);
        }
    }//GEN-LAST:event_chkPoisTodosActionPerformed

    private void chkRecorridoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRecorridoActionPerformed
        if (chkRecorrido.isSelected()){
            habilitaBtnGraficarDatos(true);
        }
        else{
            if (!chkPoisTodos.isSelected()){
                habilitaBtnGraficarDatos(false);
            }
        }
    }//GEN-LAST:event_chkRecorridoActionPerformed

    private void btnIniciarMapaHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarMapaHistoricoActionPerformed
        setTxtBtnIniciaMapa(btnIniciarMapaHistorico.getText());
        btnIniciarMapaHistorico.setText("Abriendo...");
        btnIniciarMapaHistorico.setEnabled(false);
        if (!(controllers.ControllerHistorico.getInstance().iniciaServerYabreBrowser())) {
            JOptionPane.showMessageDialog(this,"Hubo un error al iniciar el Servidor Web ó el Navegador");
        }        
    }//GEN-LAST:event_btnIniciarMapaHistoricoActionPerformed

    private void btnGraficarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarDatosActionPerformed
        if (getIdCampaniaElegida()>=0){
            //si no se ha hecho click en iniciar Mapa historico previamente, lo hago
            if (btnIniciarMapaHistorico.isEnabled() && btnIniciarMapaHistorico.isVisible()){
                btnIniciarMapaHistoricoActionPerformed(null);
            }
            
            if (chkRecorrido.isSelected()){
                ControllerHistorico.getInstance().cargaRecorridoEnMapa(getIdCampaniaElegida());
            }
            if (getCategoriasSeleccionadas().size()>0){
                ControllerHistorico.getInstance().cargaPoisEnMapa(getIdCampaniaElegida(),getCategoriasSeleccionadas());
            }
            if (!(getCategoriasSeleccionadas().size()>0) && !(chkRecorrido.isSelected())){
                JOptionPane.showMessageDialog(null, "No ha elegido que datos de la campaña elegida desea graficar");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No ha elegido ninguna campaña para graficar");
        }
    }//GEN-LAST:event_btnGraficarDatosActionPerformed

    public void restauraBtnIniciarMapa(){
        btnIniciarMapaHistorico.setText(getTxtBtnIniciaMapa());
        btnIniciarMapaHistorico.setEnabled(true);
        btnIniciarMapaHistorico.setVisible(false);
        btnDetenerMapaHistorico.setVisible(true);        
    }
    
    private void btnDetenerMapaHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetenerMapaHistoricoActionPerformed
        String txtOriginal = btnDetenerMapaHistorico.getText();
        btnDetenerMapaHistorico.setText("Cerrando...");
        btnDetenerMapaHistorico.setEnabled(false);
        if (!(controllers.ControllerPpal.getInstance().detieneServerYcierraBrowser())){
            JOptionPane.showMessageDialog(this, "Hubo un error al detener el Servidor Web o el Navegador");
        }
        btnDetenerMapaHistorico.setText(txtOriginal);
        btnDetenerMapaHistorico.setEnabled(true);
        btnDetenerMapaHistorico.setVisible(false);
        btnIniciarMapaHistorico.setVisible(true);
    }//GEN-LAST:event_btnDetenerMapaHistoricoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetenerMapaHistorico;
    private javax.swing.JButton btnGraficarDatos;
    private javax.swing.JButton btnIniciarMapaHistorico;
    private javax.swing.JCheckBox chkPoisTodos;
    private javax.swing.JCheckBox chkRecorrido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXLabel lblCantPuntosRecorrido;
    private java.awt.Label lblTituloHistorico;
    private java.awt.Label lblTxtDatosMapa;
    private java.awt.Label lblTxtTablaCatPois;
    private javax.swing.JPanel panelBtnGraficar;
    private javax.swing.JPanel panelCampanias;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelInfoMapa;
    private org.jdesktop.swingx.JXPanel panelRecorrido;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTitulo;
    private org.jdesktop.swingx.JXTable tablaCampanias;
    private org.jdesktop.swingx.JXTable tablaCatPois;
    // End of variables declaration//GEN-END:variables


    public static PanelHistorico getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelHistorico();          
       }
       return unicaInstancia;
    }
    
    public void agregaUnaFilaCampania(int id, String nombre, String barco, String capitan,
            int estado, Date fechaInicio, Date fechaFin, int cantCajones) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[cantColumnas]; //creamos la fila
        if (fechaInicio != null) { fila[NRO_COL_FECHA_INI]=sdf.format(fechaInicio); }
        if (fechaFin != null){ fila[NRO_COL_FECHA_FIN]=sdf.format(fechaFin); }
        fila[NRO_COL_CAPITAN]=capitan;
        fila[NRO_COL_NOMBRE_CAMP]=nombre;
        fila[NRO_COL_BARCO]=barco;
        fila[NRO_COL_ID_CAMP]=id;
        fila[NRO_COL_CAJONES]=cantCajones;        
        JRadioButton radioBtnCampania = new JRadioButton();
        grupoElijeCampania.add(radioBtnCampania);//lo agrego al grupo, lo cual me garantiza la selección simple
        fila[NRO_COL_ELEGIR]= radioBtnCampania;

        modeloTabla.addRow(fila);
    }    

    public void vaciaTabla() {                
        modeloTabla.setRowCount(0);
        setIdCampaniaElegida(-1);
    }
    
    private void inicializador() {
        NRO_COL_ID_CAMP=0;
        NRO_COL_ELEGIR=1;
        NRO_COL_FECHA_INI=2;
        NRO_COL_FECHA_FIN=3;
        NRO_COL_CAJONES=4;
        NRO_COL_BARCO=5;
        NRO_COL_CAPITAN=6;
        NRO_COL_NOMBRE_CAMP=7;
        cantColumnas=8;
        modeloTabla = (DefaultTableModel) tablaCampanias.getModel();
        tablaCampanias.setModel(modeloTabla);
        //seteo los radiobotones de la tabla
        grupoElijeCampania = new ButtonGroup(); 
        tablaCampanias.getColumn(1).setCellRenderer(new RadioButtonRenderer());
        tablaCampanias.getColumn(1).setCellEditor(new RadioButtonEditor(new JCheckBox()));
        habilitaPanelTablaCatPois(false);
        seteaBotonesMapa();
    }
     
    public void marcaCampaniaEnCurso(){
        if (modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso() != null){
            boolean encontro=false;
            //recorre las campañas de la tabla y cuando encuentre una que no tiene fecha de fin,
            //será la campaña en curso, y en su fecha de fin le pondrá el texto que la distinga
            int i = 0;
            int idLeido;
            while ((i<modeloTabla.getRowCount()) && (!(encontro))){
                idLeido=(Integer)modeloTabla.getValueAt(i, NRO_COL_ID_CAMP);
                if (idLeido == ControllerCampania.getInstance().getIdCampaniaEnCurso()){
                    encontro = true;
                }
                else
                {   i++;  }
            }
            if (encontro) {
                modeloTabla.setValueAt("EN CURSO", i, NRO_COL_FECHA_FIN);
            }
        }   
    }


    /**
     * @return the idCampaniaElegida
     */
    public int getIdCampaniaElegida() {
        return idCampaniaElegida;
    }

    /**
     * @param idCampaniaElegida the idCampaniaElegida to set
     */
    public void setIdCampaniaElegida(int idCampaniaElegida) {
        if (idCampaniaElegida>=0){
            this.idCampaniaElegida = Integer.parseInt(modeloTabla.getValueAt(idCampaniaElegida, 0).toString());                        
            habilitaChkRecorrido(true);
            setTxtCantidadDePuntosDeCampElegida(controllers.ControllerHistorico.getInstance().getCantPuntosHistoricos(this.idCampaniaElegida));            
        }
        else
          { this.idCampaniaElegida = idCampaniaElegida; 
            habilitaChkRecorrido(false);
            setTxtCantidadDePuntosDeCampElegida(-1);
          }
        inicializaTablaCategoriasPois();//carga las categorias para esta campaña        
    }

    public void inicializaTablaCategoriasPois() {
        if (getIdCampaniaElegida()>=0){
            TableModelCatPoisHistorico tableModelCatPois = (TableModelCatPoisHistorico)cargaGrillaCategoriaPOIS();        
            tablaCatPois.setModel(tableModelCatPois);
            //resize la columna Elejir
            tablaCatPois.getColumnModel().getColumn(1).setMinWidth(30); 
            tablaCatPois.getColumnModel().getColumn(1).setMaxWidth(30); 
            tablaCatPois.getColumnModel().getColumn(1).setPreferredWidth(30); 
            tablaCatPois.getColumnModel().getColumn(1).setResizable(false);
            //escondo la columna Iconos
            tablaCatPois.getColumnModel().getColumn(2).setMinWidth(40); 
            tablaCatPois.getColumnModel().getColumn(2).setMaxWidth(40); 
            tablaCatPois.getColumnModel().getColumn(2).setPreferredWidth(40);
            //seteo los checkboxes
            tablaCatPois.getColumn(1).setCellRenderer(new CheckBoxRenderer());
            tablaCatPois.getColumn(1).setCellEditor(new CheckBoxEditor(new JCheckBox()));
            //ajusto la columna de cantidad de puntos
            tablaCatPois.getColumnModel().getColumn(4).setMinWidth(50); 
            tablaCatPois.getColumnModel().getColumn(4).setPreferredWidth(50); 
            
            habilitaBtnGraficarDatos(false); 
            chkPoisTodos.setSelected(false);
            chkRecorrido.setSelected(false);
            if (tableModelCatPois.getRowCount()==0){            
                tableModelCatPois.addRow(new Object[]{-1,new JCheckBox(),null,"No se encontraron categorias de Pois en sistema..."});
                habilitaChkTodosLosPois(false);
                //habilitaPanelTablaCatPois(false);
                //habilitaBtnGraficarDatos(false);
            }
            else{
                habilitaChkTodosLosPois(true);
                //habilitaPanelTablaCatPois(true);
                //habilitaBtnGraficarDatos(true);
            }
        }
        else{
                //escondo la columna ELEJIR
                tablaCatPois.getColumnModel().getColumn(1).setMinWidth(0); 
                tablaCatPois.getColumnModel().getColumn(1).setMaxWidth(0); 
                tablaCatPois.getColumnModel().getColumn(1).setPreferredWidth(0); 
                tablaCatPois.getColumnModel().getColumn(1).setResizable(false);
                DefaultTableModel modelo = (DefaultTableModel)tablaCatPois.getModel();
                modelo.setRowCount(0);//vacío la tabla de categorias de POis
                setCategoriasSeleccionadas(new ArrayList()); //inicializo el vector de categorias seleccionadas                
                modelo.addRow(new Object[]{-1,new JCheckBox(),null,"No se ha seleccionado ninguna campaña..."});
                tablaCatPois.setModel(modelo);
                habilitaChkTodosLosPois(false);
        }
        //escondo la columna ID 
        tablaCatPois.getColumnModel().getColumn(0).setMinWidth(0); 
        tablaCatPois.getColumnModel().getColumn(0).setMaxWidth(0); 
        tablaCatPois.getColumnModel().getColumn(0).setPreferredWidth(0); 
        tablaCatPois.getColumnModel().getColumn(0).setResizable(false);        
  }
    
  public void habilitaPanelTablaCatPois(boolean estado){
      lblTxtTablaCatPois.setEnabled(estado);
      tablaCatPois.setEnabled(estado);
  }
  
  public void habilitaChkTodosLosPois(boolean estado){
      chkPoisTodos.setEnabled(estado);
  }
  
  public void habilitaChkRecorrido(boolean estado){
      chkRecorrido.setEnabled(estado);
  }
   
  public void habilitaBtnGraficarDatos(boolean estado){
      btnGraficarDatos.setEnabled(estado);
  }
  
  public void habilitaPanelTablaCampanias(boolean estado){
      tablaCampanias.setEnabled(estado);
  }
  
    public TableModel cargaGrillaCategoriaPOIS() {
        setCategoriasSeleccionadas(new ArrayList()); //inicializo en vacío el vector de categorias seleccionadas
        TableModelCatPoisHistorico dm = new TableModelCatPoisHistorico();
        //Cabecera
        String[] encabezado = new String[5];
        encabezado[0] = "Id";
        encabezado[1] = "Elejir";        
        encabezado[2] = "Icono";
        encabezado[3] = "Nombre de la categoria";
        encabezado[4] = "#Puntos";
        dm.setColumnIdentifiers(encabezado);
        //Cuerpo
        for (CategoriaPoi cP : ControllerHistorico.getInstance().getCatPOISDeUnaCampFromDB(getIdCampaniaElegida())) {
            Object[] fila = new Object[5]; //creamos la fila
            fila[0]=cP.getId(); //en la columna 0 va el ID
            fila[1]=new JCheckBox(); //en la columna 1 va el CheckBox
            fila[2]=cP.getPathIcono();//en la columna 2 va el Icono
            fila[3]=cP.getTitulo();//en la columna 3 va el Nombre de la categoria de POI
            fila[4]=ControllerHistorico.getInstance().getCantPOISDeUnaCampSegunCatPoi(getIdCampaniaElegida(),cP.getId());
            dm.addRow(fila);
        }
        return dm;
    }

    /**
     * @return the categoriasSeleccionadas
     */
    public ArrayList<Integer> getCategoriasSeleccionadas() {
        return categoriasSeleccionadas;
    }

    /**
     * @param categoriasSeleccionadas the categoriasSeleccionadas to set
     */
    public void setCategoriasSeleccionadas(ArrayList<Integer> categoriasSeleccionadas) {
        this.categoriasSeleccionadas = categoriasSeleccionadas;
    }

    public Integer getIdCatPoiFromRow(int row) {
        int idSeleccionado=-1;
        if (row>=0){
            idSeleccionado = Integer.parseInt(tablaCatPois.getModel().getValueAt(row, 0).toString());
        }
        return idSeleccionado;
    }

    private void setTxtCantidadDePuntosDeCampElegida(int cantPuntosHistoricos) {
        if (cantPuntosHistoricos>=0){
            lblCantPuntosRecorrido.setText("Recorrido compuesto por "+cantPuntosHistoricos+" puntos");
        }
        else{
            lblCantPuntosRecorrido.setText("");
        }
    }

    public void seteaBotonesMapa() {
        if (modelo.gisModule.WebServer.getInstance().isWebServerEncendido()){
            btnDetenerMapaHistorico.setVisible(true);
            btnIniciarMapaHistorico.setVisible(false);
        }
        else{
            btnDetenerMapaHistorico.setVisible(false);
            btnIniciarMapaHistorico.setVisible(true);            
        }
    }

    /**
     * @return the txtBtnIniciaMapa
     */
    public String getTxtBtnIniciaMapa() {
        return txtBtnIniciaMapa;
    }

    /**
     * @param txtBtnIniciaMapa the txtBtnIniciaMapa to set
     */
    public void setTxtBtnIniciaMapa(String txtBtnIniciaMapa) {
        this.txtBtnIniciaMapa = txtBtnIniciaMapa;
    }
    
}
/*  - - -   clases y metodos que cargan y controlan los RADIOBUTTONS en la TABLA CAMPAÑAS   - - -  */
class RadioButtonRenderer implements TableCellRenderer {  
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    if (value==null) return null;
    return (Component)value;
  }
} 
class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
  private JRadioButton button;
  public RadioButtonEditor(JCheckBox checkBox) {
    super(checkBox);
  }
  public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) {
    if (value==null) return null;
    button = (JRadioButton)value;
    button.addItemListener(this);
    PanelHistorico.getInstance().setIdCampaniaElegida(row);
    return (Component)value;
  }
  public Object getCellEditorValue() {
    button.removeItemListener(this);
    return button;
  }
  public void itemStateChanged(ItemEvent e) {    
    super.fireEditingStopped();
  }
}


/*  clases y metodos que cargan y controlan los CHECKBOXES en la TABLA CATEGORIA DE POIS */
class CheckBoxRenderer implements TableCellRenderer {  
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    if (value==null) return null;
    return (Component)value;
  }
} 
class CheckBoxEditor extends DefaultCellEditor implements ItemListener {
  private JCheckBox button;
  public CheckBoxEditor(JCheckBox checkBox) {
    super(checkBox);
  }
  public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column) {
    if (value==null) return null;
    button = (JCheckBox)value;
    button.addItemListener(this);
    if (!button.isSelected()){ //trabaja con la lógica invertida, porque el evento lo captura antes de dejarlo seleccionado
        PanelHistorico.getInstance().getCategoriasSeleccionadas().add(PanelHistorico.getInstance().getIdCatPoiFromRow(row));
    }
    else{
        PanelHistorico.getInstance().getCategoriasSeleccionadas().remove(PanelHistorico.getInstance().getIdCatPoiFromRow(row));
    }
    return (Component)value;
  }
  public Object getCellEditorValue() {
    button.removeItemListener(this);
    return button;
  }
  public void itemStateChanged(ItemEvent e) {    
    super.fireEditingStopped();
  }
}

/* - - -   clases y métodos q configuran las COLUMNAS del TABLEMODEL de la tabla CATEGORIA DE POIS   - - -  */
class TableModelCatPoisHistorico extends DefaultTableModel {        
    @Override  
      public Class getColumnClass(int col) {  
        switch (col){
            case 0: return Integer.class;//esta column accepts only Integer values
            case 1: return Boolean.class;
            default: return String.class;//other columns accept String values
        }
    }  
    @Override  
      public boolean isCellEditable(int row, int col) {
        if (col == 1)       //la columna de los checkbox will be editable
            return true;
        else return false;
      }
}