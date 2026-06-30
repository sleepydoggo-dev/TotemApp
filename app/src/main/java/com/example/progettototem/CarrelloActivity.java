package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;

public class CarrelloActivity extends BaseActivity {
    private CarrelloAdapter adapter;
    private TextView tTotale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);

        RecyclerView rv = findViewById(R.id.recyclerCarrello);
        rv.setLayoutManager(new LinearLayoutManager(this));

        tTotale = findViewById(R.id.textTotalePrezzo);

        Carrello carrello = Carrello.getInstance();
        // [QoL] Passiamo il listener all'adapter per aggiornare il totale in tempo reale quando cambiano le quantità
        adapter = new CarrelloAdapter(this, carrello.getProdotti(), this::aggiornaTotale);
        rv.setAdapter(adapter);

        aggiornaTotale();
    }

    public void aggiornaTotale() {
        Carrello carrello = Carrello.getInstance();
        // Uso di String.format diretto per evitare errori con le risorse strings.xml
        tTotale.setText(String.format(Locale.getDefault(), "€ %.2f", carrello.getTotale()));
    }

    public void procediAlCheckout(View view) {
        Carrello carrello = Carrello.getInstance();
        if (carrello.getProdotti().isEmpty()) {
            Toast.makeText(this, "Il carrello è vuoto!", Toast.LENGTH_SHORT).show();
            return;
        }

        // [BUGFIX] Utilizzo del file di preferenze unificato "TOTEM_PREFS"
        // Precedentemente usava "AppPrefs", causando mancate rilevazioni del logout
        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", Context.MODE_PRIVATE);
        String user = prefs.getString("LOGGED_USERNAME", null);

        if (user != null) {
            // Loggato -> Vai alla cassa
            startActivity(new Intent(this, PagamentoActivity.class));
        } else {
            // NON Loggato -> Vai alla Barriera di Autenticazione (HomeActivity)
            // [QoL] Messaggio più chiaro per l'utente
            Toast.makeText(this, "Accedi o Registrati per completare l'ordine", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void tornaIndietro(View view) {
        finish();
    }
}