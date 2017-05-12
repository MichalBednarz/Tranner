package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Michał on 2017-05-09.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(
            MainActivity.class);

    @Test
    public void shouldOnCategoryClickOpenCategoryActivity() {
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(CategoryActivity.class.getName()));
    }



}