package core;


import java.util.ArrayList;

import database.PersonDatabaseHandler;

public class Person implements CalendarOwner{
	PersonDatabaseHandler pdbh;
	private Calendar cal;
	private String name;
	private String username;
	private String password;
	private String admin;
	
	public Person(String name, String username, String password, String admin) {
		pdbh = new PersonDatabaseHandler();
		this.name = name;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.cal = new Calendar(this);
		pdbh.add(new String[]{name, username, password,admin});
	}
	
	public Person(String name) {
		this.cal = new Calendar(this);
		ArrayList<String> list = pdbh.get(name);
		this.name = name;
		this.username = list.get(1);
		this.password = list.get(2);
		
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
		return this.pdbh.getAllGroups(this.username);
	}
	
	public ArrayList<String> getPersonEvents() {
		return this.
	}
	
	public Calendar getCalendar() {
		return cal;
	}
	
	public String getPrimaryKey() {
		return username;
	}

	public boolean isAdmin() {
		if(admin == "TRUE")
			return true;
		return false;
	}

	public void setAdmin(boolean admin) {
		if(admin)
			this.admin = "TRUE";
		this.admin = "FALSE";
	}
	
}