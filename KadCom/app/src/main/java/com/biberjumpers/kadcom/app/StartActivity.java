package com.biberjumpers.kadcom.app;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.app.Activity;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;

public class StartActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private TextView text5;
	private TextView text6;
	private TextView text7;
	private TextView text8;
	private TextView text9;
	private TextView text10;
	private CheckBox checkbox;
	private Button accept;
	
	private SharedPreferences starts;
	private Intent Weiter = new Intent();
	private TimerTask Timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.start);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		text5 = (TextView) findViewById(R.id.text5);
		text6 = (TextView) findViewById(R.id.text6);
		text7 = (TextView) findViewById(R.id.text7);
		text8 = (TextView) findViewById(R.id.text8);
		text9 = (TextView) findViewById(R.id.text9);
		text10 = (TextView) findViewById(R.id.text10);
		checkbox = (CheckBox) findViewById(R.id.checkbox);
		accept = (Button) findViewById(R.id.accept);
		starts = getSharedPreferences("starts", Activity.MODE_PRIVATE);
		
		accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (checkbox.isChecked()) {
					starts.edit().putString("starts", "accepted").commit();
					SketchwareUtil.showMessage(getApplicationContext(), "Na dann los!");
					Timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									SketchwareUtil.showMessage(getApplicationContext(), "Starte die App bitte neu!");
								}
							});
						}
					};
					_timer.schedule(Timer, (int)(2000));
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Du musst zuerst alles akzeptieren");
				}
			}
		});
	}
	private void initializeLogic() {
	}
	
	@Override
	public void onBackPressed() {
		
	}
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
