package com.example.progettototem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarrelloAdapter extends RecyclerView.Adapter<CarrelloAdapter.ViewHolder> {
    private final List<ProdottoOrdinato> lista;
    private final Context context;
    private final OnCartChangedListener listener;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    public CarrelloAdapter(Context context, List<ProdottoOrdinato> lista, OnCartChangedListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crea un nuovo ViewHolder
        View v = LayoutInflater.from(context).inflate(R.layout.item_carrello, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Imposta i dati del ViewHolder
        ProdottoOrdinato po = lista.get(position);
        holder.tNome.setText(po.getProdotto().nome);
        holder.tPrezzoUnit.setText("cad. € " + String.format("%.2f", po.getProdotto().prezzo));
        holder.tPrezzoTot.setText("€ " + String.format("%.2f", po.getPrezzoTotale()));
        holder.tQuant.setText(String.valueOf(po.getQuantita()));


        // Gestisce i click dei bottoni
        holder.btnPiu.setOnClickListener(v -> {
            po.setQuantita(po.getQuantita() + 1);
            Carrello.getInstance().salva(context);
            notifyItemChanged(position);
            listener.onCartChanged();
        });

        holder.btnMeno.setOnClickListener(v -> {
            if (po.getQuantita() > 1) {
                po.setQuantita(po.getQuantita() - 1);
            } else {
                lista.remove(position);
            }
            Carrello.getInstance().salva(context);
            notifyDataSetChanged();
            listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNome, tPrezzoUnit, tPrezzoTot, tQuant;
        Button btnPiu, btnMeno;
        public ViewHolder(View itemView) {
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeProdottoCarrello);
            tPrezzoUnit = itemView.findViewById(R.id.prezzoUnitarioCarrello);
            tPrezzoTot = itemView.findViewById(R.id.prezzoTotaleCarrello);
            tQuant = itemView.findViewById(R.id.textQuantitaCarrello);
            btnPiu = itemView.findViewById(R.id.btnPiuCarrello);
            btnMeno = itemView.findViewById(R.id.btnMenoCarrello);
        }
    }
}