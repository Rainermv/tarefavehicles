package com.vehicles.rainervieira.vehicles;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Created by rainervieira on 06/07/2016.
 */
public class ExtendedCursorAdapter extends CursorAdapter implements SpinnerAdapter {

    private String column_name = "marca";

    public ExtendedCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    public ExtendedCursorAdapter(Context context, Cursor c, int flags, String column_name) {
        super(context, c, 0);
        this.column_name = column_name;
    }


    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_template, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView element = (TextView) view.findViewById(R.id.element);

        // Extract properties from cursor
        String element_string = cursor.getString(cursor.getColumnIndexOrThrow(column_name));

        // Populate fields with extracted properties
        element.setText(element_string);
    }

    public void setColumn(String c_name){
        column_name = c_name;
    }
}
