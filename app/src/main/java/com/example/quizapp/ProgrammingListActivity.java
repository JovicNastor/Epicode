package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ProgrammingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.aboutus){
            new AlertDialog.Builder(ProgrammingListActivity.this)
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

        return super.onOptionsItemSelected(item);
    }


    public void cplusplus(View view) {
        Intent intent = new Intent(this,LessonCplusplusActivity.class);
        startActivity(intent);
    }

    public void java(View view) {
        Intent intent = new Intent(this,LessonJavaActivity.class);
        startActivity(intent);
    }

    public void javascript(View view) {
        Intent intent = new Intent(this,LessonJavascriptActivity.class);
        startActivity(intent);
    }

    public void php(View view) {
        Intent intent = new Intent(this,LessonPhpActivity.class);
        startActivity(intent);
    }

    public void python(View view) {
        Intent intent = new Intent(this,LessonPythonActivity.class);
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