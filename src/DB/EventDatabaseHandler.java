package DB;

import java.util.ArrayList;

public class EventDatabaseHandler implements DatabaseHandler {

	@Override
	public ArrayList<String> get(String primaryKey) {
		// TODO Auto-generated method stub
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
		Database.makeStatement("");
		return true;
		}
		catch (Exception e){
		return false;
		}
	}
	
	@Override
	public boolean remove(String primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}
