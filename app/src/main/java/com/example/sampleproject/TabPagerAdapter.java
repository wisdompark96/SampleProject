package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.google.android.material.picker.Month;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

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
                MonthFragment monthFragment = MonthFragment.newInstance();
                return monthFragment;
            case 1:
                WeeklyFragment weeklyFragment = WeeklyFragment.newInstance();
                return weeklyFragment;
            case 2:
                DailyFragment dailyFragment = DailyFragment.newInstance();
                return dailyFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
