package com.example.filmes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private List<Slider> listSliders;
    private ViewPager sliderPager;
    private TabLayout indicator;
    private List<Movie> listFilmesMaisAssitidos;
    private RecyclerView rvFilmesMaisAssitidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderPager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        rvFilmesMaisAssitidos = findViewById(R.id.rv_filmesMaisAssitidos);

        listSliders = new ArrayList<>();
        listFilmesMaisAssitidos = new ArrayList<>();

        montarSlider();
        montarFilmesMaisAssitidos();
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
                Number vote = 0;

                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 5; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    img = "https://image.tmdb.org/t/p/w500/" +  trend.getString("poster_path");
                    title = trend.getString("title");
                    id = trend.getInt("id");
                    vote = trend.getInt("vote_average");
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

        AdapterMovie adapterMovie = new AdapterMovie(listFilmesMaisAssitidos);

        rvFilmesMaisAssitidos.setAdapter(adapterMovie);
        rvFilmesMaisAssitidos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

}