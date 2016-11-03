package components;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
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

import java.util.Date;
import java.util.Vector;

public class Register extends JFrame implements ActionListener{
	private Container pane;
	private JButton Checkout, BackOffice;
	private JPanel ButtonContainer;
	private User admin, currentUser = new User (0,"","",-1,0);
	private static final long serialVersionUID = 1L;
	private boolean registerActive = false;
	private Vector <PurchasedItem> sale = new Vector<PurchasedItem>();
	private DecimalFormat df = new DecimalFormat("#,###,###.00");
	
	Register(){
		FileManager.loadItems("src/data/items.csv");
		FileManager.loadUsers("src/data/users.csv");
		FileManager.loadOrders("src/data/orders.csv");
		
		//FileManager.writeUsersDB("src/data/users.txt");
		//FileManager.writeItemDB("src/data/items.txt");
		
		
		this.setTitle("Register");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(250, 300);
		pane = this.getContentPane();
		
		ButtonContainer = new JPanel();
		ButtonContainer.setPreferredSize(new Dimension(200, 250));
		
		Checkout = new JButton("Checkout");
		Checkout.setSize(new Dimension(40,60));
		ButtonContainer.add(Checkout);
		BackOffice = new JButton("Back Office");
		BackOffice.setSize(new Dimension(40,60));
		ButtonContainer.add(BackOffice);
		
		Checkout.addActionListener(this);
		BackOffice.addActionListener(this);
		
		pane.add(ButtonContainer);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		new Register();
	}
	
	boolean deployLogin(){
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
		    	//deployLogin();
		    }
		    //User is valid
		    else {
		    currentUser.clone(searchUser(ID, password));
		    return true;
		    }		    
	    }
	    return false;
	}

	void displayRegister(){
		if (currentUser.getID()==-1) {
			currentUser.reset();
			return;
		}
			registerActive = true;
			//Highest Level, Contains all elements in the register.
			JPanel registerGUI = new JPanel(new BorderLayout());
			registerGUI.setPreferredSize(new Dimension(250,300));
			
			//Mid Level, split input outputs into two sections.
			JPanel inputWindow = new JPanel(new FlowLayout());
			inputWindow.setPreferredSize(new Dimension(250,100));
			
			JPanel outputWindow = new JPanel(new FlowLayout());
			
			//Elements of the output window
			JTextArea itemWindow = new JTextArea();
			itemWindow.setPreferredSize(new Dimension(105,420));
			itemWindow.setEditable(false);
			itemWindow.append("Name:\n------------------------------\n");
			
			JTextArea quantityWindow = new JTextArea();
			quantityWindow.setPreferredSize(new Dimension(50,420));
			quantityWindow.setEditable(false);
			quantityWindow.append("Quantity\n--------------\n");
			
			JTextArea totalWindow = new JTextArea();
			totalWindow.setPreferredSize(new Dimension(75,420));
			totalWindow.setEditable(false);
			totalWindow.append("Total\n-------------------\n");
			
			//Elements of the input window
			JLabel itemLabel = new JLabel("Item ID: ");
			JTextField itemField = new JTextField(8);
			JLabel quantityLabel = new JLabel("Quantity: ");
			JTextField quantityField = new JTextField(2);
			JButton ringUpButton = new JButton("Ring Up");
			ringUpButton.setPreferredSize(new Dimension(120,75));
			JButton orderButton = new JButton("Create Order");
			orderButton.setPreferredSize(new Dimension(120,75));
			
			//Add elements of inputWindow into inputWindow.
			inputWindow.add(itemLabel);
			inputWindow.add(itemField);
			inputWindow.add(quantityLabel);
			inputWindow.add(quantityField);
			inputWindow.add(ringUpButton);
			inputWindow.add(orderButton);
			
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
					if(itemField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "No part number entered.", "Error", JOptionPane.ERROR_MESSAGE);
						itemField.requestFocus();
					}
					else{
						if(quantityField.getText().equals("")){
							JOptionPane.showMessageDialog(null, "No quantity entered.", "Error", JOptionPane.ERROR_MESSAGE);
							quantityField.requestFocus();
						}
						else{
							try {	
								
						    	int ID = Integer.parseInt(itemField.getText());
						    	int quantity = Integer.parseInt(quantityField.getText());
						    	Item bought;
						    	bought = FileManager.getItem(ID);
						    	if(bought.getID()==-1){
								    JOptionPane.showMessageDialog(null, "This item does not exist in the database");
								    itemField.setText("");
								    quantityField.setText("");
								    itemField.requestFocus();
						    	}
						    	else{
						    		sale.addElement(new PurchasedItem(bought.getID(), quantity, bought.getPrice(), bought.getName()));
						    		itemWindow.append(bought.getName() + "\n");
							    	quantityWindow.append("x "+ quantity + "\n");
							    	totalWindow.append("$" + df.format(bought.getPrice()*quantity)+"\n");
									itemField.setText("");
									quantityField.setText("");
									itemField.requestFocus();
						    	}
						    }
						    catch(NumberFormatException err){
						    	JOptionPane.showMessageDialog(null, "Quantity/ID Invalid, Verify input and try again.", "Error",JOptionPane.ERROR_MESSAGE);
						    	itemField.setText("");
								quantityField.setText("");
								itemField.requestFocus();
						    }
						}
					}
				}
			};
			
			itemField.addActionListener(itemQuantityListener);
			quantityField.addActionListener(itemQuantityListener);
			ringUpButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to ring up?", "Confirmation", JOptionPane.YES_NO_OPTION);
					
					if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION){
						
					}
					else{
						boolean isEmpty = true;
						reduceSaleVector();
						for (int i=0; i<sale.size();i++){
							if(sale.elementAt(i).getQuantity()!=0){
								isEmpty = false;
							}
						}
						
						if(sale.size()==0 || isEmpty){
							pane.remove(registerGUI);
							pane.add(ButtonContainer);
							pane.revalidate();
							pack();
						}
						else{
							completeSale();
							pane.remove(registerGUI);
							pane.add(ButtonContainer);
							pane.revalidate();
							pack();
							currentUser.reset();
						}
					}
				}
			});
			
			
			
			pane.remove(ButtonContainer);
			pane.add(registerGUI);
			pane.revalidate();
			pack();
	}
	
	public void completeSale(){
		String receipt = new String("Receipt:\n");
		double total = 0;
		for (int i=0; i<sale.size();i++){
			if (sale.elementAt(i).getQuantity()!=0){
				receipt += sale.elementAt(i).printItem();
				total += sale.elementAt(i).getPrice()*sale.elementAt(i).getQuantity();
			}
		}
		receipt +="\nTotal\n--------\n" + df.format(total) +"\n";
		JOptionPane.showMessageDialog(null, receipt);
		for(int i=0; i<sale.size(); i++){
			FileManager.sellItem(sale.elementAt(i).getID(), sale.elementAt(i).getQuantity());
		}
		FileManager.writeItemDB("src/data/items.csv");
		sale.clear();
	}
	
	public void completeOrder(){		
		String order = new String("Order:\n");		
		double total = 0;		
		for (int i=0; i<sale.size();i++){		
			if (sale.elementAt(i).getQuantity()!=0){		
				order += sale.elementAt(i).printItem();		
				total += sale.elementAt(i).getPrice()*sale.elementAt(i).getQuantity();		
			}		
		}		
		Vector <OrderedItem> orderArr =new Vector <OrderedItem>();		
		for (int i=0;i<sale.size();i++){		
			orderArr.addElement(new OrderedItem(sale.elementAt(i).getID(),sale.elementAt(i).getQuantity()));		
		}		
		int orderID;		
		if(FileManager.orders.isEmpty()){		
			orderID = 1;		
		}		
		else{		
			orderID = FileManager.orders.elementAt(FileManager.orders.size()-1).getID()+1;		
		}		
		Order newOrder = new Order(orderID,currentUser.getID(),new Date(),orderArr);		
		FileManager.orders.addElement(newOrder);		
		order +="\nTotal\n--------\n" + df.format(total) +"\n";		
		JOptionPane.showMessageDialog(null, order);		
		FileManager.writeOrdersDB("src/data/orders.csv");		
		sale.clear();		
	}
	
	private void reduceSaleVector(){
		int currentSize = sale.size();
		for(int i=0; i<currentSize;i++){
			for(int f=i+1; f<currentSize; f++){
				if (sale.elementAt(i).getID()==sale.elementAt(f).getID()){
					sale.elementAt(i).setQuantity(sale.elementAt(i).getQuantity()+sale.elementAt(f).getQuantity());
					sale.removeElementAt(f);
					currentSize--;
					f--;
				}
			}
		}
	}
	
	public void deployDecisionPanel(){
		JPanel decisionPane = new JPanel();
		decisionPane.setPreferredSize(new Dimension(200,250));
		decisionPane.add(new JLabel("Choose a database to edit:"), BorderLayout.NORTH);
		JButton itemB = new JButton("Items");
		itemB.setSize(new Dimension(40,60));
	    JButton userB = new JButton("Users");
	    userB.setSize(new Dimension(40,60));
	    JButton exitB = new JButton("Exit");
	    exitB.setSize(new Dimension(40,60));
	    decisionPane.add(itemB, BorderLayout.CENTER);
	    decisionPane.add(userB, BorderLayout.CENTER);
	    decisionPane.add(exitB, BorderLayout.SOUTH);
	    
	    pane.remove(ButtonContainer);
	    pane.add(decisionPane);
	    pane.validate();
	    pack();
	    
	    exitB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				pane.remove(decisionPane);
				pane.add(ButtonContainer);
				pane.revalidate();
				pane.repaint();
			}
			
		});
	    
	    userB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JPanel userDecisionPanel = new JPanel();
				userDecisionPanel.setPreferredSize(new Dimension(200,250));
				JButton addUserButton = new JButton("Add User");
				addUserButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						//pane.remove(userDecisionPanel);
						//pane.add(deployAddUser());
						pane.revalidate();
						pack();
					}
					
				});
				
				JButton editUserButton = new JButton("Edit User");
				editUserButton.addActionListener(new ActionListener(){
					
					public void actionPerformed(ActionEvent e){
						pane.remove(userDecisionPanel);
						pane.add(deployUserEdit());
						pane.revalidate();
						pack();
					}
				});
				
				JButton backButton = new JButton("<-- Back");
				backButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						pane.remove(userDecisionPanel);
						pane.add(decisionPane);
						pane.repaint();
						pack();
					}
					
				});
				
				userDecisionPanel.add(addUserButton, BorderLayout.CENTER);
				userDecisionPanel.add(editUserButton, BorderLayout.CENTER);
				userDecisionPanel.add(backButton, BorderLayout.SOUTH);
				
				pane.remove(decisionPane);
				pane.add(userDecisionPanel);
				
				pane.revalidate();
				pack();
			}
	    });
	    
	    itemB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pane.remove(decisionPane);
				pane.add(deployItemEdit());
				pane.revalidate();
				pack();
			}
	    });
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==Checkout){
			boolean success = false;
			if(!success){
				success = deployLogin();
			}
			if (success){
				displayRegister();
			}
		}
		
		if (e.getSource()==BackOffice){
			boolean success = false;
			if(!success){
				success = deployLogin();
			}
			if (success){
				if (currentUser.getLevel()>1){
					deployDecisionPanel();
				}
				else{
					JOptionPane.showMessageDialog(null, ("You do not have access to these files."));
				}
			}
			
		}
	}
	
	/**
	 * If user is not found a dummy user with an ID of -1 will be returned
	 * @return User object that matches ID and password from the database.
	 */
	User searchUser(int ID, int password){
		User returned;
		returned = FileManager.getUser(ID);
		if (returned.getID()!=-1&&returned.getPassword()==password){
			return returned;
		}
		else return new User(-1,"Yo","Dawg",-1,47);
	}
	

	public JPanel deployItemEdit(){
		JPanel editItemPanel = new JPanel();
		editItemPanel.setLayout(new BoxLayout(editItemPanel, BoxLayout.LINE_AXIS));
		editItemPanel.setPreferredSize(new Dimension(200,250));
		
		JLabel preIDLabel = new JLabel("Item ID: ");
		
		JTextField IDField = new JTextField();
		IDField.setPreferredSize(new Dimension(70,20));
		IDField.setMaximumSize(IDField.getPreferredSize());
		//IDField.setMinimumSize(IDField.getPreferredSize());
		
		JButton searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(40,20));
		
		JPanel IDPanel = new JPanel();
		IDPanel.setLayout(new BoxLayout(IDPanel, BoxLayout.LINE_AXIS));
		IDPanel.setAlignmentX(LEFT_ALIGNMENT);
		IDPanel.setAlignmentY(TOP_ALIGNMENT);
		IDField.setText("");
		
		IDPanel.add(preIDLabel);
		IDPanel.add(IDField);
		IDPanel.add(searchButton);
		
		editItemPanel.add(IDPanel);
		
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int ID = -1;
				try {		    	
			    	ID = Integer.parseInt(IDField.getText());
			    	Item editableItem = FileManager.getItem(ID);
			    	if(IDField.getText() == ""){
						JOptionPane.showMessageDialog(null, "No ID Entered", "Error",JOptionPane.ERROR_MESSAGE);
					}
					else{
						if(editableItem.getID()==-1){
							JOptionPane.showMessageDialog(null, "Error: No item exists with this ID");
						}
						else{
							//ID Panel
							
							JLabel postIDLabel = new JLabel("" + Integer.toString(editableItem.getID()) + "   ");
							IDField.setText("");
							
							//Stock Label
							JPanel stockPanel = new JPanel();
							stockPanel.setLayout(new BoxLayout(stockPanel, BoxLayout.LINE_AXIS));
							stockPanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel preStockLabel = new JLabel("Stock: ");
							JLabel postStockLabel = new JLabel("" + Integer.toString(editableItem.getStock()) + "   ");
							
							JTextField stockField = new JTextField();
							stockField.setPreferredSize(new Dimension(70,20));
							stockField.setMaximumSize(IDField.getPreferredSize());
							
							//Price Label
							JPanel namePanel = new JPanel();
							namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
							namePanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel preNameLabel = new JLabel("Name: ");
							JLabel postNameLabel = new JLabel("" + editableItem.getName() + "   ");
							
							JTextField nameField = new JTextField();
							nameField.setPreferredSize(new Dimension(70,20));
							nameField.setMaximumSize(nameField.getPreferredSize());
							
							//Price Label
							JPanel pricePanel = new JPanel();
							pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.LINE_AXIS));
							pricePanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel prePriceLabel = new JLabel("Price: ");
							JLabel postPriceLabel = new JLabel("" + editableItem.getPrice() + "   ");
							
							JTextField priceField = new JTextField();
							priceField.setPreferredSize(new Dimension(70,20));
							priceField.setMaximumSize(priceField.getPreferredSize());
							
							JPanel buttonPanel = new JPanel();
							buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
							buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
							buttonPanel.setAlignmentY(BOTTOM_ALIGNMENT);
							JButton updateButton = new JButton("Update");
							JButton exitButton = new JButton("Exit");
							
							///////////////////////
							
							editItemPanel.setLayout(new BoxLayout(editItemPanel, BoxLayout.Y_AXIS));
							
							IDPanel.remove(searchButton);
							IDPanel.remove(IDField);
							IDPanel.add(postIDLabel);
							
							namePanel.add(preNameLabel);
							namePanel.add(postNameLabel);
							namePanel.add(nameField);
							
							stockPanel.add(preStockLabel);
							stockPanel.add(postStockLabel);
							stockPanel.add(stockField);
							
							pricePanel.add(prePriceLabel);
							pricePanel.add(postPriceLabel);
							pricePanel.add(priceField);
							
							buttonPanel.add(updateButton);
							buttonPanel.add(exitButton);
							
							editItemPanel.remove(preIDLabel);
							editItemPanel.remove(IDField);
							editItemPanel.revalidate();
							pack();
							editItemPanel.remove(searchButton);
							editItemPanel.add(IDPanel);
							editItemPanel.add(namePanel);
							nameField.requestFocus();
							editItemPanel.add(stockPanel);
							editItemPanel.add(pricePanel);
							editItemPanel.add(Box.createGlue());
							editItemPanel.add(buttonPanel);
							
							editItemPanel.revalidate();
							pack();
							
							updateButton.addActionListener(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									
									if(nameField.getText().equals("")){
										
									}
									else{
										FileManager.changeItemName(editableItem.getID(), nameField.getText());
										postNameLabel.setText(nameField.getText() + "  ");
									}
									
									if(stockField.getText().equals("")){
										
									}
									else{
										try {		    	
									    	int stock = Integer.parseInt(stockField.getText());
									    	FileManager.changeItemStock(editableItem.getID(), stock);
									    	postStockLabel.setText(Integer.toString(stock) + "  ");
									    }
									    catch(NumberFormatException e){
									    	JOptionPane.showMessageDialog(null, "Stock input invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
									    }
									}
									if(priceField.getText().equals("")){
										
									}
									else{
										try {		    	
									    	double price = Double.parseDouble(priceField.getText());
									    	FileManager.changeItemPrice(editableItem.getID(), price);
									    	postPriceLabel.setText(Double.toString(price) + "  ");
									    }
									    catch(NumberFormatException e){
									    	JOptionPane.showMessageDialog(null, "Password input invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
									    }
									}
									
									nameField.setText("");
									stockField.setText("");
									priceField.setText("");
																				
									FileManager.writeItemDB("src/data/items.csv");
									
									editItemPanel.revalidate();
									pack();
								}
							
							});
							
							exitButton.addActionListener(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									pane.remove(editItemPanel);
									pane.add(ButtonContainer);
									pane.revalidate();
									pane.repaint();
								}
								
							});
						}
					}
			    }
			    catch(NumberFormatException e){
			    	JOptionPane.showMessageDialog(null, "User ID invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
			    }
				
			}
		});
		
		
		
		return editItemPanel;
	}
	
	public JPanel deployUserEdit(){
		JPanel editUserPanel = new JPanel();
		editUserPanel.setPreferredSize(new Dimension(200,250));
		editUserPanel.setLayout(new BoxLayout(editUserPanel, BoxLayout.Y_AXIS));
		
		//ID Panel
		
		JPanel IDPanel = new JPanel();
		IDPanel.setLayout(new BoxLayout(IDPanel, BoxLayout.LINE_AXIS));
		IDPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel preIDLabel = new JLabel("ID: ");
		
		JTextField IDField = new JTextField();
		IDField.setPreferredSize(new Dimension(70,20));
		IDField.setMaximumSize(IDField.getPreferredSize());
		IDField.setText("");
		//IDField.setMinimumSize(IDField.getPreferredSize());
		
		JButton searchButton = new JButton("Search");
		searchButton.setMinimumSize(new Dimension(40,20));
		
		JPanel exitButtonPanel = new JPanel();
		exitButtonPanel.setLayout(new BoxLayout(exitButtonPanel, BoxLayout.LINE_AXIS));
		exitButtonPanel.setAlignmentX(LEFT_ALIGNMENT);
		exitButtonPanel.setAlignmentY(BOTTOM_ALIGNMENT);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setMinimumSize(new Dimension(40,20));
		
		JButton backButton = new JButton("<-- Back");
		backButton.setMinimumSize(new Dimension(40,20));
		
		IDPanel.add(preIDLabel);
		IDPanel.add(IDField);
		IDPanel.add(searchButton);
		
		exitButtonPanel.add(exitButton);
		exitButtonPanel.add(backButton);
		
		editUserPanel.add(IDPanel);
		editUserPanel.add(Box.createGlue());
		editUserPanel.add(exitButtonPanel);
		
		exitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				pane.remove(editUserPanel);
				pane.add(ButtonContainer);
				pane.revalidate();
				pane.repaint();
			}
			
		});
		
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int ID = -1;
				try {		    	
			    	ID = Integer.parseInt(IDField.getText());
			    	User editableUser = FileManager.getUser(ID);
			    	if(IDField.getText() == ""){
						JOptionPane.showMessageDialog(null, "No ID Entered", "Error",JOptionPane.ERROR_MESSAGE);
					}
					else{
						if(editableUser.getID()==-1){
							JOptionPane.showMessageDialog(null, "Error: No user exists with this ID");
						}
						else{
							editUserPanel.remove(exitButtonPanel);
							//ID Panel
							//editUserPanel.setLayout(new BoxLayout(editUserPanel, BoxLayout.LINE_AXIS));
							
							JLabel postIDLabel = new JLabel("" + Integer.toString(editableUser.getID()) + "   ");
							IDField.setText("");
							
							//Level Label
							JPanel levelPanel = new JPanel();
							levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.LINE_AXIS));
							levelPanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel preLevelLabel = new JLabel("Level: ");
							JLabel postLevelLabel = new JLabel("" + Integer.toString(editableUser.getLevel()) + "   ");
							
							JTextField levelField = new JTextField();
							levelField.setPreferredSize(new Dimension(70,20));
							levelField.setMaximumSize(IDField.getPreferredSize());
							
							//FName Label
							JPanel fNamePanel = new JPanel();
							fNamePanel.setLayout(new BoxLayout(fNamePanel, BoxLayout.LINE_AXIS));
							fNamePanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel preFNameLabel = new JLabel("First Name: ");
							JLabel postFNameLabel = new JLabel("" + editableUser.getFName() + "   ");
							
							JTextField fNameField = new JTextField();
							fNameField.setPreferredSize(new Dimension(70,20));
							fNameField.setMaximumSize(fNameField.getPreferredSize());
							
							//LName Label
							JPanel lNamePanel = new JPanel();
							lNamePanel.setLayout(new BoxLayout(lNamePanel, BoxLayout.LINE_AXIS));
							lNamePanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel preLNameLabel = new JLabel("Last Name: ");
							JLabel postLNameLabel = new JLabel("" + editableUser.getLName() + "   ");
							
							JTextField lNameField = new JTextField();
							lNameField.setPreferredSize(new Dimension(70,20));
							lNameField.setMaximumSize(lNameField.getPreferredSize());
							
							//Password Label
							JPanel passwordPanel = new JPanel();
							passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.LINE_AXIS));
							passwordPanel.setAlignmentX(LEFT_ALIGNMENT);
							
							JLabel prePasswordLabel = new JLabel("Password: ");
							JLabel postPasswordLabel = new JLabel("" + editableUser.getPassword() + "   ");
							
							JTextField passwordField = new JTextField();
							passwordField.setPreferredSize(new Dimension(70,20));
							passwordField.setMaximumSize(passwordField.getPreferredSize());
							
							JPanel buttonPanel = new JPanel();
							buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
							buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
							buttonPanel.setAlignmentY(BOTTOM_ALIGNMENT);
							JButton updateButton = new JButton("Update");
							JButton exitButton = new JButton("Exit");
							
							///////////////////////
							
							editUserPanel.setLayout(new BoxLayout(editUserPanel, BoxLayout.Y_AXIS));
							
							IDPanel.remove(searchButton);
							IDPanel.remove(IDField);
							IDPanel.add(postIDLabel);
							
							fNamePanel.add(preFNameLabel);
							fNamePanel.add(postFNameLabel);
							fNamePanel.add(fNameField);
							
							lNamePanel.add(preLNameLabel);
							lNamePanel.add(postLNameLabel);
							lNamePanel.add(lNameField);
							
							levelPanel.add(preLevelLabel);
							levelPanel.add(postLevelLabel);
							levelPanel.add(levelField);
							
							passwordPanel.add(prePasswordLabel);
							passwordPanel.add(postPasswordLabel);
							passwordPanel.add(passwordField);
							
							buttonPanel.add(updateButton);
							buttonPanel.add(exitButton);
							
							editUserPanel.remove(preIDLabel);
							editUserPanel.remove(IDField);
							editUserPanel.revalidate();
							pack();
							editUserPanel.remove(searchButton);
							editUserPanel.add(IDPanel);
							editUserPanel.add(fNamePanel);
							fNameField.requestFocus();
							editUserPanel.add(lNamePanel);
							editUserPanel.add(levelPanel);
							editUserPanel.add(passwordPanel);
							editUserPanel.add(Box.createGlue());
							editUserPanel.add(buttonPanel);
							
							editUserPanel.revalidate();
							pack();
							
							updateButton.addActionListener(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									if(fNameField.getText().equals("")){												
										if(lNameField.getText().equals("")){
											
										}
										else{
											FileManager.changeUserName(editableUser.getID(), editableUser.getFName(), lNameField.getText());
											postLNameLabel.setText(lNameField.getText() + "  ");
										}
									}
									else{
										if(lNameField.getText().equals("")){
											FileManager.changeUserName(editableUser.getID(), fNameField.getText(), editableUser.getLName());
											postFNameLabel.setText(fNameField.getText() + "  ");
										}
										else{
											FileManager.changeUserName(editableUser.getID(), fNameField.getText(), lNameField.getText());
											postFNameLabel.setText(fNameField.getText() + "  ");
											postLNameLabel.setText(lNameField.getText() + "  ");
										}
									}
									if(levelField.getText().equals("")){
										
									}
									else{
										try {		    	
									    	int level = Integer.parseInt(levelField.getText());
									    	FileManager.changeUserLevel(editableUser.getID(), level);
									    	postLevelLabel.setText(Integer.toString(level) + "  ");
									    }
									    catch(NumberFormatException e){
									    	JOptionPane.showMessageDialog(null, "Level input invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
									    }
									}
									if(passwordField.getText().equals("")){
										
									}
									else{
										try {		    	
									    	int password = Integer.parseInt(passwordField.getText());
									    	FileManager.changeUserPassword(editableUser.getID(), password);
									    	postPasswordLabel.setText(Integer.toString(password) + "  ");
									    }
									    catch(NumberFormatException e){
									    	JOptionPane.showMessageDialog(null, "Password input invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
									    }
									}
									
									fNameField.setText("");
									lNameField.setText("");
									levelField.setText("");
									passwordField.setText("");
									
									FileManager.writeUsersDB("src/data/users.csv");
									
									editUserPanel.revalidate();
									pack();
								}
							
							});
							
							exitButton.addActionListener(new ActionListener(){

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									pane.remove(editUserPanel);
									pane.add(ButtonContainer);
									pane.revalidate();
									pane.repaint();
								}
								
							});
						}
					}
			    }
			    catch(NumberFormatException e){
			    	JOptionPane.showMessageDialog(null, "User ID invalid, please use numbers.", "Error",JOptionPane.ERROR_MESSAGE);
			    }
				
			}
		});
		
		return editUserPanel;
	}
	
	public JPanel deployAddUserPanel(){
		JPanel addUserPanel = new JPanel();
		
		
		return addUserPanel;
	}
}


