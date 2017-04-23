package com.example.android.tranner.categoryscreen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.android.tranner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michał on 2017-04-22.
 */

public class FragmentLayoutAdapter extends RecyclerView.Adapter<FragmentLayoutAdapter.ViewHolder> {


    private OnCategoryItemClickListener mListener;

    public FragmentLayoutAdapter() {
        super();
    }

    public void setListener(OnCategoryItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fragment_item_name)
        TextView mName;
        @BindView(R.id.fragment_item_description)
        TextView mDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public FragmentLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FragmentLayoutAdapter.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCategoryItemOpened();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
