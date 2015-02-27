package Core;

import java.sql.ResultSet;

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
	
	public void setName() {
		DatabaseHandler.
	}
	public String getName() {
		return this.name;
	}
	
	
	
}
