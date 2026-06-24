package com.example.progettototem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;

public class OpzioniActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opzioni);

        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE);
        RadioGroup rgAspetto = findViewById(R.id.radioGroupAspetto);
        // Setta l'aspetto corrente nella RadioGroup
        int currentTheme = prefs.getInt("THEME_MODE", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        if (currentTheme == AppCompatDelegate.MODE_NIGHT_NO) rgAspetto.check(R.id.radioTemaChiaro);
        else if (currentTheme == AppCompatDelegate.MODE_NIGHT_YES) rgAspetto.check(R.id.radioTemaScuro);
        else rgAspetto.check(R.id.radioTemaSistema);
        rgAspetto.setOnCheckedChangeListener((group, checkedId) -> {
            int mode;
            if (checkedId == R.id.radioTemaChiaro) mode = AppCompatDelegate.MODE_NIGHT_NO;
            else if (checkedId == R.id.radioTemaScuro) mode = AppCompatDelegate.MODE_NIGHT_YES;
            else mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;

            prefs.edit().putInt("THEME_MODE", mode).apply();
            AppCompatDelegate.setDefaultNightMode(mode);
        });

        // Lingua
        RadioGroup rgLingua = findViewById(R.id.radioGroupLingua);
        String currentLang = prefs.getString("APP_LANG", "it");
        // Setta la lingua corrente nella RadioGroup
        if (currentLang.equals("en")) rgLingua.check(R.id.radioLangEnglish);
        else rgLingua.check(R.id.radioLangItaliano);
        // Gestisci il cambio di lingua
        rgLingua.setOnCheckedChangeListener((group, checkedId) -> {
            String lang = (checkedId == R.id.radioLangEnglish) ? "en" : "it";
            // Salva la lingua corrente nel SharedPreferences
            if (!lang.equals(currentLang)) {
                prefs.edit().putString("APP_LANG", lang).apply();
                // Riavvia l'applicazione partendo dalla MainActivity per aggiornare tutto lo stack
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void tornaIndietro(View view) { finish(); }
    public void apriInfo(View view) { Toast.makeText(this, getString(R.string.app_info), Toast.LENGTH_SHORT).show(); }
    public void apriAiuto(View view) { Toast.makeText(this, getString(R.string.help), Toast.LENGTH_SHORT).show(); }
    public void segnalaBug(View view) { Toast.makeText(this, getString(R.string.report_bug), Toast.LENGTH_SHORT).show(); }
    public void lasciaRecensione(View view) { Toast.makeText(this, getString(R.string.review), Toast.LENGTH_SHORT).show(); }
}