package Core;

public class Room implements CalendarOwner{
	private Calendar roomCal;
	private String roomID;
	
	public Calendar getCalendar() {
		return roomCal;
	}

	public String getPrimaryKey() {
		return roomID;
	}

}
