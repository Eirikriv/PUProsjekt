package gui;

import java.util.ArrayList;

import database.RoomDatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SessionData {
	static RoomDatabaseHandler rdb = new RoomDatabaseHandler();
	
	public static String username;
	public static ObservableList<String> allMembers;
	public static ObservableList<String> allGroups;
	public static ObservableList<String> allRooms;
	public static String currentWeek;
	
	public static ObservableList<String> availableRooms(String start, String end, String capacity) {
		System.out.println("" + start + end + capacity);
		ArrayList<String> rooms = rdb.getAvailableRooms(start, end, capacity);
		ObservableList<String> room = FXCollections.observableArrayList(rooms);
		System.out.println(room.size());
		return room;
	}
}
