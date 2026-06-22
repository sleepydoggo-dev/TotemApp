package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DettagliActivity extends AppCompatActivity {
    private int quantita = 1;
    private double prezzoSingolo = 5.00;
    private TextView textQuantita;
    private Button btnAggiungi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli);

        textQuantita = findViewById(R.id.textQuantita);
        btnAggiungi = findViewById(R.id.btnAggiungiCarrello);
    }

    public void tornaIndietro(View view) {
        finish();
    }

    public void aumentaQuantita(View view) {
        quantita++;
        textQuantita.setText(String.valueOf(quantita));
        aggiornaPulsante();
    }

    public void diminuisciQuantita(View view) {
        if (quantita > 1) {
            quantita--;
            textQuantita.setText(String.valueOf(quantita));
            aggiornaPulsante();
        }
    }

    public void aggiungiAlCarrello(View view) {
        Toast.makeText(this, "Aggiunto al carrello!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    private void aggiornaPulsante() {
        double totale = quantita * prezzoSingolo;
        btnAggiungi.setText(String.format("AGGIUNGI  € %.2f", totale));
    }
}