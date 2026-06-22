package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tornaIndietro(View view) {
        finish();
    }

    public void eseguiLogin(View view) {
        EditText editUser = findViewById(R.id.editUsername);
        EditText editPass = findViewById(R.id.editPassword);
        TextView textErrore = findViewById(R.id.textErroreLogin);

        String username = editUser.getText().toString();
        String password = editPass.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            textErrore.setVisibility(View.VISIBLE);
            textErrore.setText("Inserisci tutti i campi");
        } else {
            textErrore.setVisibility(View.GONE);
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}