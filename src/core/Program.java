package core;

import java.util.ArrayList;


//import database.EventDatabaseHandler;
//import database.EventDatabaseHandler;
//import database.GroupDatabaseHandler;
import database.PersonDatabaseHandler;
//import database.RoomDatabaseHandler;
import database.RoomDatabaseHandler;

public class Program {
	private static PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
//	private static GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
//	private static EventDatabaseHandler edbh = new EventDatabaseHandler();
	private static RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
	
	public static Person login(String username, String password) {
		String id = pdbh.login(username, password);
		Person p = new Person(id);
		return p;
	}
	
	public static ArrayList<Room> getAllRooms(){
		ArrayList<String> roomIDs = rdbh.getAllRooms();
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i < roomIDs.size(); i++) {
			Room r = new Room(roomIDs.get(i));
			rooms.add(r);
		}
		return rooms;
	}
}