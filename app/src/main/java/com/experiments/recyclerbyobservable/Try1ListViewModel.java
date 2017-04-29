package com.experiments.recyclerbyobservable;

import android.databinding.ListChangeRegistry;
import android.databinding.ObservableList;
import android.widget.Adapter;

/**
 * 4/27/17.
 */

public class Try1ListViewModel extends ObservableList.OnListChangedCallback implements ListViewModel<Try1ViewModel> {

    private final ListChangeRegistry listChangeRegistry = new ListChangeRegistry();
    private final ObservableList<Try1Model> backend;



    public Try1ListViewModel(ObservableList<Try1Model> backend) {
        this.backend = backend;
        this.backend.addOnListChangedCallback(this);
    }

    @Override
    public void addOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback) {
        listChangeRegistry.add(listChangedCallback);
    }

    @Override
    public void removeOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback) {
        listChangeRegistry.remove(listChangedCallback);
    }

    // Just delegate the listener
    // This is a simple List View Model, all changes in the list is delegated to the view through
    //    this ViewModel.
    // If you want a filtered list (or even more complicated a sorted one), you should make some
    //    calculations to map the model indices to the ViewModel indices.
    @Override
    public void onChanged(ObservableList observableList) {
        listChangeRegistry.notifyChanged(null);
    }

    @Override
    public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
        listChangeRegistry.notifyChanged(null, i, i1);
    }

    @Override
    public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
        listChangeRegistry.notifyInserted(null, i, i1);
    }

    @Override
    public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
        listChangeRegistry.notifyMoved(null, i, i1, i2);
    }

    @Override
    public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
        listChangeRegistry.notifyRemoved(null, i, i1);
    }

    @Override
    public Try1ViewModel getViewModelForPosition(int position) {
        return new Try1ViewModel(backend.get(position));
    }

    public int size() {
        return backend.size();
    }

    /**
     * Adds item to the model THROUGH THIS CLASS
     * The concept of MVVM is to isolate Models from Views
     * As a rule taught by Web Developers, "NEVER TRUST THE CLIENT"
     *
     * Validation may happen here. You may validate in model (the main and common validations).
     * In the ViewModel you can validate what is specific to this view
     *
     */
    public void addItem(String name, String address, String third, String fourth) {
        Try1Model model = new Try1Model();
        model.setName(name);
        model.setAddress(address);
        model.setThird(third);
        model.setFourth(fourth);
        backend.add(model);
    }
}
