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

        // Salvataggio ordine nello storico e pulizia carrello persistente
        String loggedUser = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (loggedUser != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
                if (!Carrello.getInstance().getProdotti().isEmpty()) {
                    dbHelper.salvaOrdine(loggedUser, Carrello.getInstance().getTotale(), Carrello.getInstance().getProdotti());
                }
                // Svuota il carrello anche nel database persistente
                dbHelper.salvaCarrello(loggedUser, new java.util.ArrayList<>());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {


                Carrello.getInstance().svuota();

            }
        }
    }

    public void tornaAllaHome(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}