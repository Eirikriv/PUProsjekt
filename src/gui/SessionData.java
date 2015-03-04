package gui;

import java.util.ArrayList;

import database.RoomDatabaseHandler;
import javafx.collections.ObservableList;

public class SessionData {
	RoomDatabaseHandler rdb = new RoomDatabaseHandler();
	
	public static String username;
	public static ObservableList<String> allMembers;
	public static ObservableList<String> allGroups;
	public static ObservableList<String> allRooms;
	
	public ArrayList<String> availableRooms(String start, String end, String capacity) {
		ArrayList<String> rooms = rdb.getAvailableRooms(start, end, capacity);
		return rooms;
	}
}
