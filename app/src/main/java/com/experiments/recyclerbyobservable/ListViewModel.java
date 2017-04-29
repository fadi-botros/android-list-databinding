package com.experiments.recyclerbyobservable;

import android.databinding.ObservableList;

/**
 * 4/26/17.
 */

/**
 * The list ViewModel, this must be only a bridge between ObservableList of Models and
 *   the RecyclerViewAdapter
 *
 * Here the implementors use an ObservableList#OnListChangedCallback ONLY FOR BRIDGING
 *   WITHOUT PASSING THE ORIGINAL ObservableList of Models to completely isolate views
 *   from Models
 */
public interface ListViewModel<T> {
    public void addOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback);
    public void removeOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback);

    public T getViewModelForPosition(int position);
    public int size();
}
