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
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.adapters.FragmentSlidingAdapter;
import com.example.android.tranner.categoryscreen.fragments.FragmentFamiliar;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.dagger.components.DaggerItemPresenterComponent;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.data.providers.itemprovider.ItemContract;
import com.example.android.tranner.data.providers.itemprovider.ItemPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;

public class CategoryActivity extends AppCompatActivity implements
        ItemContract.View,
        FragmentFamiliar.OnFamiliarFragmentListener,
        FragmentNew.OnNewFragmentListener {

    private static final String TAG = "CategoryActivity";

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    ItemPresenter mPresenter;
    private Category mCategory;
    private FragmentSlidingAdapter mAdapter;
    private FragmentNew mFragmentNew;
    private FragmentFamiliar mFragmentFamiliar;
    private TrannerApp mTrannerApp;
    private List<CategoryItem> mItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragmentNew = FragmentNew.newInstance();
        mFragmentFamiliar = FragmentFamiliar.newInstance();

        //handle Category instance passed from MainActivity
        //category instance is specific Category opened by the user
        Intent intent = getIntent();
        if (intent.hasExtra(CATEGORY_INTENT)) {
            Bundle bundle = intent.getExtras();
            mCategory = (Category) bundle.get(CATEGORY_INTENT);
            if (mCategory != null) {
                this.setTitle(mCategory.getCategory());
            }
        } else {
            Log.d(TAG, "onCreate: Category instance hasn't been passed");
        }

        //handle ItemPresenter provided by dependency injection
        mTrannerApp = (TrannerApp) getApplication();
        DaggerItemPresenterComponent.builder()
                .appComponent(mTrannerApp.getComponent())
                .build()
                .inject(this);

        //read from database
        mItemList = new ArrayList<>();
        mPresenter.loadItems(mCategory);
        mPresenter.setView(this);

        mAdapter = new FragmentSlidingAdapter(this, getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mSlidingTabs.setupWithViewPager(mViewpager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
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
    public void onFamiliarItemAdded() {

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

    @Override
    public void onItemLoaded(List<CategoryItem> categoryList) {
        mItemList.clear();
        mItemList.addAll(categoryList);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Items loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemLoaded() {
        Toast.makeText(this, "No item loaded...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLoadError() {
        Toast.makeText(this, "Item load error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAdded() {
        mPresenter.loadItems(mCategory);
        Toast.makeText(this, "Item successfully added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemAdded() {
        Toast.makeText(this, "No item added...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAddedError() {
        Toast.makeText(this, "Item add error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDeleted() {
        mPresenter.loadItems(mCategory);
        Toast.makeText(this, "Item deleted...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemDeleted() {
        Toast.makeText(this, "No item deleted...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDeletedError() {
        Toast.makeText(this, "Item deletion error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemUpdated() {
        mPresenter.loadItems(mCategory);
        Toast.makeText(this, "Item successfully updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemUpdated() {
        Toast.makeText(this, "No item updated...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemUpdatedError() {
        Toast.makeText(this, "Item update error...", Toast.LENGTH_SHORT).show();
    }
}
