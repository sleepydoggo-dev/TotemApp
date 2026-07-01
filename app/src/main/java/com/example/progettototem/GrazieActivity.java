package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GrazieActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazie);

        TextView tNome = findViewById(R.id.textGrazieNome);
        String nome = Carrello.getInstance().getNomeUtente();
        // Se il nome è nullo o vuoto, usa il nome di default
        if (nome == null || nome.isEmpty()) {
            nome = getString(R.string.user_name);
        }

        tNome.setText(getString(R.string.thanks, nome));

        // [BUGFIX] Utilizzo del metodo centralizzato per svuotare il carrello in memoria e nel DB
        String loggedUser = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (loggedUser != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
                if (!Carrello.getInstance().getProdotti().isEmpty()) {
                    dbHelper.salvaOrdine(loggedUser, Carrello.getInstance().getTotale(), Carrello.getInstance().getProdotti());
                }
            } catch (Exception e) {
                android.util.Log.e("ErroreApp", "Errore salvataggio ordine", e);
            } finally {
                // Svuota in memoria e pulisce il DB per l'utente corrente
                Carrello.getInstance().svuotaEPulisciDB(this, loggedUser);
            }
        }
    }

    public void tornaAllaHome(View view) {
        Intent i = new Intent(this, CategorieActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}