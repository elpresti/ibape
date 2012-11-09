/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelSelector.java
 *
 * Created on 24/04/2012, 19:15:54
 */
package gui;

import controllers.ControllerLance;

/**
 *
 * @author Sebastian
 */
public class PanelSelector extends javax.swing.JPanel {

    static PanelSelector unicaInstancia;

    /** Creates new form PanelSelector */
    private PanelSelector() {
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

        PanelScrollBotones = new javax.swing.JScrollPane();
        jXTaskPaneContainer1 = new org.jdesktop.swingx.JXTaskPaneContainer();
        panelLogo = new org.jdesktop.swingx.JXPanel();
        lblLogo = new org.jdesktop.swingx.JXLabel();
        taskPaneNavegacion = new org.jdesktop.swingx.JXTaskPane();
        panelNavegacion = new org.jdesktop.swingx.JXPanel();
        lnkNavegacion = new org.jdesktop.swingx.JXHyperlink();
        taskPaneHistorico = new org.jdesktop.swingx.JXTaskPane();
        panelHistorico = new org.jdesktop.swingx.JXPanel();
        lnkHistorico = new org.jdesktop.swingx.JXHyperlink();
        taskPaneOpciones = new org.jdesktop.swingx.JXTaskPane();
        panelOpciones = new org.jdesktop.swingx.JXPanel();
        panelConfiguracion = new org.jdesktop.swingx.JXPanel();
        lnkConfiguracion = new org.jdesktop.swingx.JXHyperlink();
        panelAlertas = new org.jdesktop.swingx.JXPanel();
        lnkAlertas = new org.jdesktop.swingx.JXHyperlink();
        panelCampanias = new org.jdesktop.swingx.JXPanel();
        lnkCampanias = new org.jdesktop.swingx.JXHyperlink();
        panelPois = new org.jdesktop.swingx.JXPanel();
        lnkPois = new org.jdesktop.swingx.JXHyperlink();
        panelInformes = new org.jdesktop.swingx.JXPanel();
        lnkInformes = new org.jdesktop.swingx.JXHyperlink();
        taskPaneAyuda = new org.jdesktop.swingx.JXTaskPane();
        panelAyuda = new org.jdesktop.swingx.JXPanel();
        lnkAyuda = new org.jdesktop.swingx.JXHyperlink();
        panelBotones = new org.jdesktop.swingx.JXPanel();
        panelBtnRegistraPOI = new org.jdesktop.swingx.JXPanel();
        btnRegistraPOI = new javax.swing.JButton();
        panelBtnLanzaRecoge = new org.jdesktop.swingx.JXPanel();
        btnLanzaRecoge = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(170, 1800));
        setMinimumSize(new java.awt.Dimension(100, 500));
        setPreferredSize(new java.awt.Dimension(170, 530));
        setLayout(new java.awt.BorderLayout());

        PanelScrollBotones.setMaximumSize(new java.awt.Dimension(170, 1800));
        PanelScrollBotones.setMinimumSize(new java.awt.Dimension(152, 200));
        PanelScrollBotones.setPreferredSize(new java.awt.Dimension(170, 530));

        jXTaskPaneContainer1.setBackground(new java.awt.Color(240, 240, 240));
        jXTaskPaneContainer1.setMaximumSize(new java.awt.Dimension(150, 1000));
        jXTaskPaneContainer1.setMinimumSize(new java.awt.Dimension(150, 100));
        jXTaskPaneContainer1.setPreferredSize(new java.awt.Dimension(150, 750));
        jXTaskPaneContainer1.setScrollableTracksViewportWidth(false);
        jXTaskPaneContainer1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelLogo.setPreferredSize(new java.awt.Dimension(150, 150));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoIbapeChico.png"))); // NOI18N
        lblLogo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panelLogo.add(lblLogo);

        jXTaskPaneContainer1.add(panelLogo);

        taskPaneNavegacion.setTitle("Navegación");
        taskPaneNavegacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelNavegacion.setPreferredSize(new java.awt.Dimension(140, 30));
        panelNavegacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        lnkNavegacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-navegacion.png"))); // NOI18N
        lnkNavegacion.setText("Navegación");
        lnkNavegacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkNavegacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkNavegacionActionPerformed(evt);
            }
        });
        panelNavegacion.add(lnkNavegacion);

        taskPaneNavegacion.add(panelNavegacion);

        jXTaskPaneContainer1.add(taskPaneNavegacion);

        taskPaneHistorico.setTitle("Historico");
        taskPaneHistorico.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelHistorico.setPreferredSize(new java.awt.Dimension(140, 35));
        panelHistorico.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-historial.png"))); // NOI18N
        lnkHistorico.setText("Historico");
        lnkHistorico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkHistoricoActionPerformed(evt);
            }
        });
        panelHistorico.add(lnkHistorico);

        taskPaneHistorico.add(panelHistorico);

        jXTaskPaneContainer1.add(taskPaneHistorico);

        taskPaneOpciones.setTitle("Opciones");
        taskPaneOpciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelOpciones.setMinimumSize(new java.awt.Dimension(110, 170));
        panelOpciones.setPreferredSize(new java.awt.Dimension(140, 200));
        panelOpciones.setLayout(new java.awt.GridLayout(5, 1));

        panelConfiguracion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-configuracion.png"))); // NOI18N
        lnkConfiguracion.setText("Configuración");
        lnkConfiguracion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkConfiguracionActionPerformed(evt);
            }
        });
        panelConfiguracion.add(lnkConfiguracion);

        panelOpciones.add(panelConfiguracion);

        panelAlertas.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkAlertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-alertas.png"))); // NOI18N
        lnkAlertas.setText("Alertas");
        lnkAlertas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkAlertas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkAlertasActionPerformed(evt);
            }
        });
        panelAlertas.add(lnkAlertas);

        panelOpciones.add(panelAlertas);

        panelCampanias.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkCampanias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-campanias.png"))); // NOI18N
        lnkCampanias.setText("Campañas");
        lnkCampanias.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkCampanias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkCampaniasActionPerformed(evt);
            }
        });
        panelCampanias.add(lnkCampanias);

        panelOpciones.add(panelCampanias);

        panelPois.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkPois.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-pois.png"))); // NOI18N
        lnkPois.setText("Puntos de Interes");
        lnkPois.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkPois.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkPoisActionPerformed(evt);
            }
        });
        panelPois.add(lnkPois);

        panelOpciones.add(panelPois);

        panelInformes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkInformes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-Informes.png"))); // NOI18N
        lnkInformes.setText("Informes");
        lnkInformes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkInformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkInformesActionPerformed(evt);
            }
        });
        panelInformes.add(lnkInformes);

        panelOpciones.add(panelInformes);

        taskPaneOpciones.add(panelOpciones);

        jXTaskPaneContainer1.add(taskPaneOpciones);

        taskPaneAyuda.setTitle("Ayuda");
        taskPaneAyuda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

        panelAyuda.setPreferredSize(new java.awt.Dimension(140, 35));
        panelAyuda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lnkAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/icono-ayuda.png"))); // NOI18N
        lnkAyuda.setText("Ayuda");
        lnkAyuda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lnkAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnkAyudaActionPerformed(evt);
            }
        });
        panelAyuda.add(lnkAyuda);

        taskPaneAyuda.add(panelAyuda);

        jXTaskPaneContainer1.add(taskPaneAyuda);

        panelBotones.setMinimumSize(new java.awt.Dimension(150, 100));
        panelBotones.setPreferredSize(new java.awt.Dimension(150, 100));
        panelBotones.setLayout(new javax.swing.BoxLayout(panelBotones, javax.swing.BoxLayout.PAGE_AXIS));

        panelBtnRegistraPOI.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 20));

        btnRegistraPOI.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegistraPOI.setText("Registra POI");
        btnRegistraPOI.setMaximumSize(new java.awt.Dimension(110, 23));
        btnRegistraPOI.setPreferredSize(new java.awt.Dimension(110, 23));
        btnRegistraPOI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistraPOIActionPerformed(evt);
            }
        });
        panelBtnRegistraPOI.add(btnRegistraPOI);

        panelBotones.add(panelBtnRegistraPOI);

        panelBtnLanzaRecoge.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));

        btnLanzaRecoge.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnLanzaRecoge.setText("Lance");
        btnLanzaRecoge.setMaximumSize(new java.awt.Dimension(110, 23));
        btnLanzaRecoge.setPreferredSize(new java.awt.Dimension(110, 23));
        btnLanzaRecoge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLanzaRecogeActionPerformed(evt);
            }
        });
        panelBtnLanzaRecoge.add(btnLanzaRecoge);

        panelBotones.add(panelBtnLanzaRecoge);

        jXTaskPaneContainer1.add(panelBotones);

        PanelScrollBotones.setViewportView(jXTaskPaneContainer1);

        add(PanelScrollBotones, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

private void lnkNavegacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkNavegacionActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelNavegacion.getInstance());
    controllers.ControllerNavegacion.getInstance().actualizaDatosEnGui();
}//GEN-LAST:event_lnkNavegacionActionPerformed

private void lnkHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkHistoricoActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelHistorico.getInstance());
    controllers.ControllerHistorico.getInstance().actualizaDatosEnGui();
}//GEN-LAST:event_lnkHistoricoActionPerformed

private void lnkConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkConfiguracionActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcConfiguracion.getInstance());
}//GEN-LAST:event_lnkConfiguracionActionPerformed

private void lnkAlertasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkAlertasActionPerformed
    PanelOpcAlertas.getInstance().actualizaLabelCantOcurNoVistas();
    PanelOpcAlertas.getInstance().vaciaTablaOcur();
    PanelOpcAlertas.getInstance().cargaGrillaOcurAlertas();
    if (PanelOpcAlertas.getInstance().isModificandoAlerta()){
            VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcAlertasAgregaEdita.getInstance());
            PanelOpcAlertasAgregaEdita.getInstance().setVisible(true);
            PanelOpcAlertas.getInstance().setVisible(false);
    }else{
            VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcAlertas.getInstance());    
            PanelOpcAlertasAgregaEdita.getInstance().setVisible(false);
            PanelOpcAlertas.getInstance().setVisible(true);
    }

}//GEN-LAST:event_lnkAlertasActionPerformed

private void lnkCampaniasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkCampaniasActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcCampanias.getInstance());
}//GEN-LAST:event_lnkCampaniasActionPerformed

private void lnkPoisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkPoisActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcPOIs.getInstance());
    PanelOpcPOIs.getInstance().cargaGrillaPOIS();
    PanelOpcPOIs.getInstance().cargaGrillaCategoriaPOIS();
}//GEN-LAST:event_lnkPoisActionPerformed

private void lnkInformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkInformesActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcInformes.getInstance());
    controllers.ControllerInforme.getInstance().actualizaDatosEnGui();
}//GEN-LAST:event_lnkInformesActionPerformed

private void lnkAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnkAyudaActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelAyuda.getInstance());
}//GEN-LAST:event_lnkAyudaActionPerformed

private void btnLanzaRecogeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLanzaRecogeActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcLances.getInstance());
    ControllerLance.getInstance().registrarLance();  
}//GEN-LAST:event_btnLanzaRecogeActionPerformed

private void btnRegistraPOIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistraPOIActionPerformed
    VentanaIbape.getInstance().ponerEnPanelDerecho(PanelOpcPOIs.getInstance());
    PanelOpcPOIs.getInstance().botonRegPOI();
}//GEN-LAST:event_btnRegistraPOIActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane PanelScrollBotones;
    private javax.swing.JButton btnLanzaRecoge;
    private javax.swing.JButton btnRegistraPOI;
    private org.jdesktop.swingx.JXTaskPaneContainer jXTaskPaneContainer1;
    private org.jdesktop.swingx.JXLabel lblLogo;
    private org.jdesktop.swingx.JXHyperlink lnkAlertas;
    private org.jdesktop.swingx.JXHyperlink lnkAyuda;
    private org.jdesktop.swingx.JXHyperlink lnkCampanias;
    private org.jdesktop.swingx.JXHyperlink lnkConfiguracion;
    private org.jdesktop.swingx.JXHyperlink lnkHistorico;
    private org.jdesktop.swingx.JXHyperlink lnkInformes;
    private org.jdesktop.swingx.JXHyperlink lnkNavegacion;
    private org.jdesktop.swingx.JXHyperlink lnkPois;
    private org.jdesktop.swingx.JXPanel panelAlertas;
    private org.jdesktop.swingx.JXPanel panelAyuda;
    private org.jdesktop.swingx.JXPanel panelBotones;
    private org.jdesktop.swingx.JXPanel panelBtnLanzaRecoge;
    private org.jdesktop.swingx.JXPanel panelBtnRegistraPOI;
    private org.jdesktop.swingx.JXPanel panelCampanias;
    private org.jdesktop.swingx.JXPanel panelConfiguracion;
    private org.jdesktop.swingx.JXPanel panelHistorico;
    private org.jdesktop.swingx.JXPanel panelInformes;
    private org.jdesktop.swingx.JXPanel panelLogo;
    private org.jdesktop.swingx.JXPanel panelNavegacion;
    private org.jdesktop.swingx.JXPanel panelOpciones;
    private org.jdesktop.swingx.JXPanel panelPois;
    private org.jdesktop.swingx.JXTaskPane taskPaneAyuda;
    private org.jdesktop.swingx.JXTaskPane taskPaneHistorico;
    private org.jdesktop.swingx.JXTaskPane taskPaneNavegacion;
    private org.jdesktop.swingx.JXTaskPane taskPaneOpciones;
    // End of variables declaration//GEN-END:variables

    public static PanelSelector getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new PanelSelector();
        }
        return unicaInstancia;
    }

    /* codigo de prueba para poder probar un panel simplemente haciendo "Run File" sobre su clase
    public static void main(String[] args) {
    javax.swing.JFrame elFrame = new javax.swing.JFrame();
    elFrame.setSize(500, 510);
    elFrame.add(new PanelSelector()); 
    elFrame.setVisible(true);
    }
     */
    private void inicializador() {
        setGuiEnNavegacion(false);
        cargaIconosAbotones();
    }

    public void setGuiEnNavegacion(boolean b) {
        mostrarBtnPanelNavegacion(b);
        mostrarBtnRegistraPOI(b);
    }

    public void mostrarBtnPanelNavegacion(boolean b) {
        taskPaneNavegacion.setVisible(b);
    }

    public void mostrarBtnRegistraPOI(boolean estado) {
        btnRegistraPOI.setVisible(estado);
    }

    public void mostrarBtnLanzaRecoge(boolean estado) {
        btnLanzaRecoge.setVisible(estado);
    }

    private void cargaIconosAbotones() {
        //lnkNavegacion.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-navegacion.png"));
        //lnkAlertas.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-alertas.png"));
        //lnkAyuda.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-ayuda.png"));
        //lnkCampanias.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-campanias.png"));
        //lnkConfiguracion.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-configuracion.png"));
        //lnkHistorico.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-historial.png"));
        //lnkInformes.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-informes.png"));
        //lnkPois.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\icono-pois.png"));
    }

    public void setTxtBtnIniciaLance() {
        btnLanzaRecoge.setText("Iniciar lance");
    }

    public void setTxtBtnFinLance() {
        btnLanzaRecoge.setText("Finalizar lance");
    }
}