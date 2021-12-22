package com.example.laboratorio3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ListaContactosActivity extends AppCompatActivity {

    ListView listView;
    ContactoDbHelper ContactoDbHelper;
    SQLiteDatabase db;
    ArrayList<Contacto> contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        listView = (ListView)findViewById(R.id.listView);
        registerForContextMenu(listView);
        ContactoDbHelper = new ContactoDbHelper(this);
        db = ContactoDbHelper.getWritableDatabase();
        CargarContactos();
    }

    @SuppressLint("Range")
    public void CargarContactos()
    {
        String [] columns = {
                ContactoContract.ContactoEntry._ID,
                ContactoContract.ContactoEntry.COLUMN_NAME_NAME,
                ContactoContract.ContactoEntry.COLUMN_NAME_APELLIDOS,
                ContactoContract.ContactoEntry.COLUMN_NAME_PHONE,
                ContactoContract.ContactoEntry.COLUMN_NAME_SEX
        };

        @SuppressLint("Recycle") Cursor cursor = db.query(ContactoContract.ContactoEntry.TABLE_NAME, columns, null, null, null, null, null);

        contactos = new ArrayList<>();
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                int id = cursor.getInt(cursor.getColumnIndex(ContactoContract.ContactoEntry._ID));
                String nombre = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.COLUMN_NAME_NAME));
                String apellidos = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.COLUMN_NAME_APELLIDOS));
                String telefono = cursor.getString(cursor.getColumnIndex(ContactoContract.ContactoEntry.COLUMN_NAME_PHONE));
                int sexo = cursor.getInt(cursor.getColumnIndex(ContactoContract.ContactoEntry.COLUMN_NAME_SEX));

                Contacto contacto = new Contacto(id, nombre, apellidos, telefono, sexo);
                contactos.add(contacto);

                cursor.moveToNext();
            }
        }

        ContactoAdapter adapter = new ContactoAdapter(this, R.layout.modelo_lista, contactos);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_menu_nuevo:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.add_edit_layout, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etApellidos = (EditText)v.findViewById(R.id.etApellidos);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);
                final RadioButton rbNonBinary  = (RadioButton)v.findViewById(R.id.rbNonBinary);

                builder.setTitle("Agregar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", (dialog, which) -> {
                    //codigo para agregar un nuevo registro
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_NAME, etNombre.getText().toString());
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_APELLIDOS, etApellidos.getText().toString());
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                    int sexo = -1;
                    if(rbMasculino.isChecked())
                        sexo = 0;
                    else if(rbFemenino.isChecked())
                        sexo = 1;
                    else if(rbNonBinary.isChecked())
                        sexo = 2;

                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_SEX, sexo);

                    ListaContactosActivity.this.db.insert(ContactoContract.ContactoEntry.TABLE_NAME, null, contentValues);
                    ListaContactosActivity.this.CargarContactos();
                    Toast.makeText(ListaContactosActivity.this, "Registro agregado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;
            case R.id.action_cerrar_sesion:
                FirebaseAuth.getInstance().signOut();
                gologin();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gologin() {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //mover el cursor al registro que se mantuvo pulsado
        final int _id = contactos.get(info.position).getId();

        switch (item.getItemId()) {
            case R.id.action_editar:

                //editar registro
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.add_edit_layout, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etApellidos = (EditText)v.findViewById(R.id.etApellidos);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);
                final RadioButton rbNonBinary  = (RadioButton)v.findViewById(R.id.rbNonBinary);


                //Obtener nombre y telefono del cursor y ponerlo en los EditText correspondientes
                String nombre = contactos.get(info.position).getNombre();
                String apellidos = contactos.get(info.position).getApellidos();
                String telefono = contactos.get(info.position).getTelefono();
                final int sexo = contactos.get(info.position).getSexo();

                rbMasculino.setChecked(sexo == 0);
                rbFemenino.setChecked(sexo == 1);
                rbNonBinary.setChecked(sexo == 2);

                etNombre.setText(nombre);
                etApellidos.setText(apellidos);
                etTelefono.setText(telefono);

                builder.setTitle("Editar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", (dialog, which) -> {
                    //codigo para editar registro
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_NAME, etNombre.getText().toString());
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_APELLIDOS, etApellidos.getText().toString());
                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                    int sexo1 = -1;
                    if(rbMasculino.isChecked())
                        sexo1 = 0;
                    else if(rbFemenino.isChecked())
                        sexo1 = 1;
                    else if(rbNonBinary.isChecked())
                        sexo1 = 2;

                    contentValues.put(ContactoContract.ContactoEntry.COLUMN_NAME_SEX, sexo1);

                    String where = ContactoContract.ContactoEntry._ID + " = '" + _id + "'";
                    ListaContactosActivity.this.db.update(ContactoContract.ContactoEntry.TABLE_NAME, contentValues, where, null);
                    ListaContactosActivity.this.CargarContactos();
                    Toast.makeText(ListaContactosActivity.this, "Registro editado", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;
            case R.id.action_eliminar:

                //borrar registro
                String where = ContactoContract.ContactoEntry._ID + " = '" + _id + "'";
                db.delete(ContactoContract.ContactoEntry.TABLE_NAME, where,null);
                CargarContactos();
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        ContactoDbHelper.close();
        super.onDestroy();
    }
}