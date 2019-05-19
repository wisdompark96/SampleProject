package com.example.sampleproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 1300);


    }
    private class splashHandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }
    @Override
    public void onBackPressed() {

    }
}
