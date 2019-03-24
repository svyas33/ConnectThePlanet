package com.example.android.connecttheplanet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DrawerLayout drawerLayout;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        Toolbar toolbar = findViewById(R.id.toolbarinmain);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_foreground);
        mAuth = FirebaseAuth.getInstance();
        activity = this;
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        Intent intent;
                        switch (menuItem.getItemId()){
                            case R.id.nav_projects_create:intent = new Intent(activity, CreateProjectActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_projects_view:intent = new Intent(activity, ViewProjectsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_user_profile:intent = new Intent(activity, MyProfile.class);
                                startActivity(intent);
                                break;
                        }



                        return true;
                    }
                });
    }

    private void sendUserToLogInActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void userNull(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            sendUserToLogInActivity();
        } else{
            updateUI(currentUser);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(getApplicationContext());
        userNull();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}
