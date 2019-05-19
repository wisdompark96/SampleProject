package com.example.sampleproject.database;

import androidx.lifecycle.LiveData;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmLiveData<T> extends LiveData<RealmResults<ScheduleVO>> {

    private RealmResults<ScheduleVO> results;

    private final RealmChangeListener<RealmResults<ScheduleVO>> listener = new RealmChangeListener<RealmResults<ScheduleVO>>() {
        @Override
        public void onChange(RealmResults<ScheduleVO> results) {
            setValue(results);
        }
    };

    public RealmLiveData(RealmResults<ScheduleVO> realmResults) {
        results = realmResults;
        setValue(realmResults);
    }


    @Override
    protected void onActive() {
        results.addChangeListener(listener);
    }

    @Override
    protected void onInactive() {
        results.removeChangeListener(listener);
    }
}
