package core;

import java.util.ArrayList;

import database.RoomDatabaseHandler;

public class Room implements CalendarOwner{
	private Calendar roomCal;
	private RoomDatabaseHandler rdbh;
	private String roomID;
	private String capacity;
	private String desc;
	
	public Room(String roomID) {
		roomCal = new Calendar(this);
		rdbh = new RoomDatabaseHandler();
		this.roomID = roomID;
		ArrayList<String> info = rdbh.get(roomID);
		capacity = info.get(0);
		desc = info.get(1);
	}
	
	public Room(String roomID, String capacity, String desc) {
		roomCal = new Calendar(this);
		rdbh = new RoomDatabaseHandler();
		this.roomID = roomID;
		this.capacity = capacity;
		this.desc = desc;
		rdbh.add(new String[]{roomID, capacity, desc});
	}
	
	public Calendar getCalendar() {
		return roomCal;
	}

	public String getPrimaryKey() {
		return roomID;
	}
	
	public String getCapacity() {
		return capacity;
	}
	
	public String getDescription() {
		return desc;
	}
}