package com.example.mrfit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.crunch.CrunchTutorialActivity;
import com.example.arm.ArmTutorialActivity;
import com.example.sixinch.SixInchTutorialActivity;



public class ExListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exlist);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Button overhpBtn = (Button)findViewById(R.id.overheadpressbutton);
        Button crunchBtn = (Button)findViewById(R.id.crunchbutton);
        Button sixinchBtn = (Button)findViewById(R.id.sixinchbutton);




        overhpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global) getApplicationContext()).setExType("1");
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity1 = new Intent(ExListActivity.this, ArmTutorialActivity.class);
                startActivity(intentSubActivity1);
            }
        });


        crunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global) getApplicationContext()).setExType("2");
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity1 = new Intent(ExListActivity.this, CrunchTutorialActivity.class);
                startActivity(intentSubActivity1);
            }
        });

        sixinchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Global) getApplicationContext()).setExType("3");
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity1 = new Intent(ExListActivity.this, SixInchTutorialActivity.class);
                startActivity(intentSubActivity1);
            }
        });



    }

}
