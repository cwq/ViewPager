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
	// �ӳ�3��  
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
        // ��ȡSharedPreferences����Ҫ������  
        // ʹ��SharedPreferences����¼�����ʹ�ô���  
		SharedPreferences preferences = getSharedPreferences(
				getString(R.string.SHAREDPREFERENCES_NAME), MODE_PRIVATE);
  
        // ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ  
        boolean isFirstIn = preferences.getBoolean("isFirstIn", true);  
  
        // �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������  
        if (!isFirstIn) {  
            // ʹ��Handler��postDelayed������3���ִ����ת��MainActivity  
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
