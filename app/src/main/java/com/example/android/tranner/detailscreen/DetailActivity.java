package com.example.android.tranner.detailscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.components.DaggerDetailActivityComponent;
import com.example.android.tranner.data.providers.detailprovider.DetailContract;
import com.example.android.tranner.data.providers.detailprovider.DetailPresenter;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.DETAIL_INTENT;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    DetailPresenter mPresenter;

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
            int itemId = bundle.getInt(DETAIL_INTENT);
            mPresenter.loadItem(itemId);
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Ups, something went wrong!")
                    .show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();

    }

    @Override
    public void onItemLoaded(CategoryItem item) {
        setTitle(item.getName());
        Toast.makeText(this, "Item loaded.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLoadError() {

    }

    @Override
    public void onItemUpdated() {

    }

    @Override
    public void onNoItemUpdated() {

    }

    @Override
    public void onItemUpdateError() {

    }
}
