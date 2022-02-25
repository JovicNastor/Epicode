package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class QuizJsActivity extends AppCompatActivity {
    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_js);

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizJsActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.score_bottom_sheet,(LinearLayout)findViewById(R.id.scorebarview));
        TextView score = bottomSheetView.findViewById(R.id.scorebar);
        Button restart = bottomSheetView.findViewById(R.id.restart);

        score.setText("Your score is \n" + currentScore + "/50");

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase
                        .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                        .getReference("Scores")
                        .push()
                        .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                                .getCurrentUser())
                                .getUid(), "Javascript", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizJsActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QuizJsActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                            }
                        });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizJsActivity.this, ScoreboardActivity.class);
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

        if (questionAttempted == 50) {
            finishQuiz();
        } else {
            questionNumber.setText("Question answered: "+questionAttempted+" / 50");
            question.setText(quizArrayList.get(currentPage).getQuestion());
            option1.setText(quizArrayList.get(currentPage).getOption1());
            option2.setText(quizArrayList.get(currentPage).getOption2());
        }
    }

    public void getQuizQuestion(ArrayList<Quiz> quizArrayList) {

        quizArrayList.add(new Quiz("Adding White Space to scripts may slow down the execution speed of a script.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("The statements inside an if statement are contained by the same curly braces used to contain the statements in a function.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("The external JavaScript file must contain the <script> tag.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("JavaScript is the same as Java.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("If you type the following code in the console window, what result will you get?\n 3 > 2 > 1 === false;","TRUE","FALSE", "TRUE"));

        quizArrayList.add(new Quiz("Inside which HTML element do we put the JavaScript?","<scripting>","<script> ", "<script> "));
        quizArrayList.add(new Quiz("What is the correct JavaScript syntax to change the content of the HTML element below? <p id='demo'>This is a demonstration.</p>","document.getElementByName('p').innerHTML = 'Hello World!';","document.getElementById('demo').innerHTML = 'Hello World!';", "document.getElementById('demo').innerHTML = 'Hello World!';"));
        quizArrayList.add(new Quiz("Where is the correct place to insert a JavaScript?","The <head> section","Both the <head> section and the <body> section are correct", "Both the <head> section and the <body> section are correct"));
        quizArrayList.add(new Quiz("What is the correct syntax for referring to an external script called 'xxx.js'?","<script href='xxx.js'>","<script src='xxx.js'>", "<script src='xxx.js'>"));
        quizArrayList.add(new Quiz("The external JavaScript file must contain the <script> tag.","True  ","False", "False"));

        quizArrayList.add(new Quiz("How do you write 'Hello World' in an alert box?","alertBox('Hello World');","alert('Hello World');", "alert('Hello World');"));
        quizArrayList.add(new Quiz("How do you create a function in JavaScript?","function:myFunction()","function myFunction()  ", "function myFunction()  "));
        quizArrayList.add(new Quiz("How do you call a function named 'myFunction'?","call myFunction()","myFunction()", "myFunction()"));
        quizArrayList.add(new Quiz("How to write an IF statement in JavaScript?","if i = 5","if (i == 5)", "if (i == 5)"));
        quizArrayList.add(new Quiz("How to write an IF statement for executing some code if 'i' is NOT equal to 5?","if i =! 5 then","if (i != 5)", "if (i != 5)"));

        quizArrayList.add(new Quiz("How does a WHILE loop start?","while (i <= 10; i++)","while (i <= 10)", "while (i <= 10)"));
        quizArrayList.add(new Quiz("How does a FOR loop start?","for (i <= 5; i++)","for (i = 0; i <= 5; i++)", "for (i = 0; i <= 5; i++)"));
        quizArrayList.add(new Quiz("How can you add a comment in a JavaScript?","'This is a comment","//This is a comment", "//This is a comment"));
        quizArrayList.add(new Quiz("How to insert a comment that has more than one line?","//This comment has more than one line//","*This comment has more than one line*/", "*This comment has more than one line*/"));
        quizArrayList.add(new Quiz("What is the correct way to write a JavaScript array?","var colors = 'red', 'green', 'blue'","var colors = ['red', 'green', 'blue']", "var colors = ['red', 'green', 'blue']"));

        quizArrayList.add(new Quiz("How do you round the number 7.25, to the nearest integer?","Math.rnd(7.25)","Math.round(7.25)", "Math.round(7.25)"));
        quizArrayList.add(new Quiz("How do you find the number with the highest value of x and y?","Math.ceil(x, y)","Math.max(x, y)", "Math.max(x, y)"));
        quizArrayList.add(new Quiz("What is the correct JavaScript syntax for opening a new window called 'w2' ?","w2 = window.new('http://www.w3schools.com');","w2 = window.open('http://www.w3schools.com');", "w2 = window.open('http://www.w3schools.com');"));
        quizArrayList.add(new Quiz("JavaScript is the same as Java.","True","False", "False"));
        quizArrayList.add(new Quiz("How can you detect the client's browser name?","browser.name","navigator.appName", "navigator.appName"));

        quizArrayList.add(new Quiz("Which event occurs when the user clicks on an HTML element?","onchange","onclick", "onclick"));
        quizArrayList.add(new Quiz("How do you declare a JavaScript variable?","v carName;","var carName; ", "var carName; "));
        quizArrayList.add(new Quiz("Which operator is used to assign a value to a variable?","-","=", "="));
        quizArrayList.add(new Quiz("What will the following code return: Boolean(10 > 9)","false","true", "true"));
        quizArrayList.add(new Quiz("Is JavaScript case-sensitive?","No","Yes", "Yes"));

        quizArrayList.add(new Quiz("Which type of JavaScript language is ___","Object-Oriented","Object-Based", "Object-Based"));
        quizArrayList.add(new Quiz("Which one of the following also known as Conditional Expression:","Switch statement","immediate if", "immediate if"));
        quizArrayList.add(new Quiz("In JavaScript, what is a block of statement?","both conditional block and a single statement","block that combines a number of statements into a single compound statement", "block that combines a number of statements into a single compound statement"));
        quizArrayList.add(new Quiz("When interpreter encounters an empty statements, what it will do:","Prompts to complete the statement","Ignores the statements", "Ignores the statements"));
        quizArrayList.add(new Quiz("The 'function' and ' var' are known as:","Data types","Declaration statements", "Declaration statements"));

        quizArrayList.add(new Quiz("Which one of the following is the correct way for calling the JavaScript code?","Preprocessor","Function/Method", "Function/Method"));
        quizArrayList.add(new Quiz("Which of the following type of a variable is volatile?","Dynamic variable","Mutable variable", "Mutable variable"));
        quizArrayList.add(new Quiz("When there is an indefinite or an infinite value during an arithmetic computation in a program, then JavaScript prints______.","Prints an exception error","Displays 'Infinity'", "Displays 'Infinity'"));
        quizArrayList.add(new Quiz("In the JavaScript, which one of the following is not considered as an error:","Syntax error","Division by zero", "Division by zero"));
        quizArrayList.add(new Quiz("Which of the following givenfunctions of the Number Object formats a number with a different number of digits to the right of the decimal?","toExponential()","toFixed()", "toFixed()"));

        quizArrayList.add(new Quiz("Which of the following number object function returns the value of the number?","toString()","valueOf()", "valueOf()"));
        quizArrayList.add(new Quiz("Which of the following function of the String object returns the character in the string starting at the specified position via the specified number of characters?","search()","substr()", "substr()"));
        quizArrayList.add(new Quiz("Choose the correct snippet from the following to check if the variable 'a' is not equal the 'NULL':","if(a!=null)","if(a!==null)", "if(a!==null)"));
        quizArrayList.add(new Quiz("In JavaScript, what will be used for calling the function definition expression:","Function prototype","Function literal", "Function literal"));
        quizArrayList.add(new Quiz("Which one of the following is used for the calling a function or a method in the JavaScript:","Functional expression","Invocation expression", "Invocation expression"));

        quizArrayList.add(new Quiz("Which of the following number object function returns the value of the number?","toString()","valueOf()", "valueOf()"));
        quizArrayList.add(new Quiz("Which of the following function of the String object returns the character in the string starting at the specified position via the specified number of characters?","search()","substr()", "substr()"));
        quizArrayList.add(new Quiz("Choose the correct snippet from the following to check if the variable 'a' is not equal the 'NULL':","if(a!=null)","if(a!==null)", "if(a!==null)"));
        quizArrayList.add(new Quiz("In JavaScript, what will be used for calling the function definition expression:","Function prototype","Function literal", "Function literal"));
        quizArrayList.add(new Quiz("Which one of the following is used for the calling a function or a method in the JavaScript:","Functional expression","Invocation expression", "Invocation expression"));

    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "Javascript", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizJsActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizJsActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}