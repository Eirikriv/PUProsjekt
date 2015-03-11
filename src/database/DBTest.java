package database;

//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.ArrayList;

public class DBTest {
	public static void main(String[] args) throws SQLException {
//		PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
//		GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
//		RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
		EventDatabaseHandler edbh = new EventDatabaseHandler();
//		ArrayList<String> list = pdbh.get("martibni");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
//		ArrayList<String> rooms = rdbh.getAllRooms();		
//		for (int i = 0; i < rooms.size(); i++)
//			System.out.println(rooms.get(i));
		ArrayList<String> p = edbh.getAllParticipants("4");
		for (int i = 0; i < p.size(); i++)
			System.out.println(p.get(i));
//		Database.initializeDatabase();
//		pdbh.add(new String[]{"cecilite", "Cecilie Teisberg", "password"});
//		pdbh.add(new String[]{"eirikriv", "Eirik Rivedal", "password1"});
//		pdbh.add(new String[]{"niconiel", "Nicolaj Nielsen", "password2"});
//		pdbh.add(new String[]{"martibni", "Martin Børø Nilsen", "password3"});
//		pdbh.add(new String[]{"andersro", "Anders Rønold", "password4"});
//		String groupID = gdbh.add(new String[]{"PU-gruppe 58"});
//		gdbh.addGroupMember(groupID, "cecilite");
//		gdbh.addGroupMember(groupID, "eirikriv");
//		gdbh.addGroupMember(groupID, "niconiel");
//		gdbh.addGroupMember(groupID, "martibni");
//		gdbh.addGroupMember(groupID, "andersro");
//		groupID = gdbh.add(new String[]{"Gruppe"});
//		gdbh.addGroupMember(groupID, "cecilite");
//		rdbh.add(new String[]{"G022", "15", "Grupperom"});
//		String eventID = edbh.add(new String[]{"Sprintmøte", "cecilite", "2015-03-13 10:15", "2015-03-13 14:00", "Avslutte ukens sprint", "G022"});
//		edbh.addPerson(eventID, "cecilite");
//		edbh.addPerson(eventID, "eirikriv");
//		edbh.addPerson(eventID, "niconiel");
//		edbh.addPerson(eventID, "martibni");
//		edbh.addPerson(eventID, "andersro");
		
//		ArrayList<String> list = pdbh.getAllGroups("cecilite");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
//		
//		list = gdbh.getGroupMembers(""+1);
//		for(String string : list) {
//			System.out.println(string);
//		}
		
//		ArrayList<String> list = rdbh.getAvailableRooms("2015-03-02 11:00", "2015-03-02 13:00", "10");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
//		list = rdbh.getAvailableRooms("2015-03-02 10:00", "2015-03-02 12:00", "10");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}
	}
}
