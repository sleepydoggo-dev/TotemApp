package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // [BUGFIX] Se l'utente ha appena fatto il login/registrazione, onResume verrà chiamato
        // quando le attività Login/Register finiscono. In tal caso, chiudiamo anche la Home
        // per tornare all'attività che aveva richiesto l'autenticazione (es. Carrello).
        String user = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (user != null) {
            finish();
        }
    }

    public void vaiALogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void vaiARegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void chiudiHome(View view) {
        finish(); // Torna semplicemente al Carrello (o da dove è arrivato)
    }
}