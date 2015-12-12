package com.example.sixinch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mrfit.BaseActivity;
import com.example.mrfit.Global;
import com.example.mrfit.R;


public class SixInchSettingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixinch_setting);
        final EditText numtext3 = (EditText)findViewById(R.id.getNumText3);
        final EditText timetext3 = (EditText)findViewById(R.id.getTimeText3);
        final EditText pausetext3 = (EditText)findViewById(R.id.getPauseText3);
        TextView selectedUserInfo = (TextView)findViewById(R.id.selectedUserInfo);

        String[] selectedUserArray = ((Global)getApplicationContext()).get_s_user_info();
        selectedUserInfo.setText("ID : " + selectedUserArray[0] + "  이름 : " + selectedUserArray[1] + "\n" +
                "키 : " + selectedUserArray[2] + "  발크기 : " + selectedUserArray[3] + "  다리길이 : " + selectedUserArray[4]);
        Button nextBtn = (Button) findViewById(R.id.nextButton999);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String E_NUM3, E_TIME3, P_TIME3;
                E_NUM3 = numtext3.getText().toString();
                E_TIME3 = timetext3.getText().toString();
                P_TIME3 = pausetext3.getText().toString();
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(SixInchSettingActivity.this, SixInchTrainingActivity.class);
                intentSubActivity.putExtra("E_TIME3", E_TIME3);
                intentSubActivity.putExtra("E_NUM3", E_NUM3);
                intentSubActivity.putExtra("P_TIME3", P_TIME3);
                startActivity(intentSubActivity);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
