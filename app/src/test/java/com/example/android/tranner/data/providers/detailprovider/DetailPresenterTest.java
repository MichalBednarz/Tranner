package com.example.android.tranner.data.providers.detailprovider;

import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.data.providers.itemprovider.ItemContract;
import com.example.android.tranner.data.providers.itemprovider.NewItemPresenter;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-05-07.
 */
public class DetailPresenterTest {
    private final CategoryItem SINGLE_DATA = new CategoryItem();
    private final int ITEM_ID = 1;
    private final int VALID_ROW = 1;
    private final int NOT_VALID_ROW = 0;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    DetailContract.Repository repository;
    @Mock
    DetailContract.View view;
    private DetailPresenter presenter;

    @Before
    public void setUp() {
        presenter = new DetailPresenter(repository, Schedulers.trampoline());
        presenter.attachView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        presenter.detachView();
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassItemView() {
        when(repository.loadItem(ITEM_ID)).thenReturn(Single.just(SINGLE_DATA));

        presenter.loadItem(ITEM_ID);

        verify(view).onItemLoaded(SINGLE_DATA);
    }

    @Test
    public void shouldHandleLoadItemError() {
        when(repository.loadItem(ITEM_ID)).thenReturn(Single.error(new Throwable("error")));

        presenter.loadItem(ITEM_ID);

        verify(view).onItemLoadError();
    }

    @Test
    public void shouldUpdateItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(VALID_ROW));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onItemUpdated();
    }

    @Test
    public void shouldUpdateNoItem() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.just(NOT_VALID_ROW));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onNoItemUpdated();
    }

    @Test
    public void shouldHandleUpdateItemError() {
        when(repository.updateItem(SINGLE_DATA)).thenReturn(Single.error(new Throwable("error")));

        presenter.updateItem(SINGLE_DATA);

        verify(view).onItemUpdateError();
    }
}