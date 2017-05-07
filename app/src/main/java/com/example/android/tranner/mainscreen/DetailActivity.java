package com.example.android.tranner.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.android.tranner.R;
import com.example.android.tranner.data.ConstantKeys;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;
import static com.example.android.tranner.data.ConstantKeys.DETAIL_INTENT;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private CategoryItem mParentItem;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Intent getStartIntent(Context context, CategoryItem item) {
        Intent startIntent = new Intent(context, DetailActivity.class);
        startIntent.putExtra(ConstantKeys.DETAIL_INTENT, item);

        return startIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().hasExtra(CATEGORY_INTENT)) {
            Bundle bundle = getIntent().getExtras();
            mParentItem = (CategoryItem) bundle.get(DETAIL_INTENT);
            if (mParentItem.getName() != null && mParentItem.getName().length() > 0) {
                this.setTitle(mParentItem.getName());
            }
        } else {
            Log.d(TAG, "onCreate: CategoryItem must be passed");
        }


    }
}
