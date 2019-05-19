package com.example.sampleproject.database.util;

import com.example.sampleproject.database.RealmLiveData;
import com.example.sampleproject.database.ScheduleVO;

import io.realm.Realm;
import io.realm.RealmResults;

public class BaseDAO<T> {
    protected Realm mRealm;

    public BaseDAO(Realm mRealm){
        this.mRealm = mRealm;
    }

    protected RealmLiveData<ScheduleVO> asLiveData(RealmResults<ScheduleVO> data){
        return new RealmLiveData<>(data);
    }
}
