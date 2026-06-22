package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_home); }

    public void vaiALogin(View view) {
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().putBoolean("IS_GUEST", false).apply();
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void vaiARegistrati(View view) {
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().putBoolean("IS_GUEST", false).apply();
        startActivity(new Intent(this, RegisterActivity.class));
    }
    public void vaiACategorie(View view) {
        getSharedPreferences("TOTEM_PREFS", MODE_PRIVATE).edit().putBoolean("IS_GUEST", true).apply();
        startActivity(new Intent(this, CategorieActivity.class));
    }
}