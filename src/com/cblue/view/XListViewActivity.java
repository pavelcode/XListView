package com.cblue.view;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.R;
import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

public class XListViewActivity extends Activity implements IXListViewListener {

	private XListView mXListView;
	private List<String> items;
	private Handler handler;
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mXListView = (XListView) findViewById(R.id.xListView);
		handler = new Handler();
		items = new ArrayList<String>();
		getData("");
		arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, items);
		mXListView.setAdapter(arrayAdapter);
		mXListView.setXListViewListener(this);
		mXListView.setPullLoadEnable(true); // 上拉加载
		mXListView.setPullRefreshEnable(true); // 下拉刷新

	}

	private void getData(String tag) {
		for (int i = 0; i < 10; i++) {
			items.add(tag + " data" + i);
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Log.i("aaa", "下拉刷新");
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				items.clear();
				getData("web");
				arrayAdapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, items);
				mXListView.setAdapter(arrayAdapter);
			    onLoad();
			}
		}, 2 * 1000);

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		Log.i("aaa", "上拉加载");
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				getData("local");
				arrayAdapter.notifyDataSetChanged();
			    onLoad();

			}
		}, 2 * 1000);


	}
	
	private void onLoad(){
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
		mXListView.setRefreshTime("刚刚");
	}

}
