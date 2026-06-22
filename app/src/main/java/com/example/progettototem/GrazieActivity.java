package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GrazieActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazie);

        TextView textGrazie = findViewById(R.id.textGrazieNome);

        Intent intent = getIntent();
        if (intent.hasExtra("NOME_UTENTE")) {
            String nome = intent.getStringExtra("NOME_UTENTE");
            textGrazie.setText("Grazie per l'acquisto, " + nome);
        }
    }

    public void tornaAllaHome(View view) {
        Intent backToHome = new Intent(this, HomeActivity.class);
        backToHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(backToHome);
        finish();
    }
}