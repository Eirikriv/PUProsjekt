package DB;

import java.util.ArrayList;

public class EventDatabaseHandler implements DatabaseHandler {

	@Override
	public ArrayList<String> get(String primaryKey) {
		return null;
	}
	
	//Tar inn en liste på formen [name, start, end, desc, roomID]
	public boolean add(String[] eventInfo) {
		try {
			Database.makeStatement("INSERT INTO Event\n"
						+ "VALUES( '"+eventInfo[0]+"', '"+ eventInfo[1] +"', '"+ eventInfo[2] +"', '"+ eventInfo[3] +"', '"+ eventInfo[4] +"');");
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
	
	//Tar inn en liste på formen [name, start, end, desc, roomID]
	public boolean update(String[] eventInfo) {
		try {
			Database.makeStatement("UPDATE Event\n"
								 + "SET name = '"+ eventInfo[0]+", start = '"+ eventInfo[1] + "', end = '"+eventInfo[2] +"', desc = '"+ eventInfo[3]+ "', roomID = '"+eventInfo[4]+"\n"
								 + "WHERE name = '"+eventInfo[0]+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	
	@Override
	public boolean remove(String primaryKey) {
		try {
			Database.makeStatement("DELETE FROM Event"
								+  "WHERE EventID = '"+primaryKey+"';");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}

}
