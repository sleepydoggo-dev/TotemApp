package com.example.progettototem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PreferitiActivity extends BaseActivity {
    private RecyclerView rv;
    private TextView tVuoto;
    private DatabaseHelper dbHelper;

    private List<Prodotto> lista;
    private boolean isGridView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferiti);

        if (savedInstanceState != null) {
            isGridView = savedInstanceState.getBoolean("STATO_GRIGLIA", false);
        }

        dbHelper = new DatabaseHelper(this);
        rv = findViewById(R.id.recyclerPreferiti);
        tVuoto = findViewById(R.id.textNessunPreferito);

        caricaPreferiti();
    }

    private void caricaPreferiti() {
        String user = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (user != null) {
            lista = dbHelper.getPreferiti(user);
            if (lista.isEmpty()) {
                tVuoto.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            } else {
                tVuoto.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                aggiornaLayout();
            }
        } else {
            finish();
        }
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

    public void tornaIndietro(View view) { finish(); }
    
    @Override
    protected void onResume() {
        super.onResume();
        caricaPreferiti();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("STATO_GRIGLIA", isGridView);
    }
}
