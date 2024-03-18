package com.example.task31;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText nameEditText = findViewById(R.id.nameEditText);

        // Check if there's a stored userName
        String userName = getIntent().getStringExtra("userName");
        if (userName != null) {
            // If userName is passed from QuizActivity, display it in the EditText
            nameEditText.setText(userName);
        }

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user's name from EditText
                String enteredUserName = nameEditText.getText().toString();

                // Start the QuizActivity and pass the userName
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("userName", enteredUserName);
                startActivity(intent);
            }
        });

    }
}



