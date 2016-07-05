package com.vehicles.rainervieira.vehicles;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

/**
 * Created by Rainer on 05/07/2016.
 */
public class InsertFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_insert, container, false);

        DatabaseHandler database_helper = new DatabaseHandler(rootView.getContext());
        SQLiteDatabase database = database_helper.getWritableDatabase();

        //database.execSQL("DROP TABLE veiculos_marcas");

        database_helper.testTable(database);
        database_helper.close();

        //Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_marca);



        return rootView;
    }


}
