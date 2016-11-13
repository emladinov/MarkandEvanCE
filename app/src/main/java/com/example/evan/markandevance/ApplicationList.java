package com.example.evan.markandevance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ApplicationList extends AppCompatActivity {
    private ListView mainListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        mainListView = (ListView) findViewById(R.id.listofApps);
        String [] accounts = new String[] {"Amazon", "Dropbox","Google"};
        ArrayList<String> websites = new ArrayList<String>();
        websites.addAll(Arrays.asList(accounts));
        adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter);
    }
}
