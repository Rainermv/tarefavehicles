package com.vehicles.rainervieira.vehicles;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Rainer on 05/07/2016.
 */
public class ScreenSlidePagerActivity extends FragmentActivity {

    QueryFragment query_fragment;
    InsertFragment insert_fragment;

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPger and a PagerAdapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed(){

        if (mPager.getCurrentItem() == 0){

            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        }
        else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() -1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenSlidePagerAdapter (FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){

                case 0:
                    if (query_fragment == null){
                        query_fragment = new QueryFragment();
                    }
                    return query_fragment;

                case 1:
                    if (insert_fragment == null){
                        insert_fragment = new InsertFragment();
                    }
                    return insert_fragment;

            }

            return null;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void InsertVehicle(View view){

        Spinner spinner_marca = (Spinner) this.findViewById(R.id.spinner_marca);
        Cursor selected_cursor_marca = (Cursor)spinner_marca.getSelectedItem();
        String marca = selected_cursor_marca.getString(selected_cursor_marca.getColumnIndexOrThrow("marca"));

        Spinner spinner_modelo = (Spinner) this.findViewById(R.id.spinner_modelo);
        Cursor selected_cursor_modelo = (Cursor) spinner_modelo.getSelectedItem();
        String modelo = selected_cursor_modelo.getString(selected_cursor_modelo.getColumnIndexOrThrow("modelo"));

        EditText text_placa = (EditText) this.findViewById(R.id.text_placa);
        String placa = text_placa.getText().toString();

        EditText text_ano = (EditText) this.findViewById(R.id.text_ano);
        String ano = text_ano.getText().toString();

        DatabaseHandler database_helper = DatabaseHandler.getInstance(this);
        SQLiteDatabase database = database_helper.getWritableDatabase();

        database_helper.InsertVeiculo(database, marca, modelo, placa, ano);

        AlertDialog alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Operação Concluida");
        builder.setMessage("Veículo cadastrado com sucesso");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();

        spinner_marca.setSelection(0);
        spinner_modelo.setSelection(0);
        text_placa.setText("");
        text_ano.setText("");

        database_helper.close();
    }

    public void queryVehicles(View view){

        EditText search_marca = (EditText) this.findViewById(R.id.search_marca);
        String marca = search_marca.getText().toString();

        EditText search_modelo = (EditText) this.findViewById(R.id.search_modelo);
        String modelo = search_modelo.getText().toString();

        EditText search_placa = (EditText) this.findViewById(R.id.search_placa);
        String placa = search_placa.getText().toString();

        EditText search_ano = (EditText) this.findViewById(R.id.search_ano);
        String ano = search_ano.getText().toString();

        DatabaseHandler database_helper = DatabaseHandler.getInstance(this);;
        SQLiteDatabase database = database_helper.getReadableDatabase();

        Cursor veiculos = database_helper.getVeiculos(database);

        VehicleListCursorAdapter adapter = new VehicleListCursorAdapter(this, veiculos,0);

        ListView list_vehicles = (ListView) this.findViewById(R.id.list_vehicles);
        list_vehicles.setAdapter(adapter);

        final Activity act = this;

        list_vehicles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* Parameters
            parent:     The AdapterView where the click happened.
            view:       The view within the AdapterView that was clicked (this will be a view provided by the adapter)
            position:   The position of the view in the adapter.
            id:         The row id of the item that was clicked. */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(act, OpenVehicleActivity.class);

                TextView query_text_id = (TextView) parent.findViewById(R.id.query_text_id);
                String car_id = query_text_id.getText().toString();

                TextView query_text_marca = (TextView) parent.findViewById(R.id.query_text_marca);
                String marca = query_text_marca.getText().toString();

                TextView query_text_modelo = (TextView) parent.findViewById(R.id.query_text_modelo);
                String modelo = query_text_modelo.getText().toString();

                TextView query_text_placa = (TextView) parent.findViewById(R.id.query_text_placa);
                String placa = query_text_placa.getText().toString();

                TextView query_text_ano = (TextView) parent.findViewById(R.id.query_text_ano);
                String ano = query_text_ano.getText().toString();

                intent.putExtra("car_id", car_id);
                intent.putExtra("marca", marca);
                intent.putExtra("modelo", modelo);
                intent.putExtra("placa", placa);
                intent.putExtra("ano", ano);

                startActivity(intent);
            }
        });

        //database_helper.close();

    }

    void OpenVehicle(AdapterView<?> parent, View view, int position, long id, Activity act){

        Intent intent = new Intent(act, OpenVehicleActivity.class);

        Spinner spinner_marca = (Spinner) this.findViewById(R.id.spinner_marca);
        //Cursor selected_cursor_marca = (Cursor)spinner_marca.getSelectedItem();
        //String marca = selected_cursor_marca.getString(selected_cursor_marca.getColumnIndexOrThrow("marca"));

        Spinner spinner_modelo = (Spinner) this.findViewById(R.id.spinner_modelo);
        Cursor selected_cursor_modelo = (Cursor) spinner_modelo.getSelectedItem();
        String modelo = selected_cursor_modelo.getString(selected_cursor_modelo.getColumnIndexOrThrow("modelo"));

        EditText text_placa = (EditText) this.findViewById(R.id.text_placa);
        String placa = text_placa.getText().toString();

        EditText text_ano = (EditText) this.findViewById(R.id.text_ano);
        String ano = text_ano.getText().toString();

        startActivity(intent);
    }


}

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */


