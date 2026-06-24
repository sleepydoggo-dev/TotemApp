package com.example.progettototem;

import java.util.List;

public class Ordine {
    private int id;
    private String data;
    private double totale;
    private List<ProdottoOrdinato> prodotti;

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