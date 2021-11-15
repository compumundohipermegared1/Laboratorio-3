package com.example.laboratorio3.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.laboratorio3.models.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    String TAG = "DB: ";

    public ContactoDataSource (Context context){
        dbhelper = new ContactoDbOpenHelper(context);
    }

    public void setDb(SQLiteDatabase db){
        db = dbhelper.getWritableDatabase();
        Log.i(TAG,"openDB");
    }

    public void closeDB(){
        dbhelper.close();
        Log.i(TAG,"closeDB");
    }

    @SuppressLint("Range")
    public List<Contacto> obtenerContactos(){
        List<Contacto> contactos = new ArrayList<>();
        String query = "SELECT * FROM contacto ";
        Cursor cursor = db.rawQuery(query, null);

        Log.i (TAG," Filas retornadas" + cursor.getCount());
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                Contacto contacto = new Contacto();

                contacto.setId(cursor.getLong(cursor.getColumnIndex("id")));
                contacto.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                contacto.setPaterno(cursor.getString(cursor.getColumnIndex("apellido_P")));
                contacto.setMaterno(cursor.getString(cursor.getColumnIndex("apellido_M")));
                contacto.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));

                contactos.add(contacto);
            }
        }
        return contactos;
    }
}
