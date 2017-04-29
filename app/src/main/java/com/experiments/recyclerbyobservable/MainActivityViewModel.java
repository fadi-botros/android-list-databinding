package com.experiments.recyclerbyobservable;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 4/29/17.
 */

public class MainActivityViewModel extends BaseObservable {
    public static LayoutInflater layoutInflater;

    public static LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public static void setLayoutInflater(LayoutInflater layoutInflater) {
        MainActivityViewModel.layoutInflater = layoutInflater;
    }

    private ObservableList<Try1Model> mainData = new ObservableArrayList<>();
    private Try1ViewModel currentEditing = new Try1ViewModel(new Try1Model());

    // The list ViewModel s, they are only views, they don't store data themselves.
    private ListViewModel<Try1ViewModel> mainList = new Try1ListViewModel(mainData);
    private ListViewModel<Try1ViewModel> filteredList = new Try1FilteringViewModel(mainData);

    // The search text field data
    private String autocompleteText = "";

    public ObservableList<Try1Model> getMainData() {
        return mainData;
    }

    public ListViewModel<Try1ViewModel> getMainList() {
        return mainList;
    }

    public ListViewModel<Try1ViewModel> getFilteredList() {
        return filteredList;
    }

    public Try1ViewModel getCurrentEditing() {
        return currentEditing;
    }

    @Bindable
    public void setCurrentEditing(Try1ViewModel currentEditing) {
        this.currentEditing = currentEditing;
        notifyPropertyChanged(BR.currentEditing);
    }

    public String getAutocompleteText() {
        return autocompleteText;
    }

    @Bindable
    public void setAutocompleteText(String autocompleteText) {
        this.autocompleteText = autocompleteText;
        notifyPropertyChanged(BR.autocompleteText);
    }

    public void addNewOne(View view) {
        Log.e("NewOne", "A");
        // A new model
        Try1Model model = new Try1Model();
        // Add this model to the backend list
        this.mainData.add(model);
        // Make a new ViewModel to be associated with the fragment
        setCurrentEditing(new Try1ViewModel(model));
    }

    @BindingAdapter("app:customListAttribute")
    public static void setListAttribute(RecyclerView recycler, ListViewModel<Try1ViewModel> listViewModel) {
        recycler.setLayoutManager(new LinearLayoutManager(recycler.getRootView().getContext(),
                LinearLayoutManager.VERTICAL,
                false));
        Try1ObserverRecyclerView adapter = new Try1ObserverRecyclerView(getLayoutInflater());
        adapter.setBackendList(listViewModel);
        recycler.setAdapter(adapter);
    }
}
