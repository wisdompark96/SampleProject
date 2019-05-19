package com.example.sampleproject.tab1_month;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class ScheduleDecorator implements DayViewDecorator {
    private HashSet<CalendarDay> dates;

    public ScheduleDecorator(){
        this.dates = new HashSet<>();
    }

    public void setDates(Collection<CalendarDay> dates){
        this.dates = new HashSet<>(dates);
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(Color.RED));
    }
}
