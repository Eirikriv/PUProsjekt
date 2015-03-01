package Core;


import java.util.ArrayList;

import DB.GroupDatabaseHandler;
import DB.PersonDatabaseHandler;

public class Calendar {
	private PersonDatabaseHandler pdbh;
	@SuppressWarnings("unused")
	private GroupDatabaseHandler gdbh;
	private ArrayList<Event> calendar = new ArrayList<Event>();
	
	public Calendar(Person owner) {
		//make the calendar
		ArrayList<ArrayList<String>> list = pdbh.getPersonEvents(owner.getName());
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
		this.calendar = result;
	}
	
	public void addEvent(String name, String start, String end, String desc, String roomId) {
		Event e = new Event(name, start, end, desc, roomId);
		this.calendar.add(e);
	}

	
	public ArrayList<Event> getCalendar() {
		return calendar;
	}
	
	public void updateCalendar(Person owner) {
		ArrayList<ArrayList<String>> list = pdbh.getPersonEvents(owner.getName());
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
		this.calendar = result;
	}
		
}
	

