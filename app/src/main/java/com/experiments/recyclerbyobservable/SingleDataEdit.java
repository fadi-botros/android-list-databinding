package com.experiments.recyclerbyobservable;


import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ListenerUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.experiments.recyclerbyobservable.databinding.FragmentSingleDataEditBinding;
import com.experiments.recyclerbyobservable.databinding.Try1DataView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleDataEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleDataEdit extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    // Not sure whether make it private, protected or public
    // But I think FULLY ISOLATE MODEL FROM VIEW
    protected void setModel(Try1Model model) {
        setViewModel(new Try1ViewModel(model));
    }

    public void setViewModel(Try1ViewModel viewModel) {
        FragmentSingleDataEditBinding binding = DataBindingUtil.getBinding(getView());
        binding.setVariable(BR.try1ViewModel, viewModel);
        binding.executePendingBindings();
    }


    public SingleDataEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param model The model to bind to
     * @return A new instance of fragment SingleDataEdit.
     */
    public static SingleDataEdit newInstance(Try1Model model /*String param1, String param2*/) {
        SingleDataEdit fragment = new SingleDataEdit();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return DataBindingUtil.inflate(inflater,
                R.layout.fragment_single_data_edit,
                container,
                false).getRoot();
    }



}
