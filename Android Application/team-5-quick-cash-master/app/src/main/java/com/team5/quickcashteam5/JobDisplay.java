package com.team5.quickcashteam5;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class JobDisplay extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private final Calendar calendar = Calendar.getInstance();
    private final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_display);

        // Load navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.JobDisplayDrawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar,
                R.string.navigaction_drawer_open,
                R.string.navigaction_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Fetch selected job
        final Job myJob = getIntent().getParcelableExtra("myJob");

        // Generate Page with Job Details
        TextView JobTitle = findViewById(R.id.JobDisplayTitle);
        String myJobTitle = myJob.getTitle();
        if (myJobTitle == "" || myJobTitle == null) { myJobTitle = "No title provided"; }
        JobTitle.setText(myJobTitle);

        TextView JobDescription = findViewById(R.id.JobDisplayDescription);
        String myJobDescription = myJob.getDescription();
        if (myJobDescription == "" || myJobDescription == null) {
            myJobDescription = "No description provided";
        }
        JobDescription.setText(myJobDescription);

        TextView JobCreated = findViewById(R.id.JobDisplayCreateDate);
        calendar.setTime(myJob.getCreateDate());
        JobCreated.setText(format.format(calendar.getTime()));

        TextView JobDeadline = findViewById(R.id.JobDisplayDeadline);
        calendar.setTime(myJob.getDeadline());
        String myJobDeadline;
        if (calendar.get(Calendar.YEAR) == 1969) { myJobDeadline = "No deadline provided"; }
        else { myJobDeadline = format.format(calendar.getTime()); }
        JobDeadline.setText(myJobDeadline);

        TextView JobDurationText = findViewById(R.id.JobDisplayDuration);
        int JobDuration = myJob.getDuration();
        String myJobDuration;
        if (JobDuration == 0) { myJobDuration = "Duration not provided"; }
        else { myJobDuration = Integer.toString(JobDuration); }
        JobDurationText.setText(myJobDuration);

        TextView JobLocation = findViewById(R.id.JobDisplayLocation);
        String myJobLocation = myJob.getLocation();
        if (myJobLocation == "" || myJobLocation == null) {
            myJobLocation = "No location provided";
        }
        JobLocation.setText(myJobLocation);

        final TextView JobPaymentText = findViewById(R.id.JobDisplayPayment);
        final Double JobPayment = myJob.getPaymentAmount();
        String myJobPayment;
        if (JobPayment == 0) { myJobPayment = "Payment not provided"; }
        else { myJobPayment = Double.toString(JobPayment); }
        JobPaymentText.setText(myJobPayment);

        TextView JobEquipment = findViewById(R.id.JobDisplayEquip);
        ArrayList<String> JobEquipmentList = myJob.getEquipment();
        String myJobEquipment;
        if (JobEquipmentList.size() == 0) { myJobEquipment = "No equipment provided"; }
        else { myJobEquipment = JobEquipmentList.toString(); }
        JobEquipment.setText(myJobEquipment);

        TextView JobPictures = findViewById(R.id.JobDisplayPicture);
        ArrayList<String> JobPictureList = myJob.getPictures();
        String myJobPictures;
        if (JobPictureList.size() == 0) { myJobPictures = "No pictures provided"; }
        else { myJobPictures = JobPictureList.toString(); }
        JobPictures.setText(myJobPictures);



        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Button taskComplete = findViewById(R.id.taskComplete);
        taskComplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(myJob.getState() == 0){
                    Toast.makeText(JobDisplay.this, "Employee set job to complete", Toast.LENGTH_SHORT).show();
                    myJob.setState(6);
                    ref.child("Jobs").child(myJob.getJobID() + "").child("state").setValue(6);
                }
                else if(myJob.getState() == 6) {
                    Transaction transaction = new Transaction(JobDisplay.this, JobPayment);
                    transaction.ProcessPayment();
                    transaction.close();
                    String myJobID = myJob.getJobID();
                    myJob.setState(0);
                    ref.child("Jobs").child(myJobID + "").child("state").setValue(0);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //have to do this for each item in the nav bar
        switch (item.getItemId()){
            case R.id.nav_post:
                startActivity(new Intent(JobDisplay.this, JobPostLanding.class));
                break;
            case R.id.nav_search:
                startActivity(new Intent(JobDisplay.this, JobList.class));
                break;
        }
        return true;
    }
}