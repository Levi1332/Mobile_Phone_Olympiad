package com.btk.course_main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class activity_for_test extends AppCompatActivity implements View.OnClickListener{
TextView totalQuestionsTextViev;
TextView questionsTextViev;
Button ansA,ansB,ansC,ansD;
Button submitBtn;

int score = 0;
int totalQuestion = QustionAnswer.qustion.length;
int currentQuestionIndex = 0;
String selectedAnswer = "";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_for_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        totalQuestionsTextViev = findViewById(R.id.total_question);
        questionsTextViev = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextViev.setText("Total question :"+ totalQuestion);

        loadNewQuestion();
    }

    @Override
    public void onClick(View v) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) v;
        if (clickedButton.getId()==R.id.submit_btn)
        {
            if (selectedAnswer.equals(QustionAnswer.correctAnswers[currentQuestionIndex])){
                score++;
            }
            currentQuestionIndex++;
            loadNewQuestion();

        }else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);

        }
    }
    private void loadNewQuestion(){

        if (currentQuestionIndex== totalQuestion)
        {
            finishQuiz();
            return;
        }
        questionsTextViev.setText(QustionAnswer.qustion[currentQuestionIndex]);
        ansA.setText(QustionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QustionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QustionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QustionAnswer.choices[currentQuestionIndex][3]);
    }
    private void finishQuiz(){
        String passStatus = "";
        if(score>totalQuestion*0.60)
        {
            passStatus = "Passed";
        }else {
            passStatus = "Fieled";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is"+score+"out of "+totalQuestion)
                .setPositiveButton("Restart",(dialogInterface,i)->restartQuis())
                .setCancelable(false)
                .show();
    }
    private void restartQuis(){
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}