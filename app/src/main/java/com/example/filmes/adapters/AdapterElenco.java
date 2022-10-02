package com.example.filmes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmes.R;
import com.example.filmes.models.Elenco;
import com.example.filmes.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdapterElenco extends RecyclerView.Adapter<AdapterElenco.MyViewHolder> {

    private List<Elenco> elencoList;

    public AdapterElenco(List<Elenco> elencoList) {
        this.elencoList = elencoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_elenco, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Elenco elenco = elencoList.get(position);

        Picasso.get()
                .load(elenco.getImg())
                .into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return elencoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.item_elenco_img);

        }
    }
}
