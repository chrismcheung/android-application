package com.team5.quickcashteam5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    Button registerbutton1;
    EditText firstname1,lastname1,emailaddress1,registerpassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiser);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerbutton1 = (Button) findViewById(R.id.registerbutton);
        registerbutton1.setOnClickListener(this);
        firstname1 = (EditText) findViewById(R.id.firstname);
        lastname1 = (EditText) findViewById(R.id.lastname);
        emailaddress1 = (EditText) findViewById(R.id.emailaddress);
        registerpassword1 = (EditText) findViewById(R.id.registerpassword);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerbutton:
                registerbutton();
                break;
        }
    }

    private void registerbutton() {
        final String firstname = firstname1.getText().toString().trim();
        final String lastname = lastname1.getText().toString().trim();
        final String emailaddress = emailaddress1.getText().toString().trim();
        String registerpassword = registerpassword1.getText().toString().trim();
        if (firstname.isEmpty()){
            firstname1.setError("Please enter your first name!");
            firstname1.requestFocus();
            return;
        }
        if (lastname.isEmpty()){
            lastname1.setError("Please enter your last name!");
            lastname1.requestFocus();
            return;
        }
        if (emailaddress.isEmpty()){
            emailaddress1.setError("Please enter your email address!");
            emailaddress1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()){
            emailaddress1.setError("This email is invalid!");
            emailaddress1.requestFocus();
            return;
        }
        if (registerpassword.isEmpty()){
            registerpassword1.setError("Please set your password!");
            registerpassword1.requestFocus();
            return;
        }
        if (!registerpassword.isEmpty()){
            if (registerpassword1.length() < 6){
                registerpassword1.setError("Password must be at least 6 characters");
            }
        }
        mAuth.createUserWithEmailAndPassword(emailaddress, registerpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = new FirebaseUser(firstname, lastname, emailaddress);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterPage.this,"Register successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterPage.this,MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(RegisterPage.this, "Register Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterPage.this, "Register Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
