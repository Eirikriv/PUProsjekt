package core;

public class Group implements CalendarOwner {
	private String groupID;
	private String groupName;
	private Calendar groupCal;

	public Calendar getCalendar() {
		return groupCal;
	}

	public String getPrimaryKey() {
		return groupID;
	}
	
	public String getName() {
		return groupName;
	}
}