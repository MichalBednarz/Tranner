package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.mainscreen.themes.AppTheme;
import com.example.android.tranner.mainscreen.themes.ThemePreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Michał on 2017-05-09.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String PASTEL = "Pastel";
    private static final String YELLOW = "Yellow";
    private static final String SOFTY = "Softy";
    private static final String CUSTOM_NAME = "Category";
    private static final String EMPTY_NAME = "";

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<MainActivity>(
            MainActivity.class);

    private ThemePreferences themePreferences;
    private String[] suggestedArray;

    @Before
    public void setUp() {
        Context context = mActivityRule.getActivity();
        themePreferences = new ThemePreferences(context);
        suggestedArray = context.getResources().getStringArray(R.array.category_suggested);
    }

    @Test
    public void drawerHeadersNotClickable() {
        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());

        onView(withText(R.string.nav_themes)).perform(click());

        onView(withText(R.string.nav_menu)).perform(click());
    }

    @Test
    public void shouldApplyThemePastel() {
        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());

        onView(withText(PASTEL)).perform(click());

        assertEquals(themePreferences.getSelectedTheme(), AppTheme.PASTEL);
    }

    @Test
    public void shouldApplyThemeYellow() {
        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());

        onView(withText(YELLOW)).perform(click());

        assertEquals(themePreferences.getSelectedTheme(), AppTheme.YELLOW);
    }

    @Test
    public void shouldApplyThemeSofty() {
        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());

        onView(withText(SOFTY)).perform(click());

        assertEquals(themePreferences.getSelectedTheme(), AppTheme.SOFT);
    }

    @Test
    public void shouldClickOnDrawerAboutUs() {
        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());

        onView(withText(R.string.nav_about_us)).perform(click());
    }

    @Test
    public void shouldClickOnFabOpenDialog() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_title)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldPickCategoryFromList() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_choose)).perform(click());

        //TODO check if default added
    }

    @Test
    public void shouldCategoryDialogClickCustom() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_custom)).perform(click());

        onView(withText(R.string.category_custom_title)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldCancelCustomDialog() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_custom)).perform(click());

        onView(withText(R.string.cancel)).perform(click());
    }

    @Test
    public void shouldTypeCustomCategory() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_custom)).perform(click());

        typeTextIntoFocusedView(CUSTOM_NAME);

        onView(withText(R.string.dialog_ok)).perform(click());
    }

    @Test
    public void shouldCustomEditTextHasFocus() {
        onView(withId(R.id.activity_main_fab)).perform(click());

        onView(withText(R.string.category_custom)).perform(click());

        onView(withHint(R.string.category_custom_hint)).check(matches(hasFocus()));
    }

    @Test
    public void shouldOpenCategoryActivityAndReturn() {
        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(CategoryActivity.class.getName()));
    }
}