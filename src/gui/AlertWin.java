/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.alertas.AlertaListaOn;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class AlertWin extends javax.swing.JFrame implements Observer,Runnable{
    static AlertWin unicaInstancia;
    private Thread threadAlertWin;
    private boolean accionMouse=false;
    private boolean show;
    private boolean counter;
    private int idShowing;
    /**
     * Creates new form Splash
     */
    public AlertWin(){
       controllers.ControllerAlertas.getInstance().addObserver(this);
    }
    public static AlertWin getInstance() {
       if (unicaInstancia == null){
          unicaInstancia = new AlertWin();
       }

       return unicaInstancia;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAlerta = new javax.swing.JPanel();
        panelContenidoAlerta = new org.jdesktop.swingx.JXPanel();
        panelDatosAlerta = new org.jdesktop.swingx.JXPanel();
        panelTitulo = new org.jdesktop.swingx.JXPanel();
        lblTituloAlerta = new org.jdesktop.swingx.JXLabel();
        panelDescripcion = new org.jdesktop.swingx.JXPanel();
        panelSuperior = new org.jdesktop.swingx.JXPanel();
        panelIcono = new org.jdesktop.swingx.JXPanel();
        lblIcono = new org.jdesktop.swingx.JXLabel();
        panelFechas = new org.jdesktop.swingx.JXPanel();
        labelFechaActivacion = new org.jdesktop.swingx.JXLabel();
        labelFechaActivacionDatos = new org.jdesktop.swingx.JXLabel();
        labelFechaDesactivacion = new org.jdesktop.swingx.JXLabel();
        labelFechaDesactivacionDatos = new org.jdesktop.swingx.JXLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescripcion = new javax.swing.JTextArea();
        panelNavegacionAlertas = new org.jdesktop.swingx.JXPanel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        barraTimeout = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(300, 550));

        panelAlerta.setPreferredSize(new java.awt.Dimension(300, 480));
        panelAlerta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                f(evt);
            }
        });
        panelAlerta.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlertWin.this.mouseMoved(evt);
            }
        });
        panelAlerta.setLayout(new java.awt.BorderLayout());

        panelContenidoAlerta.setPreferredSize(new java.awt.Dimension(300, 480));
        panelContenidoAlerta.setLayout(new java.awt.BorderLayout());

        panelDatosAlerta.setPreferredSize(new java.awt.Dimension(300, 450));
        panelDatosAlerta.setLayout(new java.awt.BorderLayout());

        panelTitulo.setForeground(new java.awt.Color(0, 0, 204));
        panelTitulo.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        panelTitulo.setPreferredSize(new java.awt.Dimension(300, 50));

        lblTituloAlerta.setForeground(new java.awt.Color(0, 0, 255));
        lblTituloAlerta.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        panelTitulo.add(lblTituloAlerta);

        panelDatosAlerta.add(panelTitulo, java.awt.BorderLayout.NORTH);

        panelDescripcion.setPreferredSize(new java.awt.Dimension(300, 400));
        panelDescripcion.setLayout(new java.awt.BorderLayout());

        panelSuperior.setMinimumSize(new java.awt.Dimension(300, 100));
        panelSuperior.setPreferredSize(new java.awt.Dimension(300, 100));
        panelSuperior.setLayout(new java.awt.BorderLayout());

        panelIcono.setMaximumSize(new java.awt.Dimension(300, 200));
        panelIcono.setMinimumSize(new java.awt.Dimension(300, 50));
        panelIcono.setPreferredSize(new java.awt.Dimension(100, 50));
        panelIcono.setLayout(new javax.swing.BoxLayout(panelIcono, javax.swing.BoxLayout.LINE_AXIS));

        lblIcono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-alertas.png"))); // NOI18N
        lblIcono.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblIcono.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblIcono.setMaximumSize(new java.awt.Dimension(300, 100));
        lblIcono.setMinimumSize(new java.awt.Dimension(300, 100));
        lblIcono.setPreferredSize(new java.awt.Dimension(300, 100));
        lblIcono.setTextAlignment(org.jdesktop.swingx.JXLabel.TextAlignment.CENTER);
        lblIcono.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panelIcono.add(lblIcono);

        panelSuperior.add(panelIcono, java.awt.BorderLayout.PAGE_START);

        panelFechas.setPreferredSize(new java.awt.Dimension(300, 50));
        panelFechas.setLayout(new java.awt.GridLayout(2, 2));

        labelFechaActivacion.setText("Activación:");
        panelFechas.add(labelFechaActivacion);
        panelFechas.add(labelFechaActivacionDatos);

        labelFechaDesactivacion.setText("Desactivación:");
        panelFechas.add(labelFechaDesactivacion);
        panelFechas.add(labelFechaDesactivacionDatos);

        panelSuperior.add(panelFechas, java.awt.BorderLayout.SOUTH);

        panelDescripcion.add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 300));

        jTextAreaDescripcion.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        jTextAreaDescripcion.setColumns(20);
        jTextAreaDescripcion.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextAreaDescripcion.setLineWrap(true);
        jTextAreaDescripcion.setRows(5);
        jTextAreaDescripcion.setFocusable(false);
        jTextAreaDescripcion.setPreferredSize(new java.awt.Dimension(300, 290));
        jScrollPane1.setViewportView(jTextAreaDescripcion);

        panelDescripcion.add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        panelDatosAlerta.add(panelDescripcion, java.awt.BorderLayout.CENTER);

        panelContenidoAlerta.add(panelDatosAlerta, java.awt.BorderLayout.NORTH);

        panelNavegacionAlertas.setPreferredSize(new java.awt.Dimension(253, 30));

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        panelNavegacionAlertas.add(btnAnterior);

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        panelNavegacionAlertas.add(btnSiguiente);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        panelNavegacionAlertas.add(btnCerrar);

        panelContenidoAlerta.add(panelNavegacionAlertas, java.awt.BorderLayout.SOUTH);

        panelAlerta.add(panelContenidoAlerta, java.awt.BorderLayout.NORTH);

        getContentPane().add(panelAlerta, java.awt.BorderLayout.NORTH);

        barraTimeout.setMaximumSize(new java.awt.Dimension(32767, 20));
        barraTimeout.setPreferredSize(new java.awt.Dimension(146, 20));
        getContentPane().add(barraTimeout, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        setShow(false);
        this.setVisible(false);
        int index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
        }
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void f(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_f
        setAccionMouse(true);
    }//GEN-LAST:event_f

    private void mouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseMoved
        setAccionMouse(true);
    }//GEN-LAST:event_mouseMoved

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed

        int index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
        }
        controllers.ControllerAlertas.getInstance().muestraOcurAnt(getIdShowing());
        muestraAlertaSinPreload();
        index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
        }
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed

        int index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
        }
        controllers.ControllerAlertas.getInstance().muestraOcurSig(getIdShowing());
        muestraAlertaSinPreload();
        index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
        }
    }//GEN-LAST:event_btnSiguienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraTimeout;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescripcion;
    private org.jdesktop.swingx.JXLabel labelFechaActivacion;
    private org.jdesktop.swingx.JXLabel labelFechaActivacionDatos;
    private org.jdesktop.swingx.JXLabel labelFechaDesactivacion;
    private org.jdesktop.swingx.JXLabel labelFechaDesactivacionDatos;
    private org.jdesktop.swingx.JXLabel lblIcono;
    private org.jdesktop.swingx.JXLabel lblTituloAlerta;
    private javax.swing.JPanel panelAlerta;
    private org.jdesktop.swingx.JXPanel panelContenidoAlerta;
    private org.jdesktop.swingx.JXPanel panelDatosAlerta;
    private org.jdesktop.swingx.JXPanel panelDescripcion;
    private org.jdesktop.swingx.JXPanel panelFechas;
    private org.jdesktop.swingx.JXPanel panelIcono;
    private org.jdesktop.swingx.JXPanel panelNavegacionAlertas;
    private org.jdesktop.swingx.JXPanel panelSuperior;
    private org.jdesktop.swingx.JXPanel panelTitulo;
    // End of variables declaration//GEN-END:variables

    
    public void hacePreLoad(){
        int i=0;
        setShow(true);
        while((i<=10)&&(isShow())){
            barraTimeout.setString("Alerta se cerrara en: "+Integer.toString(10-i)+" segs.");
            barraTimeout.setValue(i*10);
            try {
               if (!isAccionMouse()) {
                   threadAlertWin.sleep(1000);
               }else{
                   i=0;
                   threadAlertWin.sleep(200);
                   setAccionMouse(false);
               }
             
            } catch (InterruptedException ex) {
                Logger.getLogger(AlertWin.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
        }
        int index=controllers.ControllerAlertas.getInstance().getIndexAlertaShowing(getIdShowing());
        if (i==11){

        }else{
            if (controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).getVista()==0){
            controllers.ControllerAlertas.getInstance().getAlertasActivadas().get(index).setVista(1);
            controllers.ControllerAlertas.getInstance().setCantOcurNoVistas(controllers.ControllerAlertas.getInstance().getCantOcurNoVistas()-1);
            }
        }
    }

    private void inicializador() {
        this.setVisible(false);
        initComponents();
        this.setTitle("Alerta");
        panelAlerta.setOpaque(false);
        barraTimeout.setBorderPainted(true);
        barraTimeout.setForeground(new Color(50,50,153,100));
        barraTimeout.setStringPainted(true);
        int posicionX = (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(getWidth()/2);
        int posicionY = Toolkit.getDefaultToolkit().getScreenSize().height/2-(getHeight()/2);
        this.setLocation(posicionX,posicionY);  
        //hacePreLoad();        
        //VentanaIbape.getInstance().setVisible(true);
    }
    
    public void muestraAlerta(){
        setAccionMouse(false);
        setVisible(true);
        setCounter(true);
        hacePreLoad();
        setVisible(false);
        gui.PanelOpcAlertas.getInstance().actualizaLabelCantOcurNoVistas();
        gui.PanelOpcAlertas.getInstance().revalidate();
        
    }
    
   public void muestraAlertaSinPreload(){
        setAccionMouse(false);
        //setShow(true);
        setVisible(true);        
    }
    
    public void disparaVentanaAlertas(){
        this.start();
    }
    

    
    public void start(){
       if (threadAlertWin == null) {
            threadAlertWin = new Thread(this);
            threadAlertWin.setPriority(Thread.MIN_PRIORITY);
            threadAlertWin.start();
        }
    }
    

    
 /*
    public static void main(String args[]) {       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash();
            }
        });
    }
*/

    

    public void update(Observable o, Object arg) {
      controllers.ControllerAlertas c = controllers.ControllerAlertas.getInstance();
      if (o == c){
            AlertaListaOn a=c.getAlertaShowing(getIdShowing());
            lblTituloAlerta.setText(a.getAlerta().getTitulo());
            int cantCondiciones=a.getAlerta().getCondiciones().size();
            String descripcion=a.getAlerta().getMensaje();    
            /*for (int i=0;i<=cantCondiciones-1;i++){
                    descripcion=descripcion+c.getAlertasActivadas().get(c.getAlertasActivadas().size()-1).getAlerta().getCondiciones().get(i).getDescripcion();
            }*/
            jTextAreaDescripcion.setText(descripcion);
            String fechaAct = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(a.getFechaActivacion());
            if (!a.isEstadoActivacion()){
                String fechaDes = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(a.getFechaDesactivacion());
                labelFechaDesactivacionDatos.setText(fechaDes);
            }else{
                labelFechaDesactivacionDatos.setText("");
            }
            labelFechaActivacionDatos.setText(fechaAct);

            
      }
    }

    /**
     * @return the accionMouse
     */
    public boolean isAccionMouse() {
        return accionMouse;
    }

    /**
     * @param accionMouse the accionMouse to set
     */
    public void setAccionMouse(boolean accionMouse) {
        this.accionMouse = accionMouse;
    }

    /**
     * @return the show
     */
    public boolean isShow() {
        return show;
    }

    /**
     * @param show the show to set
     */
    public void setShow(boolean show) {
        this.show = show;
    }

    /**
     * @return the idShowing
     */
    public int getIdShowing() {
        return idShowing;
    }

    /**
     * @param idShowing the idShowing to set
     */
    public void setIdShowing(int idShowing) {
        this.idShowing = idShowing;
    }

    public void run() {
        inicializador();
    }

    void muestraUltAlerta() {
        setAccionMouse(true);
        setVisible(true);
        hacePreLoad();
        setVisible(false);
    }

    /**
     * @return the counter
     */
    public boolean isCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(boolean counter) {
        this.counter = counter;
    }
    
}
