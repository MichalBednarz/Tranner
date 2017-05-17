package com.example.android.tranner.categoryscreen.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.tranner.R;
import com.example.android.tranner.categoryscreen.ReloadEvent;
import com.example.android.tranner.categoryscreen.activities.CategoryActivity;
import com.example.android.tranner.categoryscreen.adapters.FragmentLayoutAdapter;
import com.example.android.tranner.categoryscreen.listeners.OnAddItemDialogListener;
import com.example.android.tranner.categoryscreen.listeners.OnFamiliarFragmentListener;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.dagger.components.DaggerFragmentFamiliarComponent;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.data.providers.itemprovider.FamiliarItemPresenter;
import com.example.android.tranner.data.providers.itemprovider.ItemContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.android.tranner.data.ConstantKeys.ITEM_TAB_FAMILIAR;
import static com.example.android.tranner.data.ConstantKeys.ITEM_TAB_NEW;
import static com.example.android.tranner.data.ConstantKeys.RELOAD_EVENT;
import static java.util.Collections.EMPTY_LIST;

public class FragmentFamiliar extends Fragment implements OnListItemClickListener, OnAddItemDialogListener, ItemContract.FamiliarView {

    public static final String ARG_ID = "arg_id";

    @BindView(R.id.recyclerview_familiar_fragment)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_familiar_fab)
    FloatingActionButton mFab;

    Unbinder unbinder;

    @Inject
    FamiliarItemPresenter mFamiliarItemPresenter;

    private OnFamiliarFragmentListener mListener;
    private FragmentLayoutAdapter mAdapter;
    private int mParentId;

    public FragmentFamiliar() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_new using the provided parameters.
     *
     * @return A new instance of fragment_new FragmentFamiliar.
     */
    public static FragmentFamiliar newInstance(int parentId) {
        FragmentFamiliar fragment = new FragmentFamiliar();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, parentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    public void onEvent(ReloadEvent event) {
        if (event.getMessage().equals(RELOAD_EVENT)) {
            mFamiliarItemPresenter.loadFamiliarItems(mParentId);
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

        mFamiliarItemPresenter.attachFamiliarView(this);

        if (!getArguments().isEmpty()) {
            mParentId = getArguments().getInt(ARG_ID);
            mFamiliarItemPresenter.loadFamiliarItems(mParentId);
            setUpRecyclerView();
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(String.valueOf(R.string.error))
                    .show();
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mFamiliarItemPresenter.detachFamiliarView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter(this);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fragment_familiar_fab)
    public void onFabClicked() {
        openAddDialog();
    }

    /**
     * This method opens dialog aimed to add new item when user click floating action button.
     */
    private void openAddDialog() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(getString(R.string.add_familiar))
                .setCancelText(getString(R.string.go_back))
                .setCancelClickListener(Dialog::dismiss)
                .setConfirmText(getString(R.string.lets_add))
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();

                    openTitleDialog();
                })
                .show();
    }

    /**
     * This method opens dialog only if item add was confirmed by user in dialog
     * from openAddDialog method.
     */
    private void openTitleDialog() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.familiar_title)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, null, (dialog, input) -> {
                    CategoryItem item =
                            new CategoryItem(input.toString(), mParentId, ITEM_TAB_FAMILIAR);
                    mFamiliarItemPresenter.addFamiliarItem(item);
                    mFamiliarItemPresenter.loadFamiliarItems(mParentId);
                })
                .widgetColor(Color.CYAN)
                .positiveColor(Color.CYAN)
                .show();
    }

    /**
     * OnListItemClickListener implementation.
     */

    @Override
    public void onListDeleteItem(CategoryItem item, int position) {
        mFamiliarItemPresenter.deleteFamiliarItem(item);
    }

    @Override
    public void onListMoveItem(CategoryItem item) {
        item.setTab(ITEM_TAB_NEW);
        mFamiliarItemPresenter.updateFamiliarItem(item);

        ReloadEvent event = new ReloadEvent();
        event.setMessage(RELOAD_EVENT);
        EventBus.getDefault().post(event);
    }


    @Override
    public void onListOpenItem(CategoryItem item) {
        mListener.onFamiliarItemOpened(item);
    }

    @Override
    public void onListUpdateItem(CategoryItem item) {
        mFamiliarItemPresenter.updateFamiliarItem(item);
    }

    /**
     * OnAddItemDialog implementation.
     */

    @Override
    public void onDialogAddItem(String name) {
        CategoryItem item = new CategoryItem(name, mParentId, ITEM_TAB_FAMILIAR);
        mFamiliarItemPresenter.addFamiliarItem(item);
    }

    /**
     * FamiliarItemPresenter implementation.
     */

    @Override
    public void onFamiliarItemLoaded(List<CategoryItem> familiarItemList) {
        mAdapter.updateItemList(familiarItemList);

        Toast.makeText(getActivity(), "Familiar items loaded from database.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNoFamiliarItemLoaded() {
        mAdapter.updateItemList(EMPTY_LIST);

        Toast.makeText(getActivity(), "No familiar items loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFamiliarItemLoadError() {

    }

    @Override
    public void onItemAdded() {
        mFamiliarItemPresenter.loadFamiliarItems(mParentId);
    }

    @Override
    public void onNoItemAdded() {

    }

    @Override
    public void onItemAddedError() {

    }

    @Override
    public void onItemDeleted() {
        mFamiliarItemPresenter.loadFamiliarItems(mParentId);
    }

    @Override
    public void onNoItemDeleted() {

    }

    @Override
    public void onItemDeletedError() {

    }

    @Override
    public void onItemUpdated() {
        mFamiliarItemPresenter.loadFamiliarItems(mParentId);
    }

    @Override
    public void onNoItemUpdated() {

    }

    @Override
    public void onItemUpdatedError() {

    }
}
