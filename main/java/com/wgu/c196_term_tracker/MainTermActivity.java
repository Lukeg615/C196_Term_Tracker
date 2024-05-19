package com.wgu.c196_term_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import adapters.TermAdapter;
import database.AppDatabase;
import entities.Term;

public class MainTermActivity extends AppCompatActivity implements TermAdapter.OnTermListener {
    List<Term> terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());
        terms = db.termDAO().getAllTerms();

        termRecyclerView();

    }

    private void termRecyclerView() {
        RecyclerView RecyclerView = findViewById(R.id.termsRecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        RecyclerView.addItemDecoration(decoration);
        TermAdapter termListAdapter = new TermAdapter(this, terms, this);
        RecyclerView.setAdapter(termListAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onTermClick(int position) {
        Term selectedTerm = terms.get(position);
        Intent intent = new Intent(this, TermCoursesActivity.class);
        // Sends Term ID to TermDetailsActivity
        intent.putExtra("TERM_ID", selectedTerm.getTermID());
        startActivity(intent);
    }

    public void addTermButtonClick(View view) {
        Intent intent = new Intent(this, AddTermActivity.class);
        startActivity(intent);
    }

}