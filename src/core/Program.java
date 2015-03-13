package core;

import java.util.ArrayList;

import database.GroupDatabaseHandler;
import database.PersonDatabaseHandler;
import database.RoomDatabaseHandler;

public class Program {
	private static PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
    private static GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
//    private static EventDatabaseHandler edbh = new EventDatabaseHandler();
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
	
	public static ArrayList<String> getAllGroups(String username) {
		ArrayList<String> list = pdbh.getAllGroups(username);
		return list;
		}
	
	public static ArrayList<Person> getAllPersons() {
		ArrayList<String> persons = pdbh.getAllPersons();
		ArrayList<Person> allPersons = new ArrayList<Person>();
		for (int i = 0; i < persons.size(); i++) {
			Person p = new Person(persons.get(i).split("<")[0]);
			allPersons.add(p);
		}
		return allPersons;
	}
	
	public static void removePerson(String username) {
		pdbh.remove(username);
	}
	
	public static void removeGroup(String groupID) {
		gdbh.remove(groupID);
	}
	
	public static void main(String[] args) {
		System.out.println(login("martibni", "password3"));
	}
}