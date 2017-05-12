package com.example.android.tranner.categoryscreen.activities;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Michał on 2017-05-09.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {
    @Rule
    public IntentsTestRule<CategoryActivity> mActivityRule = new IntentsTestRule<CategoryActivity>(
            CategoryActivity.class, true, false);

    @Test
    public void shouldIntentPassExtras() {
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, CategoryActivity.class);
        intent.putExtra("Name", "Value");

        mActivityRule.launchActivity(intent);
    }

}