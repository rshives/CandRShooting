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

public class DrillEditor extends Activity {
	private static final String TAG = "DrillLogActivity";
	
	/*     
	 * Creates a projection that returns the DrillLogEntry ID and the DrillLogEntry contents.     *
	 */    
	private static final String[] PROJECTION =        
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
	
	// This Activity can be started by more than one action. Each action is represented    
	// as a "state" constant    
	private static final int STATE_EDIT = 0;    
	private static final int STATE_INSERT = 1;    
	
	// Global mutable variables    
	private int mState;
	private Uri mUri;
	private Cursor mCursor;
	private EditText textDate;
	private EditText textDrillName;
	private EditText textDistance;
	private EditText textResults;
	private EditText textWeapon;
	private EditText textAmmo;
	private EditText textRemarks;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		// Used when returning to caller
		final Intent intent = getIntent();
		
		// Gets the action that triggered this activity
		final String action = intent.getAction();
		
		// For an edit action
		if (intent.ACTION_EDIT.equals(action)) {
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
		
		String uriType = getContentResolver().getType(intent.getData());
		
		/*         
		 * Using the URI passed in with the triggering Intent, gets the entry or entries in        	* 
		 * the provider.         																	*
		 * 																							* 
		 * Note: This is being done on the UI thread. It will block the thread until the query      * 
		 * completes. In a sample app, going against a simple provider based on a local database,   * 
		 * the block will be momentary, but in a real app you should use         					* 
		 * android.content.AsyncQueryHandler or android.os.AsyncTask.         						*
		 */        
		mCursor = managedQuery(            
			mUri,         // The URI that gets multiple entries from the provider.            
			PROJECTION,   // A projection that returns the entry ID and entry content for each entry.            
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
		else {
			setTitle(getText(R.string.error_title));
			textDrillName.setTextKeepState(getText(R.string.error_text));
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
			saveDrillLog();
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
	
	private void saveDrillLog() {
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
