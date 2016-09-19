package components;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	private Container pane;
	private JButton Checkout, BackOffice;
	private JPanel ButtonContainer;
	private User admin;
	
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
	    JTextField PassField = new JTextField(5);

	    JPanel myPanel = new JPanel();
	    myPanel.add(new JLabel("ID:"));
	    myPanel.add(IDField);
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
	    else if(PassField.getText().equals("")){
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
		    	password = Integer.parseInt(PassField.getText());
		    }
		    catch(NumberFormatException e){
		    	JOptionPane.showMessageDialog(null, "Password invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
		    }
		    if (ID==admin.getID()&&password==admin.getPassword()){
		    	JOptionPane.showMessageDialog(null, "This is the register");
		    }
		    
		    
		    
	    }
	    //if (Integer.parseInt(xField.getText())==0){
	    	
	    //}
	    
		//String inputValue = JOptionPane.showInputDialog("Input your used ID");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==Checkout){
			deployLogin();
		}
		
		if (e.getSource()==BackOffice){
			deployLogin();
		}
	}
}
