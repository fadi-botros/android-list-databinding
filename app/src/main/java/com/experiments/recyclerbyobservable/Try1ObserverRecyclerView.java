package com.experiments.recyclerbyobservable;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import com.experiments.recyclerbyobservable.BR;

/**
 *  Date: 4/28/17.
 */

/**
 * An example implementation for the abstract adapter
 * Used ListViewModel to make it usable in the two cases: the full data, and the filtered data (Both
 *    implement this interface)
 */
public class Try1ObserverRecyclerView extends ObserverRecyclerViewAdapter<Try1ViewModel> {
    public Try1ObserverRecyclerView(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public int getResourceId() {
        return R.layout.layout_single;
    }

    @Override
    public int getVariableId() {
        return BR.try1ViewModel;
    }

    @Override
    public Object getViewModelByPosition(int position) {
        return getBackendList().getViewModelForPosition(position);
    }
}
