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
    private CarrelloAdapter adapter;

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
        adapter = new CarrelloAdapter(this, carrello.getProdotti(), this::aggiornaTotale);
        rv.setAdapter(adapter);

        aggiornaTotale();
    }

    private void aggiornaTotale() {
        tTotale.setText("€ " + String.format("%.2f", carrello.getTotale()));
    }

    public void procediAlCheckout(View view) {
        if (carrello.getProdotti().isEmpty()){
            Toast.makeText(this, "Il carrello è vuoto!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isGuest = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getBoolean("IS_GUEST", false);
        if (isGuest) {
            startActivity(new Intent(this, TempActivity.class));
        } else {
            startActivity(new Intent(this, PagamentoActivity.class));
        }
    }
    
    public void tornaIndietro(View view) { finish(); }
}
