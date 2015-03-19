package core;

public class Notification {
	private Event event;
	private Group group;
	private String message;
	
	public Notification(Event event, Group group, String message) {
		this.group = group;
		this.event = event;
		this.message = message;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Group getGroup() {
		return group;
	}
}
