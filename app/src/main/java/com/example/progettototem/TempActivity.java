package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class TempActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
    }

    public void procediPagamento(View view) {
        EditText editNome = findViewById(R.id.editTempNome);
        String nomeInserito = editNome.getText().toString();

        Intent intent = new Intent(this, PagamentoActivity.class);
        intent.putExtra("NOME_UTENTE", nomeInserito);
        startActivity(intent);
    }
}