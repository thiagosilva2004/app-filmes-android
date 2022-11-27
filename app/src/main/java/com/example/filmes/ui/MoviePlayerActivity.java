package com.example.filmes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.filmes.R;
import com.example.filmes.models.Movie;
import com.example.filmes.models.MovieDetail;
import com.example.filmes.models.Request;
import com.google.android.exoplayer2.ui.PlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MoviePlayerActivity extends AppCompatActivity {

    YouTubePlayerView videoMovie;
    private int id;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_player);

        id = getIntent().getExtras().getInt("id");

        buscar_video(id);

        videoMovie = findViewById(R.id.youtube_player_view);
        videoMovie.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        getLifecycle().addObserver(videoMovie);
    }

    private void buscar_video(int id){
        String resposta = null;
        Request request= new Request("https://api.themoviedb.org/3/movie/" + id +  "/videos?api_key=8ef54cad3dff5472f3949c36631ba427&language=pt-BR");
        try {
            resposta = request.execute().get();

            try {
                JSONObject object = new JSONObject(resposta);
                JSONArray Jarray  = object.getJSONArray("results");
                JSONObject trend;

                for (int i = 0; i < Jarray.length() && i < 1; i++) {
                    trend = new JSONObject(Jarray.getString(i));
                    videoId = trend.getString("key");
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
    }

}