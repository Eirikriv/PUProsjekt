package Core;
import java.sql.ResultSet;
import java.util.Date;

import DB.Database;
import DB.DatabaseHandler;
public class Event {
	private String name;
	private String desc;
	private Date start;
	private Date end;
	private int roomID;
	
	Event(String name, Date start, Date end, String desc){
		ResultSet rs = DatabaseHandler.createEvent(name, start, end, desc, roomID);
		this.name = name;
		this.start= start;
		this.end = end;
		this.desc = desc;
		this.roomID = roomID;
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		DatabaseHandler.updateEvent(name);		
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
		DatabaseHandler.updateEvent(desc);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
		DatabaseHandler.updateEvent(start);
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
		DatabaseHandler.updateEvent(end);
	}
	
	

}
