package com.example.progettototem;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
    private EditText editUsername, editEmail, editPassword, editConfermaPassword, editNome;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);
        editUsername = findViewById(R.id.editUsernameReg);
        editEmail = findViewById(R.id.editEmailReg);
        editPassword = findViewById(R.id.editPasswordReg);
        editConfermaPassword = findViewById(R.id.editConfermaPassword);
        editNome = findViewById(R.id.editNome);
        setupKeyboardScroll(editUsername);
        setupKeyboardScroll(editEmail);
        setupKeyboardScroll(editPassword);
        setupKeyboardScroll(editNome);
    }

    private void setupKeyboardScroll(EditText editText) {
        // Gestisci il cambio di focus per lo scroll della tastiera
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                android.widget.ScrollView scrollView = findViewById(R.id.myScrollView);

                scrollView.postDelayed(() -> scrollView.smoothScrollTo(0, v.getTop()), 300);
            }
        });
    }

    public void eseguiRegistrazione(View view) {
        String user = editUsername.getText().toString().trim().toLowerCase(); // Se si desidera permettere di creare account con username uguali ma con maiuscole/minuscole diverse togliere toLowerCase()
        String email = editEmail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();
        String confermaPass = editConfermaPassword.getText().toString().trim();
        String nome = editNome.getText().toString().trim();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty() || confermaPass.isEmpty()) {
            Toast.makeText(this, getString(R.string.errore_campi_vuoti), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, getString(R.string.errore_email_non_valida), Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pass.equals(confermaPass)) {
            Toast.makeText(this, getString(R.string.errore_password_non_uguali), Toast.LENGTH_SHORT).show();
        }


        if (dbHelper.utenteEsiste(user)) {
            Toast.makeText(this, "Errore: Username già in uso. Scegline un altro.", Toast.LENGTH_LONG).show();
            return;
        }

        long id = dbHelper.registraUtente(user, email, pass, nome);
        if (id > 0) {
            // [QoL] Auto-login dopo la registrazione: salva la sessione immediatamente
            getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE)
                    .edit()
                    .putString("LOGGED_USERNAME", user)
                    .apply();
            
            // Imposta il nome nel carrello per la ricevuta
            Carrello.getInstance().setNomeUtente(!nome.isEmpty() ? nome : user);

            Toast.makeText(this, "Registrazione completata e accesso eseguito!", Toast.LENGTH_SHORT).show();
            
            // [BUGFIX] Navigazione Back: Chiudiamo semplicemente l'attività di registrazione.
            // Invece di usare CLEAR_TASK (che rompe la cronologia e fa uscire l'app), torniamo indietro.
            finish();
        } else {
            Toast.makeText(this, "Errore durante la registrazione", Toast.LENGTH_SHORT).show();
        }
    }


}