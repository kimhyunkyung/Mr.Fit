package com.example.arm;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mrfit.BaseActivity;
import com.example.mrfit.BluetoothLeService;
import com.example.mrfit.Global;
import com.example.mrfit.R;
import com.example.mrfit.SampleGattAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ArmTrainingActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    TimerTask mTimerTask;
    TimerTask sTimerTask;
    Timer m = new Timer();
    Timer s = new Timer();
    int MAX_TIME = 600;
    TextView tTextView, nTextView, rTextView, dataTextView, testTestView;
    ImageButton sButton, rButton;
    int setnum, exnum, pausetime;
    int numCounter = 0;
    int setCounter = 0;
    int oneCounter = 0;
    int twoCounter = 0;

    boolean bPause = false;

    ProgressBar progressBar;
    Message msg;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setProgress(msg.arg1);
        }
    };
    TextToSpeech myTTS;

    private final static String TAG = ArmTrainingActivity.class.getSimpleName();
    private BluetoothGatt mBluetoothGatt;
    BluetoothGattCharacteristic characteristic;
    boolean enabled;
    ArrayList<String> arr=new ArrayList<String>();
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    byte[] b;
    private TextView mConnectionState;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>(); //
    private boolean mConnected = false;
    //  private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothLeService.ACTION_GATT_CONNECTED)) {
                mConnected = true;
                //updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA)); //여기서 데이터가 표시됐음 !!!!!!! 이걸잠시 주석으로 달고 밑에 display에서 getvalue해봅시다!!


                // arr.add(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

            }
        }
    };


    private void clearUI() {
        dataTextView.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arm_training);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

        TextView View1 = (TextView) findViewById(R.id.guideTxt1);
        String[] bInfo = ((Global)getApplicationContext()).get_b_device_info();
        mDeviceName = bInfo[0];
        mDeviceAddress = bInfo[1];

        Bundle intent = getIntent().getExtras();
        setnum = Integer.parseInt(intent.getString("S_NUM_A"));
        exnum = Integer.parseInt(intent.getString("C_NUM_A"));
        pausetime = Integer.parseInt(intent.getString("P_TIME_A"));
        View1.setText(setnum + "세트 운동을 진행합니다.\n" +
                "세트당 " + exnum + "회 운동합니다.\n" +
                pausetime + "초간 휴식합니다. ");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tTextView = (TextView) findViewById(R.id.timeTxt);
        nTextView = (TextView) findViewById(R.id.countTxt);
        rTextView = (TextView) findViewById(R.id.resultTxt);
        sButton = (ImageButton) findViewById(R.id.exStartButton);
        sButton.setOnClickListener(sButtonListener);
        rButton = (ImageButton) findViewById(R.id.exResetButton);
        rButton.setOnClickListener(rButtonListener);
        dataTextView = (TextView) findViewById(R.id.dataTextView);
        myTTS = new TextToSpeech(this, this);
        testTestView = (TextView) findViewById(R.id.testTestView);



        dataTextView.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                }
                                                @Override
                                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                }
                                                @Override
                                                public void afterTextChanged(Editable editable) {
                                                    if (bPause){
                                                        int if1 = editable.toString().indexOf('1');
                                                        int if2 = editable.toString().indexOf('2');
                                                        testTestView.setText(Integer.toString(if2));
                                                        if (if1 == 1) {
                                                            //bRunning = false;
                                                            numCounter++;
                                                            myTTS.speak(numCounter + "회", TextToSpeech.QUEUE_FLUSH, null);

                                                        } else if (if2 == 1 ) {
                                                            //bRunning = false;
                                                            myTTS.speak("삐", TextToSpeech.QUEUE_FLUSH, null);
                                                        }
                                                    }
                                                }
                                            }
        );
    } // end onCreate

    public void onInit(int status){
    }

    View.OnClickListener sButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            bPause = true;
            sendData();
            doExercise(setnum, exnum);
        }
    };

    View.OnClickListener rButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            bPause = !bPause;
            rTextView.setText("일시정지");
        }
    }; ///////으아아아아아아아아아아아아아아아아아앙아아?????????

    public void doExercise(int set, int ex){
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        msg = handler.obtainMessage();
                        msg.arg1 = (100 / exnum) * numCounter;
                        handler.sendMessage(msg);
                        tTextView.setText(setCounter + "세트 진행");
                        nTextView.setText("운동횟수 " + numCounter + "회 / " + exnum + "회");
                        rTextView.setText("");
                        if(numCounter == 0) { //횟수카운트0. 시작할때
                            //myTTS.speak(setCounter + "세트",TextToSpeech.QUEUE_FLUSH, null);
                        }
                        if(numCounter >= exnum) { //횟수채웠을때
                            setCounter++;
                            msg.arg1 = 100;
                            nTextView.setText("운동횟수 " + numCounter + "회 / " + exnum + "회");
                            numCounter = 0;//초기화
                            rTextView.setText("휴식");
                            doPauseTimer(pausetime);
                        }
                        if(setCounter > setnum) {
                            stopTask();
                        }
                    }
                });
            }
        };
        m.schedule(mTimerTask, 0, 500);
    }





    public void doPauseTimer(int k) {
        bPause = false;
        myTTS.speak(Integer.toString(k) + "초간 휴식", TextToSpeech.QUEUE_FLUSH, null);
        sTimerTask = new TimerTask() {
            public void run() {
                bPause = !bPause;
            }
        };
        s.schedule(sTimerTask, k * 1000);
    }

    public void stopTask() {
        tTextView.setText(setnum + "세트 완료");
        nTextView.setText("운동횟수  " + exnum + "/" + exnum);
        rTextView.setText("운동이 종료되었습니다!");
        myTTS.speak("운동이 종료되었습니다", TextToSpeech.QUEUE_FLUSH, null);
        msg.arg1 = 100;
    }

    public String getData() {
        if(bPause){
            BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(2).get(0);
            mBluetoothLeService.readCharacteristic(characteristic);
            b=characteristic.getValue();
            if (b != null && b.length > 0) {
                String text = characteristic.getStringValue(0);
                displayData(text);
                final StringBuilder stringBuilder = new StringBuilder(b.length);
                for (byte byteChar : b)
                    stringBuilder.append(String.format("%02X ", byteChar));
                String resultValue = stringBuilder.toString();
                displayData(resultValue);
                return resultValue;
            }
        }
        return null;
    }

    public void sendData() {
        BluetoothGattCharacteristic ch2 = mGattCharacteristics.get(2).get(1);
        byte[] armvalue = {0};
        ch2.setValue(armvalue);
        mBluetoothLeService.writeCharacteristic(ch2);
    }

    @Override
    protected void onDestroy() {
        Log.i("test", "onDstory()");
        m.cancel();
        s.cancel();
        super.onDestroy();
        myTTS.shutdown();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) { // data전달할....
        if (data != null) {
            dataTextView.setText(data);
        }
    }
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().
                getString(R.string.unknown_service);
        String unknownCharaString = getResources().
                getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData =
                new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics =
                new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData =
                    new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME, SampleGattAttributes.
                    lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic :
                    gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData =
                        new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid,
                                unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }}

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }


}