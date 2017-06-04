package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentLayoutAdapter extends RecyclerView.Adapter<FragmentLayoutAdapter.ViewHolder> {

    private OnListItemClickListener mListener;
    private List<CategoryItem> mItemList = new ArrayList<>();
    private Context mContext;
    private DiffUtil.DiffResult mDiffResult;

    public FragmentLayoutAdapter(Fragment fragment) {
        this.mContext = fragment.getContext();
        this.mListener = (OnListItemClickListener) fragment;
    }

    public void setListener(OnListItemClickListener listener) {
        mListener = listener;
    }


    public void updateItemList(List<CategoryItem> newItemList) {
        mDiffResult = DiffUtil.calculateDiff(new ItemListDiffCallback(mItemList, newItemList), true);
        mItemList.clear();
        mItemList.addAll(newItemList);
        mDiffResult.dispatchUpdatesTo(this);
    }

    @Override
    public FragmentLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FragmentLayoutAdapter.ViewHolder holder, int position) {
        CategoryItem item = mItemList.get(position);
        holder.mName.setText(item.getName());
        holder.mDescription.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> mListener.onListOpenItem(item));
        holder.mButton.setOnClickListener(v -> showPopupMenu(v, item, holder, position));

        setItemBackdrop(holder, position);
    }

    /**
     * Subsidiary method invoked in onBindViewHolder responsible for setting three different colors
     * to different items backdrops.
     *
     * @param holder
     * @param position
     */
    private void setItemBackdrop(FragmentLayoutAdapter.ViewHolder holder, int position) {

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

    private void showPopupMenu(View view, final CategoryItem item, final FragmentLayoutAdapter.ViewHolder holder,final int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_fragment_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_delete:
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(mContext.getResources().getString(R.string.delete_title))
                            .setContentText(mContext.getResources().getString(R.string.delete_content))
                            .setCancelText(mContext.getResources().getString(R.string.delete_cancel))
                            .setConfirmText(mContext.getResources().getString(R.string.delete_confirm))
                            .showCancelButton(true)
                            .setCancelClickListener(SweetAlertDialog::cancel)
                            .setConfirmClickListener(sDialog -> {
                                mListener.onListDeleteItem(item, position);
                                sDialog.cancel();
                            })
                            .show();
                    return true;
                case R.id.item_move:
                    mListener.onListMoveItem(item);
                    return true;
            }
            return false;
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_item_name)
        TextView mName;
        @BindView(R.id.fragment_item_description)
        TextView mDescription;
        @BindView(R.id.fragment_item_button)
        ImageButton mButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
