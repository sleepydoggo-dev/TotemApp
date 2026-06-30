package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CarrelloActivity extends BaseActivity {
    private TextView tTotale;
    private Carrello carrello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);

        // Ottieni l'istanza del carrello
        carrello = Carrello.getInstance();
        tTotale = findViewById(R.id.textTotalePrezzo);
        RecyclerView rv = findViewById(R.id.recyclerCarrello);
        // Imposta il layout manager e l'adapter per il RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        CarrelloAdapter adapter = new CarrelloAdapter(this, carrello.getProdotti(), this::aggiornaTotale);
        rv.setAdapter(adapter);

        aggiornaTotale();
    }

    private void aggiornaTotale() {
        tTotale.setText(getString(R.string.price_format, carrello.getTotale()));
    }

    public void procediAlCheckout(View view) {
        if (carrello.getProdotti().isEmpty()){
            Toast.makeText(this, "Il carrello è vuoto!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se l'utente è già loggato tramite il file unificato
        String user = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (user != null) {
            // Se loggato, vai direttamente al pagamento
            startActivity(new Intent(this, PagamentoActivity.class));
        } else {
            // Altrimenti vai alla Home per fare Login/Register
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
    
    public void tornaIndietro(View view) { finish(); }
}
