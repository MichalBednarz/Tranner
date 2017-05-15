package com.example.android.tranner.mainscreen.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Michał on 2017-04-09.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;
    private MainActivityAdapterListener mListener;

    public MainActivityAdapter(Context mContext, List<Category> mCategoryList) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
        this.mListener = (MainActivityAdapterListener) mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Category category = mCategoryList.get(position);
        holder.textView.setText(category.getTitle());
        if (category.getImageUrl() != null && !category.getImageUrl().equals("")) {
            Picasso.with(mContext).load(category.getImageUrl()).into(holder.mainItemImage);
        }
        holder.mainItemButton.setOnClickListener(view -> showPopupMenu(view, category, holder, position));

        holder.itemView.setOnClickListener(v -> {
            mListener.onCategoryOpened(category);
        });

        setItemBackdrop(holder, position);
    }

    /**
     * Subsidiary method invoked in onBindViewHolder responsible for setting three different colors
     * to different items backdrops.
     *
     * @param holder
     * @param position
     */
    private void setItemBackdrop(ViewHolder holder, int position) {

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = mContext.getTheme();

        if (position % 3 == 0) {
            theme.resolveAttribute(R.attr.item_one, typedValue, true);
            @ColorInt int color = typedValue.data;
            holder.itemView.setBackgroundColor(color);
        } else if (position % 2 == 0) {
            theme.resolveAttribute(R.attr.item_two, typedValue, true);
            @ColorInt int color = typedValue.data;
            holder.itemView.setBackgroundColor(color);
        } else {
            theme.resolveAttribute(R.attr.item_three, typedValue, true);
            @ColorInt int color = typedValue.data;
            holder.itemView.setBackgroundColor(color);
        }
    }

    /**
     * Subsidiary method invoked in onBindViewHolder on mainItemButton click responsible
     * for displaying menu and handling each menu item click.
     *
     * @param view
     * @param category
     * @param holder
     * @param position
     */
    private void showPopupMenu(View view, final Category category, final ViewHolder holder, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main_item, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(String.valueOf(R.string.delete_title))
                            .setContentText(String.valueOf(R.string.delete_content))
                            .setCancelText(String.valueOf(R.string.delete_cancel))
                            .setConfirmText(String.valueOf(R.string.delete_confirm))
                            .showCancelButton(true)
                            .setCancelClickListener(sDialog -> sDialog.cancel())
                            .setConfirmClickListener(sDialog -> {
                                mListener.onCategoryDeleted(category, position);
                                sDialog.cancel();
                            })
                            .show();
                    return true;
                case R.id.item_backdrop:
                    mListener.onChangeBackdropClicked(category, position);
                    return true;
            }
            return false;
        });
        popup.show();
    }

    /**
     * This method performs all necessary operations to update list of data smoothly.
     *
     * @param newCategories
     */
    public void updateItems(List<Category> newCategories) {
        mCategoryList.clear();
        mCategoryList.addAll(newCategories);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_item_text)
        TextView textView;
        @BindView(R.id.main_item_image)
        ImageView mainItemImage;
        @BindView(R.id.main_item_button)
        ImageButton mainItemButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
