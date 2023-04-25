package com.team5.quickcashteam5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class JobPostLanding extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobpostlanding);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,
                toolbar,
                R.string.navigaction_drawer_open,
                R.string.navigaction_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PostFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_post);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //have to do this for each item in the nav bar
        switch (item.getItemId()){
            case R.id.nav_post:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_mytasks:
                startActivity(new Intent(this, MyTasks.class));
                break;
            case R.id.nav_search:
                startActivity(new Intent(this, JobList.class));
                break;
            case R.id.nav_switch:
                startActivity(new Intent(this,JobSearchLanding.class));
                break;
            case R.id.nav_notify:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Notification()).commit();
                break;
            case R.id.nav_home:
                startActivity(new Intent(this,LandingPage.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

    }
}


