/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelOpcInformes.java
 *
 * Created on 11/04/2012, 14:48:19
 */
package gui;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;
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
import pdf.generadorPDF;
import persistencia.BrokerCajon;
import persistencia.BrokerLance;


/**
 *
 * @author emmmau
 */
public class PanelOpcInformes extends javax.swing.JPanel {

    static PanelOpcInformes unicaInstancia;
    private DefaultTableModel modeloTabla;
    private ButtonGroup grupoElijeCampania;
    private ArrayList<Integer> categoriasSeleccionadas = new ArrayList();
    private String txtBtnIniciaMapa;
    private String txtBtnGraficarDatos;
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
    private PanelOpcInformes() {
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
        lblTituloInformes = new java.awt.Label();
        panelCampanias = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCampanias = new org.jdesktop.swingx.JXTable();
        panelDatosInforme = new javax.swing.JPanel();
        panelRecorrido1 = new org.jdesktop.swingx.JXPanel();
        lblCantPuntosRecorrido1 = new org.jdesktop.swingx.JXLabel();
        lblTxtDatosInforme1 = new java.awt.Label();
        chkFechaUbicacion = new javax.swing.JCheckBox();
        chkBarco = new javax.swing.JCheckBox();
        chkCampana = new javax.swing.JCheckBox();
        chkLance = new javax.swing.JCheckBox();
        chkCajones = new javax.swing.JCheckBox();
        chkPois = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        javax.swing.JPanel panelBtnGenerarInforme = new javax.swing.JPanel();
        btnGenerarInforme = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(500, 500));
        setMinimumSize(new java.awt.Dimension(500, 500));
        setPreferredSize(new java.awt.Dimension(500, 500));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        panelTitulo.setMaximumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setMinimumSize(new java.awt.Dimension(500, 40));
        panelTitulo.setPreferredSize(new java.awt.Dimension(500, 40));

        lblTituloInformes.setFont(new java.awt.Font("Arial", 0, 18));
        lblTituloInformes.setText("Informes");
        panelTitulo.add(lblTituloInformes);

        add(panelTitulo);

        panelCampanias.setMaximumSize(new java.awt.Dimension(500, 200));
        panelCampanias.setMinimumSize(new java.awt.Dimension(500, 200));
        panelCampanias.setPreferredSize(new java.awt.Dimension(500, 200));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(492, 192));

        tablaCampanias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        tablaCampanias.setShowGrid(true);
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

        panelDatosInforme.setMaximumSize(new java.awt.Dimension(500, 1000));
        panelDatosInforme.setMinimumSize(new java.awt.Dimension(500, 1000));
        panelDatosInforme.setPreferredSize(new java.awt.Dimension(1000, 100));
        panelDatosInforme.setLayout(new java.awt.GridLayout(10, 1));

        panelRecorrido1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        lblCantPuntosRecorrido1.setForeground(new java.awt.Color(0, 204, 51));
        lblCantPuntosRecorrido1.setFont(new java.awt.Font("Tahoma", 2, 12));
        panelRecorrido1.add(lblCantPuntosRecorrido1);

        lblTxtDatosInforme1.setFont(new java.awt.Font("Tahoma", 0, 12));
        lblTxtDatosInforme1.setText("Datos a incluir en el Informe:");
        panelRecorrido1.add(lblTxtDatosInforme1);

        panelDatosInforme.add(panelRecorrido1);

        chkFechaUbicacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkFechaUbicacion.setText("Datos de Fecha y Ubicación");
        chkFechaUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFechaUbicacionActionPerformed(evt);
            }
        });
        panelDatosInforme.add(chkFechaUbicacion);

        chkBarco.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkBarco.setText("Datos del Barco");
        chkBarco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkBarcoActionPerformed(evt);
            }
        });
        panelDatosInforme.add(chkBarco);

        chkCampana.setText("Datos de la Campaña");
        panelDatosInforme.add(chkCampana);

        chkLance.setText("Datos de Lances");
        panelDatosInforme.add(chkLance);

        chkCajones.setText("Datos de Cajones de cada Lance");
        panelDatosInforme.add(chkCajones);

        chkPois.setText("Datos de POIS de la Campaña");
        panelDatosInforme.add(chkPois);

        jCheckBox5.setText("jCheckBox5");
        panelDatosInforme.add(jCheckBox5);

        jCheckBox6.setText("jCheckBox6");
        panelDatosInforme.add(jCheckBox6);

        jCheckBox7.setText("jCheckBox7");
        panelDatosInforme.add(jCheckBox7);

        add(panelDatosInforme);

        panelBtnGenerarInforme.setMaximumSize(new java.awt.Dimension(500, 40));
        panelBtnGenerarInforme.setMinimumSize(new java.awt.Dimension(500, 40));
        panelBtnGenerarInforme.setPreferredSize(new java.awt.Dimension(500, 40));
        panelBtnGenerarInforme.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btnGenerarInforme.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnGenerarInforme.setText("Generar Informe");
        btnGenerarInforme.setAutoscrolls(true);
        btnGenerarInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarInformeActionPerformed(evt);
            }
        });
        panelBtnGenerarInforme.add(btnGenerarInforme);

        add(panelBtnGenerarInforme);
    }// </editor-fold>//GEN-END:initComponents



    private void chkFechaUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFechaUbicacionActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkFechaUbicacionActionPerformed

    private void chkBarcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkBarcoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_chkBarcoActionPerformed

    private void btnGenerarInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarInformeActionPerformed

// TODO add your handling code here:
         Date fechaHoy = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
         String fecha = null,ubicacion = null,barco= null,capitan= null,nombreCampania= null,descripcion= null,fechaIniciostring= null,fechaFinstring=null;
        if (getIdCampaniaElegida()>=0){
           if (getChkFechaUbicacion().isSelected()){
               fecha= (formato.format(fechaHoy));
               ubicacion= "Mar del Plata";
           }
           if (getChkBarco().isSelected()){
               barco = (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_BARCO);
               capitan = (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_CAPITAN);
           }
           if (getChkCampana().isSelected()){

               String idCampania =  (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_ID_CAMP);
               int idCampaniaint = Integer.parseInt(idCampania );
               nombreCampania =  (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_NOMBRE_CAMP);
               descripcion = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaint).getDescripcion();
               Date fechaInicio = modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaint).getFechaInicio();
               Date fechaFin= modelo.dataManager.AdministraCampanias.getInstance().getCampania(idCampaniaint).getFechaFin();
               fechaIniciostring=formato.format(fechaInicio);
               fechaFinstring=formato.format(fechaFin);
           }
           if (getChkLance().isSelected()){
               String idCampania =  (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_ID_CAMP);
               int idCampaniaint = Integer.parseInt(idCampania );
               BrokerLance.getInstance().getLancesCampaniaFromDB(idCampaniaint);
           }
           if (getChkCajones().isSelected()){
               String idCampania =  (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_ID_CAMP);
               int idCampaniaint = Integer.parseInt(idCampania );
  //             BrokerCajon.getInstance().getCajonesFromDB().;
  //             getCajonesLanceFromDB(int idLance):ArrayList<modelo.dataManager.Cajon>
           }
           if (getChkPois().isSelected()){
               String idCampania =  (String) modeloTabla.getValueAt(tablaCampanias.getSelectedRow(), NRO_COL_ID_CAMP);
               int idCampaniaint = Integer.parseInt(idCampania );
    //           ControllerHistorico.getInstance().getCatPOISDeUnaCampFromDB(idCampaniaint);
    //           para obtener la cant de puntos de cada categoria usar el método
    //           ControllerHistorico.getInstance().getCantPOISDeUnaCampSegunCatPoi(idCampaniaint,CP.getId());
           }

      generadorPDF pdf=new generadorPDF();
      pdf.crear_PDF("Informe de pesca:",ubicacion,fecha,barco,capitan,nombreCampania,descripcion,fechaIniciostring,fechaFinstring);
      //pdf.crear_PDF(TITULO.getText(), AUTOR.getText(), ASUNTO.getText(), CLAVE.getText(), TEXTO.getText());
     }
}//GEN-LAST:event_btnGenerarInformeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarInforme;
    private javax.swing.JCheckBox chkBarco;
    private javax.swing.JCheckBox chkCajones;
    private javax.swing.JCheckBox chkCampana;
    private javax.swing.JCheckBox chkFechaUbicacion;
    private javax.swing.JCheckBox chkLance;
    private javax.swing.JCheckBox chkPois;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JScrollPane jScrollPane1;
    private org.jdesktop.swingx.JXLabel lblCantPuntosRecorrido1;
    private java.awt.Label lblTituloInformes;
    private java.awt.Label lblTxtDatosInforme1;
    private javax.swing.JPanel panelCampanias;
    private javax.swing.JPanel panelDatosInforme;
    private org.jdesktop.swingx.JXPanel panelRecorrido1;
    private javax.swing.JPanel panelTitulo;
    private org.jdesktop.swingx.JXTable tablaCampanias;
    // End of variables declaration//GEN-END:variables

    public static PanelOpcInformes getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new PanelOpcInformes();
        }
        return unicaInstancia;
    }

    public void agregaUnaFilaCampania(int id, String nombre, String barco, String capitan,
            int estado, Date fechaInicio, Date fechaFin, int cantCajones) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[cantColumnas]; //creamos la fila
        if (fechaInicio != null) {
            fila[NRO_COL_FECHA_INI] = sdf.format(fechaInicio);
        }
        if (fechaFin != null) {
            fila[NRO_COL_FECHA_FIN] = sdf.format(fechaFin);
        }
        fila[NRO_COL_CAPITAN] = capitan;
        fila[NRO_COL_NOMBRE_CAMP] = nombre;
        fila[NRO_COL_BARCO] = barco;
        fila[NRO_COL_ID_CAMP] = id;
        fila[NRO_COL_CAJONES] = cantCajones;
        JRadioButton radioBtnCampania = new JRadioButton();
        grupoElijeCampania.add(radioBtnCampania);//lo agrego al grupo, lo cual me garantiza la selección simple
        fila[NRO_COL_ELEGIR] = radioBtnCampania;

        modeloTabla.addRow(fila);
    }

    public void vaciaTabla() {
        modeloTabla.setRowCount(0);
        setIdCampaniaElegida(-1);
    }

    private void inicializador() {
        NRO_COL_ID_CAMP = 0;
        NRO_COL_ELEGIR = 1;
        NRO_COL_FECHA_INI = 2;
        NRO_COL_FECHA_FIN = 3;
        NRO_COL_CAJONES = 4;
        NRO_COL_BARCO = 5;
        NRO_COL_CAPITAN = 6;
        NRO_COL_NOMBRE_CAMP = 7;
        cantColumnas = 8;
        modeloTabla = (DefaultTableModel) tablaCampanias.getModel();
        tablaCampanias.setModel(modeloTabla);
        //seteo los radiobotones de la tabla
        grupoElijeCampania = new ButtonGroup();
        tablaCampanias.getColumn(1).setCellRenderer(new RadioButtonRenderer2());
        tablaCampanias.getColumn(1).setCellEditor(new RadioButtonEditor2(new JCheckBox()));

      
    }

    public void marcaCampaniaEnCurso() {
        if (modelo.dataManager.AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            boolean encontro = false;
            //recorre las campañas de la tabla y cuando encuentre una que no tiene fecha de fin,
            //será la campaña en curso, y en su fecha de fin le pondrá el texto que la distinga
            int i = 0;
            int idLeido;
            while ((i < modeloTabla.getRowCount()) && (!(encontro))) {
                idLeido = (Integer) modeloTabla.getValueAt(i, NRO_COL_ID_CAMP);
                if (idLeido == ControllerCampania.getInstance().getIdCampaniaEnCurso()) {
                    encontro = true;
                } else {
                    i++;
                }
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
        if (idCampaniaElegida >= 0) {
            this.idCampaniaElegida = Integer.parseInt(modeloTabla.getValueAt(idCampaniaElegida, 0).toString());
        } else {
            this.idCampaniaElegida = idCampaniaElegida;
        }

    }



    public void habilitaPanelTablaCampanias(boolean estado) {
        tablaCampanias.setEnabled(estado);
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

    /**
     * @return the txtBtnGraficarDatos
     */
    public String getTxtBtnGraficarDatos() {
        return txtBtnGraficarDatos;
    }

    /**
     * @param txtBtnGraficarDatos the txtBtnGraficarDatos to set
     */
    public void setTxtBtnGraficarDatos(String txtBtnGraficarDatos) {
        this.txtBtnGraficarDatos = txtBtnGraficarDatos;
    }

    /**
     * @return the chkBarco
     */
    public javax.swing.JCheckBox getChkBarco() {
        return chkBarco;
    }

    /**
     * @param chkBarco the chkBarco to set
     */
    public void setChkBarco(javax.swing.JCheckBox chkBarco) {
        this.chkBarco = chkBarco;
    }

    /**
     * @return the chkCajones
     */
    public javax.swing.JCheckBox getChkCajones() {
        return chkCajones;
    }

    /**
     * @param chkCajones the chkCajones to set
     */
    public void setChkCajones(javax.swing.JCheckBox chkCajones) {
        this.chkCajones = chkCajones;
    }

    /**
     * @return the chkCampana
     */
    public javax.swing.JCheckBox getChkCampana() {
        return chkCampana;
    }

    /**
     * @param chkCampana the chkCampana to set
     */
    public void setChkCampana(javax.swing.JCheckBox chkCampana) {
        this.chkCampana = chkCampana;
    }

    /**
     * @return the chkFechaUbicacion
     */
    public javax.swing.JCheckBox getChkFechaUbicacion() {
        return chkFechaUbicacion;
    }

    /**
     * @param chkFechaUbicacion the chkFechaUbicacion to set
     */
    public void setChkFechaUbicacion(javax.swing.JCheckBox chkFechaUbicacion) {
        this.chkFechaUbicacion = chkFechaUbicacion;
    }

    /**
     * @return the chkLance
     */
    public javax.swing.JCheckBox getChkLance() {
        return chkLance;
    }

    /**
     * @param chkLance the chkLance to set
     */
    public void setChkLance(javax.swing.JCheckBox chkLance) {
        this.chkLance = chkLance;
    }

    /**
     * @return the chkPois
     */
    public javax.swing.JCheckBox getChkPois() {
        return chkPois;
    }

    /**
     * @param chkPois the chkPois to set
     */
    public void setChkPois(javax.swing.JCheckBox chkPois) {
        this.chkPois = chkPois;
    }
}
/*  - - -   clases y metodos que cargan y controlan los RADIOBUTTONS en la TABLA CAMPAÑAS   - - -  */

class RadioButtonRenderer2 implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            return null;
        }
        return (Component) value;
    }
}

class RadioButtonEditor2 extends DefaultCellEditor implements ItemListener {

    private JRadioButton button;

    public RadioButtonEditor2(JCheckBox checkBox) {
        super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value == null) {
            return null;
        }
        button = (JRadioButton) value;
        button.addItemListener(this);
        PanelOpcInformes.getInstance().setIdCampaniaElegida(row);
        return (Component) value;
    }

    public Object getCellEditorValue() {
        button.removeItemListener(this);
        return button;
    }

    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }
}
