package com.example.laboratorio3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laboratorio3.models.Contacto;

public class DetalleActivity extends AppCompatActivity {

    ImageView sexo;
    TextView tvNombre, tvPaterno, tvMaterno, tvTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        setTitle("Detalle contacto");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        Contacto contacto = (Contacto) intent.getSerializableExtra("contacto");
        String nombre = contacto.getNombre();

        tvNombre = findViewById(R.id.tvNombre);
        tvPaterno = findViewById(R.id.tvPaterno);
        tvMaterno = findViewById(R.id.tvMaterno);
        tvTelefono = findViewById(R.id.tvTelefono);

        tvNombre.setText(nombre);
        tvPaterno.setText(contacto.getPaterno());
        tvMaterno.setText(contacto.getMaterno());
        tvTelefono.setText(contacto.getTelefono());

        Log.i("DetalleActivity", "Nombre recibido: " + nombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_eliminar:
                Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void eliminarContacto(){
        //id del contacto
    }
}