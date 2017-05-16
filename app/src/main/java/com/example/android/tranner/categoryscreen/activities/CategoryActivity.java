package com.example.android.tranner.categoryscreen.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.adapters.FragmentSlidingAdapter;
import com.example.android.tranner.categoryscreen.fragments.FragmentNew;
import com.example.android.tranner.categoryscreen.listeners.OnFamiliarFragmentListener;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;
import com.example.android.tranner.dagger.components.CategoryActivityComponent;
import com.example.android.tranner.dagger.components.DaggerCategoryActivityComponent;
import com.example.android.tranner.dagger.components.DaggerPreferenceComponent;
import com.example.android.tranner.data.ConstantKeys;
import com.example.android.tranner.data.providers.categorypreferences.PreferenceContract;
import com.example.android.tranner.data.providers.categorypreferences.PreferencePresenter;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.detailscreen.DetailActivity;
import com.example.android.tranner.mainscreen.themes.AppTheme;
import com.example.android.tranner.mainscreen.themes.ThemePreferences;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT_ID;

public class CategoryActivity extends AppCompatActivity implements
        OnFamiliarFragmentListener,
        OnNewFragmentListener,
        PreferenceContract.View {

    private static final String TAG = "CategoryActivity";
    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.category_dialog_toolbar)
    Toolbar mToolbar;
    @Inject
    PreferencePresenter mPreferencePresenter;
    private FragmentSlidingAdapter mAdapter;
    private CategoryActivityComponent mComponent;

    /**
     * Use this factory method to create a new instance of
     * this category_activity using the provided parameters.
     * @param context
     *
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

        //instantiate dependency injection component
        mComponent = DaggerCategoryActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build();

        //separate dependency injection component providing PreferencePresenter
        DaggerPreferenceComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build()
                .inject(this);

        mPreferencePresenter.attachView(this);

        //handle Category id passed from MainActivity
        //category instance is specific Category opened by the user
        if (getIntent().hasExtra(CATEGORY_INTENT_ID)) {
            int parentId = getIntent().getIntExtra(CATEGORY_INTENT_ID, -1);

            mPreferencePresenter.saveParentId(parentId);
        }

        //retrieve parent id from shared preferences so that parent Category could be loaded thereafter
        mPreferencePresenter.retrieveParentId();
        mPreferencePresenter.retrieveAppTheme();
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
     * listener implemented by {@link FragmentNew}. It sends callbacks when usere clicks on subcategory item.
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
        mPreferencePresenter.detachView();
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

    @Override
    public void onParentIdSaved() {

    }

    @Override
    public void onParentIdSaveError() {

    }

    @Override
    public void onParentIdRetrieved(int parentId) {
        mPreferencePresenter.loadParentCategory(parentId);
    }

    @Override
    public void onNoParentIdRetrieved() {

    }

    @Override
    public void onParentIdRetrieveError() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(getResources().getString(R.string.error))
                .show();
    }

    @Override
    public void onAppThemeRetrieved(String themeName) {
        setTheme(AppTheme.withName(themeName).resId());
    }

    @Override
    public void onAppThemeRetrieveError() {
        Toast.makeText(this, "App theme retrieve error.", Toast.LENGTH_SHORT).show();
    }
}
