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
import com.example.android.tranner.categoryscreen.dialogs.AddItemDialog;
import com.example.android.tranner.categoryscreen.listeners.OnAddItemDialogListener;
import com.example.android.tranner.categoryscreen.listeners.OnListItemClickListener;
import com.example.android.tranner.categoryscreen.listeners.OnNewFragmentListener;
import com.example.android.tranner.dagger.components.DaggerFragmentNewComponent;
import com.example.android.tranner.data.ConstantKeys;
import com.example.android.tranner.data.providers.itemprovider.CategoryItem;
import com.example.android.tranner.data.providers.itemprovider.ItemContract;
import com.example.android.tranner.data.providers.itemprovider.NewItemPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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

public class FragmentNew extends Fragment implements OnListItemClickListener, OnAddItemDialogListener,
        ItemContract.NewView {

    private static final String ARG_ID = "arg_id";
    private static final String TAG = "FragmentNew";
    private static final String TITLE_DIALOG = "New TITLE";
    private static final String ADD_DIALOG = "Add NEW";
    private static final String ADD_CANCEL = "Go back";
    private static final String ADD_APPROVE = "Lets add it!";
    private static final String ERROR_DIALOG = "Ups, something went wrong!";

    @BindView(R.id.recyclerview_new_fragment)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_new_fab)
    FloatingActionButton mFab;

    Unbinder unbinder;

    @Inject
    NewItemPresenter mNewItemPresenter;

    private OnNewFragmentListener mFragmentListener;
    private FragmentLayoutAdapter mAdapter;
    private AddItemDialog mAddItemDialog;
    private List<CategoryItem> mItemList = new ArrayList<>();
    private int mParentId;

    public FragmentNew() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment_new using the provided parameters.
     *
     * @return A new instance of fragment_new FragmentNew.
     */
    public static FragmentNew newInstance(int parentId) {
        FragmentNew fragment = new FragmentNew();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, parentId);
        fragment.setArguments(args);

        return fragment;
    }

    @Subscribe
    public void onEvent(ReloadEvent event) {
        if (event.getMessage().equals(RELOAD_EVENT)) {
            mNewItemPresenter.loadNewItems(mParentId);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerFragmentNewComponent.builder()
                .categoryActivityComponent(((CategoryActivity) getActivity()).getComponent())
                .build()
                .inject(this);

        mNewItemPresenter.attachNewView(this);

        if (!getArguments().isEmpty()) {
            mParentId = getArguments().getInt(ARG_ID);
            mNewItemPresenter.loadNewItems(mParentId);
            setUpRecyclerView();
        } else {
            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(ERROR_DIALOG)
                    .show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentListener = null;
        mNewItemPresenter.detachNewView();
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FragmentLayoutAdapter(this, mItemList);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fragment_new_fab)
    public void onFabClicked() {
        openAddDialog();
    }

    /**
     * This method opens dialog aimed to add new item when user click floating action button.
     */
    private void openAddDialog() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(ADD_DIALOG)
                .setCancelText(ADD_CANCEL)
                .setCancelClickListener(Dialog::dismiss)
                .setConfirmText(ADD_APPROVE)
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
                .title(TITLE_DIALOG)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, null, (dialog, input) -> {
                    CategoryItem item =
                            new CategoryItem(input.toString(), mParentId, ITEM_TAB_NEW);
                    mNewItemPresenter.addNewItem(item);
                    mNewItemPresenter.loadNewItems(mParentId);
                })
                .widgetColor(Color.CYAN)
                .positiveColor(Color.CYAN)
                .show();
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

    /**
     * OnListItemClickListener implementation
     */

    @Override
    public void onListOpenItem(CategoryItem item) {
        mFragmentListener.onNewItemOpened(item);
    }

    @Override
    public void onListDeleteItem(CategoryItem item) {
        mNewItemPresenter.deleteNewItem(item);
        mNewItemPresenter.loadNewItems(mParentId);
    }

    @Override
    public void onListMoveItem(CategoryItem item) {
        item.setTab(ConstantKeys.ITEM_TAB_FAMILIAR);

        mNewItemPresenter.updateNewItem(item);
        mNewItemPresenter.loadNewItems(mParentId);

        ReloadEvent event = new ReloadEvent();
        event.setMessage(RELOAD_EVENT);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onListUpdateItem(CategoryItem item) {
        mNewItemPresenter.updateNewItem(item);
        mNewItemPresenter.loadNewItems(mParentId);
    }

    /**
     * OnAddItemDialogListener implementation
     */

    @Override
    public void onDialogAddItem(String name) {
        CategoryItem item = new CategoryItem(name, mParentId, ConstantKeys.ITEM_TAB_NEW);

        mNewItemPresenter.addNewItem(item);
        mNewItemPresenter.loadNewItems(mParentId);
    }

    /**
     * NewItemPresenter implementation
     */

    @Override
    public void onNewItemLoaded(List<CategoryItem> newItemList) {
        mItemList.clear();
        mItemList.addAll(newItemList);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "New items loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoNewItemLoaded() {
        mItemList.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "No new items loaded from database.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewItemLoadError() {
        Toast.makeText(getActivity(), "New Item load error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAdded() {
        Toast.makeText(getActivity(), "Item added.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoItemAdded() {
        Toast.makeText(getActivity(), "No item added.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemAddedError() {
        Toast.makeText(getActivity(), "Item add error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDeleted() {
        Toast.makeText(getActivity(), "Item deleted.", Toast.LENGTH_SHORT).show();
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
