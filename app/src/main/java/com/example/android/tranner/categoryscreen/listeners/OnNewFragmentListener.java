package com.example.android.tranner.categoryscreen.listeners;

/**
 * Created by Michał on 2017-04-26.
 */

import com.example.android.tranner.data.providers.itemprovider.CategoryItem;

/**
 * This interface must be implemented by activities that contain this
 * fragment_new to allow an interaction in this fragment_new to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 */
public interface OnNewFragmentListener {

    void onNewItemOpened(CategoryItem item);


}
