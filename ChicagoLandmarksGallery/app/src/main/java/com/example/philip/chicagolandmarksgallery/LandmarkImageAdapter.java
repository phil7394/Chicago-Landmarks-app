package com.example.philip.chicagolandmarksgallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Image adapter to render grid image items
 * <p>
 * Created by Philip on 20-Oct-17.
 */

public class LandmarkImageAdapter extends BaseAdapter {

    // the application context
    private Context mContext;
    // the list of thumbnail resource ids
    private List<Integer> mThumbIds;

    public LandmarkImageAdapter(Context c, List<Integer> ids) {
        mContext = c;
        this.mThumbIds = ids;
    }

    @Override
    public int getCount() {

        return mThumbIds.size();
    }


    @Override
    public Object getItem(int position) {

        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // represents a single item in the grid
        View gridItem;
        // recycle existing view
        if (convertView == null) {

            // get the inflater from context
            LayoutInflater inflater = LayoutInflater.from(mContext);
            // inflate the item view with layout
            gridItem = inflater.inflate(R.layout.grid_item, parent, false);
        } else {
            gridItem = convertView;
        }
        // get the array of landmark names
        String[] landmarks = mContext.getResources().getStringArray(R.array.landmarks);
        // get the reference to the text view of the item
        TextView textView = (TextView) gridItem.findViewById(R.id.grid_text);
        // get the reference to the image view of the item
        ImageView imageView = (ImageView) gridItem.findViewById(R.id.grid_image);
        // set text with the car maker string at respective mPosition
        textView.setText(landmarks[position]);
        // set the image view with the resource id at position
        imageView.setImageResource(mThumbIds.get(position));

        return gridItem;
    }
}
