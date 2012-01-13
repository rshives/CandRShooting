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

public class ShooterList extends ListActivity {
	private static final String TAG = "List Activity";
	Cursor mCursor;
	
	/*
	 * Creates a projection that returns the Match ID and the Match Date.     *
	 */
	private static final String[] MATCH_PROJECTION =
		new String[] {
			Match.Results._ID,
			Match.Results.COLUMN_NAME_MATCH_DATE
		};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        
        if (intent.getData() == null) {
        	intent.setData(Match.Results.CONTENT_URI);
        }
        
    	mCursor = managedQuery(Match.Results.CONTENT_URI, MATCH_PROJECTION, null, null, null);
    	
    	if (mCursor.getCount() < 1) {
    		getContentResolver().insert(Match.Results.CONTENT_URI, null);
    		mCursor.requery();
    	}
    	
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, 
        												  android.R.layout.simple_list_item_1, 
        												  mCursor, 
        												  new String[] {Match.Results.COLUMN_NAME_MATCH_DATE}, 
        												  new int[] {android.R.id.text1});
        setListAdapter(sca);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.single_option_new, menu);
    	
    	return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		try {
			// Start a new ACTION_INSERT activity passing in the URI to the Database Table.
			// This will cause the insertion of a blank record and open it for editing.
			startActivity(new Intent(Intent.ACTION_INSERT, Match.Results.CONTENT_URI));
		}
		catch(ActivityNotFoundException anfe) {
			Log.e(TAG, anfe.getMessage());
		}
    	
    	return true;
    }
    
    @Override
	protected void onListItemClick(ListView l, View view, int pos, long id) {
		Uri uri = ContentUris.withAppendedId(Match.Results.CONTENT_ID_URI_BASE, id);
		startActivity(new Intent(Intent.ACTION_EDIT, uri));
	}
}
