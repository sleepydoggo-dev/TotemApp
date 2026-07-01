package com.example.progettototem;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdottiActivity extends BaseActivity {
    private RecyclerView rvProdotti;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);

        dbHelper = new DatabaseHelper(this);
        rvProdotti = findViewById(R.id.recyclerProdotti);

        // Layout a griglia 2 colonne come un totem
        rvProdotti.setLayoutManager(new GridLayoutManager(this, 2));

        // Capiamo da quale categoria arriviamo e carichiamo i prodotti
        String catAttuale = getIntent().getStringExtra("CATEGORIA");
        if (catAttuale == null) catAttuale = "Panini"; // Default
        caricaListaProdotti(catAttuale);
    }

    private void caricaListaProdotti(String categoria) {
        List<Prodotto> lista = dbHelper.getProdottiPerCategoria(categoria);
        ProdottiAdapter adapter = new ProdottiAdapter(this, lista);
        rvProdotti.setAdapter(adapter);
    }
}