package com.example.android.tranner.mainscreen.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.tranner.R;
import com.example.android.tranner.mainscreen.data.Category;
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


    private WebImageDialog mDialog;
    private List<String> mUrls;
    private Category mCategory;

    private WebImageDialogAdapterListener mListener;

    public WebImageDialogAdapter(WebImageDialog mDialog, List<String> mUrls, Category category) {
        this.mDialog = mDialog;
        this.mUrls = mUrls;
        this.mCategory = category;
    }

    public void setListener(WebImageDialogAdapterListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.web_dialog_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String url = mUrls.get(position);
        Picasso.with(mDialog.getContext()).load(url).into(holder.webDialogImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategory.setImageUrl(url);
                mListener.onPickImageUrl(mCategory);
                mDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
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
