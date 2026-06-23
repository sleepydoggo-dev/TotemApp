package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GrazieActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazie);

        TextView tNome = findViewById(R.id.textGrazieNome);
        String nome = Carrello.getInstance().getNomeUtente();
        
        if (nome == null || nome.isEmpty()) {
            boolean isGuest = getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).getBoolean("IS_GUEST", false);
            if (isGuest) nome = "Ospite";
            else nome = "Utente"; // Fallback se non salvato
        }

        tNome.setText("Grazie per l'acquisto, " + nome);
        
        // Svuota carrello dopo il pagamento
        Carrello.getInstance().svuota();
    }

    public void tornaAllaHome(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
