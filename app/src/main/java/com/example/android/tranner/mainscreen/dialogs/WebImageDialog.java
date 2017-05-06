package com.example.android.tranner.mainscreen.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.TrannerApp;
import com.example.android.tranner.dagger.components.DaggerImagePresenterComponent;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.imageprovider.ImageContract;
import com.example.android.tranner.data.providers.imageprovider.ImageHit;
import com.example.android.tranner.data.providers.imageprovider.ImagePresenter;
import com.example.android.tranner.data.providers.imageprovider.PixabayResponse;
import com.example.android.tranner.mainscreen.adapters.WebImageDialogAdapter;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Michał on 2017-04-10.
 */

public class WebImageDialog extends DialogFragment implements ImageContract.view {

    public static final String ARG_CATEGORY = "arg_category";
    public static final String TITLE = "Pick BACKDROP";
    public static final String NEGATIVE_BUTTON = "CANCEL";
    private static final String TAG = "WebImageDialog";
    @BindView(R.id.web_dialog_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.web_edit_search)
    AppCompatEditText mEditSearch;
    @BindView(R.id.web_search_button)
    Button mSearchButton;
    Unbinder unbinder;
    @Inject
    ImagePresenter mImagePresenter;
    private List<ImageHit> mImageList = new ArrayList<>();
    private WebImageDialogAdapterListener mListener;
    private WebImageDialogAdapter mAdapter;
    private Category mPickedCategory;
    private SweetAlertDialog mAlertDialog;

    public static WebImageDialog newInstance(Category category) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY, category);
        WebImageDialog fragment = new WebImageDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @OnClick(R.id.web_search_button)
    public void searchImagesByTag() {
        if (mEditSearch.getText() != null && mEditSearch.getText().length() > 0) {
            mImagePresenter.fetchImages(mEditSearch.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Please type query.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (WebImageDialogAdapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CreateCategoryListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.web_dialog_fragment, null);
        unbinder = ButterKnife.bind(this, view);

        //Retrieve specific category chosen by user to modify its backdrop
        mPickedCategory = (Category) getArguments().getSerializable(ARG_CATEGORY);

        //Provide ImagePresenter by dagger2 dependency injection
        DaggerImagePresenterComponent.builder()
                .networkComponent(((TrannerApp) getActivity().getApplication()).getNetworkingComponent())
                .build()
                .inject(this);

        //Provide ImagePresenter with class implementing ImageContract.View
        mImagePresenter.init(this);

        mAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Waiting for response...");

        setupRecyclerView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(TITLE)
                .setView(view)
                .setNegativeButton(NEGATIVE_BUTTON, (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mImagePresenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupRecyclerView() {
        mAdapter = new WebImageDialogAdapter(this.getContext(), mImageList, mPickedCategory);
        mAdapter.setListener(mListener);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * ImagePresenter view methods.
     */

    @Override
    public void onWaitingForResults() {
        mAlertDialog.show();

    }

    @Override
    public void onStopWaiting() {
        mAlertDialog.dismiss();
    }

    @Override
    public void onImagesFetched(PixabayResponse pixabayResponseList) {
        mImageList.clear();
        mImageList.addAll(pixabayResponseList.getHits());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageFetchError() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Ups, something went wrong!")
                .show();
    }
}
