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
	private CalendarOwner calOwner;
	
	public Calendar(CalendarOwner owner) {
		//make the calendar
		calOwner = owner;
		ArrayList<String> list = pdbh.getPersonEvents(owner.getPrimaryKey());
		ArrayList<Event> result = new ArrayList<Event>();
		for (int i=0; i<list.size() ; i++) {
			Event e = new Event(list.get(i));
			result.add(e);
		}
		this.calendar = result;
	}
	
	public ArrayList<Event> getEvents() {
		return this.calendar;
	}
	public void addEvent(String name, String start, String end, String desc, String roomId) {
		Event e = new Event(name, calOwner.getPrimaryKey(), start, end, desc, roomId);
		this.calendar.add(e);
	}

	
	public ArrayList<Event> updateCalendar() {
		ArrayList<String> list = null;
		if (Person.class.isInstance(calOwner))
			list = pdbh.getPersonEvents(calOwner.getPrimaryKey());
		else if (Group.class.isInstance(calOwner))
			list = gdbh.getGroupEvents(calOwner.getPrimaryKey());
		else if (Room.class.isInstance(calOwner))
			list = rdbh.getRoomEvents(calOwner.getPrimaryKey());
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