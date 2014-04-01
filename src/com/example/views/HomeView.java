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
	
	//listview����
	private static List<Map<String, String>> data;
	//listview
	private static ListView homeView;
	//����ˢ��view
	private static TextView pullView;
	
	//��ǰlistview�ĵ�һ��item�Ƿ��ڽ����һ��
	private static boolean atStart = true;
	//�Ƿ�ˢ��
	private static boolean isFresh = false;
	//�ж��Ƿ�ˢ�µľ���
	private static int FRESH_HEIGHT;
//	//listview��һ��item�ĸ߶�
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
		//��ȡ����ˢ�µ������߶�
		FRESH_HEIGHT = context.getResources().getDimensionPixelOffset(R.dimen.FRESH_HEIGHT);
		//��ʼ������ˢ�µ�TextView
		pullView = (TextView) view.findViewById(R.id.pull);
		pullView.setHeight(0);
		
		//��ʼ��ListView
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
				//�жϵ�ǰlistview�ĵ�һ��item�Ƿ��ڽ����һ��
				if (firstVisibleItem == 0) {
					atStart = true;
				} else {
					atStart = false;
				}
			}
		});
		homeView.setOnTouchListener(new OnTouchListener() {
			
			//�Ƿ�����������ͼ
			boolean isPullDown = false;
			//��ʼ��y����
			float startY;
			
//			/**
//			 * �������������OnItemClick��item��ѡ���¼�����
//			 * Ϊ����������ͬʱҲ����ListView�Ļ�����return true ��ֹ�¼���
//			 * ��down������itemѡ�У���move�е��»��������item.heightʱ return false �����¼�����Ϊ���볬�� item�򲻻ᱻѡ��
//			 */
//			boolean isFirst;
			
			//��һ�εĸ߶�
			int lastH;
			// ��ǰ���߶�
			int maxH;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
//					isFirst = true;
					if (atStart && !isFresh) {
						//�����ǰlistview�ĵ�һ��item�ڽ����һ�� ��Ϊ������ͼ
						isPullDown = true;
						startY = event.getY() + pullView.getHeight();
						maxH = lastH = pullView.getHeight();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (isPullDown) {
						homeView.setSelection(0);
						//��ǰ�����ʼ��ľ���
						float h = event.getY() + pullView.getHeight() - startY;
						if (h > 0) {
							pullView.setHeight((int) (h / 2));
							
							if (pullView.getHeight() > FRESH_HEIGHT) {
								//���������ֵ ��ʾ�ͷŸ���
								pullView.setText(R.string.pullup);
								isFresh = true;
							} else {
								isFresh = false;
								pullView.setText(R.string.pulldowm);
							}
							
							if (pullView.getHeight() > lastH && pullView.getHeight() > maxH) {
								//���� ���Ҵ��� ��ǰ���߶� �� return false �����¼�
								maxH = lastH = pullView.getHeight();
								return false;
							} else {
								//���� return true ��ֹ�¼�
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
							//�����������   ���Ƴ�������ͼ
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
