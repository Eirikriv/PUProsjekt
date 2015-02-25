package DB;

public class DBTest {
	public static void main(String[] args) {
		Database.initializeDatabase();
		String query = "SELECT Person.Name, Event.Name, Start, End, Description\n"
				+ "FROM Person, HasEvent, Event\n"
				+ "WHERE Person.ID = HasEvent.OwnerID AND Event.EventID = HasEvent.EventID\n"
				+ "GROUP BY	Person.Name;";
		System.out.println(Database.doQuery(query));
		query = "SELECT	Group.Name, Person.Name\n"
				+ "FROM Group, PersonInGroup, Person\n"
				+ "WHERE Group.GroupID = PersonInGroup.GroupID AND\n"
				+ "Person.PersonID = PersonInGroup.PersonID\n"
				+ "GROUP BY	Group.Name;";
		System.out.println(Database.doQuery(query));
		query = "SELECT	Person.Name, Event.Name, Start, End, Description\n"
				+ "FROM Group, OwnerEvent, Event, PersonInGroup, Person\n"
				+ "WHERE Group.OwnerID = OwnerEvent.OwnerID AND\n"
				+ "Event.EventID = OwnerEvent.EventID AND\n"
				+ "Person.ID = PersonGroup.ID AND Group.ID = PersonGroup.ID\n"
				+ "GROUP BY	Person.Name;";
		System.out.println(Database.doQuery(query));
		query = "SELECT	Event.Name, RoomID, Capacity, Room.Descritpiton\n"
				+ "FROM Event, Room\n"
				+ "WHERE Event.RoomID = Room.RoomID;";
		System.out.println(Database.doQuery(query));
	}
}
