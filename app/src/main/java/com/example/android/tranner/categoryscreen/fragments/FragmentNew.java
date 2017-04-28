package com.example.android.tranner.categoryscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.adapters.FragmentLayoutAdapter;
import com.example.android.tranner.categoryscreen.adapters.OnCategoryItemClickListener;
import com.example.android.tranner.categoryscreen.dialogs.NewDialog;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class FragmentNew extends Fragment implements OnCategoryItemClickListener {

    private OnNewFragmentListener mFragmentListener;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private FragmentLayoutAdapter mAdapter;
    private NewDialog mNewDialog;
    private List<CategoryItem> mItemList;

    public FragmentNew() {
        // Required empty public constructor
        mItemList = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_new using the provided parameters.
     *
     * @return A new instance of fragment_new FragmentNew.
     */
    public static FragmentNew newInstance() {
        FragmentNew fragment = new FragmentNew();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public void updateItemList(List<CategoryItem> itemList) {
        mItemList.clear();
        mItemList.addAll(itemList);
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewFragmentListener) {
            mFragmentListener = (OnNewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        setUpRecyclerView(view);

        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentListener = null;
    }


    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_new_fragment);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter(mItemList);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onFabClicked(View v) {
        switch (v.getId()) {
            case R.id.fragment_new_fab:
                FragmentManager manager = getActivity().getSupportFragmentManager();
                mNewDialog = NewDialog.newInstance();
                mNewDialog.show(manager, "category_dialog");
                break;
        }
    }

    @Override
    public void onCategoryItemOpened() {
        mFragmentListener.onNewItemOpened();
    }

}
