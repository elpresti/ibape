/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelBarraDeEstado.java
 *
 * Created on 30/04/2012, 20:27:34
 */
package gui;

import controllers.ControllerNavegacion;
import controllers.ControllerPpal;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Timer;
import modelo.dataManager.UltimaImgProcesada;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class PanelBarraDeEstado extends javax.swing.JPanel implements Runnable {
    Thread threadBarraEstado;
    static PanelBarraDeEstado unicaInstancia;
    ArrayList<String> colaMensajes=new ArrayList<String>();
    
    
    /** Creates new form PanelBarraDeEstado */
    private PanelBarraDeEstado() {
        initComponents();
        start();
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

        barraEstado = new org.jdesktop.swingx.JXStatusBar();
        panelIzquierdo = new org.jdesktop.swingx.JXPanel();
        barraProcesaImg = new javax.swing.JProgressBar();
        lblMensaje = new org.jdesktop.swingx.JXLabel();
        panelMedio = new org.jdesktop.swingx.JXPanel();
        panelDerecho = new org.jdesktop.swingx.JXPanel();
        HistoricoEstado = new javax.swing.JPanel();
        lblHistoricoEstado = new org.jdesktop.swingx.JXLabel();
        AlertasEstado = new javax.swing.JPanel();
        lblAlertasEstado = new org.jdesktop.swingx.JXLabel();
        GpsEstado = new javax.swing.JPanel();
        lblGpsEstado = new org.jdesktop.swingx.JXLabel();
        SondaEstado = new javax.swing.JPanel();
        lblSondaEstado = new org.jdesktop.swingx.JXLabel();
        LanEstado = new javax.swing.JPanel();
        lblLanEstado = new org.jdesktop.swingx.JXLabel();

        setMaximumSize(new java.awt.Dimension(2147483647, 30));
        setMinimumSize(new java.awt.Dimension(500, 20));
        setPreferredSize(new java.awt.Dimension(700, 30));
        setLayout(new java.awt.BorderLayout());

        barraEstado.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        barraEstado.setMinimumSize(new java.awt.Dimension(500, 20));
        barraEstado.setPreferredSize(new java.awt.Dimension(700, 30));
        barraEstado.setLayout(new java.awt.BorderLayout());

        panelIzquierdo.setMinimumSize(new java.awt.Dimension(10, 20));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setPreferredSize(new java.awt.Dimension(450, 30));
        panelIzquierdo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        barraProcesaImg.setPreferredSize(new java.awt.Dimension(350, 17));
        panelIzquierdo.add(barraProcesaImg);

        lblMensaje.setText("Bienvenido a IBAPE!");
        lblMensaje.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelIzquierdo.add(lblMensaje);

        barraEstado.add(panelIzquierdo, java.awt.BorderLayout.WEST);

        panelMedio.setMaximumSize(new java.awt.Dimension(1, 30));
        panelMedio.setMinimumSize(new java.awt.Dimension(1, 30));
        panelMedio.setOpaque(false);
        panelMedio.setPreferredSize(new java.awt.Dimension(1, 30));
        panelMedio.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));
        barraEstado.add(panelMedio, java.awt.BorderLayout.CENTER);

        panelDerecho.setMaximumSize(new java.awt.Dimension(3205, 30));
        panelDerecho.setMinimumSize(new java.awt.Dimension(250, 30));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new java.awt.Dimension(250, 30));
        panelDerecho.setLayout(new java.awt.GridLayout(1, 5, 8, 0));

        HistoricoEstado.setBackground(new java.awt.Color(204, 0, 0));
        HistoricoEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        HistoricoEstado.setToolTipText("Estado de la conexión de red a la Sonda");
        HistoricoEstado.setEnabled(false);
        HistoricoEstado.setMaximumSize(new java.awt.Dimension(30, 20));
        HistoricoEstado.setMinimumSize(new java.awt.Dimension(30, 20));

        lblHistoricoEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblHistoricoEstado.setText("HIST");

        javax.swing.GroupLayout HistoricoEstadoLayout = new javax.swing.GroupLayout(HistoricoEstado);
        HistoricoEstado.setLayout(HistoricoEstadoLayout);
        HistoricoEstadoLayout.setHorizontalGroup(
            HistoricoEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(HistoricoEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HistoricoEstadoLayout.createSequentialGroup()
                    .addGap(0, 15, Short.MAX_VALUE)
                    .addComponent(lblHistoricoEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 16, Short.MAX_VALUE)))
        );
        HistoricoEstadoLayout.setVerticalGroup(
            HistoricoEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
            .addGroup(HistoricoEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(HistoricoEstadoLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(lblHistoricoEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        panelDerecho.add(HistoricoEstado);

        AlertasEstado.setBackground(new java.awt.Color(204, 0, 0));
        AlertasEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AlertasEstado.setToolTipText("Estado de la conexión de red a la Sonda");
        AlertasEstado.setEnabled(false);
        AlertasEstado.setMaximumSize(new java.awt.Dimension(30, 20));
        AlertasEstado.setMinimumSize(new java.awt.Dimension(30, 20));

        lblAlertasEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblAlertasEstado.setText("ALERT");

        javax.swing.GroupLayout AlertasEstadoLayout = new javax.swing.GroupLayout(AlertasEstado);
        AlertasEstado.setLayout(AlertasEstadoLayout);
        AlertasEstadoLayout.setHorizontalGroup(
            AlertasEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(AlertasEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AlertasEstadoLayout.createSequentialGroup()
                    .addGap(0, 11, Short.MAX_VALUE)
                    .addComponent(lblAlertasEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );
        AlertasEstadoLayout.setVerticalGroup(
            AlertasEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
            .addGroup(AlertasEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AlertasEstadoLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(lblAlertasEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        panelDerecho.add(AlertasEstado);

        GpsEstado.setBackground(new java.awt.Color(204, 0, 0));
        GpsEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        GpsEstado.setToolTipText("Estado del puerto serie del Gps");
        GpsEstado.setEnabled(false);
        GpsEstado.setMaximumSize(new java.awt.Dimension(30, 20));
        GpsEstado.setMinimumSize(new java.awt.Dimension(30, 20));
        GpsEstado.setName(""); // NOI18N

        lblGpsEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblGpsEstado.setText("GPS");

        javax.swing.GroupLayout GpsEstadoLayout = new javax.swing.GroupLayout(GpsEstado);
        GpsEstado.setLayout(GpsEstadoLayout);
        GpsEstadoLayout.setHorizontalGroup(
            GpsEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(GpsEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(GpsEstadoLayout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(lblGpsEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        GpsEstadoLayout.setVerticalGroup(
            GpsEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
            .addGroup(GpsEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(GpsEstadoLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(lblGpsEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        panelDerecho.add(GpsEstado);

        SondaEstado.setBackground(new java.awt.Color(204, 0, 0));
        SondaEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        SondaEstado.setToolTipText("Estado del puerto serie de la Sonda");
        SondaEstado.setEnabled(false);
        SondaEstado.setMaximumSize(new java.awt.Dimension(30, 20));
        SondaEstado.setMinimumSize(new java.awt.Dimension(30, 20));

        lblSondaEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblSondaEstado.setText("Sonda");

        javax.swing.GroupLayout SondaEstadoLayout = new javax.swing.GroupLayout(SondaEstado);
        SondaEstado.setLayout(SondaEstadoLayout);
        SondaEstadoLayout.setHorizontalGroup(
            SondaEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(SondaEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SondaEstadoLayout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(lblSondaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );
        SondaEstadoLayout.setVerticalGroup(
            SondaEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
            .addGroup(SondaEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(SondaEstadoLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(lblSondaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        panelDerecho.add(SondaEstado);

        LanEstado.setBackground(new java.awt.Color(204, 0, 0));
        LanEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        LanEstado.setToolTipText("Estado de la conexión de red a la Sonda");
        LanEstado.setEnabled(false);
        LanEstado.setMaximumSize(new java.awt.Dimension(30, 20));
        LanEstado.setMinimumSize(new java.awt.Dimension(30, 20));

        lblLanEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblLanEstado.setText("LAN");

        javax.swing.GroupLayout LanEstadoLayout = new javax.swing.GroupLayout(LanEstado);
        LanEstado.setLayout(LanEstadoLayout);
        LanEstadoLayout.setHorizontalGroup(
            LanEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
            .addGroup(LanEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LanEstadoLayout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(lblLanEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 18, Short.MAX_VALUE)))
        );
        LanEstadoLayout.setVerticalGroup(
            LanEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
            .addGroup(LanEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LanEstadoLayout.createSequentialGroup()
                    .addGap(0, 7, Short.MAX_VALUE)
                    .addComponent(lblLanEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 8, Short.MAX_VALUE)))
        );

        panelDerecho.add(LanEstado);

        barraEstado.add(panelDerecho, java.awt.BorderLayout.EAST);

        add(barraEstado, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlertasEstado;
    private javax.swing.JPanel GpsEstado;
    private javax.swing.JPanel HistoricoEstado;
    private javax.swing.JPanel LanEstado;
    private javax.swing.JPanel SondaEstado;
    private org.jdesktop.swingx.JXStatusBar barraEstado;
    private javax.swing.JProgressBar barraProcesaImg;
    private org.jdesktop.swingx.JXLabel lblAlertasEstado;
    private org.jdesktop.swingx.JXLabel lblGpsEstado;
    private org.jdesktop.swingx.JXLabel lblHistoricoEstado;
    private org.jdesktop.swingx.JXLabel lblLanEstado;
    private org.jdesktop.swingx.JXLabel lblMensaje;
    private org.jdesktop.swingx.JXLabel lblSondaEstado;
    private org.jdesktop.swingx.JXPanel panelDerecho;
    private org.jdesktop.swingx.JXPanel panelIzquierdo;
    private org.jdesktop.swingx.JXPanel panelMedio;
    // End of variables declaration//GEN-END:variables

    public static PanelBarraDeEstado getInstance() {
       if (unicaInstancia == null) {
          unicaInstancia = new PanelBarraDeEstado();          
       }
       return unicaInstancia;
    }
    
    public void setGpsDesconectado(){
        GpsEstado.setBackground(new Color(204,0,0));
        GpsEstado.setToolTipText("Desconectado");
        lblGpsEstado.setForeground(Color.white);
    }
    
    public void setGpsConectando(){
        GpsEstado.setBackground(Color.orange);
        GpsEstado.setToolTipText("Conectando...");        
        lblGpsEstado.setForeground(Color.black);
    }
    
    public void setGpsConectado(){
        GpsEstado.setBackground(Color.green);
        GpsEstado.setToolTipText("Conectado");                
        lblGpsEstado.setForeground(Color.black);
    }

    public void setSondaDesconectado(){
        SondaEstado.setBackground(new Color(204,0,0));
        SondaEstado.setToolTipText("Desconectado");
        lblSondaEstado.setForeground(Color.white);
    }    
    
    public void setSondaConectando(){
        SondaEstado.setBackground(Color.orange);
        SondaEstado.setToolTipText("Conectando...");        
        lblSondaEstado.setForeground(Color.black);
    }

    public void setSondaConectado(){
        SondaEstado.setBackground(Color.green);
        SondaEstado.setToolTipText("Conectado");                
        lblSondaEstado.setForeground(Color.black);
    }
    
    public void setLanDesconectado() {
        LanEstado.setBackground(new Color(204,0,0));
        LanEstado.setToolTipText("Desconectado");
        lblLanEstado.setForeground(Color.white);
    }    
    
    public void setLanConectando() {
        LanEstado.setBackground(Color.orange);
        LanEstado.setToolTipText("Conectando...");        
        lblLanEstado.setForeground(Color.black);
    }    
    
    public void setLanConectado() {
        LanEstado.setBackground(Color.green);
        LanEstado.setToolTipText("Conectado");                
        lblLanEstado.setForeground(Color.black);
    }

    public void setLanLeyendo() {
        LanEstado.setBackground(Color.blue);
        LanEstado.setToolTipText("Leyendo");
        lblLanEstado.setForeground(Color.white);
    }

    public void setAlertasOcurrencia() {
        AlertasEstado.setBackground(Color.blue);
        AlertasEstado.setToolTipText("Ocurrencia");
        lblAlertasEstado.setForeground(Color.white);
    }
    
    public void setAlertasActivadas() {
        AlertasEstado.setBackground(Color.green);
        AlertasEstado.setToolTipText("Hay alertas activas");
        lblAlertasEstado.setForeground(Color.black);
    }

    public void setAlertasDesactivadas() {
        AlertasEstado.setBackground(new Color(204,0,0));
        AlertasEstado.setToolTipText("No hay alertas activas");
        lblAlertasEstado.setForeground(Color.white);
    }    

    public void setHistoricoDesactivado() {
        HistoricoEstado.setBackground(new Color(204,0,0));
        HistoricoEstado.setToolTipText("Desactivado");
        lblHistoricoEstado.setForeground(Color.white);
    }    
    
    public void setHistoricoActivando() {
        HistoricoEstado.setBackground(Color.orange);
        HistoricoEstado.setToolTipText("Activando...");        
        lblHistoricoEstado.setForeground(Color.black);
    }    
    
    public void setHistoricoActivado() {
        HistoricoEstado.setBackground(Color.green);
        HistoricoEstado.setToolTipText("Activado");                
        lblHistoricoEstado.setForeground(Color.black);
    }

    public void setHistoricoGuardando() {
        HistoricoEstado.setBackground(Color.blue);
        HistoricoEstado.setToolTipText("Guardando");
        lblHistoricoEstado.setForeground(Color.white);
    }    
    
    public void mostrarMensaje(String msg){
        if ((msg.contains("gnu."))|| 
            (msg.contains("java."))){
            mostrarMensaje(msg, 1);  
        }
        else { mostrarMensaje(msg, 2); }
    }
    
    public void mostrarMensaje(String msg, int prioridad){        
        //prioridades: 
        //1=ALTA (ej: Mensaje de error) se muestra en font color rojo
        //2=MEDIA (ej: Mensaje de advertencia, progreso de tarea, etc) se muestra en negro
        //3=BAJA (ej: Mensajes de ayuda rapida, sugerencias,etc) se muestra en azul        
        colaMensajes.add(prioridad+msg);
    }    
    
    public void run(){
        Thread esteThread = Thread.currentThread();
        int idMensajeAmostrar=0;
        String mensajeAmostrar;
        int prioridad;
        while (threadBarraEstado == esteThread){    
            try{            
                if (colaMensajes.size()>0){
                   idMensajeAmostrar=getIdMensajeAmostrar(colaMensajes);
                   mensajeAmostrar=colaMensajes.get(idMensajeAmostrar);
                   colaMensajes.remove(idMensajeAmostrar);
                   setLblMensaje(mensajeAmostrar);
                   prioridad=Integer.valueOf(String.valueOf(mensajeAmostrar.charAt(0)));
                   if (prioridad==1){ Thread.sleep(5000); }
                   else { Thread.sleep(4000); }
                }
                else
                    { Thread.sleep(2000); 
                      setLblMensaje(1+"");
                    }               
            }
            catch (Exception e)
               { persistencia.Logueador.getInstance().agregaAlLog(e.toString()); }
        }
    }
            
    public void start(){
        if (threadBarraEstado == null){
            threadBarraEstado = new Thread(this);
            threadBarraEstado.setPriority(Thread.MIN_PRIORITY);
            threadBarraEstado.start();
        }
    }

    /**
     * @return the lblMensaje
     */
    public org.jdesktop.swingx.JXLabel getLblMensaje() {
        return lblMensaje;
    }

    /**
     * @param lblMensaje the lblMensaje to set
     */
    private void setLblMensaje(String lblMensaje) {
        String nroPrioridad=String.valueOf(lblMensaje.charAt(0));
        int prioridad=Integer.valueOf(nroPrioridad);
        String salida=lblMensaje.substring(1);
        this.lblMensaje.setText(salida);
        if (prioridad==1)
            { this.lblMensaje.setForeground(Color.red); }
        else
            if (prioridad == 2)
                { this.lblMensaje.setForeground(Color.black); }
            else
                { this.lblMensaje.setForeground(Color.blue); }        
    }
    
    public int getIdMensajeAmostrar(ArrayList<String> colaMensajes){
      int idMensajeAmostrar=0;      
      return idMensajeAmostrar;
    }

    private void inicializador() {
        getBarraProcesaImg().setVisible(false);
        getBarraProcesaImg().setStringPainted(true);
        getBarraProcesaImg().setBorderPainted(true);

    }

    public void setProgresoProcesaImg(int estado){
      MuestraMsgEnBarraProcesaImg mmebpi;
      String[] msg = {"",""};
      String imgFileName=modelo.dataManager.UltimaImgProcesada.getInstance().getFileName().toLowerCase(); 
      switch(estado){
          case -2:  mmebpi = new MuestraMsgEnBarraProcesaImg();
                    msg[0] = "-2"; msg[1] = "Error al leer DAT: "+imgFileName.replace(".jpg",".dat");
                    mmebpi.setMensaje(msg);
                    mmebpi.start();
                    ControllerNavegacion.getInstance().errorGuiTablaMarcas();                    
                    break;
          case -1:  mmebpi = new MuestraMsgEnBarraProcesaImg();
                    msg[0] = "-1"; msg[1] = "Error al procesar IMG: "+imgFileName;
                    mmebpi.setMensaje(msg);
                    mmebpi.start();
                    ControllerNavegacion.getInstance().errorGuiTablaMarcas();
                    break;
           case 1:  Logueador.getInstance().agregaAlLog("Comienza el procesamiento de "+UltimaImgProcesada.getInstance().getFileName());
                    getBarraProcesaImg().setVisible(true);
                    lblMensaje.setVisible(false);
                    getBarraProcesaImg().setValue(estado*10);
                    ControllerNavegacion.getInstance().loadingGuiImgProcesada();
                    ControllerNavegacion.getInstance().loadingGuiImgSinProcesar();
                    ControllerNavegacion.getInstance().loadingGuiTablaMarcas();
                    break;
           case 2:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Leyendo DAT...");
                    break;
           case 3:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Fin DAT. Leyendo IMG...");
                    break;
           case 4:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Analizando aptitud...");
                    break;
           case 5:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Erosionando...");
                    break;
           case 6:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Binarizando...");
                    break;
           case 7:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Dilatando...");
                    break;
           case 8:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Buscando fondo...");
                    break;
           case 9:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Quitó fondo. Buscando marcas...");
                    break;
           case 10:  getBarraProcesaImg().setValue(estado*10);
                    getBarraProcesaImg().setString("Fin!");
                    break;
          case 11:  mmebpi = new MuestraMsgEnBarraProcesaImg();
                    msg[0] = "11"; msg[1]="Fin de procesamiento: "+imgFileName;
                    mmebpi.setMensaje(msg);
                    mmebpi.start();
                    ControllerNavegacion.getInstance().cargaResultadosEnTablaMarcas();
                    break;
          case 12:  mmebpi = new MuestraMsgEnBarraProcesaImg();
                    ArrayList<Float> porcentajes = ControllerPpal.getInstance().getPorcentajesColorUltimaImg();
                    msg[0] = "12"; msg[1]="Imagen no apta [R:"+porcentajes.get(0)+" A:"+porcentajes.get(1)+" N:"+porcentajes.get(2)+"]: "+imgFileName;
                    Logueador.getInstance().agregaAlLog(msg[1]);
                    ControllerNavegacion.getInstance().errorImgNoApta();
                    mmebpi.setMensaje(msg);
                    mmebpi.start();
                    ControllerNavegacion.getInstance().cargaResultadosEnTablaMarcas();
                    break;
          case 13:  mmebpi = new MuestraMsgEnBarraProcesaImg();
                    msg[0] = "13"; msg[1]="Imagen procesada. No hay marcas...";
                    Logueador.getInstance().agregaAlLog(msg[1]);
                    ControllerNavegacion.getInstance().errorImgNoApta();
                    mmebpi.setMensaje(msg);
                    mmebpi.start();
                    ControllerNavegacion.getInstance().cargaResultadosEnTablaMarcas();
                    break;
          default:  getBarraProcesaImg().setValue(estado*10);
                    break;
      }
    }

    /**
     * @return the barraProcesaImg
     */
    public javax.swing.JProgressBar getBarraProcesaImg() {
        return barraProcesaImg;
    }

}

class MuestraMsgEnBarraProcesaImg implements Runnable{
    Thread thMsgBarraProcesaImg;
    private String[] mensaje;
    public void run() { 
        PanelBarraDeEstado.getInstance().getLblMensaje().setVisible(false);
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setVisible(true);
        Color colorFondoAnterior = PanelBarraDeEstado.getInstance().getBarraProcesaImg().getBackground();
        Color colorLetrasAnterior = PanelBarraDeEstado.getInstance().getBarraProcesaImg().getForeground();
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setValue(100);
        if (mensaje[0].equals("-1") || mensaje[0].equals("-2")){
            PanelBarraDeEstado.getInstance().getBarraProcesaImg().setForeground(Color.red);
        }else{
            PanelBarraDeEstado.getInstance().getBarraProcesaImg().setForeground(new Color(00,204,51));
        }
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setString(mensaje[1]);
        try{  thMsgBarraProcesaImg.sleep(5000);/* 5 segundos de msg */ }
        catch(Exception e){ Logueador.getInstance().agregaAlLog(e.toString()); }
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setBackground(colorFondoAnterior);
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setForeground(colorLetrasAnterior);
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setString(null);
        PanelBarraDeEstado.getInstance().getBarraProcesaImg().setVisible(false);
        PanelBarraDeEstado.getInstance().getLblMensaje().setVisible(true);
        thMsgBarraProcesaImg = null;
    }
    public void start() {
        if (thMsgBarraProcesaImg == null) {
            thMsgBarraProcesaImg = new Thread(this);
            thMsgBarraProcesaImg.setPriority(Thread.MIN_PRIORITY);
            thMsgBarraProcesaImg.start();
        }
    }
    public void setMensaje(String[] mensaje) {
        this.mensaje = mensaje;
    }
}