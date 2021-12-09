package com.example.laboratorio3;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laboratorio3.adapters.ContactoAdapter;
import com.example.laboratorio3.db.ContactoDataSource;
import com.example.laboratorio3.models.Contacto;
import com.example.laboratorio3.models.ContactoUpd;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int REQUEST_CODE_AGREGAR_CONTACTO = 1001;
    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_DETALLE_ACTIVITY = 1002;
    ListView lvContactos;
    List<Contacto> contactos;
    ContactoDataSource dataSource;
    ArrayAdapter<Contacto> adapter;
    ArrayList<Contacto> datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Agenda");

        lvContactos = findViewById(R.id.lvContactos);


        dataSource = new ContactoDataSource(this);

        dataSource.openDB();
        contactos = dataSource.obtenerContactos();
        dataSource.closeDB();



        adapter = new ContactoAdapter(this, R.layout.activity_detalle,contactos);

        lvContactos.setAdapter(adapter);
        lvContactos.setOnItemClickListener(this);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contacto contacto = contactos.get(i);
        String nombre = contacto.getNombre();
        Log.i("MainActivity", "Nombre: " + nombre);
        Toast.makeText(this, "Click en item " + i, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,DetalleActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("contacto", contacto);

        startActivityForResult(intent, REQUEST_CODE_DETALLE_ACTIVITY);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_actiivity,menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_agregar_contacto:
                Intent intent = new Intent(this, AgregarContatoActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_AGREGAR_CONTACTO);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        SQLiteDatabase db = null;

        //mover el cursor al registro que se mantuvo pulsado
        final long _id = datos.get(info.position).getId();

        switch (item.getItemId()) {
            case R.id.edit:


                //editar registro
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.activity_agregar_contato, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etPaterno = (EditText)v.findViewById(R.id.etPaterno);
                final EditText etMaterno = (EditText)v.findViewById(R.id.etMaterno);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);


                //Obtener nombre y telefono del cursor y ponerlo en los EditText correspondientes
                String nombre = datos.get(info.position).getNombre();
                String a_paterno = datos.get(info.position).getPaterno();
                String a_materno = datos.get(info.position).getMaterno();
                String telefono = datos.get(info.position).getTelefono();
                final int sexo = datos.get(info.position).getSexo();

                rbMasculino.setChecked(sexo == 0);
                rbFemenino.setChecked(sexo == 1);

                etNombre.setText(nombre);
                etPaterno.setText(a_paterno);
                etMaterno.setText(a_materno);
                etTelefono.setText(telefono);

                builder.setTitle("Editar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //codigo para editar registro
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ContactoUpd.COLUMN_NAME_NAME, etNombre.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_NAME, etPaterno.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_NAME, etMaterno.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                        int sexo = -1;
                        if(rbMasculino.isChecked())
                            sexo = 0;
                        else if(rbFemenino.isChecked())
                            sexo = 1;

                        contentValues.put(ContactoUpd.COLUMN_NAME_SEX, sexo);

                        String where = ContactoUpd._ID + " = '" + _id + "'";
                        db.update(ContactoUpd.TABLE_NAME, contentValues, where, null);
                        AgregarContatoActivity.CargarPersonas();
                        Toast.makeText(MainActivity.this, "Registro editado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;
            case R.id.delete:
                //borrar registro
                String where = ContactoUpd._ID + " = '" + _id + "'";
                db.delete(ContactoUpd.TABLE_NAME, where,null);
                AgregarContatoActivity.CargarPersonas();
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_AGREGAR_CONTACTO && resultCode == 1){

            Log.i(TAG, "actualizar el listview");
            actualizarLista();


        }

        if (requestCode == REQUEST_CODE_DETALLE_ACTIVITY && resultCode == -1){
            //actualizar
        }

    }

    public void actualizarLista(){
        dataSource.openDB();
        contactos = dataSource.obtenerContactos();
        dataSource.closeDB();

        adapter.clear();
        adapter.addAll(contactos);
        adapter.notifyDataSetChanged();
    }
}