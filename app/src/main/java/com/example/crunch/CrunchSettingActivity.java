package com.example.crunch;

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


public class CrunchSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunch_setting);
        final EditText numtext2 = (EditText)findViewById(R.id.getNumText3);
        final EditText setnumtext2 = (EditText)findViewById(R.id.getTimeText3);
        final EditText pausetext2 = (EditText)findViewById(R.id.getPauseText3);
        TextView selectedUserInfo = (TextView)findViewById(R.id.selectedUserInfo);

        String[] selectedUserArray = ((Global)getApplicationContext()).get_s_user_info();
        selectedUserInfo.setText("ID : " + selectedUserArray[0] + "  이름 : " + selectedUserArray[1] + "\n" +
                "키 : " + selectedUserArray[2] + "  발크기 : " + selectedUserArray[3] + "  다리길이 : " + selectedUserArray[4]);
        Button nextBtn = (Button) findViewById(R.id.nextButton999);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String E_NUM2, E_TIME2, P_TIME2;
                E_NUM2 = numtext2.getText().toString();
                E_TIME2 = setnumtext2.getText().toString();
                P_TIME2 = pausetext2.getText().toString();
                Log.i("onClick", "CallSubActivity");
                Intent intentSubActivity = new Intent(CrunchSettingActivity.this, CrunchTrainingActivity.class);
                intentSubActivity.putExtra("E_NUM2", E_NUM2);
                intentSubActivity.putExtra("E_TIME2", E_TIME2);
                intentSubActivity.putExtra("P_TIME2", P_TIME2);
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
