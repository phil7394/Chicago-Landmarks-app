package com.example.philip.chicagolandmarks;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * This Activity Contains two fragments, one to list
 * landmark names and the other to display the webpage
 * <p>
 * Created by Philip on 19-Oct-17.
 */
public class ChicagoLandmarksActivity extends AppCompatActivity
        implements LandmarksFragment.ListSelectionListener {

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String LOG_TAG = "FragmentActivity";
    // custom permission name
    private static final String CUSTOM_APP_PERMISSION = "com.example.philip.chicagolandmarks.EXPLORE_GALLERY";
    // custom action for the broadcast intent
    private static final String BROADCAST_INTENT_ACTION = "com.example.philip.chicagolandmarks.ACTION_EXPLORE_GALLERY";
    private static final int PERMISSION_REQUEST_CODE = 0;
    // mLandmarksArray contains the names of landmarks
    public static String[] mLandmarksArray;
    // mWebpageArray contains the homepage links of landmarks
    public static String[] mWebpageArray;

    private WebpageFragment mWebpageFragment;
    private LandmarksFragment mLandmarksFragment;

    private FragmentManager mFragmentManager;
    private FrameLayout mLandmarksFrameLayout, mWebpageFrameLayout;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(LOG_TAG, getClass().getSimpleName() + ":entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the names and urls
        mLandmarksArray = getResources().getStringArray(R.array.landmarks);
        mWebpageArray = getResources().getStringArray(R.array.webpages);

        //set the content view for this activity
        setContentView(R.layout.activity_chicago_landmarks);

        // Get references to the LandmarksFramelayout and to the WebpageFramelayout
        mLandmarksFrameLayout = (FrameLayout) findViewById(R.id.landmarks_framelayout);
        mWebpageFrameLayout = (FrameLayout) findViewById(R.id.webpage_framelayout);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Get the reference to LandmarksFragment
        mLandmarksFragment = (LandmarksFragment) mFragmentManager.findFragmentById(R.id.landmarks_framelayout);

        // if mLandmarksFragment is null, create new instance
        if (mLandmarksFragment == null) {
            mLandmarksFragment = new LandmarksFragment();
        }
        // Add the mLandmarksFragment to the layout
        // using replace() instead of add() to avoid overlapping fragments
        fragmentTransaction.replace(R.id.landmarks_framelayout,
                mLandmarksFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        //Log.i("BACKSTACK", "onBackStackChanged");
                        setLayout();
                    }
                });

        // Get the reference to LandmarksFragment
        mWebpageFragment = (WebpageFragment) mFragmentManager.findFragmentById(R.id.webpage_framelayout);

        // if mWebpageFragment is null, create new instance
        if (mWebpageFragment == null) {
            mWebpageFragment = new WebpageFragment();
        }

        // set the layout depending on orientation
        setLayout();
    }

    /**
     * sets the layout depending on the orientation
     */
    private void setLayout() {
        // for portrait mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> PORTRAIT");
            // checking if mWebpageFragment was already added
            if (!mWebpageFragment.isAdded()) {
                Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> mWebpageFragment is not Added()");
                // set dimensions of mLandmarksFrameLayout so as to cover the whole container
                mLandmarksFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                // set dimensions of mWebpageFrameLayout so as to be hidden
                mWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> mWebpageFragment is Added()");
                // set dimensions of mLandmarksFrameLayout so as to be hidden
                mLandmarksFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                // set dimensions of mWebpageFrameLayout so as to cover the whole container
                mWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }
        }
        // for landscape mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> LANDSCAPE");
            // checking if mWebpageFragment was already added
            if (!mWebpageFragment.isAdded()) {
                Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> mWebpageFragment is not Added()");
                // set dimensions of mLandmarksFrameLayout so as to cover the whole container
                mLandmarksFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                // set dimensions of mWebpageFrameLayout so as to be hidden
                mWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            } else {
                Log.i(LOG_TAG, getClass().getSimpleName() + ": setLayout() --> mWebpageFragment is Added()");
                // Make the mLandmarksFrameLayout take 1/3 of the layout's width
                mLandmarksFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                // Make the mWebpageFrameLayout take 2/3's of the layout's width
                mWebpageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
    }

    /**
     * Called when the user selects an item in the LandmarksFragment
     **/
    @Override
    public void onListSelection(int index) {

        // If the WebpageFragment has not been added, add it now
        if (!mWebpageFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the WebpageFragment to the layout
            fragmentTransaction.replace(R.id.webpage_framelayout,
                    mWebpageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        // check if the index currently displayed is the same
        // as the index to be displayed
        if (mWebpageFragment.getShownIndex() != index) {

            // Tell the WebpageFragment to show the webpage at position index
            mWebpageFragment.showWebpageAtIndex(index);

        }
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflate options menu layout
        inflater.inflate(R.menu.landmarks_options_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // exit this app
            case R.id.exitApp:
                finish();
                return true;
            // go to gallery to explore, if permission is granted
            case R.id.exploreGallery:
                Log.i(LOG_TAG, getClass().getSimpleName() + ": explore gallery option");

                // check if permission is granted
                if (!checkPermissions()) {
                    // request permission from user
                    ActivityCompat.requestPermissions(this, new String[]{CUSTOM_APP_PERMISSION}, PERMISSION_REQUEST_CODE);

                } else {
                    // create intent and send broadcast as permission is already granted
                    prepareAndSendBroadcast();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * checks if the custom permission was granted or not
     *
     * @return
     */
    public boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(this, CUSTOM_APP_PERMISSION) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * prepares a broadcast intent and sends the broadcast
     */
    public void prepareAndSendBroadcast() {
        // create new intent
        Intent broadcastIntent = new Intent();
        // set the custom action specified in intent filter of receiver app
        broadcastIntent.setAction(BROADCAST_INTENT_ACTION);

        // to match any components in packages that are currently stopped.
        //broadcastIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //Toast.makeText(this, "sending broadcast", Toast.LENGTH_SHORT).show();

        // send broadcast
        sendBroadcast(broadcastIntent);
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // check if permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                // send broadcast, if permission was granted
                prepareAndSendBroadcast();
            } else {
                // get the package manager
                PackageManager pm = getPackageManager();
                try {
                    // check if the gallery package was installed or not
                    PackageInfo pi = pm.getPackageInfo("com.example.philip.chicagolandmarksgallery", pm.GET_PERMISSIONS);
                    Log.i("Gallery permissions", pi.permissions[0].name);
                    // if gallery package was found, then the custom permission was registered,
                    // but user denied permission
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                } catch (PackageManager.NameNotFoundException e) {
                    // if the gallery package was not found, the custom permission was not registered.
                    // let user know that the gallery app needs to be installed
                    Toast.makeText(this, "Permission not registered! Please install Chicago Landmarks Gallery app", Toast.LENGTH_LONG).show();

                }

            }

        }
    }

    /**
     * when back button is pressed, set the layout for LandmarksFragment
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(LOG_TAG, getClass().getSimpleName() + ": onBackPressed()");
        // get fragment transaction
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // replace LandmarkFragment with new instance with no item selected
        fragmentTransaction.replace(R.id.landmarks_framelayout, new LandmarksFragment());
        // commit transaction
        fragmentTransaction.commit();
        // execute immediately
        mFragmentManager.executePendingTransactions();

    }
}

