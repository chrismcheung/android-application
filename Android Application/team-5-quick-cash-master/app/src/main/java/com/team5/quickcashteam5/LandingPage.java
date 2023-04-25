package com.team5.quickcashteam5;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LandingPage extends AppCompatActivity implements View.OnClickListener{
    Button jobSearchNav1;
    Button jobPostLNav1;
    TextView welcome;
    String userID;
    FirebaseUser user;
    String userFirstName = "";
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Button logout;

    EditText amountView;
    double amount;
    Button send;
    private static final String PAYPAL_USER_ID = "AQrQxP1U3uCcJswI9ZLK2b98BNi9wLYDI3KN5C6TtfMrsN5OWoisXWNT303mosgRsz3th1ROamfDUcaS";
    private static final int PAYPAL_REQUEST_CODE = 473;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_USER_ID);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginlanding);
        jobPostLNav1 = (Button) findViewById(R.id.jobPostLNav);
        jobSearchNav1 = (Button) findViewById(R.id.jobSearchNav);
        welcome = (TextView) findViewById(R.id.landingWelcome);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        logout = (Button) findViewById(R.id.buttonLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read or create a local document to write down the session
                SharedPreferences preferences = getSharedPreferences("file",MODE_PRIVATE);
                //create an editor for writing on this file
                SharedPreferences.Editor editor = preferences.edit();
                //if it is checked then set it to "true"
                editor.putString("remembered","false");
                //write it
                editor.apply();
                //directing user to login page
                Intent intent = new Intent(LandingPage.this,MainActivity.class);
                startActivity(intent);
            }
        });


        //This block of code just doesn't seem to trigger sometimes, not sure why -June
        ref.child("Users").child(userID).child("firstname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userFirstName = snapshot.getValue(userFirstName.getClass());
                welcome.setText("Hello " + userFirstName + ", please select an option below.");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        jobPostLNav1.setOnClickListener(this);
        jobSearchNav1.setOnClickListener(this);


    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.jobPostLNav:
                startActivity(new Intent(LandingPage.this,JobPostLanding.class));
                break;
        }
        switch(v.getId()){
            case R.id.jobSearchNav:
                startActivity(new Intent(LandingPage.this,JobSearchLanding.class));
                break;
        }
    }
}
