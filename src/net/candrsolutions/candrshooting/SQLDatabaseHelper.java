package net.candrsolutions.candrshooting;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;

public class SQLDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "SQLDatabaseHelper";
	
	/**
	 * The database that the provider uses as its underlying data store
	 */
	public static final String DATABASE_NAME = "candrshooting.db";
	
	/**
	 * The database version
	 */
	public static final int DATABASE_VERSION = 3;

	
	public SQLDatabaseHelper(Context context, CursorFactory factory) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*try {
			db.execSQL("CREATE TABLE " + Drill.Drills.TABLE_NAME + " (" + 
					Drill.Drills._ID + " INTEGER PRIMARY KEY," +
					Drill.Drills.COLUMN_NAME_DATE + " TEXT," +
					Drill.Drills.COLUMN_NAME_DRILLNAME + " TEXT," +
					Drill.Drills.COLUMN_NAME_DISTANCE + " TEXT," +
					Drill.Drills.COLUMN_NAME_RESULTS + " TEXT," +
					Drill.Drills.COLUMN_NAME_WEAPON + " TEXT," + 
					Drill.Drills.COLUMN_NAME_AMMO + " TEXT," +
					Drill.Drills.COLUMN_NAME_REMARKS + " TEXT);");
			
			db.execSQL("CREATE TABLE " + Match.Results.TABLE_NAME + " (" +
					Match.Results._ID + " INTEGER PRIMARY KEY," +
					Match.Results.COLUMN_NAME_MATCH_DATE + " TEXT," +
					Match.Results.COLUMN_NAME_STAGE_NUMBER + " TEXT," +
					Match.Results.COLUMN_NAME_STAGE_NAME + " TEXT," +
					Match.Results.COLUMN_NAME_NUMBER + " TEXT," +
					Match.Results.COLUMN_NAME_CLASSIFICATION + " TEXT," +
					Match.Results.COLUMN_NAME_DIVISION + " TEXT," +
					Match.Results.COLUMN_NAME_STAGE_RAW_POINTS + " TEXT" +
					Match.Results.COLUMN_NAME_STAGE_PENALTIES + " TEXT" +
					Match.Results.COLUMN_NAME_STAGE_TIME + " TEXT" +
					Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR + " TEXT" +
					Match.Results.COLUMN_NAME_STAGE_POINTS + " TEXT" +
					Match.Results.COLUMN_NAME_STAGE_PERCENTAGE + " TEXT);");
		}
		catch (SQLException sqle) {
			Log.w(TAG, "Unable to open Database" + sqle.getMessage());
		}*/
	}

	/**
	 * 
	 * Demonstrates that the provider must consider what happens when the
	 * underlying datastore is changed. In this sample, the database is upgraded the database
	 * by destroying the existing data.
	 * A real application should upgrade the database in place.        
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Logs that the database is being upgraded
		//Log.w(TAG, "Upgrading database from version " + oldVersion + " to "	+ newVersion + ", which will destroy all old data");
		
		// Kills the table and existing data
		//db.execSQL("DROP TABLE IF EXISTS " + Drill.Drills.TABLE_NAME);
		//db.execSQL("DROP TABLE IF EXISTS " + Match.Results.TABLE_NAME);
		
		// Recreates the database with a new version
		//onCreate(db);
	}

}
