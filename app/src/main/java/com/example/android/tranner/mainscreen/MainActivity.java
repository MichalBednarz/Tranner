package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.data.Category;
import com.example.android.tranner.mainscreen.dagger2.components.DaggerPresenterComponent;
import com.example.android.tranner.mainscreen.dialogs.CategoryDialog;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;
import com.example.android.tranner.data.providers.categoryprovider.CategoryContract;
import com.example.android.tranner.data.providers.categoryprovider.CategoryPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CategoryDialogListener,
        MainActivityAdapterListener,
        WebImageDialogAdapterListener,
        CategoryContract.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    CategoryPresenter mPresenter;

    public List<Category> mCategoryList;
    private MainActivityAdapter mAdapter;
    private CategoryDialog mCategoryDialog;
    private WebImageDialog mWebDialog;
    private TrannerApp mTrannerApp;

    /**
     * Method that handles click on floating action button.
     * This method gets new instance of CategoryDialog class
     * and displays it.
     *
     * @param view
     */
    public void fabClick(View view) {
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

    }

    @Override
    public void onNoCategoryDeleted() {

    }

    @Override
    public void onNoCategoryUpdated() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //change item theme depending on last user choice stored in activity shared preferences
        //invoked necessarily before setting view
        changeTheme(getPreferences(MODE_PRIVATE).getInt("theme", 0));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTrannerApp = (TrannerApp) getApplication();

        //Dependency injection providing CategoryPresenter
        DaggerPresenterComponent.builder()
                .appComponent(mTrannerApp.getComponent())
                .build()
                .inject(this);

        mPresenter.setView(this);


        mCategoryList = new ArrayList<>();
        mPresenter.loadCategories();
        mAdapter = new MainActivityAdapter(this, mCategoryList);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onCategoryUpdatedError() {

    }

    @Override
    public void onCategoryDeletedError() {
        Toast.makeText(this, "Category Deleted Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryAddedError() {
        Toast.makeText(this, "Category Added Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (item.getItemId()) {
            case R.id.action_change_theme_one:
                editor.putInt("theme", 0);
                editor.apply();
                this.recreate();
                return true;
            case R.id.action_change_theme_two:
                editor.putInt("theme", 1);
                editor.apply();
                this.recreate();
                return true;
            case R.id.action_change_theme_three:
                editor.putInt("theme", 2);
                editor.apply();
                this.recreate();
                return true;

        }
        return super.onOptionsItemSelected(item);
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
            Toast.makeText(this, "Failed to add category...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryDeleted(Category category) {
        mPresenter.deleteCategory(category);
        Toast.makeText(this, "Category " + category.getCategory() + " proceeded to deletion.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeBackdropClicked(Category category) {
        FragmentManager manager = getSupportFragmentManager();
        mWebDialog = WebImageDialog.newInstance(category);
        mWebDialog.show(manager, "web_dialog");
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
