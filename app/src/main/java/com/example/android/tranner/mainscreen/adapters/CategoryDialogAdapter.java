package com.example.android.tranner.mainscreen.adapters;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-04-10.
 */

public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogAdapter.ViewHolder> {

    public List<Category> mDialogSuggested =
            new ArrayList<Category>() {{
                add(new Category("Hobby"));
                add(new Category("Work"));
                add(new Category("Sport"));
                add(new Category("Cooking"));
                add(new Category("Passion"));
                add(new Category("Reading"));
            }};

    private CategoryDialogListener mListener;
    private DialogFragment mDialog;

    public CategoryDialogAdapter(DialogFragment mDialog, CategoryDialogListener mListener) {
        this.mDialog = mDialog;
        this.mListener = mListener;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_dialog_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Category suggested = mDialogSuggested.get(position);
        holder.dialogItemText.setText(suggested.getCategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNewCategoryCreated(suggested);
                mDialogSuggested.remove(position);
                notifyDataSetChanged();
                mDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDialogSuggested.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialog_item_text)
        TextView dialogItemText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
