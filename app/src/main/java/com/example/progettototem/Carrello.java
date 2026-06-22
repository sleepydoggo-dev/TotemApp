package com.example.progettototem;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private static Carrello instance;
    private List<ProdottoOrdinato> prodotti;

    private Carrello() {
        prodotti = new ArrayList<>();
    }

    public static synchronized Carrello getInstance() {
        if (instance == null) instance = new Carrello();
        return instance;
    }

    public void aggiungiProdotto(Prodotto p, int quantita) {
        // Verifica se il prodotto è già presente, se sì aumenta la quantità
        for (ProdottoOrdinato po : prodotti) {
            if (po.getProdotto().nome.equals(p.nome)) {
                po.setQuantita(po.getQuantita() + quantita);
                return;
            }
        }
        prodotti.add(new ProdottoOrdinato(p, quantita));
    }

    public List<ProdottoOrdinato> getProdotti() {
        return prodotti;
    }

    public double getTotale() {
        double totale = 0;
        for (ProdottoOrdinato po : prodotti) {
            totale += po.getPrezzoTotale();
        }
        return totale;
    }
    
    public void svuota() {
        prodotti.clear();
    }
}