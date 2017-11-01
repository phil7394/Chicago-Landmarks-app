package com.example.philip.chicagolandmarksgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gallery to display landmark images as grid view
 * <p>
 *  Created by Philip on 20-Oct-17.
 */
public class LandmarksGalleryActivity extends AppCompatActivity {

    // list of landmark image ids
    private ArrayList<Integer> mLandmarkImgIds = new ArrayList<Integer>(
            Arrays.asList(R.drawable.willis_tower,
                    R.drawable.millennium_park,
                    R.drawable.cloud_gate,
                    R.drawable.navy_pier,
                    R.drawable.shedd_aquarium,
                    R.drawable.field_museum,
                    R.drawable.adler_planetarium));


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the main layout
        setContentView(R.layout.activity_landmarks_gallery);
        // get reference to gridView
        GridView gridView = (GridView) findViewById(R.id.gridView);
        // set the image adapter for the gridView
        gridView.setAdapter(new LandmarkImageAdapter(this, mLandmarkImgIds));

    }

}
