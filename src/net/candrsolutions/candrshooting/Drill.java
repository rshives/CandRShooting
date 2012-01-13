package net.candrsolutions.candrshooting;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines a contract between the Drill content provider and its clients. A contract defines the
 * information that a client needs to access the provider as one or more data tables. A contract
 * is a public, non-extendable (final) class that contains constants defining column names and
 * URIs. A well-written client depends only on the constants in the contract.
 */
public final class Drill {
	public static final String AUTHORITY = "net.candrsolutions.provider.Drill";
	
	// This class cannot be instantiated
	private Drill() {
	}
	
	/**
	 * DrillLog table contract
     */
	public static final class Drills implements BaseColumns {
		// This class cannot be instantiated
		private Drills() {}
		
		/**
		 * The table name offered by this provider.
         */
		public static final String TABLE_NAME = "drills";
		
		/*
		 * URI definitions
		 */
		
		/**
		 * The scheme part for this provider's URI.
		 */
		private static final String SCHEME = "content://";
		
		/**
		 * Path parts for the URIs.
		 */
		
		/**
		 * Path part for the Entries URI.
		 */
		private static final String PATH_DRILLS = "/drills";
		
		/**
		 * Path part for the Entry ID URI.
		 */
		private static final String PATH_DRILL_ID = "/drills/";
		
		/**
		 * 0-relative position of an entry ID segment in the path part of an entry ID URI.
		 */
		public static final int DRILL_ID_PATH_POSITION = 1;
		
		/**
		 * Path part for the Live Folder URI.
		 */
		// private static final String PATH_LIVE_FOLDER = "/live_folders/entries";
		
		/**
		 * The content:// style URL for this table.
		 * In other words the URI to the DrillLog table.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_DRILLS);
		
		/**
		 * The content URI base for a single entry. Callers must
		 * append a numeric entry id to this Uri to retrieve an entry.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_DRILL_ID);
		
		/**
		 * The content URI match pattern for a single entry, specified by its ID. Use this to match
		 * incoming URIs or to construct an Intent.
		 */
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_DRILL_ID + "#");
		
		/**
		 * The content Uri pattern for an entries listing for live folders.
		 */
		// public static final Uri LIVE_FOLDER_URI = Uri.parse(SCHEME + AUTHORITY + PATH_LIVE_FOLDER);
		
		/*
		 * MIME type definitions
		 */
		
		/**
		 * The MIME type of providing a directory of entries.
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.candrsolutions.drill";
		
		/**
		 * The MIME type of a sub-directory of a single entry.
		 */
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.candrsolutions.drill";
		
		/**
		 * The default sort order for this table.
		 */
		public static final String DEFAULT_SORT_ORDER = "date DESC";
		
		/*
		 * Column definitions
		 */
		
		/**
		 * Column name for the date of the entry.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_DATE = "date";
		
		/**
		 * Column name for the drill name.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_DRILLNAME = "drillName";
		
		/**
		 * Column name for the distance.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_DISTANCE = "distance";
		
		/**
		 * Column name for the results.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_RESULTS = "results";
		
		/**
		 * Column name for the weapon.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_WEAPON = "weapon";
		
		/**
		 * Column name for the ammo.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_AMMO = "ammo";
		
		/**
		 * Column name for the remarks.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_REMARKS = "remarks"; 
	}
}
