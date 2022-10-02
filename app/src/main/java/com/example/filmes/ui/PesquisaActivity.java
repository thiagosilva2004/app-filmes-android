package com.example.filmes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.filmes.R;
import com.example.filmes.adapters.AdapterMovie;
import com.example.filmes.adapters.MovieItemClick;
import com.example.filmes.models.Movie;
import com.example.filmes.models.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PesquisaActivity extends AppCompatActivity implements MovieItemClick {

    private RecyclerView rv_pesquisa;
    private List<Movie> moviePesquisaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        String pesquisa_value = getIntent().getExtras().getString("pesquisa_value");

        rv_pesquisa = findViewById(R.id.rv_movie_search);
        moviePesquisaList = new ArrayList<Movie>();
        montarFilmesPesquisados(pesquisa_value);
    }

    private void montarFilmesPesquisados(String valor){
        String resposta= null;
        Request request = new Request("https://api.themoviedb.org/3/search/movie?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR&query=" + valor + "&page=1&include_adult=false");
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
                    moviePesquisaList.add(new Movie(title, img, idMovie, vote));
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

        AdapterMovie adapterMovie = new AdapterMovie(moviePesquisaList, this);

        rv_pesquisa.setAdapter(adapterMovie);
        rv_pesquisa.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
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
                    Intent intent = new Intent(PesquisaActivity.this, PesquisaActivity.class);
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

    @Override
    public void onMovieClick(int id, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("id", id);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PesquisaActivity.this,movieImageView, "sharedName");
        startActivity(intent, options.toBundle());
    }
}