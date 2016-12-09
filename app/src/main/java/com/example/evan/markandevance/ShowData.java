package com.example.evan.markandevance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.evan.markandevance.R.id.textView2;
import static com.example.evan.markandevance.R.id.textView3;

public class ShowData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        Intent intent = getIntent();

        String userName = intent.getStringExtra("un");
        String password = intent.getStringExtra("pw");
        TextView userText = (TextView) findViewById(R.id.textView3);
        TextView passText = (TextView) findViewById(R.id.textView2);
        userText.setText(userName);
        passText.setText(password);
        
    }
}
