package com.example.labuser.mcmockexam;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_list_textview);

        ListView listView = (ListView) findViewById(R.id.album_list);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.get_albums) {
            GetAlbumsTask task = new GetAlbumsTask();
            task.execute();
        }

        return true;
    }

    private class GetAlbumsTask extends AsyncTask<Void, Void, String[]> {

        protected String[] doInBackground(Void... voids) {
            String albumJsonStr = null;

            // Get JSON from REST API

            HttpURLConnection urlConnection = null;

            try {
                final String baseUrl = "http://jsonplaceholder.typicode.com/albums";

                // Get input stream

                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                // Extract JSON string from input stream

                /*StringBuffer buffer = new StringBuffer();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }
                } finally {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }

                albumJsonStr = buffer.toString();*/

                StringBuilder out = new StringBuilder();
                InputStreamReader in = new InputStreamReader(inputStream, "UTF-8");

                char[] buffer = new char[1024];
                int rsz;
                while ((rsz = in.read(buffer, 0, buffer.length)) >= 0) {
                    out.append(buffer, 0, rsz);
                }

                albumJsonStr = out.toString();

                Log.i("PAOK", albumJsonStr);
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            // Turn JSON to Array of Strings

            try {
                JSONArray albumJsonArray = new JSONArray(albumJsonStr);

                String[] albumArray = new String[albumJsonArray.length()];

                for (int i = 0; i < albumJsonArray.length(); i++) {
                    JSONObject albumJson = (JSONObject) albumJsonArray.get(i);

                    albumArray[i] = albumJson.getString("title");
                }

                return albumArray;
            } catch (JSONException e) {
                return null;
            }
        }

        protected void onPostExecute(String[] albums) {
            adapter.clear();

            adapter.addAll(albums);
        }
    }
}
