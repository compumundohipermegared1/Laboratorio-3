package com.example.listaelementos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.listaelementos.models.Contacto;

public class DetalleActivity extends AppCompatActivity {

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}