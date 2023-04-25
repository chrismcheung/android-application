package com.team5.quickcashteam5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class NavHeaderFragment extends Fragment {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    TextView welcome, welcomeEmail;
    String userID, userFirstName, userEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.nav_header,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();

        welcome = (TextView) getActivity().findViewById(R.id.navWelcome);
        welcomeEmail = (TextView) getView().findViewById(R.id.navWelcomeEmail);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> it = snapshot.getChildren().iterator();
                userEmail = it.next().getValue(userEmail.getClass());
                userFirstName = it.next().getValue(userFirstName.getClass());
                welcome.setText("Welcome, " + userFirstName);
                welcomeEmail.setText(userEmail);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}