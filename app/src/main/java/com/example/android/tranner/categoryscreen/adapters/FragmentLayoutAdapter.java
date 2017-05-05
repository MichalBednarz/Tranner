package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
        holder.mName.setOnClickListener(v -> new MaterialDialog.Builder(mContext)
                .title("New TITLE")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, null, (dialog, input) -> {
                    item.setName(input.toString());
                    mListener.onListUpdateItem(item);
                })
                .widgetColor(Color.CYAN)
                .positiveColor(Color.CYAN)
                .show());
        holder.mDescription.setText(item.getDescription());
        holder.itemView.setOnClickListener(v -> mListener.onListOpenItem(item));
        holder.mButton.setOnClickListener(v -> showPopupMenu(v, item, holder));

    }

    private void showPopupMenu(View view, final CategoryItem item, final FragmentLayoutAdapter.ViewHolder holder) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_fragment_item, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_delete:
                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this file!")
                            .setCancelText("No, cancel plx!")
                            .setConfirmText("Yes, delete it!")
                            .showCancelButton(true)
                            .setCancelClickListener(SweetAlertDialog::cancel)
                            .setConfirmClickListener(sDialog -> {
                                mListener.onListDeleteItem(item);
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
