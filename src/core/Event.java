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
	private ArrayList<CalendarOwner> participants = new ArrayList<CalendarOwner>();
	private ArrayList<CalendarOwner> declined = new ArrayList<CalendarOwner>(); 
	
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
		ArrayList<String> p = edbh.getAllParticipants(eventID);
		for (int i = 0; i < p.size(); i++)
			participants.add(new Person(p.get(i)));
		ArrayList<String> d = edbh.getAllDeclined(eventID);
		for (int i = 0; i < d.size(); i++)
			declined.add(new Person(d.get(i)));
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

	public void setDesc(String desc) {
		this.desc = desc;
		edbh.update(new String[]{this.name, this.start, this.end, desc,this.roomID});
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		edbh.update(new String[]{this.name, start, this.end, desc,this.roomID});
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
		edbh.update(new String[]{this.name, this.start, end, this.desc,this.roomID});
	}
	
	public String getRoom() {
		return roomID;
	}
	
	public ArrayList<CalendarOwner> getParticipants() {
		return participants;
	}
	
	public ArrayList<CalendarOwner> getDeclined() {
		return declined;
	}
}