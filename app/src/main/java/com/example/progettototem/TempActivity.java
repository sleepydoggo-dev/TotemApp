package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TempActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
    }

    public void procediPagamento(View view) {
        EditText editNome = findViewById(R.id.editTempNome);
        String nome = editNome.getText().toString().trim();
        
        if (!nome.isEmpty()) {
            Carrello.getInstance().setNomeUtente(nome);
        }
        
        startActivity(new Intent(this, PagamentoActivity.class));
    }
    
    public void tornaIndietro(View view) { finish(); }
}
