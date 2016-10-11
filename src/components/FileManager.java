package components;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Vector;

import javax.swing.JOptionPane;

public class FileManager {
	static Vector <Item> items;
	static Vector <User> users;
	static void loadItems(String fileName){
		try {
	        FileInputStream fStream = new FileInputStream(fileName);
	        BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
	        if (in.ready()){
	        	String firstLineCheck = in.readLine();
	        	if (!firstLineCheck.equals("items")){
	        		in.close();
	        		JOptionPane.showMessageDialog(null, ("File "+ fileName +" is not an item database."));
	        		return;
	        	}
	        }
	        items = new Vector <Item>();
	        String input;
	        while (in.ready()) {
				input = in.readLine();
				String[] split = input.split("\\s+");
				Item bob = new Item(Integer.parseInt(split[0]),split[1],Double.parseDouble(split[2]),Integer.parseInt(split[3]));
				items.add(bob);
	        }
	        in.close();
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, ("File input error"));
	    }
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, ("File formatted improperly"));
		}
	}
	static void loadUsers(String fileName){
		try {
	        FileInputStream fStream = new FileInputStream(fileName);
	        BufferedReader in = new BufferedReader(new InputStreamReader(fStream));
	        if (in.ready()){
	        	String firstLineCheck = in.readLine();
	        	if (!firstLineCheck.equals("users")){
	        		in.close();
	        		JOptionPane.showMessageDialog(null, ("File "+ fileName +" is not an user database."));
	        		return;
	        	}
	        }
	        users = new Vector <User>();
	        String input;
	        while (in.ready()) {
				input = in.readLine();
				String[] split = input.split("\\s+");
				User newUser = new User(Integer.parseInt(split[0]),split[1],split[2],Integer.parseInt(split[3]),Integer.parseInt(split[4]));
				users.addElement(newUser);
	        }
	        in.close();
	    } catch (IOException e) {
	    	JOptionPane.showMessageDialog(null, ("File input error"));
	    }
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, ("File formatted improperly"));
		}
	}
	static void printItems(){
		for(int i=0;i<items.size();i++){
			items.get(i).printItem();
		}
	}
	static void printUsers(){
		for(int i=0;i<users.size();i++){
			users.get(i).printUser();
		}
	}
	/**
	 * @param ID Input
	 * @param index Index found
	 * If ID is not found, index has a negative value of the location where the new element could be added
	 */
	static private int findItem(int ID){
		int index= -1;
		for(int i=0;i<items.size();i++){
			if (items.elementAt(i).getID()==ID){
				index=i;
				return index;
			}
			if (items.elementAt(i).getID()>ID){
				index= -i;
				return index;
			}
		}
		return index;
	}	
	/**
	 * @param ID: ID of the item being searched
	 * @return A dummy item with an ID of negative 1 if the item isn't found, else it returns the item
	 */
	static Item getItem(int ID){
		int index=0;
		index = findItem(ID);
		Item dummy = new Item(-1, "None", 0, 0);
		if (index>=0){
			return items.elementAt(index);
		}		
		else{
			return dummy;
		}
	}
	/**
	 * @param ID: ID of the item being searched
	 * @return A dummy user with an ID of negative 1 if the user isn't found, else it returns the user
	 */
	static User getUser(int ID){
		int index=0;
		index = findUser(ID);
		User dummy = new User(-1, "Dummy", "Dummington",0,0000);
		if (index>=0){
			return users.elementAt(index);
		}		
		else{
			return dummy;
		}
	}
	/**
	 * @param ID Input
	 * @param index Index found
	 * If ID is not found, index has a negative value of the location where the new element could be added
	 */
	static private int findUser(int ID){
		int index= -1;
		for(int i=0;i<users.size();i++){
			if (users.elementAt(i).getID()==ID){
				index=i;
				return index;
			}
			if (users.elementAt(i).getID()>ID){
				index= -i;
				return index;
			}
			index=-i;
		}
		return index-1;
	}
	static void addItem(Item newItem){
		int index = 0;
		index = findItem(newItem.getID());
		if (index>=0){
			JOptionPane.showMessageDialog(null, ("Item already in storage."));
			return;
		}
		items.insertElementAt(newItem, -index);
	}
	static void removeItem(int ID){
		int index = 0;
		index = findItem(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, ("Item not found."));
			return;
		}
		items.remove(index);
	}
	static void addUser(User newUser){
		int index = 0;
		index = findUser(newUser.getID());
		if (index>=0){
			JOptionPane.showMessageDialog(null, ("User already in database."));
			return;
		}
		users.insertElementAt(newUser, -index);
	}
	static void removeUser(int ID){
		int index = 0;
		index = findUser(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, ("User not found"));
			return;
		}
		users.remove(index);
	}
	static void sellItem(int ID, int amount){
		int index;
		index = findItem(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, ("No item found with this ID"));
		}
		else{
			if (items.elementAt(index).getStock()-amount<0){
				JOptionPane.showMessageDialog(null, ("Not enough "+items.elementAt(index).getName()+" in stock. Current stock is " + items.elementAt(index).getStock()));
			}
			else{
				items.elementAt(index).setStock(items.elementAt(index).getStock()-amount);
			}
		}
	}
	static void changeItem(Item newItem){
		int index;
		index = findItem(newItem.getID());
		if (index<0){
			JOptionPane.showMessageDialog(null, ("No item found with this ID"));
		}
		else{
			items.setElementAt(newItem, index);
		}
	}
	static void changeUser(User newUser){
		int index;
		index = findItem(newUser.getID());
		if (index<0){
			JOptionPane.showMessageDialog(null, "No user found with this ID");
		}
		else{
			users.setElementAt(newUser, index);
		}
	}
	static void changeItemPrice(int ID, double newPrice){
		int index;
		index = findItem(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No item found with this ID");
		}
		else{
			if (newPrice<0){
				JOptionPane.showMessageDialog(null, "Error, no negative prices allowed");
			}
			else{
				items.elementAt(index).setPrice(newPrice);
			}
		}
	}
	static void changeItemName(int ID, String newName){
		int index;
		index = findItem(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No item found with this ID");
		}
		else{
			if (newName.equals("")){
				JOptionPane.showMessageDialog(null, "Error, input must not be empty");
			}
			else{
				items.elementAt(index).setName(newName);
			}
		}
	}
	static void changeItemStock(int ID, int newStock){
		int index;
		index = findItem(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No item found with this ID");
		}
		else{
			if (newStock<0){
				JOptionPane.showMessageDialog(null, "Error, no negative stock allowed");
			}
			else{
				items.elementAt(index).setStock(newStock);
			}
		}
	}
	static void changeUserName(int ID, String newFName, String newLName){
		int index;
		index = findUser(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No user found with this ID");
		}
		else{
			if (newFName.equals("")||newLName.equals("")){
				JOptionPane.showMessageDialog(null, "Error, input must not be empty");
			}
			else{
				users.elementAt(index).setFName(newFName);
				users.elementAt(index).setLName(newLName);
			}
		}
	}
	static void changeUserLevel(int ID,int newLevel){
		int index;
		index = findUser(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No user found with this ID");
		}
		else{
			if (newLevel<0){
				JOptionPane.showMessageDialog(null, "Level cannot be negative");
			}
			else{
				users.elementAt(index).setLevel(newLevel);
			}
		}
	}
	static void changeUserPassword(int ID,int newPassword){
		int index;
		index = findUser(ID);
		if (index<0){
			JOptionPane.showMessageDialog(null, "No user found with this ID.");
		}
		else{
			if (newPassword<0){
				JOptionPane.showMessageDialog(null, "Password cannot be negative");
			}
			else{
				users.elementAt(index).setPassword(newPassword);
			}
		}
	}
	static void writeItemDB(String fileName){
		try {
			PrintWriter itemsDBWriter = new PrintWriter(fileName);
			itemsDBWriter.println("items");
			for (int i=0;i<items.size();i++){
				itemsDBWriter.println(items.elementAt(i).getID() + " "+items.elementAt(i).getName()
						+ " " + items.elementAt(i).getPrice()+" "+items.elementAt(i).getStock());
			}
			itemsDBWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	static void writeUsersDB(String fileName){
		try {
			PrintWriter itemsDBWriter = new PrintWriter(fileName);
			itemsDBWriter.println("users");
			for (int i=0;i<users.size();i++){
				itemsDBWriter.println(users.elementAt(i).getID() + " "+users.elementAt(i).getFName()
						+ " " + users.elementAt(i).getLName()+" "+users.elementAt(i).getLevel()+" "+users.elementAt(i).getPassword());
			}
			itemsDBWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
}
