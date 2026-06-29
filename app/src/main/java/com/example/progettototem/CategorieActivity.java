package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class CategorieActivity extends BaseActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        drawerLayout = findViewById(R.id.drawer_layout_cat);
        NavigationView navigationView = findViewById(R.id.nav_view_cat);
        ImageButton btnMenu = findViewById(R.id.btnMenuDrawerCat);

        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        if (navigationView != null) {
            // Gestione del logout e visibilità menu
            String user = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(user != null);
            navigationView.getMenu().findItem(R.id.nav_preferiti).setVisible(user != null);
            navigationView.getMenu().findItem(R.id.nav_storico).setVisible(user != null);

            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_opzioni) {
                    startActivity(new Intent(this, OpzioniActivity.class));
                } else if (id == R.id.nav_preferiti) {
                    startActivity(new Intent(this, PreferitiActivity.class));
                } else if (id == R.id.nav_storico) {
                    startActivity(new Intent(this, StoricoOrdiniActivity.class));
                } else if (id == R.id.nav_logout) {
                    eseguiLogout();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }
    }

    private void eseguiLogout() {
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        Carrello.getInstance().svuota();
        Carrello.getInstance().setNomeUtente(null);
        recreate();
    }

    public void scegliCategoria(View view) {
        String catKey = "";
        int id = view.getId();
        if (id == R.id.btnCat1) catKey = "Panini";
        else if (id == R.id.btnCat2) catKey = "Primi";
        else if (id == R.id.btnCat3) catKey = "Secondi";
        else if (id == R.id.btnCat4) catKey = "Bevande";
        
        Intent intent = new Intent(this, ProdottiActivity.class);
        intent.putExtra("CATEGORIA", catKey);
        startActivity(intent);
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }
    
    public void tornaIndietro(View view) { finish(); }
}
