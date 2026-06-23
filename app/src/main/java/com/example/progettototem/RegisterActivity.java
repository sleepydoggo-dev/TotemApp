package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);
    }

    public void tornaIndietro(View view) { finish(); }

    public void eseguiRegistrazione(View view) {
        EditText editUser = findViewById(R.id.editUsernameReg);
        EditText editEmail = findViewById(R.id.editEmailReg);
        EditText editPass = findViewById(R.id.editPasswordReg);
        EditText editConf = findViewById(R.id.editConfermaPassword);
        EditText editNome = findViewById(R.id.editNome);

        String user = editUser.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString();
        String conf = editConf.getText().toString();
        String nome = editNome.getText().toString().trim();

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Compila tutti i campi obbligatori", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(pass)) {
            Toast.makeText(this, "La password non rispetta i requisiti", Toast.LENGTH_LONG).show();
            return;
        }

        if (!pass.equals(conf)) {
            Toast.makeText(this, "Le password non coincidono", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.registraUtente(user, email, pass, nome);
        if (result > 0) {
            Toast.makeText(this, "Registrazione completata! Accedi ora.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Errore nella registrazione", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        String specialChars = "@$!%*?&._-";
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (specialChars.indexOf(c) != -1) hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial && password.length() >= 4;
    }

    public void loginGoogle(View view) {
        Toast.makeText(this, "Accesso con Google non configurato", Toast.LENGTH_SHORT).show();
    }
}