/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import modelo.dataManager.AdministraCampanias;
import modelo.dataManager.CategoriaPoi;
import persistencia.BrokerCategoriasPOI;
import persistencia.BrokerHistoricoPunto;
import persistencia.BrokerHistoricoSondaSet;

/**
 *
 * @author Sebastian
 */
public class ControllerHistorico {
    static ControllerHistorico unicaInstancia;
    private BrokerHistoricoPunto brokerHistoricoPunto;
    private BrokerHistoricoSondaSet brokerHistoricoSondaSet;
            
    private ControllerHistorico(){
        inicializador();
    }
    
    public static ControllerHistorico getInstance() {
       if (unicaInstancia == null)
          unicaInstancia = new ControllerHistorico();
       return unicaInstancia;
    }

    private void inicializador() {
        brokerHistoricoPunto = BrokerHistoricoPunto.getInstance();
        brokerHistoricoSondaSet = BrokerHistoricoSondaSet.getInstance();        
    }

    public void configuraBrokerHistorico() {
        brokerHistoricoPunto.setGuardaDatosGps(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosSonda(gui.PanelOpcCampanias.getInstance().getChkHistoricoGpsSonda().isSelected());
        brokerHistoricoPunto.setGuardaDatosPeces(gui.PanelOpcCampanias.getInstance().getChkHistoricoPeces().isSelected());
        brokerHistoricoSondaSet.setGuardaDatosSondaSets(gui.PanelOpcCampanias.getInstance().getChkHistoricoSondaSets().isSelected());
    }

    public void cargaGrillaCampanias() {
        gui.PanelHistorico.getInstance().vaciaTabla();
        int cantConHistorico=0;
        ArrayList<modelo.dataManager.Campania> campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();
        if ((campanias == null) || (campanias.isEmpty()) ) {
            modelo.dataManager.AdministraCampanias.getInstance().leerCampaniasDeLaDB();
            campanias = modelo.dataManager.AdministraCampanias.getInstance().getCampanias();            
        }
        if ((!(campanias == null)) && (campanias.size() > 0)) {
            // while (), pongo cada objeto Campania en la grilla de campanias                    
            int i = 0;
            boolean tieneHistorico;            
            while (i < campanias.size()) {
                tieneHistorico = (campanias.get(i).getFolderHistorico() != null && campanias.get(i).getFolderHistorico().length() > 0);
                if (tieneHistorico){
                    gui.PanelHistorico.getInstance().agregaUnaFilaCampania(
                            campanias.get(i).getId(),
                            campanias.get(i).getDescripcion(),
                            campanias.get(i).getBarco(),
                            campanias.get(i).getCapitan(),
                            campanias.get(i).getEstado(),
                            campanias.get(i).getFechaInicio(),
                            campanias.get(i).getFechaFin(),
                            campanias.get(i).getCantTotalCajones()
                            );
                    cantConHistorico++;
                }
                i++;
            }
        }
        if (cantConHistorico == 0) {
            gui.PanelHistorico.getInstance().agregaUnaFilaCampania(-1, "No hay campañas con Historico",
                    null, null, 0, null, null, 0);
            gui.PanelHistorico.getInstance().habilitaPanelTablaCampanias(false);
        } else {
            gui.PanelHistorico.getInstance().habilitaPanelTablaCampanias(true);
        }
        if (AdministraCampanias.getInstance().getCampaniaEnCurso() != null) {
            gui.PanelHistorico.getInstance().marcaCampaniaEnCurso();
        }

    }
       
    public TableModel cargaGrillaCategoriaPOIS() {
        TableModelCatPoisHistorico dm = new TableModelCatPoisHistorico();
        //Cabecera
        String[] encabezado = new String[4];
        encabezado[0] = "Id";
        encabezado[1] = "Elejir";        
        encabezado[2] = "Icono";
        encabezado[3] = "Nombre de la categoria";
        dm.setColumnIdentifiers(encabezado);
        //Cuerpo
        for (CategoriaPoi cP : BrokerCategoriasPOI.getInstance().getCatPOISFromDB()) {
            Object[] fila = new Object[4]; //creamos la fila
            fila[0]=cP.getId(); //en la columna 0 va el ID
            fila[1]=new JCheckBox(); //en la columna 1 va el CheckBox
            fila[2]=cP.getPathIcono();//en la columna 2 va el Icono
            fila[3]=cP.getTitulo();//en la columna 3 va el Nombre de la categoria de POI
            dm.addRow(fila);
        }
        return dm;
    }
    
}

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