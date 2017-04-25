package com.example.android.tranner.categoryscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.adapters.FragmentLayoutAdapter;
import com.example.android.tranner.categoryscreen.adapters.OnCategoryItemClickListener;

public class FragmentNew extends Fragment implements OnCategoryItemClickListener {

    private OnNewFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private FragmentLayoutAdapter mAdapter;

    public FragmentNew() {
        // Required empty public constructor
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

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_new_fragment);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onFabClicked(View v) {
        switch (v.getId()) {
            case R.id.fragment_new_fab:
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewFragmentListener) {
            mListener = (OnNewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCategoryItemOpened() {
        mListener.onNewItemOpened();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment_new to allow an interaction in this fragment_new to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnNewFragmentListener {
        void onNewItemAdded();

        void onNewItemOpened();
    }
}
