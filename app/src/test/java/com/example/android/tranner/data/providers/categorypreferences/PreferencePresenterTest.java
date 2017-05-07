package com.example.android.tranner.data.providers.categorypreferences;

import android.content.SharedPreferences;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-05-06.
 */
public class PreferencePresenterTest {
    private final Category DATA = new Category();
    private final int PARENT_ID = 1;
    private final int DEFAULT_ID = -1;
    private final String PREF_KEY = "id_key";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    PreferenceContract.Repository repository;
    @Mock
    PreferenceContract.View view;
    @Mock
    SharedPreferences sharedPreferences;

    private PreferencePresenter presenter;
    private RxSharedPreferences rxSharedPreferences;


    @Before
    public void setUp() {
        presenter = new PreferencePresenter(repository, Schedulers.trampoline());
        presenter.init(view);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
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

    @Test
    public void shouldSaveParentId() {
        when(repository.saveParentId(PARENT_ID)).thenReturn(Single.just(true));

        presenter.saveParentId(PARENT_ID);

        verify(view).onParentIdSaved();
    }

    @Test
    public void shouldNotSaveParentId() {
        when(repository.saveParentId(PARENT_ID)).thenReturn(Single.just(false));

        presenter.saveParentId(PARENT_ID);

        verify(view).onParentIdSaveError();
    }

    @Test
    public void shouldHandleSaveParentIdError() {
        when(repository.saveParentId(PARENT_ID)).thenReturn(Single.error(new Throwable()));

        presenter.saveParentId(PARENT_ID);

        verify(view).onParentIdSaveError();
    }

    @Test
    public void shouldRetrieveParentId() {
        Preference<Integer> savedPreference = rxSharedPreferences.getInteger(PREF_KEY, PARENT_ID);

        when(repository.retrieveParentId()).thenReturn(savedPreference.asObservable().firstOrError());

        presenter.retrieveParentId();

        verify(view).onParentIdRetrieved(PARENT_ID);
    }

    @Test
    public void shouldRetrieveNoParentId() {
        Preference<Integer> savedPreference = rxSharedPreferences.getInteger(PREF_KEY, DEFAULT_ID);

        when(repository.retrieveParentId()).thenReturn(savedPreference.asObservable().firstOrError());

        presenter.retrieveParentId();

        verify(view).onNoParentIdRetrieved();
    }

    @Test
    public void shouldHandleRetrieveParentIdError() {
        when(repository.retrieveParentId()).thenReturn(Single.error(new Throwable("error")));

        presenter.retrieveParentId();

        verify(view).onParentIdRetrieveError();
    }


}