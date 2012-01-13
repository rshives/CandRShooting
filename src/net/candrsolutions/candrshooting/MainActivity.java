package net.candrsolutions.candrshooting;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
//import android.widget.Toast;

public class MainActivity extends ListActivity {
	private static final String TAG = "MainActivity";
	Cursor mCursor;
	
	/*     
	 * Creates a projection that returns the DrillLogEntry ID and the DrillLogEntry Date.     *
	 */    
	private static final String[] PROJECTION =        
		new String[] {            
			Drill.Drills._ID,            
			Drill.Drills.COLUMN_NAME_DATE
		};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        
        if (intent.getData() == null) {
        	intent.setData(Drill.Drills.CONTENT_URI);
        }
        
    	mCursor = managedQuery(Drill.Drills.CONTENT_URI, PROJECTION, null, null, null);
    	
    	if (mCursor.getCount() < 1) {
    		getContentResolver().insert(Drill.Drills.CONTENT_URI, null);
    		mCursor.requery();
    	}
    	
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, 
        												  R.layout.list_drill_log_entries, 
        												  mCursor, 
        												  new String[] {Drill.Drills.COLUMN_NAME_DATE}, 
        												  new int[] {R.id.textView1});
        setListAdapter(sca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_options, menu);
    	
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.newDrill:
			newDrillEntry();
			return true;
		case R.id.viewIDPA:
			viewIDPARulebook();
			return true;
		case R.id.viewUSPSA:
			return true;
		case R.id.itemViewMatchResults:
			viewMatchResults();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }

    @Override
	protected void onListItemClick(ListView l, View view, int pos, long id) {
		Log.i(TAG, "Starting ACTION_EDIT activity.");
		Uri uri = ContentUris.withAppendedId(Drill.Drills.CONTENT_ID_URI_BASE, id);
		startActivity(new Intent(Intent.ACTION_EDIT, uri));
	}

	private void viewIDPARulebook() {
		Log.i(TAG, "Launching IDPA Rulebook.");
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.idpa.com/Documents/IDPARuleBook2005.pdf"));
		Uri IDPA_Rulebook = Uri.parse("android.resource://net.candrsolutions.candrshooting/raw/idparulebook2005.pdf");
		Intent localIntent = new Intent(Intent.ACTION_VIEW, IDPA_Rulebook);
			
		try {
			startActivity(webIntent);
		}
		catch (Exception webex) {
			try {
				//Toast.makeText(getApplicationContext(), "Launching a local pdf reader.", Toast.LENGTH_SHORT).show();
				Log.i(TAG, "Launching a local pdf reader.");
				startActivity(localIntent);
			}
			catch (Exception localex) {
				//Toast.makeText(getApplicationContext(), "A pdf reader must be installed to read the rulebooks.", Toast.LENGTH_LONG).show();
				Log.e(TAG, "A pdf reader must be installed to read the rulebooks.");
			}
		}
	}
	
	private void newDrillEntry() {
		Log.i(TAG, "Launching Drill Log.");
			
		try {
			// Start a new ACTION_INSERT activity passing in the URI to the Database Table.
			// This will cause the insertion of a blank record and open it for editing.
			startActivity(new Intent(Intent.ACTION_INSERT, Drill.Drills.CONTENT_URI));
		}
		catch(ActivityNotFoundException anfe) {
			//Toast.makeText(getApplicationContext(), "Something went wrong...\n" + anfe.getMessage(), Toast.LENGTH_LONG).show();
			Log.e(TAG, anfe.getMessage());
		}
	}
	
	private void viewMatchResults() {
		try {
			// Start a new ACTION_VIEW activity passing in the URI to a directory of Match Results.
			startActivity(new Intent(Intent.ACTION_VIEW, Match.Results.CONTENT_URI));
		}
		catch(ActivityNotFoundException anfe) {
			Log.e(TAG, anfe.getMessage());
		}
	}
}