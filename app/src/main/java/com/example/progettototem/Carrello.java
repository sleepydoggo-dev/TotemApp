package com.example.progettototem;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private static Carrello instance;
    private final List<ProdottoOrdinato> prodotti;



    private Carrello() {

        prodotti = new ArrayList<>();
    }

    public static synchronized Carrello getInstance() {
        // Se l'istanza è già stata creata, la restituisce
        if (instance == null) instance = new Carrello();
        return instance;
    }


    public void aggiungiProdotto(Prodotto p, int quantita, Context context) {
        // Cerca se il prodotto è già presente nel carrello
        boolean trovato = false;
        for (ProdottoOrdinato po : prodotti) {
            if (po.getProdotto().nome.equals(p.nome)) {
                po.setQuantita(po.getQuantita() + quantita);
                trovato = true;
                break;
            }
        }
        if (!trovato) {
            prodotti.add(new ProdottoOrdinato(p, quantita));
        }

        // logcat per rintracciare eventuale problema
        salva(context);
        android.util.Log.d("TEST_CARRELLO", "Prodotto aggiunto. Totale elementi: " + prodotti.size());
    }


    public void salva(Context context) {
        // [BUGFIX] Utilizzo di "TOTEM_PREFS" per la persistenza del carrello
        // Questo assicura che il carrello venga salvato correttamente nel DB associato all'utente
        String user = context.getSharedPreferences("TOTEM_PREFS", Context.MODE_PRIVATE)
                .getString("LOGGED_USERNAME", null);

        if (user != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                dbHelper.salvaCarrello(user, this.prodotti);
            } catch (Exception e) {
                android.util.Log.e("ErroreApp", "Eccezione catturata", e);
            }
        }
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

    private String nomeUtente;
    public void setNomeUtente(String nome) {

        this.nomeUtente = nome;
    }

    public String getNomeUtente() {

        return nomeUtente;
    }

    public void svuota() {
        prodotti.clear();
    }

    public void svuotaEPulisciDB(Context context, String username) {
        prodotti.clear();
        if (username != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                dbHelper.salvaCarrello(username, new ArrayList<>());
            } catch (Exception e) {
                android.util.Log.e("ErroreApp", "Eccezione durante svuotamento carrello DB", e);
            }
        }
    }

    public void carica(Context context, String username) {
        prodotti.clear();
        if (username != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                List<ProdottoOrdinato> listaSalvata = dbHelper.caricaCarrello(username);
                if (listaSalvata != null) {
                    prodotti.addAll(listaSalvata);
                }
            } catch (Exception e) {
                android.util.Log.e("ErroreApp", "Eccezione catturata", e);
            }
        }
    }

    public void caricaEUnisci(Context context, String username) {
        if (username != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                List<ProdottoOrdinato> listaSalvata = dbHelper.caricaCarrello(username);
                if (listaSalvata != null) {
                    for (ProdottoOrdinato salvato : listaSalvata) {
                        boolean esiste = false;
                        for (ProdottoOrdinato attuale : prodotti) {
                            if (attuale.getProdotto().nome.equals(salvato.getProdotto().nome)) {
                                attuale.setQuantita(attuale.getQuantita() + salvato.getQuantita());
                                esiste = true;
                                break;
                            }
                        }
                        if (!esiste) {
                            prodotti.add(salvato);
                        }
                    }
                }
                // Salva il carrello unito nel DB
                dbHelper.salvaCarrello(username, prodotti);
            } catch (Exception e) {
                android.util.Log.e("ErroreApp", "Eccezione catturata", e);
            }
        }
    }
}
