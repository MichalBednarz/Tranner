package com.example.android.tranner.mainscreen.adapters;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.mainscreen.listeners.CategoryDialogAdapterListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.tranner.data.DialogSuggestedList.SUGGESTED_CATEGORIES;

/**
 * Created by Michał on 2017-04-10.
 */

public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogAdapter.ViewHolder> {


    public int mSelected = -1;

    private CategoryDialogAdapterListener mListener;
    private DialogFragment mDialog;

    public CategoryDialogAdapter(DialogFragment mDialog) {
        this.mDialog = mDialog;
        this.mListener = (CategoryDialogAdapterListener) mDialog;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_dialog_item, parent, false);

        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Category suggested = SUGGESTED_CATEGORIES.get(position);
        holder.dialogItemText.setText(suggested.getCategory());
        holder.itemView.setOnClickListener(v -> {
            mSelected = position;
            notifyDataSetChanged();
            mListener.onCategorySelected(suggested);
        });

        ColorDrawable[] color = {
                new ColorDrawable(Color.WHITE),
                new ColorDrawable(mDialog.getResources().getColor(R.color.colorPink))
        };
        TransitionDrawable trans = new TransitionDrawable(color);
        holder.itemView.setBackground(trans);

        if (mSelected == position) {
            trans.startTransition(1500);
        } else {
            trans.resetTransition();
        }
    }

    @Override
    public int getItemCount() {
        return SUGGESTED_CATEGORIES.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dialog_item_text)
        TextView dialogItemText;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
