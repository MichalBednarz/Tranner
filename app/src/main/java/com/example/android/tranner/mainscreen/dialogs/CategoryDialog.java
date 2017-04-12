package com.example.android.tranner.mainscreen.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.android.tranner.mainscreen.adapters.CategoryDialogAdapter;
import com.example.android.tranner.mainscreen.adapters.CategoryDialogAdapterDecoration;
import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;
import com.example.android.tranner.R;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;


/**
 * Created by Michał on 2017-04-09.
 */

public class CategoryDialog extends android.support.v4.app.DialogFragment {

    public static final String TAG = CategoryDialog.class.getSimpleName();

    private EditText mDialogEdit;
    private RecyclerView mRecyclerview;
    private CategoryDialogAdapter mAdapter;
    private CategoryDialogListener mListener;

    public static CategoryDialog newInstance() {

        Bundle args = new Bundle();
        CategoryDialog fragment = new CategoryDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (CategoryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CreateCategoryListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.category_dialog_fragment, null);
        mDialogEdit = (EditText) view.findViewById(R.id.dialog_edit);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.dialog_recyclerview);
        mAdapter = new CategoryDialogAdapter(this, mListener);
        mRecyclerview.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.addItemDecoration(new CategoryDialogAdapterDecoration(getActivity()));

        AlertDialog.Builder builder = new AlertDialog
                .Builder(getActivity())
                .setView(view)
                .setTitle("Create new category!")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Category category = new Category(mDialogEdit.getText().toString());
                        mListener.onNewCategoryCreated(category);
                    }
                });

        return builder.create();
    }
}



