package core;

import java.util.ArrayList;

import database.GroupDatabaseHandler;

public class Group implements CalendarOwner {
	private String groupID;
	private String groupName;
	private Calendar groupCal;
	private ArrayList<String> members;
	GroupDatabaseHandler gdbh;
	
	public Group(String groupID, String groupName) {
		gdbh = new GroupDatabaseHandler();
		this.groupName = groupName;
		this.groupCal = new Calendar(this);
		this.groupID=gdbh.add(new String[]{groupID});
	}
	public Calendar getCalendar() {
		return groupCal;
	}
	public String getPrimaryKey() {
		return groupID;
	}
	public String getName() {
		return groupName;
	}
	public void addMember(String username){
		members.add(username);//legg at db oppdaterer 
		gdbh.addGroupMember(groupID, username);
	}
	public void removeMember(String username){
		if(members.contains(username)){
			members.remove(username);
			gdbh.removeGroupMember(this.groupID, username);
			}
	}
	
}	
