package Core;

import DB.DatabaseHandler;
public class Event {
	private String name;
	private String desc;
	private String start;
	private String end;
	private String roomID;
	
	Event(String name, String start, String end, String desc, String roomid){
		DatabaseHandler.addEvent(name, start, end, desc, roomid);
		this.name = name;
		this.start= start;
		this.end = end;
		this.desc = desc;
		this.roomID = roomid;
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		DatabaseHandler.updateEvent(name, this.start, this.end, this.desc,this.roomID);		
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		DatabaseHandler.updateEvent(this.name, this.start, this.end, desc,this.roomID);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		DatabaseHandler.updateEvent(this.name, start, this.end, desc,this.roomID);
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
		DatabaseHandler.updateEvent(this.name, this.start, end, this.desc,this.roomID);
	}
	
	

}
