package core;


import java.util.ArrayList;

import database.GroupDatabaseHandler;
import database.PersonDatabaseHandler;
import database.RoomDatabaseHandler;

public class Calendar {
	private PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
	private GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
	private RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
	private ArrayList<Event> calendar = new ArrayList<Event>();
	
	public Calendar(CalendarOwner owner) {
		//make the calendar
		ArrayList<String> list = pdbh.getPersonEvents(owner.getPrimaryKey());
		ArrayList<Event> result = new ArrayList<Event>();
		for (int i=0; i<list.size() ; i++) {
			Event e = new Event(list.get(i));
			result.add(e);
		}
		this.calendar = result;
	}
	
	public void addEvent(String owner, String name, String start, String end, String desc, String roomId) {
		Event e = new Event(name, owner, start, end, desc, roomId);
		this.calendar.add(e);
	}

	
	public ArrayList<Event> updateCalendar(CalendarOwner owner) {
		ArrayList<String> list = null;
		if (Person.class.isInstance(owner))
			list = pdbh.getPersonEvents(owner.getPrimaryKey());
		else if (Group.class.isInstance(owner))
			list = gdbh.getGroupEvents(owner.getPrimaryKey());
		else if (Room.class.isInstance(owner))
			list = rdbh.getRoomEvents(owner.getPrimaryKey());
		else
			throw new IllegalArgumentException("Invalid CalendarOwner object");
		ArrayList<Event> result = new ArrayList<Event>();
		for (int i=0; i<list.size() ; i++) {
			Event e = new Event(list.get(i));
			result.add(e);
		}
		this.calendar = result;
		return calendar;
	}
		
}