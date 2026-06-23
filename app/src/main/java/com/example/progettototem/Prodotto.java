package com.example.progettototem;

public class Prodotto {
    public String nome;
    public double prezzo;
    public String descrizione;

    public Prodotto(String nome, double prezzo, String descrizione) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPrezzo() {
        return this.prezzo;
    }

    public String getDescrizione() {
        return this.descrizione;
    }
}