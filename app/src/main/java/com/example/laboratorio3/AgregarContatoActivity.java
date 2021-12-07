package com.example.laboratorio3;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laboratorio3.db.ContactoDataSource;
import com.example.laboratorio3.models.Contacto;
import com.example.laboratorio3.models.ContactoUpd;

import java.util.ArrayList;


public class AgregarContatoActivity extends AppCompatActivity {

    EditText etNombre, etPaterno, etMaterno, etTelefono, etSexo;
    ContactoDataSource dataSource;
    ArrayList<Contacto> datos;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contato);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataSource = new ContactoDataSource(this);

        etNombre = findViewById(R.id.etNombre);
        etPaterno = findViewById(R.id.etPaterno);
        etMaterno = findViewById(R.id.etMaterno);
        etTelefono = findViewById(R.id.etTelefono);
        //etSexo = findViewById(R.id.etSexo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar_contacto_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //mover el cursor al registro que se mantuvo pulsado
        final Long _id = datos.get(info.position).getId();

        switch (item.getItemId()) {
            case R.id.edit:
                //editar registro
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.activity_agregar_contato, null);
                final EditText etNombre = (EditText)v.findViewById(R.id.etNombre);
                final EditText etTelefono = (EditText)v.findViewById(R.id.etTelefono);
                final RadioButton rbMasculino  = (RadioButton)v.findViewById(R.id.rbMasculino);
                final RadioButton rbFemenino  = (RadioButton)v.findViewById(R.id.rbFemenino);

                //Obtener nombre y telefono del cursor y ponerlo en los EditText correspondientes
                String nombre = datos.get(info.position).getNombre();
                String apellido_p = datos.get(info.position).getPaterno();
                String apellido_m = datos.get(info.position).getMaterno();
                String telefono = datos.get(info.position).getTelefono();
                int sexo = datos.get(info.position).getSexo();

                rbMasculino.setChecked(sexo == 0);
                rbFemenino.setChecked(sexo == 1);

                etNombre.setText(nombre);
                etTelefono.setText(telefono);

                builder.setTitle("Editar registro");
                builder.setView(v);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //codigo para editar registro
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ContactoUpd.COLUMN_NAME_NAME, etNombre.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_A_PATERNO, etPaterno.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_A_MATERNO, etMaterno.getText().toString());
                        contentValues.put(ContactoUpd.COLUMN_NAME_PHONE, etTelefono.getText().toString());

                        int sexo = -1;
                        if(rbMasculino.isChecked())
                            sexo = 0;
                        else if(rbFemenino.isChecked())
                            sexo = 1;

                        contentValues.put(ContactoUpd.COLUMN_NAME_SEX, sexo);

                        String where = ContactoUpd._ID + " = '" + _id + "'";
                        db.update(ContactoUpd.TABLE_NAME, contentValues, where, null);
                        ContactoDataSource.CargarPersonas();
                        Toast.makeText(null, "Registro editado", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.show();
                return true;

            case R.id.delete:
                //borrar registro
                String where = ContactoUpd._ID + " = '" + _id + "'";
                db.delete(ContactoUpd.TABLE_NAME, where,null);
                ContactoDataSource.CargarPersonas();
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    /*
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_guardar_contacto:
                //Toast.makeText(this,"Guardar",Toast.LENGTH_SHORT).show();
                dataSource.openDB();
                guardarContacto();
                dataSource.closeDB();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    */


    public void guardarContacto(){
        String nombre = etNombre.getText().toString();
        String paterno = etPaterno.getText().toString();
        String materno = etMaterno.getText().toString();
        String telefono = etTelefono.getText().toString();
        int sexo = etSexo.getInputType();

        if(crearContacto(nombre, paterno, materno, telefono, sexo) != -1){
            Toast.makeText(this, "contacto agregado", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();

        }
        else{
            Log.i("AgregarContactoActivity", "error");
        }


    }

    public long crearContacto(String nombre, String paterno, String materno, String telefono, int sexo){
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setPaterno(paterno);
        contacto.setMaterno(materno);
        contacto.setTelefono(telefono);
        contacto.setSexo(sexo);

        contacto = dataSource.insertarContacto(contacto);

        return contacto.getId();
    }
}