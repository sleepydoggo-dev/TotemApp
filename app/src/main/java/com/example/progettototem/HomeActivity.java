package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void vaiALogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void vaiARegistrati(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void vaiACategorie(View view) {
        startActivity(new Intent(this, CategorieActivity.class));
    }
}