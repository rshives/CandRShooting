package net.candrsolutions.candrshooting;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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

public class MatchFragmentActivity extends Activity {
	private static final String TAG = "MatchFragmentActivity";
	
	/*
	 * Creates a projection that returns all Match fields except the date.	*
	 */
	private static final String[] MATCH_PROJECTION =
		new String[] {
			Match.Results._ID,
			Match.Results.COLUMN_NAME_CLASSIFICATION,
			Match.Results.COLUMN_NAME_DIVISION,
			Match.Results.COLUMN_NAME_NUMBER,
			Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR,
			Match.Results.COLUMN_NAME_STAGE_NAME,
			Match.Results.COLUMN_NAME_STAGE_NUMBER,
			Match.Results.COLUMN_NAME_STAGE_PENALTIES,
			Match.Results.COLUMN_NAME_STAGE_PERCENTAGE,
			Match.Results.COLUMN_NAME_STAGE_POINTS,
			Match.Results.COLUMN_NAME_STAGE_RAW_POINTS,
			Match.Results.COLUMN_NAME_STAGE_TIME
		};
	
	private static final String[] MATCH_DATE_PROJECTION = 
		new String[] {
			Match.Results._ID,
			Match.Results.COLUMN_NAME_MATCH_DATE
		};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.match_fragment_layout_land);
    }
	
    /**
     * This is the "top-level" fragment, showing a list of items that the
     * user can pick.  Upon picking an item, it takes care of displaying the
     * data to the user as appropriate based on the current UI layout.
     */
	public static class MatchTitleFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
		private static final String TAG = "MatchTitleFragment";
	    boolean mDualPane;
	    int mCurCheckPosition = 0;
	    SimpleCursorAdapter mAdapter;
	    
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        
	        setHasOptionsMenu(true);
	        
	        // If the database is empty
	        setEmptyText(getString(R.string.empty_database));
	        
	        // Populate list with our static array of titles.
	        // with our database records...
	        mAdapter = new SimpleCursorAdapter(getActivity(), 
	        		android.R.layout.simple_list_item_activated_1, 
	        		null, 
	        		new String[] {Match.Results.COLUMN_NAME_MATCH_DATE}, 
	        		new int[] {android.R.id.text1}, 0);
	        
	        setListAdapter(mAdapter);
	        getLoaderManager().initLoader(0, null, this);
	        
	        // Check to see if we have a frame in which to embed the details
	        // fragment directly in the containing UI.
	        View detailsFrame = getActivity().findViewById(R.id.detailsFrame);
	        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
	
	        if (savedInstanceState != null) {
	            // Restore last state for checked position.
	            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
	        }
	
	        if (mDualPane) {
	            // In dual-pane mode, the list view highlights the selected item.
	            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	            // Make sure our UI is in the correct state.
	            showDetails(mCurCheckPosition);
	        }
	    }
	
	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("curChoice", mCurCheckPosition);
	    }
	
	    @Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
	        showDetails(position);
	    }
	
	    @Override 
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    	inflater.inflate(R.menu.single_option_new, menu);
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
	    
	    /**
	     * Helper function to show the details of a selected item, either by
	     * displaying a fragment in-place in the current UI, or starting a
	     * whole new activity in which it is displayed.
	     */
	    void showDetails(int index) {
	        mCurCheckPosition = index;

	        if (mDualPane) {
	            // We can display everything in-place with fragments, so update
	            // the list to highlight the selected item and show the data.
	            getListView().setItemChecked(index, true);
	
	            // Check what fragment is currently shown, replace if needed.
	            MatchDetailsFragment details = (MatchDetailsFragment)
	                    getFragmentManager().findFragmentById(R.id.detailsFrame);
	            if (details == null || details.getShownIndex() != index) {
	                // Make new fragment to show this selection.
	                details = MatchDetailsFragment.newInstance(ContentUris.withAppendedId(Match.Results.CONTENT_ID_URI_BASE, ++index).toString());
	
	                // Execute a transaction, replacing any existing fragment
	                // with this one inside the frame.
	                FragmentTransaction ft = getFragmentManager().beginTransaction();
	                ft.replace(R.id.detailsFrame, details);
	                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	                ft.commit();
	            }
	
	        } else {
	            // Otherwise we need to launch a new activity to display
	            // the dialog fragment with selected text.
	            Intent intent = new Intent();
	            intent.setClass(getActivity(), DetailsActivity.class);
	            intent.setData(ContentUris.withAppendedId(Match.Results.CONTENT_ID_URI_BASE, ++index));
	            startActivity(intent);
	        }
	    }

		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			return new CursorLoader(getActivity(), Match.Results.CONTENT_URI, MATCH_DATE_PROJECTION, null, null, null);
		}

		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			mAdapter.swapCursor(data);
			
		}

		public void onLoaderReset(Loader<Cursor> loader) {
			mAdapter.swapCursor(null);
			
		}
	}
}
