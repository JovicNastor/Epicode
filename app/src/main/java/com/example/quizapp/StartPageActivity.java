package com.example.quizapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    Button login_page_button, register_page_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_start_page);

        login_page_button = findViewById(R.id.login_page_button);
        register_page_button = findViewById(R.id.register_page_button);

        login_page_button.setOnClickListener(V -> login_page());
        register_page_button.setOnClickListener(V -> register_page());
    }

    public void login_page() {
        startActivity(new Intent(StartPageActivity.this, LoginActivity.class));
    }

    public void register_page() {
        startActivity(new Intent(StartPageActivity.this, RegisterActivity.class));
    }
}