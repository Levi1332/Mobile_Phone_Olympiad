package com.btk.course_main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class activity_for_test extends AppCompatActivity implements View.OnClickListener{
TextView totalQuestionsTextViev;
private DatabaseReference mDataBase;
private String achievement_key = "achievement";
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

        mDataBase = FirebaseDatabase.getInstance().getReference(achievement_key);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextViev.setText("Количество вопросов: "+ totalQuestion);

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
            passStatus = "Congratulations";
            updatePoints(50);
        }else {
            passStatus = "Fieled";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Счет: "+score+" из "+totalQuestion)
                .setPositiveButton("Exit",(dialogInterface,i)->goToMain())
                .setCancelable(false)
                .show();
    }
    private void goToMain()
    {
        Intent intent = new Intent(activity_for_test.this, MainActivity.class);
        startActivity(intent);
    }
    private void updatePoints(int pointsToAdd) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Получаем ссылку на документ пользователя
        DocumentReference userRef = db.collection("users").document(userId);

        // Получаем текущее значение поля "points" из базы данных
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Получаем текущее количество баллов пользователя
                    Long currentPoints = documentSnapshot.getLong("points");
                    if (currentPoints != null) {
                        // Прибавляем новые баллы к текущему количеству
                        long newPoints = currentPoints + pointsToAdd;
                        // Обновляем значение в базе данных
                        userRef.update("points", newPoints)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Баллы успешно обновлены
                                        // Можно добавить какую-то обратную связь или обновить UI
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Ошибка при обновлении баллов
                                        Log.e("MainActivity", "Ошибка при обновлении баллов: " + e.getMessage());
                                        // Можно добавить обработку ошибки
                                    }
                                });
                    }
                } else {
                    // Пользователь не найден в базе данных
                    Log.e("MainActivity", "Пользователь не найден в базе данных");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Ошибка при получении данных из базы данных
                Log.e("MainActivity", "Ошибка при получении данных из базы данных: " + e.getMessage());
            }
        });
    }

}