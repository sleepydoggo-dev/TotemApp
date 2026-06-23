package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategorieActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
    }

    public void scegliCategoria(View view) {
        Button b = (Button) view;
        String categoria = b.getText().toString();
        
        Intent intent = new Intent(this, ProdottiActivity.class);
        intent.putExtra("CATEGORIA", categoria);
        startActivity(intent);
    }
    
    public void tornaIndietro(View view) { finish(); }

    public void apriCarrello(View view) {
        startActivity(new Intent(this, CarrelloActivity.class));
    }
}