package com.team5.quickcashteam5;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    /**
     * COMMON VALUES FOR TESTING
     */
    private final String FirstName = "Frederick";
    private final String LastName = "Hoekstra";
    private static final String Email = "testAdmin@teamfive.ca";
    private final String InvalidEmail = "testAdminteam5.ca";
    private static final String Password = "HelloWorld123456";
    private final String InvalidPassword = "HelloWorld";
    private static FirebaseDatabase db;
    private static FirebaseAuth mAuth;

    /**
     * Initialize common methods for testing.
     */
    @BeforeClass
    public static void InitializeEnvironment() {
        Intents.init();

        // Setup database connection
        db = FirebaseDatabase.getInstance();

        // Create a new testing account.
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(Email, Password);
    }

    /**
     * Cleanup common methods for testing.
     */
    @AfterClass
    public static void CleanEnvironment() {
        Intents.release();

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        com.google.firebase.auth.FirebaseUser AuthUser = authResult.getUser();

                        db.getReference("Users").child(AuthUser.getUid()).setValue(null);
                        AuthUser.delete();
                    }
                });
    }

    @Test
    public void LoginValidUser() {
        onView(withId(R.id.email))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(click())
                .perform(typeText(Password), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(click());

        //Skip checking for Toast display. Verify change in activity instead.
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        ActivityScenario scenario = activityScenarioRule.getScenario().launch(intent);
        intended(hasComponent(LandingPage.class.getName()));
    }

    @Test
    public void LoginInvalidUser() {
        onView(withId(R.id.email))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(click())
                .perform(typeText(InvalidPassword), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(click());

        // Check for element within the MainActivity as the application can't detect if the activity
        // doesn't change and testing for Toast displays are not natively supported by Espresso.
        onView(withId(R.id.password))
                .check(matches(isDisplayed()));
    }

    @Test
    public void LoginEmptyPassword() {
        onView(withId(R.id.email))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.password))
                .perform(click())
                .check(matches(hasErrorText("Please enter the password!")));
    }

    @Test
    public void LoginEmptyEmail() {
        onView(withId(R.id.email))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.email))
                .perform(click())
                .check(matches(hasErrorText("Please enter your email address!")));
    }

    @Test
    public void LoginInvalidEmail() {
        onView(withId(R.id.email))
                .perform(click())
                .perform(typeText(InvalidEmail), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(click())
                .perform(typeText(Password), closeSoftKeyboard());

        onView(withId(R.id.button))
                .perform(click());

        onView(withId(R.id.email))
                .perform(click())
                .check(matches(hasErrorText("This email is invalid!")));
    }
}
