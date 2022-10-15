package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {
    private boolean correctAnswer;
    private Button showCorrect;
    private TextView answerTextView;
    private TextView askTextView;
    public static final String KEY_EXTRA_ANSWER_SHOWN="Quiz.answerShown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        showCorrect=findViewById(R.id.show_correct);
        answerTextView=findViewById(R.id.answer_text);
        askTextView=findViewById(R.id.ask_text);
        correctAnswer=getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);
        showCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer=correctAnswer?R.string.button_true:R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });

    }
    private void setAnswerShownResult(boolean answerWasShown){
        Intent resultIntent=new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(RESULT_OK,resultIntent);
    }
}