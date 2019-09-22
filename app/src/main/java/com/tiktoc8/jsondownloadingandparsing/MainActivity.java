package com.tiktoc8.jsondownloadingandparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String result = "";

    String url = "https://jsonplaceholder.typicode.com/users";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);

        new jsonTask().execute();
    }

    class jsonTask extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL  myurl = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection)myurl.openConnection();
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    builder.append(line);

                }
                  result = builder.toString();

                Log.e("Json",builder.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Log.e("jsonobject",object.getString("name"));
                    textView.setText(object.getString("name"));
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
