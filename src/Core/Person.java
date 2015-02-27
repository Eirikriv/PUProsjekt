package Core;


import java.util.ArrayList;

import DB.DatabaseHandler;

public class Person {
	
	private Calendar cal;
	private String name;
	private String username;
	private String password;
	
	
	public Person(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.cal = new Calendar(this.name);
		DatabaseHandler.addPerson(name, username, password);
	}
	
	public Person(String name) {
		this.cal = new Calendar(this.name);
		ArrayList<String> list = DatabaseHandler.getPersonInformation(name);
		this.name = name;
		this.username = list.get(1);
		this.password = list.get(2);
	}
	
	public void updatePassword(String password) {
		DatabaseHandler.updatePerson(this.name, this.username, password);
		this.password = password;
	}
	
	public void setName(String name) {
		DatabaseHandler.updatePerson(name, this.username, this.password);
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	
	
}
