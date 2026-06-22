package com.example.progettototem;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OpzioniActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opzioni);
    }
    public void tornaIndietro(View view) { finish(); }
    public void apriInfo(View view) { Toast.makeText(this, "Info App", Toast.LENGTH_SHORT).show(); }
    public void apriAiuto(View view) { Toast.makeText(this, "Aiuto", Toast.LENGTH_SHORT).show(); }
    public void segnalaBug(View view) { Toast.makeText(this, "Segnalazione", Toast.LENGTH_SHORT).show(); }
    public void lasciaRecensione(View view) { Toast.makeText(this, "Grazie!", Toast.LENGTH_SHORT).show(); }
}