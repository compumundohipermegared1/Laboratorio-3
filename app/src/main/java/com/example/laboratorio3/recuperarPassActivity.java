package com.example.laboratorio3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class recuperarPassActivity extends AppCompatActivity {

    private EditText mRestoreEmail;
    private Button mRestoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);

        //Toma el valor.

        mRestoreEmail = findViewById(R.id.et_restorecorreo);
        mRestoreButton = findViewById(R.id.et_restorebutton);

        mRestoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mRestoreEmail.getText().toString().isEmpty()){
                    //ENVIA UN CORREO DE RESTAURACION.
                }else{
                    Toast.makeText(recuperarPassActivity.this, "Ingrese un correo.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}