package com.example.task31;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.graphics.Color;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import android.content.Intent;


public class QuizActivity extends AppCompatActivity {
    private String[] questionTitles = {
            "Question 1:",
            "Question 2:",
            "Question 3:",
            "Question 4:",
            "Question 5:"
    };

    private String[] questions = {
            "What’s the highest mountain in the world?",
            "What’s the biggest country in the world?",
            "Which of the following is not a color?",
            "Which of the following is the odd one?",
            "Which one is not a Scandinavian country?"
};
    private String[][] options = {
            {"Kanchenjunga", "Mount Everest", "K2"},
            {"Croatia", "United States","Russia" },
            { "Banana", "Purple", "Green"},
            {"Sun", "Moon", "Strawberry"},
            {"Sweden", "Egypt", "Norway"}
    };

    private String[] correctAnswers = {"Mount Everest", "Russia", "Banana", "Strawberry", "Egypt"};
    private String[] selectedAnswers;
    private int currentQuestionIndex = 0;
    private TextView questionTitleTextView;
    private TextView questionTextView;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button nextButton;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private Button takeNewQuizButton;
   private  Button finishButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String userName = getIntent().getStringExtra("userName");
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome " + userName + "!");

        progressTextView = findViewById(R.id.progressTextView);
        progressBar = findViewById(R.id.progressBar);
        questionTitleTextView = findViewById(R.id.questionTitleTextView);
        questionTextView = findViewById(R.id.questionTextView);
        answer1Button = findViewById(R.id.answer1Button);
        answer2Button = findViewById(R.id.answer2Button);
        answer3Button = findViewById(R.id.answer3Button);
        nextButton = findViewById(R.id.nextButton);
        selectedAnswers = new String[correctAnswers.length];


        // Display the first question
        displayQuestion(currentQuestionIndex);



        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(answer1Button.getText().toString());
            }
        });

        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(answer2Button.getText().toString());
            }
        });

        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(answer3Button.getText().toString());
            }
        });
    }

    private void displayQuestion(int index){
        // Calculate the progress (assuming currentQuestionIndex starts from 0)
        int progress = (currentQuestionIndex + 1) * 100 / questions.length;

// Update the ProgressBar
        progressBar.setProgress(progress);

// Display the progress in 1/5 format
        String progressText = (currentQuestionIndex + 1) + "/" + questions.length;
        progressTextView.setText(progressText);

        questionTitleTextView.setText(questionTitles[index]);
        questionTextView.setText(questions[index]);
        answer1Button.setText(options[index][0]);
        answer2Button.setText(options[index][1]);
        answer3Button.setText(options[index][2]);
    }

    private void checkAnswer(String selectedAnswer) {
        // Update selected answer
        selectedAnswers[currentQuestionIndex] = selectedAnswer;
        Button selectedButton = null;
        Button correctButton = null;

        // Determine which button was clicked
        if (selectedAnswer.equals(options[currentQuestionIndex][0])) {
            selectedButton = answer1Button;
        } else if (selectedAnswer.equals(options[currentQuestionIndex][1])) {
            selectedButton = answer2Button;
        } else if (selectedAnswer.equals(options[currentQuestionIndex][2])) {
            selectedButton = answer3Button;
        }

        // Determine the correct button
        if (correctAnswers[currentQuestionIndex].equals(options[currentQuestionIndex][0])) {
            correctButton = answer1Button;
        } else if (correctAnswers[currentQuestionIndex].equals(options[currentQuestionIndex][1])) {
            correctButton = answer2Button;
        } else if (correctAnswers[currentQuestionIndex].equals(options[currentQuestionIndex][2])) {
            correctButton = answer3Button;
        }


        // Check if the answer is correct
        if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
            // Correct answer
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            // Change the color of the selected button to green
            selectedButton.setBackgroundColor(Color.GREEN);
        } else {
            // Incorrect answer
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
            // Change the color of the selected button to red
            assert selectedButton != null;
            selectedButton.setBackgroundColor(Color.RED);
            // Change the color of the correct button to green
            assert correctButton != null;
            correctButton.setBackgroundColor(Color.GREEN);
        }

        final Button finalSelectedButton = selectedButton;
        final Button finalCorrectButton = correctButton;
        int purpleColor = ContextCompat.getColor(this, R.color.purple);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset the background color of both buttons
                finalSelectedButton.setBackgroundColor(purpleColor);
                assert finalCorrectButton != null;
                finalCorrectButton.setBackgroundColor(purpleColor);

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    displayQuestion(currentQuestionIndex);

                    }else {
                    // Quiz finished
                    showFinalScore();
                }


                // Check if it's the fourth question
                    if (currentQuestionIndex == 4) {

                        // Change nextButton to submitButton
                        nextButton.setText("Submit");
                    }
            }

            private int calculateScore() {
                int score = 0;
                for (int i = 0; i < correctAnswers.length; i++) {
                    String selectedAnswer = selectedAnswers[i]; // Corrected variable name
                    String correctAnswer = correctAnswers[i];
                    if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
                        score++;
                    }
                }
                return score;
            }
            private void showFinalScore() {
                setContentView(R.layout.activity_final);
                TextView congratsTextView = findViewById(R.id.congratsTextView);
                TextView scoreTextView = findViewById(R.id.scoreTextView);
                Button takeNewQuizButton = findViewById(R.id.takeNewQuizButton);
                Button finishButton = findViewById(R.id.finishButton);
                String userName = getIntent().getStringExtra("userName");

                congratsTextView.setText("Congratulations " + userName + "!");
                scoreTextView.setText(calculateScore() + "/" + questions.length);


                takeNewQuizButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                        intent.putExtra("userName", userName);
                        startActivity(intent);
                    }
                });


                finishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                    }

            });
        }
    });
}
}







