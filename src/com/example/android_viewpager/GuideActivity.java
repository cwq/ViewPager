package com.example.android_viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	
	private ViewPager guideViewPager;
	private List<View> viewList;
	private ImageView[] dots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide);

		init();
	}
	
	/**
	 * 初始化组件
	 */
	private void init() {
		// 实例化ViewPager
		guideViewPager = (ViewPager) findViewById(R.id.guideviewpager);
		
		viewList = new ArrayList<View>();
		//实例化各个界面的布局对象 
		LayoutInflater mLi = LayoutInflater.from(this);
		View view = mLi.inflate(R.layout.guide_view01, null);
		viewList.add(view);
		view = mLi.inflate(R.layout.guide_view02, null);
		viewList.add(view);
		view = mLi.inflate(R.layout.guide_view03, null);
		viewList.add(view);
		view = mLi.inflate(R.layout.guide_view04, null);
		viewList.add(view);
		view = mLi.inflate(R.layout.guide_view05, null);
		viewList.add(view);
		view = mLi.inflate(R.layout.guide_view06, null);
		viewList.add(view);
		
		guideViewPager.setAdapter(new MyAdapter());
		guideViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		dots = new ImageView[viewList.size()];
		// 实例化底部小点图片对象
		dots[0] = (ImageView) findViewById(R.id.page0);
		dots[1] = (ImageView) findViewById(R.id.page1);
		dots[2] = (ImageView) findViewById(R.id.page2);
		dots[3] = (ImageView) findViewById(R.id.page3);
		dots[4] = (ImageView) findViewById(R.id.page4);
		dots[5] = (ImageView) findViewById(R.id.page5);
		
		//实例化开始按钮
		((Button) viewList.get(5).findViewById(R.id.startBtn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGuided();
				goHome();
			}
		});
	}
	
	
	private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);  
        this.startActivity(intent);  
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }  
	
	/**
	 * 设置已经引导过了，下次启动不用再次引导 
	 */
    private void setGuided() {  
        SharedPreferences preferences = getSharedPreferences(  
                getString(R.string.SHAREDPREFERENCES_NAME), Context.MODE_PRIVATE);  
        Editor editor = preferences.edit();  
        // 存入数据  
        editor.putBoolean("isFirstIn", false);  
        // 提交修改  
        editor.commit();  
    } 
    
    private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(viewList.get(position));
			return viewList.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(viewList.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
    	
    }
    
    private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			dots[position].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_focused));
			if (position - 1 >= 0) {
				dots[position - 1].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
			}
			if (position + 1 < dots.length) {
				dots[position + 1].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_unfocused));
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
