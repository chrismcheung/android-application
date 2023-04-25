package com.team5.quickcashteam5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobList extends AppCompatActivity implements FirebaseRecyclerAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private FirebaseRecyclerAdapter MyRecyclerAdapter;
    //private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        //Leaving this in because i am scared
        // Load navigation bar
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //drawer = findViewById(R.id.JobListDrawer);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,
        //        toolbar,
        //        R.string.navigaction_drawer_open,
        //        R.string.navigaction_drawer_close);

        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

        //NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);


        // Fetch available jobs and create RecyclerView.
        fetchJobs(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Job> value) {
                RecyclerView recyclerView = findViewById(R.id.JobListRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(JobList.this));
                MyRecyclerAdapter = new FirebaseRecyclerAdapter(JobList.this, value);
                MyRecyclerAdapter.setClickListener(JobList.this);
                recyclerView.setAdapter(MyRecyclerAdapter);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Job selectedJob = MyRecyclerAdapter.getItem(position);
        Intent JobDisplayIntent = new Intent(this, JobDisplay.class);
        JobDisplayIntent.putExtra("myJob", selectedJob);

        startActivity(JobDisplayIntent);
    }

    private void fetchJobs(final FirebaseCallback callback) {
        Query HiringJobs = db.child("Jobs").orderByChild("state").equalTo(0);

        HiringJobs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Job> Jobs = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Job currJob = data.getValue(Job.class);
                    Jobs.add(currJob);
                }

                callback.onCallback(Jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Nothing needed for cancellations at the moment.
            }
        });
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
            case R.id.nav_search:
                break;
            /*case R.id.nav_mytasks:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyTasksFragment()).commit();
                break; */
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

    private interface FirebaseCallback {
        void onCallback(ArrayList<Job> value);
    }
}