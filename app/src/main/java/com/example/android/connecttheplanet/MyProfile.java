package com.example.android.connecttheplanet;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile extends AppCompatActivity {
    EditText displayName, displayCompany, displayBio;
    Button signOutButton, confirmButton;
    String userID, name, company, bio;
    DatabaseReference mCustomerDatabase;
    private FirebaseAuth mAuth;

    public MyProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        displayBio = findViewById(R.id.my_profile_bio);
        displayName = findViewById(R.id.my_profile_name);
        displayCompany = findViewById(R.id.my_profile_company);
        confirmButton = findViewById(R.id.my_profile_confirm);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null){
            return;
        }
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        getUserInfo();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        signOutButton = (Button)findViewById(R.id.my_profile_signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });
    }

    private void getUserInfo() {
        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map userInfo = ( Map<String, Object>) dataSnapshot.getValue();
                if(userInfo ==null){
                    return;
                }
                if(userInfo.get("name")!=null){
                    name = userInfo.get("name").toString();
                    displayName.setText(name);
                }
                if(userInfo.get("company")!=null){
                    company = userInfo.get("company").toString();
                    displayCompany.setText(company);
                }
                if(userInfo.get("bio")!=null){
                    bio = userInfo.get("bio").toString();
                    displayBio.setText(bio);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveUserData() {
        bio = displayBio.getText().toString();
        name = displayName.getText().toString();
        company = displayCompany.getText().toString();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", name);
        userInfo.put("bio", bio);
        userInfo.put("company", company);
        mCustomerDatabase.updateChildren(userInfo);
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        return;
    }

}