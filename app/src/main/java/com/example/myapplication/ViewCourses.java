package com.example.myapplication;

import static com.example.myapplication.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CourseModal;

import java.util.ArrayList;

public class ViewCourses extends AppCompatActivity implements CourseRVAdapter.OnItemClickListener {

    private ArrayList<CourseModal> courseModalArrayList;
    private DBHandler dbHandler;
    private CourseRVAdapter courseRVAdapter;
    private RecyclerView coursesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_view_courses);

        courseModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewCourses.this);
        courseModalArrayList = dbHandler.readCourses();

        courseRVAdapter = new CourseRVAdapter(courseModalArrayList, ViewCourses.this, this);
        coursesRV = findViewById(id.idRVCourses);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewCourses.this, RecyclerView.VERTICAL, false);
        coursesRV.setLayoutManager(linearLayoutManager);

        coursesRV.setAdapter(courseRVAdapter);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the mainPageButton click listener
        View mainPageButton = findViewById(id.mainPageButton);
        if (mainPageButton != null) {
            mainPageButton.setOnClickListener(v -> navigateUpToMainActivity());
        }

    }

    private void updateRecyclerView() {
        // Refresh the data from the database
        courseModalArrayList.clear();
        courseModalArrayList.addAll(dbHandler.readCourses());
        courseRVAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUpToMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateUpToMainActivity() {
        NavUtils.navigateUpFromSameTask(this);
    }
    @Override
    public void onItemClick(CourseModal modal) {
        Intent i = new Intent(this, UpdateCourseActivity.class);
        i.putExtra("name", modal.getCourseName());
        i.putExtra("description", modal.getCourseDescription());
        i.putExtra("duration", modal.getCourseDuration());
        i.putExtra("tracks", modal.getCourseTracks());
        startActivity(i);
    }
}
