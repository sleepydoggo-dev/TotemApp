package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdottiAdapter extends RecyclerView.Adapter<ProdottiAdapter.ViewHolder> {
    private final List<Prodotto> listaProdotti;
    private final Context context;
    private final Map<Integer, Integer> quantitaMap = new HashMap<>();

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
        
        Integer cachedValue = quantitaMap.get(position);
        int currentQty = (cachedValue != null) ? cachedValue : 1;

        holder.tNome.setText(p.nome);
        holder.tDesc.setText(p.descrizione);
        holder.tPrezzo.setText(context.getString(R.string.price_format, p.prezzo));
        holder.tQuant.setText(String.valueOf(currentQty));

        // Impostazione immagine dinamica
        if (p.immagineKey != null) {
            int imgResId = context.getResources().getIdentifier(p.immagineKey, "drawable", context.getPackageName());
            if (imgResId != 0) {
                holder.imgProdotto.setImageResource(imgResId);
            } else {
                holder.imgProdotto.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        // Click sull'immagine per i dettagli
        holder.imgProdotto.setOnClickListener(v -> {
            Intent intent = new Intent(context, DettagliActivity.class);
            intent.putExtra("NOME", p.nome);
            intent.putExtra("PREZZO", p.prezzo);
            intent.putExtra("DESC", p.descrizione);
            intent.putExtra("IMG", p.immagineKey);
            context.startActivity(intent);
        });

        holder.btnPiu.setOnClickListener(v -> {
            Integer val = quantitaMap.get(position);
            int q = ((val != null) ? val : 1) + 1;
            quantitaMap.put(position, q);
            holder.tQuant.setText(String.valueOf(q));
        });

        holder.btnMeno.setOnClickListener(v -> {
            Integer val = quantitaMap.get(position);
            int q = (val != null) ? val : 1;
            if (q > 1) {
                q--;
                quantitaMap.put(position, q);
                holder.tQuant.setText(String.valueOf(q));
            }
        });

        holder.btnAggiungi.setOnClickListener(v -> {
            Integer val = quantitaMap.get(position);
            int q = (val != null) ? val : 1;
            Carrello.getInstance().aggiungiProdotto(p, q, context);
            Toast.makeText(context, context.getString(R.string.added_to_cart_format, p.nome), Toast.LENGTH_SHORT).show();
            quantitaMap.put(position, 1);
            holder.tQuant.setText("1");
        });

        // Gestione Preferiti
        String user = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).getString("LOGGED_USERNAME", null);

        if (user != null) {
            try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                boolean isFav = dbHelper.isPreferito(user, p.nome);
                holder.btnFav.setImageResource(isFav ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
            }

            holder.btnFav.setOnClickListener(v -> {
                try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
                    if (dbHelper.isPreferito(user, p.nome)) {
                        dbHelper.rimuoviPreferito(user, p.nome);
                        holder.btnFav.setImageResource(android.R.drawable.btn_star_big_off);
                        Toast.makeText(context, context.getString(R.string.rimosso_preferiti), Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.aggiungiPreferito(user, p);
                        holder.btnFav.setImageResource(android.R.drawable.btn_star_big_on);
                        Toast.makeText(context, context.getString(R.string.aggiunto_preferiti), Toast.LENGTH_SHORT).show();
                    }
                }
                if (context instanceof PreferitiActivity) {
                    ((PreferitiActivity) context).onResume();
                }
            });
        } else {
            holder.btnFav.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() { return listaProdotti.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNome, tDesc, tPrezzo, tQuant;
        Button btnPiu, btnMeno, btnAggiungi;
        ImageButton btnFav, imgProdotto;

        public ViewHolder(View itemView) {
            super(itemView);
            imgProdotto = itemView.findViewById(R.id.imgProdotto);
            tNome = itemView.findViewById(R.id.nomeProdotto);
            tDesc = itemView.findViewById(R.id.descProdotto);
            tPrezzo = itemView.findViewById(R.id.prezzoProdottoItem);
            tQuant = itemView.findViewById(R.id.textQuantitaItem);
            btnPiu = itemView.findViewById(R.id.btnPiuItem);
            btnMeno = itemView.findViewById(R.id.btnMenoItem);
            btnAggiungi = itemView.findViewById(R.id.btnAggiungiItem);
            btnFav = itemView.findViewById(R.id.btnPreferitoItem);
        }
    }
}
