package com.example.laboratorio3.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import com.example.laboratorio3.R;
import com.example.laboratorio3.adapters.ContactoAdapter;
import com.example.laboratorio3.models.Contacto;
import com.example.laboratorio3.models.ContactoUpd;

import java.util.ArrayList;
import java.util.List;

public class ContactoDataSource {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    String TAG = "DB: ";

    public ContactoDataSource (Context context){
        dbhelper = new ContactoDbOpenHelper(context);
    }

    public void openDB() {
        db = dbhelper.getWritableDatabase();
        Log.i(TAG, "openDB");
    }

    public void closeDB(){
        dbhelper.close();
        Log.i(TAG,"closeDB");
    }

    public List<Contacto> obtenerContactos(){

        List<Contacto> contactos = new ArrayList<>();

        String query = "SELECT * FROM contacto";
        Cursor cursor = db.rawQuery(query, null);

        Log.i(TAG, "Filas retornadas: "+cursor.getCount());

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Contacto contacto = new Contacto();
                contacto.setId(cursor.getLong(0));
                contacto.setNombre(cursor.getString(1));
                contacto.setPaterno(cursor.getString(2));
                contacto.setMaterno(cursor.getString(3));
                contacto.setTelefono(cursor.getString(4));
                contacto.setSexo(cursor.getInt(5));

                contactos.add(contacto);
            }
        }

        return contactos;
    }

    @SuppressLint("Range")
    public static void CargarPersonas()
    {
        ArrayList<Contacto> datos;
        SQLiteDatabase db = null;
        ListView listView = null;

        String [] columns = {
                ContactoUpd._ID,
                ContactoUpd.COLUMN_NAME_NAME,
                ContactoUpd.COLUMN_NAME_A_PATERNO,
                ContactoUpd.COLUMN_NAME_A_MATERNO,
                ContactoUpd.COLUMN_NAME_PHONE,
                ContactoUpd.COLUMN_NAME_SEX
        };

        Cursor cursor = db.query(ContactoUpd.TABLE_NAME, columns, null, null, null, null, null);

        datos = new ArrayList<Contacto>();
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                long id = cursor.getInt(cursor.getColumnIndex(ContactoUpd._ID));
                String nombre = cursor.getString(cursor.getColumnIndex(ContactoUpd.COLUMN_NAME_NAME));
                String apellido_p = cursor.getString(cursor.getColumnIndex(ContactoUpd.COLUMN_NAME_A_PATERNO));
                String apellido_m = cursor.getString(cursor.getColumnIndex(ContactoUpd.COLUMN_NAME_A_MATERNO));
                String telefono = cursor.getString(cursor.getColumnIndex(ContactoUpd.COLUMN_NAME_PHONE));
                int sexo = cursor.getInt(cursor.getColumnIndex(ContactoUpd.COLUMN_NAME_SEX));

                Contacto contacto = new Contacto(id, nombre, apellido_p, apellido_m, telefono, sexo);
                datos.add(contacto);

                cursor.moveToNext();
            }
        }

        ContactoAdapter adapter = new ContactoAdapter(null, R.layout.activity_detalle, datos);
        listView.setAdapter(adapter);
    }

    public Contacto insertarContacto(Contacto contacto){
        ContentValues valores = new ContentValues();
        valores.put("nombre", contacto.getNombre());
        valores.put("apellido_p", contacto.getPaterno());
        valores.put("apellido_m",contacto.getMaterno());
        valores.put("telefono", contacto.getTelefono());
        valores.put("sexo", contacto.getSexo());
        long insertid = db.insert("contacto",null,valores);
        contacto.setId(insertid);

        return contacto;

    }

    public boolean eliminarContacto(long id ){
        String where = "id = " +id;
        int result = db.delete("contacto", where, null);

        return (result == 1);

    }
}
