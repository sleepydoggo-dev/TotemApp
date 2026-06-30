package com.example.progettototem;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Attributo implements Serializable {
    private final String nome;
    private final double prezzoExtra;
    private boolean selezionato;

    public Attributo(String nome, double prezzoExtra) {
        this.nome = nome;
        this.prezzoExtra = prezzoExtra;
        this.selezionato = false;
    }

    public String getNome() { return nome; }
    public double getPrezzoExtra() { return prezzoExtra; }
    public boolean isSelezionato() { return selezionato; }
    public void setSelezionato(boolean selezionato) { this.selezionato = selezionato; }

    @NonNull
    @Override
    public String toString() {
        if (prezzoExtra > 0) {
            return nome + " (+€" + String.format("%.2f", prezzoExtra) + ")";
        }
        return nome;
    }
}