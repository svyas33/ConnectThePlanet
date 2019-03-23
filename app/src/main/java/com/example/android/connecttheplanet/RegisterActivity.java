package com.example.android.connecttheplanet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText userEmail, userPassword,userName, userCompany, userBio;
    private TextView alreadyHaveAccountLink;
    private RadioGroup mRadioGroup;
    private DatabaseReference postsRef;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private FirebaseAuth mAuth;
    private RadioButton radioButton;
    DatabaseReference ref;
    String email, password, name, major,bio,year,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

}
