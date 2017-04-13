package com.example.android.tranner.mainscreen.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.adapters.WebImageDialogAdapter;
import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.data.WebImageDialogList;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;

import java.util.List;

/**
 * Created by Michał on 2017-04-10.
 */

public class WebImageDialog extends android.support.v4.app.DialogFragment {

    private static final String TAG = "WebImageDialog";

    private List<String> mUrls = new WebImageDialogList().initializeWebLinkList();
    private WebImageDialogAdapterListener mListener;
    private RecyclerView mRecyclerView;
    private WebImageDialogAdapter mAdapter;

    public static WebImageDialog newInstance(Category category) {

        Bundle args = new Bundle();
        args.putSerializable("category", category);
        WebImageDialog fragment = new WebImageDialog();
        fragment.setArguments(args);
        return fragment;
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.web_dialog_fragment, null);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.web_dialog_recyclerview);
        mAdapter = new WebImageDialogAdapter(this, mUrls, (Category) getArguments().getSerializable("category"));
        mAdapter.setListener(mListener);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick your favorite backdrop!")
                .setView(v)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
}
