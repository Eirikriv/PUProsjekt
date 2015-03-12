package core;

import java.util.ArrayList;
import database.PersonDatabaseHandler;

public class Person implements CalendarOwner{
	PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
	private Calendar cal;
	private String name;
	private String username;
	private String password;
	private String admin;
	
	public Person(String name, String username, String password, String admin) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.cal = new Calendar(this);
		pdbh.add(new String[]{name, username, password,admin});
	}
	
	public Person(String username) {
		this.username = username;
		ArrayList<String> list = pdbh.get(username);
		name = list.get(0);
		password = list.get(1);
		admin = list.get(2);
		cal = new Calendar(this);
	}
	
	public void updatePassword(String password) {
		pdbh.update(new String[]{this.name, this.username, password});
		this.password = password;
	}
	
	public void setName(String name) {
		pdbh.update(new String[]{name, this.username, this.password});
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<String> getAllGroups() {
		return this.pdbh.getAllGroups(username);
	}

	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getPrimaryKey() {
		return username;
	}

	public boolean isAdmin() {
		if(admin.equals("1")) {
			return true;			
		}
		return false;
	}

	public void setAdmin(boolean admin) {
		if(admin)
			this.admin = "TRUE";
		this.admin = "FALSE";
	}
	
	public static void main(String[] args) {
		Person p = new Person("anders", "a", "b", "0");
		System.out.println(p.getName());
		
	}
	
}