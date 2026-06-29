package com.example.progettototem;

import java.io.Serializable;

public class Attributo implements Serializable {
    private String nome;
    private double prezzoExtra;
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

    @Override
    public String toString() {
        if (prezzoExtra > 0) {
            return nome + " (+€" + String.format("%.2f", prezzoExtra) + ")";
        }
        return nome;
    }
}