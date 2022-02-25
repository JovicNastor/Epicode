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

public class QuizPhpActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_php);

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizPhpActivity.this);
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
                                .getUid(), "PHP", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizPhpActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QuizPhpActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                            }
                        });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizPhpActivity.this, ScoreboardActivity.class);
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
        quizArrayList.add(new Quiz("Parent constructors are not called implicitly if the child class defines a constructor.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Interface constant can be override in class implementing the interface.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("Static methods can be call with class name and colon operator, $this is not available inside the method declared as static.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Static properties can be accessed through the object using the arrow operator ->.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("If parent class has Final method abc(). Method abc() can be overridden in child class.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("In PHP, a class can be inherited from one base class and with multiple base classes.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("To create instance of class \"new\" keyword is not required.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("$this is a reference to the calling object","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("The variable name is case-sensitive in PHP.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("PHP is an open source software","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("What does PHP stand for?","Personal Hypertext Processor","PHP: Hypertext Preprocessor", "PHP: Hypertext Preprocessor"));
        quizArrayList.add(new Quiz("PHP server scripts are surrounded by delimiters, which?","<?php>...</?>","<?php...?>", "<?php...?>"));
        quizArrayList.add(new Quiz("How do you write 'Hello World' in PHP","Document.Write('Hello World');","echo 'Hello World'; ", "echo 'Hello World'; "));
        quizArrayList.add(new Quiz("All variables in PHP start with which symbol?","&","$", "$"));
        quizArrayList.add(new Quiz("What is the correct way to end a PHP statement?","</php>",";", ";"));
        quizArrayList.add(new Quiz("The PHP syntax is most similar to:","VBScript","Perl and C", "Perl and C"));
        quizArrayList.add(new Quiz("How do you get information from a form that is submitted using the 'get' method?","Request.QueryString;","$_GET[];", "$_GET[];"));
        quizArrayList.add(new Quiz("When using the POST method, variables are displayed in the URL:","True","False", "False"));
        quizArrayList.add(new Quiz("In PHP you can use both single quotes ( ' ' ) and double quotes ( ' ' ) for strings:","False","True", "True"));
        quizArrayList.add(new Quiz("Include files must have the file extension '.inc'","False","False", "False"));
        quizArrayList.add(new Quiz("What is the correct way to include the file 'time.inc' ?","<?php include:'time.inc'; ?>","<?php include 'time.inc'; ?>", "<?php include 'time.inc'; ?>"));
        quizArrayList.add(new Quiz("What is the correct way to create a function in PHP?","new_function myFunction()","function myFunction()", "function myFunction()"));
        quizArrayList.add(new Quiz("What is the correct way to open the file 'time.txt' as readable?","fopen('time.txt','r+');","fopen('time.txt','r');", "fopen('time.txt','r');"));
        quizArrayList.add(new Quiz("PHP allows you to send emails directly from a script","False","True", "True"));
        quizArrayList.add(new Quiz("Which superglobal variable holds information about headers, paths, and script locations?","$_SESSION","$_SERVER", "$_SERVER"));
        quizArrayList.add(new Quiz("What is the correct way to add 1 to the $count variable?","$count =+1","$count++;", "$count++;"));
        quizArrayList.add(new Quiz("What is a correct way to add a comment in PHP?","*...*","/*...*/", "/*...*/"));
        quizArrayList.add(new Quiz("PHP can be run on Microsoft Windows IIS(Internet Information Server):","False","True", "True"));
        quizArrayList.add(new Quiz("The die() and exit() functions do the exact same thing.","False","True", "True"));
        quizArrayList.add(new Quiz("Which one of these variables has an illegal name?","$my_Var","$my-Var", "$my-Var"));
        quizArrayList.add(new Quiz("How do you create a cookie in PHP?","makecookie()","setcookie()", "setcookie()"));
        quizArrayList.add(new Quiz("In PHP, the only way to output text is with echo.","True","False", "False"));
        quizArrayList.add(new Quiz("How do you create an array in PHP?","$cars = array['Volvo', 'BMW', 'Toyota'];","$cars = array('Volvo', 'BMW', 'Toyota');", "$cars = array('Volvo', 'BMW', 'Toyota');"));
        quizArrayList.add(new Quiz("The if statement is used to execute some code only if a specified condition is true","False","True", "True"));
        quizArrayList.add(new Quiz("Which operator is used to check if two values are equal and of same data type?","==","===", "==="));
        quizArrayList.add(new Quiz("PHP stands for –","Pretext Hypertext Preprocessor","Hypertext Preprocessor", "Hypertext Preprocessor"));
        quizArrayList.add(new Quiz("Who is known as the father of PHP?","Drek Kolkevi","Rasmus Lerdrof", "Rasmus Lerdrof"));
        quizArrayList.add(new Quiz("Variable name in PHP starts with –","! (Exclamation)","$ (Dollar)", "$ (Dollar)"));
        quizArrayList.add(new Quiz("Which of the following is the default file extension of PHP?",".hphp",".php", ".php"));
        quizArrayList.add(new Quiz("Which of the following is not a variable scope in PHP?","Local","Local", "Local"));
        quizArrayList.add(new Quiz("Which of the following is used for concatenation in PHP?","* (Asterisk)",". (dot)", ". (dot)"));
        quizArrayList.add(new Quiz("Which of the following starts with __ (double underscore) in PHP?","Inbuilt constants","Magic constants", "Magic constants"));
        quizArrayList.add(new Quiz("What does PEAR stands for?","PHP event and application repository","PHP extension and application repository", "PHP extension and application repository"));
        quizArrayList.add(new Quiz("Which of the following is the correct way to create a function in PHP?","Create myFunction()","function myFunction()", "function myFunction()"));
        quizArrayList.add(new Quiz("Which of the following PHP function is used to generate unique id?","mdid()","uniqueid()", "uniqueid()"));
        quizArrayList.add(new Quiz("Which of the following is the correct way of defining a variable in PHP?","$variable name = value;","$variable_name = value;", "$variable_name = value;"));
        quizArrayList.add(new Quiz("What is the use of fopen() function in PHP?","The fopen() function is used to open folders in PHP","The fopen() function is used to open files in PHP", "The fopen() function is used to open files in PHP"));
        quizArrayList.add(new Quiz("What is the use of isset() function in PHP?","The isset() function is used to check whether the variable is free or not","The isset() function is used to check whether variable is set or not", "The isset() function is used to check whether variable is set or not"));
        quizArrayList.add(new Quiz("What is the use of sprintf() function in PHP?","The sprintf() function is used to print the output of program","The sprintf() function is used to send output to variable", "The sprintf() function is used to send output to variable"));
        quizArrayList.add(new Quiz("Which of the following function displays the information about PHP and its configuration?","php_info()","phpinfo()", "phpinfo()"));
        // quizArrayList.add(new Quiz("Which of the following function is used to find files in PHP?","fold()","glob()", "glob()"));
        // quizArrayList.add(new Quiz("Which of the following function is used to set cookie in PHP?","createcookie()","setcookie()", "setcookie()"));
        // quizArrayList.add(new Quiz("Which of the following function is used to get the ASCII value of a character in PHP?","val()","chr()", "chr()"));
        // quizArrayList.add(new Quiz("Which of the following function is used to unset a variable in PHP","delete()","unset()", "unset()"));
        // quizArrayList.add(new Quiz("Which of the following function is used to sort an array in descending order?","sort()","rsort()", "rsort()"));
    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "PHP", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizPhpActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizPhpActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}