package com.example.philip.chicagolandmarks;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * WebpageFragment to display webpages of landmarks.
 * <p>
 * Created by Philip on 19-Oct-17.
 */

public class WebpageFragment extends Fragment {

    private static final String TAG = "WebpageFragment";

    private WebView mWebpageView = null;

    private int mCurrIdx = -1;
    private int mWebpageArrLen;

    /**
     * get the selected index
     *
     * @return
     */
    int getShownIndex() {
        return mCurrIdx;
    }

    /**
     * Show the webpage at position newIndex
     *
     * @param newIndex
     */
    void showWebpageAtIndex(int newIndex) {
        // check for valid index
        if (newIndex < 0 || newIndex >= mWebpageArrLen) {
            return;
        }

        mCurrIdx = newIndex;

        // get web settings
        WebSettings webSettings = mWebpageView.getSettings();
        // enable java script on webpages
        webSettings.setJavaScriptEnabled(true);

        Log.i(TAG, getClass().getSimpleName() + "webpage index = " + mCurrIdx);

        // load url to display on web view
        mWebpageView.loadUrl(ChicagoLandmarksActivity.mWebpageArray[mCurrIdx]);

    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":onCreate()");
        super.onCreate(savedInstanceState);

        // Retain this Fragment across Activity reconfigurations
        setRetainInstance(true);

    }

    /**
     * Called to create the content view for this Fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in webpage_fragment.xml
        // The last parameter is false because the returned view does not need to be attached to the container ViewGroup
        return inflater.inflate(R.layout.webpage_fragment, container, false);
    }

    /**
     * Set up some information about the mWebpageView TextView
     *
     * @param savedInstanceState
     **/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
        // get webpageView reference
        mWebpageView = (WebView) getActivity().findViewById(R.id.webpageView);
        // get length of urls list
        mWebpageArrLen = ChicagoLandmarksActivity.mWebpageArray.length;
        // load webpage for mCurrIdx
        showWebpageAtIndex(mCurrIdx);

    }
}
