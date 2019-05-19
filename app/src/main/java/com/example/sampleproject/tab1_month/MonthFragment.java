package com.example.sampleproject.tab1_month;

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

import com.example.sampleproject.R;
import com.example.sampleproject.database.ScheduleVO;
import com.example.sampleproject.databinding.FragmentMonthlyBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MonthFragment extends Fragment {

    FragmentMonthlyBinding binding;
    ArrayList<CalendarDay> calendarDays;
    ScheduleDecorator decorator;
    MonthViewModel monthViewModel;
    public static MonthFragment instance;
    int year;
    int month;

    public static MonthFragment newInstance(){
        Bundle args = new Bundle();

        if(instance == null)
            instance = new MonthFragment();
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monthly,container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarDays = new ArrayList<>();

        decorator = new ScheduleDecorator();
        binding.calendarMonth.addDecorator(decorator);
        binding.calendarMonth.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                subScribeUI(date.getYear(), date.getMonth());
            }
        });
        Realm.init(getContext());
        monthViewModel = ViewModelProviders.of(this).get(MonthViewModel.class);

        year = CalendarDay.today().getYear();
        month = CalendarDay.today().getMonth();
        subScribeUI(year,month);
    }

    public void subScribeUI(int year, int month){
        monthViewModel.getSchedules(year,month ).observe(this, new Observer<List<ScheduleVO>>() {
            @Override
            public void onChanged(List<ScheduleVO> scheduleVOS) {
                Log.e("MonthFragment", scheduleVOS.toString());

                for(ScheduleVO scheduleVO : scheduleVOS) {
                    CalendarDay calendarDay = CalendarDay.from(scheduleVO.getYear(), scheduleVO.getMonth(), scheduleVO.getDay());
                    calendarDays.add(calendarDay);
                    decorator.setDates(calendarDays);
                    binding.calendarMonth.invalidateDecorators();
                }
            }
        });
    }

    public void getResult(){
        subScribeUI(year, month);
    }

}
