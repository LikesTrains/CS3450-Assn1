package components;

import java.util.Date;
import java.util.Vector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
	private Date date;
	private Vector <OrderedItem> items;
	private int cashierID;
	private int ID;
	
	public String getDate() {
		return date.getYear()+" "+date.getMonth()+" "+date.getDay();
	}
	public String getItems(){
		String returning=new String();
		for (int i=0;i<items.size();){
			returning+=items.elementAt(i).getID()+" "+items.elementAt(i).getQuantity();
			i++;
			if (i!=items.size()){
				returning+=",";
			}
		}
		return returning;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCashier() {
		return cashierID;
	}
	public void setCashier(int cashier) {
		this.cashierID = cashier;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}	
	public Order(int orderID, int cashierID, Date saleDate, Vector <OrderedItem> initItems){
		this.ID = orderID;
		this.cashierID = cashierID;
		this.items = new Vector <OrderedItem> (initItems.size());
		this.items = initItems;
		this.date = saleDate;
	}
	public String printOrder(){
		return "";
	}
}
