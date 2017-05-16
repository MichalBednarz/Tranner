package com.example.android.tranner.categoryscreen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.adapters.FragmentSlidingAdapter;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.categoryscreen.listeners.OnFamiliarFragmentListener;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;
import com.example.android.tranner.dagger.components.CategoryActivityComponent;
import com.example.android.tranner.dagger.components.DaggerCategoryActivityComponent;
import com.example.android.tranner.data.ConstantKeys;
import com.example.android.tranner.data.providers.categoryparent.ParentCategoryContract;
import com.example.android.tranner.data.providers.categoryparent.ParentCategoryPresenter;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.detailscreen.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT_ID;

public class CategoryActivity extends AppCompatActivity implements
        OnFamiliarFragmentListener,
        OnNewFragmentListener,
        ParentCategoryContract.View {

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.category_dialog_toolbar)
    Toolbar mToolbar;
    @Inject
    ParentCategoryPresenter mParentCategoryPresenter;
    private FragmentSlidingAdapter mAdapter;
    private CategoryActivityComponent mComponent;

    /**
     * Use this factory method to create a new instance of
     * this category_activity using the provided parameters.
     *
     * @param context
     * @param category
     * @return
     */
    public static Intent getStartIntent(Context context, Category category) {
        Intent startIntent = new Intent(context, CategoryActivity.class);
        startIntent.putExtra(ConstantKeys.CATEGORY_INTENT_ID, category.getId());

        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mComponent = DaggerCategoryActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build();

        mComponent.inject(this);

        mParentCategoryPresenter.attachView(this);

        if (getIntent().hasExtra(CATEGORY_INTENT_ID)) {
            int parentId = getIntent().getIntExtra(CATEGORY_INTENT_ID, -1);

            mParentCategoryPresenter.loadParentCategory(parentId);
        }
    }

    public CategoryActivityComponent getComponent() {
        return mComponent;
    }

    @Override
    public void onFamiliarItemOpened(CategoryItem item) {
        Intent startIntent = DetailActivity.getStartIntent(this, item.getId());
        startActivity(startIntent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    /**
     * This method is component of {@link OnNewFragmentListener}
     * listener implemented by {@link FragmentNew}. It sends callbacks when user clicks on subcategory item.
     */
    @Override
    public void onNewItemOpened(CategoryItem item) {
        Intent startIntent = DetailActivity.getStartIntent(this, item.getId());
        startActivity(startIntent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mParentCategoryPresenter.detachView();
    }

    /**
     * CategoryActivity Presenter methods responsible for retrieval of
     * parent category stored in shared preferences.
     */

    @Override
    public void onParentCategoryLoaded(Category parentCategory) {
        setTitle(parentCategory.getTitle());
        mAdapter = new FragmentSlidingAdapter(getSupportFragmentManager(), parentCategory.getId());
        mViewpager.setAdapter(mAdapter);
        mSlidingTabs.setupWithViewPager(mViewpager);
    }

    @Override
    public void onParentCategoryLoadError() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getResources().getString(R.string.error))
                .show();
    }
}
