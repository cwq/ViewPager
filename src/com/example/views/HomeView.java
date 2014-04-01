package com.example.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_viewpager.R;

public class HomeView {
	
	//listview数据
	private static List<Map<String, String>> data;
	//listview
	private static ListView homeView;
	//下拉刷新view
	private static TextView pullView;
	
	//当前listview的第一个item是否在界面第一个
	private static boolean atStart = true;
	//是否刷新
	private static boolean isFresh = false;
	//判断是否刷新的距离
	private static int FRESH_HEIGHT;
//	//listview中一个item的高度
//	private static final int itemHeight = 20;
	
	private static final String TAG = "Scroll";
	
	private static final int END_FRESH = 101;
	
	private static final Handler mHandler = new Handler(){
		@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case END_FRESH:
				isFresh = false;
				pullView.setHeight(0);
				break;

			default:
				break;
			}
    	};
	};;
	
	private static void setData() {
		data = new ArrayList<Map<String,String>>();
		Map<String,String> item;
		for (int i = 0; i < 50; i++) {
			item = new HashMap<String, String>();
			item.put("name", "name"+i);
			item.put("price", "$10"+i);
			item.put("address", "address"+i);
			data.add(item);
		}
	}
	
	public static void initHomeView(final Context context, View view) {
		//获取下拉刷新的碰到高度
		FRESH_HEIGHT = context.getResources().getDimensionPixelOffset(R.dimen.FRESH_HEIGHT);
		//初始化下拉刷新的TextView
		pullView = (TextView) view.findViewById(R.id.pull);
		pullView.setHeight(0);
		
		//初始化ListView
		homeView = (ListView) view.findViewById(R.id.homelist);
		
		setData();
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(context, data,
				R.layout.list_item, new String[] { "name", "price", "address" },
				new int[] { R.id.name, R.id.price, R.id.address });
		homeView.setAdapter(simpleAdapter);

		homeView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (!isFresh) {
					Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		homeView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				//判断当前listview的第一个item是否在界面第一个
				if (firstVisibleItem == 0) {
					atStart = true;
				} else {
					atStart = false;
				}
			}
		});
		homeView.setOnTouchListener(new OnTouchListener() {
			
			//是否是下拉的意图
			boolean isPullDown = false;
			//起始点y坐标
			float startY;
			
//			/**
//			 * 处理下拉引起的OnItemClick（item被选中事件）；
//			 * 为避免下拉的同时也触发ListView的滑动，return true 终止事件；
//			 * 而down触发了item选中，在move中当下滑距离大于item.height时 return false 传出事件，因为距离超出 item则不会被选中
//			 */
//			boolean isFirst;
			
			//上一次的高度
			int lastH;
			// 当前最大高度
			int maxH;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
//					isFirst = true;
					if (atStart && !isFresh) {
						//如果当前listview的第一个item在界面第一个 则为下拉意图
						isPullDown = true;
						startY = event.getY() + pullView.getHeight();
						maxH = lastH = pullView.getHeight();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (isPullDown) {
						homeView.setSelection(0);
						//当前点距起始点的距离
						float h = event.getY() + pullView.getHeight() - startY;
						if (h > 0) {
							pullView.setHeight((int) (h / 2));
							
							if (pullView.getHeight() > FRESH_HEIGHT) {
								//如果超过阈值 显示释放更新
								pullView.setText(R.string.pullup);
								isFresh = true;
							} else {
								isFresh = false;
								pullView.setText(R.string.pulldowm);
							}
							
							if (pullView.getHeight() > lastH && pullView.getHeight() > maxH) {
								//下拉 并且大于 当前最大高度 则 return false 传出事件
								maxH = lastH = pullView.getHeight();
								return false;
							} else {
								//否则 return true 终止事件
								lastH = pullView.getHeight();
								return true;
							}
							
							
//							if (isFirst && pullView.getHeight() > itemHeight) {
//								isFirst = false;
//								return false;
//							} else {
//								return true;
//							}
						} else {
							//如果上拉到顶   则推出下拉意图
							isPullDown = false;
							isFresh = false;
							pullView.setHeight(0);
						}
					}
					
					break;
				case MotionEvent.ACTION_UP:
					if (isPullDown) {
						isPullDown = false;
						if (isFresh) {
							pullView.setText(R.string.fresh);
							if (FRESH_HEIGHT < pullView.getHeight()) {
								pullView.setHeight(FRESH_HEIGHT);
							}
							mHandler.sendEmptyMessageDelayed(END_FRESH, 2000);
						} else {
							pullView.setHeight(0);
						}
						return true;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}
}
