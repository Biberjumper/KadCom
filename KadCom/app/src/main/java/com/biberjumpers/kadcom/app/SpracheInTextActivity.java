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
import android.widget.EditText;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class SpracheInTextActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private LinearLayout platzhalzer_oben;
	private LinearLayout platzhalter2;
	private Button zuruek;
	private EditText text1;
	private Button start;
	private TextView erster_text;
	
	private TimerTask Timer;
	private SharedPreferences mode;
	private Intent Zuruek = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.sprache_in_text);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalzer_oben = (LinearLayout) findViewById(R.id.platzhalzer_oben);
		platzhalter2 = (LinearLayout) findViewById(R.id.platzhalter2);
		zuruek = (Button) findViewById(R.id.zuruek);
		text1 = (EditText) findViewById(R.id.text1);
		start = (Button) findViewById(R.id.start);
		erster_text = (TextView) findViewById(R.id.erster_text);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				_spech();
			}
		});
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			platzhalzer_oben.setBackgroundColor(0xFF00B0FF);
			start.setTextColor(0xFFE91E63);
			zuruek.setTextColor(0xFFE91E63);
			platzhalter2.setBackgroundColor(0xFF2196F3);
		}
		else {
			platzhalzer_oben.setBackgroundColor(0xFF37474F);
			start.setTextColor(0xFF2196F3);
			zuruek.setTextColor(0xFF2196F3);
			platzhalter2.setBackgroundColor(0xFF455A64);
		}
		erster_text.setVisibility(View.GONE);
		text1.setSelectAllOnFocus(true);
	} private final int REQ_CODE_SPEECH_INPUT=100;
	private void sowhat(){
	}
	
	private void _spech () {
		Intent intent = new Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH); intent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); intent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()); intent.putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "Sag etwas"); try { startActivityForResult(intent, REQ_CODE_SPEECH_INPUT); } catch (ActivityNotFoundException a) { Toast.makeText(getApplicationContext(), "Speech Input wird auf deinem Gerät nicht unzerstützt", Toast.LENGTH_SHORT).show(); } } @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) { super.onActivityResult(requestCode, resultCode, data); switch (requestCode) { case REQ_CODE_SPEECH_INPUT: { if (resultCode == RESULT_OK && null != data) { ArrayList<String> result = data .getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS); erster_text.setText(result.get(0)); } break; } }  
		Timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						text1.setText(text1.getText().toString().concat(" ".concat(erster_text.getText().toString())));
						Timer = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										erster_text.setText("");
									}
								});
							}
						};
						_timer.schedule(Timer, (int)(20));
					}
				});
			}
		};
		_timer.schedule(Timer, (int)(1000));
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
