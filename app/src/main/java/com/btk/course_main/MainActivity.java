package com.btk.course_main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private Button button1, button2, button3, button4, button5, button6;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);



        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference userRef = db.collection("users").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                textView = findViewById(R.id.textView3);
                if (documentSnapshot.exists()) {
                    Long points = documentSnapshot.getLong("points");
                    if (points != null) {
                        textView.setText("Ваши поинты: " + points.toString());
                    } else {
                        textView.setText("Данные о поинтах отсутствуют");
                    }
                } else {
                    textView.setText("Пользователь не найден в базе данных");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                textView.setText("Ошибка при получении данных из базы данных: " + e.getMessage());
            }
        });
    
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id;
                if (item.getItemId() == R.id.about)
                    id = 1;
                else if (item.getItemId() == R.id.home)
                    id = 2;
                else if (item.getItemId() == R.id.dashboard)
                    id = 3;
                else id = 0;
                switch (id) {
                    case 3:
                        startActivity(new Intent(getApplicationContext(), Dashboad.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case 2:
                        return true;

                    case 1:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_new_activity(6);
            }
        });
    }

    private void start_new_activity(int button_number) {
        switch (button_number) {
            case 1:
                defining_team("Тема 1");
                break;
            case 2:
                defining_team("Тема 2");
                break;
            case 3:
                defining_team("Тема 3");
                break;
            case 4:
                defining_team("Тема 4");
                break;
            case 5:
                defining_team("Тема 5");
                break;
            case 6:
                defining_team("Тема 6");
                break;
        }
    }

    private void defining_team(String team) {
        Intent intent = new Intent(MainActivity.this, theory_and_tests.class);
        intent.putExtra("theme", team);
        startActivity(intent);
    }
}