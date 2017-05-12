package com.example.android.tranner.detailscreen;

import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Michał on 2017-05-09.
 */
@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    public static final int ITEM_ID = 1;
    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule = new ActivityTestRule<>(
            DetailActivity.class);
    private SystemAnimations mSystemAnimations;

    @Before
    public void setUp() throws Exception {
        mSystemAnimations = new SystemAnimations(getInstrumentation().getContext());
        mSystemAnimations.disableAll();
    }

    @After
    public void tearDown() throws Exception {
        mSystemAnimations.enableAll();
    }

    @Test
    public void shouldOpenMenuOnFab() throws InterruptedException {
        onView(withParent(withId(R.id.detail_category_fab_menu)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withChild(withChild(withId(R.id.material_design_floating_action_menu_item3))))
                .check(matches(isDisplayed()));

    }


    @Test
    public void shouldClickOnMenuOnFabEdit() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withId(R.id.material_design_floating_action_menu_item1)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldClickOnMenuOnFabImage() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withId(R.id.material_design_floating_action_menu_item2)).perform(click());
    }

    @Test
    public void shouldClickOnMenuOnFabAlbum() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withId(R.id.material_design_floating_action_menu_item3)).perform(click());
    }

   /* @Test
    public void shouldDescriptionFocusable() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withText(R.string.detail_fab_menu_edit)).perform(click());

        onView(withId(R.id.activity_detail_textview)).check(matches(isFocusable()));
    }

    @Test
    public void shouldDescriptionFocus() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withText(R.string.detail_fab_menu_edit)).perform(click());

        onView(withId(R.id.activity_detail_textview)).check(matches(hasFocus()));
    }

    @Test
    public void shouldTypeDescription() {
        onView(withId(R.id.detail_category_fab_menu)).perform(click());

        onView(withText(R.string.detail_fab_menu_edit)).perform(click());

        onView(withId(R.id.activity_detail_textview))
                .perform(typeText("description"), closeSoftKeyboard());
    }*/
}