package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sampleproject.tab1_month.MonthFragment;
import com.example.sampleproject.tab2_weekly.WeeklyFragment;
import com.example.sampleproject.tab3_daily.DailyFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    MonthFragment monthFragment;
    WeeklyFragment weeklyFragment;
    DailyFragment dailyFragment;

    public TabPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Returning the current tabs
        switch (position) {
            case 0:
                monthFragment = MonthFragment.newInstance();
                return monthFragment;
            case 1:
                weeklyFragment = WeeklyFragment.newInstance();
                return weeklyFragment;
            case 2:
                dailyFragment = DailyFragment.newInstance();
                return dailyFragment;
            default:
                return null;
        }

    }

    public void sendEvent(int position){
        switch (position) {
            case 0:
                monthFragment.getResult();
                break;
            case 1:
                weeklyFragment.getResult();
                break;
            case 2:
                dailyFragment.getResult();
        }

    }


    @Override
    public int getCount() {
        return tabCount;
    }
}
