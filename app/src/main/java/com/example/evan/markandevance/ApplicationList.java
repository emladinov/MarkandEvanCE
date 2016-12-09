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
        //set list of applications to
        new GetSites().execute(); //fetch websites from database
        mainListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show(); debug
        String params = (String)adapterView.getItemAtPosition(position);
        if(params == "Add an account")
        {
            //display message
            Toast.makeText(this, "Add an account", Toast.LENGTH_SHORT).show();
            //open the page where you can add an account
            Intent i = new Intent(getApplicationContext(), addAcc.class);
            startActivity(i);
        }
        else
        new GetData("my arg", 10).execute(params);
    }

    private class GetSites extends AsyncTask <String[], Void, String[]> //Background thread
            // used to connect to the database and get the list of websites
    {
        protected void onPreExecute(){
            super.onPreExecute(); //do nothing
        }
        protected String[] doInBackground(String[]...params)
        {
            try {
                URL url = new URL("http://149.61.165.155/vault.php"); //where the data is coming from
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // set up connection
                connection.setRequestMethod("GET"); //set mode to GET.
                InputStream data = new BufferedInputStream(connection.getInputStream()); //set up input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(data)); //buffer it
                String dataStr = reader.readLine(); //put page output into a string
                JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                List<String> stuff = new ArrayList<String>(); //set up list to extract JSON array elements
                for(int count = 0; count < arr.length(); count++)
                {
                    stuff.add(arr.getString(count)); //fill list from JSON array
                }
                    return stuff.toArray(new String[stuff.size()]); //array made up of websites on the DB
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
        ArrayList<String> websites = new ArrayList<String>(); //list to be filled with strings
        websites.addAll(Arrays.asList(params)); //add all website strings to the list
        websites.add("Add an account"); //add "add an account" as the bottom element
        adapter = new ArrayAdapter<String>(ApplicationList.this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter);
        //fill the listView from the list of websites
    }
    }
    private class GetData extends AsyncTask <String, Void, String[]> //Background thread
            // used to get the username and password for a particular website
    {
        private String stringArg;
        private int intArg;

        public GetData(String stringArg, int intArg) { //constructor
            this.stringArg = stringArg;
            this.intArg = intArg;
        }
        protected String[] doInBackground(String...strings) //pass in a string parameter
        {
            Uri.Builder builder = new Uri.Builder()//Uri.Builder used to generate request statement
                        .appendQueryParameter("site", strings[0]); //pass input in as a part of the request
            String input = builder.build().getEncodedQuery(); //write the request to a string
            //input = site=[strings[0]], where strings[0] is the input website string
            //this is used to pass the request to the php file
            try {
                URL url = new URL("http://149.61.165.155/vault_getData.php?"); //where the data is coming from
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // set up connection
                connection.setRequestMethod("POST"); //set mode to POST.
                connection.setDoOutput(true);
                connection.setDoInput(true);
                //allow for both input and output
                OutputStream os = connection.getOutputStream();
                BufferedWriter author = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //set up output stream for writing the request
                author.write(input); //send request to the server (equivalent to running the php doc in a browser)
                author.flush();     //example: http://149.61.165.155/vault_getData.php?site=www.facebook.com
                author.close();     //'site' is the input parameterto the php page that we use to run the query, set to "www.facebook.com" in the above URL
                InputStream data = new BufferedInputStream(connection.getInputStream()); //set up input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(data)); //buffer it
                String dataStr = reader.readLine(); //pull page output into a string
                JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                List<String> stuff = new ArrayList<String>(); //set up a list to extract JSON array
                for(int count = 0; count < arr.length(); count++)
                {
                    stuff.add(arr.getString(count)); //fill the list with JSON array elements
                }
                return stuff.toArray(new String[stuff.size()]); //return the list as an array
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
            //expects username and password from the output array
            //username should be element 0, password should be element 1.
            String userName = params[0];
            String password = params[1];
            Intent i = new Intent(getApplicationContext(), ShowData.class);
            //get the username and password and display them to the user
            i.putExtra("un", userName);
            i.putExtra("pw", password);
            startActivity(i);
        }
    }
}

