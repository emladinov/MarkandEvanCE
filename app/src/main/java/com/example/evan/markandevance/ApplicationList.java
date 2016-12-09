package com.example.evan.markandevance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import java.net.HttpURLConnection;
import java.util.List;


public class ApplicationList extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView mainListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        mainListView = (ListView) findViewById(R.id.listofApps);
        new GetSites().execute(); //fetch websites from database
        mainListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show(); debug
        String params = (String)adapterView.getItemAtPosition(position);
        if(params == "Add an account")
        {
            Toast.makeText(this, "Add an account", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), addAcc.class);
            startActivity(i);
        }
        else
        new GetData("my arg", 10).execute(params);
    }

    private class GetSites extends AsyncTask <String[], Void, String[]>
    {
        protected void onPreExecute(){
            super.onPreExecute();
        }
        protected String[] doInBackground(String[]...params)
        {
            try {
                URL url = new URL("http://149.61.165.155/vault.php"); //where the data is coming from
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // set up connection
                connection.setRequestMethod("GET"); //set mode to GET.
                InputStream data = new BufferedInputStream(connection.getInputStream()); //set up input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(data)); //buffer it
                StringBuilder bdr = new StringBuilder();
                System.out.println("Made it this far \n");
                String dataStr = reader.readLine();
                JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                List<String> stuff = new ArrayList<String>();
                for(int count = 0; count < arr.length(); count++)
                {
                    stuff.add(arr.getString(count));
                }
                    return stuff.toArray(new String[stuff.size()]); //our array
            }
            catch(MalformedURLException e)
            {
                System.out.println(e);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            catch(JSONException e)
            {
                System.out.println(e);
            }
            return null;
        }
    protected void onPostExecute(String[] params){
        super.onPostExecute(params);
        ArrayList<String> websites = new ArrayList<String>(); //fill
        websites.addAll(Arrays.asList(params));
        websites.add("Add an account");
        adapter = new ArrayAdapter<String>(ApplicationList.this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter);
    }
    }
    private class GetData extends AsyncTask <String, Void, String[]>
    {
        private String stringArg;
        private int intArg;

        public GetData(String stringArg, int intArg) {
            this.stringArg = stringArg;
            this.intArg = intArg;
        }
        protected String[] doInBackground(String...strings)
        {
            Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("site", strings[0]);
            String input = builder.build().getEncodedQuery();
            try {
                URL url = new URL("http://149.61.165.155/vault_getData.php?"); //where the data is coming from
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // set up connection
                connection.setRequestMethod("POST"); //set mode to POST.
                connection.setDoOutput(true);
                connection.setDoInput(true);
                OutputStream os = connection.getOutputStream();
                BufferedWriter author = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                author.write(input);
                author.flush();
                author.close();
                InputStream data = new BufferedInputStream(connection.getInputStream()); //set up input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(data)); //buffer it
                StringBuilder bdr = new StringBuilder();
                System.out.println("Made it this far \n");
                String dataStr = reader.readLine();
                JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                List<String> stuff = new ArrayList<String>();
                for(int count = 0; count < arr.length(); count++)
                {
                    stuff.add(arr.getString(count));
                }
                return stuff.toArray(new String[stuff.size()]); //our array
            }
            catch(MalformedURLException e)
            {
                System.out.println(e);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            catch(JSONException e)
            {
                System.out.println(e);
            }
            return null;
        }
        protected void onPostExecute(String[] params){
            super.onPostExecute(params);
            String userName = params[0];
            String password = params[1];
            Intent i = new Intent(getApplicationContext(), ShowData.class);
            i.putExtra("un", userName);
            i.putExtra("pw", password);
            startActivity(i);
        }
    }
}

