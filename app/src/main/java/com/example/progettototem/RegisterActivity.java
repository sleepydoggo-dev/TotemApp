package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_register); }

    public void tornaIndietro(View view) { finish(); }
    public void eseguiRegistrazione(View view) {
        EditText u = findViewById(R.id.editUsernameReg);
        if(u.getText().toString().isEmpty()) {
            Toast.makeText(this, "Inserisci almeno il nome utente", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}