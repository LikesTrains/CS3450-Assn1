package components;

public class User {
	private int level;
	private String FName, LName;
	private int ID;
	private int password;
	
	User(int level, String FName, String LName, int ID, int password){
		this.setLevel(level);
		this.setFName(FName);
		this.setLName(LName);
		this.setID(ID);
		this.setPassword(password);
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}	
	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}	
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}	
}
