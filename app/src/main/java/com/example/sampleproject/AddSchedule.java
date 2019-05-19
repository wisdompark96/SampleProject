package com.example.sampleproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.sampleproject.database.ScheduleVO;
import com.example.sampleproject.databinding.ActivityAddScheduleBinding;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddSchedule extends AppCompatActivity {

    ActivityAddScheduleBinding mBinding;
    private Realm mRealm;
    private Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_schedule);
        mBinding.setActivity(this);
        mBinding.btnSave.setEnabled(false);

        mBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mBinding.etContent.getText().length() > 0 && mBinding.tvCalendar.getText().length() != 0){
                    mBinding.btnSave.setEnabled(true);
                } else{
                    mBinding.btnSave.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_calendar:

                DatePickerDialog dialog = new DatePickerDialog(this);
                dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mBinding.tvCalendar.setText(year+"."+(month+1)+"."+dayOfMonth);
                        mDate = new Date(year, month, dayOfMonth);
                        if(mBinding.etContent.getText().length() != 0)
                            mBinding.btnSave.setEnabled(true);
                    }
                });
                if(!dialog.isShowing())
                    dialog.show();
                break;
            case R.id.btn_save:
                mRealm.beginTransaction();
                ScheduleVO scheduleVO = mRealm.createObject(ScheduleVO.class, getNextKey());
                scheduleVO.setContent(mBinding.etContent.getText().toString());
                scheduleVO.setYear(mDate.getYear());
                scheduleVO.setMonth(mDate.getMonth());
                scheduleVO.setDay(mDate.getDate());
                scheduleVO.setHour(getNextHour());
                mRealm.commitTransaction();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_close:
                setResult(RESULT_CANCELED);
                finish();
        }
    }

    public int getNextKey() {
        try {
            Number number = mRealm.where(ScheduleVO.class).max("id");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int getNextHour(){
        try {
            Number number = mRealm.where(ScheduleVO.class).equalTo("year",mDate.getYear()).equalTo("month",mDate.getMonth()).equalTo("day",mDate.getDate()).max("hour");
            if (number != null) {
                return number.intValue() + 1;
            } else {
                return 0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }


}
