package com.team5.quickcashteam5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTasks extends AppCompatActivity implements FirebaseRecyclerAdapter.ItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref = db.getReference();
    private FirebaseRecyclerAdapter MyRecyclerAdapter;
    private FirebaseRecyclerAdapter MyRecyclerAdapter2;
    private DrawerLayout drawer;
    private String authId = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytasks);

        // Load navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.MyTasksDrawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigaction_drawer_open,
                R.string.navigaction_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Fetch jobs and create RecyclerView
        fetchTasks(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Job> value, ArrayList<Job> valueAccepted) {
                RecyclerView recyclerView = findViewById(R.id.MyTasksRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyTasks.this));
                MyRecyclerAdapter = new FirebaseRecyclerAdapter(MyTasks.this, value);
                MyRecyclerAdapter.setClickListener(MyTasks.this);
                recyclerView.setAdapter(MyRecyclerAdapter);

                RecyclerView recyclerView2 = findViewById(R.id.MyTasksRecycler2);
                recyclerView2.setLayoutManager(new LinearLayoutManager(MyTasks.this));
                MyRecyclerAdapter2 = new FirebaseRecyclerAdapter(MyTasks.this, valueAccepted);
                MyRecyclerAdapter2.setClickListener(MyTasks.this);
                recyclerView2.setAdapter(MyRecyclerAdapter2);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ViewGroup myView = (ViewGroup) view.getParent();
        int id = myView.getId();
        Job selectedJob = null;

        if (id == R.id.MyTasksRecycler) {
            selectedJob = MyRecyclerAdapter.getItem(position);
        }
        if (id == R.id.MyTasksRecycler2) {
            selectedJob = MyRecyclerAdapter2.getItem(position);
        }

        Intent JobDisplayIntent = new Intent(this, JobDisplay.class);
        JobDisplayIntent.putExtra("myJob", selectedJob);

        startActivity(JobDisplayIntent);
    }

    private void fetchTasks(final FirebaseCallback callback) {
        Query Tasks = ref.child("Jobs").orderByChild("state");

        Tasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Job> Jobs = new ArrayList<>();
                ArrayList<Job> Jobs2 = new ArrayList<>();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Job currJob = data.getValue(Job.class);
                    if (currJob.getAuthor().equals(authId)) {
                        if (currJob.getState() == 0) {
                            Jobs.add(currJob);
                        } else if (currJob.getState() == 6) {
                            Jobs2.add(currJob);
                        }
                    }
                }
                callback.onCallback(Jobs, Jobs2);
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

    private interface FirebaseCallback {
        void onCallback(ArrayList<Job> value, ArrayList<Job> valueAccepted);
    }
}
