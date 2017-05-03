package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentLayoutAdapter extends RecyclerView.Adapter<FragmentLayoutAdapter.ViewHolder> {

    private OnListItemClickListener mListener;
    private List<CategoryItem> mItemList;
    private Context mContext;

    public FragmentLayoutAdapter(Fragment fragment, List<CategoryItem> itemList) {
        this.mItemList = itemList;
        this.mContext = fragment.getContext();
        this.mListener = (OnListItemClickListener) fragment;
    }

    public void setListener(OnListItemClickListener listener) {
        mListener = listener;
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
        holder.mButton.setOnClickListener(v -> showPopupMenu(v, item, holder));
    }

    private void showPopupMenu(View view, final CategoryItem item, final FragmentLayoutAdapter.ViewHolder holder) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Delete item?")
                            .setNegativeButton("CANCEL", (dialog, which) -> {
                            }).setPositiveButton("DELETE", (dialog, which) -> mListener.onListDeleteItem(item)).create().show();
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
