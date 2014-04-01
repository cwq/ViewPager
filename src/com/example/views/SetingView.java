package com.example.views;

import com.example.android_viewpager.R;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SetingView {

	private static String[] data = { "��        ��", "�ҵ��ղ�", "��������", "������",
			"��        ��" };
	private static ListView setingView;

	public static void initSetingView(final Context context, View view) {
		setingView = (ListView) view.findViewById(R.id.setinglist);
		setingView.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, data));
		setingView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "position:" + position,
						Toast.LENGTH_SHORT).show();
			}

		});
	}
}
