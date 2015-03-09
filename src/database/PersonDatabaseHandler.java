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
			Database.makeStatement("INSERT INTO Person(Username, Name, Admin) "
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
	//Returnerer en liste på formen [[title, description, start, end], ...] 
	public ArrayList<ArrayList<String>> getPersonEvents(String username) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {
			String query = "SELECT Event.Title, Event.Description, Event.Start, Event.End\n"
						+  "FROM Person, Event, PersonEvent\n"
						+  "WHERE Person.Username = PersonEvent.Username\n"
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
	
	//Returnerer en liste på formen [username, name, password, admin]
	public String login(String username, String password) {
		String username1 = "";
		try {
			String query = "SELECT Username\n"
					+ "FROM Person\n"
					+ "WHERE Username = '" + username + "'\n"
						+ "AND Password = '" + password + "';";
			ResultSet rs = Database.makeQuery(query);
			int x = 0;
			while (rs.next()) {
				if (x > 0)
					throw new IllegalStateException("Multiple users found...");
				username1 = rs.getString(1);
				x++;
			}
			if (username1.length() == 0)
				throw new IllegalStateException("User not found in database");
			return username1;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}
	
	public ArrayList<String> getAllGroups(String username) {
		ArrayList<String> personInfo = new ArrayList<String>();
		try {
			String query = "SELECT Groups.Name, Groups.GroupID\n"
					+ "FROM Groups, PersonInGroup\n"
					+ "WHERE PersonInGroup.GroupID = Groups.GroupID\n"
					+ "AND PersonInGroup.Username = '" + username + "';";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				personInfo.add(rs.getString(2)+":"+rs.getString(1));

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
			String query = "SELECT Username, Name\n"
						+  "FROM Person;";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				list.add(rs.getString(1) + "<" + rs.getString(2) + ">");
			}
			return list;
		}
			
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something went wrong");
		}
	}

	public ArrayList<String> getNotifications(String username) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "SELECT EventID FROM Event, PersonEvent, Person "
					+ "WHERE Event.EventID = PersonEvent.EventID AND Person.PersonID = PersonEvent.PersonID AND "
					+ "PersonID = '" + username + "' AND Notified = FALSE";
			ResultSet rs = Database.makeQuery(query);
			while(rs.next()) {
				list.add("" + rs.getInt(1));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public void isNotified(String username) {
		try {
			String statement = "UPDATE Person SET Notified = TRUE WHERE Username = '" + username + "'";
			Database.makeStatement(statement);
		} catch(Exception e) {
			throw new IllegalArgumentException();
		}
	}
}
