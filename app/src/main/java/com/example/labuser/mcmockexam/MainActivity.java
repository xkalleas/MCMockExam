package com.example.labuser.mcmockexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

}
