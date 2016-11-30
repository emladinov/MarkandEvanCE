package com.example.evan.markandevance;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Evan on 11/30/2016.
 */

public class jsonParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    //constructor
    public JSONParser(){

    }

    //get json from url
    //by making http post or get method


    public JSONObject makeHttpRequest(String url, String method, List <NameValuePair> params) {

        try {

            if (method == "POST"){
                //request method is port
                //default HttpClient
            }
            else if(method == "GET"){

            }
        }

    }
}
