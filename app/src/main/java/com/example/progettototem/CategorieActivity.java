package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CategorieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_categorie); }

    public void scegliCategoria(View view) {
        Button b = (Button) view;
        Intent i = new Intent(this, ProdottiActivity.class);
        i.putExtra("CATEGORIA", b.getText().toString());
        startActivity(i);
    }
}