package com.example.arm;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.mrfit.BaseActivity;
import com.example.mrfit.R;


public class ArmTutorialActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm_tutorial);

        ImageButton button1 = (ImageButton) findViewById(R.id.playTutorialButton);
        ImageButton button2 = (ImageButton) findViewById(R.id.stopTutorialButton);
        Button button3 = (Button) findViewById(R.id.nextBtn);


        videoView = (VideoView) findViewById(R.id.tutorialVideoView);

        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.armraise_tutorial));

        videoView.setMediaController(new android.widget.MediaController(this));
        videoView.requestFocus();



        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                playVideo();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopVideo();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(ArmTutorialActivity.this, ArmSensorActivity.class);
                startActivity(intentSubActivity);
            }
        });

    }

    private void playVideo() {

        videoView.seekTo(0);

        videoView.start();
    }

    private void stopVideo() {

        videoView.pause();

        videoView.stopPlayback();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arm_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

