package com.example.filmes.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmes.R;
import com.example.filmes.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MyViewHolder> {

    private List<Movie> movieList;
    private MovieItemClick movieItemClick;

    public AdapterMovie(List<Movie> movieList, MovieItemClick listener) {
        this.movieList = movieList;
        this.movieItemClick = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        if (movie.getVote() >= 8.5){
            holder.itemVote.setBackgroundResource(R.drawable.bg_vote_otimo);
        }else if(movie.getVote() >= 7.0){
            holder.itemVote.setBackgroundResource(R.drawable.bg_vote_bom);
        }else if(movie.getVote() >= 5.0){
            holder.itemVote.setBackgroundResource(R.drawable.bg_vote);
        }else if(movie.getVote() >= 3.5){
            holder.itemVote.setBackgroundResource(R.drawable.bg_vote_ruim);
        }else{
            holder.itemVote.setBackgroundResource(R.drawable.bg_vote_pessimo);
        }

        holder.itemTitle.setText(movie.getTitle());
        holder.itemVote.setText(String.valueOf(movie.getVote()));
        Picasso.get()
                .load(movie.getImg())
                .into(holder.itemImg);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private ImageView itemImg;
        private TextView itemVote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.item_title_movie);
            itemImg = itemView.findViewById(R.id.item_img_movie);
            itemVote = itemView.findViewById(R.id.item_vote_movie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie movie = movieList.get(getAdapterPosition());
                    movieItemClick.onMovieClick(movie.getId(), itemImg);
                }
            });
        }
    }
}
