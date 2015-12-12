package com.example.arm;

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


public class ArmSettingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm_setting);
        final EditText getSetNumA = (EditText)findViewById(R.id.getSetNumA);
        final EditText getCountNumA = (EditText)findViewById(R.id.getCountNumA);
        final EditText getPauseTimeA = (EditText)findViewById(R.id.getPauseTimeA);
        TextView selectedUserInfo = (TextView)findViewById(R.id.selectedUserInfo);

        String[] selectedUserArray = ((Global)getApplicationContext()).get_s_user_info();
        selectedUserInfo.setText("ID : " + selectedUserArray[0] + "  이름 : " + selectedUserArray[1] + "\n" +
                "키 : " + selectedUserArray[2] + "  발크기 : " + selectedUserArray[3] + "  다리길이 : " + selectedUserArray[4]);
        Button nextBtn = (Button) findViewById(R.id.nextButton999);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String S_NUM_A, C_NUM_A, P_TIME_A;
                S_NUM_A = getSetNumA.getText().toString();
                C_NUM_A = getCountNumA.getText().toString();
                P_TIME_A = getPauseTimeA.getText().toString();
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(ArmSettingActivity.this, ArmTrainingActivity.class);
                intentSubActivity.putExtra("S_NUM_A", S_NUM_A);
                intentSubActivity.putExtra("C_NUM_A", C_NUM_A);
                intentSubActivity.putExtra("P_TIME_A", P_TIME_A);
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
