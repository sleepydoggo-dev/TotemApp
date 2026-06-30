package com.example.progettototem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
    private EditText editUsername, editEmail, editPassword, editNome;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);
        editUsername = findViewById(R.id.editUsernameReg);
        editEmail = findViewById(R.id.editEmailReg);
        editPassword = findViewById(R.id.editPasswordReg);
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
        String nome = editNome.getText().toString().trim();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, getString(R.string.errore_campi_vuoti), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, getString(R.string.errore_email_non_valida), Toast.LENGTH_SHORT).show();
            return;
        }


        if (dbHelper.utenteEsiste(user)) {
            Toast.makeText(this, "Errore: Username già in uso. Scegline un altro.", Toast.LENGTH_LONG).show();
            return;
        }

        long id = dbHelper.registraUtente(user, email, pass, nome);
        if (id > 0) {
            getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE)
                    .edit()
                    .putString("LOGGED_USERNAME", user)
                    .apply();
            
            Carrello.getInstance().setNomeUtente(nome != null && !nome.isEmpty() ? nome : user);
            
            Toast.makeText(this, "Registrazione completata!", Toast.LENGTH_SHORT).show();
            
            // Vai alle Categorie dopo la registrazione
            Intent intent = new Intent(this, CategorieActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Errore durante la registrazione", Toast.LENGTH_SHORT).show();
        }
    }


    public void tornaIndietro(View view) {
        finish();
    }
}