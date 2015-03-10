package gui;

import java.util.ArrayList;
import java.util.Calendar;

import core.Event;
import core.Person;
import database.RoomDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SessionData {
	static RoomDatabaseHandler rdb = new RoomDatabaseHandler();
	
	public static String username;
	public static ObservableList<String> allMembers;
	public static ObservableList<String> allGroups;
	public static ObservableList<String> allRooms;
	public static ObservableList<GroupData> allGroupInfo;
	public static String currentWeek;
	public static String message;
	public static ArrayList<Event> allEvents;
	public static Person person;
	public static Calendar cal;
	
	public static ObservableList<String> availableRooms(String start, String end, String capacity) {
		System.out.println("" + start + end + capacity);
		ArrayList<String> rooms = rdb.getAvailableRooms(start, end, capacity);
		ObservableList<String> room = FXCollections.observableArrayList(rooms);
		System.out.println(room.size());
		return room;
	}
}
