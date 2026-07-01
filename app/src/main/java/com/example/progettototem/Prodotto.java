package com.example.progettototem;

public class Prodotto {
    public String nome;
    public double prezzo;
    public String descrizione;
    public String immagineKey;

    public Prodotto(String nome, double prezzo, String descrizione, String immagineKey) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.immagineKey = immagineKey;
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

    public String getImmagineKey() {
        return this.immagineKey;
    }
}
