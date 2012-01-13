package net.candrsolutions.candrshooting;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class DetailsActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "DetailsActivity";
	Uri uri;
	SimpleCursorAdapter mAdapter;
	
	/*
	 * Creates a projection that returns the Match ID and the Match Date.     *
	 */
	private static final String[] MATCH_PROJECTION =
		new String[] {
		Match.Results._ID,
		Match.Results.COLUMN_NAME_STAGE_NAME,
		Match.Results.COLUMN_NAME_STAGE_TIME
		};
	
	private static final int[] LAYOUT_MAP = 
		new int[] {
			//R.id.textForStageName, 
			//R.id.textForStageTime
		};
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }

        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            MatchDetailsFragment details = new MatchDetailsFragment();
            details.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }*/
        
        Intent intent = getIntent();
        
        if (intent.getData() == null)
        	uri = ContentUris.withAppendedId(Match.Results.CONTENT_ID_URI_BASE, 0);
        else
        	uri = intent.getData();
        
        // Populate list with our database records...
        getLoaderManager().initLoader(0, null, this);
        mAdapter = new SimpleCursorAdapter(getBaseContext(), 
        		android.R.layout.simple_list_item_1, 
        		null, 
        		MATCH_PROJECTION, 
        		LAYOUT_MAP, 
        		0);
        
        setListAdapter(mAdapter);
    }

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getBaseContext(), uri, MATCH_PROJECTION, null, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
