package com.example.laboratorio3;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class recuperarPassActivity extends AppCompatActivity {

    AwesomeValidation awesomeValidation;
    private static final String TAG = "recuperarPassActivity";
    private EditText mRestoreEmail;
    private Button mRestoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.et_email_olvidado, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);

        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.example.laboratorio3",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();

        mRestoreEmail = findViewById(R.id.et_email_olvidado);
        mRestoreButton = findViewById(R.id.btn_recuperar_email);

        mRestoreButton.setOnClickListener(view ->{

            String email = mRestoreEmail.getText().toString();

            if (awesomeValidation.validate()) {
                if (!email.isEmpty()) {
                    Log.d(TAG, "Ingreso a : Email.");
                    Log.d(TAG, "Datos de entrada: " + email + " .... " + actionCodeSettings);

                    //ENVIA UN CORREO DE RESTAURACION.
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendSignInLinkToEmail(email, actionCodeSettings)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                } else {
                                    String error_message = task.getException().getMessage();
                                    Log.d(TAG, "Error messaege." + error_message);
                                }
                            });
                } else {
                    Log.d(TAG, "Ingrese un correo.");
                    Toast.makeText(recuperarPassActivity.this, "Ingrese un correo.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.d(TAG, "Ingrese un correo valido.");
                Toast.makeText(recuperarPassActivity.this, "Ingrese un correo valido.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}