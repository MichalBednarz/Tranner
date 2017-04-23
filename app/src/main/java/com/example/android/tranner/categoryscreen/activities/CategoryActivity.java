package com.example.android.tranner.categoryscreen.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.adapters.FragmentSlidingAdapter;
import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.data.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;

public class CategoryActivity extends AppCompatActivity implements
        FragmentFamiliar.OnFamiliarFragmentListener,
        FragmentNew.OnNewFragmentListener {

    public static final String TAG = CategoryActivity.class.getSimpleName();

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Category mCategory;
    private FragmentSlidingAdapter mAdapter;
    private FragmentNew mFragmentNew;
    private FragmentFamiliar mFragmentFamiliar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentNew = FragmentNew.newInstance();
        mFragmentFamiliar = FragmentFamiliar.newInstance();

        Intent intent = getIntent();

        if (intent.hasExtra(CATEGORY_INTENT)) {
            Bundle bundle = getIntent().getExtras();
            try {
                mCategory = (Category) bundle.get(CATEGORY_INTENT);
                this.setTitle(mCategory.getCategory());
            } catch (NullPointerException e) {

                Log.d(TAG, "onCreate: null bundle key");
            }
        } else {
            Log.d(TAG, "onCreate: null bundle");
        }

        mAdapter = new FragmentSlidingAdapter(this, getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);

        mSlidingTabs.setupWithViewPager(mViewpager);


    }

    public void onFabClicked(View v) {
        switch (v.getId()) {
            case R.id.fragment_new_fab:
                mFragmentNew.onFabClicked(v);
                break;
            case R.id.fragment_familiar_fab:
                mFragmentFamiliar.onFabClicked(v);
                break;
        }
    }

    public FragmentNew getFragmentNew() {
        return mFragmentNew;
    }

    public FragmentFamiliar getFragmentFamiliar() {
        return mFragmentFamiliar;
    }


    @Override
    public void onFamiliarItemAdded(Uri uri) {

    }

    @Override
    public void onFamiliarItemOpened() {

    }

    @Override
    public void onNewItemAdded() {

    }

    @Override
    public void onNewItemOpened() {

    }
}
