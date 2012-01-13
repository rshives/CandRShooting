package net.candrsolutions.candrshooting;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class MatchDetailsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	/*
	 * Creates a projection that returns the Match ID and the Match Stage Name.     *
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
			Match.Results.COLUMN_NAME_STAGE_POINTS,
			Match.Results.COLUMN_NAME_STAGE_RAW_POINTS,
			Match.Results.COLUMN_NAME_STAGE_TIME,
			Match.Results.COLUMN_NAME_STAGE_PERCENTAGE
		};

	// Global Mutable Variables
	private Uri uri;
	SimpleCursorAdapter mAdapter;
	/*private EditText textClassification;
	private EditText textDivision;
	private EditText textNumber;
	private EditText textStageHitFactor;
	private EditText textStageNumber;
	private EditText textStageName;
	private EditText textStagePenalties;
	private EditText textStagePoints;
	private EditText textStageRawPoints;
	private EditText textStageTime;
	private EditText textStagePercentage;*/
	
	/**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static MatchDetailsFragment newInstance(String uri) {
        MatchDetailsFragment f = new MatchDetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("uri", uri);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
    	int subStringStart = getArguments().getString("uri").lastIndexOf("/");
    	String index = getArguments().getString("uri").substring(++subStringStart); 
        return Integer.parseInt(index);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	uri = Uri.parse(getArguments().getString("uri"));
    	getLoaderManager().initLoader(0, null, this);
    	mAdapter = new SimpleCursorAdapter(getActivity(), 
    			R.layout.match_results_entry,
    			null,
    			new String[] {Match.Results.COLUMN_NAME_CLASSIFICATION,
							  Match.Results.COLUMN_NAME_DIVISION,
							  Match.Results.COLUMN_NAME_NUMBER,
							  Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR,
							  Match.Results.COLUMN_NAME_STAGE_NAME,
							  Match.Results.COLUMN_NAME_STAGE_NUMBER,
							  Match.Results.COLUMN_NAME_STAGE_PENALTIES,
							  Match.Results.COLUMN_NAME_STAGE_POINTS,
							  Match.Results.COLUMN_NAME_STAGE_RAW_POINTS,
							  Match.Results.COLUMN_NAME_STAGE_TIME,
							  Match.Results.COLUMN_NAME_STAGE_PERCENTAGE},
				new int[] {R.id.editTextClassification,
    					   R.id.editTextDivision,
    					   R.id.editTextNumber,
    					   R.id.editTextHitFactor,
    					   R.id.editTextStageNumber,
    					   R.id.editTextStageName,
    					   R.id.editTextStagePenalties,
    					   R.id.editTextStagePoints,
    					   R.id.editTextRawPoints,
    					   R.id.editTextStageTime,
    					   R.id.editTextStagePercentage},
    			0);
    	
    	setListAdapter(mAdapter);
    }

    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//return inflater.inflate(R.layout.match_results_entry, container, false);
    //}
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    	
    }
    
    @Override
	public void onResume() {
		super.onResume();
		
		getLoaderManager().restartLoader(0, null, this);
		
    	// Set the values of the TextViews
    	/*textClassification.setTextKeepState(Match.Results.COLUMN_NAME_CLASSIFICATION);
    	textDivision.setTextKeepState(Match.Results.COLUMN_NAME_DIVISION);
    	textNumber.setTextKeepState(Match.Results.COLUMN_NAME_NUMBER);
    	textStageHitFactor.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_HIT_FACTOR);
    	textStageNumber.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_NUMBER);
    	textStageName.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_NAME);
    	textStagePenalties.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_PENALTIES);
    	textStagePoints.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_POINTS);
    	textStageRawPoints.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_RAW_POINTS);
    	textStageTime.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_TIME);
    	textStagePercentage.setTextKeepState(Match.Results.COLUMN_NAME_STAGE_PERCENTAGE);*/
    }
    
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity(), uri, MATCH_PROJECTION, null, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}
}
