package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProdottiActivity extends BaseActivity {
    private RecyclerView rv;
    private ProgressBar progressBar;
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
        progressBar = findViewById(R.id.loadingProdotti);

        caricaProdotti();
    }

    private void caricaProdotti() {
        progressBar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Simuliamo un ritardo per far vedere la barra di caricamento (facoltativo)
            try { Thread.sleep(800); } catch (InterruptedException e) { e.printStackTrace(); }
            
            lista = dbHelper.getProdottiPerCategoria(categoria);

            handler.post(() -> {
                progressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                aggiornaLayout();
            });
        });
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