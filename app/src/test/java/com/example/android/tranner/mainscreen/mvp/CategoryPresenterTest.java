package com.example.android.tranner.mainscreen.mvp;

import com.example.android.tranner.data.Category;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-04-20.
 */

public class CategoryPresenterTest {

    private final List<Category> MANY_DATA = Arrays.asList(new Category(), new Category(), new Category());

    private CategoryPresenter presenter;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    CategoryContract.Repository repository;
    @Mock
    CategoryContract.View view;
    @Mock
    Category category;

    @Before
    public void setUp() {
        presenter = new CategoryPresenter(repository, Schedulers.trampoline());
        presenter.setView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassCategoriesToView() throws Exception {
        when(repository.loadCategories()).thenReturn(Single.just(MANY_DATA));

        presenter.loadCategories();

        verify(view).onCategoryLoaded(MANY_DATA);
    }

    @Test
    public void shouldPassNoCategoriesToView() throws Exception {
        when(repository.loadCategories()).thenReturn(Single.just(EMPTY_LIST));

        presenter.loadCategories();

        verify(view).onNoCategoryLoaded();
    }

    @Test
    public void ShouldHandleError() throws Exception {
        when(repository.loadCategories()).thenReturn(Single.error(new Throwable("exception")));

        presenter.loadCategories();

        verify(view).onCategoryLoadError();
    }

    @Test
    public void shouldAddCategory() {
        when(repository.addCategory(category)).thenReturn(Single.just(0L));

        presenter.addCategory(category);

        verify(view).onCategoryAdded();
    }

    @Test
    public void shouldAddNoCategory() {
        when(repository.addCategory(category)).thenReturn(Single.just(-1L));

        presenter.addCategory(category);

        verify(view).onNoCategoryAdded();
    }

    @Test
    public void shouldHandleAddCategoryError() {
        when(repository.addCategory(category)).thenReturn(Single.error(new Throwable("exception")));

        presenter.addCategory(category);

        verify(view).onCategoryAddedError();
    }


    @Test
    public void shouldDeleteCategory() {
        when(repository.deleteCategory(category)).thenReturn(Single.just(1));

        presenter.deleteCategory(category);

        verify(view).onCategoryDeleted();
    }

    @Test
    public void shouldDeleteNoCategory() {
        when(repository.deleteCategory(category)).thenReturn(Single.just(0));

        presenter.deleteCategory(category);

        verify(view).onNoCategoryDeleted();
    }

    @Test
    public void shouldHandleDeleteCategoryError() {
        when(repository.deleteCategory(category)).thenReturn(Single.error(new Throwable("exception")));

        presenter.deleteCategory(category);

        verify(view).onCategoryDeletedError();
    }

    @Test
    public void shouldUpdateCategory() {
        when(repository.updateCategory(category)).thenReturn(Single.just(1));

        presenter.updateCategory(category);

        verify(view).onCategoryUpdated();
    }

    @Test
    public void shouldUpdateNoCategory() {
        when(repository.updateCategory(category)).thenReturn(Single.just(0));

        presenter.updateCategory(category);

        verify(view).onNoCategoryUpdated();
    }

    @Test
    public void shouldHandleCategoryUpdateError() {
        when(repository.updateCategory(category)).thenReturn(Single.error(new Throwable("exception")));

        presenter.updateCategory(category);

        verify(view).onCategoryUpdatedError();
    }

}