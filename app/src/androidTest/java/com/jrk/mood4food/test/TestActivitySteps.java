package com.jrk.mood4food.test;

import android.content.Intent;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.jrk.mood4food.R;
import com.jrk.mood4food.TestActivity;

import org.junit.Rule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

public class TestActivitySteps {

    @Rule
    private ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    private TestActivity activity;

    @Before("@test")
    public void setUp() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@test")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on test screen$")
    public void I_am_on_test_screen() {
        assertNotNull(activity);
    }

    @When("^I click the button$")
    public void I_click_button() {
        onView(withId(R.id.btnClickMe)).perform(click());
    }

    @Then("^I should see a text$")
    public void I_see_text() {
        onView(withId(R.id.tvClicked)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
