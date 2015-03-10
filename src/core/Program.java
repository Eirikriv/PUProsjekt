package core;

//import database.EventDatabaseHandler;
//import database.EventDatabaseHandler;
//import database.GroupDatabaseHandler;
import database.PersonDatabaseHandler;
//import database.RoomDatabaseHandler;

public class Program {
	private static PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
//	private static GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
//	private static EventDatabaseHandler edbh = new EventDatabaseHandler();
//	private static RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
	
	public static Person login(String username, String password) {
		String id = pdbh.login(username, password);
		Person p = new Person(id);
		return p;
	}
}