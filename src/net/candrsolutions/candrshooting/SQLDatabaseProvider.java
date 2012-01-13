package net.candrsolutions.candrshooting;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class SQLDatabaseProvider extends ContentProvider {
	private static String TAG = "SQLDatabaseProvider";
	
	/**
	 * The database that the provider uses as its underlying data store
	 */
	protected static final String DATABASE_NAME = "candrshooting.db";
	
	/**
	 * The database version
	 */
	protected static final int DATABASE_VERSION = 1;
	
	// Handle to a new DatabaseHelper.
	protected DatabaseHelper mOpenHelper;
	
	/**
	 * This class helps open, create, and upgrade the database file. Set to package visibility
	 * for testing purposes.
	 */
	static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			// calls the super constructor, requesting the default cursor factory.
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		/**
		 * 
		 * Creates the underlying database with table name and column names taken from the
		 * DrillLog class.
		 */
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + Drill.Drills.TABLE_NAME + " ("
					+ Drill.Drills._ID + " INTEGER PRIMARY KEY,"
					+ Drill.Drills.COLUMN_NAME_DATE + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_DRILLNAME + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_DISTANCE + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_RESULTS + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_WEAPON + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_AMMO + " TEXT,"
					+ Drill.Drills.COLUMN_NAME_REMARKS + " TEXT"
					+ ");");
			
			// This will create the matchResults table with FLOAT data types for:
			// Stage Time, Stage Hit Factor, Stage Points, and Stage Percentage
			/*db.execSQL("CREATE TABLE " + Match.Results.TABLE_NAME + " ("
					+ Match.Results._ID + " INTEGER PRIMARY KEY,"
					+ Match.Results.COLUMN_NAME_MATCH_DATE + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_NUMBER + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_NAME + " TEXT,"
					+ Match.Results.COLUMN_NAME_NUMBER + " TEXT,"
					+ Match.Results.COLUMN_NAME_CLASSIFICATION + " TEXT,"
					+ Match.Results.COLUMN_NAME_DIVISION + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_RAW_POINTS + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_PENALTIES + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_TIME + " FLOAT,"
					+ Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR + " FLOAT,"
					+ Match.Results.COLUMN_NAME_STAGE_POINTS + " FLOAT,"
					+ Match.Results.COLUMN_NAME_STAGE_PERCENTAGE + " FLOAT"
					+ ");");
			*/
			
			// This will create the matchResults table with TEXT data types for all fields.
			db.execSQL("CREATE TABLE " + Match.Results.TABLE_NAME + " ("
					+ Match.Results._ID + " INTEGER PRIMARY KEY,"
					+ Match.Results.COLUMN_NAME_MATCH_DATE + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_NUMBER + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_NAME + " TEXT,"
					+ Match.Results.COLUMN_NAME_NUMBER + " TEXT,"
					+ Match.Results.COLUMN_NAME_CLASSIFICATION + " TEXT,"
					+ Match.Results.COLUMN_NAME_DIVISION + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_RAW_POINTS + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_PENALTIES + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_TIME + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_POINTS + " TEXT,"
					+ Match.Results.COLUMN_NAME_STAGE_PERCENTAGE + " TEXT"
					+ ");");
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
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
			
			// Kills the table and existing data
			db.execSQL("DROP TABLE IF EXISTS " + Drill.Drills.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + Match.Results.TABLE_NAME);
			
			// Recreates the database with a new version
			onCreate(db);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
