package com.vehicles.rainervieira.vehicles;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
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
        Cursor marcas = database_helper.getMarcas(database);
        Cursor modelos = database_helper.getModelos(database);

        //database_helper.testTable(database);
        Spinner spinner_marca = (Spinner) rootView.findViewById(R.id.spinner_marca);
        Spinner spinner_modelo = (Spinner) rootView.findViewById(R.id.spinner_modelo);

        ExtendedCursorAdapter adapter_marca = new ExtendedCursorAdapter(rootView.getContext(), marcas, 0, "marca");
        ExtendedCursorAdapter adapter_modelo = new ExtendedCursorAdapter(rootView.getContext(), modelos, 0, "modelo");
        //adapter.setDropDownViewResource();

        spinner_marca.setAdapter(adapter_marca);
        spinner_modelo.setAdapter(adapter_modelo);

        //database_helper.close();

        return rootView;
    }


}
