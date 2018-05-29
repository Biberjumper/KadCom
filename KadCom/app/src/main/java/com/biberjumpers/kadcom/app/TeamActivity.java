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
import android.widget.ScrollView;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;

public class TeamActivity extends Activity {
	
	
	private LinearLayout platzhalter_oben;
	private ScrollView scroll;
	private Button zuruek;
	private LinearLayout fenster;
	private TextView admins;
	private TextView admin1;
	private TextView admin2;
	private TextView admin3;
	private TextView admin4;
	private TextView operatoren;
	private TextView op1;
	private TextView op2;
	private TextView op3;
	private TextView moderatoren_plus;
	private TextView mod_plus1;
	private TextView mod_plus2;
	private TextView mod_plus3;
	private TextView mod_plus4;
	private TextView mod_plus5;
	private TextView mod_plus6;
	private TextView mod_plus7;
	private TextView mod_plus8;
	private TextView moderatoren;
	private TextView mod1;
	private TextView supporter5;
	private TextView supporter2;
	private TextView mod2;
	private TextView supporter;
	private TextView supporter7;
	private TextView halter36;
	private TextView supporter4;
	private TextView supporter6;
	private TextView supporter3;
	private TextView supporter9;
	private TextView supporter8;
	private TextView supporter10;
	private TextView supporter11;
	private TextView supporter12;
	private TextView supporter13;
	private TextView supporter14;
	private TextView supporter15;
	private TextView supporter17;
	private TextView supporter16;
	
	private SharedPreferences mode;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.team);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		scroll = (ScrollView) findViewById(R.id.scroll);
		zuruek = (Button) findViewById(R.id.zuruek);
		fenster = (LinearLayout) findViewById(R.id.fenster);
		admins = (TextView) findViewById(R.id.admins);
		admin1 = (TextView) findViewById(R.id.admin1);
		admin2 = (TextView) findViewById(R.id.admin2);
		admin3 = (TextView) findViewById(R.id.admin3);
		admin4 = (TextView) findViewById(R.id.admin4);
		operatoren = (TextView) findViewById(R.id.operatoren);
		op1 = (TextView) findViewById(R.id.op1);
		op2 = (TextView) findViewById(R.id.op2);
		op3 = (TextView) findViewById(R.id.op3);
		moderatoren_plus = (TextView) findViewById(R.id.moderatoren_plus);
		mod_plus1 = (TextView) findViewById(R.id.mod_plus1);
		mod_plus2 = (TextView) findViewById(R.id.mod_plus2);
		mod_plus3 = (TextView) findViewById(R.id.mod_plus3);
		mod_plus4 = (TextView) findViewById(R.id.mod_plus4);
		mod_plus5 = (TextView) findViewById(R.id.mod_plus5);
		mod_plus6 = (TextView) findViewById(R.id.mod_plus6);
		mod_plus7 = (TextView) findViewById(R.id.mod_plus7);
		mod_plus8 = (TextView) findViewById(R.id.mod_plus8);
		moderatoren = (TextView) findViewById(R.id.moderatoren);
		mod1 = (TextView) findViewById(R.id.mod1);
		supporter5 = (TextView) findViewById(R.id.supporter5);
		supporter2 = (TextView) findViewById(R.id.supporter2);
		mod2 = (TextView) findViewById(R.id.mod2);
		supporter = (TextView) findViewById(R.id.supporter);
		supporter7 = (TextView) findViewById(R.id.supporter7);
		halter36 = (TextView) findViewById(R.id.halter36);
		supporter4 = (TextView) findViewById(R.id.supporter4);
		supporter6 = (TextView) findViewById(R.id.supporter6);
		supporter3 = (TextView) findViewById(R.id.supporter3);
		supporter9 = (TextView) findViewById(R.id.supporter9);
		supporter8 = (TextView) findViewById(R.id.supporter8);
		supporter10 = (TextView) findViewById(R.id.supporter10);
		supporter11 = (TextView) findViewById(R.id.supporter11);
		supporter12 = (TextView) findViewById(R.id.supporter12);
		supporter13 = (TextView) findViewById(R.id.supporter13);
		supporter14 = (TextView) findViewById(R.id.supporter14);
		supporter15 = (TextView) findViewById(R.id.supporter15);
		supporter17 = (TextView) findViewById(R.id.supporter17);
		supporter16 = (TextView) findViewById(R.id.supporter16);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	private void initializeLogic() {
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF0277BD);
			zuruek.setTextColor(0xFFE91E63);
			fenster.setBackgroundColor(0xFF29B6F6);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF455A64);
			zuruek.setTextColor(0xFF2196F3);
			fenster.setBackgroundColor(0xFF37474F);
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
