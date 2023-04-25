package com.team5.quickcashteam5;

import android.content.Intent;
import android.provider.Telephony;

import androidx.annotation.ContentView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.espresso.intent.Intents;

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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LandingPageTest {

    @Rule
    public ActivityScenarioRule<LandingPage> activityScenarioRule =
            new ActivityScenarioRule<>(LandingPage.class);
    public IntentsTestRule<LandingPage> intentsTestRule
            = new IntentsTestRule<>(LandingPage.class);

    /**
     * Initialize common methods for testing.
     */
    @BeforeClass
    public static void InitializeCommon() {
        Intents.init();
    }

    /**
     * Cleanup common methods for testing.
     */
    @AfterClass
    public static void CleanupCommon() {
        Intents.release();
    }

    @Test
    public void testJobSearchButton() {
        onView(withId(R.id.jobSearchNav))
                .perform(click());

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LandingPage.class);
        ActivityScenario scenario = activityScenarioRule.getScenario().launch(intent);
        intended(hasComponent(JobSearchLanding.class.getName()));

    }

    @Test
    public void testJobPostButton(){
        onView(withId(R.id.jobPostLNav))
                .perform(click());

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), LandingPage.class);
        ActivityScenario scenario = activityScenarioRule.getScenario().launch(intent);
        intended(hasComponent(JobPostLanding.class.getName()));
    }
}
