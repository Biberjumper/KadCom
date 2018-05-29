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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.view.View;

public class InfosActivity extends Activity {
	
	
	private LinearLayout platzhalter_oben;
	private TextView info_text1;
	private TextView info_text2;
	private TextView info_text3;
	private TextView info_text4;
	private TextView info_text5;
	private LinearLayout platzhalter_unten;
	private Button zuruek;
	
	private Intent Menu = new Intent();
	private Intent Einstellungen = new Intent();
	private SharedPreferences mode;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.infos);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		info_text1 = (TextView) findViewById(R.id.info_text1);
		info_text2 = (TextView) findViewById(R.id.info_text2);
		info_text3 = (TextView) findViewById(R.id.info_text3);
		info_text4 = (TextView) findViewById(R.id.info_text4);
		info_text5 = (TextView) findViewById(R.id.info_text5);
		platzhalter_unten = (LinearLayout) findViewById(R.id.platzhalter_unten);
		zuruek = (Button) findViewById(R.id.zuruek);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	private void initializeLogic() {
		setTitle("Infos zur App");
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
			info_text1.setBackgroundColor(Color.TRANSPARENT);
			info_text2.setBackgroundColor(Color.TRANSPARENT);
			info_text3.setBackgroundColor(Color.TRANSPARENT);
			info_text4.setBackgroundColor(Color.TRANSPARENT);
			info_text5.setBackgroundColor(Color.TRANSPARENT);
			platzhalter_unten.setBackgroundColor(Color.TRANSPARENT);
			info_text1.setTextColor(0xFF000000);
			info_text3.setTextColor(0xFF000000);
			info_text4.setTextColor(0xFF000000);
			info_text5.setTextColor(0xFF000000);
		}
		if (mode.getString("mode", "").equals("1")) {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
			info_text1.setBackgroundColor(0xFF455A64);
			info_text2.setBackgroundColor(0xFF546E7A);
			info_text3.setBackgroundColor(0xFF607D8B);
			info_text4.setBackgroundColor(0xFF78909C);
			info_text5.setBackgroundColor(0xFF90A4AE);
			platzhalter_unten.setBackgroundColor(0xFFB0BEC5);
			info_text1.setTextColor(0xFF90A4AE);
			info_text2.setTextColor(0xFFB0BEC5);
			info_text3.setTextColor(0xFFCFD8DC);
			info_text4.setTextColor(0xFFEEEEEE);
			info_text5.setTextColor(0xFFECEFF1);
		}
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
