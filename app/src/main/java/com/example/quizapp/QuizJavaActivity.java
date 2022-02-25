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

public class QuizJavaActivity extends AppCompatActivity {

    private TextView question, questionNumber;
    private Button option1, option2;
    private ArrayList<Quiz> quizArrayList;
    Random random;
    int currentScore = 0, questionAttempted = 0, currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_java);

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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(QuizJavaActivity.this);
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
                                .getUid(), "Java", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(QuizJavaActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(QuizJavaActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                            }
                        });

                getCurrentPage();
                questionAttempted = 0;
                currentScore = 0;
                bottomSheetDialog.dismiss();

                Intent intent = new Intent(QuizJavaActivity.this, ScoreboardActivity.class);
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
        quizArrayList.add(new Quiz("int x[] = new int[]{10,20,30};<br><br>Arrays can also be created and initialize as in above statement.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("In an instance method or a constructor, \"this\" is a reference to the current object.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Garbage Collection is manual process.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("The JRE deletes objects when it determines that they are no longer being used. This process is called Garbage Collection.","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Constructor overloading is not possible in Java.","TRUE","FALSE", "FALSE"));

        quizArrayList.add(new Quiz("Assignment operator is evaluated Left to Right.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("All binary operators except for the assignment operators are evaluated from Left to Right","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Java programming is not statically-typed, means all variables should not first be declared before they can be used.","TRUE","FALSE", "FALSE"));
        quizArrayList.add(new Quiz("In Java SE 7 and later, underscore characters \"_\" can appear anywhere between digits in a numerical literal","TRUE","FALSE", "TRUE"));
        quizArrayList.add(new Quiz("Variable name can begin with a letter, \"$\", or \"_\".","TRUE","FALSE", "TRUE"));

        quizArrayList.add(new Quiz("Which of the following option leads to the portability and security of Java?","Dynamic binding between objects","Bytecode is executed by JVM", "Bytecode is executed by JVM"));
        quizArrayList.add(new Quiz("Which of the following is not a Java features?","Object-oriented","Use of Pointers", "Use of Pointers"));
        quizArrayList.add(new Quiz("_____ is used to find and fix bugs in the Java programs.","JDK","JDB", "JDB"));
        quizArrayList.add(new Quiz("Which of the following is a valid declaration of a char?","char ca = 'tea'","char ch = 'utea';", "char ch = 'utea';"));
        quizArrayList.add(new Quiz("What is the return type of the hashCode() method in the Object class?","Void","Int", "Int"));

        quizArrayList.add(new Quiz("What does the expression float a = 35 / 0 return?","0","Infinity", "Infinity"));
        quizArrayList.add(new Quiz("Evaluate the following Java expression, if x=3, y=5, and z=10:++z + y - y + z + x++","15","25", "25"));
        quizArrayList.add(new Quiz("Which of the following tool is used to generate API documentation in HTML format from doc comments in source code?","Javap tool","Javadoc tool", "Javadoc tool"));
        quizArrayList.add(new Quiz("Which of the following creates a List of 3 visible items and multiple selections abled?","new List(3, false)","new List(false, 3)", "new List(false, 3)"));
        quizArrayList.add(new Quiz("Which of the following for loop declaration is not valid?","for ( int i = 7; i <= 77; i += 7 )","for ( int i = 99; i >= 0; i / 9 )", "for ( int i = 99; i >= 0; i / 9 )"));

        quizArrayList.add(new Quiz("Which method of the Class. class is used to determine the name of a class represented by the class object as a String?","getClass()","getName()", "getName()"));
        quizArrayList.add(new Quiz("In which process, a local variable has the same name as one of the instance variables?","Abstraction","Variable Shadowing", "Variable Shadowing"));
        quizArrayList.add(new Quiz("Which of the following is true about the anonymous inner class?","It has only methods","It has no class name", "It has no class name"));
        quizArrayList.add(new Quiz("Which package contains the Random class?","Java.io package","Java.util package", "Java.util package"));
        quizArrayList.add(new Quiz("Which of the following is an immediate subclass of the Panel class?","Frame class","Applet class", "Applet class"));

        quizArrayList.add(new Quiz("Which of these classes are the direct subclasses of the Throwable class?","IOException and VirtualMachineError class","Error and Exception class", "Error and Exception class"));
        quizArrayList.add(new Quiz("What do you mean by chained exceptions in Java?","Exceptions occur in chains with discarding the debugging information","An exception caused by other exceptions", "An exception caused by other exceptions"));
        quizArrayList.add(new Quiz("In which memory a String is stored, when we create a string using new operator?","String memory","Heap memory", "Heap memory"));
        quizArrayList.add(new Quiz("What is the use of the intern() method?","It creates a new string in the database","It returns the existing string from the memory", "It returns the existing string from the memory"));
        quizArrayList.add(new Quiz("Which of the following is a marker interface?","Result interface","Remote interface", "Remote interface"));

        quizArrayList.add(new Quiz("Which of the following is a reserved keyword in Java?","object","strictfp", "strictfp"));
        quizArrayList.add(new Quiz("Which keyword is used for accessing the features of a package?","Export","import", "import"));
        quizArrayList.add(new Quiz("In java, jar stands for_____.","Java Archive Runner","None of the above", "None of the above"));
        quizArrayList.add(new Quiz("What is the use of w in regex?","Used for a whitespace character","Used for a word character", "Used for a word character"));
        quizArrayList.add(new Quiz("Which of the given methods are of Object class?","notify(), wait( long msecs ), and synchronized()","notify(), notifyAll(), and wait()", "notify(), notifyAll(), and wait()"));

        quizArrayList.add(new Quiz("Which of the following is a valid syntax to synchronize the HashMap?","Map m = hashMap.synchronizeMap();","Map m1 = Collections.synchronizedMap(hashMap);", "Map m1 = Collections.synchronizedMap(hashMap);"));
        quizArrayList.add(new Quiz("What is the initial quantity of the ArrayList list?","5","10", "10"));
        quizArrayList.add(new Quiz("Which of the following is a mutable class in java?","java.lang.String","java.lang.StringBuilder", "java.lang.StringBuilder"));
        quizArrayList.add(new Quiz("What is meant by the classes and objects that dependents on each other?","Cohesion","Tight coupling", "Tight coupling"));
        quizArrayList.add(new Quiz("How many threads can be executed at a time?","Single threads","Multiple threads", "Multiple threads"));

        quizArrayList.add(new Quiz("If three threads trying to share a single object at the same time, which condition will arise in this scenario?","Recursion","Race condition", "Race condition"));
        quizArrayList.add(new Quiz("If a thread goes to sleep","It releases all locks.","It does not release any locks.", "It does not release any locks."));
        quizArrayList.add(new Quiz("Which of the following modifiers can be used for a variable so that it can be accessed by any thread or a part of a program?","Transient","Volatile", "Volatile"));
        quizArrayList.add(new Quiz("In character stream I/O, a single read/write operation performs _____","One byte read/write at a time.","Two bytes read/write at a time.", "Two bytes read/write at a time."));
        quizArrayList.add(new Quiz("Which of the following interface is used to declare core methods in java?","Set","Collection", "Collection"));

        quizArrayList.add(new Quiz("Which of these interfaces handle sequences?","Set","List", "List"));
        quizArrayList.add(new Quiz("Which of the following declarations does not compile?","Int num1, num2;","Double num1, num2 = 0;", "Double num1, num2 = 0;"));
        quizArrayList.add(new Quiz("Which statement about valid .java file is true?","It can only contain one class declaration","It define at most one public class", "It define at most one public class"));
        quizArrayList.add(new Quiz("Which is correct about an instance variable of type string?","It will not compile without initializing on the declaration","It defaults to null", "It defaults to null"));
        quizArrayList.add(new Quiz("Which of the following does not compile?","Int num = 0;","Int num = _9_111;", "Int num = _9_111;"));

//        quizArrayList.add(new Quiz("What is true of the finalize() method?","It will be called exactly once","It may be called zero or one times", "It may be called zero or one times"));
//        quizArrayList.add(new Quiz("Which two primitives have wrapper classes that are not merely the name of the primitive with an uppercase letter?","Byte and int","Char and int", "Char and int"));
//        quizArrayList.add(new Quiz("Which of the following does not compile?","double num=2.11;","double num=2._11;", "double num=2._11;"));
//        quizArrayList.add(new Quiz("Which one is equality operator in java?","-=","!=", "!="));
//        quizArrayList.add(new Quiz("-=","Left shift operator","Zero fill right shift", "Zero fill right shift"));
//
//        quizArrayList.add(new Quiz("Which is not an operator in java?","||","</", "</"));
//        quizArrayList.add(new Quiz("The main() method of an application has to be public. Otherwise, it could not be called by a Java interpreter.","False","True", "True"));
//        quizArrayList.add(new Quiz("Java Language was initially called as?","Sumatra","Oak", "Oak"));
//        quizArrayList.add(new Quiz("Which one is a template for creating different objects?","Array","Class", "Class"));
//        quizArrayList.add(new Quiz("Which of these operators is used to allocate memory to array variable in java?","alloc","new", "new"));

    }

    public void saveFirebase() {
        FirebaseDatabase
                .getInstance("https://epicode-d3bb6-default-rtdb.firebaseio.com")
                .getReference("Scores")
                .push()
                .setValue(new Score(Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser())
                        .getUid(), "Java", new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), String.format("%d", currentScore)))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(QuizJavaActivity.this, "Save to firebase", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(QuizJavaActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                    }
                });
    }
}