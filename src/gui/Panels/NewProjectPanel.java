/*
 * NewProjectPanel.java
 *
 * Created on June 24, 2009, 4:32 PM
 */

package gui.Panels;

import gui.MenuActions.*;
import gr.forth.ics.rdfsuite.services.DbSettings;
import gr.forth.ics.rdfsuite.services.Format;
import gr.forth.ics.rdfsuite.services.util.ModelUtils;
import gr.forth.ics.swkmclient.Client;
import gr.forth.ics.swkmclient.ClientFactory;
import gui.GraphInternalFrame;
import gui.MainFrame;
import gui.Project;
import gui.ProjectManager;
import gui.ProjectTree;
import gui.RDFFileFilter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 *
 * @author  zabetak
 */
public class NewProjectPanel extends javax.swing.JPanel {
    private Project nProj = null;
    private Set<String> files = null;
    
//    /*
//     * Settings for connection with the database
//     */
//    private String dbHost = "http://139.91.183.8";
//    private int dbPort = 3027;
//    private String dbWebContext = "SwkmMiddlewareWS";
//    private String dbName = "zabetak_db";
//    private String dbUserName = "swkm";
//    private String dbPasswd = "";
    /*
     * Settings for connection with the database
     */
    private String dbHost = "http://139.91.183.30";
    private int dbPort = 3025;
    private String dbWebContext = "SwkmMiddlewareWS";
    private String dbName = "zabetak_db";
    private String dbUserName = "swtech";
    private String dbPasswd = "";
    /*
     * The client that uses the previous db settings
     */
    private Client client = null;
    
    /** Creates new form NewProjectPanel */
    public NewProjectPanel() {
        files = new HashSet<String>();
        
        initComponents();
    }

    public boolean ok(){
        //Retrieve user's layout options
        if(nProj == null){
            JOptionPane.showMessageDialog(null, "No Project Created", "Error In Creating Project", JOptionPane.ERROR_MESSAGE);
            return false;
        }
            String params = "";
//            String layout = group.getSelection().getActionCommand();
            Project.DEPENDENCIES depType = null;
            if(b1.isSelected()){
                depType = Project.DEPENDENCIES.NONE;
            }else if(b2.isSelected()){
                depType = Project.DEPENDENCIES.DIRECT;
            }else if(b3.isSelected()){
                depType = Project.DEPENDENCIES.TRANSITIVE;
            }

            nProj.setName(projectNameField.getText());
            //Add new project to already opened ones, open a new frame and add it to desktop workspace 
            if (ProjectManager.getSingleton().addProject(nProj, false)) {
                //Sets the client for the active project.If the project does not need a client we don't care,null is set
                ProjectManager.getSingleton().getActiveProject().addVisualizationFrame("frame" + ProjectManager.getSingleton().getActiveProject().getFrameCounter());
                String selectedNs[] = selectedNamespaces();
                if (selectedNs != null) {
                    ((GraphInternalFrame) ProjectManager.getSingleton().getActiveProject().getActiveFrame()).getGraph().setGraphNameSpaces(selectedNs);
                    ((GraphInternalFrame) ProjectManager.getSingleton().getActiveProject().getActiveFrame()).getGraph().setDependencyLoad(depType);
                    ProjectManager.getSingleton().getActiveProject().addGraphInFrame("random", params);
                    MainFrame.getSingleton().getDesktop().add(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                    ProjectTree.getSingleton().highlighActiveFrame(ProjectManager.getSingleton().getActiveProject(), ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                    //Ask the user if he/she wants to apply the forceDirectedAlgorithm and do the appropriate actions
                    ForceDirectedQuestionAction forceDirectedQuestionMenu = new ForceDirectedQuestionAction();
                    forceDirectedQuestionMenu.execute();
                } else {
                    System.out.println("TEST INSIDE");
                    ProjectManager.getSingleton().getActiveProject().removeFrame(ProjectManager.getSingleton().getActiveProject().getActiveFrame());
                    ProjectManager.getSingleton().removeProject(ProjectManager.getSingleton().getActiveProject(), MainFrame.getSingleton().getDesktop());
                    JOptionPane.showMessageDialog(null, "No Namespaces Selected", "Error In Creating Project", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

            }//end if project created successfully. 
            else {
                JOptionPane.showMessageDialog(null, "Project : " + projectNameField.getText() + " is already open!", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }//end else
            return true;
    }
    
    private String[] selectedNamespaces() {
        if (namespaceList != null) {
            int selectedNs[] = namespaceList.getSelectedIndices();
            if (selectedNs.length > 0) {
                String[] namespaces = new String[selectedNs.length];
                for (int i = 0; i < selectedNs.length; i++) {
                    namespaces[i] = namespaceList.getModel().getElementAt(selectedNs[i]).toString();
                }
                return namespaces;
            }else{
                return null;
            }

        } else {
            return null;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        projectNameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jComboBox1 = new javax.swing.JComboBox();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        SWKMnsList = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        namespaceList = new javax.swing.JList();
        b2 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        b3 = new javax.swing.JRadioButton();
        b1 = new javax.swing.JRadioButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "1.Enter Project Name"));

        projectNameField.setText("MyProject");

        jLabel1.setText("ProjectName:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(projectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(481, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(projectNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jTabbedPane2.addTab("tab1", jComboBox1);

        jTabbedPane1.addTab("Sample", jTabbedPane2);
        jTabbedPane1.addTab("Local", jTabbedPane3);
        jTabbedPane1.addTab("Http", jTabbedPane4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "2.Select Project Files"));

        jButton5.setText("Remove");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Clear");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LOM2.rdf", "Cidoc.rdf", "Cidoc_Digital.rdfs", "TestFile.rdf", "forcesample.rdf", "ontologyRegistry.rdf", "testSchema1.rdfs", "testSchema1a.rdfs", "testSchema1b.rdfs", "testSchema2.rdfs", "testSchema3.rdfs", "testSchema4.rdfs", "testSchema5.rdfs", "testSchema6.rdfs" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(66, 66, 66))
        );

        jTabbedPane5.addTab("Sample", jPanel3);

        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("File:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );

        jTabbedPane5.addTab("Local", jPanel2);

        jLabel3.setText("HTTP:");

        jButton4.setText("Add");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );

        jTabbedPane5.addTab("HTTP", jPanel4);

        jButton7.setText("Connection Settings");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton9.setText("Add");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton8.setText("Connect");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(SWKMnsList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7)
                            .addComponent(jButton8))))
                .addContainerGap())
        );

        jTabbedPane5.addTab("SWKM", jPanel7);

        jLabel5.setText("Files:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5)))
                    .addComponent(jLabel5))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "3.Select Namespaces(Multiple With Ctrl)"));

        jScrollPane2.setViewportView(namespaceList);

        buttonGroup1.add(b2);
        b2.setText("Direct Dependencies");

        jLabel6.setText("Namespaces:");

        jLabel4.setText("Namespace Dependencies:");

        buttonGroup1.add(b3);
        b3.setText("Transitive Dependencies");

        buttonGroup1.add(b1);
        b1.setSelected(true);
        b1.setText("None");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addComponent(b1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b2)
                            .addGap(77, 77, 77)
                            .addComponent(b3))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b1)
                    .addComponent(b3)
                    .addComponent(b2))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 703, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if (nProj == null) {
        nProj = new Project(null);
    }
    
    String fileName = jComboBox2.getSelectedItem().toString();
    //if filename a predefined one, open from local file system using appropriate address
    if (fileName.equals("LOM2.rdf")) {
        fileName = "/SampleRDFFiles/LOM2.rdf";
    } else if (fileName.equals("Cidoc.rdf")) {
        fileName = "/SampleRDFFiles/cidoc_crm_v3.4.9.rdfs";
    } else if (fileName.equals("TestFile.rdf")) {
        fileName = "/SampleRDFFiles/testFile.rdf";
    } else if (fileName.equals("forcesample.rdf")) {
        fileName = "/SampleRDFFiles/forceSample.rdf";
    } else if (fileName.equals("ontologyRegistry.rdf")) {
        fileName = "/SampleRDFFiles/OntologyRegistry1.rdf";
    } else if (fileName.equals("multipleNameSpacesTest.rdf")) {
        fileName = "/SampleRDFFiles/multipleNameSpacesTest.rdf";
    } else if (fileName.equals("Cidoc_Digital.rdfs")){
        fileName = "/SampleRDFFiles/cidoc_digital.rdfs";
    }else if (fileName.equals("testSchema1.rdfs")){
        fileName = "/SampleRDFFiles/testSchema1.rdfs";
    }else if (fileName.equals("testSchema1a.rdfs")){
        fileName = "/SampleRDFFiles/testSchema1a.rdfs";
    }else if (fileName.equals("testSchema1b.rdfs")){
        fileName = "/SampleRDFFiles/testSchema1b.rdfs";
    }else if (fileName.equals("testSchema2.rdfs")){
        fileName = "/SampleRDFFiles/testSchema2.rdfs";
    }else if (fileName.equals("testSchema3.rdfs")){
        fileName = "/SampleRDFFiles/testSchema3.rdfs";
    }else if (fileName.equals("testSchema4.rdfs")){
        fileName = "/SampleRDFFiles/testSchema4.rdfs";
    }else if (fileName.equals("testSchema5.rdfs")){
        fileName = "/SampleRDFFiles/testSchema5.rdfs";
    }else if (fileName.equals("testSchema6.rdfs")){
        fileName = "/SampleRDFFiles/testSchema6.rdfs";
    }
    nProj.addDocument(fileName, Project.LOCATION_TYPE.CLASSPATH, null);
    if (fileName != null) {
        files.add(fileName);
        jList1.setListData(files.toArray());
    }
    namespaceList.setListData(nProj.getProjectNamespaces().toArray());
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    JFileChooser fc = new JFileChooser();
                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fc.setFileFilter(new RDFFileFilter());
                    int returnVal = fc.showOpenDialog(MainFrame.getSingleton());

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        jTextField2.setText(fc.getSelectedFile().getAbsolutePath());
                    }
}//GEN-LAST:event_jButton2ActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    if (nProj == null) {
        nProj = new Project(null);
    }
    String fileName = jTextField2.getText();
    nProj.addDocument(fileName, Project.LOCATION_TYPE.LOCAL, null);
    if (fileName != null) {
        files.add(fileName);
        jList1.setListData(files.toArray());
    }
    File f = new File(fileName);
    if (!f.exists()) {
        JOptionPane.showMessageDialog(null, "The file specified doesn't exist", "Alert", JOptionPane.ERROR_MESSAGE);

    }
    namespaceList.setListData(nProj.getProjectNamespaces().toArray());
}//GEN-LAST:event_jButton3ActionPerformed

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    int selIndexFiles[] = jList1.getSelectedIndices();
    for (int i = 0; i < selIndexFiles.length; i++) {
        String fileName = jList1.getModel().getElementAt(selIndexFiles[i]).toString();
        if (nProj != null) {
            nProj.removeDocument(fileName);
            namespaceList.setListData(nProj.getProjectNamespaces().toArray());
            files.remove(fileName);
            jList1.setListData(files.toArray());
        }
    }
}//GEN-LAST:event_jButton5ActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    
        
    if (nProj != null) {
        nProj.removeAllDocuments();
        namespaceList.setListData(nProj.getProjectNamespaces().toArray());
        files.clear();
        jList1.setListData(files.toArray());
    }

}//GEN-LAST:event_jButton6ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    if (nProj == null) {
        nProj = new Project(null);
    }
    String fileName = jTextField3.getText();
    nProj.addDocument(fileName, Project.LOCATION_TYPE.URL, null);
    if (fileName != null) {
        files.add(fileName);
        jList1.setListData(files.toArray());
    }
    if (fileName.equals("") || fileName.equals("No Data Model") || fileName == null) {
        JOptionPane.showMessageDialog(null, "The file specified doesn't exist", "Alert", JOptionPane.ERROR_MESSAGE);

    }
    namespaceList.setListData(nProj.getProjectNamespaces().toArray());
}//GEN-LAST:event_jButton4ActionPerformed

private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
JLabel hostLabel = new JLabel("Host:");
                    JLabel portLabel = new JLabel("Port:");
                    JLabel ContextLabel = new JLabel("WebContext:");
                    JLabel dbNameLabel = new JLabel("Database name:");
                    JLabel userNameLabel = new JLabel("Username:");
                    JLabel dbPasswdLabel = new JLabel("Password:");


                    JTextField dbHostField = new JTextField(dbHost, 15);
                    JTextField dbPortField = new JTextField(new Integer(dbPort).toString(), 5);
                    JTextField dbContextField = new JTextField(dbWebContext, 15);
                    JTextField dbNameField = new JTextField(dbName, 15);
                    JTextField dbUserNameField = new JTextField(dbUserName, 15);
                    JTextField dbPasswdField = new JTextField(dbPasswd, 15);


                    hostLabel.setLabelFor(dbHostField);
                    portLabel.setLabelFor(dbPortField);
                    ContextLabel.setLabelFor(dbContextField);
                    dbNameLabel.setLabelFor(dbNameField);
                    userNameLabel.setLabelFor(dbUserNameField);
                    dbPasswdLabel.setLabelFor(dbPasswdField);


                    JPanel Labelp = new JPanel(new GridLayout(0, 1));
                    Labelp.add(hostLabel);
                    Labelp.add(portLabel);
                    Labelp.add(ContextLabel);
                    Labelp.add(dbNameLabel);
                    Labelp.add(userNameLabel);
                    Labelp.add(dbPasswdLabel);

                    JPanel Fieldp = new JPanel(new GridLayout(0, 1));
                    Fieldp.add(dbHostField);
                    Fieldp.add(dbPortField);
                    Fieldp.add(dbContextField);
                    Fieldp.add(dbNameField);
                    Fieldp.add(dbUserNameField);
                    Fieldp.add(dbPasswdField);

                    JPanel genP = new JPanel();
                    genP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    genP.add(Labelp, BorderLayout.CENTER);
                    genP.add(Fieldp, BorderLayout.LINE_END);


                    Object[] message = new Object[2];
                    message[0] = "Enter connection settings";
                    message[1] = genP;

                    String[] options = {"OK", "Cancel"};

                    int result = JOptionPane.showOptionDialog(
                            null, // the parent that the dialog blocks 
                            message, // the dialog message array 
                            "Connection Settings", // the title of the dialog window 
                            JOptionPane.DEFAULT_OPTION, // option type 
                            JOptionPane.INFORMATION_MESSAGE, // message type 
                            null, // optional icon, use null to use the default icon 
                            options, // options string array, will be made into buttons 
                            options[0] // option that should be made into a default button 
                            );
                    if (result == 0) {
                        dbHost = dbHostField.getText();
                        dbPort = Integer.valueOf(dbPortField.getText());
                        dbWebContext = dbContextField.getText();
                        dbUserName = dbUserNameField.getText();
                        dbName = dbNameField.getText();
                        dbPasswd = dbPasswdField.getText();
                    }

                // TODO add your handling code here:
}//GEN-LAST:event_jButton7ActionPerformed

private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                    DbSettings dbs = new DbSettings();
                    dbs.setDbName(dbName);
                    dbs.setUsername(dbUserName);
                    dbs.setPassword(dbPasswd);

                    try {
                        client = ClientFactory.newClientToWebApp(dbHost, dbPort, dbWebContext).with(dbs);

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Problem with creating the connection", "Alert", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String queryRes = client.query().query(Format.RDF_XML, "namespaces");

                    System.out.println(queryRes);

                    queryRes = queryRes.replaceAll("\n", "");
                    String[] namespaces = parseNamespaces(queryRes);
                    if (namespaces == null) {
                        JOptionPane.showMessageDialog(null, "Error in Connection Parameter's", "Alert", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //Get the default namespaces from the database and exlude them from the option list
                    Set<String> defaultNameSpaces = ModelUtils.getDefaultNamespaces();

                    for (int i = 0; i < namespaces.length; i++) {
                        for (String dNSpace : defaultNameSpaces) {
                            if (dNSpace.equals(namespaces[i])) {
                                namespaces[i] = null;
                            }
                        }
                    }

                    int realSize = 0;
                    for (int i = 0; i < namespaces.length; i++) {
                        if (namespaces[i] != null) {
                            realSize++;
                        }
                    }
                    String[] namespacesToAdd = new String[realSize];
                    int k = 0;
                    for (int i = 0; i < namespaces.length; i++) {
                        if (namespaces[i] != null) {
                            namespacesToAdd[k] = namespaces[i];
                            k++;
                        }
                    }
                    SWKMnsList.setListData(namespacesToAdd);
}//GEN-LAST:event_jButton8ActionPerformed

private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    if (nProj == null) {
        nProj = new Project(null);
    }
    String fileName = SWKMnsList.getModel().getElementAt(SWKMnsList.getSelectedIndex()).toString();
//     		 		dbConnectionNeeded = true;
    nProj.addDocument(fileName, Project.LOCATION_TYPE.SWKM, client);
    if (fileName != null) {
        files.add(fileName);
        jList1.setListData(files.toArray());
    }
    if (fileName.equals("") || fileName.equals("No Data Model") || fileName == null) {
        JOptionPane.showMessageDialog(null, "The file specified doesn't exist", "Alert", JOptionPane.ERROR_MESSAGE);

    }
    namespaceList.setListData(nProj.getProjectNamespaces().toArray());
}//GEN-LAST:event_jButton9ActionPerformed

private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_jComboBox2ActionPerformed

/**
     * Parse the RDF file that contains the namespaces stored in db and returns a String array with all these
     * @param rdfFile : the file that contains namespaces
     * @return a sting array with all namespaces
     */
    private static String[] parseNamespaces(String rdfFile) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(rdfFile)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("rdf:li");
            String[] namespaces = new String[nodeList.getLength()];

            for (int i = 1; i < nodeList.getLength(); i++) {
                org.w3c.dom.Node node = nodeList.item(i);
                if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    namespaces[i] = node.getAttributes().getNamedItem("rdf:resource").getTextContent();
                }
            }//end for
            return namespaces;
        }//end try 
        catch (Exception e) {
            e.printStackTrace();
        }//end catch
        return null;
    }//end parseNamespaces


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList SWKMnsList;
    private javax.swing.JRadioButton b1;
    private javax.swing.JRadioButton b2;
    private javax.swing.JRadioButton b3;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JList namespaceList;
    private javax.swing.JTextField projectNameField;
    // End of variables declaration//GEN-END:variables

}
