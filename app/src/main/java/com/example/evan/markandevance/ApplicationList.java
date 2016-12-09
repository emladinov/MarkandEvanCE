package com.example.evan.markandevance;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
//import org.apache.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URLStreamHandler;
import java.util.List;


public class ApplicationList extends AppCompatActivity {
    private ListView mainListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        mainListView = (ListView) findViewById(R.id.listofApps);
        fetchStr(); //fetch websites from database
       /* ArrayList<String> websites = new ArrayList<String>(); //fill
        websites.addAll(Arrays.asList(accounts));
        adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter); */
        //this is to make this master again
    }
    private void fetchStr()
    {
        //String [] outputStr = {"nothing"};
        new GetSites().execute();
        //return outputStr; //return that
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
                //JSONObject obj = new JSONObject();
                JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                //String [] stuff = new String[arr.length()];
                List<String> stuff = new ArrayList<String>();
                for(int count = 0; count < arr.length(); count++)
                {
                    //JSONObject temp = new JSONObject(dataStr);
                    stuff.add(arr.getString(count));
                    //System.out.println(stuff[count]);
                }
                    String [] output = stuff.toArray(new String[stuff.size()]); //our array
                    return output;
                //while((str = reader.readLine()) != null) //feed data into string builder
                //{
                // bdr.append(str); // get that into my array
                //outputStr = str.toString();
                //}
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
        adapter = new ArrayAdapter<String>(ApplicationList.this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter);
    }
    }
}

