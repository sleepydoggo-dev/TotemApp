package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Verifica se l'utente è già loggato
        String user = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        
        // Se l'utente è loggato, andiamo alla CategorieActivity
        if (user != null) {
            Carrello.getInstance().carica(this, user);
            startActivity(new Intent(this, CategorieActivity.class));
            finish();
            return;
        }

        // Mostriamo la Home per fare Login/Register (punto obbligato ora)
        setContentView(R.layout.activity_home);
    }

    public void vaiALogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void vaiARegistrati(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
