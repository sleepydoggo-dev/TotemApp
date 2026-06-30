package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
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
        if (lang == null || lang.isEmpty()) lang = "it";

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public void apriOpzioni(View view) {
        startActivity(new Intent(this, OpzioniActivity.class));
    }

    public void apriProfilo(View view) {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        if (prefs.getString("LOGGED_USERNAME", null) == null) {
            Toast.makeText(this, "Devi accedere per vedere il tuo profilo", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, StoricoOrdiniActivity.class));
        }
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    public void eseguiLogout(View view) {
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        Carrello.getInstance().svuota();
        Toast.makeText(this, "Logout effettuato", Toast.LENGTH_SHORT).show();

        // Ricarica la pagina iniziale fresca
        Intent intent = new Intent(this, CategorieActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}