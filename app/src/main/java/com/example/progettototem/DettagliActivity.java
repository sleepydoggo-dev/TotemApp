package com.example.progettototem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DettagliActivity extends BaseActivity {
    private String nome;
    private double prezzoUnitario;
    private String descrizione;
    private String immagineKey;
    private int quantita = 1;

    private String loggedUser;
    private TextView tQuantita;
    private Button btnAggiungi;
    private android.widget.ImageButton btnPreferito;
    private LinearLayout containerAttributi;
    private List<Attributo> listaAttributi = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private boolean isPreferito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli);

        dbHelper = new DatabaseHelper(this);
        loggedUser = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getString("LOGGED_USERNAME", null);

        nome = getIntent().getStringExtra("NOME");
        prezzoUnitario = getIntent().getDoubleExtra("PREZZO", 0.0);
        descrizione = getIntent().getStringExtra("DESC");
        immagineKey = getIntent().getStringExtra("IMG");

        TextView tNome = findViewById(R.id.textNomeDettaglio);
        TextView tDesc = findViewById(R.id.textDescrizioneDettaglio);
        android.widget.ImageView imgProd = findViewById(R.id.imgProdottoDettaglio);
        tQuantita = findViewById(R.id.textQuantita);
        btnAggiungi = findViewById(R.id.btnAggiungiCarrello);
        btnPreferito = findViewById(R.id.btnPreferitiDettaglio);
        containerAttributi = findViewById(R.id.containerAttributi);

        tNome.setText(nome);
        tDesc.setText(descrizione);

        if (immagineKey != null && imgProd != null) {
            int imgResId = getResources().getIdentifier(immagineKey, "drawable", getPackageName());
            if (imgResId != 0) imgProd.setImageResource(imgResId);
        }

        if (loggedUser != null) {
            isPreferito = dbHelper.isPreferito(loggedUser, nome);
            aggiornaIconaPreferito();
        } else {
            // [QoL] Invece di nasconderlo, lo lasciamo visibile ma con feedback al click
            isPreferito = false;
            aggiornaIconaPreferito();
        }

        caricaAttributi();
        aggiornaPrezzo();
    }

    private void aggiornaIconaPreferito() {
        if (btnPreferito != null) {
            btnPreferito.setImageResource(isPreferito ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
        }
    }

    public void gestisciPreferito(View view) {
        if (loggedUser == null) {
            // [QoL] Feedback immediato se l'utente tenta di usare i preferiti senza login
            Toast.makeText(this, "Accedi per salvare questo prodotto nei preferiti", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (isPreferito) {
            dbHelper.rimuoviPreferito(loggedUser, nome);
            Toast.makeText(this, R.string.rimosso_preferiti, Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.aggiungiPreferito(loggedUser, new Prodotto(nome, prezzoUnitario, descrizione, immagineKey));
            Toast.makeText(this, R.string.aggiunto_preferiti, Toast.LENGTH_SHORT).show();
        }
        isPreferito = !isPreferito;
        aggiornaIconaPreferito();
    }

    private void caricaAttributi() {
        listaAttributi = dbHelper.getAttributiPerProdotto(nome);
        if (listaAttributi.isEmpty()) {
            findViewById(R.id.labelPersonalizza).setVisibility(View.GONE);
        } else {
            for (Attributo attr : listaAttributi) {
                CheckBox cb = new CheckBox(this);
                cb.setText(attr.toString());
                cb.setTextSize(16);
                cb.setPadding(0, 10, 0, 10);
                cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    attr.setSelezionato(isChecked);
                    aggiornaPrezzo();
                });
                containerAttributi.addView(cb);
            }
        }
    }

    private void aggiornaPrezzo() {
        double prezzoExtra = 0;
        for (Attributo a : listaAttributi) {
            if (a.isSelezionato()) {
                prezzoExtra += a.getPrezzoExtra();
            }
        }
        double totale = (prezzoUnitario + prezzoExtra) * quantita;
        btnAggiungi.setText(String.format(Locale.getDefault(), "AGGIUNGI  € %.2f", totale));
        tQuantita.setText(String.valueOf(quantita));
    }

    public void tornaIndietro(View view) {
        finish();
    }

    public void aumentaQuantita(View view) {
        quantita++;
        aggiornaPrezzo();
    }

    public void diminuisciQuantita(View view) {
        if (quantita > 1) {
            quantita--;
            aggiornaPrezzo();
        }
    }

    public void aggiungiAlCarrello(View view) {
        // In una versione più completa, ProdottoOrdinato dovrebbe salvare anche gli attributi selezionati
        Prodotto p = new Prodotto(nome, prezzoUnitario, descrizione, immagineKey);
        
        // Calcoliamo il prezzo finale comprensivo di extra per il carrello
        double prezzoExtra = 0;
        StringBuilder sbExtra = new StringBuilder();
        for (Attributo a : listaAttributi) {
            if (a.isSelezionato()) {
                prezzoExtra += a.getPrezzoExtra();
                if (sbExtra.length() > 0) sbExtra.append(", ");
                sbExtra.append(a.getNome());
            }
        }
        
        if (sbExtra.length() > 0) {
            p.nome += " (" + sbExtra + ")";
        }
        p.prezzo += prezzoExtra;

        Carrello.getInstance().aggiungiProdotto(p, quantita, this);
        Toast.makeText(this, R.string.added_to_cart_msg, Toast.LENGTH_SHORT).show();
        finish();
    }
}