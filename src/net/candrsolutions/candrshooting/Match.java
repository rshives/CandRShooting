package net.candrsolutions.candrshooting;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines a contract between the Match content provider and its clients. A contract defines the
 * information that a client needs to access the provider as one or more data tables. A contract
 * is a public, non-extendable (final) class that contains constants defining column names and
 * URIs. A well-written client depends only on the constants in the contract.
 */
public final class Match {
	private static final String TAG = "Match";
	public static final String AUTHORITY = "net.candrsolutions.provider.Match";
	
	// This class cannot be instantiated
	private Match() {
	}
	
	/**
	 * Results table contract
     */
	public static final class Results implements BaseColumns {
		private static final String TAG = "Results";
		
		// This class cannot be instantiated
		private Results() {}
	
		/**
		 * The table name offered by this provider.
         */
		public static final String TABLE_NAME = "matchResults";
		
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
		 * Path part for the Results URI.
		 */
		private static final String PATH_RESULTS = "/results";
		
		/**
		 * Path part for a specific Result ID URI.
		 */
		private static final String PATH_RESULT_ID = "/results/";
		
		/**
		 * 0-relative position of a result ID segment in the path part of a result ID URI.
		 */
		public static final int RESULT_ID_PATH_POSITION = 1;

		/**
		 * The content:// style URL for this table.
		 * In other words the URI to the matchResults table.
		 */
		public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_RESULTS);
		
		/**
		 * The content URI base for a single result set. Callers must
		 * append a numeric result id to this Uri to retrieve a result set.
		 */
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_RESULT_ID);
		
		/**
		 * The content URI match pattern for a single result set specified by its ID. Use this to match
		 * incoming URIs or to construct an Intent.
		 */
		public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_RESULT_ID + "#");

		/*
		 * MIME type definitions
		 */
		
		/**
		 * The MIME type of a directory of results.
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.candrsolutions.match_result";
		
		/**
		 * The MIME type of a sub-directory of a single result.
		 */
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.candrsolutions.match_result";
		
		/**
		 * The default sort order for this table.
		 */
		public static final String DEFAULT_SORT_ORDER = "matchDate DESC";
		
		/*
		 * Column definitions
		 */
		
		/**
		 * Column name for the date of the result set.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_MATCH_DATE = "matchDate";
		
		/**
		 * Column name for the stage number (within the match).
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_NUMBER = "stageNumber";
		
		/**
		 * Column name for the stage name.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_STAGE_NAME = "stageName";
		
		/**
		 * Column name for the competitor's number (within the stage).
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_NUMBER = "number";
		
		/**
		 * Column name for the classification.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_CLASSIFICATION = "classification";
		
		/**
		 * Column name for the division.
		 * <P>Type: TEXT</P>
		 */
		public static final String COLUMN_NAME_DIVISION = "division";
		
		/**
		 * Column name for the points earned on the stage.
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_RAW_POINTS = "stageRawPoints";
		
		/**
		 * Column name for the penalties earned on the stage.
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_PENALTIES = "stagePenalties";
		
		/**
		 * Column name for the time, in seconds, it took to run the stage.
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_TIME = "stageTime";
		
		/**
		 * Column name for the stage hit factor.
		 * Hit Factor is calculated from the equation:
		 * HF = (RP - P) / T
		 * Where:
		 * RP = Raw Points
		 * P  = Penalties
		 * T  = Time
		 * 
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_HIT_FACTOR = "stageHitFactor";
		
		/**
		 * Column name for the stage points.
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_POINTS = "stagePoints";
		
		/**
		 * Column name for the stage percentage.
		 * <P>Type: STRING</P>
		 */
		public static final String COLUMN_NAME_STAGE_PERCENTAGE = "stagePercentage";
	}
}
