package com.example.filmes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.filmes.adapters.AdapterMovie;
import com.example.filmes.adapters.AdapterSliderPager;
import com.example.filmes.models.Movie;
import com.example.filmes.adapters.MovieItemClick;
import com.example.filmes.R;
import com.example.filmes.models.Request;
import com.example.filmes.models.Slider;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MovieItemClick {

    private List<Slider> listSliders;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private List<Movie> listFilmesMaisAssitidos;
    private List<Movie> listFilmesMelhoresAvaliados;
    private RecyclerView rvFilmesMaisAssitidos;
    private RecyclerView rvFilmesMelhoresAvaliados;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        rvFilmesMaisAssitidos = findViewById(R.id.rv_filmesMaisAssitidos);
        rvFilmesMelhoresAvaliados = findViewById(R.id.rv_melhores_avaliados);
        progressBar = findViewById(R.id.proCarregamento);

        listSliders = new ArrayList<>();
        listFilmesMaisAssitidos = new ArrayList<>();
        listFilmesMelhoresAvaliados = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        montarSlider();
        montarFilmesMaisAssitidos();
        montarMelhoresAvaliados();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void montarSlider(){
        String respostaSlider = null;
        Request requestSlider = new Request("https://api.themoviedb.org/3/movie/popular?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR&page=1");
        try {
            respostaSlider = requestSlider.execute().get();

            try {
                JSONObject object = new JSONObject(respostaSlider);
                JSONArray Jarray  = object.getJSONArray("results");

                String img = "";
                String title = "";
                double vote = 0;
                int id = 0;

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("poster_path");
                    title = trend.getString("title");
                    vote = trend.getDouble("vote_average");
                    id = trend.getInt("id");
                    listSliders.add(new Slider(img, title, id, vote));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (respostaSlider == null){
            // mostra erro
        }

        AdapterSliderPager adapterSliderPager = new AdapterSliderPager(this,listSliders);

        sliderPager.setAdapter(adapterSliderPager);
        indicator.setupWithViewPager(sliderPager);
    }

    private void montarFilmesMaisAssitidos(){
        String resposta= null;
        Request request = new Request("https://api.themoviedb.org/3/movie/now_playing?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR&page=1");
        try {
            resposta = request.execute().get();

            try {
                JSONObject object = new JSONObject(resposta);
                JSONArray Jarray  = object.getJSONArray("results");

                String img = "";
                String title = "";
                int id = 0;
                double vote = 0;

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("poster_path");
                    title = trend.getString("title");
                    id = trend.getInt("id");
                    vote = trend.getDouble("vote_average");
                    listFilmesMaisAssitidos.add(new Movie(title, img, id, vote));
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

        AdapterMovie adapterMovie = new AdapterMovie(listFilmesMaisAssitidos, this);

        rvFilmesMaisAssitidos.setAdapter(adapterMovie);
        rvFilmesMaisAssitidos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void montarMelhoresAvaliados(){
        String resposta= null;
        Request request = new Request("https://api.themoviedb.org/3/movie/top_rated?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR&page=1");
        try {
            resposta = request.execute().get();

            try {
                JSONObject object = new JSONObject(resposta);
                JSONArray Jarray  = object.getJSONArray("results");

                String img = "";
                String title = "";
                int id = 0;
                double vote = 0;

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("poster_path");
                    title = trend.getString("title");
                    id = trend.getInt("id");
                    vote = trend.getDouble("vote_average");
                    listFilmesMelhoresAvaliados.add(new Movie(title, img, id, vote));
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

        AdapterMovie adapterMovie = new AdapterMovie(listFilmesMelhoresAvaliados, this);

        rvFilmesMelhoresAvaliados.setAdapter(adapterMovie);
        rvFilmesMelhoresAvaliados.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onMovieClick(int id, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("id", id);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,movieImageView, "sharedName");
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
                    Intent intent = new Intent(MainActivity.this, PesquisaActivity.class);
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