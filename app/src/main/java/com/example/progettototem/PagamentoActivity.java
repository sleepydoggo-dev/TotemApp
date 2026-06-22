package com.example.progettototem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class PagamentoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_pagamento); }
    public void confermaPagamento(View view) { startActivity(new Intent(this, GrazieActivity.class)); }
}