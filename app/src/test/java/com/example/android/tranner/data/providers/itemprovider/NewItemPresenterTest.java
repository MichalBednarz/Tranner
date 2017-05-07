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
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-05-03.
 */
public class NewItemPresenterTest {
    private final List<CategoryItem> MANY_DATA = Arrays.asList(new CategoryItem(), new CategoryItem(), new CategoryItem());
    private final CategoryItem SINGLE_DATA = new CategoryItem();
    private final int CATEGORY = 1;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ItemContract.Repository repository;
    @Mock
    ItemContract.NewView view;
    private NewItemPresenter presenter;

    @Before
    public void setUp() {
        presenter = new NewItemPresenter(repository, Schedulers.trampoline());
        presenter.setView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassNewItemsToView() {
        when(repository.loadNewItems(CATEGORY)).thenReturn(Single.just(MANY_DATA));

        presenter.loadNewItems(CATEGORY);

        verify(view).onNewItemLoaded(MANY_DATA);
    }

    @Test
    public void shouldPassNoNewItemsToView() {
        when(repository.loadNewItems(CATEGORY)).thenReturn(Single.just(EMPTY_LIST));

        presenter.loadNewItems(CATEGORY);

        verify(view).onNoNewItemLoaded();
    }

    @Test
    public void shouldHandlePassNewItemsToViewError() {
        when(repository.loadNewItems(CATEGORY)).thenReturn(Single.error(new Throwable("exception")));

        presenter.loadNewItems(CATEGORY);

        verify(view).onNewItemLoadError();
    }

    @Test
    public void shouldAddItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(0L));

        presenter.addNewItem(SINGLE_DATA);

        verify(view).onItemAdded();
    }

    @Test
    public void shouldAddNoItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(-1L));

        presenter.addNewItem(SINGLE_DATA);

        verify(view).onNoItemAdded();
    }

    @Test
    public void shouldHandleAddItemToDatabaseError() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.addNewItem(SINGLE_DATA);

        verify(view).onItemAddedError();
    }

    @Test
    public void shouldDeleteItem() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.deleteNewItem(SINGLE_DATA);

        verify(view).onItemDeleted();
    }

    @Test
    public void shouldDeleteNoItem() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.deleteNewItem(SINGLE_DATA);

        verify(view).onNoItemDeleted();
    }

    @Test
    public void shouldHandleDeleteItemError() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.deleteNewItem(SINGLE_DATA);

        verify(view).onItemDeletedError();
    }

    @Test
    public void shouldUpdateItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.updateNewItem(SINGLE_DATA);

        verify(view).onItemUpdated();
    }

    @Test
    public void shouldUpdateNoItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.updateNewItem(SINGLE_DATA);

        verify(view).onNoItemUpdated();
    }

    @Test
    public void shouldHandleItemUpdateError() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.updateNewItem(SINGLE_DATA);

        verify(view).onItemUpdatedError();
    }
}