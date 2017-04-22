package com.example.android.tranner.categoryscreen;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.android.tranner.R;
import com.example.android.tranner.data.Category;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.ConstantKeys.CATEGORY_INTENT;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Category mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = getIntent().getExtras();
        mCategory = (Category) bundle.get(CATEGORY_INTENT);
        this.setTitle(mCategory.getCategory());


    }

}
