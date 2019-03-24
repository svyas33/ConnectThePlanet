package com.example.android.connecttheplanet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        ((TextView)findViewById(R.id.main_message)).setText(Html.fromHtml("Dear Reader,<br/><br/>Welcome to our app. Here at Connect The Planet our goal is to connect people willing to help and initiate change in their communities. Our goal is to connect people with ideas to those willing to help make these ideas a reality. \n\n Do you have a way to better your community? Are you itching to help out and volunteer for community projects? Is your company interested in sponsoring community service project? If so, we are here to help! \n\nPlease take time to read through our website to get more familiarized with our goals and how you can help make an impact! <br/><br/>Best, <br/><br/>The Connect The Planet Team"));
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

        Button siteButton = findViewById(R.id.button);
        siteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://35.222.234.217/index.php";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
