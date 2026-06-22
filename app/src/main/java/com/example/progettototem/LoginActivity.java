package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_login); }

    public void tornaIndietro(View view) { finish(); }
    public void eseguiLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}