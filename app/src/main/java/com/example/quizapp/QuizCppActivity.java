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

public class QuizCppActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_cpp);

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizCppActivity.this);
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
                                .getUid(), "C++", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizCppActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QuizCppActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                            }
                        });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizCppActivity.this, ScoreboardActivity.class);
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
        quizArrayList.add(new Quiz("The computer will carry out the instructions that follow the symbol //","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("A program must have a main function.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("The following is an example of a declaration statement:\n cout << “Enter a number: ”;","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("An identifier must start with a letter or an underscore.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("It is best to use very short identifiers.","TRUE","FALSE", "FALSE"));

        quizArrayList.add(new Quiz("In the statement below: “Hello!” is called a string literal. cout << “Hello!”","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("There is no limit on the size of the numbers that can be stored in the int data type.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("76.45e-2 is a valid value for a float data type.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("There are only two possible values for the bool data type.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("All data types take up the same amount of storage.","TRUE","FALSE", "FALSE"));

        quizArrayList.add(new Quiz("C++ was developed by ___?","Thomas Kushz","Bjarne Stroutstrup", "Bjarne Stroutstrup"));
        quizArrayList.add(new Quiz("Which one of the following is a keyword?","Jump","Switch", "Switch"));
        quizArrayList.add(new Quiz("____ is the smallest individual unit in a program.","Token","Character", "Token"));
        quizArrayList.add(new Quiz("What is a constant that contains a single character enclosed within single quotes?","Character","Numeric", "Character"));
        quizArrayList.add(new Quiz("The modulus operator uses ___ character.","+","%", "%"));

        quizArrayList.add(new Quiz("Every variable should be separated by ___ separator.","Comma","Semicolon", "Comma"));
        quizArrayList.add(new Quiz("Auto, static, extern and register are called as __","Auto","Storage specifier", "Storage specifier"));
        quizArrayList.add(new Quiz("How many storage specifies are there in a C++?","4","5", "4"));
        quizArrayList.add(new Quiz("Signed, unsigned, long and short are some of the _","Derived data","Modifiers", "Modifiers"));
        quizArrayList.add(new Quiz("Logical AND (&&) and Logical OR (||) are ___ operators.","Logical","Equality", "Logical"));

        quizArrayList.add(new Quiz("____ operators have lower precedence to relational and arithmetic operators.","Conditional","Relational", "Relational"));
        quizArrayList.add(new Quiz("How many C++ data types are broadly classified?","2","3", "3"));
        quizArrayList.add(new Quiz("Float and double are related to ____ data type.","Void","Floating", "Floating"));
        quizArrayList.add(new Quiz("Variable names must begin with ___","Number","Letter", "Letter"));
        quizArrayList.add(new Quiz("Integer values are stored in ___ bit format in binary form.","8","16", "16"));

        quizArrayList.add(new Quiz("Which of the following is not a C++ keyword?","If","Cont", "Cont"));
        quizArrayList.add(new Quiz("____ is a variable that holds a memory address.","Char","Pointer", "Pointer"));
        quizArrayList.add(new Quiz("Addressing is done using ___ number system.","Decimal","Hexadecimal", "Hexadecimal"));
        quizArrayList.add(new Quiz("Which one of the following is a membership operator?",".","::", "::"));
        quizArrayList.add(new Quiz("____ Operator requires two operands.","Logical","Binary", "Binary"));

        quizArrayList.add(new Quiz("Auto variables get undefined values known as ___","Auto","Garbage", "Garbage"));
        quizArrayList.add(new Quiz("____ storage class variables are defined in another program.","Static","Extern", "Extern"));
        quizArrayList.add(new Quiz("____ Type is used to declare a generic pointer in C++.","Int","Void", "Void"));
        quizArrayList.add(new Quiz("Which is a conditional operator?","@","?", "?"));
        quizArrayList.add(new Quiz("Built in Data type is also called as ___ data type.","Secondary","Fundamental", "Fundamental"));

        quizArrayList.add(new Quiz("Additive, Multiplicative, shift, Bitwise logical and assignment operators is ____ operator.","Unary","Binary", "Binary"));
        quizArrayList.add(new Quiz("Binary plus (+) and Binary minus (-) are ___ operator.","Multiplicative","Additive", "Additive"));
        quizArrayList.add(new Quiz("What is the length of double data type?","8","64", "64"));
        quizArrayList.add(new Quiz("Which operator requires three operands?","Unary","Bitwise", "Bitwise"));
        quizArrayList.add(new Quiz("What are bitwise operators?","&","other", "other"));

        quizArrayList.add(new Quiz("The constant that should not have fractional part is ___","Float","Integer", "Integer"));
        quizArrayList.add(new Quiz("What is the other name for variable?","Constant","Identifier", "Identifier"));
        quizArrayList.add(new Quiz("____ constant is a signed real number.","Integer","Floating point", "Floating point"));
        quizArrayList.add(new Quiz("How many operators are classified in C++?","11","13", "13"));
        quizArrayList.add(new Quiz("____ is the C increment Operator in C++.","+*","++", "++"));

        quizArrayList.add(new Quiz("Rick Mascitti coined the name ___","COBOL","C++", "C++"));
        quizArrayList.add(new Quiz("____ is a sequence of characters surrounded by double quotes.","Constant","String literal", "String literal"));
        quizArrayList.add(new Quiz("How many fundamental data types are there in C++?","1","3", "3"));
        quizArrayList.add(new Quiz("____ is an operator which returns the memory size requirements in terms of bytes.","Double","Size of", "Size of"));
        quizArrayList.add(new Quiz("Where a condition is checked to see whether to do one or more iteration is _____","Definite iteration","Sequencing", "Sequencing"));

//        quizArrayList.add(new Quiz("____ class is another qualifier that can be added to a variable declaration.","Register","Storage", "Storage"));
//        quizArrayList.add(new Quiz("____ storage class global variable known to all functions in the current program.","Register","Extern", "Extern"));
//        quizArrayList.add(new Quiz("The address number starts at ___","1","Null", "Null"));
//        quizArrayList.add(new Quiz("_____ are the kind of data that variables hold in a programming language.","Constant type","Data types", "Data types"));
//        quizArrayList.add(new Quiz("The operands and the operators are grouped in a specific logical way of evaluation is called ____","Assignment","Association", "Association"));
//
//        quizArrayList.add(new Quiz("Which one gives special meaning to the language compiler?","Variable","Keywords", "Keywords"));
//        quizArrayList.add(new Quiz("Which operator requires one operand?","Binary","Unary", "Unary"));
//        quizArrayList.add(new Quiz("___ data type to indicate the function does not return a value.","Static","Public", "Public"));
//        quizArrayList.add(new Quiz("___  type is further divided into int and char.","Float","Integral", "Integral"));
//        quizArrayList.add(new Quiz("The enum, unsigned int, short int and int data type’s uses ___ bits.","8","16", "16"));

    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "C++", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizCppActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizCppActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}