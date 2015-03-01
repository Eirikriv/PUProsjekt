package Core;


import DB.EventDatabaseHandler;
public class Event {
	private EventDatabaseHandler edbh;
	private String name;
	private String desc;
	private String start;
	private String end;
	private String roomID;
	
	Event(String name, String start, String end, String desc, String roomid){
		edbh.add(new String[]{name, start, end, desc, roomid});
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
