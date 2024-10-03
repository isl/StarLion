package gui;

import graphs.Graph;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.InputStream;

import java.util.Hashtable;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * MenuBar is the one that contains menus with all the available actions
 * It is also responsible for assigning the proper actions to the different menus 
 * @author leonidis-zabetak
 *
 */
public class MenuBar  {

    private JMenuBar menubar;
    private Hashtable<String, Integer> Mnemonics;//        public  String fileName = "";

    /**
     * Create a new MenuBar Object
     * Populate Mnemonics table and create all menus specified in menuconfig.xml
     */
    public MenuBar() {
        createMnemonics();
        menubar = new JMenuBar();
        createMenus();
    }//end empty constructor

    /**
     * Return the populated JMenuBar with all menus
     * @return menubar
     */
    public JMenuBar getMenuBar() {
        return menubar;
    }//end getMenuBar

    /**
     * Populate the internal Hashtable with mnemonics for each letter (the same letter is used as the hash key)
     * Mnemonics['A'] = new Integer(KeyEvent.VK_A)
     * Mnemonics['B'] = new Integer(KeyEvent.VK_B)
     * etc...
     */
    public void createMnemonics() {
        Mnemonics = new Hashtable<String, Integer>();
        Mnemonics.put("A", new Integer(KeyEvent.VK_A));
        Mnemonics.put("B", new Integer(KeyEvent.VK_B));
        Mnemonics.put("C", new Integer(KeyEvent.VK_C));
        Mnemonics.put("D", new Integer(KeyEvent.VK_D));
        Mnemonics.put("E", new Integer(KeyEvent.VK_E));
        Mnemonics.put("F", new Integer(KeyEvent.VK_F));
        Mnemonics.put("G", new Integer(KeyEvent.VK_G));
        Mnemonics.put("H", new Integer(KeyEvent.VK_H));
        Mnemonics.put("I", new Integer(KeyEvent.VK_I));
        Mnemonics.put("J", new Integer(KeyEvent.VK_J));
        Mnemonics.put("K", new Integer(KeyEvent.VK_K));
        Mnemonics.put("L", new Integer(KeyEvent.VK_L));
        Mnemonics.put("M", new Integer(KeyEvent.VK_M));
        Mnemonics.put("N", new Integer(KeyEvent.VK_N));
        Mnemonics.put("O", new Integer(KeyEvent.VK_O));
        Mnemonics.put("P", new Integer(KeyEvent.VK_P));
        Mnemonics.put("Q", new Integer(KeyEvent.VK_Q));
        Mnemonics.put("R", new Integer(KeyEvent.VK_R));
        Mnemonics.put("S", new Integer(KeyEvent.VK_S));
        Mnemonics.put("T", new Integer(KeyEvent.VK_T));
        Mnemonics.put("U", new Integer(KeyEvent.VK_U));
        Mnemonics.put("V", new Integer(KeyEvent.VK_V));
        Mnemonics.put("W", new Integer(KeyEvent.VK_W));
        Mnemonics.put("X", new Integer(KeyEvent.VK_X));
        Mnemonics.put("Y", new Integer(KeyEvent.VK_Y));
        Mnemonics.put("Z", new Integer(KeyEvent.VK_Z));
    }//end createMnemonics



    /**
     *	Parse menu config file and create each menu described in it
     */
    private void createMenus() {
        String expression = "/menubar/menu";
        //Open config file
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            //Create a new Document that represents this file
            db = dbf.newDocumentBuilder();
            InputStream menuConfigResource = MenuBar.class.getResourceAsStream("/config/menuconfig.xml");
            Document doc = db.parse(menuConfigResource);
            doc.getDocumentElement().normalize();
            XPath xpath = XPathFactory.newInstance().newXPath();
            //Create a list with all available defined "Menu" nodes
            NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
            //For each menu create the menus and menuites it contains
            for (int i = 0; i < nodes.getLength(); i++) {
                menubar.add(
                        createMenu(
                        nodes.item(i).getAttributes().getNamedItem("name").getNodeValue(),
                        nodes.item(i).getChildNodes()));
            }
        }//end try 
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }//end catch
    }//end createMenus

    /**
     * For each menu create all menus or menu items it contains
     * <menuitem>
     *		<name/>
     *		<actionCommand/> 
     *		<mnemonic/>
     *		<enabled/>
     *		<shouldEnable/>
     *		<dependsOn/>
     * </menuitem>
     * @param name : the name of the menu
     * @param children : a list with menu's children items
     * @return new menu
     */
    private JMenu createMenu(String name, NodeList children) {

        JMenu menu = new JMenu(name);
        //For each child create the menu item it describes 
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals("menuitem")) {
                MenuItem menuItem = createMenuItem(
                        children.item(i).getChildNodes().item(1).getTextContent(),
                        children.item(i).getChildNodes().item(3).getTextContent(),
                        getMnemonic(children.item(i).getChildNodes().item(5).getTextContent()),
                        children.item(i).getChildNodes().item(7).getTextContent(),
                        children.item(i).getChildNodes().item(9).getTextContent(),
                        children.item(i).getChildNodes().item(11).getTextContent());
                menu.add(menuItem);
            } else if (children.item(i).getNodeName().equals("menu")) {
                menu.add(createMenu(children.item(i).getAttributes().getNamedItem("name").getNodeValue(), children.item(i).getChildNodes()));
            } else if (children.item(i).getNodeName().equals("separator")) {
                menu.add(new JSeparator());
            }
        }//end for children.getLength

        return menu;
    }//end createMenu

    /**
     * Create a new menu item with a specified name, mnemonic and action
     * @param name : name that will appear in the menu
     * @param mnemonic : its mnemonic key
     * @param action : string that represents item's action
     * @param enabled : this item should be enabled at start
     * @param shouldEnable : this should be enabled sometime during use 
     * @return new menu item
     */
    private MenuItem createMenuItem(String name, String action, int mnemonic, String enabled, String shouldEnable, String dependencies) {

        MenuItem menuItem = new MenuItem(name);


        
            Class cls = null;
            Action a = null;
            try {
                cls = Class.forName("gui.MenuActions." + action);
                a = (Action) cls.newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(MenuBar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                
            
            if(a != null){
                menuItem.setAction(a);
            }
            
        

//            menuItem.setActionCommand(action);
//            menuItem.addActionListener(this);
        

        if (mnemonic != -1) {
            menuItem.setMnemonic(mnemonic);
            menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK));
        }


        menuItem.setText(name);
        if (enabled.equals("true")) {
            menuItem.setEnabled(true);
        } else {
            menuItem.setEnabled(false);
        }
        if (shouldEnable.equals("true")) {
            menuItem.setShouldEnable(true);
        } else {
            menuItem.setShouldEnable(false);
        }
        menuItem.setDependencies(dependencies);

        return menuItem;
    }

    /**
     * Update status of all available menus in menubar
     */
    public void updateMenus() {
        JMenu menu;
        for (int i = 0; i < menubar.getComponentCount(); i++) {
            menu = (JMenu) menubar.getComponent(i);
            updateMenu(menu);
        }
    }//end updateMenus

    /**
     * Update status of specified menu iff it should be updated (do not update unsupported functions)
     * and its specified dependencies (project, frame) are meet
     * @param menu
     */
    private void updateMenu(JMenu menu) {
        MenuItem menuItem;
        JMenu intMenu;
        for (int i = 0; i < menu.getItemCount(); i++) {
            if (menu.getItem(i) instanceof MenuItem) {
                menuItem = (MenuItem) menu.getItem(i);
                if (menuItem.getShouldEnable()) {
                    if (menuItem.getDependencies().contains("project")) {
                        if (ProjectManager.getSingleton().getTotalProjectsNo() > 0) {
                            menuItem.setEnabled(true);
                        } else {
                            menuItem.setEnabled(false);
                            continue;
                        }
                    }//end if menuItem.getDependencies().contains("project")
                    if (menuItem.getDependencies().contains("frame")) {
                        if (ProjectManager.getSingleton().getActiveProject().getTotalFramesNo() > 0) {
                            menuItem.setEnabled(true);
                        } else {
                            menuItem.setEnabled(false);
                            continue;
                        }
                        /*If no GraphInternal frame is active*/
                        if (getActiveGraph() == null) {
                            menuItem.setEnabled(false);
                            continue;
                        }
                    }//end if menuItem.getDependencies().contains("frame")
                }//end if getShouldEnable	
            }//end if instanceof MenuItem
            else if (menu.getItem(i) instanceof JMenu) {
                intMenu = (JMenu) menu.getItem(i);
                updateMenu(intMenu);
            }//end if instanceof JMenu
        }//end for i<menu.getItemCount
    }//end updateMenu

    /**
     * Get mnemonic for given letter (using Mnemonics HashTable)
     * @param c : letter used as hash key
     * @return c's mnemomic
     */
    private int getMnemonic(String c) {
        Integer mnemonic = (Integer) Mnemonics.get(c);
        if (mnemonic != null) {
            return mnemonic.intValue();
        } else {
            return -1;
        }
    }//end getMnemonic

    private Graph getActiveGraph() {
        InternalFrame IF = ProjectManager.getSingleton().getActiveProject().getActiveFrame();
        if (IF instanceof GraphInternalFrame) {
            return ((GraphInternalFrame) IF).getGraph();
        } else {
            return null;
        }

    }
}//end class menuBar