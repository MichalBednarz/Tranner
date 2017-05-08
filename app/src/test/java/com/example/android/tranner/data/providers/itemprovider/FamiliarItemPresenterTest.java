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
 * Created by Michał on 2017-05-03.
 */
public class FamiliarItemPresenterTest {
    private final List<CategoryItem> MANY_DATA = Arrays.asList(new CategoryItem(), new CategoryItem(), new CategoryItem());
    private final CategoryItem SINGLE_DATA = new CategoryItem();
    private final int CATEGORY_ID = 1;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ItemContract.Repository repository;
    @Mock
    ItemContract.FamiliarView view;
    private FamiliarItemPresenter presenter;

    @Before
    public void setUp() {
        presenter = new FamiliarItemPresenter(repository, Schedulers.trampoline());
        presenter.attachFamiliarView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        presenter.detachFamiliarView();
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassFamiliarItemsToView() {
        when(repository.loadFamiliarItems(CATEGORY_ID)).thenReturn(Single.just(MANY_DATA));

        presenter.loadFamiliarItems(CATEGORY_ID);

        verify(view).onFamiliarItemLoaded(MANY_DATA);
    }

    @Test
    public void shouldPassNoFamiliarItemsToView() {
        when(repository.loadFamiliarItems(CATEGORY_ID)).thenReturn(Single.just(EMPTY_LIST));

        presenter.loadFamiliarItems(CATEGORY_ID);

        verify(view).onNoFamiliarItemLoaded();
    }

    @Test
    public void shouldHandlePassFamiliarItemsToViewError() {
        when(repository.loadFamiliarItems(CATEGORY_ID)).thenReturn(Single.error(new Throwable("exception")));

        presenter.loadFamiliarItems(CATEGORY_ID);

        verify(view).onFamiliarItemLoadError();
    }

    @Test
    public void shouldAddItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(0L));

        presenter.addFamiliarItem(SINGLE_DATA);

        verify(view).onItemAdded();
    }

    @Test
    public void shouldAddNoItemToDatabase() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.just(-1L));

        presenter.addFamiliarItem(SINGLE_DATA);

        verify(view).onNoItemAdded();
    }

    @Test
    public void shouldHandleAddItemToDatabaseError() {
        when(repository.addItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.addFamiliarItem(SINGLE_DATA);

        verify(view).onItemAddedError();
    }

    @Test
    public void shouldDeleteItem() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.deleteFamiliarItem(SINGLE_DATA);

        verify(view).onItemDeleted();
    }

    @Test
    public void shouldDeleteNoItem() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.deleteFamiliarItem(SINGLE_DATA);

        verify(view).onNoItemDeleted();
    }

    @Test
    public void shouldHandleDeleteItemError() {
        when(repository.deleteItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.deleteFamiliarItem(SINGLE_DATA);

        verify(view).onItemDeletedError();
    }

    @Test
    public void shouldUpdateItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(1));

        presenter.updateFamiliarItem(SINGLE_DATA);

        verify(view).onItemUpdated();
    }

    @Test
    public void shouldUpdateNoItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(0));

        presenter.updateFamiliarItem(SINGLE_DATA);

        verify(view).onNoItemUpdated();
    }

    @Test
    public void shouldHandleItemUpdateError() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("exception")));

        presenter.updateFamiliarItem(SINGLE_DATA);

        verify(view).onItemUpdatedError();
    }
}