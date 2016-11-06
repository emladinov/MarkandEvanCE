package com.example.evan.markandevance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Console;

import static com.example.evan.markandevance.R.id.passEntry;

public class PasswordEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_entry);
    }

    public void onClick(View view) {
        EditText objEdit;
      //  if passtext == entry;
      //  go to page
        objEdit = (EditText)findViewById(R.id.passEntry);
        String text = objEdit.getText().toString();
        if (text.equals("test")) {
            Intent intent = new Intent(this, ApplicationList.class);
            startActivity(intent);
        }
    }
}
