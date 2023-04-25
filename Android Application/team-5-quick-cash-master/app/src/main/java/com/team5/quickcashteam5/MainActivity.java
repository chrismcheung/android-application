package com.team5.quickcashteam5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    TextView register;
    String email1, password1;



    EditText email, password;

    Button login;


    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button);

        //read the remember file that has been created for checking if the user's session is remembered or not
        //get the file "checkBox"
        SharedPreferences preferences = getSharedPreferences("file",MODE_PRIVATE);
        //save the value
        String checkbox = preferences.getString("remembered","");

        //if the checkbox is set to "true"
        if(checkbox.equals("true")){
            //directing user to landing page
            Intent intent = new Intent(MainActivity.this,LandingPage.class);
            startActivity(intent);

        }





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                email1 = email.getText().toString();
                password1 = password.getText().toString();
                if (email1.isEmpty()){
                    email.setError("Please enter your email address!");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    email.setError("This email is invalid!");
                    email.requestFocus();
                    return;
                }
                if (password1.isEmpty()){
                    password.setError("Please enter the password!");
                    password.requestFocus();
                    return;
                }

                else {
                    //Show("Logging into " + email1);
                    mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Failed! Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                                //read or create a local document to write down the session
                                SharedPreferences preferences = getSharedPreferences("file",MODE_PRIVATE);
                                //create an editor for writing on this file
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("remembered","true");
                                //write it
                                editor.apply();

                                startActivity(new Intent(MainActivity.this, LandingPage.class));
                            }
                        }
                    });
                }
            }
        });

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);



    }


    public void Show(String t){
        Toast.makeText(MainActivity.this,t,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterPage.class));
                break;
        }
    }
}


