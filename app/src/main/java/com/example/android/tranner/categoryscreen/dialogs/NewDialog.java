package com.example.android.tranner.categoryscreen.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;

/**
 * Created by Michał on 2017-04-26.
 */

public class NewDialog extends DialogFragment {

    private OnNewFragmentListener mDialogListener;
    private EditText mEditText;

    public NewDialog() {

    }

    public static NewDialog newInstance() {

        Bundle args = new Bundle();

        NewDialog fragment = new NewDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mDialogListener = (OnNewFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement NewDialogtListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.new_dialog, null);
        mEditText = (EditText) v.findViewById(R.id.new_dialog_edit);


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add SUBCATEGORY")
                .setView(v)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Create", (dialog, which) -> mDialogListener.onNewItemAdded(mEditText.getText().toString()));

        return builder.create();
    }
}
