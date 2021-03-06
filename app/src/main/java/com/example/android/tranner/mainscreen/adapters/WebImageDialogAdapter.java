package com.example.android.tranner.mainscreen.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.tranner.R;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.imageprovider.ImageHit;
import com.example.android.tranner.mainscreen.dialogs.WebImageDialog;
import com.example.android.tranner.mainscreen.listeners.WebImageDialogAdapterListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-04-10.
 */

public class WebImageDialogAdapter extends RecyclerView.Adapter<WebImageDialogAdapter.ViewHolder> {

    private DialogFragment mDialogFragment;
    private List<ImageHit> mImageList;
    private Category mCategory;
    private WebImageDialogAdapterListener mListener;

    public WebImageDialogAdapter(DialogFragment dialogFragment, List<ImageHit> imageList, Category category) {
        this.mDialogFragment = dialogFragment;
        this.mImageList = imageList;
        this.mCategory = category;
    }

    public void setListener(WebImageDialogAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_dialog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String imageURL = mImageList.get(position).getWebformatURL();
        Picasso.with(mDialogFragment.getContext()).load(imageURL).into(holder.webDialogImage);
        holder.itemView.setOnClickListener(v -> {
            mCategory.setImageUrl(imageURL);
            mListener.onPickImageUrl(mCategory);
            mDialogFragment.dismiss();
        });
    }

    /**
     * This method performs all necessary operations to update list of data smoothly.
     *
     * @param newCategories
     */
    public void updateItems(List<ImageHit> images) {
        mImageList.clear();
        mImageList.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.web_dialog_image)
        ImageView webDialogImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
