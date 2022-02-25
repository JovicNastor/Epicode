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

public class QuizPyActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_py);

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizPyActivity.this);
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
                                .getUid(), "Python", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizPyActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QuizPyActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                            }
                        });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizPyActivity.this, ScoreboardActivity.class);
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
        quizArrayList.add(new Quiz("Which of the following is not a valid function name?","Function_1","1function", "1function"));
        quizArrayList.add(new Quiz("To print out the text as shown below, what code should we execute?\n\nI love to take a ride in his sport's car! ","Print 'I love to take a ride in his sport's car!'","Print \"  I love to take a ride in his sport's car!\"", "Print \"I love to take a ride in his sport's car!\""));
        quizArrayList.add(new Quiz("Refer to the the code below:\n\na_list = [10, 20, 30, 40].\n\nWhat is the value of a_list[-2]? ","10","30", "30"));
        quizArrayList.add(new Quiz("Which of the following is not a valid data type in python? ","INT","DOUBLE", "DOUBLE"));
        quizArrayList.add(new Quiz("Study the code below: for i in x:\n  print i\n\nWhich of the option below is incorrect? ","X is a str","X is an int", "X is an int"));
        quizArrayList.add(new Quiz("What is a correct syntax to output “Hello World” in Python?","echo “Hello World”","print(“Hello World”)", "print(“Hello World”)"));
        quizArrayList.add(new Quiz("How do you insert COMMENTS in Python code?","//This is a comment","#This is a comment", "#This is a comment"));
        quizArrayList.add(new Quiz("Which one is NOT a legal variable name?","Myvar","my-var", "my-var"));
        quizArrayList.add(new Quiz("How do you create a variable with the numeric value 5?","x = int(5)","Both the other answers", "Both the other answers"));
        quizArrayList.add(new Quiz("What is the correct file extension for Python files?",".py",".pyth", ".pyth"));
        quizArrayList.add(new Quiz("How do you create a variable with the floating number 2.8?","X = float(2.8)","Both the other answers are correct.", "Both the other answers are correct."));
        quizArrayList.add(new Quiz("What is the correct syntax to output the type of a variable or object in Python?","Print(typeOf(x))","Print(type(x))", "Print(type(x))"));
        quizArrayList.add(new Quiz("What is the correct way to create a function in Python?","Create myFunction():","Def myFunction():", "Def myFunction():"));
        quizArrayList.add(new Quiz("In Python, ‘Hello’, is the same as “Hello”","False","True", "True"));
        quizArrayList.add(new Quiz("What is a correct syntax to return the first character in a string?","X = sub(“Hello”, 0, 1)","X = “Hello”[0]", "X = “Hello”[0]"));
        quizArrayList.add(new Quiz("Which method can be used to remove any whitespace from both the beginning and the end of a string?","Ptrim()","Strip()", "Strip()"));
        quizArrayList.add(new Quiz("Which method can be used to return a string in upper case letters","upper()","upperCase()", "upperCase()"));
        quizArrayList.add(new Quiz("Which method can be used to replace parts of a string?","Switch()","Replace()", "Replace()"));
        quizArrayList.add(new Quiz("Which operator is used to multiply numbers?","%","*", "*"));
        quizArrayList.add(new Quiz("Which operator can be used to compare two values?","<>","==", "=="));
        quizArrayList.add(new Quiz("Which of these collections defines a LIST?","[“apple”, “banana”, “cherry”]","{“name”: “apple”, “color”: “green”}", "{“name”: “apple”, “color”: “green”}"));
        quizArrayList.add(new Quiz("Which of these collections defines a TUPLE?","[“apple”, “banana”, “cherry”]","(“apple”, “banana”, “cherry”)", "(“apple”, “banana”, “cherry”)"));
        quizArrayList.add(new Quiz("Which of these collections defines a SET?","[“apple”, “banana”, “cherry”]","{“apple”, “banana”, “cherry”}", "{“apple”, “banana”, “cherry”}"));
        quizArrayList.add(new Quiz("Which of these collections defines a DICTIONARY?","(“apple”, “banana”, “cherry”)","{“name”: “apple”, “color”: “green”}", "{“name”: “apple”, “color”: “green”}"));
        quizArrayList.add(new Quiz("Which collection is ordered, changeable, and allows duplicate members?","TUPLE","LIST", "LIST"));
        quizArrayList.add(new Quiz("Which collection does not allow duplicate members?","LIST","SET", "SET"));
        quizArrayList.add(new Quiz("How do you start writing an if statement in Python?","If x > y then:","If x > y:", "If x > y:"));
        quizArrayList.add(new Quiz("How do you start writing a while loop in Python?","X > y while {","While x > y:", "While x > y:"));
        quizArrayList.add(new Quiz("While x > y:","For x > y:","For x in y:", "For x in y:"));
        quizArrayList.add(new Quiz("Which statement is used to stop a loop?","Stop","Break", "Break"));
        quizArrayList.add(new Quiz("Select the right way to create a string literal Ault’Kelly","Str1 = ‘Ault//’Kelly’","Str1 = ‘Ault/’Kelly’", "Str1 = ‘Ault/’Kelly’"));
        quizArrayList.add(new Quiz("What is the result of print(type([]) is list)","False","True", "True"));
        quizArrayList.add(new Quiz("What is the output of the following code: Print(bool(0), bool(3.14159), bool(-3), bool(1.0+1j))","True True False True","False True True True", "False True True True"));
        quizArrayList.add(new Quiz("Please select the correct expression to reassign a global variable “x” to 20 inside a function?","Global x =20","X = 20", "X = 20"));
        quizArrayList.add(new Quiz("What is the output of print(type({}) is set)","True","False", "False"));
        quizArrayList.add(new Quiz("Select all the valid String creation in Python","Str1 = “str1”","Str1 = ‘’’str’’’", "Str1 = ‘’’str’’’"));
        quizArrayList.add(new Quiz("What is the data type of?","Integer","Int", "Int"));
        quizArrayList.add(new Quiz("What is the output of the following variable assignment?","X = 7 Def myfunc():X = x + 1Print(x) Myfunc()Print(x)Refer Variables in Python.","Error", "Error"));
        quizArrayList.add(new Quiz("What is the data type of the following aTuple = (1, ‘Jhon’, 1+3j) print(type(aTuple[2:3]))?","Complex","Tuple", "Tuple"));
        quizArrayList.add(new Quiz("What is the output of the following code?","25","50", "25"));
        quizArrayList.add(new Quiz("What is the data type of?","Hex","Int", "Int"));
        quizArrayList.add(new Quiz("What is the output of the following code","50","NameError", "NameError"));
        quizArrayList.add(new Quiz("In Python 3, what is the output of type(range(5)). (What data type it will return).","None","Range", "Range"));
        quizArrayList.add(new Quiz("What is the output of ?","0.33","2", "2"));
        quizArrayList.add(new Quiz("Bitwise shift operators (<<, >>) has higher precedence than Bitwise And(&) operator","False","True", "True"));
        quizArrayList.add(new Quiz("What is the output of the following Python code","","", ""));
        quizArrayList.add(new Quiz("What is the output of the expression?","-5","5", "-5"));
        quizArrayList.add(new Quiz("What is the output of print(2 ** 3 ** 2)","64","512", "512"));
        quizArrayList.add(new Quiz("What is the value of the following Python Expression?","9","9.0", "9.0"));
        quizArrayList.add(new Quiz("Which of the following operators has the highest precedence?","+","*", "*"));
        // quizArrayList.add(new Quiz("What is the output of the following code?","True True False True","False True True True", "False True True True"));
        // quizArrayList.add(new Quiz("What is the output of ?","12","2", "2"));
        // quizArrayList.add(new Quiz("What is the output of print(2 * 3 ** 3 * 4)","864","216", "216"));
        // quizArrayList.add(new Quiz("What is the output of print(2%6)?","0.33","2", "2"));
        // quizArrayList.add(new Quiz("Bitwise shift operators (<<, >>) has higher precedence than Bitwise And(&) operator","False","True", "True"));
    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "Python", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizPyActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizPyActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}