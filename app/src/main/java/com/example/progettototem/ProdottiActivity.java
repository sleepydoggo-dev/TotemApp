package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdottiActivity extends BaseActivity {
    private RecyclerView rv;
    private boolean isGridView = false;
    private List<Prodotto> lista;
    private String categoria;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);
        dbHelper = new DatabaseHelper(this);

        TextView t = findViewById(R.id.textNomeCategoria);
        categoria = getIntent().getStringExtra("CATEGORIA");
        if(categoria != null) t.setText(categoria);

        rv = findViewById(R.id.recyclerSingolaCategoria);
        
        // Recupero prodotti dal database in base alla categoria
        lista = dbHelper.getProdottiPerCategoria(categoria);

        aggiornaLayout();
    }

    private void aggiornaLayout() {
        if (isGridView) {
            rv.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            rv.setLayoutManager(new LinearLayoutManager(this));
        }
        ProdottiAdapter adapter = new ProdottiAdapter(this, lista);
        rv.setAdapter(adapter);
    }

    public void cambiaVista(View view) {
        isGridView = !isGridView;
        ImageButton btn = (ImageButton) view;
        if (isGridView) btn.setImageResource(android.R.drawable.ic_dialog_info);
        else btn.setImageResource(android.R.drawable.ic_dialog_dialer);
        aggiornaLayout();
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    public void tornaIndietro(View view) { finish(); }
}