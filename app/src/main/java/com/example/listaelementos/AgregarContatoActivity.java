package com.example.listaelementos;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listaelementos.db.ContactoDataSource;
import com.example.listaelementos.models.Contacto;

public class AgregarContatoActivity extends AppCompatActivity {

    EditText etNombre, etPaterno, etMaterno, etTelefono;
    ContactoDataSource dataSource;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_agregar_contacto_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

    public void guardarContacto(){
        String nombre = etNombre.getText().toString();
        String paterno = etPaterno.getText().toString();
        String materno = etMaterno.getText().toString();
        String telefono = etTelefono.getText().toString();

        if(crearContacto(nombre, paterno, materno, telefono) != -1){
            Toast.makeText(this, "contacto agregado", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();

        }
        else{
            Log.i("AgregarContactoActivity", "error");
        }


    }

    public long crearContacto(String nombre,String paterno,String materno,String telefono){
        Contacto contacto = new Contacto();
        contacto.setNombre(nombre);
        contacto.setPaterno(paterno);
        contacto.setMaterno(materno);
        contacto.setTelefono(telefono);

        contacto = dataSource.insertarContacto(contacto);

        return contacto.getId();
    }
}