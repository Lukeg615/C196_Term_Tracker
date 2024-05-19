package com.wgu.c196_term_tracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import database.AppDatabase;
import entities.Course;
import entities.Term;

public class AddCoursesActivity extends AppCompatActivity {
    Course createCourse;
    EditText courseNameEdit;
    String courseName;
    Button courseStartSelectBTN;
    Button courseEndSelectBTN;
    Calendar calender;
    DatePickerDialog picker;
    LocalDate courseStartDate;
    LocalDate courseEndDate;
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    Button termSelector;
    Button statusSelector;
    Button instructorSelector;
    Button addCourseButton;
    AppDatabase database;
    List<Term> termList;
    String[] termNames;
    int termIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        database = AppDatabase.getInstance(this.getApplicationContext());
        termList = database.termDAO().getAllTerms();
        populateView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, TermCoursesActivity.class);
        // Pass the term ID back to TermCoursesActivity
        intent.putExtra("TERM_ID", getIntent().getIntExtra("TERM_ID", -1));
        startActivity(intent);
        return true;
    }

    private void populateView() {
        courseNameEdit = findViewById(R.id.courseNameEditText);
        startDatePicker();
        endDatePicker();
        populateTermSpinner();
        addCourse();
    }


    private void startDatePicker() {
        courseStartSelectBTN = findViewById(R.id.courseStartSelectBTN);
        //Set an on-click listener
        courseStartSelectBTN.setOnClickListener(v -> {
            //Set values for the calendar
            calender = Calendar.getInstance();
            int day = calender.get(Calendar.DAY_OF_MONTH);
            int month = calender.get(Calendar.MONTH);
            int year = calender.get(Calendar.YEAR);
            //Create a date picker dialog
            picker = new DatePickerDialog(AddCoursesActivity.this, (view, year1, month1, dayOfMonth) -> {
                courseStartDate = LocalDate.of(year1, (month1 +1), dayOfMonth);
                courseStartSelectBTN.setText(formatter.format(courseStartDate));
            }, year, month, day);
            picker.show();
        });
    }

    private void endDatePicker() {
        courseEndSelectBTN = findViewById(R.id.courseEndSelectBTN);
        //Set an on-click listener
        courseEndSelectBTN.setOnClickListener(v -> {
            //Set values for the calendar
            calender = Calendar.getInstance();
            int day = calender.get(Calendar.DAY_OF_MONTH);
            int month = calender.get(Calendar.MONTH);
            int year = calender.get(Calendar.YEAR);
            //Create a date picker dialog
            picker = new DatePickerDialog(AddCoursesActivity.this, (view, year1, month1, dayOfMonth) -> {
                courseEndDate = LocalDate.of(year1, (month1 +1), dayOfMonth);
                courseEndSelectBTN.setText(formatter.format(courseEndDate));
            }, year, month, day);
            picker.show();
        });
    }

    private void populateTermSpinner() {
        termSelector = findViewById(R.id.termSelectBTN);
        //Populate spinner by database generated string array
        termNames = database.termDAO().getTermsArray();
        // Initialize the selected index
        AtomicInteger selected = new AtomicInteger(-1);

        // Create the AlertDialog
        AlertDialog termDialog = new AlertDialog.Builder(AddCoursesActivity.this)
                .setSingleChoiceItems(termNames, selected.get(),
                        (dialog, which) -> {
                            // Handle term selection
                            termSelector.setText(termNames[which]);
                            selected.set(which);
                            termIndex = which; // Update termIndex
                            // Dismiss the dialog
                            dialog.dismiss();
                        })
                .setTitle("Select Term")
                .create();

        // Set an OnClickListener for the termSelector
        termSelector.setOnClickListener(v -> {
            termDialog.getListView().setSelection(selected.get());
            termDialog.show();
        });
    }

    private boolean isNull() {
        if (courseNameEdit.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please add a course name", Toast.LENGTH_SHORT).show();
            return true;
        } else if (termIndex < 0) {
            Toast.makeText(this, "Please select a term", Toast.LENGTH_SHORT).show();
            return true;
        } else if (courseStartDate == null) {
            Toast.makeText(this, "Please select a start date", Toast.LENGTH_SHORT).show();
            return true;
        } else if (courseEndDate == null) {
            Toast.makeText(this, "Please select and end date", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return false;
    }

    private void addCourse() {
        addCourseButton = findViewById(R.id.addCourseBTN);
        // Set an on-click listener
        addCourseButton.setOnClickListener(v -> {
            courseName = courseNameEdit.getText().toString();
            // checks for null values
            if (isNull()) {
                return;
            }
            // Get the selected term ID from the database using termIndex
            int termId = termList.get(termIndex).getTermID();

            // If all fields are filled in, add the new term to the database.
            createCourse = new Course(null, courseName, courseStartDate, courseEndDate, termId);
            database.courseDAO().insertAll(createCourse);
            Toast.makeText(AddCoursesActivity.this, "Course was added successfully", Toast.LENGTH_SHORT).show();
            // Finish the current activity
            finish();
            // Send the user back to the previous TermCoursesActivity with the refreshed data.
            Intent intent = new Intent(AddCoursesActivity.this, TermCoursesActivity.class);
            // Pass the term ID back to TermCoursesActivity
            intent.putExtra("TERM_ID", getIntent().getIntExtra("TERM_ID", -1));
            startActivity(intent);
        });
    }

}