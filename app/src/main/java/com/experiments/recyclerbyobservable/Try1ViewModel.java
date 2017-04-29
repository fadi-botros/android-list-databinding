package com.experiments.recyclerbyobservable;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.Observable;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.experiments.recyclerbyobservable.BR;
import com.experiments.recyclerbyobservable.Try1Model;

/**
 * Date: 4/22/17.
 */

/**
 * ViewModel, only forward the calls to the model, and forward the events from the
 *    model to the observers (views)
 */
public class Try1ViewModel extends BaseObservable {
    private final Try1Model model;

    public Try1ViewModel(Try1Model model) {
        this.model = model;
        this.model.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Try1ViewModel.this.notifyPropertyChanged(i);
            }
        });
    }

    @Bindable
    public String getName() {
        return model.getName();
    }


    @Bindable
    public void setName(String name) {
        model.setName(name);
    }

    @Bindable
    public String getAddress() {
        return model.getAddress();
    }

    @Bindable
    public void setAddress(String address) {
        model.setAddress(address);
    }

    @Bindable
    public String getThird() {
        return model.getThird();
    }

    @Bindable
    public void setThird(String third) {
        model.setThird(third);
    }

    @Bindable
    public String getFourth() {
        return model.getFourth();
    }

    @Bindable
    public void setFourth(String fourth) {
        model.setFourth(fourth);
    }

    /**
     * These two functions must have access to the EditText s to take the text from, and set the model
     *    properties accordingly
     *
     * @param view The view, from the onClick (through lambda in DataBinding)
     * @param name The name EditText, from the onClick (through lambda in DataBinding)
     * @param address The address EditText, from the onClick (through lambda in DataBinding)
     * @param third The third EditText, from the onClick (through lambda in DataBinding)
     * @param fourth The fourth EditText, from the onClick (through lambda in DataBinding)
     */
    public void addClicked(View view, EditText name, EditText address, EditText third, EditText fourth) {
        setName(name.getText().toString());
        setAddress(address.getText().toString());
        setThird(third.getText().toString());
        setFourth(fourth.getText().toString());
        Log.d("AddClicked", "on " + this.getName());
    }

    public void editClicked(View view, EditText name, EditText address, EditText third, EditText fourth) {
        setName(name.getText().toString());
        setAddress(address.getText().toString());
        setThird(third.getText().toString());
        setFourth(fourth.getText().toString());
        Log.d("EditClicked", "on " + this.getName());
    }

    public void deleteClicked(View view) {
        Log.d("DeleteClicked", "on " + this.getName());
    }

}
