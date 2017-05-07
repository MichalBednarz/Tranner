package com.example.android.tranner.data.providers.imageprovider;

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
 * Created by Michał on 2017-05-05.
 */
public class ImagePresenterTest {

    private final PixabayResponse VALID_DATA = new PixabayResponse();
    private PixabayResponse EMPTY_DATA;
    private final String QUERY = "query";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ImageContract.service service;
    @Mock
    ImageContract.view view;

    private ImagePresenter presenter;

    @Before
    public void setUp() {
        presenter = new ImagePresenter(service, Schedulers.trampoline());
        presenter.init(view);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldPassResponseToView() {
        when(service.fetchImages(QUERY)).thenReturn(Single.just(VALID_DATA));

        presenter.fetchImages(QUERY);

        verify(view).onImagesFetched(VALID_DATA);
    }

    @Test
    public void shouldHandleResponseError() {
        when(service.fetchImages(QUERY)).thenReturn(Single.error(new Throwable("error")));

        presenter.fetchImages(QUERY);

        verify(view).onImageFetchError();
    }

    @Test
    public void shouldWait() {
        when(service.fetchImages(QUERY)).thenReturn(Single.just(VALID_DATA));

        presenter.fetchImages(QUERY);

        verify(view).onWaitingForResults();
    }

    @Test
    public void shouldStopWaiting() {
        when(service.fetchImages(QUERY)).thenReturn(Single.just(VALID_DATA));

        presenter.fetchImages(QUERY);

        verify(view).onStopWaiting();
    }
}