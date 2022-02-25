package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
    }

    public void cplusplus(View view) {
        Intent intent = new Intent(this,QuizCppActivity.class);
        startActivity(intent);
    }

    public void java(View view) {
        Intent intent = new Intent(this,QuizJavaActivity.class);
        startActivity(intent);
    }

    public void javascript(View view) {
        Intent intent = new Intent(this,QuizJsActivity.class);
        startActivity(intent);
    }

    public void php(View view) {
        Intent intent = new Intent(this,QuizPhpActivity.class);
        startActivity(intent);
    }

    public void python(View view) {
        Intent intent = new Intent(this,QuizPyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.nothing);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}