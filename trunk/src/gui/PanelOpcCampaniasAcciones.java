package gui;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author Sebastian
 */
public class PanelOpcCampaniasAcciones extends JXPanel implements TableCellEditor,TableCellRenderer { 
    //JComponent panelAcciones = new JXPanel();
    /** Creates new form PanelOpcCampaniasAcciones */
    public PanelOpcCampaniasAcciones() {

        this.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 1));
        
        // create and add all elments you need to your panel
        JXHyperlink btnEditar = new JXHyperlink();
        btnEditar.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\tabla-icono-editar.png")); // NOI18N
        btnEditar.setText("");
        this.add(btnEditar);

        JXHyperlink btnEliminar = new JXHyperlink();
        btnEliminar.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\tabla-icono-eliminar.gif")); // NOI18N
        btnEliminar.setText("");
        this.add(btnEliminar);

        JXHyperlink btnGuardar = new JXHyperlink();
        btnGuardar.setIcon(new javax.swing.ImageIcon("imgs\\iconos\\tabla-icono-guardar.png")); // NOI18N
        btnGuardar.setText("");
        this.add(btnGuardar);

        //btnEliminar.addActionListener( /* the listener which will handle the events */);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });       
        //btnGuardar.addActionListener( /* the listener which will handle the events */);
        //btnEditar.addActionListener( /* the listener which will handle the events */);
        
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        //btn.setText( /* according to row or whatever*/);
        // set all elemnts of you panel to the according values
        // or add dynamically an action listener
        return this;
    }

//    public Object getCellEditorValue() {
//        return new Void();
//    }
    
    public Component getTableCellEditorComponente(){
        return this;
    }

    public Object getCellEditorValue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
  public boolean isCellEditable(EventObject anEvent) {
         // La celda es editable ante cualquier evento.
         return true;
     }
  

    public boolean shouldSelectCell(EventObject anEvent) {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("Metodo shouldSelectCell(). EventObject: "+anEvent.toString());
        return true;
    }

    public boolean stopCellEditing() {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("Metodo stopCellEditing()");
        return true;        
    }

    public void cancelCellEditing() {
        System.out.println("Metodo cancelCellEditing()");
    }

    public void addCellEditorListener(CellEditorListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("Metodo addCellEditorListener()");
    }

    public void removeCellEditorListener(CellEditorListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("Metodo removeCellEditorListener()");
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this;
    }
    
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                              
        JOptionPane.showMessageDialog(null, "se pulso el boton eliminar");
    }  
    
}    
