package com.example.laboratorio3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.laboratorio3.ContactoContract.ContactoEntry;

public class ContactoDbHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 2;
    public static String DATABASE_NAME = "Contactos.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactoEntry.TABLE_NAME + " (" +
                    ContactoEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactoEntry.COLUMN_NAME_NAME + " TEXT," +
                    ContactoEntry.COLUMN_NAME_APELLIDOS + " TEXT," +
                    ContactoEntry.COLUMN_NAME_PHONE + " TEXT," +
                    ContactoEntry.COLUMN_NAME_SEX + " INTEGER DEFAULT -1" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactoEntry.TABLE_NAME;


    public ContactoDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_ADD_SEX = "ALTER TABLE " + ContactoEntry.TABLE_NAME + " ADD " + ContactoEntry.COLUMN_NAME_SEX + " INTEGER DEFAULT -1";
        db.execSQL(SQL_ADD_SEX);
    }
}
