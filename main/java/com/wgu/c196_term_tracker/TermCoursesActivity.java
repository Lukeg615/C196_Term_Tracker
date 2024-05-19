package com.wgu.c196_term_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import adapters.CourseAdapter;
import adapters.TermAdapter;
import database.AppDatabase;
import entities.Course;
import entities.Term;

public class TermCoursesActivity extends AppCompatActivity implements CourseAdapter.OnCourseListener {
    TextView termName;
    Term term;
    AppDatabase database;
    List<Course> courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_courses);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        database = AppDatabase.getInstance(this.getApplicationContext());

        // Receive the term ID from the intent extra
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("TERM_ID")) {
            int termId = intent.getIntExtra("TERM_ID", -1);
            if (termId != -1) {
                // Use the term ID to fetch the term details from the database
                term = database.termDAO().getTermById(termId);
                if (term != null) {
                    // Populate views with term details
                    populateViews(term);
                    // Create the view
                    courseRecyclerView();
                }
            }
        }
    }


    private void courseRecyclerView() {
        RecyclerView RecyclerView = findViewById(R.id.coursesRecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView.addItemDecoration(decoration);
        // Fetch courses for the selected term from the database
        List<Course> courses = database.courseDAO().getCoursesByTermId(term.getTermID());
        CourseAdapter courseListAdapter = new CourseAdapter(this, courses, this);
        RecyclerView.setAdapter(courseListAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainTermActivity.class);
        startActivity(intent);
        return true;
    }



    private void populateViews(Term term) {
        this.term = term;
        termName = findViewById(R.id.detailsTermName);
        // Populate TextViews with term details
        termName.setText(term.getTermName());
        // Populate other views as needed
    }

    @Override
    public void onCourseClick(int position) {
    // enter what happens when a course is clicked
    }

    public void addCourseButtonClick(View view) {
        Intent intent = new Intent(this, AddCoursesActivity.class);
        // Pass the term ID to AddCoursesActivity
        intent.putExtra("TERM_ID", term.getTermID());
        startActivity(intent);
    }

}