package com.example.android.tranner.detailscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.components.DaggerDetailActivityComponent;
import com.example.android.tranner.data.providers.detailprovider.DetailContract;
import com.example.android.tranner.data.providers.detailprovider.DetailPresenter;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.android.tranner.data.ConstantKeys.DETAIL_INTENT;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final int SELECT_PHOTO = 1;
    private static final String TAG = "DetailActivity";
    @Inject
    DetailPresenter mPresenter;

    @BindView(R.id.detail_activity_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_detail_textview)
    EditText mDescription;
    @BindView(R.id.detail_category_fab_menu)
    FloatingActionMenu mFloatingMenu;
    @BindView(R.id.material_design_floating_action_menu_item1)
    FloatingActionButton mFloatingMenuOne;
    @BindView(R.id.material_design_floating_action_menu_item2)
    FloatingActionButton mFloatingMenuTwo;
    @BindView(R.id.material_design_floating_action_menu_item3)
    FloatingActionButton mFloatingMenuThree;
    @BindView(R.id.htab_collapse_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.detail_activity_layout)
    CoordinatorLayout detailActivityLayout;

    private CategoryItem mParentItem;
    private int mParentId;

    public static Intent getStartIntent(Context context, int itemId) {
        Intent startIntent = new Intent(context, DetailActivity.class);
        Bundle args = new Bundle();
        args.putInt(DETAIL_INTENT, itemId);
        startIntent.putExtras(args);

        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DaggerDetailActivityComponent.builder()
                .appComponent(((TrannerApp) getApplication()).getComponent())
                .build()
                .inject(this);

        mPresenter.attachView(this);

        if (getIntent().hasExtra(DETAIL_INTENT)) {
            Bundle bundle = getIntent().getExtras();
            mParentId = bundle.getInt(DETAIL_INTENT);
            mPresenter.loadItem(mParentId);
        } else {
            /*new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Ups, something went wrong!")
                    .show();*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @OnClick(R.id.material_design_floating_action_menu_item1)
    public void onItemOneClick() {
        mFloatingMenu.close(false);

        mDescription.setClickable(true);
        mDescription.setEnabled(true);
        mDescription.setFocusable(true);
        mDescription.setFocusableInTouchMode(true);

        Log.d(TAG, "onItemOneClick: " + mDescription.requestFocus());

        showSoftKeyboard(mDescription);

    }

    private void showSoftKeyboard(View view) {
        Log.d(TAG, "showSoftKeyboard: " + view.requestFocus());

        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @OnClick(R.id.material_design_floating_action_menu_item2)
    public void onItemTwoClick() {

    }

    @OnClick(R.id.material_design_floating_action_menu_item3)
    public void onItemThreeClick() {
        mFloatingMenu.close(true);

        pickImageFromAlbum();
    }

    private void pickImageFromAlbum() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();

                    mParentItem.setImageUri(selectedImage.toString());

                    mPresenter.updateItem(mParentItem);
                }
        }
    }

    @Override
    public void onItemLoaded(CategoryItem item) {
        mParentItem = item;
        mCollapseToolbar.setTitle(item.getName());
        mDescription.setText(item.getDescription());

        if (item.getImageUri() != null) {
            updateBackground(item.getImageUri());
        } else {

        }

        Toast.makeText(this, "Item loaded.", Toast.LENGTH_SHORT).show();
    }

    private void updateBackground(String path) {
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(Uri.parse(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
        Drawable drawable = new BitmapDrawable(yourSelectedImage);
        mCollapseToolbar.setBackgroundDrawable(drawable);
    }

    @Override
    public void onItemLoadError() {
        Toast.makeText(this, "Item load error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemUpdated() {
        mPresenter.loadItem(mParentId);
        Toast.makeText(this, "Item updated.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemUpdated() {
        Toast.makeText(this, "No item updated.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemUpdateError() {
        Toast.makeText(this, "Item update error.", Toast.LENGTH_SHORT).show();
    }
}
