package core;

public class Notification {
	private Event event;
	private String message;
	
	public Notification(Event event, String message) {
		this.event = event;
		this.message = message;
	}
	
	public Event getEvent() {
		return event;
	}
	
	public String getMessage() {
		return message;
	}
}
