package com.btk.course_main;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ferivication extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPussword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ferivication);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPussword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPussword.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Не правильный емейл");
                }
                if (pass.isEmpty()) {
                    signupPussword.setError("Не верный пароль");
                } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Пользователь успешно зарегистрирован
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                if (firebaseUser != null) {
                                    // Получаем идентификатор пользователя
                                    String userId = firebaseUser.getUid();
                                    // Обновляем базу данных, чтобы начислить пользователю 100 поинтов
                                    updateUserPoints(userId);
                                }
                                Toast.makeText(ferivication.this, "Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ferivication.this, activity_login.class));
                            } else {
                                // Произошла ошибка при регистрации
                                Toast.makeText(ferivication.this, "При регестрации произошла ошибка" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    private void updateUserPoints(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Создаем хэшмэп с данными о начальном количестве поинтов
        Map<String, Object> userPoints = new HashMap<>();
        userPoints.put("points", 100);

        // Обновляем запись о пользователе в базе данных
        db.collection("users").document(userId)
                .set(userPoints, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Данные о начальных поинтах успешно добавлены в базу данных");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Ошибка при добавлении данных о начальных поинтах в базу данных", e);
                    }
                });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ferivication.this,activity_login.class));
            }
        });

    }
}