package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private EditText editUser, editPass;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        editUser = findViewById(R.id.editUsername);
        editPass = findViewById(R.id.editPassword);
    }

    public void eseguiLogin(View view) {
        String user = editUser.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, getString(R.string.errore_campi_vuoti), Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se l'utente esiste nel database
        String loggedUser = dbHelper.eseguiLoginERecuperaUsername(user, pass);

        if (loggedUser == null) {
            Toast.makeText(this, getString(R.string.errore_credenziali), Toast.LENGTH_SHORT).show();
        } else {
            getSharedPreferences("AppPrefs", MODE_PRIVATE)
                    .edit()
                    .putString("LOGGED_USERNAME", loggedUser)
                    .apply();

            // Carica il carrello dell'utente in modo centralizzato
            Carrello.getInstance().carica(this, loggedUser);

            Toast.makeText(this, "Bentornato, " + loggedUser, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void tornaIndietro(View view) {
        finish();
    }
}