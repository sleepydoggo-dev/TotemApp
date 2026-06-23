package com.example.progettototem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class OpzioniActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opzioni);

        RadioGroup rgAspetto = findViewById(R.id.radioGroupAspetto);
        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE);
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
    }

    public void tornaIndietro(View view) { finish(); }
    public void apriInfo(View view) { Toast.makeText(this, "Info App", Toast.LENGTH_SHORT).show(); }
    public void apriAiuto(View view) { Toast.makeText(this, "Aiuto", Toast.LENGTH_SHORT).show(); }
    public void segnalaBug(View view) { Toast.makeText(this, "Segnalazione", Toast.LENGTH_SHORT).show(); }
    public void lasciaRecensione(View view) { Toast.makeText(this, "Grazie!", Toast.LENGTH_SHORT).show(); }
}
