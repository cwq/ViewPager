package com.example.android_viewpager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {
	
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<View> list;
	private List<String> titleList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setBackgroundColor(Color.LTGRAY);
		pagerTabStrip.setTabIndicatorColor(Color.GRAY);
		pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		pagerTabStrip.setTextColor(Color.WHITE);
		
		list = new ArrayList<View>();
		View view = LayoutInflater.from(this).inflate(R.layout.tab1, null);
		list.add(view);
		view = LayoutInflater.from(this).inflate(R.layout.tab2, null);
		list.add(view);
		view = LayoutInflater.from(this).inflate(R.layout.tab3, null);
		list.add(view);
		
		titleList = new ArrayList<String>();
		titleList.add("title1");
		titleList.add("title2");
		titleList.add("title3");
		
		viewPager.setAdapter(new MyAdapter());
		viewPager.setCurrentItem(1);
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				Log.v("viewpager", "onPageSelected: "+position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
			}
		});
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
			return list.size();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			Log.v("viewpager", "destroyItem: "+position);
			container.removeView(list.get(position));
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titleList.get(position);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			Log.v("viewpager", "instantiateItem: "+position);
			container.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
