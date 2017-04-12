package com.example.android.tranner.mainscreen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.mainscreen.dagger2.DaggerMainActivityComponent;
import com.example.android.tranner.mainscreen.dagger2.MainActivityModule;
import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.dialogs.CategoryDialog;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;
import com.example.android.tranner.mainscreen.mvp.CategoryContract;
import com.example.android.tranner.mainscreen.mvp.CategoryPresenter;

import java.util.ArrayList;
import java.util.Collections;
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
    @Inject CategoryPresenter mPresenter;
    private List<Category> mCategoryList;
    private MainActivityAdapter mAdapter;
    private CategoryDialog mCategoryDialog;
    private WebImageDialog mWebDialog;

    public void fabClick(View view) {
        FragmentManager manager = getSupportFragmentManager();
        mCategoryDialog = CategoryDialog.newInstance();
        mCategoryDialog.show(manager, "category_dialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);

        mCategoryList = new ArrayList<>();
        mPresenter.loadCategories();
        mAdapter = new MainActivityAdapter(this, mCategoryList);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

    }

    @Override
    protected void onDestroy() {
        mPresenter.closeDatabase();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_theme:

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
        Toast.makeText(this, "Category " + category.getCategory() + " deleted.", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "New category successfully added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryDeleted() {
        mPresenter.loadCategories();
        Toast.makeText(this, "Category deleted.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryLoaded(List<Category> categoryList) {
        mCategoryList.clear();
        mCategoryList.addAll(categoryList);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Categories successfully loaded!", Toast.LENGTH_SHORT).show();
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
}
