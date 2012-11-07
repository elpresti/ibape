/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.PanelBarraDeEstado;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.dataCapture.Sistema;
import modelo.dataManager.AdministraCampanias;
import persistencia.BrokerConfig;
import persistencia.BrokerDbMapa;
import persistencia.Logueador;

/**
 *
 * @author Sebastian
 */
public class ControllerPpal {

    static ControllerPpal unicaInstancia;
    private ControllerConfig contConfig = ControllerConfig.getInstance();

    public void accionesAlSalir() {
        if (BrokerDbMapa.getInstance().isUsarMapaNavegacion() || modelo.gisModule.WebServer.getInstance().isWebServerEncendido()) {
            modelo.gisModule.WebServer.getInstance().cerrarWebServer();
            modelo.gisModule.Browser.getInstance().cerrarBrowserPortable();
        }
        controllers.ControllerPpal.getInstance().guardarConfigPanelConfig();
        if ((AdministraCampanias.getInstance().getCampaniaEnCurso() != null)
                && (AdministraCampanias.getInstance().getCampaniaEnCurso().getEstado() == 1)) {
            controllers.ControllerCampania.getInstance().setEstadoCampaniaEnCurso(2); //si hay alguna campania en curso, la pauso
        }
        ControllerLance.getInstance().cierraAppMientrasHayLance(); //que hacer si hay un lance en curso
        //ControllerAlertas contAlertas=controllers.ControllerAlertas.getInstance();
        //contAlertas.guardaOcurAlertas();
    }

    public void accionesAlIniciar() {
        controllers.ControllerConfig.getInstance().inicializaConexiones();
        persistencia.BrokerCampania.getInstance();
        controllers.ControllerHistorico.getInstance();
        controllers.ControllerCampania.getInstance();
        controllers.ControllerAlertas.getInstance().accionesAlIniciar();
        modelo.alertas.AdministraAlertas.getInstance();
        if (AdministraCampanias.getInstance().cargaUltimaCampaniaPausada()) {
            gui.PanelOpcCampanias.getInstance().setGuiCampaniaIniciada();
            gui.PanelOpcCampanias.getInstance().setGuiCampaniaPausada();
            gui.PanelOpcCampanias.getInstance().cargarDatosDeCampaniaEnCurso();
            gui.PanelOpcCampanias.getInstance().marcaCampaniaEnCurso();
        }
        modelo.dataCapture.Sistema.getInstance().compruebaDirectoriosNecesarios();
    }

    public static ControllerPpal getInstance() {
        if (unicaInstancia == null) {
            unicaInstancia = new ControllerPpal();
        }
        return unicaInstancia;
    }

    public boolean guardarConfigPanelConfig() {
        boolean sePudo = true;
        gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();
        gui.PanelOpcCampanias c = gui.PanelOpcCampanias.getInstance();
        String comboPuertoGPS = "";
        if (p.getComboPuertoGps().getSelectedItem() != null) {
            comboPuertoGPS = p.getComboPuertoGps().getSelectedItem().toString();
        }
        String comboPuertoSONDA = "";
        if (p.getComboPuertoSonda().getSelectedItem() != null) {
            comboPuertoSONDA = p.getComboPuertoSonda().getSelectedItem().toString();
        }
        sePudo = sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Gps(
                String.valueOf(p.getChkEstadoGps().isSelected()),
                comboPuertoGPS,
                p.getComboVelocidadGps().getSelectedItem().toString(),
                p.getComboBitsDatosGps().getSelectedItem().toString(),
                p.getComboParidadGps().getSelectedItem().toString(),
                String.valueOf(p.getChkAutoConectaGps().isSelected()));
        sePudo = sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Sonda(
                String.valueOf(p.getChkEstadoSonda().isSelected()),
                comboPuertoSONDA,
                p.getComboVelocidadSonda().getSelectedItem().toString(),
                p.getComboBitsDatosSonda().getSelectedItem().toString(),
                p.getComboParidadSonda().getSelectedItem().toString(),
                String.valueOf(p.getChkAutoConectaSonda().isSelected()));
        sePudo = sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Lan(
                String.valueOf(p.getChkEstadoLan().isSelected()),
                p.getCampoRutaHistorico().getText(),
                String.valueOf(p.getChkAutoConectaLan().isSelected()));
        sePudo = sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Unidades(
                p.getComboDistancia().getSelectedItem().toString(),
                p.getComboVelocidad().getSelectedItem().toString(),
                p.getComboTemp().getSelectedItem().toString());
        sePudo = sePudo && BrokerConfig.getInstance().actualizaDatosPanelConfig_Historico(
                String.valueOf(c.getChkHistoricoGpsSonda().isSelected()),
                String.valueOf(c.getChkHistoricoPeces().isSelected()),
                String.valueOf(c.getChkHistoricoSondaSets().isSelected()));
        sePudo = sePudo && BrokerConfig.getInstance().guardaConfiguracion();
        return sePudo;
    }

    public boolean conectaYleeDelGps() {
        boolean sePudo = false;
        try {
            gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();
            modelo.dataCapture.Gps gps = modelo.dataCapture.Gps.getInstance();
            //seteamos el puerto
            String puertoGps = String.valueOf(p.getComboPuertoGps().getSelectedItem());
            String nroCom;
            if (!(puertoGps.indexOf(":") == -1)) {
                nroCom = puertoGps.substring(0, puertoGps.indexOf(":"));
            } else {
                nroCom = puertoGps;
            }
            nroCom = nroCom.replace("COM", "").trim();
            gps.setNroCom(Integer.valueOf(nroCom));

            //seteamos la velocidad del puerto
            Integer velocidadGps = Integer.valueOf(String.valueOf(p.getComboVelocidadGps().getSelectedItem()));
            gps.setVelocidadCom(velocidadGps);
            //seteamos los bits de datos
            Integer bitsDatosGps = Integer.valueOf(String.valueOf(p.getComboBitsDatosGps().getSelectedItem()));
            gps.setBitsDeDatosCom(bitsDatosGps);
            //seteamos los bits de paridad
            if (String.valueOf(p.getComboParidadGps().getSelectedItem()).equals("Impar")) {
                gps.setBitsParidadCom(1);
            } else if (String.valueOf(p.getComboParidadGps().getSelectedItem()).equals("Par")) {
                gps.setBitsParidadCom(2);
            } else {
                gps.setBitsParidadCom(0);
            }
            //disparamos la lectura del puerto
            gps.disparaLectura();
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean detenerLecturaGps() {
        boolean sePudo = false;
        try {
            modelo.dataCapture.Gps.getInstance().detieneLectura();
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean conectaYleeDeLaSonda() {
        boolean sePudo = false;
        try {
            gui.PanelOpcConfiguracion p = gui.PanelOpcConfiguracion.getInstance();
            modelo.dataCapture.Sonda sonda = modelo.dataCapture.Sonda.getInstance();
            //seteamos el puerto
            String puertoSonda = String.valueOf(p.getComboPuertoSonda().getSelectedItem());
            String nroCom;
            if (!(puertoSonda.indexOf(":") == -1)) {
                nroCom = puertoSonda.substring(0, puertoSonda.indexOf(":"));
            } else {
                nroCom = puertoSonda;
            }
            nroCom = nroCom.replace("COM", "").trim();
            sonda.setNroCom(Integer.valueOf(nroCom));
            //seteamos la velocidad del puerto
            Integer velocidadSonda = Integer.valueOf(String.valueOf(p.getComboVelocidadSonda().getSelectedItem()));
            sonda.setVelocidadCom(velocidadSonda);
            //seteamos los bits de datos
            Integer bitsDatosSonda = Integer.valueOf(String.valueOf(p.getComboBitsDatosSonda().getSelectedItem()));
            sonda.setBitsDeDatosCom(bitsDatosSonda);
            //seteamos los bits de paridad
            if (String.valueOf(p.getComboParidadSonda().getSelectedItem()).equals("Impar")) {
                sonda.setBitsParidadCom(1);
            } else if (String.valueOf(p.getComboParidadSonda().getSelectedItem()).equals("Par")) {
                sonda.setBitsParidadCom(2);
            } else {
                sonda.setBitsParidadCom(0);
            }
            //disparamos la lectura del puerto
            sonda.disparaLectura();
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean detenerLecturaSonda() {
        boolean sePudo = false;
        try {
            modelo.dataCapture.Sonda.getInstance().detieneLectura();
            sePudo = true;
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public boolean abrirWebServer() {
        boolean sePudo = false;
        try {
            if (!modelo.gisModule.WebServer.getInstance().isWebServerEncendido()) {
                modelo.gisModule.WebServer.getInstance().start();
                sePudo = true;
            } else {
                sePudo = true;
            }
        } catch (Exception e) {
            Logueador.getInstance().agregaAlLog(e.toString());
        }
        return sePudo;
    }

    public void msgReiniciarAplicacion() {
        JOptionPane.showMessageDialog(null, "Fue necesario inicializar IBAPE, ahora debe reiniciar la aplicación");
    }

    public void msgNoSePudoCopiarArchivosNecesarios() {
        String archOS;
        if (Sistema.getInstance().is64bOS()) {
            archOS = "run64b.bat";
        } else {
            archOS = "run32b.bat";
        }
        JOptionPane.showMessageDialog(null, "No se pudieron copiar los archivos necesarios para recibir datos por puerto "
                + "serie\n Pruebe de iniciar IBAPE ejecutando " + archOS, "Problemas para capturar datos", JOptionPane.ERROR_MESSAGE);
    }

    public void vaciarJTable(DefaultTableModel dTM) {
        dTM.setColumnCount(0);
        while (dTM.getRowCount() > 0) {
            dTM.removeRow(0);
        }
        //dTM.setRowCount(0);
    }

    public void ocultarColJTable(JTable tabla, int indice) {
        tabla.getColumnModel().getColumn(indice).setMaxWidth(0);
        tabla.getColumnModel().getColumn(indice).setMinWidth(0);
        tabla.getColumnModel().getColumn(indice).setPreferredWidth(0);
        tabla.getColumnModel().getColumn(indice).setResizable(false);
    }

    public Object[] creaUnaFilaGenerica(int cantCols, ArrayList unObjeto) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] fila = new Object[cantCols]; //creamos la fila
        for (int i = 0; i < unObjeto.size(); i++) {
            //System.out.println(unObjeto.get(i).getClass().toString());
            if (unObjeto.get(i) != null) {
                if (unObjeto.get(i).toString().equalsIgnoreCase("false")) {
                    fila[i] = false;
                } else {
                    if (unObjeto.get(i).toString().equalsIgnoreCase("true")) {
                        fila[i] = true;
                    } else {
                        if (unObjeto.get(i).getClass().toString().equals("class javax.swing.ImageIcon")) {
                            fila[i] = new ImageIcon(unObjeto.get(i).toString());
/*
                            //--- resize Icon
                            Image source = new ImageIcon(unObjeto.get(i).toString()).getImage();
                            BufferedImage image = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2d = (Graphics2D) image.getGraphics();
                            g2d.drawImage(source, 0, 0, null);
                            g2d.dispose();
                            //-------------
                            fila[i] = modelo.dataCapture.Sistema.getInstance().getLabelWithImgResized(25, 32, image);//en la columna 2 va el Icono
*/
                        } else {
                            fila[i] = unObjeto.get(i);
                        }
                    }
                }
            } else {
                fila[i] = "";
            }
        }
        return fila;
    }

    public void actualizaBarraProgresoProcesaImg(int progresoProcesamiento) {
        PanelBarraDeEstado.getInstance().setProgresoProcesaImg(progresoProcesamiento);
    }

    public ArrayList<Float> getPorcentajesColorUltimaImg() {
        return modelo.dataManager.UltimaImgProcesada.getInstance().getPorcentajesColores();
    }
    
    public void abreAyudaOnline(){
        String urlAyudaOnline = "https://docs.google.com/document/d/1hVcYc_wnO9TtpbMH3SzWf_XhUkNxsVi9q665GpzSc0w/edit";
        modelo.gisModule.Browser.getInstance().setUrlTemp(urlAyudaOnline);
        modelo.gisModule.Browser.getInstance().start();
    }
    
}