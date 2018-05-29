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
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class EinstellungenActivity extends Activity {
	
	
	private LinearLayout platzhalter_oben;
	private LinearLayout night_mode;
	private LinearLayout nightmode_on_off;
	private LinearLayout token_text;
	private LinearLayout token_set;
	private Button zuruek;
	private TextView night_mode_schrift;
	private Button night_mode_on;
	private Button night_mode_off;
	private TextView token_schrift;
	private EditText edittext_token;
	private Button token_set_button;
	
	private SharedPreferences mode;
	private Intent I = new Intent();
	private SharedPreferences token;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.einstellungen);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		night_mode = (LinearLayout) findViewById(R.id.night_mode);
		nightmode_on_off = (LinearLayout) findViewById(R.id.nightmode_on_off);
		token_text = (LinearLayout) findViewById(R.id.token_text);
		token_set = (LinearLayout) findViewById(R.id.token_set);
		zuruek = (Button) findViewById(R.id.zuruek);
		night_mode_schrift = (TextView) findViewById(R.id.night_mode_schrift);
		night_mode_on = (Button) findViewById(R.id.night_mode_on);
		night_mode_off = (Button) findViewById(R.id.night_mode_off);
		token_schrift = (TextView) findViewById(R.id.token_schrift);
		edittext_token = (EditText) findViewById(R.id.edittext_token);
		token_set_button = (Button) findViewById(R.id.token_set_button);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		token = getSharedPreferences("token", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		night_mode_on.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mode.getString("mode", "").equals("0")) {
					mode.edit().putString("mode", "1").commit();
					platzhalter_oben.setBackgroundColor(0xFF37474F);
					night_mode.setBackgroundColor(0xFF455A64);
					nightmode_on_off.setBackgroundColor(0xFF546E7A);
					token_text.setBackgroundColor(0xFF455A64);
					token_set.setBackgroundColor(0xFF546E7A);
					zuruek.setTextColor(0xFF03A9F4);
					night_mode_on.setTextColor(0xFF2196F3);
					night_mode_off.setTextColor(0xFF9C27B0);
					night_mode_schrift.setTextColor(0xFFFFFFFF);
					token_schrift.setTextColor(0xFFFFFFFF);
					token_set_button.setTextColor(0xFF9C27B0);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Bereits aktiv");
				}
			}
		});
		
		night_mode_off.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mode.getString("mode", "").equals("1")) {
					mode.edit().putString("mode", "0").commit();
					platzhalter_oben.setBackgroundColor(0xFF00B0FF);
					night_mode.setBackgroundColor(0xFF42A5F5);
					nightmode_on_off.setBackgroundColor(0xFF2196F3);
					token_text.setBackgroundColor(0xFF42A5F5);
					token_set.setBackgroundColor(0xFF2196F3);
					zuruek.setTextColor(0xFFE91E63);
					night_mode_on.setTextColor(0xFFE91E63);
					night_mode_off.setTextColor(0xFF4CAF50);
					night_mode_schrift.setTextColor(0xFF000000);
					token_schrift.setTextColor(0xFF000000);
					token_set_button.setTextColor(0xFF4CAF50);
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Bereits deaktiviert");
				}
			}
		});
		
		token_set_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (edittext_token.getText().toString().length() > 0) {
					token.edit().putString("token", edittext_token.getText().toString()).commit();
					SketchwareUtil.showMessage(getApplicationContext(), "Token gespeichert");
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Du hast nichts eingegeben");
				}
			}
		});
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			night_mode.setBackgroundColor(0xFF42A5F5);
			nightmode_on_off.setBackgroundColor(0xFF2196F3);
			token_text.setBackgroundColor(0xFF42A5F5);
			token_set.setBackgroundColor(0xFF2196F3);
			zuruek.setTextColor(0xFFE91E63);
			night_mode_on.setTextColor(0xFFE91E63);
			night_mode_off.setTextColor(0xFF4CAF50);
			night_mode_schrift.setTextColor(0xFF000000);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			night_mode.setBackgroundColor(0xFF455A64);
			nightmode_on_off.setBackgroundColor(0xFF546E7A);
			token_text.setBackgroundColor(0xFF455A64);
			token_set.setBackgroundColor(0xFF546E7A);
			zuruek.setTextColor(0xFF03A9F4);
			night_mode_on.setTextColor(0xFF2196F3);
			night_mode_off.setTextColor(0xFF9C27B0);
			night_mode_schrift.setTextColor(0xFFFFFFFF);
			token_schrift.setTextColor(0xFFFFFFFF);
			token_set_button.setTextColor(0xFF9C27B0);
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
