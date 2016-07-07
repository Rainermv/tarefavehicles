package com.vehicles.rainervieira.vehicles;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by rainervieira on 06/07/2016.
 */
public class VehicleListCursorAdapter extends CursorAdapter {

    public VehicleListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.vehiclelist_template, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView text_id = (TextView) view.findViewById(R.id.query_text_id);
        TextView text_marca = (TextView) view.findViewById(R.id.text_marca);
        TextView text_modelo = (TextView) view.findViewById(R.id.text_modelo);
        TextView text_placa = (TextView) view.findViewById(R.id.text_placa);
        TextView text_ano = (TextView) view.findViewById(R.id.text_ano);

        // Extract properties from cursor
        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        String marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"));
        String modelo = cursor.getString(cursor.getColumnIndexOrThrow("modelo"));
        String placa = cursor.getString(cursor.getColumnIndexOrThrow("placa"));
        String ano = cursor.getString(cursor.getColumnIndexOrThrow("ano"));

        // Populate fields with extracted properties
        text_id.setText(id);
        text_marca.setText(marca);
        text_modelo.setText(modelo);
        text_placa.setText(placa);
        text_ano.setText(ano);

    }
}
