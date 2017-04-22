package com.example.android.tranner;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.android.tranner.categoryscreen.CategoryActivity;
import com.example.android.tranner.data.Category;
import com.example.android.tranner.mainscreen.MainActivity;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by Michał on 2017-04-09.
 */
@RunWith(CustomTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity activity;
    private List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());
    private MainActivityAdapter mainActivityAdapter;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        mainActivityAdapter = new MainActivityAdapter(activity, categoryList);
    }
    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldStartCategoryActivity() {
        RecyclerView recycler = (RecyclerView) activity.findViewById(R.id.main_recycler_view);

      /*  recycler.measure(0, 0);
        recycler.layout(0, 0, 100, 1000);*/
        recycler.setLayoutManager(new LinearLayoutManager(activity));
        recycler.setAdapter(mainActivityAdapter);


        MainActivityAdapter adapter = (MainActivityAdapter) recycler.getAdapter();
        Assertions.assertThat(adapter.getItemCount()).isEqualTo(categoryList.size());


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recycler.findViewHolderForAdapterPosition(0).itemView.performClick();

            }
        }, 500);

    }

}