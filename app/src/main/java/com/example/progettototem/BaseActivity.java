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
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
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
}