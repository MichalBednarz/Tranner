package com.example.android.tranner.data.providers.categoryparent;

import android.content.SharedPreferences;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.categoryprovider.CategoryContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-05-06.
 */
public class ParentCategoryPresenterTest {
    private final Category DATA = new Category();
    private final int PARENT_ID = 1;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    CategoryContract.Repository repository;
    @Mock
    ParentCategoryContract.View view;
    @Mock
    SharedPreferences sharedPreferences;

    private ParentCategoryPresenter presenter;


    @Before
    public void setUp() {
        presenter = new ParentCategoryPresenter(repository, Schedulers.trampoline());
        presenter.attachView(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        presenter.detachView();
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassParentCategoryToView() {
        when(repository.loadParentCategory(PARENT_ID)).thenReturn(Single.just(DATA));

        presenter.loadParentCategory(PARENT_ID);

        verify(view).onParentCategoryLoaded(DATA);
    }

    @Test
    public void shouldHandleLoadCategoryError() {
        when(repository.loadParentCategory(PARENT_ID)).thenReturn(Single.error(new Throwable()));

        presenter.loadParentCategory(PARENT_ID);

        verify(view).onParentCategoryLoadError();
    }
}