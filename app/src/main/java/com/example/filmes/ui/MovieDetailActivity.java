package com.example.filmes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.filmes.R;
import com.example.filmes.adapters.AdapterElenco;
import com.example.filmes.adapters.AdapterMovie;
import com.example.filmes.adapters.MovieItemClick;
import com.example.filmes.models.Elenco;
import com.example.filmes.models.Movie;
import com.example.filmes.models.MovieDetail;
import com.example.filmes.models.Request;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetailActivity extends AppCompatActivity implements MovieItemClick {

    private ImageView cover;
    private ImageView img_filmes;
    private TextView title;
    private TextView description;
    private TextView vote;
    private MovieDetail movieDetail;
    private FloatingActionButton btnPlay;
    private RecyclerView rv_elenco;
    private List<Elenco> elencoList;
    private RecyclerView rv_similar_movies;
    private List<Movie> movieSimilarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        int id = getIntent().getExtras().getInt("id");
        elencoList = new ArrayList<>();
        movieSimilarList = new ArrayList<>();

        inicializar_componentes();
        montar_detalhes_filmes(id);
        montar_elenco_filmes(id);
        montarSimilarMovies(id);
    }

    private void inicializar_componentes(){
         cover = findViewById(R.id.detail_movie_cover);
         img_filmes = findViewById(R.id.detailMovie_img);
         title = findViewById(R.id.detail_movie_title);
         description = findViewById(R.id.detail_movie_description);
         vote = findViewById(R.id.detail_movie_vote);
         btnPlay = findViewById(R.id.btnPlay);
         rv_elenco = findViewById(R.id.rv_elenco);
         rv_similar_movies = findViewById(R.id.rv_similar_movies);
    }

    private void montar_detalhes_filmes(int id){
        String resposta = null;
        Request request= new Request("https://api.themoviedb.org/3/movie/" + id +  "?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR");
        try {
            resposta = request.execute().get();

            try {
                JSONObject object = new JSONObject(resposta);

                String imgMovie = "";
                String coverMovie = "";
                String titleMovie = "";
                double voteMovie = 0;
                String descricaoMovie = "";

                imgMovie = "https://image.tmdb.org/t/p/w500/" +  object.getString("poster_path");
                coverMovie = "https://image.tmdb.org/t/p/w500/" +  object.getString("backdrop_path");
                titleMovie = object.getString("title");
                descricaoMovie = object.getString("overview");
                voteMovie = object.getDouble("vote_average");
                movieDetail = new MovieDetail(id, imgMovie, coverMovie, titleMovie, voteMovie, descricaoMovie);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (resposta == null){
            // mostra erro
        }

        title.setText(movieDetail.getTitle());
        getSupportActionBar().setTitle(movieDetail.getTitle());
        description.setText(movieDetail.getDescricao());
        vote.setText(String.valueOf(movieDetail.getVote()));

        Picasso.get()
                .load(movieDetail.getCover())
                .into(cover);

        Picasso.get()
                .load(movieDetail.getImg())
                .into(img_filmes);

        // cover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }

    private void montar_elenco_filmes(int id){
        String resposta = null;
        Request request= new Request("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR");
        try {
            resposta = request.execute().get();
            try {
                JSONObject object = new JSONObject(resposta);
                JSONArray Jarray  = object.getJSONArray("cast");

                String img = "";
                String nome = "";

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("profile_path");
                    nome = trend.getString("name");
                    elencoList.add(new Elenco(nome, img));
            }
        } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resposta == null){
            // mostra erro
        }

        AdapterElenco adapterElenco = new AdapterElenco(elencoList);
        rv_elenco.setAdapter(adapterElenco);
        rv_elenco.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void montarSimilarMovies(int id){
        String resposta= null;
        Request request = new Request("https://api.themoviedb.org/3/movie/" + id + "/similar?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR&page=1");
        try {
            resposta = request.execute().get();

            try {
                JSONObject object = new JSONObject(resposta);
                JSONArray Jarray  = object.getJSONArray("results");

                String img = "";
                String title = "";
                int idMovie = 0;
                double vote = 0;

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("poster_path");
                    title = trend.getString("title");
                    idMovie = trend.getInt("id");
                    vote = trend.getDouble("vote_average");
                    movieSimilarList.add(new Movie(title, img, idMovie, vote));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (resposta == null){
            // mostra erro
        }

        AdapterMovie adapterMovie = new AdapterMovie(movieSimilarList, this);

        rv_similar_movies.setAdapter(adapterMovie);
        rv_similar_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onMovieClick(int id, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("id", id);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailActivity.this,movieImageView, "sharedName");
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_pesquisar);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Pesquise o filme");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != ""){
                    Intent intent = new Intent(MovieDetailActivity.this, PesquisaActivity.class);
                    intent.putExtra("pesquisa_value", s);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_pesquisar:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}