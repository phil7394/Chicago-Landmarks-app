package com.example.philip.chicagolandmarksgallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Broadcast receiver that starts the gallery
 * <p>
 * Created by Philip on 20-Oct-17.
 */
public class LandmarksBroadcastReceiver extends BroadcastReceiver {

    /**
     * start gallery activity when broadcast is received
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // intent to start LandmarksGalleryActivity explicitly
        Intent galleryIntent = new Intent(context, LandmarksGalleryActivity.class);
        // start activity
        context.startActivity(galleryIntent);
    }
}
