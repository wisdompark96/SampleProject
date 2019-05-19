package com.example.sampleproject.tab2_weekly;

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
import com.example.sampleproject.R;
import com.example.sampleproject.database.ScheduleVO;
import com.example.sampleproject.databinding.FragmentWeeklyBinding;
import com.example.sampleproject.tab1_month.MonthViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class WeeklyFragment extends Fragment implements MonthLoader.MonthChangeListener {

    FragmentWeeklyBinding binding;
    MonthViewModel monthViewModel;
    int year;
    int month;
    static WeeklyFragment weeklyFragment;

    public static WeeklyFragment newInstance(){
        Bundle args = new Bundle();

        if(weeklyFragment == null)
            weeklyFragment = new WeeklyFragment();
        weeklyFragment.setArguments(args);
        return weeklyFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weekly, container, false);
        binding.weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
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

        binding.weekView.setEventTextColor(Color.WHITE);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH)+7);
        binding.weekView.goToDate(today);
        binding.weekView.setMonthChangeListener(this);

    }

    public ArrayList<WeekViewEvent> subScribeUI(int newYear, int newMonth){
        final ArrayList<WeekViewEvent> weekViewEvents = new ArrayList<>();
        monthViewModel.getSchedules(newYear, newMonth).observe(this, new Observer<List<ScheduleVO>>() {
            @Override
            public void onChanged(List<ScheduleVO> scheduleVOS) {
                Log.e("WeeklyFragment", scheduleVOS.toString());
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
        onMonthChange(year,month);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        year = newYear;
        month = newMonth;
        Log.e("Weekly", month+".");
        return  subScribeUI(year, month);
    }
}
