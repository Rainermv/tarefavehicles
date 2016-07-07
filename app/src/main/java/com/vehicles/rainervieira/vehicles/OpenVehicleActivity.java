package com.vehicles.rainervieira.vehicles;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            int spinnerPosition = adapter_marca.getPosition(marca,marcas);
            spinner_marca.setSelection(spinnerPosition);
        }

        if (!modelo.equals(null)) {
            int spinnerPosition = adapter_modelo.getPosition(modelo, modelos);
            spinner_modelo.setSelection(spinnerPosition);
        }

    }

    public void confirmUpdateVehicle(View view){

        AlertDialog alert;

        final View fview = view;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alteração");
        builder.setMessage("Alterar dados do veículo?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        updateVehicle(fview);
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();

    }

    private void updateVehicle(View view){

        TextView text_id = (TextView) this.findViewById(R.id.text_id);
        String v_id = text_id.getText().toString();

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

        //database_helper.InsertVeiculo(database, marca, modelo, placa, ano);
        int result = database_helper.UpdateVeiculo(database,v_id, marca, modelo, placa, ano);

        AlertDialog alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (result >= 1) {
            builder.setTitle("Operação Concluida");
            builder.setMessage("Veículo atualizado com sucesso");
        }
        else{
            builder.setTitle("Erro na Operação");
            builder.setMessage("Veículo não atualizado");
        }

        builder.setPositiveButton("Fechar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();

    }

    public void confirmDeleteVehicle(View view){

        AlertDialog alert;

        final View fview = view;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar");
        builder.setMessage("Remover veículo do banco de dados?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        deleteVehicle(fview);
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();

    }

    private void deleteVehicle(View view){

        TextView text_id = (TextView) this.findViewById(R.id.text_id);
        String v_id = text_id.getText().toString();

        DatabaseHandler database_helper = DatabaseHandler.getInstance(this);
        SQLiteDatabase database = database_helper.getWritableDatabase();

        //database_helper.InsertVeiculo(database, marca, modelo, placa, ano);
        final int result = database_helper.DeleteVeiculo(database,v_id);

        AlertDialog alert;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (result >= 1) {
            builder.setTitle("Operação Concluida");
            builder.setMessage("Veículo removido com sucesso");
        }
        else{
            builder.setTitle("Erro na Operação");
            builder.setMessage("Veículo não removido");
        }

        builder.setPositiveButton("Fechar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                        if (result >=1){
                            finish();
                        }
                    }
                });

        alert = builder.create();
        alert.show();

    }

}
