package com.vehicles.rainervieira.vehicles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Rainer on 05/07/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "posgrad";
    private static final String TABLE_VEICULOS = "veiculos";
    private static final String TABLE_VEICULOS_MARCAS = "veiculos_marcas";
    private static final String TABLE_VEICULOS_MODELOS = "veiculos_modelos";

    private static final String KEY_ID = "id";
    private static final String KEY_MARCA = "marca";
    private static final String KEY_MODELO = "modelo";
    private static final String KEY_PLACA = "placa";
    private static final String KEY_ANO = "ano";

    private static final String KEY_ID_MARCA = "id_marca";
    private static final String KEY_ID_MODELO = "id_modelo";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);
        populateTables(db);

        testTable(db);

    }

    private void createTables(SQLiteDatabase db){

        String CREATE_VEICULOS =
                "CREATE TABLE " + TABLE_VEICULOS +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_ID_MARCA + " INTEGER, " +
                        KEY_ID_MODELO + " INTEGER, " +
                        KEY_PLACA + " VARCHAR(7), " +
                        KEY_ANO + " INTEGER)";

        String CREATE_VEICULOS_MARCAS =
                "CREATE TABLE " + TABLE_VEICULOS_MARCAS +
                        "(" + KEY_ID_MARCA + " INTEGER PRIMARY KEY, " +
                        KEY_MARCA + " VARCHAR(32))";

        String CREATE_VEICULOS_MODELOS =
                "CREATE TABLE " + TABLE_VEICULOS_MODELOS +
                        "(" + KEY_ID_MODELO + " INTEGER PRIMARY KEY, " +
                        KEY_MODELO + " VARCHAR(32))";


        db.execSQL(CREATE_VEICULOS);
        db.execSQL(CREATE_VEICULOS_MARCAS);
        db.execSQL(CREATE_VEICULOS_MODELOS);

    }

    private void populateTables(SQLiteDatabase db){

        ContentValues marcas = new ContentValues();
        marcas.put(KEY_MARCA, "Wolksvagen");

        db.insert(TABLE_VEICULOS_MARCAS, null, marcas );
    }

    private void testTable(SQLiteDatabase db){

        Cursor rows = db.query(false, TABLE_VEICULOS_MARCAS, null, null, null,null, null, null, null);

        Log.d("MARCAS", "rows: " + rows.getCount());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
