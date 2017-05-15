package com.example.android.tranner.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.components.DaggerMainActivityComponent;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.categoryprovider.CategoryContract;
import com.example.android.tranner.data.providers.categoryprovider.CategoryPresenter;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.mainscreen.dialogs.CategoryDialog;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;
import com.example.android.tranner.mainscreen.themes.AppCompatThemedActivity;
import com.example.android.tranner.mainscreen.themes.AppTheme;
import com.example.android.tranner.mainscreen.themes.AttributeExtractor;
import com.example.android.tranner.mainscreen.themes.OnThemeSelectedListener;
import com.example.android.tranner.mainscreen.themes.ThemePickerAdapter;
import com.example.android.tranner.mainscreen.themes.ThemePreferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.tranner.data.ConstantKeys.HEADER_URL;
import static com.example.android.tranner.data.ConstantKeys.WEB_DIALOG_TAG;

public class MainActivity extends AppCompatThemedActivity implements CategoryDialogListener,
        OnThemeSelectedListener,
        MainActivityAdapterListener,
        WebImageDialogAdapterListener,
        CategoryContract.View {

    private static final String TAG = "MainActivity";


    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.recyclerview_inside_nav)
    RecyclerView mThemeRecyclerView;
    @Inject
    CategoryPresenter mPresenter;
    private List<Category> mCategoryList;
    private ImageView mNavHeaderImage;
    private MainActivityAdapter mAdapter;
    private CategoryDialog mCategoryDialog;
    private WebImageDialog mWebDialog;
    private View mNavigationHeader;
    private ThemePreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = new ThemePreferences(this);
        applyPreviouslySelectedTheme();
        setContentView(R.layout.activity_drawer_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        //Dependency injection providing CategoryPresenter
        DaggerMainActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build()
                .inject(this);

        mPresenter.attachView(this);

        mCategoryList = new ArrayList<>();
        mPresenter.loadCategories();

        setUpCategoryRecyclerView();

        mNavigationHeader = mNavigationView.getHeaderView(0);
        mNavHeaderImage = (ImageView) mNavigationHeader.findViewById(R.id.nav_header_backdrop);

        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
        // initializing recycler view inside navigation view
        setUpThemeRecyclerView();
    }

    /**
     * Handle theme change incurred by the user in navigation view.
     * @param theme
     */
    @Override
    public void onThemeSelected(AppTheme theme) {
        applyTheme(theme);
    }

    /**
     * Retrieve from shared preferences theme selected lately by the user
     * and apply it to the layout.
     */
    private void applyPreviouslySelectedTheme() {
        AppTheme theme = mPreferences.getSelectedTheme();
        setTheme(theme.resId());
    }

    /**
     * Provide navigation view with multiple theme options displayed with help of recycler view.
     */
    private void setUpThemeRecyclerView() {
        AttributeExtractor extractor = new AttributeExtractor();
        mThemeRecyclerView.setAdapter(new ThemePickerAdapter(extractor, this));
        mThemeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCategoryRecyclerView() {
        mAdapter = new MainActivityAdapter(this, mCategoryList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Load navigation menu header information.
     */
    private void loadNavHeader() {
        Picasso.with(this).load(HEADER_URL)
                .into(mNavHeaderImage);
    }

    /**
     * Provide all navigation view elements functionality.
     */
    private void setUpNavigationView() {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                        super.onDrawerOpened(drawerView);
                    }
                };

        mDrawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    /**
     * Method that handles click on floating action button.
     * This method gets new instance of CategoryDialog class
     * and displays it.
     */
    @OnClick(R.id.fab)
    public void showCategoryDialog() {
        FragmentManager manager = getSupportFragmentManager();
        mCategoryDialog = CategoryDialog.newInstance();
        mCategoryDialog.show(manager, "category_dialog");
    }

    @Override
    public void onChangeBackdropClicked(Category category, int position) {
        mWebDialog = WebImageDialog.newInstance(category);

        mWebDialog.show(getSupportFragmentManager(), WEB_DIALOG_TAG);
    }

    @Override
    public void onCategoryOpened(Category category) {
        Intent startIntent = CategoryActivity.getStartIntent(this, category);
        startActivity(startIntent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onCategoryDeleted(Category category, int position) {
        mPresenter.deleteCategory(category);
        mPresenter.loadCategories();

        Toast.makeText(this, "Category " + category.getTitle() + " proceeded to deletion.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewCategoryCreated(Category category) {
        try {
            if (category.getTitle().length() > 0) {
                mPresenter.addCategory(category);
            } else {
                Toast.makeText(this, "No category added...", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "Category insertion error...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPickImageUrl(Category category) {
        mPresenter.updateCategory(category);
    }

    /**
     * Presenter methods.
     */

    @Override
    public void onCategoryLoaded(List<Category> categoryList) {
        mAdapter.updateItems(categoryList);

        Toast.makeText(this, "Categories loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoCategoryLoaded() {
        mCategoryList.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "No category loaded...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryLoadError() {
        Toast.makeText(this, "Category load error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoCategoryAdded() {
        Toast.makeText(this, "Category already exists...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoCategoryDeleted() {
        Toast.makeText(this, "No category deleted...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoCategoryUpdated() {
        Toast.makeText(this, "No category updated...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryUpdatedError() {
        Toast.makeText(this, "Category update error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryDeletedError() {
        Toast.makeText(this, "Category deletion error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryAddedError() {
        Toast.makeText(this, "Category insertion error...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryUpdated() {
        mPresenter.loadCategories();
    }

    @Override
    public void onCategoryAdded() {
        mPresenter.loadCategories();

        Toast.makeText(this, "Categories loaded after insertion.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryDeleted() {
        mPresenter.loadCategories();

        Toast.makeText(this, "Categories loaded after deletion.", Toast.LENGTH_SHORT).show();
    }
}
