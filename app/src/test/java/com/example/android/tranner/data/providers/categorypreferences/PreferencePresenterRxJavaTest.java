package com.example.android.tranner.data.providers.categorypreferences;

import android.content.SharedPreferences;

import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

/**
 * Created by Michał on 2017-05-07.
 */
public class PreferencePresenterRxJavaTest {

    private final int PARENT_ID = 1;
    private final String PREF_ID = "1";
    private final Category CATEGORY = new Category();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    PreferenceContract.Repository repository;
    @Mock
    SharedPreferences sharedPreferences;
    private RxSharedPreferences rxPreferences;

    @Before
    public void setUp() {
        rxPreferences = RxSharedPreferences.create(sharedPreferences);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void shouldLoadParentCategoryWithoutErrors() {
        when(repository.loadParentCategory(PARENT_ID)).thenReturn(Single.just(CATEGORY));

        TestObserver<Category> observer = new TestObserver<>();
        Observable<Category> observable = repository.loadParentCategory(PARENT_ID).toObservable();
        observable.subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValue(CATEGORY);
    }

    @Test
    public void shouldSaveParentIdWithoutErrors() {
        when(repository.saveParentId(PARENT_ID)).thenReturn(Single.just(true));

        TestObserver<Boolean> observer = new TestObserver<>();
        Observable<Boolean> observable = repository.saveParentId(PARENT_ID).toObservable();
        observable.subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValue(true);
    }

    @Test
    public void shouldHandleSaveParentIdError() {
        when(repository.saveParentId(PARENT_ID)).thenReturn(Single.just(false));

        TestObserver<Boolean> observer = new TestObserver<>();
        Observable<Boolean> observable = repository.saveParentId(PARENT_ID).toObservable();
        observable.subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValue(false);
    }

    @Test
    public void shouldRetrieveParentId() {
        Preference<Integer> pref = rxPreferences.getInteger(PREF_ID, PARENT_ID);

        when(repository.retrieveParentId()).thenReturn(pref.asObservable().firstOrError());

        TestObserver<Integer> observer = new TestObserver<>();
        Single<Integer> single = repository.retrieveParentId();
        Observable<Integer> observable = single.toObservable();
        observable.subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValue(PARENT_ID);
    }

    @Test
    public void shouldHandleParentIdError() {
        Preference<Integer> pref = rxPreferences.getInteger(PREF_ID, PARENT_ID);

        when(repository.retrieveParentId()).thenReturn(pref.asObservable().firstOrError());

        TestObserver<Integer> observer = new TestObserver<>();
        Single<Integer> single = repository.retrieveParentId();
        Observable<Integer> observable = single.toObservable();
        observable.subscribe(observer);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValue(PARENT_ID);
    }

}