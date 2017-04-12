package com.example.android.tranner.mainscreen;

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
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.mainscreen.dagger2.DaggerMainActivityComponent;
import com.example.android.tranner.mainscreen.dagger2.MainActivityComponent;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CategoryDialogListener,
        MainActivityAdapterListener,
        WebImageDialogAdapterListener,
        CategoryContract.View {

    private static final String TAG = "MainActivity";
    private static MainActivity sInstance;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    private List<Category> mCategoryList;
    private MainActivityAdapter mAdapter;
    private CategoryDialog mCategoryDialog;
    private WebImageDialog mWebDialog;
    @Inject CategoryPresenter mPresenter;


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

        mPresenter.loadCategories();

        mCategoryList = new ArrayList<>();
        mCategoryList.add(new Category("one"));
        mCategoryList.add(new Category("two"));
        mCategoryList.add(new Category("three"));
        mCategoryList.add(new Category("four"));


        mAdapter = new MainActivityAdapter(this, mCategoryList);

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

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
    public void onNewCategoryCreated(String category) {
        try {
            if (category.length() > 0) {
                mCategoryList.add(new Category(category));
                mAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Category " + category + " successfully added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No category added...", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCategoryDeleted(int position) {
        String name = mCategoryList.get(position).getCategory();
        mCategoryList.remove(position);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Category " + name + " deleted.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeBackdropClicked(int position) {
        FragmentManager manager = getSupportFragmentManager();
        mWebDialog = WebImageDialog.newInstance(position);
        mWebDialog.show(manager, "web_dialog");
    }

    @Override
    public void onPickImageUrl(int position, String mImageUrl) {
        mCategoryList.get(position).setImageUrl(mImageUrl);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryLoaded(List<Category> categoryList) {
        this.mCategoryList = categoryList;
    }

    @Override
    public void onNoCategoryLoaded() {
        Toast.makeText(this, "No categories...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategoryLoadError() {
        Toast.makeText(this, "Category load error...", Toast.LENGTH_SHORT).show();
    }
}
