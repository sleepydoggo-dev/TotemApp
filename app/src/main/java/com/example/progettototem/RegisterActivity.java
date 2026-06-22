package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void tornaIndietro(View view) {
        finish();
    }


    public void eseguiRegistrazione(View view) {

        EditText editPassword = findViewById(R.id.editPasswordReg);
        EditText editConferma = findViewById(R.id.editConfermaPassword);

        String password = editPassword.getText().toString();
        String conferma = editConferma.getText().toString();


        if (!password.equals(conferma)) {

            Toast.makeText(this, "Attenzione: le password non combaciano!", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "Registrazione completata!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void loginGoogle(View view) {
        Toast.makeText(this, "Accesso con Google effettuato", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}