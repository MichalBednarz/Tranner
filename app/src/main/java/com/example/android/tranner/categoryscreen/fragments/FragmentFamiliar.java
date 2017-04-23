package com.example.android.tranner.categoryscreen.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.adapters.FragmentLayoutAdapter;
import com.example.android.tranner.categoryscreen.adapters.OnCategoryItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment_new must implement the
 * {@link FragmentFamiliar.OnFamiliarFragmentListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFamiliar#newInstance} factory method to
 * create an instance of this fragment_new.
 */
public class FragmentFamiliar extends Fragment implements OnCategoryItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment_new initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFamiliarFragmentListener mListener;
    private RecyclerView mRecyclerView;
    private FragmentLayoutAdapter mAdapter;

    public FragmentFamiliar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_new using the provided parameters.
     *
     * @return A new instance of fragment_new FragmentFamiliar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFamiliar newInstance() {
        FragmentFamiliar fragment = new FragmentFamiliar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_familiar, container, false);
        setUpRecyclerView(view);

        return view;
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_familiar_fragment);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter();
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onFabClicked(View v) {
        switch(v.getId()) {
            case R.id.fragment_familiar_fab:

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFamiliarFragmentListener) {
            mListener = (OnFamiliarFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFamiliarFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCategoryItemOpened() {
        mListener.onFamiliarItemOpened();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment_new to allow an interaction in this fragment_new to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFamiliarFragmentListener {
        void onFamiliarItemAdded(Uri uri);
        void onFamiliarItemOpened();
    }
}
