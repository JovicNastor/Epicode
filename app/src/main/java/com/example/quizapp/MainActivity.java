package com.example.quizapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure you want to exit?</font>"));
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity(); //Force Exit
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void goto_quiz(View view) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    public void goto_quizes(View view) {
        Intent intent = new Intent(MainActivity.this, QuizListActivity.class);
        startActivity(intent);
    }

    public void goto_scoreboard(View view) {
        Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
        startActivity(intent);

    }

    public void goto_lessons(View view) {
        Intent intent = new Intent(MainActivity.this, ProgrammingListActivity.class);
        startActivity(intent);
    }

    public void goto_dictionary(View view) {
        Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
        startActivity(intent);
    }

    public void goto_account(View view) {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure you want to exit?</font>"));
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(MainActivity.this, StartPageActivity.class);
                        startActivity(intent);
                    }
                });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void about(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(Html.fromHtml("<font color='#000000'>EpiCode Developers</font>"))
                .setMessage(Html.fromHtml("<center><font color='#000000'>Jose Victor Nastor</font>"+"<br>"+
                        "<font color='#000000'>Ayessa May Gabbac</font>"+"<br>"+
                        "<font color='#000000'>George Baltazar Jr</font>"+"<br>"+
                        "<font color='#000000'>Junel Joaquin</font>" ))
                .setIcon(R.drawable.logoprog)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}