package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdottiAdapter extends RecyclerView.Adapter<ProdottiAdapter.ViewHolder> {
    private final List<Prodotto> listaProdotti;
    private final Context context;

    public ProdottiAdapter(Context context, List<Prodotto> lista) {
        this.context = context;
        this.listaProdotti = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prodotto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prodotto p = listaProdotti.get(position);

        holder.tNome.setText(p.nome);

        // Mappatura dinamica delle immagini (stile Totem)
        int imgRes = R.drawable.burger; // default fallback
        String n = p.nome.toLowerCase();

        if (n.contains("pizza")) imgRes = R.drawable.pizza;
        else if (n.contains("pasta")) imgRes = R.drawable.pasta_bg;
        else if (n.contains("lasagna")) imgRes = R.drawable.lasagna;
        else if (n.contains("cotoletta")) imgRes = R.drawable.cotoletta;
        else if (n.contains("grigliata")) imgRes = R.drawable.grigliata;
        else if (n.contains("acqua") || n.contains("water")) imgRes = R.drawable.water;
        else if (n.contains("coca")) imgRes = R.drawable.coca;
        else if (n.contains("fanta")) imgRes = R.drawable.fanta;
        else if (n.contains("sprite")) imgRes = R.drawable.sprite;
        else if (n.contains("birra")) imgRes = R.drawable.birra;
        else if (n.contains("chinotto")) imgRes = R.drawable.chinotto;

        holder.imgProdotto.setImageResource(imgRes);

        // Click sull'intero blocco del prodotto (Apre i dettagli)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DettagliActivity.class);
            intent.putExtra("NOME", p.nome);
            intent.putExtra("PREZZO", p.prezzo);
            intent.putExtra("DESC", p.descrizione);
            intent.putExtra("IMG", p.immagineKey);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return listaProdotti.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNome;
        ImageView imgProdotto;

        public ViewHolder(View itemView) {
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeProdotto);
            imgProdotto = itemView.findViewById(R.id.imgProdottoItem);
        }
    }
}
