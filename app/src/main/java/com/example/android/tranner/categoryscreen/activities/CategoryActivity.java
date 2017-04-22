package com.example.android.tranner.categoryscreen.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.adapters.FragmentAdapter;
import com.example.android.tranner.data.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Category mCategory;
    private FragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        mCategory = (Category) bundle.get(CATEGORY_INTENT);
        this.setTitle(mCategory.getCategory());

        mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);

        mSlidingTabs.setupWithViewPager(mViewpager);

    }

}
