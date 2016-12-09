package com.example.evan.markandevance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
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
import java.util.List;

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
        userName = (EditText)findViewById(R.id.UnIn);
        String UN = userName.getText().toString();
        password = (EditText)findViewById(R.id.PwIn);
        String PW = password.getText().toString();
        website = (EditText)findViewById(R.id.WebIn);
        String WEB = website.getText().toString();
        TestParam params = new TestParam(UN,PW,WEB);
        new addData("arg", 3).execute(params);
    }
    private class addData extends AsyncTask<TestParam, Void, String>
    {
        private String stringArg;
        private int intArg;

        public addData(String stringArg, int intArg) {
            this.stringArg = stringArg;
            this.intArg = intArg;
        }
        protected String doInBackground(TestParam...TestParams) {
            //ArrayList<String> passwordlist = new ArrayList<String>();
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("site", TestParams[0].WEB);
            builder.appendQueryParameter("usr", TestParams[0].UN);
            builder.appendQueryParameter("pwd", TestParams[0].PW);

            String input = builder.build().getEncodedQuery();
            try {
                //String input = URLEncoder.encode("site", "UTF-8") + "=" + URLEncoder.encode("www.facebook.com", "UTF-8");
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(data)); //buffer it
                StringBuilder bdr = new StringBuilder();
                System.out.println("Made it this far \n");
                //String dataStr = reader.readLine();
                //JSONObject obj = new JSONObject();
                //JSONArray arr  = new JSONArray(dataStr); //set up JSON array decoder
                //String [] stuff = new String[arr.length()];
                //List<String> stuff = new ArrayList<String>();
                //for(int count = 0; count < arr.length(); count++) {
                 //   //JSONObject temp = new JSONObject(dataStr);
                 //   stuff.add(arr.getString(count));
                    //System.out.println(stuff[count]);
                //}
                return "";
                //while((str = reader.readLine()) != null) //feed data into string builder
                //{
                // bdr.append(str); // get that into my array
                //outputStr = str.toString();
                //}help
            }
            catch(MalformedURLException e)
            {
                System.out.println(e);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            //catch(JSONException e)
            //{
             //   System.out.println(e);
            //}
            return null;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Intent i = new Intent(getApplicationContext(), ApplicationList.class);
            startActivity(i);
        }
    }
    private static class TestParam
    {
        String UN;
        String PW;
        String WEB;

        TestParam(String username, String password, String website) {
            this.UN = username;
            this.PW = password;
            this.WEB = website;
        }
    }
}
