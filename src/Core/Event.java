package Core;

public class Event {
	private String name;
	private String desc;
	private String start;
	private String end;
	
	Event(String Name,String Desc,String Start,String end){
		this.name=Name;
		this.desc=Desc;
		this.start = Start;
		this.end = end;
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
