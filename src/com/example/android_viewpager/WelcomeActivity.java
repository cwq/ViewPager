package com.example.android_viewpager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeActivity extends Activity{
	
	private static final int GO_HOME = 1000;  
    private static final int GO_GUIDE = 1001; 
	// 延迟3秒  
    private static final long SPLASH_DELAY_MILLIS = 2000; 
    
    private final Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;

			default:
				break;
			}
    	};
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		init();
	}

	private void init() {
        // 读取SharedPreferences中需要的数据  
        // 使用SharedPreferences来记录程序的使用次数  
		SharedPreferences preferences = getSharedPreferences(
				getString(R.string.SHAREDPREFERENCES_NAME), MODE_PRIVATE);
  
        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值  
        boolean isFirstIn = preferences.getBoolean("isFirstIn", true);  
  
        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面  
        if (!isFirstIn) {  
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity  
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);  
        } else {  
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);  
        }  
  
    }  
  
    private void goHome() {  
        Intent intent = new Intent(this, MainActivity.class);  
        this.startActivity(intent);  
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }  
  
    private void goGuide() {  
        Intent intent = new Intent(this, GuideActivity.class);  
        this.startActivity(intent);  
        this.finish();  
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }  

}
