package com.example.progettototem;

public class ProdottoOrdinato {
    private final Prodotto prodotto;
    private int quantita;

    public ProdottoOrdinato(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {

        return prodotto;
    }

    public int getQuantita() {

        return quantita;
    }

    public void setQuantita(int quantita) {

        this.quantita = quantita;
    }

    public double getPrezzoTotale() {

        return prodotto.prezzo * quantita;
    }
}