package com.example.android.connecttheplanet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;

public class ViewProjectsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String name, numVolunteers, cost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_projects);

        mAuth = FirebaseAuth.getInstance();

        ListView mListView = findViewById(R.id.project_listView);

        SimpleAdapter adapter = new SimpleAdapter(this, CreateProjectActivity.data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "subtitle"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        mListView.setAdapter(adapter);
    }
}
