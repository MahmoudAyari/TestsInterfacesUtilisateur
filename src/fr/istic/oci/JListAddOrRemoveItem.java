package fr.istic.oci;
import java.awt.BorderLayout;
import java.awt.Dimension; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
 
public class JListAddOrRemoveItem extends JFrame implements ActionListener {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int width = 400;
    private int height = 500;
    private static final String IDENTIF_PATTERN = "^[0-9]{3}-[A-Z]{3}-[0-9]{3}$" ;
    private static Pattern pattern;
    private static Matcher matcher;
    JButton btnAdd,btnDelete;
    private JTextField name,surname,identifiant;
    private JList<String> myList;
    JLabel nameLabel,surnameLabel , identifierLabel ;
    public JListAddOrRemoveItem() {
        // add main panel
        add(createMainPanel());
 
        // display
        setTitle("Add/Remove JList");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }
 
    private JPanel createMainPanel() {
        // create main panel
        JPanel panel = new JPanel(new BorderLayout());
        // set border empty for main panel
        panel.setBorder(new EmptyBorder(0, 10, 10, 10));
        // add content
        panel.add(createAddPanel(), BorderLayout.PAGE_START);
        panel.add(createDeletePanel(), BorderLayout.PAGE_END);
       
        panel.add(createList(), BorderLayout.CENTER);
        return panel;
    }
 
    // delete panel in top of frame
    private JPanel createDeletePanel() {
        JPanel panel = new JPanel();
         btnDelete = new JButton("Delete");
         btnDelete.addActionListener(this);
        panel.add(btnDelete);
        btnDelete.setEnabled(false);
        return panel;
    }
 
    // add panel in bottom of frame
    private JPanel createAddPanel() {
      
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 140));
        JLabel nameLabel = new JLabel("Nom");
        name = new JTextField("",20); 
        panel.add(nameLabel);
        panel.add(name);
        JLabel surnameLabel = new JLabel("Prénom");
        surname = new JTextField("",20); 
        panel.add(surnameLabel);
        panel.add(surname);
        JLabel idLabel = new JLabel("Ident");
        identifiant = new JTextField("",20); 
        panel.add(idLabel);
        panel.add(identifiant);
         btnAdd = new JButton("Add");
        btnAdd.addActionListener(this);
        panel.add(btnAdd);
        
        return panel;
    }
 
    // create Jlist
    private JList<String> createList() {
        // create defaultListModel
        DefaultListModel<String> model = new DefaultListModel<>();
        // set model to JList
        myList = new JList<String>(model);
        myList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()){
                  btnDelete.setEnabled(true);
                } 
            }
        });
        return myList;
    }
 
    public void select(){
    	DefaultListModel<String> model = (DefaultListModel<String>) myList
                .getModel();
    	if (!model.isEmpty() && myList.getSelectedIndex() >= 0) {
    		btnDelete.setEnabled(true);
    	}
    }
    
 
    private void deleteItem() {
        // get model of Jlist
        DefaultListModel<String> model = (DefaultListModel<String>) myList
                .getModel();
        
        // delete item is selected
        if (!model.isEmpty() && myList.getSelectedIndex() >= 0) {
            model.remove(myList.getSelectedIndex());
               btnDelete.setEnabled(true);
                   
        }
        // set model for JList
        myList.setModel(model);
    }
 
    private void addItem() {
        String n = name.getText().trim();
        String s = surname.getText().trim();
        String i=  identifiant.getText().trim();
        String item= n+"---"+s+"---"+i;
        if (n.equals("") || s.equals("")|| i.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Tous les champs sont obligatoires", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!validate(i)) {
        	JOptionPane.showMessageDialog(null,
                    "Identifiant non Valide", "Error",
                    JOptionPane.ERROR_MESSAGE);
		} 
        else {
            DefaultListModel model = (DefaultListModel) myList.getModel();
            model.addElement(item);
            myList.setModel(model);
            name.setText("");
            surname.setText("");
            identifiant.setText("");
            btnDelete.setEnabled(false);
        }
    }
 
    
    public boolean validate(final String identifiant) {
	     matcher = pattern.matcher(identifiant);
	     return matcher.matches();
	  }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Delete") {
            deleteItem();
            btnDelete.setEnabled(false);
            return;
        }
        if (e.getActionCommand() == "Add") {
            addItem();
        }
    }
 
    public static void main(String[] args) {
    	 
    	pattern = Pattern.compile(IDENTIF_PATTERN);
        new JListAddOrRemoveItem();
    }
}