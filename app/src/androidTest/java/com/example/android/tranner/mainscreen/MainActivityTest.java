package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.mainscreen.themes.AppTheme;
import com.example.android.tranner.mainscreen.themes.ThemePreferences;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
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
    private static final String EMPTY_TEXT = "";
    private static final String IMAGE_QUERY = "flowers";

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

        onView(withText(R.string.nav_other)).perform(click());
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
    public void shouldAddCustomCategory() {
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
    public void shouldOpenCategoryActivity() {
        onView(withId(R.id.main_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(CategoryActivity.class.getName()));
    }

    @Test
    public void shouldLaunchCategoryActivityWithTitle() {
        Matcher<RecyclerView.ViewHolder> matcher = MyRecyclerView.withTitle(suggestedArray[0]);

        onView((withId(R.id.main_recycler_view))).perform(scrollToHolder(matcher), actionOnHolderItem(matcher, click()));

        onView(withId(R.id.category_dialog_toolbar)).check(matches(isDisplayed()));

        onView(withText(suggestedArray[0])).check(matches(withParent(withId(R.id.category_dialog_toolbar))));
    }

    @Test
    public void shouldShowPopupMenu() {
        onView(withId(R.id.main_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerView.clickChildViewWithId(R.id.main_item_button)));

        onView(withText(R.string.main_item_delete)).check(matches(isDisplayed()));

        onView(withText(R.string.main_item_backdrop)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldOpenWebImageDialog() {
        onView(withId(R.id.main_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerView.clickChildViewWithId(R.id.main_item_button)));

        onView(withText(R.string.main_item_backdrop)).perform(click());

        onView(withText(R.string.web_title)).check(matches(isDisplayed()));
        onView(withText(R.string.web_image_search)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldCancelWebImageDialog() {
        onView(withId(R.id.main_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerView.clickChildViewWithId(R.id.main_item_button)));

        onView(withText(R.string.main_item_backdrop)).perform(click());

        onView(withText(R.string.cancel)).perform(click());
    }

    @Test
    public void shouldPickImage() {
        onView(withId(R.id.main_recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerView.clickChildViewWithId(R.id.main_item_button)));

        onView(withText(R.string.main_item_backdrop)).perform(click());

        typeTextIntoFocusedView(IMAGE_QUERY);

        onView(withText(R.string.web_image_search)).perform(click());

        onView(withId(R.id.web_dialog_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyRecyclerView.clickChildViewWithId(R.id.web_dialog_image)));

    }
}