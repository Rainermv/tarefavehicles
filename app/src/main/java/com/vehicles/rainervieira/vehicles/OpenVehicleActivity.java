package com.vehicles.rainervieira.vehicles;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class OpenVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vehicle);

        Intent intent = getIntent();

        String car_id = intent.getStringExtra("car_id");
        String marca = intent.getStringExtra("marca");
        String modelo = intent.getStringExtra("modelo");
        String placa = intent.getStringExtra("placa");
        String ano = intent.getStringExtra("ano");

        TextView text_id = (TextView) this.findViewById(R.id.text_id);
        text_id.setText(car_id);

        EditText text_placa = (EditText) this.findViewById(R.id.text_placa);
        text_placa.setText(placa);

        EditText text_ano = (EditText) this.findViewById(R.id.text_ano);
        text_ano.setText(ano);

        DatabaseHandler database_helper = DatabaseHandler.getInstance(this);
        SQLiteDatabase database = database_helper.getWritableDatabase();

        //database.execSQL("DROP TABLE veiculos_marcas");
        Cursor marcas = database_helper.getMarcas(database);
        Cursor modelos = database_helper.getModelos(database);

        //database_helper.testTable(database);
        Spinner spinner_marca = (Spinner) this.findViewById(R.id.spinner_marca);
        Spinner spinner_modelo = (Spinner) this.findViewById(R.id.spinner_modelo);

        SpinnerCursorAdapter adapter_marca = new SpinnerCursorAdapter(this, marcas, 0, "marca");
        SpinnerCursorAdapter adapter_modelo = new SpinnerCursorAdapter(this, modelos, 0, "modelo");
        //adapter.setDropDownViewResource();

        spinner_marca.setAdapter(adapter_marca);
        spinner_modelo.setAdapter(adapter_modelo);

        if (!marca.equals(null)) {
            int spinnerPosition = adapter_marca.getPosition(marca);
            spinner_marca.setSelection(spinnerPosition);
        }

        if (!modelo.equals(null)) {
            int spinnerPosition = adapter_modelo.getPosition(modelo);
            spinner_modelo.setSelection(spinnerPosition);
        }

    }




}
