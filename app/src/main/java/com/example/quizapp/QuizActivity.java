package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        question = findViewById(R.id.question);
        questionNumber = findViewById(R.id.questionAttempted);
        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        quizArrayList = new ArrayList<>();
        random = new Random();
        getQuizQuestion(quizArrayList);

        getCurrentPage();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizArrayList.get(currentPage).getAnswer().trim().toLowerCase().equals(option1.getText().toString().trim().toLowerCase())) {
                    currentScore++;
                }
                questionAttempted++;

                getCurrentPage();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizArrayList.get(currentPage).getAnswer().trim().toLowerCase().equals(option2.getText().toString().trim().toLowerCase())) {
                    currentScore++;
                }
                questionAttempted++;

                getCurrentPage();
            }
        });
    }

    public void finishQuiz() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet,(LinearLayout)findViewById(R.id.scorebarview));
        TextView score = bottomSheetView.findViewById(R.id.scorebar);
        Button restart = bottomSheetView.findViewById(R.id.restart);

        score.setText("Your score is \n" + currentScore + "/10");

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase
                    .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                    .getReference("Scores")
                    .push()
                    .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                            .getCurrentUser())
                            .getUid(), "", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(QuizActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(QuizActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                        }
                    });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizActivity.this, ScoreboardActivity.class);
                startActivity(intent);
            }
        });
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void getCurrentPage() {
        currentPage = random.nextInt(quizArrayList.size());
        setDataToViews(currentPage);
    }

    public void setDataToViews(int currentPage) {

        if (questionAttempted == 10) {
            finishQuiz();
        } else {
            questionNumber.setText("Question answered: "+questionAttempted+" / 10");
            question.setText(quizArrayList.get(currentPage).getQuestion());
            option1.setText(quizArrayList.get(currentPage).getOption1());
            option2.setText(quizArrayList.get(currentPage).getOption2());
        }
    }

    public void getQuizQuestion(ArrayList<Quiz> quizArrayList) {
        quizArrayList.add(new Quiz("Constructor overloading is not possible in Java.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Assignment operator is evaluated Left to Right.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("All binary operators except for the assignment operators are evaluated from Left to Right","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("It is not possible to achieve inheritance of structures in c++?","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Java is a multi-platform, object-oriented, and network-centric programming language?","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("If the logical operator is && and if one statement is false then the final result would be true.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("C++ is an object-oriented programming language?","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("PHP originally stood for Personal Home Page?","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("JavaScript is high-level, often just-in-time compiled and multi-paradigm?\n","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Bjarne Stroustrup, creator of the Java computer language?","TRUE","FALSE", "TRUE"));
    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}