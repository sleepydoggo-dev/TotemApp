package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Gestisci la visibilità del tasto logout
        String user = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        View btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            btnLogout.setVisibility(user != null ? View.VISIBLE : View.GONE);
        }
    }

    public void vaiAOrdina(View view) {
        startActivity(new Intent(this, CategorieActivity.class));
    }

    public void vaiAOpzioni(View view) {
        startActivity(new Intent(this, OpzioniActivity.class));
    }

    public void vaiAStorico(View view) {
        startActivity(new Intent(this, StoricoOrdiniActivity.class));
    }

    public void vaiAPreferiti(View view) {
        startActivity(new Intent(this, PreferitiActivity.class));
    }

    public void eseguiLogout(View view) {
        // Rimuove l'utente loggato dalle SharedPreferences
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        
        // Svuota il carrello in memoria e il nome utente
        Carrello.getInstance().svuota();
        Carrello.getInstance().setNomeUtente(null);
        
        // Ricarica la MainActivity per aggiornare la UI (il tasto logout sparirà)
        recreate();
    }
}
