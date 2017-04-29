package com.experiments.recyclerbyobservable;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.experiments.recyclerbyobservable.BR;

import java.io.Serializable;

/**
 * Date: 4/22/17.
 */

/**
 * Model, this is where the application logic happens
 */
public class Try1Model extends BaseObservable implements Serializable {
    public static final long serialVersionUID = 3;
    private String name;
    private String address;
    private String third;
    private String fourth;

    public String getName() {
        return name;
    }

    @Bindable
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getAddress() {
        return address;
    }

    @Bindable
    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public String getThird() {
        return third;
    }

    @Bindable
    public void setThird(String third) {
        this.third = third;
        notifyPropertyChanged(BR.third);
    }

    public String getFourth() {
        return fourth;
    }

    @Bindable
    public void setFourth(String fourth) {
        this.fourth = fourth;
        notifyPropertyChanged(BR.fourth);
    }
}
