package com.example.progettototem;

import java.util.List;

public class Ordine {
    private final int id;
    private final String data;
    private final double totale;
    private final List<ProdottoOrdinato> prodotti;

    public Ordine(int id, String data, double totale, List<ProdottoOrdinato> prodotti) {
        this.id = id;
        this.data = data;
        this.totale = totale;
        this.prodotti = prodotti;
    }

    public int getId() {
        return id;
    }
    public String getData() {
        return data;
    }
    public double getTotale() {
        return totale;
    }
    public List<ProdottoOrdinato> getProdotti() {
        return prodotti;
    }
}