package com.example.csquestionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    TextView tv , textView , timer;
    Button submitButton , quitButton;
    RadioGroup radio_g;
    RadioButton rb1 , rb2 , rb3 , rb4;
    SharedPreferences sp;
    private int randomQuestionNumber = 0 , questionsCounter = 0 , i , removedQuestion;
    private long duration;
    boolean stillPlaying = true;
    String language , name;
    public static int marks=0 , correct=0 , wrong=0;
    BackgroundColor backgroundColor;
    LinkedList<Integer> list = new LinkedList<Integer>(); // For generating random question numbers and not repeat questions

    String[] answers = {"main method","<=","this","interface","public","import pkg.*","None of the mentioned","java","equals()","int"};
    String[] opt = {
            "finalize method","main method","static method","private method",
            "&","&=","|=","<=",
            "import","this","catch","abstract",
            "Interface","interface","intf","Intf",
            "public","protected","private","All of the mentioned",
            "Import pkg.","import pkg.*","Import pkg.*","import pkg.",
            "int","float","void","None of the mentioned",
            "lang","java","util","java.packages",
            "equals()","Equals()","isequal()","Isequal()",
            "int","long","byte","float"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] questions = questionsLanguage(); // Set questions by the language

        setContentView(R.layout.activity_questions);

        // Get Id's:
        final TextView score = findViewById(R.id.tvScore);
        textView=findViewById(R.id.dispName);
        submitButton=findViewById(R.id.button3);
        quitButton=findViewById(R.id.btnQuit);
        tv=findViewById(R.id.tvQue);
        radio_g=findViewById(R.id.answersGrp);
        rb1=findViewById(R.id.radioButton);
        rb2=findViewById(R.id.radioButton2);
        rb3=findViewById(R.id.radioButton3);
        rb4=findViewById(R.id.radioButton4);
        timer=findViewById(R.id.tvTimer);
        backgroundColor = findViewById(R.id.bgBackground);

        // Get player name:
        Intent intent = getIntent();
        name = intent.getStringExtra("myName");

        // Name entered? (It isn't an empty string?):
        if (name.trim().equals("")) { // No:
            String hello = getString(R.string.hello_user); // Set the string: hello = Hello User
            textView.setText(hello); // Apply
        }
        else { // Yes:
            String hello = getString(R.string.hello_text); // Set the string: hello = Hello "name"
            hello = hello + " " + name;
            textView.setText(hello); // Apply
        }

        // Set ArrayList for random questions:
        for(i = 1; i <= questions.length ; i++) {
            list.add(i);
        }

        // Set first question:
        i = list.element();
        randomQuestionNumber = i - 1; // Get a random first question number - it's -1 because the questions array starts from index 0 and list from 1
        tv.setText(questions[randomQuestionNumber]); // Set the first question
        rb1.setText(opt[randomQuestionNumber*4]); // Set answers:
        rb2.setText(opt[randomQuestionNumber*4 + 1]);
        rb3.setText(opt[randomQuestionNumber*4 + 2]);
        rb4.setText(opt[randomQuestionNumber*4 + 3]);
        questionsCounter++;
        removedQuestion = list.indexOf(i);
        list.remove(removedQuestion); // Remove the question number from the list - in order to not repeat questions
        Collections.shuffle(list); // Shuffle list

        // Set Timer:
        duration = TimeUnit.SECONDS.toMillis(30); // Timer duration
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                 // Convert millisecond to minute and second:
                 String sDuration = String.format(Locale.ENGLISH , "%02d : %02d" ,
                         TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) ,
                         TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                         TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                 // Set string to TextView (timer)
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                // Go to results (time's up):
                if(stillPlaying) { // If we are still answering questions:
                    if((sp.getString("Language", "en").equals("en"))) {
                        Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "נגמר הזמן!", Toast.LENGTH_SHORT).show();
                    }
                    Intent in = new Intent(getApplicationContext(), com.example.csquestionapp.ResultActivity.class);
                    in.putExtra("myName", name);
                    startActivity(in);
                } // else - player already  finished
            }
        }.start();

        // Get the randomized background color from shared prefs:
        sp = getApplicationContext().getSharedPreferences("Settings" , Context.MODE_PRIVATE);
        ActionBar actionBar = getSupportActionBar(); // set ActionBar language too
        actionBar.setTitle(getResources().getString(R.string.app_name)); // setTitle won't be null! (app_name was given a name)

        // Set Background Color:
        backgroundColor.setColor(MainActivity.currentBackgroundColor);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(QuestionsActivity.this , R.anim.righttoleft);
                submitButton.startAnimation(animation);

                if(radio_g.getCheckedRadioButtonId()==-1)
                {
                    if((sp.getString("Language", "en").equals("en"))) {
                        Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "אנא בחר אופציה אחת", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                RadioButton uans =findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if(ansText.equals(answers[randomQuestionNumber])) {
                    correct++;
                    if((sp.getString("Language", "en").equals("en"))) {
                        Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "נכון", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    wrong++;
                    if((sp.getString("Language", "en").equals("en"))) {
                        Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "לא נכון", Toast.LENGTH_SHORT).show();
                    }
                }

                questionsCounter++;

                if (score != null) {
                    score.setText((""+correct));
                }

                if(questionsCounter < questions.length)
                {
                    i = list.element();
                    randomQuestionNumber = i - 1; // Get a random question number - it's -1 because the questions array starts from index 0 and list from 1
                    tv.setText(questions[randomQuestionNumber]); // Set the question
                    rb1.setText(opt[randomQuestionNumber*4]); // Set answers:
                    rb2.setText(opt[randomQuestionNumber*4 +1]);
                    rb3.setText(opt[randomQuestionNumber*4 +2]);
                    rb4.setText(opt[randomQuestionNumber*4 +3]);
                    removedQuestion = list.indexOf(i);
                    list.remove(removedQuestion); // Remove the question number from the list - in order to not repeat questions
                    Collections.shuffle(list); // Shuffle list
                }
                else
                {
                    marks=correct;
                    stillPlaying = false;
                    Intent in = new Intent(getApplicationContext(), com.example.csquestionapp.ResultActivity.class);
                    in.putExtra("myName", name);
                    startActivity(in);
                }
                radio_g.clearCheck();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(QuestionsActivity.this , R.anim.blink_anim);
                quitButton.startAnimation(animation);
                stillPlaying = false;
                Intent intent=new Intent(getApplicationContext(), com.example.csquestionapp.ResultActivity.class);
                intent.putExtra("myName", name);
                startActivity(intent);
            }
        });
    }

    public String[] questionsLanguage() {

        // Set shared preferences:
        sp = getApplicationContext().getSharedPreferences("Settings" , Context.MODE_PRIVATE);
        language = sp.getString("Language", "en");

        if(language.equals("en")) { // English:
            return new String[]{
                    "Which method can be defined only once in a program?",
                    "Which of these is not a bitwise operator?",
                    "Which keyword is used by method to refer to the object that invoked it?",
                    "Which of these keywords is used to define interfaces in Java?",
                    "Which of these access specifiers can be used for an interface?",
                    "Which of the following is correct way of importing an entire package ‘pkg’?",
                    "What is the return type of Constructors?",
                    "Which of the following package stores all the standard java classes?",
                    "Which of these method of class String is used to compare two String objects for their equality?",
                    "An expression involving byte, int, & literal numbers is promoted to which of these?"
            };
        } else { // Hebrew:
            return new String[]{
                    "איזו שיטה ניתן להגדיר רק פעם אחת בתוכנית?",
                    "מי מאלה אינו מפעיל bitwise?",
                    "איזו מילת מפתח משמשת לשיטה להתייחס לאובייקט שהפעיל אותה?",
                    "אילו ממילות המפתח הללו משמשות להגדרת interfaces ב- Java?",
                    "באילו ממפרטי הגישה הללו ניתן להשתמש עבור interface?",
                    "איזו מהאפשרויות הבאות היא הדרך הנכונה לייבא חבילה שלמה 'pkg'?",
                    "מהו סוג ההחזרה של בנאים?",
                    "איזו חבילה מאחסנת את כל שיעורי הJava הסטנדרטיים?",
                    "איזו מהשיטות הללו של מחלקת String משמשת להשוואת שני אובייקטים של מחרוזת לשוויון שלהם?",
                    "למי מבין אלה מקודם ביטוי הכולל מספרים, בתים ואינטרטיים מילוליים?"

            };
        }
    }
}