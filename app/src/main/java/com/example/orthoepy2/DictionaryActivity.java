package com.example.orthoepy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class DictionaryActivity extends AppCompatActivity {

    private static final String TAG = "DictionaryActivity";
    private FloatingActionButton back;
    private View user_item;
    private View study_item;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        back = findViewById(R.id.fab_dic);
        user_item = findViewById(R.id.app_bar_user);
        user_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DictionaryActivity.this, UserActivity.class);
                startActivity(i);
            }
        });
        study_item = findViewById(R.id.app_bar_study);
        study_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DictionaryActivity.this, StudyActivity.class);
                startActivity(i);
            }
        });
        list = new ArrayList<>();;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DictionaryActivity.this, SignInActivity.class);
                startActivity(i);
            }
        });
        listView = findViewById(R.id.listView);
        list.addAll(Arrays.asList(getResources().getStringArray(R.array.dictionary_words)));
        adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item, list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_word);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}