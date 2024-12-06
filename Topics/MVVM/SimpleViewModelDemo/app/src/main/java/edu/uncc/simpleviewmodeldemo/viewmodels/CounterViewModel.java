package edu.uncc.simpleviewmodeldemo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CounterViewModel extends AndroidViewModel {
    MutableLiveData<Integer> countLiveData;
    public CounterViewModel(@NonNull Application application) {
        super(application);
        countLiveData = new MutableLiveData<>();
        countLiveData.setValue(1);
    }

    public MutableLiveData<Integer> getCountLiveData() {
        return countLiveData;
    }

    public void incrementCounter(){
        Integer count = countLiveData.getValue();
        countLiveData.setValue(count + 1);
    }

    public void decrementCounter(){
        Integer count = countLiveData.getValue();
        countLiveData.setValue(count - 1);
    }

}
