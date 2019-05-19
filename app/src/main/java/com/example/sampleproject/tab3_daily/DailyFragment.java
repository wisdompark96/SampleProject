package com.example.sampleproject.tab3_daily;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.example.sampleproject.R;
import com.example.sampleproject.database.ScheduleVO;
import com.example.sampleproject.databinding.FragmentDailyBinding;
import com.example.sampleproject.tab1_month.MonthViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class DailyFragment extends Fragment {

    FragmentDailyBinding binding;
    MonthViewModel monthViewModel;
    int year;
    int month;
    static DailyFragment dailyFragment;
    public static DailyFragment newInstance(){
        Bundle args = new Bundle();

        if(dailyFragment == null)
            dailyFragment = new DailyFragment();
        dailyFragment.setArguments(args);
        return dailyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily, container, false);
        binding.dailyView.goToToday();
        binding.dailyView.setWeekViewLoader(new WeekViewLoader() {
            @Override
            public double toWeekViewPeriodIndex(Calendar instance) {
                return 0;
            }

            @Override
            public List<? extends WeekViewEvent> onLoad(int periodIndex) {
                return null;
            }
        });
        binding.dailyView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE M/dd", Locale.getDefault());
                    return sdf.format(date.getTime()).toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            }

            @Override
            public String interpretTime(int hour) {
                return "";
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Realm.init(getContext());

        monthViewModel = ViewModelProviders.of(this).get(MonthViewModel.class);

        binding.dailyView.setEventTextColor(Color.WHITE);

        binding.dailyView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override

            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                year = newYear;
                month = newMonth;
                Log.e("WeeklyFragment",newYear+","+newMonth);

                return  subScribeUI(year, month);
            }
        });
    }

    public ArrayList<WeekViewEvent> subScribeUI(int newYear, int newMonth){
        final ArrayList<WeekViewEvent> weekViewEvents = new ArrayList<>();
        monthViewModel.getSchedules(newYear, newMonth).observe(this, new Observer<List<ScheduleVO>>() {
            @Override
            public void onChanged(List<ScheduleVO> scheduleVOS) {
                for(ScheduleVO scheduleVO : scheduleVOS) {
                    WeekViewEvent weekViewEvent = new WeekViewEvent(scheduleVO.getId(),scheduleVO.getContent(),scheduleVO.getYear(),scheduleVO.getMonth()+1,scheduleVO.getDay(),scheduleVO.getHour(),0,scheduleVO.getYear(),scheduleVO.getMonth()+1,scheduleVO.getDay(),scheduleVO.getHour()+1,0);
                    if(!weekViewEvents.contains(scheduleVO.getId()))
                        weekViewEvents.add(weekViewEvent);
                }
            }
        });
        return weekViewEvents;
    }

    public void getResult(){
        subScribeUI(year, month);
    }
}
