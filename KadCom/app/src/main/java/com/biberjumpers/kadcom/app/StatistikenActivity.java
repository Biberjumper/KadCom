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
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.view.View;

public class StatistikenActivity extends Activity {
	
	
	private String pkg = "";
	private String p_ver = "";
	
	private ScrollView vscroll;
	private LinearLayout seite;
	private LinearLayout platzhalter_oben;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private Button zuruek;
	private TextView starts;
	private TextView stat1;
	private TextView firstjoin;
	private TextView stat2;
	private TextView number;
	private TextView stat3;
	private TextView menuecount;
	private TextView stat4;
	private TextView reloadcount;
	private TextView stat5;
	private TextView votencounter;
	private TextView stat6;
	private TextView nonetopen;
	private TextView stat7;
	private TextView ratenumber;
	private TextView stat8;
	private TextView ratecomment;
	private TextView stat9;
	private TextView version_text;
	private TextView stat10;
	
	private SharedPreferences mode;
	private SharedPreferences startcount;
	private SharedPreferences Usernumber;
	private SharedPreferences first;
	private SharedPreferences menucount;
	private SharedPreferences reloadcounter;
	private SharedPreferences votecount;
	private SharedPreferences nonetcounter;
	private SharedPreferences rate_number;
	private SharedPreferences rate_comment;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.statistiken);
		initialize();
		initializeLogic();
	}
	
	private void initialize() {
		
		vscroll = (ScrollView) findViewById(R.id.vscroll);
		seite = (LinearLayout) findViewById(R.id.seite);
		platzhalter_oben = (LinearLayout) findViewById(R.id.platzhalter_oben);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		zuruek = (Button) findViewById(R.id.zuruek);
		starts = (TextView) findViewById(R.id.starts);
		stat1 = (TextView) findViewById(R.id.stat1);
		firstjoin = (TextView) findViewById(R.id.firstjoin);
		stat2 = (TextView) findViewById(R.id.stat2);
		number = (TextView) findViewById(R.id.number);
		stat3 = (TextView) findViewById(R.id.stat3);
		menuecount = (TextView) findViewById(R.id.menuecount);
		stat4 = (TextView) findViewById(R.id.stat4);
		reloadcount = (TextView) findViewById(R.id.reloadcount);
		stat5 = (TextView) findViewById(R.id.stat5);
		votencounter = (TextView) findViewById(R.id.votencounter);
		stat6 = (TextView) findViewById(R.id.stat6);
		nonetopen = (TextView) findViewById(R.id.nonetopen);
		stat7 = (TextView) findViewById(R.id.stat7);
		ratenumber = (TextView) findViewById(R.id.ratenumber);
		stat8 = (TextView) findViewById(R.id.stat8);
		ratecomment = (TextView) findViewById(R.id.ratecomment);
		stat9 = (TextView) findViewById(R.id.stat9);
		version_text = (TextView) findViewById(R.id.version_text);
		stat10 = (TextView) findViewById(R.id.stat10);
		mode = getSharedPreferences("mode", Activity.MODE_PRIVATE);
		startcount = getSharedPreferences("startcount", Activity.MODE_PRIVATE);
		Usernumber = getSharedPreferences("Usernumber", Activity.MODE_PRIVATE);
		first = getSharedPreferences("first", Activity.MODE_PRIVATE);
		menucount = getSharedPreferences("menucount", Activity.MODE_PRIVATE);
		reloadcounter = getSharedPreferences("reloadcounter", Activity.MODE_PRIVATE);
		votecount = getSharedPreferences("votecount", Activity.MODE_PRIVATE);
		nonetcounter = getSharedPreferences("nonetcounter", Activity.MODE_PRIVATE);
		rate_number = getSharedPreferences("rate_number", Activity.MODE_PRIVATE);
		rate_comment = getSharedPreferences("rate_comment", Activity.MODE_PRIVATE);
		
		zuruek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
	}
	private void initializeLogic() {
		pkg = "com.biberjumpers.kadcom.app";
		try { android.content.pm.PackageInfo pinfo = getPackageManager().getPackageInfo(pkg, android.content.pm.PackageManager.GET_ACTIVITIES); p_ver = pinfo.versionName; } catch (Exception e){ showMessage(e.toString()); }
		stat1.setText(startcount.getString("startcount", ""));
		stat2.setText(first.getString("first", ""));
		stat3.setText(Usernumber.getString("Usernumber", ""));
		stat4.setText(menucount.getString("menucount", ""));
		stat5.setText(reloadcounter.getString("reloadcounter", ""));
		stat6.setText(votecount.getString("votecount", ""));
		stat7.setText(nonetcounter.getString("nonetcounter", ""));
		stat10.setText(p_ver);
		if (rate_number.getString("rate_number", "").equals("")) {
			stat8.setText("-");
		}
		else {
			stat8.setText(rate_number.getString("rate_number", ""));
		}
		if (rate_comment.getString("rate_comment", "").equals("") || rate_comment.getString("rate_comment", "").equals(" ")) {
			stat9.setText("-");
		}
		else {
			stat9.setText(rate_comment.getString("rate_comment", ""));
		}
		if (mode.getString("mode", "").equals("0")) {
			platzhalter_oben.setBackgroundColor(0xFF00B0FF);
			zuruek.setTextColor(0xFFE91E63);
			linear1.setBackgroundColor(0xFF039BE5);
			linear2.setBackgroundColor(0xFF0288D1);
			linear3.setBackgroundColor(0xFF039BE5);
			seite.setBackgroundColor(0xFF00B0FF);
			linear4.setBackgroundColor(0xFF0288D1);
			starts.setTextColor(0xFF000000);
			stat1.setTextColor(0xFF000000);
			firstjoin.setTextColor(0xFF000000);
			stat2.setTextColor(0xFF000000);
			number.setTextColor(0xFF000000);
			stat3.setTextColor(0xFF000000);
			stat4.setTextColor(0xFF000000);
			menuecount.setTextColor(0xFF000000);
			vscroll.setBackgroundColor(0xFF00B0FF);
			linear5.setBackgroundColor(0xFF039BE5);
			reloadcount.setTextColor(0xFF000000);
			stat5.setTextColor(0xFF000000);
			linear6.setBackgroundColor(0xFF0288D1);
			votencounter.setTextColor(0xFF000000);
			stat6.setTextColor(0xFF000000);
			linear7.setBackgroundColor(0xFF039BE5);
			nonetopen.setTextColor(0xFF000000);
			stat7.setTextColor(0xFF000000);
			linear8.setBackgroundColor(0xFF0288D1);
			stat8.setTextColor(0xFF000000);
			ratenumber.setTextColor(0xFF000000);
			linear9.setBackgroundColor(0xFF039BE5);
			ratecomment.setTextColor(0xFF000000);
			stat9.setTextColor(0xFF000000);
			linear10.setBackgroundColor(0xFF0288D1);
			version_text.setTextColor(0xFF000000);
			stat10.setTextColor(0xFF000000);
		}
		else {
			platzhalter_oben.setBackgroundColor(0xFF37474F);
			zuruek.setTextColor(0xFF2196F3);
			linear1.setBackgroundColor(0xFF455A64);
			linear2.setBackgroundColor(0xFF546E7A);
			linear3.setBackgroundColor(0xFF455A64);
			linear4.setBackgroundColor(0xFF546E7A);
			starts.setTextColor(0xFFFFFFFF);
			stat1.setTextColor(0xFFFFFFFF);
			firstjoin.setTextColor(0xFFFFFFFF);
			stat2.setTextColor(0xFFFFFFFF);
			number.setTextColor(0xFFFFFFFF);
			stat3.setTextColor(0xFFFFFFFF);
			menuecount.setTextColor(0xFFFFFFFF);
			stat4.setTextColor(0xFFFFFFFF);
			vscroll.setBackgroundColor(0xFF37474F);
			linear5.setBackgroundColor(0xFF455A64);
			reloadcount.setTextColor(0xFFFFFFFF);
			stat5.setTextColor(0xFFFFFFFF);
			linear6.setBackgroundColor(0xFF546E7A);
			votencounter.setTextColor(0xFFFFFFFF);
			stat6.setTextColor(0xFFFFFFFF);
			linear7.setBackgroundColor(0xFF455A64);
			nonetopen.setTextColor(0xFFFFFFFF);
			stat7.setTextColor(0xFFFFFFFF);
			linear8.setBackgroundColor(0xFF546E7A);
			ratenumber.setTextColor(0xFFFFFFFF);
			stat8.setTextColor(0xFFFFFFFF);
			linear9.setBackgroundColor(0xFF455A64);
			ratecomment.setTextColor(0xFFFFFFFF);
			stat9.setTextColor(0xFFFFFFFF);
			linear10.setBackgroundColor(0xFF455A64);
			version_text.setTextColor(0xFFFFFFFF);
			stat10.setTextColor(0xFFFFFFFF);
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
