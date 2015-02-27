package Core;
import java.sql.ResultSet;
import java.util.Date;

import DB.DatabaseHandler;
public class Event {
	private String name;
	private String desc;
	private Date start;
	private Date end;
	
	Event(String name, Date start, Date end, String desc){
		this.name = name;
		this.start= start;
		this.end = end;
		this.desc = desc;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	

}
