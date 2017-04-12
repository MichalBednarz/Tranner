package com.example.android.tranner.mainscreen.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        holder.mainItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, category, holder);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v, category, holder);
                return true;
            }
        });


        //provides item animation respectively for down scroll and up scrool
        if(position > mPreviousPosition) {
            AnimationUtil.animate(holder, true);
        }else {
            AnimationUtil.animate(holder, false);
        }
        mPreviousPosition = position;

        //
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
        if (position % 3 == 0) {
            holder.itemView.setBackgroundColor(Color.GREEN);
        } else if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            holder.itemView.setBackgroundColor(Color.MAGENTA);
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
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Delete category?")
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mListener.onCategoryDeleted(category);
                                    }
                                }).create().show();
                        return true;
                    case R.id.item_backdrop:
                        mListener.onChangeBackdropClicked(category);
                        return true;
                }
                return false;
            }
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
