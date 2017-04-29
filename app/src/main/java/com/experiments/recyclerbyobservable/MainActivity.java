package com.experiments.recyclerbyobservable;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.experiments.recyclerbyobservable.databinding.MainActivityView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityViewModel.setLayoutInflater(getLayoutInflater());
        MainActivityView binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final MainActivityViewModel data = new MainActivityViewModel();
        // Listen to any change in the current editing
        data.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (i == BR.currentEditing) {
                    // On change, make the fragment edit the current one
                    ((SingleDataEdit) getSupportFragmentManager().findFragmentById(R.id.fragment))
                            .setViewModel(data.getCurrentEditing());
                }
            }
        });
        binding.setData(data);
    }
}
