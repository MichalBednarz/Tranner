package com.example.android.tranner.mainscreen.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.adapters.CategoryDialogAdapter;
import com.example.android.tranner.mainscreen.adapters.CategoryDialogAdapterDecoration;
import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;

/**
 * Created by Michał on 2017-04-14.
 */

public class CategoryDialog extends DialogFragment {
    private static final String TAG = "AKDialogFragment";

    private RecyclerView mRecyclerview;
    private EditText mDialogEdit;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.category_dialog_view, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mDialogEdit = (EditText)rootView.findViewById(R.id.dialog_edit);
        mRecyclerview = (RecyclerView) rootView.findViewById(R.id.dialog_recyclerview_full);

        toolbar.setTitle("New category");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        
        mAdapter = new CategoryDialogAdapter(this, mListener);
        mRecyclerview.setAdapter(mAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.addItemDecoration(new CategoryDialogAdapterDecoration(getActivity()));

        setHasOptionsMenu(true);
        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_category_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Category category = new Category(mDialogEdit.getText().toString());
            mListener.onNewCategoryCreated(category);
            return true;
        } else if (id == android.R.id.home) {

            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
