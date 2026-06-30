package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CategorieActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        
        // [QoL] Gestione dinamica della visibilità del tasto logout nella Top Bar
        aggiornaStatoLogout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // [QoL] Ricarichiamo lo stato al ritorno da altre activity (es. dopo il login)
        aggiornaStatoLogout();
    }

    private void aggiornaStatoLogout() {
        View btnLogout = findViewById(R.id.btnLogout);
        if (btnLogout != null) {
            String user = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
            btnLogout.setVisibility(user != null ? View.VISIBLE : View.GONE);
        }
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

    public void tornaIndietro(View view) { finish(); }
}