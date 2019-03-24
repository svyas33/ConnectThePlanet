package com.example.android.connecttheplanet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProjectActivity extends AppCompatActivity {

    String projName, projDetails,orgEmail;
    int numOfVolunteers, costOfProject;
    ImageView projImg;
    Button create;
    private FirebaseAuth mAuth;
    Uri resultUri;

    Activity activity = this;

    String numVolunteers, name, cost;
    static List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    static Map<String,Map<String, Object>> projectList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        mAuth = FirebaseAuth.getInstance();


        projImg = findViewById(R.id.create_imageView);

        final DatabaseReference currentProjectDb = FirebaseDatabase.getInstance().getReference().child("Project");

        create = (Button)findViewById(R.id.create_applyButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projName = ((EditText) findViewById(R.id.create_projectNameTextView)).getText().toString();
                projDetails = ((EditText) findViewById(R.id.create_detailsTextView)).getText().toString();
                orgEmail = ((EditText)findViewById(R.id.create_organizeremail)).getText().toString();

                numOfVolunteers = Integer.parseInt(((EditText) findViewById(R.id.create_numVolunteersTextView)).getText().toString());
                costOfProject = Integer.parseInt(((EditText) findViewById(R.id.create_moneyNeededTextView)).getText().toString());

                DatabaseReference childrenRef = currentProjectDb.child(projName);

                Map<String, Object> projectInfo = new HashMap<>();
                projectInfo.put("name", projName);
                projectInfo.put("details", projDetails);
                projectInfo.put("numVolunteers", numOfVolunteers);
                projectInfo.put("cost", costOfProject);
                projectInfo.put("email", orgEmail);
                childrenRef.updateChildren(projectInfo);
                projectList.put(projName,projectInfo);

                currentProjectDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            name = (String) childSnapShot.child("name").getValue();
                            numVolunteers = (String) String.valueOf(childSnapShot.child("numVolunteers").getValue());
                            cost = (String) String.valueOf(childSnapShot.child("cost").getValue());
                            Map<String, String> datum = new HashMap<String, String>(2);
                            datum.put("title", name);
                            datum.put("subtitle", (numVolunteers + " volunteers & $" + cost + " needed"));
                            data.add(datum);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        System.out.println("The read failed: " + firebaseError.getMessage());
                    }
                });




                activity.finish();
            }
        });

        ImageView addProjImg = findViewById(R.id.create_addImageButton);
        addProjImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            projImg.setImageURI(resultUri);
        }
    }
}
