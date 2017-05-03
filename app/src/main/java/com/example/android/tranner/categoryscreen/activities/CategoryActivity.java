package com.example.android.tranner.categoryscreen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.adapters.FragmentSlidingAdapter;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.categoryscreen.listeners.OnFamiliarFragmentListener;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;
import com.example.android.tranner.dagger.components.CategoryActivityComponent;
import com.example.android.tranner.dagger.components.DaggerCategoryActivityComponent;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;

public class CategoryActivity extends AppCompatActivity implements
        OnFamiliarFragmentListener,
        OnNewFragmentListener {

    private static final String TAG = "CategoryActivity";

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Category mParentCategory;
    private FragmentSlidingAdapter mAdapter;
    private CategoryActivityComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //handle Category instance passed from MainActivity
        //category instance is specific Category opened by the user
        Intent intent = getIntent();
        if (intent.hasExtra(CATEGORY_INTENT)) {
            Bundle bundle = intent.getExtras();
            mParentCategory = (Category) bundle.get(CATEGORY_INTENT);
            if (mParentCategory != null) {
                this.setTitle(mParentCategory.getCategory());
            }
        } else {
            Log.d(TAG, "onCreate: Category instance hasn't been passed");
        }

        //instantiate dependency injection component
        mComponent = DaggerCategoryActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build();


        mAdapter = new FragmentSlidingAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mSlidingTabs.setupWithViewPager(mViewpager);

    }

    public CategoryActivityComponent getComponent() {
        return mComponent;
    }

    public Category getParentCategory() {
        return mParentCategory;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFamiliarItemAdded(String name) {
    }

    @Override
    public void onFamiliarItemOpened() {

    }

    /**
     * This method is component of {@link OnNewFragmentListener}
     * listener implemented by {@link FragmentNew}. It sends callbacks when usere clicks on subcategory item.
     */
    @Override
    public void onNewItemOpened(CategoryItem item) {

    }

}
