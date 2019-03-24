package com.example.android.connecttheplanet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
    String email, password, name, company,bio,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user !=null){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        userEmail = findViewById(R.id.register_email);
        userPassword = findViewById(R.id.register_password);
        userName = findViewById(R.id.register_name);
        userCompany = findViewById(R.id.register_company);
        userBio = findViewById(R.id.sign_up_bio);
        mRadioGroup = findViewById(R.id.radioGroup);
        alreadyHaveAccountLink = findViewById(R.id.already_have_account);
        alreadyHaveAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToLoginActivity();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        createAccountButton = findViewById(R.id.register_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = userEmail.getText().toString();
                password = userPassword.getText().toString();
                name = userName.getText().toString();
                company = userCompany.getText().toString();
                bio = userBio.getText().toString();
                int radioId = mRadioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(radioId);
                if(radioButton.getText()==null){
                    return;
                }
                status = radioButton.getText().toString();
                ref = FirebaseDatabase.getInstance().getReference();
                createUser();
            }
        });

    }

    private void createUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("User").child(userId);
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("name", name);
                            userInfo.put("status", status);
                            userInfo.put("bio", bio);
                            userInfo.put("company", company);
                            userInfo.put("profileImageUrl", "default");
                            currentUserDb.updateChildren(userInfo);
                            //updateUI(user);
                            sendUserToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void sendUserToMainActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }


}