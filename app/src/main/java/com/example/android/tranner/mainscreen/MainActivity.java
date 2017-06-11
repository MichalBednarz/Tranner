package com.example.android.tranner.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.dagger.components.DaggerMainActivityComponent;
import com.example.android.tranner.data.ConstantKeys;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.categoryprovider.CategoryContract;
import com.example.android.tranner.data.providers.categoryprovider.CategoryPresenter;
import com.example.android.tranner.mainscreen.adapters.MainActivityAdapter;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;
import com.example.android.tranner.mainscreen.themes.AppCompatThemedActivity;
import com.example.android.tranner.mainscreen.themes.AppTheme;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.WEB_DIALOG_TAG;
import static java.util.Collections.EMPTY_LIST;

public class MainActivity extends AppCompatThemedActivity implements CategoryDialogListener,
        MainActivityAdapterListener,
        WebImageDialogAdapterListener,
        CategoryContract.View {

    private static final int THEME_POSITION = 0;
    private static final int PASTEL_POSITION = 1;
    private static final int YELLOW_POSITION = 2;
    private static final int SOFT_POSITION = 3;
    private static final int OTHER_POSITION = 4;
    private static final int ABOUT_POSITION = 5;

    @BindView(R.id.activity_main_fab)
    FloatingActionButton fab;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    CategoryPresenter mPresenter;
    private List<Category> mCategoryList = new ArrayList<>();
    private MainActivityAdapter mAdapter;
    private WebImageDialog mWebDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        DaggerMainActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build()
                .inject(this);

        mPresenter.attachView(this);

        mPresenter.loadCategories();

        setUpCategoryRecyclerView();

        setUpNavigationDrawer();
    }

    private void setUpNavigationDrawer() {
        View navigationHeader = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        ImageView headerBackdrop = ButterKnife.findById(navigationHeader, R.id.nav_header_backdrop);

        PrimaryDrawerItem itemTheme = new PrimaryDrawerItem().withIdentifier(THEME_POSITION).withName(R.string.nav_themes).withSelectable(false);
        SecondaryDrawerItem itemPastel = new SecondaryDrawerItem().withIdentifier(PASTEL_POSITION).withName(AppTheme.PASTEL.themeName());
        SecondaryDrawerItem itemYellow = new SecondaryDrawerItem().withIdentifier(YELLOW_POSITION).withName(AppTheme.YELLOW.themeName());
        SecondaryDrawerItem itemSoft = new SecondaryDrawerItem().withIdentifier(SOFT_POSITION).withName(AppTheme.SOFT.themeName());
        PrimaryDrawerItem itemMenu = new PrimaryDrawerItem().withIdentifier(OTHER_POSITION).withName(R.string.nav_other).withSelectable(false).withSelectable(false);
        SecondaryDrawerItem itemAbousUs = new SecondaryDrawerItem().withIdentifier(ABOUT_POSITION).withName(R.string.nav_about_us);

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHeader(navigationHeader)
                .addDrawerItems(
                        itemTheme,
                        new DividerDrawerItem(),
                        itemPastel,
                        itemYellow,
                        itemSoft,
                        new DividerDrawerItem(),
                        itemMenu,
                        itemAbousUs
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case THEME_POSITION:
                            break;
                        case PASTEL_POSITION:
                            applyTheme(AppTheme.PASTEL);
                            break;
                        case YELLOW_POSITION:
                            applyTheme(AppTheme.YELLOW);
                            break;
                        case SOFT_POSITION:
                            applyTheme(AppTheme.SOFT);
                            break;
                        case OTHER_POSITION:
                            break;
                        case ABOUT_POSITION:
                            break;
                    }

                    return true;
                })
                .build();

        Picasso.with(this).load(ConstantKeys.HEADER_URL).into(headerBackdrop);
    }

    private void setUpCategoryRecyclerView() {
        mAdapter = new MainActivityAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    /**
     * Method that handles click on floating action button.
     * This method instantiates and displays dialog used to
     * add new Category with the name chosen by the user.
     */
    @OnClick(R.id.activity_main_fab)
    public void showCategoryDialog() {
        /*
          Instantiate dialog intended to appear on click of neutral button in suggestions dialog.
         */
        MaterialDialog customs = new MaterialDialog.Builder(this)
                .title(R.string.category_custom_title)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(getString(R.string.category_custom_hint), null, false, (dialog, input) ->
                        mPresenter.addCategory(new Category(input.toString())))

                .negativeText(R.string.dialog_cancel)
                .build();

         /*
          Instantiate dialog containing list of pre-made suggested category names.
         */
        MaterialDialog suggestions = new MaterialDialog.Builder(this)
                .title(R.string.category_title)
                .items(R.array.category_suggested)
                .itemsCallbackSingleChoice(0, (dialog, view, which, text) -> {
                    mPresenter.addCategory(new Category(text.toString()));

                    return true;
                })
                .positiveText(R.string.category_choose)
                .neutralText(R.string.category_custom)
                .onNeutral((dialog, which) -> {
                    customs.show();
                })
                .build();

        suggestions.show();
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
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getResources().getString(R.string.delete_title))
                .setContentText(getResources().getString(R.string.delete_content))
                .setCancelText(getResources().getString(R.string.delete_cancel))
                .setConfirmText(getResources().getString(R.string.delete_confirm))
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> sDialog.cancel())
                .setConfirmClickListener(sDialog -> {
                    mPresenter.deleteCategory(category);
                    mPresenter.loadCategories();

                    sDialog.cancel();
                })
                .show();
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
        mAdapter.updateCategoryList(categoryList);

        Toast.makeText(this, "Categories loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoCategoryLoaded() {
        mAdapter.updateCategoryList(EMPTY_LIST);

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
