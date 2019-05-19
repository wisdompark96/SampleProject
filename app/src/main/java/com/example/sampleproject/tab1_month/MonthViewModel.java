package com.example.sampleproject.tab1_month;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sampleproject.database.RealmLiveData;
import com.example.sampleproject.database.ScheduleVO;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MonthViewModel extends AndroidViewModel {
    private MutableLiveData<List<ScheduleVO>> mSchedules;
    private Realm mRealm;

    public MonthViewModel(@NonNull Application application) {
        super(application);
    }

    public RealmLiveData<ScheduleVO> getSchedules(int year, int month){
        mRealm = Realm.getDefaultInstance();
        return asLiveData(mRealm.where(ScheduleVO.class).equalTo("year",year).equalTo("month", month).findAllAsync());
    }

    protected RealmLiveData<ScheduleVO> asLiveData(RealmResults<ScheduleVO> data){
        return new RealmLiveData<>(data);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


}
