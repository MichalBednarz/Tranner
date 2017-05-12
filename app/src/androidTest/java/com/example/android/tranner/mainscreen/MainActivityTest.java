package com.example.android.tranner.mainscreen;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Michał on 2017-05-08.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void shouldOpenMenu() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(withText(R.string.action_theme_one)).check(matches(isDisplayed()));
        onView(withText(R.string.action_theme_two)).check(matches(isDisplayed()));
        onView(withText(R.string.action_theme_three)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldClickOnMenuActionOne() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(withText(R.string.action_theme_one)).perform(click());
    }

    @Test
    public void shouldClickOnMenuActionTwo() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(withText(R.string.action_theme_two)).perform(click());
    }

    @Test
    public void shouldClickOnMenuActionThree() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        onView(withText(R.string.action_theme_three)).perform(click());
    }

    @Test
    public void shouldClickOnCategory() {
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void shouldOpenCloseCategoryDialog() {

    }

    @Test
    public void shouldClickOnCategoryButton() {
        //onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.scrollToPosition(0));
        //onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.scrollToHolder(
               // withHolderTimeView("text")));
    }


    public static Matcher<RecyclerView.ViewHolder> withHolderTimeView(final String text) {
        return new BoundedMatcher<RecyclerView.ViewHolder, MainActivityAdapter.ViewHolder>(MainActivityAdapter.ViewHolder.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + text);
            }

            @Override
            protected boolean matchesSafely(MainActivityAdapter.ViewHolder item) {
                ImageButton imageButton = (ImageButton) item.itemView.findViewById(R.id.main_item_button);
                if (imageButton == null) {
                    return false;
                }
                return imageButton.performClick();
            }
        };
    }


}