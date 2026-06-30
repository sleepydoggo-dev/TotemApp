package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity {
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
    
    public void tornaIndietro(View view) {
        finish();
    }
}