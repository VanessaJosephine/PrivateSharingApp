package com.example.privatesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.privatesharingapp.sqlitehelper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button logInBtn, signUpBtn;
    EditText username, password;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // FindViewById
        logInBtn = findViewById(R.id.button);
        signUpBtn = findViewById(R.id.button2);
        username = findViewById(R.id.editTextText);
        password = findViewById(R.id.editTextTextPassword);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = databaseHelper.getUsers(
                        username.getText().toString(),
                        password.getText().toString()
                );
                if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please enter your username and password", Toast.LENGTH_LONG).show();
                else if (result == true) // Correct credentials
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor myEditor = sharedPreferences.edit();
                    myEditor.putString("username", username.getText().toString());
                    myEditor.apply();
                    Toast.makeText(MainActivity.this, "Welcome " + username.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent2);
                }
                // Wrong credentials
                else Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_LONG).show();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent1);
            }
        });
    }
}