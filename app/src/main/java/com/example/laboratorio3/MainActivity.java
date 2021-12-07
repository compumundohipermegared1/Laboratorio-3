package com.example.laboratorio3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laboratorio3.adapters.ContactoAdapter;
import com.example.laboratorio3.db.ContactoDataSource;
import com.example.laboratorio3.models.Contacto;
import com.example.listaelementos.adapters.ContactoAdapter;
import com.example.listaelementos.db.ContactoDataSource;
import com.example.listaelementos.models.Contacto;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int REQUEST_CODE_AGREGAR_CONTACTO = 1001;
    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_DETALLE_ACTIVITY = 1002;
    ListView lvContactos;
    List<Contacto> contactos;
    ContactoDataSource dataSource;
    ArrayAdapter<Contacto> adapter;

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