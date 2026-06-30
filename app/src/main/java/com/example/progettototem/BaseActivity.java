package com.example.progettototem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyLocale();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyLocale();
    }

    private void applyLocale() {
        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE);
        String lang = prefs.getString("APP_LANG", "it");

        if (lang.isEmpty()) {
            lang = "it";
        }

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);

        // Aggiorna sia il contesto dell'attività che quello dell'applicazione
        res.updateConfiguration(config, res.getDisplayMetrics());
        getApplicationContext().getResources().updateConfiguration(config, res.getDisplayMetrics());

        // Forza anche la direzione del layout (LTR per italiano)
        config.setLayoutDirection(locale);
    }

    // Metodi condivisi per la Top Bar
    public void apriOpzioni(View view) {
        startActivity(new Intent(this, OpzioniActivity.class));
    }

    public void apriProfilo(View view) {
        startActivity(new Intent(this, StoricoOrdiniActivity.class));
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    public void eseguiLogout(View view) {
        // Rimuove l'utente loggato dal file di preferenze unificato
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        
        // Svuota il carrello in memoria e il nome utente
        Carrello.getInstance().svuota();
        Carrello.getInstance().setNomeUtente(null);
        
        // Messaggio di feedback
        android.widget.Toast.makeText(this, getString(R.string.logout) + " effettuato", android.widget.Toast.LENGTH_SHORT).show();

        // Ricarica l'attività corrente per aggiornare la UI (il tasto logout sparirà)
        recreate();
    }
}