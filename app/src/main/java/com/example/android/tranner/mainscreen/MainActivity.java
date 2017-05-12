package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements CategoryDialogListener,
        MainActivityAdapterListener,
        WebImageDialogAdapterListener,
        CategoryContract.View {

    private static final String TAG = "MainActivity";
    private static final String HEADER_URL = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String THEME_PREFERENCE = "theme_preference";
    private static final int THEME_PASTEL = 0;
    private static final int THEME_YELLOW = 1;
    private static final int THEME_SOFT = 2;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    CategoryPresenter mPresenter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    private List<Category> mCategoryList;
    private ImageView mNavHeaderImage;
    private MainActivityAdapter mAdapter;
    private CategoryDialog mCategoryDialog;
    private WebImageDialog mWebDialog;
    private View mNavigationHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //change item theme depending on last user choice stored in activity shared preferences
        //invoked necessarily before setting View
        changeTheme(getPreferences(MODE_PRIVATE).getInt(THEME_PREFERENCE, 0));
        super.onCreate(savedInstanceState);
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
        mAdapter = new MainActivityAdapter(this, mCategoryList);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        mNavigationHeader = mNavigationView.getHeaderView(0);
        mNavHeaderImage = (ImageView) mNavigationHeader.findViewById(R.id.img_header_bg);

        // load nav menu header data
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // loading header background image
        Picasso.with(this).load(HEADER_URL)
                .into(mNavHeaderImage);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                case R.id.nav_theme_one:
                    recreateWithNewTheme(THEME_PASTEL);
                    return true;
                case R.id.nav_theme_two:
                    recreateWithNewTheme(THEME_YELLOW);
                    return true;
                case R.id.nav_theme_three:
                    recreateWithNewTheme(THEME_SOFT);
                    return true;
                case R.id.nav_about_us:

                    return true;
                case R.id.nav_exit:
                    mDrawer.closeDrawers();
                    exit();
                    return true;
                default:
                    return true;
            }
        });


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

        //Setting the actionbarToggle to drawer layout
        mDrawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    /**
     * This method saves already picked theme number in shared preferences and recreates current
     * activity with the purpose of applying it.
     * @param theme constant pointing one of themes.
     */
    private void recreateWithNewTheme(int theme) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(THEME_PREFERENCE, theme);
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    /**
     * Proceed user to the home screen.
     */
    private void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    /**
     * Method responsible for theme change depending on
     * option selected from action bar by the user.
     *
     * @param preference denotes number of item in list of available themes.
     */
    private void changeTheme(int preference) {
        switch (preference) {
            case 0:
                setTheme(R.style.AppThemeOne);
                break;
            case 1:
                setTheme(R.style.AppThemeTwo);
                break;
            case 2:
                setTheme(R.style.AppThemeThree);
                break;
        }
    }

    @Override
    public void onChangeBackdropClicked(Category category) {
        FragmentManager manager = getSupportFragmentManager();
        mWebDialog = WebImageDialog.newInstance(category);
        mWebDialog.show(manager, "web_dialog");
    }

    @Override
    public void onCategoryOpened(Category category) {
        Intent startIntent = CategoryActivity.getStartIntent(this, category);
        startActivity(startIntent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onCategoryDeleted(Category category) {
        mPresenter.deleteCategory(category);
        Toast.makeText(this, "Category " + category.getCategory() + " proceeded to deletion.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryLoaded(List<Category> categoryList) {
        mCategoryList.clear();
        mCategoryList.addAll(categoryList);
        mAdapter.notifyDataSetChanged();
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
    public void onNewCategoryCreated(Category category) {
        try {
            if (category.getCategory().length() > 0) {
                mPresenter.addCategory(category);
            } else {
                Toast.makeText(this, "No category added...", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "Category insertion error...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryUpdated() {
        mPresenter.loadCategories();
    }

    @Override
    public void onPickImageUrl(Category category) {
        mPresenter.updateCategory(category);
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
