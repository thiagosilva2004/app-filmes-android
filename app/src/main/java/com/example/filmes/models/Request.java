package com.example.filmes.models;

import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class Request extends AsyncTask<Void, Void, String> {

    private String urlPassada;

    public Request(String urlPassada) {
        this.urlPassada = urlPassada;
    }

    @Override
    protected String doInBackground(Void... voids) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(urlPassada);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type","application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                stringBuilder.append(scanner.next());
            }
        }catch (Exception e){
            Log.e("Erro", e.getMessage());
        }

        return stringBuilder.toString();
    }
}
