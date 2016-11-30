package com.example.evan.markandevance;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class ApplicationList extends AppCompatActivity {
    private ListView mainListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);
        mainListView = (ListView) findViewById(R.id.listofapps);
        String data_url = "http://149.61.165.155";
        String [] accounts = new String[50];
        try {
            URL url = new URL(data_url);
            System.out.println("It works");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //OutputStream outputStream = httpURLConnection.getOutputStream();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String line = "";
            int i = 0;

            while((line = bufferedreader.readLine()) != null){
                accounts[i] += line;
                i++;
            }


            bufferedreader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            System.out.println(accounts[1]);
        }
         catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }



        ArrayList<String> websites = new ArrayList<String>();
        websites.addAll(Arrays.asList(accounts));
        adapter = new ArrayAdapter<String>(this, R.layout.rowlayout, websites);
        mainListView.setAdapter(adapter);
    }
}

