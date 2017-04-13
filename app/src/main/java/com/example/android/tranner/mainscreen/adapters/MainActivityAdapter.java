package com.example.android.tranner.mainscreen.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.tranner.mainscreen.data.Category;
import com.example.android.tranner.mainscreen.listeners.MainActivityAdapterListener;
import com.example.android.tranner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-04-09.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;
    private MainActivityAdapterListener mListener;
    //field used for animation purpose to detect whether down or up scrooling
    private int mPreviousPosition = 0;

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
        holder.textView.setText(category.getCategory());
        if (category.getImageUrl() != null) {
            Picasso.with(mContext).load(category.getImageUrl()).into(holder.mainItemImage);
        }
        holder.mainItemButton.setOnClickListener(view -> showPopupMenu(view, category, holder));
        holder.itemView.setOnLongClickListener(v -> {
            showPopupMenu(v, category, holder);
            return true;
        });

        provideAnimation(holder, position);

        setItemBackdrop(holder, position);
    }

    /**
     * Provides item animation respectively for down scroll and up scrool.
     * @param holder
     * @param position
     */
    private void provideAnimation(ViewHolder holder, int position) {
        if(position > mPreviousPosition) {
            AnimationUtil.animate(holder, true);
        }else {
            AnimationUtil.animate(holder, false);
        }
        mPreviousPosition = position;
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
     * @param position
     * @param holder
     */
    private void showPopupMenu(View view, final Category category, final ViewHolder holder) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main_item, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete category?")
                            .setNegativeButton("CANCEL", (dialog, which) -> {
                            }).setPositiveButton("DELETE", (dialog, which) -> mListener.onCategoryDeleted(category)).create().show();
                    return true;
                case R.id.item_backdrop:
                    mListener.onChangeBackdropClicked(category);
                    return true;
            }
            return false;
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
