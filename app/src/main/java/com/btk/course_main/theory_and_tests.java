package com.btk.course_main;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class theory_and_tests extends AppCompatActivity {
    private Button button1, button2, button3, button4, button5;
    private TextView textView;
    BottomNavigationView bottomNavigationView;
    private int topic_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_theory_and_tests);

        button1 = findViewById(R.id.point1);
        button2 = findViewById(R.id.point2);
        button3 = findViewById(R.id.point3);
        button4 = findViewById(R.id.point4);
        button5 = findViewById(R.id.point5);

        textView = findViewById(R.id.main_theory);
        Intent intent = getIntent();
        String selectedTheme = intent.getStringExtra("theme");

        defining_a_topic(selectedTheme);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beta_switch_topic(1);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beta_switch_topic(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beta_switch_topic(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beta_switch_topic(4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(theory_and_tests.this, activity_for_test.class);
                startActivity(intent);
            }
        });
    }
    private void beta_switch_topic(int button_number)
    {
        switch (topic_number)
        {
            case 1:
                get_text_for_print(button_number);
                break;
        }
    }
    private void get_text_for_print(int button_number)
    {
        final String[] text_for_print = new String[1];
        switch (button_number)
        {
            case 1:
                text_for_print[0] =  getString(R.string.text_lesson1);
                textView.setText(text_for_print[0]);
                break;
            case 2:
                text_for_print[0] =  getString(R.string.text_lesson1_2);
                textView.setText(text_for_print[0]);
                break;
            case 3:
                text_for_print[0] =  getString(R.string.text_lesson1_3);
                textView.setText(text_for_print[0]);
                break;
            case 4:
                text_for_print[0] =  getString(R.string.text_lesson1_4);
                textView.setText(text_for_print[0]);
                break;
        }
    }
    private void defining_a_topic(String selectedTheme) {
        switch (selectedTheme) {
            case "Тема 1":
                topic_number=1;
                break;
            case "Тема 2":
                textView.setText("Открыта вторая тема");
                topic_number=2;
                break;
            case "Тема 3":
                textView.setText("Открыта третья тема");
                topic_number=3;
                break;
            case "Тема 4":
                textView.setText("Открыта четвертая тема");
                topic_number=4;
                break;
            case "Тема 5":
                textView.setText("Тема пять");
                topic_number=5;
                break;
            case "Тема 6":
                textView.setText("Тема шесть");
                topic_number = 6;
                break;
        }
    }
}