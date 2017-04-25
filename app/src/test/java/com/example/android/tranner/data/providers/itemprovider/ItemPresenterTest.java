package com.example.android.tranner.data.providers.itemprovider;

import com.example.android.tranner.data.providers.categoryprovider.Category;

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
 * Created by Michał on 2017-04-24.
 */

public class ItemPresenterTest {

    private final List<CategoryItem> MANY_DATA = Arrays.asList(new CategoryItem(), new CategoryItem(), new CategoryItem());
    private final CategoryItem SINGLE_DATA = new CategoryItem();
    private final Category CATEGORY = new Category();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ItemContract.Repository repository;
    @Mock
    ItemContract.View view;
    private ItemPresenter presenter;

    @Before
    public void setUp() {
        presenter = new ItemPresenter(repository, Schedulers.trampoline());
        presenter.setView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassItemsToView() {
        when(repository.loadItems(CATEGORY)).thenReturn(Single.just(MANY_DATA));

        presenter.loadItems(CATEGORY);

        verify(view).onItemLoaded(MANY_DATA);
    }

    @Test
    public void shouldPassNoItemsToView() {
        when(repository.loadItems(CATEGORY)).thenReturn(Single.just(EMPTY_LIST));

        presenter.loadItems(CATEGORY);

        verify(view).onNoItemLoaded();
    }

    @Test
    public void shouldHandlePassItemsToViewError() {
        when(repository.loadItems(CATEGORY)).thenReturn(Single.error(new Throwable("exception")));

        presenter.loadItems(CATEGORY);

        verify(view).onItemLoadError();
    }

    @Test
    public void shouldAddItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(0L));

        presenter.addItem(SINGLE_DATA);

        verify(view).onItemAdded();
    }

    @Test
    public void shouldAddNoItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(-1L));

        presenter.addItem(SINGLE_DATA);

        verify(view).onNoItemAdded();
    }

    @Test
    public void shouldHandleAddItemToDatabaseError() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.addItem(SINGLE_DATA);

        verify(view).onItemAddedError();
    }

    @Test
    public void shouldDeleteCategory() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.deleteItem(SINGLE_DATA);

        verify(view).onItemDeleted();
    }

    @Test
    public void shouldDeleteNoCategory() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.deleteItem(SINGLE_DATA);

        verify(view).onNoItemDeleted();
    }

    @Test
    public void shouldHandleDeleteCategoryError() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.deleteItem(SINGLE_DATA);

        verify(view).onItemDeletedError();
    }

    @Test
    public void shouldUpdateCategory() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onItemUpdated();
    }

    @Test
    public void shouldUpdateNoCategory() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onNoItemUpdated();
    }

    @Test
    public void shouldHandleCategoryUpdateError() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onItemUpdatedError();
    }

}