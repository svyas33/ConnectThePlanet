package com.example.android.connecttheplanet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateProjectActivity extends AppCompatActivity {

    String projName, projDetails,orgEmail;
    int numVolunteers, costOfProject;
    Button create;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        mAuth = FirebaseAuth.getInstance();

        projName = ((EditText) findViewById(R.id.create_projectNameTextView)).getText().toString();
        projDetails = ((EditText) findViewById(R.id.create_detailsTextView)).getText().toString();
        orgEmail = ((EditText)findViewById(R.id.create_organizeremail)).getText().toString();

        numVolunteers = Integer.parseInt(((EditText) findViewById(R.id.create_numVolunteersTextView)).getText().toString());
        costOfProject = Integer.parseInt(((EditText) findViewById(R.id.create_moneyNeededTextView)).getText().toString());

        final DatabaseReference currentProjectDb = FirebaseDatabase.getInstance().getReference().child("Project");


        create = (Button)findViewById(R.id.create_applyButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> projectInfo = new HashMap<>();
                projectInfo.put("name", projName);
                projectInfo.put("details", projDetails);
                projectInfo.put("numVolunteers", numVolunteers);
                projectInfo.put("cost", costOfProject);
                projectInfo.put("email", orgEmail);
                currentProjectDb.updateChildren(projectInfo);
            }
        });



    }
}
