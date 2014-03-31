package com.example.android_viewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.views.HomeView;
import com.example.views.SetingView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<View> viewList;
	private List<String> titleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}
	
	private void init() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setBackgroundColor(Color.LTGRAY);
		pagerTabStrip.setTabIndicatorColor(Color.GRAY);
		pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		pagerTabStrip.setTextColor(Color.WHITE);
		
		viewList = new ArrayList<View>();
		View view = LayoutInflater.from(this).inflate(R.layout.tab1, null);
		viewList.add(view);
		view = LayoutInflater.from(this).inflate(R.layout.tab2, null);
		viewList.add(view);
		view = LayoutInflater.from(this).inflate(R.layout.tab3, null);
		viewList.add(view);
		
		setViews();
		
		titleList = new ArrayList<String>();
		titleList.add("个人中心");
		titleList.add("主页导航");
		titleList.add("最新动态");
		
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		viewPager.setCurrentItem(1);
	}
	
	private void setViews() {
		SetingView.initSetingView(this, viewList.get(0));
		HomeView.initHomeView(this, viewList.get(1));
	}
	
	class MyAdapter extends PagerAdapter {
		
		@Override
		public float getPageWidth(int position) {
			// TODO Auto-generated method stub
			if (position == 0) {
				return 0.5f;
			} else {
				return super.getPageWidth(position);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(viewList.get(position));
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titleList.get(position);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(viewList.get(position));
			return viewList.get(position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
	} 
	
	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
        	return true;
        }else{
        	return super.onKeyDown(keyCode, event);
        }
	}
	
	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
		/*********** 双击返回键退出程序 **************/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
