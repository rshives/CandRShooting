package net.candrsolutions.candrshooting;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class ShooterEditor extends Activity {
	private static final String TAG = "Shooter Editor";
	
	/*     
	 * Creates a projection that returns the DrillLogEntry ID and the DrillLogEntry contents.     *
	 */    
	private static final String[] DRILL_PROJECTION =        
		new String[] {            
			Drill.Drills._ID,            
			Drill.Drills.COLUMN_NAME_DATE,            
			Drill.Drills.COLUMN_NAME_DRILLNAME,
			Drill.Drills.COLUMN_NAME_DISTANCE,
			Drill.Drills.COLUMN_NAME_RESULTS,
			Drill.Drills.COLUMN_NAME_WEAPON,
			Drill.Drills.COLUMN_NAME_AMMO,
			Drill.Drills.COLUMN_NAME_REMARKS
		};
	
	/*
	 * Creates a projection that returns the MatchResultsEntry record.	*
	 */
	private static final String[] MATCH_PROJECTION =
		new String[] {
			Match.Results._ID,
			Match.Results.COLUMN_NAME_MATCH_DATE,
			Match.Results.COLUMN_NAME_CLASSIFICATION,
			Match.Results.COLUMN_NAME_DIVISION,
			Match.Results.COLUMN_NAME_NUMBER,
			Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR,
			Match.Results.COLUMN_NAME_STAGE_NAME,
			Match.Results.COLUMN_NAME_STAGE_NUMBER,
			Match.Results.COLUMN_NAME_STAGE_PENALTIES,
			Match.Results.COLUMN_NAME_STAGE_POINTS,
			Match.Results.COLUMN_NAME_STAGE_RAW_POINTS,
			Match.Results.COLUMN_NAME_STAGE_TIME,
			Match.Results.COLUMN_NAME_STAGE_PERCENTAGE
		};
	
	// This Activity can be started by more than one action. Each action is represented
	// as a "state" constant
	private static final int STATE_EDIT = 0;
	private static final int STATE_INSERT = 1;
	private static final int STATE_VIEW = 2;
	
	// Global mutable variables    
	private int mState;
	private Uri mUri;
	private Cursor mCursor;
	private String mUriType;
	private EditText textDate;
	private EditText textDrillName;
	private EditText textDistance;
	private EditText textResults;
	private EditText textWeapon;
	private EditText textAmmo;
	private EditText textRemarks;
	private EditText textMatchDate;
	private EditText textClassification;
	private EditText textDivision;
	private EditText textNumber;
	private EditText textStageHitFactor;
	private EditText textStageNumber;
	private EditText textStageName;
	private EditText textStagePenalties;
	private EditText textStagePoints;
	private EditText textStageRawPoints;
	private EditText textStageTime;
	private EditText textStagePercentage;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		// Used when returning to caller
		final Intent intent = getIntent();
		
		// Gets the action that triggered this activity
		final String action = intent.getAction();
		
		mUriType = getContentResolver().getType(intent.getData());
		
		// For an edit action
		if (intent.ACTION_EDIT.equals(action) || intent.ACTION_VIEW.equals(action)) {
			mState = STATE_EDIT;
			mUri = intent.getData();
		}
		// Really an ADD action
		else if (intent.ACTION_INSERT.equals(action)) {
			mState = STATE_INSERT;
			//mUri = DrillLogEntry.Entries.CONTENT_URI;
			// This will insert a blank record into the database.
			// The insert method returns a URI to the newly created record.
			mUri = getContentResolver().insert(intent.getData(), null);
		}

		/*         
		 * Using the URI passed in with the triggering Intent, gets the record in the provider.     *
		 * 																							* 
		 * Note: This is being done on the UI thread. It will block the thread until the query      * 
		 * completes. In a sample app, going against a simple provider based on a local database,   * 
		 * the block will be momentary, but in a real app you should use         					* 
		 * android.content.AsyncQueryHandler or android.os.AsyncTask.         						*
		 */
		if (mUriType == Drill.Drills.CONTENT_ITEM_TYPE || mUriType == Drill.Drills.CONTENT_TYPE) {
			mCursor = managedQuery(            
				mUri,         // The URI that gets multiple entries from the provider.            
				DRILL_PROJECTION,   // A projection that returns the entry ID and entry content for each entry.            
				null,         // No "where" clause selection criteria.            
				null,         // No "where" clause selection values.            
				null          // Use the default sort order (date, descending)        
			);
			
			setContentView(R.layout.drill_log);
			
			textDate = (EditText) findViewById(R.id.editDate);
	        textDrillName = (EditText) findViewById(R.id.editDrillName);
	        textDistance = (EditText) findViewById(R.id.editDistance);
	        textResults = (EditText) findViewById(R.id.editResults);
	        textWeapon = (EditText) findViewById(R.id.editWeapon);
	        textAmmo = (EditText) findViewById(R.id.editAmmo);
	        textRemarks = (EditText) findViewById(R.id.editRemarks);
		}
		else if (mUriType == Match.Results.CONTENT_ITEM_TYPE || mUriType == Match.Results.CONTENT_TYPE) {
			mCursor = managedQuery(
				mUri,
				MATCH_PROJECTION,
				null,
				null,
				null
			);
			
			setContentView(R.layout.match_results_entry);
			
			textMatchDate = (EditText) findViewById(R.id.editTextDate);
			textClassification = (EditText) findViewById(R.id.editTextClassification); 
			textDivision = (EditText) findViewById(R.id.editTextDivision);
			textNumber = (EditText) findViewById(R.id.editTextNumber);
			textStageHitFactor = (EditText) findViewById(R.id.editTextHitFactor);
			textStageNumber = (EditText) findViewById(R.id.editTextStageNumber);
			textStageName = (EditText) findViewById(R.id.editTextStageName);
			textStagePenalties = (EditText) findViewById(R.id.editTextStagePenalties); 
			textStagePoints = (EditText) findViewById(R.id.editTextStagePoints);
			textStageRawPoints = (EditText) findViewById(R.id.editTextRawPoints);
			textStageTime = (EditText) findViewById(R.id.editTextStageTime);
			textStagePercentage = (EditText) findViewById(R.id.editTextStagePercentage);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (mCursor != null) {
			mCursor.requery();
			mCursor.moveToFirst();
			
			if (mState == STATE_EDIT) {
				setTitle(R.string.title_edit);
			}
			else if (mState == STATE_INSERT) {
				setTitle(getText(R.string.text_new));
			}
			
			if (mUriType == Drill.Drills.CONTENT_ITEM_TYPE || mUriType == Drill.Drills.CONTENT_TYPE) {
				int colDateIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_DATE);
				String date = mCursor.getString(colDateIndex);
				textDate.setTextKeepState(date);
				
				int colDrillNameIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_DRILLNAME);
				String drillName = mCursor.getString(colDrillNameIndex);
				textDrillName.setTextKeepState(drillName);
				
				int colDistanceIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_DISTANCE);
				String distance = mCursor.getString(colDistanceIndex);
				textDistance.setTextKeepState(distance);
				
				int colResultsIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_RESULTS);
				String results = mCursor.getString(colResultsIndex);
				textResults.setTextKeepState(results);
				
				int colWeaponIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_WEAPON);
				String weapon = mCursor.getString(colWeaponIndex);
				textWeapon.setTextKeepState(weapon);
				
				int colAmmoIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_AMMO);
				String ammo = mCursor.getString(colAmmoIndex);
				textAmmo.setTextKeepState(ammo);
				
				int colRemarksIndex = mCursor.getColumnIndex(Drill.Drills.COLUMN_NAME_REMARKS);
				String remarks = mCursor.getString(colRemarksIndex);
				textRemarks.setTextKeepState(remarks);
			}
			else if (mUriType == Match.Results.CONTENT_ITEM_TYPE || mUriType == Match.Results.CONTENT_TYPE) {
				int colMatchDateIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_MATCH_DATE);
				String matchDate = mCursor.getString(colMatchDateIndex);
				textMatchDate.setTextKeepState(matchDate);
				
				int colStageNumberIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_NUMBER);
				String stageNumber = mCursor.getString(colStageNumberIndex);
				textStageNumber.setTextKeepState(stageNumber);
				
				int colStageNameIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_NAME);
				String stageName = mCursor.getString(colStageNameIndex);
				textStageName.setTextKeepState(stageName);
				
				int colNumberIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_NUMBER);
				String number = mCursor.getString(colNumberIndex);
				textNumber.setTextKeepState(number);
				
				int colClassificationIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_CLASSIFICATION);
				String classification = mCursor.getString(colClassificationIndex);
				textClassification.setTextKeepState(classification);
				
				int colDivisionIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_DIVISION);
				String division = mCursor.getString(colDivisionIndex);
				textDivision.setTextKeepState(division);
				
				int colStageRawPointsIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_RAW_POINTS);
				String stageRawPoints = mCursor.getString(colStageRawPointsIndex);
				textStageRawPoints.setTextKeepState(stageRawPoints);
				
				int colStagePenaltiesIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_PENALTIES);
				String stagePenalties = mCursor.getString(colStagePenaltiesIndex);
				textStagePenalties.setTextKeepState(stagePenalties);
				
				int colStageTimeIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_TIME);
				String stageTime = mCursor.getString(colStageTimeIndex);
				textStageTime.setTextKeepState(stageTime);
				
				int colStageHitFactorIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR);
				String stageHitFactor = mCursor.getString(colStageHitFactorIndex);
				textStageHitFactor.setTextKeepState(stageHitFactor);
				
				int colStagePointsIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_POINTS);
				String stagePoints = mCursor.getString(colStagePointsIndex);
				textStagePoints.setTextKeepState(stagePoints);
				
				int colStagePercentageIndex = mCursor.getColumnIndex(Match.Results.COLUMN_NAME_STAGE_PERCENTAGE);
				String stagePercentage = mCursor.getString(colStagePercentageIndex);
				textStagePercentage.setTextKeepState(stagePercentage);
			}
		}
		else {
			setTitle(getText(R.string.error_title));
			
			if (mUriType == Drill.Drills.CONTENT_ITEM_TYPE || mUriType == Drill.Drills.CONTENT_TYPE) {
				textDate.setTextKeepState(getText(R.string.error_text));
			}
			else if (mUriType == Match.Results.CONTENT_ITEM_TYPE || mUriType == Match.Results.CONTENT_TYPE) {
				textMatchDate.setTextKeepState(getText(R.string.error_text));
			}
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.editor_options, menu);
    	
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.save:
			saveEntry();
			return true;
		case R.id.delete:
			deleteEntry();
			return true;
		case R.id.cancel:
			cancelEdit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
	
	private void saveEntry() {
		if (mUriType == Drill.Drills.CONTENT_ITEM_TYPE || mUriType == Drill.Drills.CONTENT_TYPE) {
			ContentValues values = new ContentValues();
			Log.i(TAG, "Updating record: " + textDate.getText().toString());
			
			values.put(Drill.Drills.COLUMN_NAME_DATE, textDate.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_DRILLNAME, textDrillName.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_DISTANCE, textDistance.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_RESULTS, textResults.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_WEAPON, textWeapon.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_AMMO, textAmmo.getText().toString());
			values.put(Drill.Drills.COLUMN_NAME_REMARKS, textRemarks.getText().toString());
			
			getContentResolver().update(mUri, values, null, null);
		}
		else if (mUriType == Match.Results.CONTENT_ITEM_TYPE || mUriType == Match.Results.CONTENT_TYPE) {
			ContentValues values = new ContentValues();
			Log.i(TAG, "Updating record: " + textMatchDate.getText().toString());
			
			values.put(Match.Results.COLUMN_NAME_CLASSIFICATION, textClassification.getText().toString());
			values.put(Match.Results.COLUMN_NAME_DIVISION, textDivision.getText().toString());
			values.put(Match.Results.COLUMN_NAME_MATCH_DATE, textMatchDate.getText().toString());
			values.put(Match.Results.COLUMN_NAME_NUMBER, textNumber.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR, textStageHitFactor.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_NAME, textStageName.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_NUMBER, textStageNumber.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_PENALTIES, textStagePenalties.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_PERCENTAGE, textStagePercentage.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_POINTS, textStagePoints.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_RAW_POINTS, textStageRawPoints.getText().toString());
			values.put(Match.Results.COLUMN_NAME_STAGE_TIME, textStageTime.getText().toString());
			
			getContentResolver().update(mUri, values, null, null);
		}
		
		finish();
	}
	
	private void deleteEntry() {
		if (mCursor != null) {
			mCursor.close();
			mCursor = null;
			getContentResolver().delete(mUri, null, null);
		}
		
		finish();
	}
	
	private void cancelEdit() {
		if (mState == STATE_INSERT) {
			// delete empty record
			deleteEntry();
		}
		else if (mState == STATE_EDIT) {
			// revert to original
			if (mCursor != null) {
				mCursor.close();
				mCursor = null;
			}
		}
		
		finish();
	}
}
