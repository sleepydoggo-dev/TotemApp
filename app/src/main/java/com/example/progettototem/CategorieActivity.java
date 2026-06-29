package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CategorieActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
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