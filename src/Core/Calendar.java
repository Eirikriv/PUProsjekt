package Core;


import java.sql.SQLException;
import java.util.ArrayList;


import DB.DatabaseHandler;

public class Calendar {
	private String name;
	ArrayList<Event> calendar = new ArrayList<Event>();
	
	public Calendar(String name) {
		this.name = name;
	}
	
	public void addEvent(String name, String start, String end, String desc, String roomId) {
		Event e = new Event(name, start, end, desc, roomId);
		this.calendar.add(e);
	}
	
	public ArrayList<Event> outputPersonEvents() throws SQLException {
		ArrayList<ArrayList<String>> list = DatabaseHandler.getPersonEvents(name);
		ArrayList<Event> result = new ArrayList<Event>();
		for (int i=0; i<list.size() ; i++) {
				String name = list.get(i).get(0);
				String start = list.get(i).get(1);
				String end = list.get(i).get(2);
				String desc = list.get(i).get(3);
				String id = list.get(i).get(4);
				
				Event e = new Event(name, start, end, desc, id);
				result.add(e);
			}
		return result;
		
		}
		
}
	

