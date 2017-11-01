package com.example.philip.chicagolandmarks;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * LandmarksFragment to display list of landmark names
 * <p>
 * Created by Philip on 19-Oct-17.
 */

public class LandmarksFragment extends ListFragment {
    private static final String TAG = "LandmarksFragment";
    private ListSelectionListener mListener = null;
    private int mCurrIdx = -1;

    /**
     * Called when the user selects an item from the List
     *
     * @param l
     * @param v
     * @param pos
     * @param id
     */
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        if (mCurrIdx != pos) {
            mCurrIdx = pos;

            // Inform the ChicagoLandmarksActivity that the item in position pos has been selected
            mListener.onListSelection(pos);
        }
        // Indicates the selected item has been checked
        l.setItemChecked(mCurrIdx, true);
    }

    /**
     * @param activity
     */
    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);

        try {

            // Set the ListSelectionListener for communicating with the ChicagoLandmarksActivity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListSelectionListener");
        }
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @param savedState
     */
    @Override
    public void onActivityCreated(Bundle savedState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        setListAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.landmark_item, ChicagoLandmarksActivity.mLandmarksArray));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        Log.i(TAG, getClass().getSimpleName() + ": mCurrIdx = " + mCurrIdx);

        if (-1 != mCurrIdx) {
            getListView().setItemChecked(mCurrIdx, true);

            // Added this call to handle configuration changes
            mListener.onListSelection(mCurrIdx);
        }
    }


    /**
     * Callback interface that allows this Fragment to
     * notify the QuoteViewerActivity when user clicks
     * on a List Item
     */
    public interface ListSelectionListener {
        /**
         * execute on list selection
         *
         * @param index
         */
        void onListSelection(int index);
    }

}
