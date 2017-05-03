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
import com.example.android.tranner.categoryscreen.listeners.OnAddItemDialogListener;

import static com.example.android.tranner.data.ConstantKeys.*;

/**
 * Created by Michał on 2017-04-26.
 */

public class AddItemDialog extends DialogFragment {

    private OnAddItemDialogListener mDialogListener;
    private EditText mEditText;

    public AddItemDialog() {

    }

    public static AddItemDialog newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(DIALOG_KEY, title);
        AddItemDialog fragment = new AddItemDialog();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mDialogListener = (OnAddItemDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement NewDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.new_dialog, null);
        mEditText = (EditText) v.findViewById(R.id.new_dialog_edit);
        Bundle bundle = getArguments();
        String title = new String();
        if(bundle != null) {
            title = bundle.getString(DIALOG_KEY);
        }


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setView(v)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Create", (dialog, which) -> mDialogListener.onDialogAddItem(mEditText.getText().toString()));

        return builder.create();
    }
}
