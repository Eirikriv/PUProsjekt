package database;

//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBTest {
	public static void main(String[] args) throws SQLException {
//		Database.initializeDatabase();
//		PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
//		pdbh.add(new String[]{"cecilite", "Cecilie Teisberg", "password"});
//		pdbh.add(new String[]{"eirikriv", "Eirik Rivedal", "password1"});
//		pdbh.add(new String[]{"niconiel", "Nicolaj Nielsen", "password2"});
//		pdbh.add(new String[]{"martibni", "Martin Børø Nilsen", "password3"});
//		pdbh.add(new String[]{"andersro", "Anders Rønold", "password4"});
//		GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
//		String groupID = gdbh.add(new String[]{"PU-gruppe 58"});
//		gdbh.addGroupMember(groupID, "cecilite");
//		gdbh.addGroupMember(groupID, "eirikriv");
//		gdbh.addGroupMember(groupID, "niconiel");
//		gdbh.addGroupMember(groupID, "martibni");
//		gdbh.addGroupMember(groupID, "andersro");
//		groupID = gdbh.add(new String[]{"Gruppe"});
//		gdbh.addGroupMember(groupID, "cecilite");
		RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
//		rdbh.add(new String[]{"R E5-103", "20", "Grupperom"});
//		EventDatabaseHandler edbh = new EventDatabaseHandler();
//		String eventID = edbh.add(new String[]{"PU-gruppemøte", "2015-03-02 12:15", "2015-03-02 14:00", "Fortsettelse av jobbing med kalendersystem", "R E5-103"});
//		edbh.addPerson(eventID, "cecilite");
//		edbh.addPerson(eventID, "eirikriv");
//		edbh.addPerson(eventID, "niconiel");
//		edbh.addPerson(eventID, "martibni");
//		edbh.addPerson(eventID, "andersro");
//		ArrayList<String> list = pdbh.getAllGroups("cecilite");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
		
		
		ArrayList<String> list = rdbh.getAvailableRooms("2015-03-02 11:00", "2015-03-02 13:00", "10");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		list = rdbh.getAvailableRooms("2015-03-02 10:00", "2015-03-02 12:00", "10");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
