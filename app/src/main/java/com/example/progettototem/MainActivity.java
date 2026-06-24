package com.example.progettototem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vaiAOrdina(View view) {
        startActivity(new Intent(this, CategorieActivity.class));
    }

    public void vaiAOpzioni(View view) {
        startActivity(new Intent(this, OpzioniActivity.class));
    }

    public void vaiAStorico(View view) {
        startActivity(new Intent(this, StoricoOrdiniActivity.class));
    }
}
