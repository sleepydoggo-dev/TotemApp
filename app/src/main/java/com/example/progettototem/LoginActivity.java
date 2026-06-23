package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
    }

    public void tornaIndietro(View view) { finish(); }

    public void eseguiLogin(View view) {
        EditText editUser = findViewById(R.id.editUsername);
        EditText editPass = findViewById(R.id.editPassword);
        
        String identifier = editUser.getText().toString().trim();
        String password = editPass.getText().toString();
        
        if (identifier.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.login(identifier, password)) {
            // Salva sessione (identifier per recupero nome/carta)
            getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit()
                    .putString("LOGGED_USER", identifier)
                    .putBoolean("IS_GUEST", false)
                    .apply();

            Carrello.getInstance().setNomeUtente(identifier);

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            findViewById(R.id.textErroreLogin).setVisibility(View.VISIBLE);
            Toast.makeText(this, "Credenziali errate o utente non registrato", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginGoogle(View view) {
        Toast.makeText(this, "Accesso con Google non configurato", Toast.LENGTH_SHORT).show();
    }
}