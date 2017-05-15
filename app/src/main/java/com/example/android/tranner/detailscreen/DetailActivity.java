package com.example.android.tranner.detailscreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.components.DaggerDetailActivityComponent;
import com.example.android.tranner.data.providers.detailprovider.DetailContract;
import com.example.android.tranner.data.providers.detailprovider.DetailPresenter;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.DETAIL_INTENT;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private static final String TAG = "DetailActivity";
    private static final int SELECT_PHOTO = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    @Inject
    DetailPresenter mPresenter;

    @BindView(R.id.detail_activity_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_category_fab_menu)
    FloatingActionMenu mFloatingMenu;
    @BindView(R.id.material_design_floating_action_menu_item2)
    FloatingActionButton mFloatingMenuTwo;
    @BindView(R.id.material_design_floating_action_menu_item3)
    FloatingActionButton mFloatingMenuThree;
    @BindView(R.id.htab_collapse_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.activity_detail_edit_text)
    TextInputLayout mDescription;
    @BindView(R.id.editText1)
    TextInputEditText mEditText;

    private CategoryItem mParentItem;
    private int mParentId;

    /**
     * Use this factory method to create a new instance of
     * this detail_activity using the provided parameters.
     *
     * @param context
     * @param itemId
     * @return
     */
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
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.error))
                    .show();
        }

        //saves description data typed by the user on keyboard hide
        KeyboardVisibilityEvent.setEventListener(
                this,
                isOpen -> {
                    if (!isOpen) {
                        mParentItem.setDescription(mEditText.getText().toString());

                        mPresenter.updateItem(mParentItem);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }

    @OnClick(R.id.material_design_floating_action_menu_item2)
    public void onItemTwoClick() {
        mFloatingMenu.close(true);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            dispatchTakePictureIntent();
        } else {
            Toast.makeText(this, "Option not available.", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
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
                break;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Uri takenPhoto = imageReturnedIntent.getData();
                    mParentItem.setImageUri(takenPhoto.toString());
                    mPresenter.updateItem(mParentItem);
                }
        }
    }

    @Override
    public void onItemLoaded(CategoryItem item) {
        extractItem(item);

        if (item.getImageUri() != null) {
            updateBackground(item.getImageUri());
        }

        Toast.makeText(this, "Item loaded.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load item attributes into layout.
     * @param item
     */
    private void extractItem(CategoryItem item) {
        mParentItem = item;

        if (item.getName() != null && item.getName().length() > 0) {
            mCollapseToolbar.setTitle(item.getName());
        } else {
            mCollapseToolbar.setTitle(getString(R.string.detail_title));
        }

        mEditText.setText(item.getDescription());
    }

    /**
     * This method takes care for conversion of image address stored by user in the item
     * into background image.
     * @param path
     */
    private void updateBackground(String path) {
        InputStream imageStream = null;

        try {
            imageStream = getContentResolver().openInputStream(Uri.parse(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Drawable drawable = new BitmapDrawable(getResources(), yourSelectedImage);
            mCollapseToolbar.setBackground(drawable);
        } else {
            Drawable drawable = new BitmapDrawable(yourSelectedImage);
            mCollapseToolbar.setBackgroundDrawable(drawable);
        }
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
