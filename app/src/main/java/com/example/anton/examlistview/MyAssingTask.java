package com.example.anton.examlistview;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAssingTask extends AsyncTask<Void, Void, String>{
    public static final String WEATHER_URL = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    @Override
    protected String doInBackground(Void... voids) {
        URL url = null;
        String resultJson;

        try {
            url = new URL(WEATHER_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        // открываем соедиение к указанному URL
        // помощью конструкции try-with-resources

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            // построчно считываем результат в объект StringBuilder
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultJson = stringBuilder.toString();

        return resultJson;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
