package com.example.android.tranner;

import android.content.ComponentName;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.android.tranner.categoryscreen.CategoryActivity;
import com.example.android.tranner.mainscreen.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * Created by Michał on 2017-04-09.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void fabShouldOpenDialog() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);
        assertNotNull(fab);
        fab.performClick();
        //TODO assert that fab opens category dialog
        //assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Lala") );
    }

    @Test
    public void shouldDeleteItem() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MenuItem menuItem = new RoboMenuItem(R.id.item_delete);
        activity.onOptionsItemSelected(menuItem);
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

    }

    @Test
    public void shouldOpenWebImageDialog() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        MenuItem menuItem = new RoboMenuItem(R.id.item_backdrop);
        activity.onOptionsItemSelected(menuItem);

    }

    @Test
    public void shouldStartCategoryActivity() {
        RecyclerView recycler = (RecyclerView) activity.findViewById(R.id.main_recycler_view);
        // workaround robolectric recyclerView issue
        recycler.measure(0, 0);
        recycler.layout(0, 0, 100, 1000);

        // lets just imply a certain item at position 0 for simplicity
        recycler.findViewHolderForAdapterPosition(0).itemView.performClick();

        // presenter is injected in my case, how this verification happens depends on how you manage your dependencies.
        //verify(fragment.presenter).onEntryClicked(MyNavigationEntry.XYZ);
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
        ShadowActivity shadow = Shadows.shadowOf(activity);

        assertThat(shadow.peekNextStartedActivityForResult().intent.getComponent(), equalTo(new ComponentName(activity, CategoryActivity.class)));
    }

}