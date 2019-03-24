package com.example.android.connecttheplanet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class ProjectInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_info);
        String name= getIntent().getStringExtra("name");
        Map<String, Object> projectInfo = CreateProjectActivity.projectList.get("name");
        ((TextView)findViewById(R.id.info_bio)).setText((String)projectInfo.get("details"));
        ((TextView)findViewById(R.id.info_projectNameTextView)).setText((String)projectInfo.get("name"));
        ((TextView)findViewById(R.id.info_volunteers)).setText((String)projectInfo.get("numVolunteers"));
        ((TextView)findViewById(R.id.info_money)).setText((String)projectInfo.get("cost"));
        ((TextView)findViewById(R.id.info_organizeremail)).setText((String)projectInfo.get("email"));
    }
}
