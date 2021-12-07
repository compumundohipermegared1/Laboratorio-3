package com.example.laboratorio3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactoDbOpenHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "agenda.db";
    public static int VERSION = 1;
    String TAG = "openHelper";

    String CREATE_TABLE_CONTACTO =
            " CREATE TABLE contacto( " +
            " id INTEGER PRIMARY KEY autoincrement, " +
            " nombre TEXT, " +
            " apellido_p TEXT, " +
            " apellido_m TEXT, " +
            " telefono TEXT," +
            " sexo INTEGER DEFAULT -1); ";

    String INSERT_CONTACTO1 = " INSERT INTO contacto( nombre, apellido_p, apellido_m, telefono, sexo )" +
            "VALUES ('Daniela', 'Galleguillos','Díaz','+66666666666', 0)";

    public ContactoDbOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTO);
        Log.i(TAG,"se creó tabla contacto ");

        db.execSQL(INSERT_CONTACTO1);
        Log.i(TAG,"Insertar contacto ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
