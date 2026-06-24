package com.example.progettototem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PagamentoActivity extends BaseActivity {
    private LinearLayout layoutCarta;
    private DatabaseHelper dbHelper;
    private EditText editNum, editScad, editCvv;
    private CheckBox checkSalva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        dbHelper = new DatabaseHelper(this);

        TextView tImporto = findViewById(R.id.textImportoPagamento);
        tImporto.setText(getString(R.string.amount_format, Carrello.getInstance().getTotale()));

        layoutCarta = findViewById(R.id.layoutDatiCarta);
        RadioGroup rg = findViewById(R.id.radioGroupPagamento);
        editNum = findViewById(R.id.editNumeroCarta);
        editScad = findViewById(R.id.editScadenza);
        editCvv = findViewById(R.id.editCVV);
        checkSalva = findViewById(R.id.checkSalvaCarta);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioCarta) layoutCarta.setVisibility(View.VISIBLE);
            else layoutCarta.setVisibility(View.GONE);
        });


        caricaCartaSalvata();
    }

    private void caricaCartaSalvata() {
        String loggedUser = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
        if (loggedUser != null) {
            Cursor cursor = dbHelper.getCarta(loggedUser);
            if (cursor.moveToFirst()) {
                String n = cursor.getString(0);
                if (n != null && !n.isEmpty()) {
                    editNum.setText(n);
                    editScad.setText(cursor.getString(1));
                    editCvv.setText(cursor.getString(2));
                    checkSalva.setChecked(true);
                }
            }
            cursor.close();
        }
    }

    public void confermaPagamento(View view) {
        RadioGroup rg = findViewById(R.id.radioGroupPagamento);
        if (rg.getCheckedRadioButtonId() == R.id.radioCarta) {
            String num = editNum.getText().toString();
            String scad = editScad.getText().toString();
            String cvv = editCvv.getText().toString();
            if (num.isEmpty() || scad.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Inserisci i dati della carta", Toast.LENGTH_SHORT).show();
                return;
            }
            if (checkSalva.isChecked()) {
                String loggedUser = getSharedPreferences("AppPrefs", MODE_PRIVATE).getString("LOGGED_USERNAME", null);
                if (loggedUser != null) {
                    dbHelper.salvaCarta(loggedUser, num, scad, cvv);
                }
            }
        }
        startActivity(new Intent(this, GrazieActivity.class));
    }
    
    public void tornaIndietro(View view) { finish(); }
}