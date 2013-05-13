package com.minecraft.app;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * @author Lucas Stuyvesant
 * @author Andrea Sanchez 
 * Contains the SQlite database and helper methods for interacting with the database 
 */
public class DataHandler {

	private static final String DATABASE_NAME = "book_and_quill.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOCATION_TABLE = "location";    
    private Context context;
    private SQLiteDatabase db;

    /**
     * Builds a database, or accesses an existing one given a context
     * @param context
     */
    public DataHandler(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
    }
    
    /**
     * Adds a new Location to database
     * @param name
     * @param x
     * @param y
     * @param z
     */
    public void insertLocation(String name, Long x, Long y, Long z) {
    	String sql = "INSERT INTO " + LOCATION_TABLE + "(name, x_coord, y_coord, z_coord) values(\'" + name + "\', " + x + ", " + y + ", " + z +")";
    	db.execSQL(sql);		
    }
    
    /**
     * Deletes a row with the given name
     * @param delName
     */
    public void deleteItem(String delName) {
    	String[]row = new String[] {String.valueOf(delName)};
    	this.db.delete(LOCATION_TABLE, "name = ?", row);
    }
    
    /**
     * Deletes all rows in the table
     */
    public void deleteAll() {
    	this.db.delete(LOCATION_TABLE, null, null);
    }
    
    /**
     * Closes database
     */
	public void close() {
		this.db.close();
	}
	
	/**
	 * Gets each row in the database and returns them as a list of Locations
	 * @return list of Locations containing database data
	 */
    public List<Location> getLocations() {
    	
		List<Location> locations = new ArrayList<Location>();
		Location location = null;
		
		//build query for join
		String sql = "select location.* from location ";
		Cursor cursor = this.db.rawQuery(sql, null);
	
		if (cursor.moveToFirst()) {
		    do {	//get info for a location
		    	location = new Location();
		    	location.setName(cursor.getString(1));
		    	location.setX(cursor.getInt(2));
		    	location.setY(cursor.getInt(3));
		    	location.setZ(cursor.getInt(4));
		    	locations.add(location);
		    } while (cursor.moveToNext());	//get all locations
		}
		if (cursor != null && !cursor.isClosed()) {
		    cursor.close();
		}
		return locations;
    }

    /**
     * Creates a SQLite database helper to perform updates and database
     * table creation
     * @author Lucas Stuyvesant
     * @author Andrea Sanchez
     */
    private static class OpenHelper extends SQLiteOpenHelper {

    	/**
    	 * Constructs an OpenHelper given a context
    	 * @param context
    	 */
		OpenHelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		/**
		 * Creates a table in the database when database is created
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + LOCATION_TABLE	+ "(_id INTEGER PRIMARY KEY, name TEXT, x_coord INTEGER, y_coord INTEGER, z_coord INTEGER)");
		}
	
		/**
		 * Drops tables when database is upgraded and recreates tables
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    Log.w("Upgrade", "Upgrading database, this will drop tables and recreate.");
		    db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE);
		    onCreate(db);
		}
    }
}
