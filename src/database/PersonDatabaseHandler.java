package database;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PersonDatabaseHandler implements DatabaseHandler {
		
	//Henter ut all info om person med PersonID id
	public ArrayList<String> get(String username) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT * FROM Person WHERE Person.Username = " + username + "";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				for (int i = 2; i <= 4; i++)
					list.add(rs.getString(i));
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Username " + username + " does not exist.");
		}
	}
	
	//Legger til person med name, username og pw
	//Returnerer PersonID til personen eller -1 hvis den ikke finnes (altså ikke ble lagt til)
	public String add(String[] personInfo) {
		try {
			Database.makeStatement("INSERT INTO Person "
						+ "VALUES('"+ personInfo[0] +"', '"+ personInfo[1] +"', '"+ personInfo[2] +"');");
			return personInfo[0];
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	//Tar inn en liste på formen [username, name, password]
	public boolean update(String[] info) {
		try {
			Database.makeStatement("UPDATE Person\n"
								 + "SET name = '"+ info[1]+", username = '"+ info[0] + "', password = '"+info[2]+"'\n"
								 + "WHERE username = '"+info[0]+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}

	@Override
	public boolean remove(String primaryKey) {
		try {
			Database.makeStatement("DELETE FROM Person\n"
								+  "WHERE name = '"+primaryKey+"';");
		return true;
		}
		catch (Exception e){
			return false;
		}
	}

	//Henter ut alle hendelser for person med PersonID id
	//Returnerer en liste på formen [[name, description, start, end], ...] 
	public ArrayList<ArrayList<String>> getPersonEvents(String username) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT Event.Name, Event.Description, Event.Start, Event.End\n"
						+  "FROM Person, Event, PersonEvent\n"
						+  "WHERE Person.PersonID = PersonEvent.PersonID\n"
						+  "AND Event.EventID = PersonEvent.EventID\n"
						+  "AND Person.Username = '" + username + "'\n"
						+  "ORDER BY Event.Start";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				ArrayList<String> temp = new ArrayList<String>();
				for (int i = 1; i <= 4; i++)
					temp.add(rs.getString(i));
				list.add(temp);
			}
			return list;
		}
		catch(Exception e) {
			throw new IllegalArgumentException("User " + username + " does not exist.");
		}
	}
	
	public ArrayList<String> login(String username, String password) {
		ArrayList<String> personInfo = new ArrayList<String>();
		try {
			String query = "SELECT username, name, password\n"
					+ "FROM Person\n"
					+ "WHERE Username = '" + username + "'\n"
						+ "AND Password = '" + password + "';";
			ResultSet rs = Database.makeQuery(query);
			while (rs.next()) {
				for (int i = 1; i <= 3; i++) {
					personInfo.add(rs.getString(i));
				}
			}
			if (personInfo.size() == 0)
				throw new IllegalStateException("User not found in database");
			if (personInfo.size() > 3)
				throw new IllegalStateException("More than one user found...");
			return personInfo;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}
	
	public ArrayList<String> getAllGroups(String username) {
		ArrayList<String> personInfo = new ArrayList<String>();
		try {
			String query = "SELECT Groups.Name\n"
					+ "FROM Groups, PersonInGroup\n"
					+ "WHERE PersonInGroup.GroupID = Groups.GroupID\n"
					+ "AND PersonInGroup.Username = '" + username + "';";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				personInfo.add(rs.getString(1));
			}
			return personInfo;
		}
			
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}
	
	public ArrayList<String> getAllPersons() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT Person.Name\n"
						+  "FROM Person;";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		}
			
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}

}
