package com.example.android.tranner.mainscreen.mvp;

import com.example.android.tranner.mainscreen.data.Category;

import java.util.List;

/**
 * Created by Michał on 2017-04-11.
 */

public class CategoryContract {
    public interface View {
        void onCategoryLoaded(List<Category> categoryList);
        void onNoCategoryLoaded();
        void onCategoryLoadError();
        void onCategoryAdded();
        void onCategoryDeleted();
        void onCategoryUpdated();
    }

    public interface Actions {
        void loadCategories();
        void addCategory(Category category);
        void deleteCategory(Category category);
        void updateCategory(Category category);
        void closeDatabase();
    }

    public interface Repository {
        List<Category> loadCategories();
        void addCategory(Category category);
        void deleteCategory(Category category);
        void updateCategory(Category category);
        void closeDatabase();
    }
}
