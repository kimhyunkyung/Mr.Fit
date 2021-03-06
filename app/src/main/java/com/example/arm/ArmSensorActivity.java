package com.example.arm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.mrfit.BaseActivity;
import com.example.mrfit.R;


public class ArmSensorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm_sensor);
        ImageView btnCallNext = (ImageView) findViewById(R.id.sensorImage);
        btnCallNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(ArmSensorActivity.this, ArmSensorscanActivity.class);
                startActivity(intentSubActivity);
            }
        });

    }

}
