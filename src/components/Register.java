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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame implements ActionListener {
	private Container pane;
	private JButton Checkout, BackOffice;
	private JPanel ButtonContainer;
	private User admin, currentUser = new User (0,"","",-1,0);
	private static final long serialVersionUID = 1L;
	
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
		    currentUser = searchUser(ID, password);
		    JOptionPane.showMessageDialog(null, "Login Successful.");
		    }		    
	    }
	    //if (Integer.parseInt(xField.getText())==0){
	    	
	    //}
	    
		//String inputValue = JOptionPane.showInputDialog("Input your used ID");
	}
	
	void displayReigster(){
		if (currentUser.getID()==-1) {
			return;
		}
		
		System.out.println("Yeah");
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
	};
}
