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
import android.content.ClipData;
import android.content.ClipboardManager;

public class KontoActivity extends Activity {
	
	
	private boolean connected = false;
	private String command = "";
	
	private LinearLayout linear_oben;
	private LinearLayout linear_kein_netz;
	private WebView webview_konto;
	private Button zuruek;
	private LinearLayout linear_oben_token;
	private TextView textview_oben_token;
	private TextView textview_kein_netz;
	
	private SharedPreferences token;
	private SharedPreferences mode;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.konto);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		linear_oben = (LinearLayout) findViewById(R.id.linear_oben);
		linear_kein_netz = (LinearLayout) findViewById(R.id.linear_kein_netz);
		webview_konto = (WebView) findViewById(R.id.webview_konto);
		webview_konto.getSettings().setJavaScriptEnabled(true);
		webview_konto.getSettings().setSupportZoom(true);
		zuruek = (Button) findViewById(R.id.zuruek);
		linear_oben_token = (LinearLayout) findViewById(R.id.linear_oben_token);
		textview_oben_token = (TextView) findViewById(R.id.textview_oben_token);
		textview_kein_netz = (TextView) findViewById(R.id.textview_kein_netz);
		token = getSharedPreferences("token", Activity.MODE_PRIVATE);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		webview_konto.setWebViewClient(new WebViewClient() {
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
		
		textview_oben_token.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (token.getString("token", "").equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Noch kein Token vorhanden -> Einstellungen");
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Token kopiert");
					((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", token.getString("token", "")));
				}
			}
		});
	}
	private void initializeLogic() {
		webview_konto.zoomOut();
		if (mode.getString("mode", "").equals("0")) {
			linear_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
			textview_oben_token.setTextColor(0xFF000000);
		}
		else {
			linear_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
			textview_oben_token.setTextColor(0xFFFFFFFF);
		}
		webview_konto.loadUrl("https://bank.kadcon.de/login.php");
		linear_kein_netz.setVisibility(View.GONE);
		_Check();
		if (connected) {
			linear_kein_netz.setVisibility(View.GONE);
			webview_konto.setVisibility(View.VISIBLE);
		}
		else {
			linear_kein_netz.setVisibility(View.VISIBLE);
			webview_konto.setVisibility(View.GONE);
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
