package core;


import java.util.ArrayList;

import database.EventDatabaseHandler;
public class Event {
	private EventDatabaseHandler edbh;
	private String eventID;
	private String owner;
	private String name;
	private String desc;
	private String start;
	private String end;
	private String roomID;
	
	Event(String title, String start, String end, String desc, String roomid){
		String eventID = edbh.add(new String[]{title, start, end, desc, roomid});
		this.eventID = eventID;
		this.name = title;
   		this.start= start;
		this.end = end;
		this.desc = desc;
		this.roomID = roomid;	
	}
	Event(String eventID) {
		this.eventID = eventID;
		ArrayList<String> eventInfo = edbh.get(eventID);
		name = eventInfo.get(0);
		owner = eventInfo.get(1);
		start = eventInfo.get(2);
		end = eventInfo.get(3);
		desc = eventInfo.get(4);
		roomID = eventInfo.get(5);
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
	
	

}