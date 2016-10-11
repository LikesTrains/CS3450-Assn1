package components;

import java.text.DecimalFormat;

public class PurchasedItem {
	private int ID, quantity;
	private double price;
	private String name;
	
	public PurchasedItem(int ID, int quantity, double price, String name){
		this.setID(ID);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setName(name);
	}
	
	public String printItem(){
		DecimalFormat df = new DecimalFormat("#,###,###.00");
		String totalPrice;
		totalPrice = df.format(quantity*price);
		
		return (name+": "+price+" x"+quantity+" = $" + totalPrice+".\n");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
