package com.example.android.tranner.data;

import com.example.android.tranner.data.providers.categoryprovider.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michał on 2017-04-25.
 */

public class DialogSuggestedList {

    public static final List<Category> SUGGESTED_CATEGORIES =
            new ArrayList<Category>() {{
                add(new Category("Hobby"));
                add(new Category("Work"));
                add(new Category("Sport"));
                add(new Category("Cooking"));
                add(new Category("Passion"));
                add(new Category("Reading"));
                add(new Category("Cars"));
                add(new Category("Mountains"));
                add(new Category("Travelling"));
                add(new Category("Cycling"));
            }};
}
