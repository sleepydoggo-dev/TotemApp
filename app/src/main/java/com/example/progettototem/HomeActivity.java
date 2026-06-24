package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {
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
        Carrello.getInstance().svuota();
        startActivity(new Intent(this, MainActivity.class));
    }
}
