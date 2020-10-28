package com.jrk.mood4food.test;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.EditText;

import com.jrk.mood4food.LoginActivity;
import com.jrk.mood4food.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.internal.matchers.TypeSafeMatcher;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.not;

public class LoginActivitySteps {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private Activity activity;

    @Before("@login-feature")
    public void setup() {
        activityTestRule.launchActivity(new Intent());
        activity = activityTestRule.getActivity();
    }

    @After("@login-feature")
    public void tearDown() {
        activityTestRule.finishActivity();
    }

    @Given("^I am on login screen")
    public void I_am_on_login_screen() {
        assertNotNull(activity);
    }

    @When("^I input email (\\S+)$")
    public void I_input_email(final String email) {
        onView(withId(R.id.email)).perform(typeText(email));
    }

    @When("^I input password \"(.*?)\"$")
    public void I_input_password(final String password) {
        onView(withId(R.id.password)).perform(typeText(password), closeSoftKeyboard());
    }

    @When("^I press submit button$")
    public void I_press_submit_button() {
        onView(withId(R.id.submit)).perform(click());
    }

    @When("^I tap sign up button$")
    public void I_tap_sign_up_button() {
        onView(withId(R.id.signup)).perform(click());
    }

    @Then("^I should see error on the (\\S+)$")
    public void I_should_see_error_on_the_editTextView(final String viewName) {
        int viewId = (viewName.equals("email")) ? R.id.email : R.id.password;
        int messageId = (viewName.equals("email")) ? R.string.msg_email_error : R.string.msg_password_error;

        onView(withId(viewId)).check(matches(hasErrorText(activity.getString(messageId))));
    }

    @Then("^I should (true|false) auth error$")
    public void I_should_see_auth_error(boolean shouldSeeError) {
        if (shouldSeeError) {
            onView(withId(R.id.error)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.error)).check(matches(not(isDisplayed())));
        }
    }

    @Then("^I should see sign up screen$")
    public void I_should_see_sign_up_screen() {
        onView(withId(R.id.page_title)).check(matches(withText(R.string.signup)));
    }

    private static Matcher<? super View> hasErrorText(String expectedError) {
        return new ErrorTextMatcher(expectedError);
    }

    /**
     * Custom matcher to assert equal EditText.setError();
     */
    private static class ErrorTextMatcher extends TypeSafeMatcher<View> {

        private final String mExpectedError;

        private ErrorTextMatcher(String expectedError) {
            mExpectedError = expectedError;
        }

        @Override
        public boolean matchesSafely(View view) {
            if (!(view instanceof EditText)) {
                return false;
            }

            EditText editText = (EditText) view;

            return mExpectedError.equals(editText.getError().toString());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with error: " + mExpectedError);
        }
    }
}
