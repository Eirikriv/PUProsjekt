package core;


import java.util.ArrayList;

import database.EventDatabaseHandler;
public class Event {
	private EventDatabaseHandler edbh = new EventDatabaseHandler();
	private String eventID;
	private String owner;
	private String name;
	private String desc;
	private String start;
	private String end;
	private String roomID;
	
	public Event(String title, String owner, String start, String end, String desc, String roomid){
		String eventID = edbh.add(new String[]{title, owner, start, end, desc, roomid});
		this.eventID = eventID;
		this.name = title;
		this.owner = owner;
   		this.start= start;
		this.end = end;
		this.desc = desc;
		this.roomID = roomid;
	}
	public Event(String eventID) {
		this.eventID = eventID;
		ArrayList<String> eventInfo = edbh.get(eventID);
		name = eventInfo.get(0);
		owner = eventInfo.get(1);
		start = eventInfo.get(2);
		end = eventInfo.get(3);
		desc = eventInfo.get(4);
		roomID = eventInfo.get(5);
	}
	
	public void addParticipant(CalendarOwner owner) {
		
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEventID() {
		return eventID;
	}

	public void setName(String name) {
		this.name = name;
		edbh.update(new String[]{name, this.start, this.end, this.desc,this.roomID});		
	}

	public String getDesc() {
		return desc;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	public String getRoom() {
		return roomID;
	}
	
	public void editEvent(String title, String start, String end, String desc, String roomID) {
		this.name = title;
		this.start = start;
		this.end = end;
		this.desc = desc;
		this.roomID = roomID;
		edbh.update(new String[] {title, start, end, desc, roomID});
	}
	
	public ArrayList<String> getInvited() {
		return edbh.getAllInvited(eventID);
	}
	
	public ArrayList<String> getParticipants() {
		return edbh.getAllParticipants(eventID);
	}
	
	public ArrayList<String> getDeclined() {
		return edbh.getAllDeclined(eventID);
	}
}