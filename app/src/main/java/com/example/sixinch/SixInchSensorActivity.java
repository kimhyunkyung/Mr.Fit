package com.example.sixinch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mrfit.BaseActivity;
import com.example.mrfit.R;
import com.example.mrfit.SelectUserActivity;

/**
 * Created by H on 2015-09-15.
 */
public class SixInchSensorActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixinch_sensor);
        ImageView btnCallNext = (ImageView) findViewById(R.id.sensorImage);
        btnCallNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(SixInchSensorActivity.this, SixInchSensorscanActivity.class);
                startActivity(intentSubActivity);
            }
        });

    }

}
