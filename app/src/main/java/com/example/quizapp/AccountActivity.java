package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AccountActivity extends AppCompatActivity {

    Query database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private TextView name;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        DatabaseReference ref = FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Users");
        ref.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uname = dataSnapshot.child("name").getValue().toString();
                String uemail = dataSnapshot.child("email").getValue().toString();

                name.setText("NAME: " + uname);
                email.setText("EMAIL: " + uemail);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void goto_home(View view) {
        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
        startActivity(intent);
    }
}