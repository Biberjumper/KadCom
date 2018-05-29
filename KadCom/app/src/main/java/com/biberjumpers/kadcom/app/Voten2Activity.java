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
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;

public class Voten2Activity extends Activity {
	
	
	private boolean connected = false;
	private String command = "";
	private double laden = 0;
	
	private LinearLayout komplette_seite;
	private LinearLayout platzhalter_oben;
	private LinearLayout kein_netz;
	private WebView voten2;
	private Button zuruek;
	private TextView ueberschrift;
	private TextView kein_netz_text;
	
	private SharedPreferences mode;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.voten2);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		komplette_seite = (LinearLayout) findViewById(R.id.komplette_seite);
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		kein_netz = (LinearLayout) findViewById(R.id.kein_netz);
		voten2 = (WebView) findViewById(R.id.voten2);
		voten2.getSettings().setJavaScriptEnabled(true);
		voten2.getSettings().setSupportZoom(true);
		zuruek = (Button) findViewById(R.id.zuruek);
		ueberschrift = (TextView) findViewById(R.id.ueberschrift);
		kein_netz_text = (TextView) findViewById(R.id.kein_netz_text);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		voten2.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		kein_netz_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				voten2.loadUrl("https://www.minecraft-serverlist.net/vote/21593");
				_Check();
				if (connected) {
					SketchwareUtil.showMessage(getApplicationContext(), "Verbunden");
					kein_netz.setVisibility(View.GONE);
					voten2.setVisibility(View.VISIBLE);
				}
				else {
					kein_netz.setVisibility(View.VISIBLE);
					voten2.setVisibility(View.GONE);
				}
				if (mode.getString("mode", "").equals("0")) {
					platzhalter_oben.setBackgroundColor(0xFF00B0FF);
					zuruek.setTextColor(0xFFE91E63);
					ueberschrift.setTextColor(0xFF000000);
					komplette_seite.setBackgroundColor(0xFFFFFFFF);
				}
				if (mode.getString("mode", "").equals("1")) {
					platzhalter_oben.setBackgroundColor(0xFF37474F);
					zuruek.setTextColor(0xFF2196F3);
					ueberschrift.setTextColor(0xFFFFFFFF);
					komplette_seite.setBackgroundColor(0xFF455A64);
				}
			}
		});
	}
	private void initializeLogic() {
		laden = 0;
		voten2.loadUrl("https://www.minecraft-serverlist.net/vote/21593");
		kein_netz.setVisibility(View.GONE);
		if (laden == 0) {
			SketchwareUtil.showMessage(getApplicationContext(), "Loading...");
			laden++;
		}
		_Check();
		if (connected) {
			SketchwareUtil.showMessage(getApplicationContext(), "Verbunden");
			kein_netz.setVisibility(View.GONE);
			voten2.setVisibility(View.VISIBLE);
		}
		else {
			kein_netz.setVisibility(View.VISIBLE);
			voten2.setVisibility(View.GONE);
		}
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
			ueberschrift.setTextColor(0xFF000000);
			komplette_seite.setBackgroundColor(0xFFFFFFFF);
		}
		if (mode.getString("mode", "").equals("1")) {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
			ueberschrift.setTextColor(0xFFFFFFFF);
			komplette_seite.setBackgroundColor(0xFF455A64);
		}
	}
	
	private void _Check () {
		try{
			command = "ping -c 1 google.com";
			connected = (Runtime.getRuntime().exec (command).waitFor() == 0); } catch (Exception e){ showMessage(e.toString());}
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
