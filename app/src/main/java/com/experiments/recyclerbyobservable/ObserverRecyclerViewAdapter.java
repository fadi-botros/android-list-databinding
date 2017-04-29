package com.experiments.recyclerbyobservable;

import android.databinding.CallbackRegistry;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ListChangeRegistry;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.experiments.recyclerbyobservable.databinding.Try1DataView;

/**
 * Date: 4/21/17.
 */

public abstract class ObserverRecyclerViewAdapter<U> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;

    public ObserverRecyclerViewAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    /**
     * Binds the adapter (adapter here assumes the view role), with an observable list (ViewModel)
     *    of ViewModel
     *
     * @param backendList ListViewModel (this contains the common interface between all list
     *                    "observers")
     */
    public void setBackendList(ListViewModel<U> backendList) {
        this.backendList = backendList;
        backendList.addOnListChangeCallback(new ObservableList.OnListChangedCallback<ObservableList>() {
            @Override
            public void onChanged(ObservableList us) {

            }

            @Override
            public void onItemRangeChanged(ObservableList us, int i, int i1) {
                notifyItemRangeChanged(i, i1);
            }

            @Override
            public void onItemRangeInserted(ObservableList us, int i, int i1) {
                notifyItemRangeInserted(i, i1);
            }

            @Override
            public void onItemRangeMoved(ObservableList us, int i, int i1, int i2) {
                notifyItemRangeRemoved(i, i1);
                notifyItemRangeInserted(i2, i1);
            }

            @Override
            public void onItemRangeRemoved(ObservableList us, int i, int i1) {
                notifyItemRangeRemoved(i, i1);
            }
        });
    }

    private ListViewModel<U> backendList;

    public ListViewModel<U> getBackendList() {
        return backendList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(DataBindingUtil.inflate(inflater,
                getResourceId(),
                parent,
                false).getRoot()) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        DataBindingUtil.findBinding(holder.itemView).
//        DataBindingUtil.bind(holder.itemView, getDatabindingComponent());
        Try1DataView binding = DataBindingUtil.getBinding(holder.itemView);
        binding.setVariable(getVariableId(),
                getViewModelByPosition(position)
                );
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return null != backendList ? backendList.size() : 0;
    }

    // Abstract methods
    @LayoutRes
    public abstract int getResourceId();

    public abstract int getVariableId();

    public abstract Object getViewModelByPosition(int position);
}
