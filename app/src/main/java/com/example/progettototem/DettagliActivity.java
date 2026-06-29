package com.example.progettototem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DettagliActivity extends BaseActivity {
    private String nome;
    private double prezzoUnitario;
    private String descrizione;
    private String immagineKey;
    private int quantita = 1;

    private TextView tQuantita;
    private Button btnAggiungi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettagli);


        nome = getIntent().getStringExtra("NOME");
        prezzoUnitario = getIntent().getDoubleExtra("PREZZO", 0.0);
        descrizione = getIntent().getStringExtra("DESC");
        immagineKey = getIntent().getStringExtra("IMG");

        TextView tNome = findViewById(R.id.textNomeDettaglio);
        TextView tDesc = findViewById(R.id.textDescrizioneDettaglio);
        android.widget.ImageView imgProd = findViewById(R.id.imgProdottoDettaglio);
        tQuantita = findViewById(R.id.textQuantita);
        btnAggiungi = findViewById(R.id.btnAggiungiCarrello);

        tNome.setText(nome);
        tDesc.setText(descrizione);

        if (immagineKey != null && imgProd != null) {
            int imgResId = getResources().getIdentifier(immagineKey, "drawable", getPackageName());
            if (imgResId != 0) imgProd.setImageResource(imgResId);
        }

        aggiornaPrezzo();
    }

    private void aggiornaPrezzo() {
        double totale = prezzoUnitario * quantita;
        btnAggiungi.setText(getString(R.string.add_to_cart_btn_format, totale));
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
        Prodotto p = new Prodotto(nome, prezzoUnitario, descrizione, immagineKey);
        Carrello.getInstance().aggiungiProdotto(p, quantita, this);
        Toast.makeText(this, R.string.added_to_cart_msg, Toast.LENGTH_SHORT).show();
        finish();
    }
}
