package com.example.sampleproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.sampleproject.databinding.FragmentWeeklyBinding;

import java.util.ArrayList;
import java.util.List;

public class WeeklyFragment extends Fragment {

    FragmentWeeklyBinding binding;

    public static WeeklyFragment newInstance(){
        Bundle args = new Bundle();

        WeeklyFragment weeklyFragment = new WeeklyFragment();
        weeklyFragment.setArguments(args);
        return weeklyFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weekly, container, false);

        binding.weekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                List<WeekViewEvent> list = new ArrayList<>();
                return list;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
