package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CategorieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
    }

    public void scegliCategoria(View view) {
        Button btnCliccato = (Button) view;
        String nomeCategoria = btnCliccato.getText().toString();

        Intent intent = new Intent(this, ProdottiActivity.class);
        intent.putExtra("CATEGORIA", nomeCategoria);
        startActivity(intent);
    }
}