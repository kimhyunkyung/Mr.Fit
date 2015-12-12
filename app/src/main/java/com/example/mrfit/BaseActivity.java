package com.example.mrfit;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by H on 2015-07-28.
 */
public class BaseActivity extends Activity {

//
//    private static Typeface mTypeface = null;
//
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        View view = findViewById(android.R.id.content);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
//        if (mTypeface == null) {
//            mTypeface = Typeface.createFromAsset(this.getAssets(), "NBGothic.ttf");
//        }
//        setGlobalFont(view);
    }
//
//    private void setGlobalFont(View view) {
//        if (view != null) {
//            if(view instanceof ViewGroup){
//                ViewGroup vg = (ViewGroup)view;
//                int vgCnt = vg.getChildCount();
//                for(int i=0; i < vgCnt; i++){
//                    View v = vg.getChildAt(i);
//                    if(v instanceof TextView){
//                        ((TextView) v).setTypeface(mTypeface);
//                    }
//                    setGlobalFont(v);
//                }
//            }
//        }
//    }
}