package com.team5.quickcashteam5;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;

import javax.xml.datatype.Duration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class PostFragmentTest {

    @Rule
    public ActivityScenarioRule<JobPostLanding> activityScenarioRule =
            new ActivityScenarioRule<>(JobPostLanding.class);

    /**
     * COMMON VALUES FOR TESTING
     */
    private final String Title = "This is a title";
    private final String Description = "This is a description";
    private final String Location = "This is a location";
    private final String Duration = "12";
    private final String Payment = "13";
    private static final String Email = "abcxyz@gmail.com";
    private static final String Password = "HelloWorld123";
    private static FirebaseAuth mAuth;
    private static DatabaseReference ref;
    private static long dateTime;

    /**
     * Initialize a proper environment for testing.
     */
    @BeforeClass
    public static void InitializeEnvironment() {
        // Setup database connection
        ref = FirebaseDatabase.getInstance().getReference();

        // Log into test account
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(Email, Password);
    }


    @Test
    public void uploadJobTest(){
        onView(withId(R.id.titleOfTask))
                .perform(click())
                .perform(typeText(Title), closeSoftKeyboard());

        onView(withId(R.id.descriptionOfTask))
                .perform(click())
                .perform(typeText(Description), closeSoftKeyboard());

        onView(withId(R.id.locationOfTask))
                .perform(click())
                .perform(typeText(Location), closeSoftKeyboard());

        onView(withId(R.id.durationOfTask))
                .perform(click())
                .perform(typeText(Duration), closeSoftKeyboard());

        onView(withId(R.id.paymentOfTask))
                .perform(click())
                .perform(typeText(Payment), closeSoftKeyboard());

        //commenting out the actual submission because CleanEnvironment doesn't properly delete the test job
        /*onView(withId(R.id.submitTask))
                .perform(click());

        dateTime = new Date().getTime();*/
    }

    /**
     * Cleanup testing environment.
     */
    @AfterClass
    public static void CleanEnvironment() {
        mAuth.signOut();

        /*ref.child("Jobs")
                .child(Long.toString(dateTime) + mAuth.getCurrentUser().getUid())
                .removeValue();*/
    }



}
