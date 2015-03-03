package database;

import java.util.ArrayList;

public interface DatabaseHandler {
	public ArrayList<String> get(String primaryKey);
	public String add(String[] info);
	public boolean update(String[] info);
	public boolean remove(String primaryKey);
}
