package com.example.android.tranner.categoryscreen.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Michał on 2017-05-09.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {

    private static final int NEW_POSITION = 0;
    private static final int FAMILIAR_POSITION = 1;

    @Rule
    public ActivityTestRule<CategoryActivity> mActivityRule = new ActivityTestRule<CategoryActivity>(
            CategoryActivity.class);

    @Test
    public void shouldOpenDialogOnFab() {
        onView(withId(R.id.fragment_new_fab)).perform(click());

        onView(withText(R.string.add_new)).check(matches(isDisplayed()));
    }

}