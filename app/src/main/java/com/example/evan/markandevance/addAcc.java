package com.example.evan.markandevance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class addAcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc);
    }
    public void onClick(View view)
    {
        EditText userName;
        EditText password;
        EditText website;
        //fetch username, target website, and password from the textboxes
        userName = (EditText)findViewById(R.id.UnIn);
        String UN = userName.getText().toString(); //Username input
        password = (EditText)findViewById(R.id.PwIn);
        String PW = password.getText().toString(); //Password input
        website = (EditText)findViewById(R.id.WebIn);
        String WEB = website.getText().toString(); //Website input
        AccInfo params = new AccInfo(UN,PW,WEB); //create AccInfo object to pass to addData
        new addData().execute(params);
    }
    private class addData extends AsyncTask<AccInfo, Void, String> //takes AccInfo as an input
    {
        protected String doInBackground(AccInfo...AccInfos) { //add record to DB
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("site", AccInfos[0].WEB);
            builder.appendQueryParameter("usr", AccInfos[0].UN);
            builder.appendQueryParameter("pwd", AccInfos[0].PW);
            //set up the query using username, website, and password from AccInfo input object
            String input = builder.build().getEncodedQuery();
            try {
                URL url = new URL("http://149.61.165.155/vault_addToDB.php?"); //where the data is coming from
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
                //^needed for the outgoing request to work, for some reason^
                //My guess is that this 'runs' the php script, running our query
                //same as before
                return "";
            }
            catch(MalformedURLException e)
            {
                System.out.println(e);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

            return null;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Intent i = new Intent(getApplicationContext(), ApplicationList.class);
            startActivity(i);
        }
    }
    private static class AccInfo //Used to group strings together into one object
        //(as opposed to passing an arrray in, as that seems like it would get messy)
    {
        String UN; //User name as a string
        String PW; //Password as a string
        String WEB; //website URL as a string

        AccInfo(String username, String password, String website) { //constructor
            this.UN = username;
            this.PW = password;
            this.WEB = website;
        }
    }
}
