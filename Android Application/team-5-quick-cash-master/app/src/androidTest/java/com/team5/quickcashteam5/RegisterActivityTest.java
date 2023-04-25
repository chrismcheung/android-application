package com.team5.quickcashteam5;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

public class RegisterActivityTest {
    @Rule
    public ActivityScenarioRule<RegisterPage> activityScenarioRule =
            new ActivityScenarioRule<>(RegisterPage.class);
    public IntentsTestRule<RegisterPage> intentsTestRule =
            new IntentsTestRule<>(RegisterPage.class);

    /**
     * COMMON VALUES FOR TESTING
     */
    private final String FirstName = "Frederick";
    private final String LastName = "Hoekstra";
    private static final String Email = "testAdmin@teamfive.ca";
    private static final String Password = "HelloWorld123456";
    private final String InvalidEmail = "testAdminteam5.ca";
    private final String WeakPassword = "12345";
    private static FirebaseAuth mAuth;
    private static FirebaseDatabase db;

    /**
     * Initialize a proper environment for testing.
     */
    @BeforeClass
    public static void InitializeEnvironment() {
        Intents.init();

        // Setup database connection
        db = FirebaseDatabase.getInstance();

        // Look for any stray test account details.
        // If found, delete them.
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                authResult.getUser().delete();
            }
        });
    }

    /**
     * Cleanup testing environment.
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
    public void RegisterUser() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click())
                .perform(typeText(LastName), closeSoftKeyboard());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.registerpassword))
                .perform(click())
                .perform(typeText(Password), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        //Skip checking for Toast display. Verify change in activity instead.
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), RegisterPage.class);
        ActivityScenario scenario = activityScenarioRule.getScenario().launch(intent);
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void RegisterEmptyFirstName() {
        onView(withId(R.id.firstname))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.firstname))
                .perform(click())
                .check(matches(hasErrorText("Please enter your first name!")));
    }

    @Test
    public void RegisterEmptyLastName() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.lastname))
                .perform(click())
                .check(matches(hasErrorText("Please enter your last name!")));
    }

    @Test
    public void RegisterEmptyEmail() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click())
                .perform(typeText(LastName), closeSoftKeyboard());

        onView(withId(R.id.emailaddress))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .check(matches(hasErrorText("Please enter your email address!")));
    }

    @Test
    public void RegisterEmptyPassword() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click())
                .perform(typeText(LastName), closeSoftKeyboard());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.registerpassword))
                .perform(click(), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.registerpassword))
                .perform(click())
                .check(matches(hasErrorText("Please set your password!")));
    }

    @Test
    public void RegisterInvalidEmail() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click())
                .perform(typeText(LastName), closeSoftKeyboard());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .perform(typeText(InvalidEmail), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .check(matches(hasErrorText("This email is invalid!")));
    }

    @Test
    public void RegisterWeakPassword() {
        onView(withId(R.id.firstname))
                .perform(click())
                .perform(typeText(FirstName), closeSoftKeyboard());

        onView(withId(R.id.lastname))
                .perform(click())
                .perform(typeText(LastName), closeSoftKeyboard());

        onView(withId(R.id.emailaddress))
                .perform(click())
                .perform(typeText(Email), closeSoftKeyboard());

        onView(withId(R.id.registerpassword))
                .perform(click())
                .perform(typeText(WeakPassword), closeSoftKeyboard());

        onView(withId(R.id.registerbutton))
                .perform(click());

        onView(withId(R.id.registerpassword))
                .perform(click())
                .check(matches(hasErrorText("Password must be at least 6 characters")));
    }
}
