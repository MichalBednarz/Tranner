package com.example.android.tranner.mainscreen;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-05-16.
 */

public class MyRecyclerView {
    public static Matcher<RecyclerView.ViewHolder> withTitle(final String title)
    {
        return new BoundedMatcher<RecyclerView.ViewHolder, MainActivityAdapter.ViewHolder>(MainActivityAdapter.ViewHolder.class)
        {
            @Override
            protected boolean matchesSafely(MainActivityAdapter.ViewHolder item) {
                TextView itemTitle = ButterKnife.findById(item.itemView, R.id.main_item_text);

                return itemTitle.getText().toString().equalsIgnoreCase(title);
            }

            @Override
            public void describeTo(Description description)
            {
                description.appendText("view holder with title: " + title);
            }
        };
    }

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
