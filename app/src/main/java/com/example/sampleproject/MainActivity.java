package com.example.sampleproject;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sampleproject.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    ActivityMainBinding binding;
    TabPagerAdapter tabPagerAdapter;
    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        setSupportActionBar(binding.toolbar);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Month"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Week"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Day"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(tabPagerAdapter);

        final SharedPreferences sharedPreferences = getSharedPreferences("position",MODE_PRIVATE);
        int position = sharedPreferences.getInt("index",-1);
        if(position == -1)
            binding.viewPager.setCurrentItem(0);
        else {
            binding.viewPager.setCurrentItem(position);
            TabLayout.Tab tab = binding.tabLayout.getTabAt(position);
            tab.select();
        }

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("index", binding.tabLayout.getSelectedTabPosition());
                editor.commit();
                binding.viewPager.setCurrentItem(binding.tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_schedule:
                Intent intent = new Intent(this, AddSchedule.class);
                startActivityForResult(intent, REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            Log.e("MainActiviy", binding.tabLayout.getSelectedTabPosition()+".");
            tabPagerAdapter.sendEvent(binding.tabLayout.getSelectedTabPosition());
        }
    }
}
