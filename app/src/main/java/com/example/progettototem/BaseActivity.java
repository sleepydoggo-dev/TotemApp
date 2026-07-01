package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyLocale();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyLocale();
        setupGlobalDrawer();
    }

    private void applyLocale() {
        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE);
        String lang = prefs.getString("APP_LANG", "it");
        if (lang.isEmpty()) lang = "it";

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    protected void setupGlobalDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout_global);
        navigationView = findViewById(R.id.nav_view_global);
        View btnMenu = findViewById(R.id.btnMenuDrawer);

        if (drawerLayout != null && btnMenu != null) {
            btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        String user = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        boolean isLoggedIn = user != null;

        if (navigationView != null) {
            // Aggiorna nome utente nell'header del drawer
            View headerView = navigationView.getHeaderView(0);
            if (headerView != null) {
                TextView tUser = headerView.findViewById(R.id.drawer_username);
                if (tUser != null) {
                    tUser.setText(isLoggedIn ? user : "Ospite");
                }
            }

            // Gestione visibilità voci menu
            navigationView.getMenu().findItem(R.id.nav_login_std).setVisible(!isLoggedIn);
            navigationView.getMenu().findItem(R.id.nav_profilo_std).setVisible(isLoggedIn);
            navigationView.getMenu().findItem(R.id.nav_carrello_std).setVisible(isLoggedIn);
            navigationView.getMenu().findItem(R.id.nav_opzioni_std).setVisible(isLoggedIn);
            navigationView.getMenu().findItem(R.id.nav_logout_std).setVisible(isLoggedIn);

            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home_cat) {
                    Intent intent = new Intent(this, CategorieActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                } else if (id == R.id.nav_login_std) {
                    startActivity(new Intent(this, HomeActivity.class));
                } else if (id == R.id.nav_profilo_std) {
                    apriProfilo(null);
                } else if (id == R.id.nav_carrello_std) {
                    apriCarrello(null);
                } else if (id == R.id.nav_opzioni_std) {
                    apriOpzioni(null);
                } else if (id == R.id.nav_logout_std) {
                    eseguiLogout(null);
                }
                if (drawerLayout != null) drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }
    }

    public void tornaIndietro(View view) {
        finish();
    }

    public void apriOpzioni(View view) {
        startActivity(new Intent(this, OpzioniActivity.class));
    }

    public void apriProfilo(View view) {
        SharedPreferences prefs = getSharedPreferences("TOTEM_PREFS", Context.MODE_PRIVATE);
        if (prefs.getString("LOGGED_USERNAME", null) == null) {
            Toast.makeText(this, "Devi accedere per vedere il tuo profilo", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, StoricoOrdiniActivity.class));
        }
    }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }

    public void eseguiLogout(View view) {
        // Rimuove l'utente loggato dal file di preferenze unificato
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().remove("LOGGED_USERNAME").apply();
        
        // Svuotiamo il carrello in memoria
        Carrello.getInstance().svuota();
        Carrello.getInstance().setNomeUtente(null);
        
        Toast.makeText(this, "Logout effettuato", Toast.LENGTH_SHORT).show();

        // Dopo il logout, puliamo lo stack per evitare che il tasto "Back" riporti a pagine protette
        Intent intent = new Intent(this, CategorieActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}