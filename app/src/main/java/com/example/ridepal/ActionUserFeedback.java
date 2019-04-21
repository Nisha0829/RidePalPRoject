package com.example.ridepal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ActionUserFeedback extends AppCompatActivity {
    DataBaseHelper feedbackEntry;
    EditText feedbackEditText;
    String feedbackValue;
    String emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_user_feedback);
        feedbackEditText = (EditText)findViewById(R.id.feedback);
        feedbackEntry = new DataBaseHelper(this);
        emailId = "abc";
        feedbackValue = feedbackEditText.getText().toString();
        final RatingBar oneRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        Button submitButton = (Button) findViewById(R.id.submitFeedback);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackValue = feedbackEditText.getText().toString();
                String totalStars = "Total Stars:: " + oneRatingBar.getNumStars();
                String rating = "Rating :: " + oneRatingBar.getRating();
                if(!feedbackValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                    feedbackEntry.addFeedBack(feedbackValue,(int)oneRatingBar.getRating(), emailId);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter feedback", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
