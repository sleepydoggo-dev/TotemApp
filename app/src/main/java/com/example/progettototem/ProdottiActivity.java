package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdottiActivity extends BaseActivity {
    private RecyclerView rvProdotti;
    private ProdottiAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);

        dbHelper = new DatabaseHelper(this);
        rvProdotti = findViewById(R.id.recyclerProdotti);

        // Layout a griglia 2 colonne come un totem
        rvProdotti.setLayoutManager(new GridLayoutManager(this, 2));

        Spinner spinner = findViewById(R.id.spinnerCategorie);
        String[] categorie = {"Panini", "Primi", "Secondi", "Bevande"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorie);
        spinner.setAdapter(spinnerAdapter);

        // Capiamo da quale categoria arriviamo e selezioniamo la voce giusta nella tendina
        String catAttuale = getIntent().getStringExtra("CATEGORIA");
        if (catAttuale != null) {
            int pos = java.util.Arrays.asList(categorie).indexOf(catAttuale);
            spinner.setSelection(pos);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selezionata = categorie[position];
                caricaListaProdotti(selezionata);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void caricaListaProdotti(String categoria) {
        List<Prodotto> lista = dbHelper.getProdottiPerCategoria(categoria);
        adapter = new ProdottiAdapter(this, lista);
        rvProdotti.setAdapter(adapter);
    }
}