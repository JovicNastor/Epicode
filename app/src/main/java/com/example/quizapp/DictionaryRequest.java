package com.example.quizapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DictionaryRequest extends AsyncTask<String, Integer, String>
{
    Context context;
    TextView showDefinition;

    DictionaryRequest(Context context, TextView tV)
    {
        this.context = context;
        showDefinition = tV;
    }

    @Override
    protected String doInBackground(String... params) {

        //TODO: replace with your own app id and app key
        final String app_id = "0ebf2247";
        final String app_key = "205270df33940b366368ecd52b04577d";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String def;

        try {
            JSONObject js = new JSONObject(result);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray lArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = lArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject de = sensesArray.getJSONObject(0);
            JSONArray d = de.getJSONArray("definitions");

            def = d.getString(0);
            showDefinition.setText(def);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //System.out.println(result);
        Log.v("Res dic", "onPostExecute"+result);
    }
}

