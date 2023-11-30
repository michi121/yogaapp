package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button, and dbhandler
    private EditText courseNameEdt, courseTracksEdt, courseDurationEdt, courseDescriptionEdt;
    private Button addCourseBtn, readCourseBtn;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseTracksEdt = findViewById(R.id.idEdtCourseTracks);
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration);
        courseDescriptionEdt = findViewById(R.id.idEdtCourseDescription);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        readCourseBtn = findViewById(R.id.idBtnReadCourse);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);

        // below line is to add on click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });

        readCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity via an intent.
                Intent i = new Intent(MainActivity.this, ViewCourses.class);
                startActivity(i);
            }
        });
    }

    private void onSubmitClick() {
        if (validateAndSubmit()) {
            // Start the ConfirmationActivity only if validation is successful
            Intent confirmationIntent = new Intent(MainActivity.this, ConfirmationActivity.class);
            confirmationIntent.putExtra("confirmationMessage", getConfirmationMessage());
            startActivity(confirmationIntent);
        }
    }

    private String getConfirmationMessage() {
        // Create and return the confirmation
        return "Course details:\n" +
                "Name: " + courseNameEdt.getText().toString().trim() + "\n" +
                "Tracks: " + courseTracksEdt.getText().toString().trim() + "\n" +
                "Duration: " + courseDurationEdt.getText().toString().trim() + "\n" +
                "Description: " + courseDescriptionEdt.getText().toString().trim();
    }

    private boolean validateAndSubmit() {
        String courseName = courseNameEdt.getText().toString();
        String courseTracks = courseTracksEdt.getText().toString();
        String courseDuration = courseDurationEdt.getText().toString();
        String courseDescription = courseDescriptionEdt.getText().toString();


        if (courseName.isEmpty() && courseTracks.isEmpty() && courseDuration.isEmpty() && courseDescription.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return false;
        }

        dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks);

        dbHandler.addNewCourse(courseName, courseDuration, courseDescription, courseTracks);

        // No need to set another click listener for readCourseBtn here, as it's already set in the onCreate method.
        return true; // Return true to indicate successful validation and submission.
    }

}
