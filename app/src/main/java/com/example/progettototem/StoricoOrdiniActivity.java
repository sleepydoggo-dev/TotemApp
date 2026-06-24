package com.example.progettototem;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoricoOrdiniActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private StoricoAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico_ordini);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerStorico);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String loggedUser = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (loggedUser != null) {
            List<Ordine> ordini = dbHelper.getOrdiniPerUtente(loggedUser);
            if (ordini.isEmpty()) {
                Toast.makeText(this, "Nessun ordine trovato", Toast.LENGTH_SHORT).show();
            }
            adapter = new StoricoAdapter(ordini);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Effettua il login per vedere lo storico", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void tornaIndietro(View view) {
        finish();
    }
}