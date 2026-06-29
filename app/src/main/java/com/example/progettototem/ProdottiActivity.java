package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
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
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);

        if (savedInstanceState != null) {
            isGridView = savedInstanceState.getBoolean("STATO_GRIGLIA", false);
        }

        dbHelper = new DatabaseHelper(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        TextView t = findViewById(R.id.textNomeCategoria);
        categoria = getIntent().getStringExtra("CATEGORIA");
        aggiornaTitoloCategoria(t);

        rv = findViewById(R.id.recyclerSingolaCategoria);
        progressBar = findViewById(R.id.loadingProdotti);

        ImageButton btnMenu = findViewById(R.id.btnMenuDrawer);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_panini) categoria = "Panini";
                else if (id == R.id.nav_primi) categoria = "Primi";
                else if (id == R.id.nav_secondi) categoria = "Secondi";
                else if (id == R.id.nav_bevande) categoria = "Bevande";

                aggiornaTitoloCategoria(t);
                caricaProdotti();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }

        caricaProdotti();
    }

    private void aggiornaTitoloCategoria(TextView t) {
        if (categoria != null && t != null) {
            int resId = getResources().getIdentifier(categoria.toLowerCase(), "string", getPackageName());
            if (resId != 0) t.setText(getString(resId));
            else t.setText(categoria);
        }
    }

    private void caricaProdotti() {
        progressBar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                android.util.Log.e("ErroreApp", "Errore nel caricamento", e);
            }

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("STATO_GRIGLIA", isGridView);
    }
}
