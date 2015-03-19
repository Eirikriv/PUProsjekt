package core;
import java.util.ArrayList;
import database.EventDatabaseHandler;
import database.PersonDatabaseHandler;


public class Person implements CalendarOwner{
	PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
	private Calendar cal;
	private String name;
	private String username;
	private String password;
	private String admin;
	
	public Person(String name, String username, String password, String admin) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.cal = new Calendar(this);
		pdbh.add(new String[]{name, username, password,admin});
	}
	
	public Person(String username) {
		this.username = username;
		ArrayList<String> list = pdbh.get(username);
		name = list.get(0);
		password = list.get(1);
		admin = list.get(2);
		cal = new Calendar(this);
	}
	
	public void updatePassword(String password) {
		pdbh.update(new String[]{this.name, this.username, password});
		this.password = password;
	}
	
	public void setName(String name) {
		pdbh.update(new String[]{name, this.username, this.password});
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<String> getAllGroups() {
		return this.pdbh.getAllGroups(username);
	}

	public Calendar getCalendar() {
		return cal;
	}
	
	public String getPrimaryKey() {
		return username;
	}

	public boolean isAdmin() {
		if(admin.equals("1")) {
			return true;			
		}
		return false;
	}

	public void setAdmin(boolean admin) {
		if(admin)
			this.admin = "TRUE";
		this.admin = "FALSE";
	}
	
	public ArrayList<Notification> getNotifications() {
		ArrayList<String> n = pdbh.getNotifications(this.username);
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		for (int i = 0; i < n.size(); i++) {
			String[] temp = n.get(i).split(":");
			notifications.add(new Notification(new Event(temp[0]), temp[1]));
		}
		return notifications;
	}
	

	public void makeAlarm() {
		EventDatabaseHandler d = new EventDatabaseHandler();
		d.eventStartsSoon(this.username);
	}
	
	public boolean hasDeclined(String eventID) {
		if (pdbh.accepted(username, eventID) == -1)
			return true;
		return false;
	}
	
	public boolean hasAccepted(String eventID) {
		if (pdbh.accepted(username, eventID) == 1)
			return true;
		return false;
	}
	
	public void acceptInvitation(String eventID) {
		pdbh.answerInvitation(username, eventID, 1);
		pdbh.isNotifiedOfEvent(username, eventID);
	}
	
	public void declineInvitation(String eventID) {
		pdbh.answerInvitation(username, eventID, -1);
		pdbh.isNotifiedOfEvent(username, eventID);
	}
	
	public void changeVisibility(String eventID, int visibility) {
		pdbh.changeVisibility(username, eventID, visibility);
	}
	
	public boolean isVisible(String eventID) {
		int visibility = pdbh.isVisible(username, eventID);
		if (visibility == 1) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Person p = new Person("martibni");
		p.makeAlarm();
	}
	
	public void isNotifiedOfEvent(String eventID) {
		pdbh.isNotifiedOfEvent(username, eventID);
	}
	
	public void isNotifiedofGroup(String groupID) {
		pdbh.isNotifiedOfGroup(username, groupID);
	}
}