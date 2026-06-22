package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ProdottiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);

        TextView t = findViewById(R.id.textNomeCategoria);
        if(getIntent().hasExtra("CATEGORIA")) t.setText(getIntent().getStringExtra("CATEGORIA"));

        RecyclerView rv = findViewById(R.id.recyclerSingolaCategoria);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Dati di prova per verificare che tutto funzioni
        List<Prodotto> lista = new ArrayList<>();
        lista.add(new Prodotto("Hamburger", 5.00, "Manzo scelto, insalata e pomodoro."));
        lista.add(new Prodotto("Patatine", 2.50, "Croccanti e salate."));
        lista.add(new Prodotto("Bibita", 2.00, "Coca Cola o Fanta."));

        ProdottiAdapter adapter = new ProdottiAdapter(this, lista);
        rv.setAdapter(adapter);
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    public void tornaIndietro(View view) { finish(); }
    public void cambiaVista(View view) { /* Logica griglia */ }
}