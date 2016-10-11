package components;

import javax.swing.JOptionPane;

public class Item {
	private int ID, stock;
	private double price;
	private String name;
	
	Item(int ID, String name, double price, int stock){
		this.ID = ID;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	public void printItem(){
		JOptionPane.showMessageDialog(null, this.ID+". "+this.name +", $"+this.price+", "+this.stock+".");
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
