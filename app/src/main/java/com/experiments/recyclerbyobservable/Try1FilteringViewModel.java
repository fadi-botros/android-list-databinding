package com.experiments.recyclerbyobservable;

/**
 * 4/27/17.
 */

import android.databinding.ListChangeRegistry;
import android.databinding.Observable;
import android.databinding.ObservableList;
import com.experiments.recyclerbyobservable.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * This tries to implement filtering in a simple way (only for demonstration).
 * Every change happens to the backend ObservableList or to any of its members, it remaps the
 *    indicies and store them.
 */
public class Try1FilteringViewModel extends
        ObservableList.OnListChangedCallback<ObservableList<Try1Model>>
        implements GeneralMapper, ListViewModel<Try1ViewModel> {

   /**
     * Integer range, A real implementation is already present in Android libraries but requires at
     *    least Android API 21.
     */
    public static class IntegerRange {
        Integer min;
        Integer max;

        public IntegerRange(Integer min, Integer max) {
            this.min = min;
            this.max = max;
            if ((null != min) && (null == max)) {
                // There is min but no max
                // So let max be = min
                max = min;
            } else if ((null == min) && (null != max)) {
                // The reverse
                min = max;
            }
        }

        public IntegerRange() {
        }
    }

    // When "name" property in any model changed, remap.
    public Observable.OnPropertyChangedCallback changeListener = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            // Only name property is listened to, we search only by name
            if (i == BR.name) {
                updateMappings();
            }
        }
    };

    private final ListChangeRegistry listChangeRegistry = new ListChangeRegistry();
    private final List<Integer> modelIndices = new ArrayList<>();
    private final ObservableList<Try1Model> backend;
    private String nameFilter = "";

    public Try1FilteringViewModel(ObservableList<Try1Model> backend) {
        this.backend = backend;
        this.backend.addOnListChangedCallback(this);
    }

    @Override
    public void onChanged(ObservableList<Try1Model> observableList) {
        listChangeRegistry.notifyChanged(null);
        updateMappings();
    }

    @Override
    public void onItemRangeChanged(ObservableList<Try1Model> observableList, int i, int i1) {
        IntegerRange range = mapModelRangeToView(i, i1);
        if (null == range) { return; }
        listChangeRegistry.notifyChanged(null, range.min, range.max);
        updateMappings();
    }

    @Override
    public void onItemRangeInserted(ObservableList<Try1Model> observableList, int i, int i1) {
        // Loop in all what are added and begin listening to it
        for (int j=i;j<(i + i1);j++) {
            observableList.get(j).addOnPropertyChangedCallback(changeListener);
        }
        // Apply addition to this view as well
        updateMappings();
        // Get what will be notified
        IntegerRange range = mapModelRangeToView(i, i1);
        // No notifications if no range (this ViewModel don't map to any models)
        if (null == range) { return; }
        // Notify all the views
        listChangeRegistry.notifyInserted(null, range.min, range.max);
    }

    @Override
    public void onItemRangeMoved(ObservableList<Try1Model> observableList, int i, int i1, int i2) {
        IntegerRange range = mapModelRangeToView(i, i1);
        if (null == range) { return; }
        listChangeRegistry.notifyMoved(null, range.min, range.max, i2);
        updateMappings();
    }

    @Override
    public void onItemRangeRemoved(ObservableList<Try1Model> observableList, int i, int i1) {
        // Loop in all what are removed and stop listening to it
        for (int j=i;j<(i + i1);j++) {
            observableList.get(j).removeOnPropertyChangedCallback(changeListener);
        }
        // Get what will be notified
        IntegerRange range = mapModelRangeToView(i, i1);
        // Apply removal to this view as well
        updateMappings();
        if (null == range) { return; }
        // Notify all the views
        listChangeRegistry.notifyRemoved(null, range.min, range.max);
    }

    @Override
    public void addOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback) {
        listChangeRegistry.add(listChangedCallback);
    }

    @Override
    public void removeOnListChangeCallback(ObservableList.OnListChangedCallback<ObservableList> listChangedCallback) {
        listChangeRegistry.remove(listChangedCallback);
    }

    @Override
    public Try1ViewModel getViewModelForPosition(int position) {
        // Get model index for position, then get the corresponding model entry.
        Integer modelIndex = (modelIndices.get(position));
        if (modelIndex == null) return null;
        return new Try1ViewModel(backend.get(modelIndex));
    }

    @Override
    public int size() {
        return modelIndices.size();
    }


    @Override
    public Integer mapModelToView(int modelIndex) {
        // Check the given index is in legal range, else return null
        return ((modelIndex >= 0) && (modelIndex < modelIndices.size())) ?
                modelIndices.get(modelIndex) : null;
    }

    @Override
    public Integer mapViewToModel(int viewIndex) {
        // Not implemented for now
        return null;
    }

    public IntegerRange mapModelRangeToView(int start, int count) {
        Integer end = start + count;

        Integer retStart = null;
        Integer retEnd = null;
        // Loop through the indices ascendingly till finding one that exists
        // This will be the start of the returned range
        for(int i=start;i<end;i++) {
            Integer tmp = mapModelToView(i);
            if (null != tmp) {
                retStart = tmp; break;
            }
        }
        // Looped through all indices and didn't find any map. There is no mapped models in the
        //    range, don't try to get the end of the returned range. Just return null.
        if (null == retStart) {
            return null;
        }
        for(int i=end - 1;i>=start;i++) {
            Integer tmp = mapModelToView(i);
            if (null != tmp) {
                retEnd = tmp; break;
            }
        }
        return new IntegerRange(retStart, retEnd);
    }

    public boolean applyFilter(Try1Model model) {
        return (null != model.getName()) ? model.getName().contains(nameFilter) : false;
    }

    public void updateMappings() {
        List<Integer> newIndices = new ArrayList<>();
        // Apply the filters
        for(int i=0;i<backend.size();i++) {
            if (applyFilter(backend.get(i))) { newIndices.add(i); }
        }
        synchronized (modelIndices) {
            // First, clear the old mapping list
            modelIndices.clear();
            // Then add all the new ones
            modelIndices.addAll(newIndices);
        }
    }
}
