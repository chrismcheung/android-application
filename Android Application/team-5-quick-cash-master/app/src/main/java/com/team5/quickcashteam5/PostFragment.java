package com.team5.quickcashteam5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostFragment extends Fragment {

    private DatabaseReference ref;
    private FirebaseDatabase db;
    String userID;

    com.google.firebase.auth.FirebaseUser user;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        //bind the view
        final View view =   inflater.inflate(R.layout.post_fragment,container,false);

        //Bind the components
        Button submitBtn = (Button)view.findViewById(R.id.submitTask);

        //when submitBtn is clicked
        submitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View InputFragmentView)
            {
                //validate inputs
                //bind input text
                EditText title1 = (EditText)view.findViewById(R.id.titleOfTask);
                String title = title1.getText().toString().trim();
                EditText description1 = (EditText)view.findViewById(R.id.descriptionOfTask);
                String description = description1.getText().toString().trim();
                EditText location1 = (EditText)view.findViewById(R.id.locationOfTask);
                String location = location1.getText().toString().trim();
                EditText duration1 = (EditText)view.findViewById(R.id.durationOfTask);
                String duration = duration1.getText().toString().trim();
                EditText payment1 = (EditText)view.findViewById(R.id.paymentOfTask);
                String payment = payment1.getText().toString().trim();


                //check if any field is empty
                if (title.isEmpty()){
                    title1.setError("Please enter the title!");
                    title1.requestFocus();
                    return;
                }
                if (description.isEmpty()){
                    description1.setError("Please enter the description!");
                    description1.requestFocus();
                    return;
                }
                if (location.isEmpty()){
                    location1.setError("Please enter the location!");
                    location1.requestFocus();
                    return;
                }
                if (duration.isEmpty()){
                    duration1.setError("Please enter the duration!");
                    duration1.requestFocus();
                    return;
                }else if(!duration.matches("[0-9]*")){
                    duration1.setError("It must be a number!");
                    duration1.requestFocus();
                    return;
                }
                if (payment.isEmpty()){
                    payment1.setError("Please enter the payment!");
                    payment1.requestFocus();
                    return;
                }else if(!payment.matches("[0-9]*")){
                    payment1.setError("It must be a number!");
                    payment1.requestFocus();
                    return;
                }

                db = FirebaseDatabase.getInstance();
                ref = db.getReference();

                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();

                Job job = new Job(userID);
                job.setTitle(title);
                job.setDescription(description);
                job.setLocation(location);
                job.setDuration(Integer.parseInt(duration));
                job.setPaymentAmount(Double.parseDouble(payment));
                job.setJobID(job.getCreateDate().getTime() + userID);
                ref.child("Jobs").child(job.getJobID()).setValue(job);
            }
        });

        return view;
    }
}
