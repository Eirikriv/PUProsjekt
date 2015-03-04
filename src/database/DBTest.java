package database;

//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBTest {
	public static void main(String[] args) throws SQLException {
		Database.initializeDatabase();
		PersonDatabaseHandler pdbh = new PersonDatabaseHandler();
		pdbh.add(new String[]{"cecilite", "Cecilie Teisberg", "password"});
		pdbh.add(new String[]{"eirikriv", "Eirik Rivedal", "password1"});
		pdbh.add(new String[]{"niconiel", "Nicolaj Nielsen", "password2"});
		pdbh.add(new String[]{"martibni", "Martin Børø Nilsen", "password3"});
		pdbh.add(new String[]{"andersro", "Anders Rønold", "password4"});
		GroupDatabaseHandler gdbh = new GroupDatabaseHandler();
		gdbh.add(new String[]{"PU-gruppe 58"});
		gdbh.addGroupMember("1", "cecilite");
		gdbh.addGroupMember("1", "eirikriv");
		gdbh.addGroupMember("1", "niconiel");
		gdbh.addGroupMember("1", "martibni");
		gdbh.addGroupMember("1", "andersro");
//		String groupID1 = gdbh.add(new String[]{"Gruppe"});
//		System.out.println(groupID + " " + groupID1);
//		gdbh.addGroupMember(groupID1, "cecilite");
		RoomDatabaseHandler rdbh = new RoomDatabaseHandler();
		rdbh.add(new String[]{"R E5-103", "20", "Grupperom"});
		EventDatabaseHandler edbh = new EventDatabaseHandler();
		edbh.add(new String[]{"PU-gruppemøte", "2015-03-02 12:15", "2015-03-02 14:00", "Fortsettelse av jobbing med kalendersystem", "R E5-103"});
		edbh.addPerson("1", "cecilite");
		edbh.addPerson("1", "eirikriv");
		edbh.addPerson("1", "niconiel");
		edbh.addPerson("1", "martibni");
		edbh.addPerson("1", "andersro");
		ArrayList<String> list = pdbh.getAllGroups("cecilite");
		System.out.println(list.size() == 0);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
