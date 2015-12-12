package com.example.mrfit;

import android.app.Application;
import android.renderscript.Int2;

/**
 * Created by H on 2015-08-24.
 */
public class Global extends Application {

    public String[] s_user_info;
    public String[] b_device_info;
    public String exType;

    @Override
    public void onCreate() {
        s_user_info = null;
        b_device_info = null;
        exType = null;
        super.onCreate();
    }
    public void setExType(String exnum) {this.exType = exnum;}
    public String getExType() {return exType; }

    public void set_s_user_info(String[] userinfo){
        this.s_user_info = userinfo;
    }
    public String[] get_s_user_info(){
        return s_user_info;
    }

    public void set_b_device_info(String[] deviceinfo){
        this.b_device_info = deviceinfo;
    }
    public String[] get_b_device_info() { return b_device_info; }

    public byte[] get_s_user_leg_byte(String[] s_user_info) {
        int a = Integer.parseInt(s_user_info[4]);
        byte[] s_user_leg_byte = Int2Byte(a);
        return s_user_leg_byte; }

    public static byte[] Int2Byte(int a) {
        byte[] int2byte = new byte[4];
        int2byte[0] |= (byte)((a&0xFF000000)>>24);
        int2byte[1] |= (byte)((a&0xFF0000)>>16);
        int2byte[2] |= (byte)((a&0xFF00)>>8);
        int2byte[3] |= (byte)(a&0xFF);
        return int2byte;
    }

    public Global() {
    }
}
