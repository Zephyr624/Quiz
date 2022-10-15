package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;
    private Button promptButton;
    private boolean answerWasShown;
    private boolean answer;
    private Question[] questions= new Question[] {
            new Question(R.string.q_easy, false),
            new Question(R.string.q_time, true),
            new Question(R.string.q_ques, false ),
            new Question(R.string.q_fill, true),
            new Question(R.string.q_last, true)
    };
    private int currentIndex = 0;
    private static final String KEY_CURRENT_INDEX="currentIndex";
    public static final String KEY_EXTRA_ANSWER="Quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private void checkAnswer(boolean userAnswer){
        boolean correctAnswer=questions[currentIndex].isTrueAnswer();
        int resultMessageId=0;
        if(answerWasShown){
            resultMessageId=R.string.answer_was_shown;
        }else{
        if(userAnswer==correctAnswer){
            resultMessageId=R.string.correct_answer;
        }else{
            resultMessageId=R.string.false_answer;
        }
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }
    private void setNext(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    private static final String TAG = "MainActivity";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"Wywołano onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Resumed");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Started");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Paused");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE_PROMPT){
            if(data==null){return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Created");
        setContentView(R.layout.activity_main);
        if(savedInstanceState!= null){
            currentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questionTextView=findViewById(R.id.question_text_view);
        promptButton=findViewById(R.id.prompt_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex=(currentIndex+1)%questions.length;
                answerWasShown = false;
                setNext();
            }
        });
        promptButton.setOnClickListener((view -> {
            Intent intent=new Intent(MainActivity.this,PromptActivity.class);
            boolean correctAnswer=questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
            startActivityForResult(intent,REQUEST_CODE_PROMPT);
        }));

        setNext();
    }

}