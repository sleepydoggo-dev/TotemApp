package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.List;

public class ProdottiActivity extends BaseActivity {
    private RecyclerView rvProdotti;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodotti);

        dbHelper = new DatabaseHelper(this);
        rvProdotti = findViewById(R.id.recyclerProdotti);
        drawerLayout = findViewById(R.id.drawer_layout_prod);
        NavigationView navigationView = findViewById(R.id.nav_view_prod);
        ImageButton btnMenu = findViewById(R.id.btnMenuDrawerProd);

        // Layout a griglia 2 colonne come un totem
        rvProdotti.setLayoutManager(new GridLayoutManager(this, 2));

        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        if (navigationView != null) {
            navigationView.setItemIconTintList(null); // Disabilita il tint automatico per mostrare i colori originali delle icone
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                String categoria = null;
                
                if (id == R.id.nav_panini) categoria = "Panini";
                else if (id == R.id.nav_primi) categoria = "Primi";
                else if (id == R.id.nav_secondi) categoria = "Secondi";
                else if (id == R.id.nav_bevande) categoria = "Bevande";
                else if (id == R.id.nav_opzioni) {
                    startActivity(new Intent(this, OpzioniActivity.class));
                } else if (id == R.id.nav_preferiti) {
                    startActivity(new Intent(this, PreferitiActivity.class));
                } else if (id == R.id.nav_storico) {
                    startActivity(new Intent(this, StoricoOrdiniActivity.class));
                } else if (id == R.id.nav_logout) {
                    eseguiLogout();
                }

                if (categoria != null) {
                    caricaListaProdotti(categoria);
                }
                
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }

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

    public void tornaIndietro(View view) {
        finish();
    }

    private void eseguiLogout() {
        // [BUGFIX] Utilizzo di "TOTEM_PREFS" per il logout nel drawer
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        Carrello.getInstance().svuota();
        Carrello.getInstance().setNomeUtente(null);
        // [QoL] Invece di chiudere l'app, ricarichiamo per mostrare lo stato non loggato
        recreate();
    }
}