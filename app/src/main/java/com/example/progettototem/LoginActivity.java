package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends BaseActivity {
    private EditText editUser, editPass;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        editUser = findViewById(R.id.editUsername);
        editPass = findViewById(R.id.editPassword);

        // [QoL] Scroll automatico quando si seleziona un campo di input per evitare che la tastiera copra il form
        setupKeyboardScroll(editUser);
        setupKeyboardScroll(editPass);
    }

    private void setupKeyboardScroll(EditText editText) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                android.widget.ScrollView scrollView = findViewById(R.id.scrollLogin);
                if (scrollView != null) {
                    scrollView.postDelayed(() -> scrollView.smoothScrollTo(0, v.getTop()), 300);
                }
            }
        });
    }

    public void loginGoogle(View view) {
        // [QoL] Placeholder per futura integrazione Google Login
        Toast.makeText(this, "Funzionalità Google non ancora disponibile", Toast.LENGTH_SHORT).show();
    }

    public void eseguiLogin(View view) {
        String user = editUser.getText().toString().trim().toLowerCase();
        String pass = editPass.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, getString(R.string.errore_campi_vuoti), Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se l'utente esiste nel database
        String loggedUser = dbHelper.eseguiLoginERecuperaUsername(user, pass);

        if (loggedUser == null) {
            Toast.makeText(this, getString(R.string.errore_credenziali), Toast.LENGTH_SHORT).show();
        } else {
            // [BUGFIX] Passaggio a "TOTEM_PREFS" per gestire la sessione
            getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE)
                    .edit()
                    .putString("LOGGED_USERNAME", loggedUser)
                    .apply();

            // [QoL] Carica il carrello salvato su cloud/DB e lo unisce a quello locale corrente
            Carrello.getInstance().caricaEUnisci(this, loggedUser);

            Toast.makeText(this, "Bentornato, " + loggedUser, Toast.LENGTH_SHORT).show();

            // [QoL] Redirezione intelligente: se l'utente era nel carrello, lo rimandiamo al pagamento
            Intent intent = new Intent(this, PagamentoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void tornaIndietro(View view) {
        finish();
    }
}