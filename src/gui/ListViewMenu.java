package gui;

import graphs.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TableView.TableRow;
import model.RDFModel;

/**
 *
 * @author zabetak
 */
 public class ListViewMenu extends JPanel implements ActionListener{
            private JTable classTable;
            private JTable propTable;
            
            private JPanel subClassPanel;
            private JLabel subClassLabel;
            private JTextField subClassTextField;
            
            private JPanel subPropPanel;
            private JLabel subPropLabel;
            private JComboBox subPropBox;
            
            private Graph graph;
            
            private String currentSubClass = "-";
            
            public ListViewMenu(Graph g){
                super(new GridLayout(0,2));
                graph = g;
                classTable = new JTable(new ClassTableModel());
                propTable = new JTable(new PropertieTableModel());
                propTable.setAutoCreateRowSorter(true);
                propTable.getSelectionModel().addListSelectionListener(new RowListener2());
                classTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
                classTable.setAutoCreateRowSorter(true);
                classTable.getSelectionModel().addListSelectionListener(new RowListener());

                Iterator it = graph.getEdgeList().values().iterator();
                int i = 0;
                
                while(it.hasNext()){
                    Edge e = (Edge)it.next();
                    JComboBox cB = new JComboBox(e.getSubProperties().toArray());
                     
                    //rowEditor.setEditorAt(i, new DefaultCellEditor(cB));
                    i++;
                }
                
                
                add(new JScrollPane(classTable));
                add(new JScrollPane(propTable));

                subClassPanel = new JPanel();
                
                
                
                subClassLabel = new JLabel("SubClass Of:");
                
                subClassTextField = new JTextField(currentSubClass);
                subClassTextField.setEditable(false);
                subClassTextField.setColumns(20);
                
                subClassLabel.setLabelFor(subClassTextField);
                
                subClassPanel.add(subClassLabel);
                subClassPanel.add(subClassTextField);
                add(subClassPanel);
                
                subPropPanel = new JPanel();
                subPropLabel = new JLabel("SubProperties:");
                subPropBox = new JComboBox();
                subPropPanel.add(subPropLabel);
                subPropPanel.add(subPropBox);
                
                add(subPropPanel);
                
            }
            
        
            private void updateSubClassInfo(){
                Node selClass = (Node) classTable.getValueAt(classTable.getSelectedRow(),classTable.getSelectedColumn());
                Iterator it = selClass.getEdgesFrom().iterator();
                while(it.hasNext()){
                    Edge e =(Edge) it.next();
                    if(e.toString().equals("isA")){
                        subClassTextField.setText(e.getTargetNode().getName());
                        return;
                    }
                }
                subClassTextField.setText("-");
            }
            
            private void updateSubPropInfo(){
                Edge selEdge = (Edge) propTable.getValueAt(propTable.getSelectedRow(),0);
                Iterator it = selEdge.getSubProperties().iterator();
                subPropBox.removeAllItems();
                while(it.hasNext()){
                    subPropBox.addItem(it.next());
                }
            }
            
            private class RowListener implements ListSelectionListener {

                public void valueChanged(ListSelectionEvent event) {
                    
                    if (event.getValueIsAdjusting()) {
                        return;
                    }
                    updateSubClassInfo();
                }
                
            }
            
            private class RowListener2 implements ListSelectionListener {

                public void valueChanged(ListSelectionEvent event) {
                    
                    if (event.getValueIsAdjusting()) {
                        return;
                    }
                    
                    updateSubPropInfo();
                }
                
            }
            
            public void actionPerformed(ActionEvent arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
            
            }
            class PropertieTableModel extends AbstractTableModel {
                private String[] columnNames = {"Properties","Domain","Range"};
                private Object[][] data;

                public PropertieTableModel(){
                    super();
                    
                    
                    ArrayList<Edge> tmpdata = new ArrayList<Edge>(graph.getEdgeList().values());
                    
                    Iterator it = tmpdata.iterator();
                    int i = 0;
                    int isAnumber = 0;
                    while(it.hasNext()){
                        Edge e = (Edge)it.next();
                        if(e.toString().equals("isA")){
                            isAnumber++;
                        }
                        
                    }
                    
                    data = new Object[tmpdata.size()-isAnumber][3];
                    
                    it = tmpdata.iterator();
                    while(it.hasNext()){
                        Edge e = (Edge)it.next();
                        if(e.toString().equals("isA")){
                            continue;
                        }
                        data[i][0] = e;
                        data[i][1] = e.getSourceNode();
                        data[i][2] = e.getTargetNode();
                        
                        i++;
                    }
                }
                
                
                
                
                public int getColumnCount() {
                    return columnNames.length;
                }

                public int getRowCount() {
                    return data.length;
                }

                public String getColumnName(int col) {
                    return columnNames[col];
                }

                public Object getValueAt(int row, int col) {
                    return data[row][col];
                }

           /*
            * JTable uses this method to determine the default renderer/
            * editor for each cell.  If we didn't implement this method,
            * then the last column would contain text ("true"/"false"),
            * rather than a check box.
            */
               public Class getColumnClass(int c) {
                    return getValueAt(0, c).getClass();
               }

               

        }
            
            class ClassTableModel extends AbstractTableModel {
                private String[] columnNames = {"Classes"};
                private Object[][] data;

                public ClassTableModel(){
                    super();
                    Hashtable<String,Node> tmpdata = graph.getNodeList();
                    
                    Iterator it = tmpdata.values().iterator();
                    int i = 0;
                    int visibleNodes = 0;
                    while(it.hasNext()){
                        Node n = (Node) it.next();
                        if(n.isVisible()){
                            visibleNodes++;
                        }
                    }
                    
                    data = new Object[visibleNodes][1];
                    it = tmpdata.values().iterator();
                    while(it.hasNext()){
                        Node n = (Node) it.next();
                        if(n.isVisible()){
                            data[i][0] = n;
                            i++;
                        }
                    }
                }
                
                public int getColumnCount() {
                    return columnNames.length;
                }

                public int getRowCount() {
                    return data.length;
                }

                public String getColumnName(int col) {
                    return columnNames[col];
                }

                public Object getValueAt(int row, int col) {
                    return data[row][col];
                }

           /*
            * JTable uses this method to determine the default renderer/
            * editor for each cell.  If we didn't implement this method,
            * then the last column would contain text ("true"/"false"),
            * rather than a check box.
            */
               public Class getColumnClass(int c) {
                    return getValueAt(0, c).getClass();
               }

               

        }
      
        class subPropertieRenderer implements TableCellRenderer{

            JComboBox contents[];
            public subPropertieRenderer(JComboBox values[]){
                this.contents = values;
            }
            
            public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    return contents[row];
                
            }
            
        }


    } 
