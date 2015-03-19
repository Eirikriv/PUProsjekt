package gui;

import java.util.ArrayList;
import java.util.Calendar;

import core.Event;
import core.Notification;
import core.Person;
import database.RoomDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SessionData {
	public static String username;
	public static ObservableList<String> allMembers;
	public static ObservableList<String> allGroups;
	public static ObservableList<String> allRooms;
	public static String currentWeek;
	public static String message;
	public static ArrayList<Event> allEvents;
	public static ArrayList<Event> allVisibleEvents;
	public static Person person;
	public static Calendar cal;
	public static String id;
	public static String prevScreen;
	public static boolean eventTab;
	public static boolean nTab;
	public static ArrayList<Notification> allNotifications;
	public static ArrayList<Object> eventInfo;
	public static core.Group group;
	public static boolean gTab;
	
	public static ObservableList<String> availableRooms(String start, String end, String capacity) {
		RoomDatabaseHandler rdb = new RoomDatabaseHandler();
		ArrayList<String> rooms = rdb.getAvailableRooms(start, end, capacity);
		if (rooms == null) {
			ObservableList<String> room = FXCollections.observableArrayList(new ArrayList<String>());
			return room;
		} else {
			ObservableList<String> room = FXCollections.observableArrayList(rooms);
			return room;
		}
		
	}
}
