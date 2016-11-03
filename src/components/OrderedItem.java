package components;

import java.text.DecimalFormat;

public class OrderedItem {
	private int ID, quantity;
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public OrderedItem(int ID, int quantity){
		this.setID(ID);
		this.setQuantity(quantity);
	}
}
