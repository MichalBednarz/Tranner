package com.example.android.tranner.categoryscreen.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.categoryscreen.adapters.FragmentLayoutAdapter;
import com.example.android.tranner.categoryscreen.dialogs.AddItemDialog;
import com.example.android.tranner.categoryscreen.listeners.OnAddItemDialogListener;
import com.example.android.tranner.categoryscreen.listeners.OnFamiliarFragmentListener;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.dagger.components.DaggerFragmentFamiliarComponent;
import com.example.android.tranner.data.providers.categoryprovider.Category;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.data.providers.itemprovider.FamiliarItemPresenter;
import com.example.android.tranner.data.providers.itemprovider.ItemContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.android.tranner.data.ConstantKeys.*;
import static com.example.android.tranner.data.ConstantKeys.DIALOG_TITLE_FAMILIAR;

public class FragmentFamiliar extends Fragment implements OnListItemClickListener, OnAddItemDialogListener, ItemContract.FamiliarView {

    @BindView(R.id.recyclerview_familiar_fragment)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_familiar_fab)
    FloatingActionButton mFab;

    Unbinder unbinder;

    @Inject
    FamiliarItemPresenter mFamiliarItemPresenter;

    private OnFamiliarFragmentListener mListener;
    private FragmentLayoutAdapter mAdapter;
    private List<CategoryItem> mItemList = new ArrayList<>();
    private Category mParentCategory;
    private AddItemDialog mAddDialog;

    public FragmentFamiliar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_new using the provided parameters.
     *
     * @return A new instance of fragment_new FragmentFamiliar.
     */
    public static FragmentFamiliar newInstance() {
        FragmentFamiliar fragment = new FragmentFamiliar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_familiar, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerFragmentFamiliarComponent.builder()
                .categoryActivityComponent(((CategoryActivity) getActivity()).getComponent())
                .build()
                .inject(this);

        mFamiliarItemPresenter.setView(this);

        mParentCategory = ((CategoryActivity) getActivity()).getParentCategory();

        mFamiliarItemPresenter.loadFamiliarItems(mParentCategory);

        setUpRecyclerView();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter(this, mItemList);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fragment_familiar_fab)
    public void onFabClicked() {
        FragmentManager manager = getChildFragmentManager();
        mAddDialog = AddItemDialog.newInstance(DIALOG_TITLE_FAMILIAR);
        mAddDialog.show(manager, "familiar_dialog");
    }

    /**
     * OnListItemClickListener implementation.
     */

    @Override
    public void onListDeleteItem(CategoryItem item) {
        mFamiliarItemPresenter.deleteFamiliarItem(item);
        mFamiliarItemPresenter.loadFamiliarItems(mParentCategory);
    }

    @Override
    public void onListMoveItem(CategoryItem item) {
        item.setTab(ITEM_TAB_NEW);
        mFamiliarItemPresenter.updateFamiliarItem(item);
        mFamiliarItemPresenter.loadFamiliarItems(mParentCategory);
    }


    @Override
    public void onListOpenItem(CategoryItem item) {
        mListener.onFamiliarItemOpened();
    }

    /**
     * OnAddItemDialog implementation.
     */

    @Override
    public void onDialogAddItem(String name) {
        CategoryItem item = new CategoryItem(name, mParentCategory.getId(), ITEM_TAB_FAMILIAR);
        mFamiliarItemPresenter.addFamiliarItem(item);
        mFamiliarItemPresenter.loadFamiliarItems(mParentCategory);
    }

    /**
     * FamiliarItemPresenter implementation.
     */

    @Override
    public void onFamiliarItemLoaded(List<CategoryItem> familiarItemList) {
        mItemList.clear();
        mItemList.addAll(familiarItemList);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "Familiar items loaded from database.", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onNoFamiliarItemLoaded() {
        mItemList.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "No familiar items loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFamiliarItemLoadError() {

    }

    @Override
    public void onItemAdded() {

    }

    @Override
    public void onNoItemAdded() {

    }

    @Override
    public void onItemAddedError() {

    }

    @Override
    public void onItemDeleted() {

    }

    @Override
    public void onNoItemDeleted() {

    }

    @Override
    public void onItemDeletedError() {

    }

    @Override
    public void onItemUpdated() {

    }

    @Override
    public void onNoItemUpdated() {

    }

    @Override
    public void onItemUpdatedError() {

    }
}
