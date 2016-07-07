package com.vehicles.rainervieira.vehicles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainer on 05/07/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    //SQLiteDatabase db;

    private static DatabaseHandler instance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "posgrad";
    private static final String TABLE_VEICULOS = "veiculos";
    private static final String TABLE_VEICULOS_MARCAS = "veiculos_marcas";
    private static final String TABLE_VEICULOS_MODELOS = "veiculos_modelos";

    private static final String KEY_ID = "_id";
    private static final String KEY_MARCA = "marca";
    private static final String KEY_MODELO = "modelo";
    private static final String KEY_PLACA = "placa";
    private static final String KEY_ANO = "ano";

    private static final String KEY_ID_MARCA = "id_marca";
    private static final String KEY_ID_MODELO = "id_modelo";

    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

        populateTables(db);
        //testTable(db);

        //this.db = db;

    }

    private void createTables(SQLiteDatabase db){

        String CREATE_VEICULOS =
                "CREATE TABLE " + TABLE_VEICULOS +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_MARCA + " VARCHAR(32), " +
                        KEY_MODELO + " VARCHAR(32), " +
                        KEY_PLACA + " VARCHAR(7), " +
                        KEY_ANO + " INTEGER)";

        String CREATE_VEICULOS_MARCAS =
                "CREATE TABLE " + TABLE_VEICULOS_MARCAS +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_MARCA + " VARCHAR(32))";

        String CREATE_VEICULOS_MODELOS =
                "CREATE TABLE " + TABLE_VEICULOS_MODELOS +
                        "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                        KEY_MODELO + " VARCHAR(32))";


        db.execSQL(CREATE_VEICULOS);
        db.execSQL(CREATE_VEICULOS_MARCAS);
        db.execSQL(CREATE_VEICULOS_MODELOS);

    }

    public void populateTables(SQLiteDatabase db){

        // MARCAS
        ArrayList<String> marcas_array = new ArrayList<String>();

        marcas_array.add("Wolksvagen");
        marcas_array.add("Fiat");
        marcas_array.add("Renault");

        int id = 0;
        for (String marca_string: marcas_array ){

            ContentValues row_marca = new ContentValues();
            row_marca.put(KEY_MARCA,marca_string);
            db.insert(TABLE_VEICULOS_MARCAS, null, row_marca );

            //ContentValues row_id = new ContentValues();
            //row_id.put(KEY_ID,id++);
            //db.insert(TABLE_VEICULOS_MARCAS, null, row_id );

            Log.d("DB", "inserting: " + marca_string);
        }

        // MODELOS
        ArrayList<String> modelos_array = new ArrayList<String>();

        modelos_array.add("Gol");
        modelos_array.add("Uno");
        modelos_array.add("Clio");

        id = 0;
        for (String modelo_string: modelos_array ){

            ContentValues row_modelo = new ContentValues();
            row_modelo.put(KEY_MODELO,modelo_string);
            db.insert(TABLE_VEICULOS_MODELOS, null, row_modelo );

            //ContentValues row_id = new ContentValues();
            //row_id.put(KEY_ID,id++);
            //db.insert(TABLE_VEICULOS_MODELOS, null, row_id );

            Log.d("DB", "inserting: " + modelo_string);
        }

    }

    public void testTable(SQLiteDatabase db){

        Cursor rows = db.query(false, TABLE_VEICULOS_MARCAS, null, null, null,null, null, null, null);

        while (rows.moveToNext()) {
            Log.d("DB", "MARCA : " + rows.getString(0));
            Log.d("DB", "MARCA : " + rows.getString(1));
        }

        rows = db.query(false, TABLE_VEICULOS_MODELOS, null, null, null,null, null, null, null);

        while (rows.moveToNext()) {
            Log.d("DB", "MODELO : " + rows.getString(0));
            Log.d("DB", "MODELO : " + rows.getString(1));
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.delete(TABLE_VEICULOS_MARCAS, null, null);
        db.delete(TABLE_VEICULOS_MODELOS, null, null);

        Log.d("DB", "Tables deleted - repopulating");

        populateTables(db);

        //this.db = db;

    }

    public void InsertVeiculo(SQLiteDatabase db, String marca, String modelo, String placa, String ano){

        ContentValues veiculo_content = new ContentValues();

        veiculo_content.put(KEY_MARCA,marca);
        veiculo_content.put(KEY_MODELO,modelo);
        veiculo_content.put(KEY_PLACA,placa);
        veiculo_content.put(KEY_ANO,ano);

        db.insert(TABLE_VEICULOS, null, veiculo_content );

        long count = DatabaseUtils.queryNumEntries(db, TABLE_VEICULOS);

        Log.d("DB", "Veículo inserido, contagem: " + count);

    }

    public int UpdateVeiculo(SQLiteDatabase db, String v_id,  String marca, String modelo, String placa, String ano) {

        ContentValues veiculo_content = new ContentValues();

        veiculo_content.put(KEY_MARCA,marca);
        veiculo_content.put(KEY_MODELO,modelo);
        veiculo_content.put(KEY_PLACA,placa);
        veiculo_content.put(KEY_ANO,ano);

        int result = db.update(TABLE_VEICULOS, veiculo_content, KEY_ID + " = " + v_id , null);

        Log.d("DB", "Veículo atualizado: " + result);

        return result;

    }

    public int DeleteVeiculo(SQLiteDatabase db, String v_id) {

        int result = db.delete(TABLE_VEICULOS, KEY_ID + " = " + v_id, null);

        Log.d("DB", "Veículo removido: " + result);

        return result;
    }

    public Cursor getVeiculos(SQLiteDatabase db, String marca, String modelo, String placa, String ano){

        HashMap<String, String> where_array = new HashMap<String, String>();

        if (!marca.equals("")){
            where_array.put("marca",marca);
        }
        if (!modelo.equals("")){
            where_array.put("modelo",modelo);
        }
        if (!placa.equals("")){
            where_array.put("placa",placa);
        }
        if (!ano.equals("")){
            where_array.put("ano",ano);
        }

        String whereClause = null;
        String[] whereArgs  = null;

        int size = where_array.size();

        if (size > 0) {

            whereClause = "";
            whereArgs = new String[size];

            int i = 0;
            for (String key : where_array.keySet()) {

                whereClause += key + " = ? ";
                whereArgs[i] = where_array.get(key);

                if (i+1 < size)
                    whereClause += " AND ";

                i++;
            }

            Log.d("DB", "whereClause: " + whereClause);
            Log.d("DB", "whereArgs: " + whereArgs.toString());

        }


        return db.query(false, TABLE_VEICULOS, null, whereClause, whereArgs,null, null, null, null);

    }

    public Cursor getMarcas(SQLiteDatabase db){

        return db.query(false, TABLE_VEICULOS_MARCAS, null, null, null,null, null, null, null);

    }

    public Cursor getModelos(SQLiteDatabase db){

        return db.query(false, TABLE_VEICULOS_MODELOS, null, null, null,null, null, null, null);

    }



}
