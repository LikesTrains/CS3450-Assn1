package components;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Register extends JFrame implements ActionListener{
	private Container pane;
	private JButton Checkout, BackOffice;
	private JPanel ButtonContainer;
	private User admin, currentUser = new User (0,"","",-1,0);
	private static final long serialVersionUID = 1L;
	private boolean registerActive = false;
	
	Register(){
		this.setTitle("Register");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(250, 300);
		pane = this.getContentPane();
		
		ButtonContainer = new JPanel();
		ButtonContainer.setPreferredSize(new Dimension(200, 250));
		
		Checkout = new JButton("Checkout");
		Checkout.setSize(40, 60);
		ButtonContainer.add(Checkout);
		BackOffice = new JButton("Back Office");
		BackOffice.setSize(40, 60);
		ButtonContainer.add(BackOffice);
		
		Checkout.addActionListener(this);
		BackOffice.addActionListener(this);
		
		pane.add(ButtonContainer);
		
		admin = new User(1, "John", "Cena", 001, 123);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		new Register();
	}
	
	void deployLogin(){
		JTextField IDField = new JTextField(5);
	    JPasswordField PassField = new JPasswordField(5);
	    

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("ID:"));
	    myPanel.add(IDField);
	    IDField.requestFocus();
	    revalidate();
	    pack();
	    IDField.getCursor();
	    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    myPanel.add(new JLabel("Password:"));
	    myPanel.add(PassField);
	    int ID=0, password=0;
	    int result = JOptionPane.showConfirmDialog(null, myPanel,"Please Enter your Login ID and Password", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.CANCEL_OPTION||result==JOptionPane.CLOSED_OPTION){
	    	
	    }
	    else if(IDField.getText().equals("")){
	    	JOptionPane.showMessageDialog(null, "Please input an ID.", "Error",JOptionPane.ERROR_MESSAGE);
	    }
	    else if(PassField.getPassword().length==0){
	    	JOptionPane.showMessageDialog(null, "Please input a Passsword.", "Error",JOptionPane.ERROR_MESSAGE);
	    }
	    else{
		    try {		    	
		    	ID = Integer.parseInt(IDField.getText());
		    }
		    catch(NumberFormatException e){
		    	JOptionPane.showMessageDialog(null, "User ID invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
		    }
		    try {
		    	String passInput = new String(PassField.getPassword());
		    	password = Integer.parseInt(passInput);
		    }
		    catch(NumberFormatException e){
		    	JOptionPane.showMessageDialog(null, "Password invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
		    }
		    if (searchUser(ID, password).getID()==-1){
		    	JOptionPane.showMessageDialog(null, "Username/password combination invalid.");
		    }
		    //User is valid
		    else {
		    currentUser.clone(searchUser(ID, password));
		    JOptionPane.showMessageDialog(null, "Login Successful.");
		    }		    
	    }
	}
	
	void displayReigster(){
		if (currentUser.getID()==-1) {
			currentUser.reset();
			return;
		}
			registerActive = true;
			//Highest Level, Contains all elements in the register.
			JPanel registerGUI = new JPanel(new BorderLayout());
			registerGUI.setPreferredSize(new Dimension(250,300));
			
			//Mid Level, split input outputs into two sections.
			JPanel outputWindow = new JPanel(new FlowLayout());
			
			JPanel inputWindow = new JPanel(new FlowLayout());
			inputWindow.setPreferredSize(new Dimension(250,100));
			
			//Elements of the output window
			JTextArea itemWindow = new JTextArea();
			itemWindow.setPreferredSize(new Dimension(105,420));
			itemWindow.setEditable(false);
			itemWindow.append("Part Number\n------------------------------\n");
			JTextArea quantityWindow = new JTextArea();
			quantityWindow.setPreferredSize(new Dimension(50,420));
			quantityWindow.setEditable(false);
			quantityWindow.append("Quantity\n--------------\n");
			JTextArea totalWindow = new JTextArea();
			totalWindow.setPreferredSize(new Dimension(50,420));
			totalWindow.setEditable(false);
			totalWindow.append("Total\n--------------\n");
			
			//JScrollPane scrollWindow = new JScrollPane(itemWindow, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			//Elements of the input window
			JLabel itemLabel = new JLabel("Part #: ");
			JTextField itemField = new JTextField(8);
			JLabel quantityLabel = new JLabel("Quantity: ");
			JTextField quantityField = new JTextField(2);
			JButton checkoutButton = new JButton("Checkout");
			checkoutButton.setPreferredSize(new Dimension(250,75));
			
			//Add elements of inputWindow into inputWindow.
			inputWindow.add(itemLabel);
			inputWindow.add(itemField);
			inputWindow.add(quantityLabel);
			inputWindow.add(quantityField);
			inputWindow.add(checkoutButton);
			
			//Add elements of outputWindow into outputWindow
			outputWindow.add(itemWindow);
			outputWindow.add(quantityWindow);
			outputWindow.add(totalWindow);
			
			//Add input and output windows into registerGUI.
			registerGUI.add(outputWindow, BorderLayout.CENTER);
			registerGUI.add(inputWindow, BorderLayout.SOUTH);
			
			ActionListener itemQuantityListener = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Dimension dim = itemWindow.getSize();
					int dimx = (int) dim.getWidth();
					itemWindow.append(itemField.getText() + "\n");
					quantityWindow.append("x "+ quantityField.getText() + "\n");
					totalWindow.append("$0.00\n");
					itemField.setText("");
					quantityField.setText("");
					itemField.requestFocus();
				}
			};
			
			itemField.addActionListener(itemQuantityListener);
			quantityField.addActionListener(itemQuantityListener);
			checkoutButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					pane.remove(registerGUI);
					pane.add(ButtonContainer);
					pane.revalidate();
					pack();
				}
			});
			
			pane.remove(ButtonContainer);
			pane.add(registerGUI);
			pane.revalidate();
			pack();
			currentUser.reset();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==Checkout){
			deployLogin();
			displayReigster();
		}
		
		if (e.getSource()==BackOffice){
			deployLogin();
		}
	}
	
	/**
	 * If user is not found a dummy user with an ID of -1 will be returned
	 * @return User object that matches ID and password from the database.
	 */
	User searchUser(int ID, int password){
		if (admin.getID()==ID&&admin.getPassword()==password){
			return admin;
		}
		return new User(1,"Yo","Dawg",-1,47);
	}
}
